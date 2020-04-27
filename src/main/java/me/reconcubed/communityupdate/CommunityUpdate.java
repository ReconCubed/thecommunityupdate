package me.reconcubed.communityupdate;

import me.reconcubed.communityupdate.config.ConfigHolder;
import me.reconcubed.communityupdate.config.ServerConfig;
import me.reconcubed.communityupdate.init.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(CommunityUpdate.MODID)
public class CommunityUpdate {
    public static final String MODID = "communityupdate";

    public static final Tag<EntityType<?>> blacklisted_entities = new EntityTypeTags.Wrapper(new ResourceLocation(MODID, "blacklisted"));
    public static final Tag<Block> spreadable = new BlockTags.Wrapper(new ResourceLocation(MODID, "spreadable"));
    public static final Tag<Block> paths = new BlockTags.Wrapper(new ResourceLocation(MODID, "paths"));

    final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    private static final Logger LOGGER = LogManager.getLogger();

    public CommunityUpdate() {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        OverrideItems.ITEMS.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
//        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModRecipes.RECIPES.register(modEventBus);

        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);

        MinecraftForge.EVENT_BUS.addListener(this::useRoseOnDirt);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (!ServerConfig.mobsAlwaysDropXp.get() || entity.world.isRemote || !(entity instanceof LivingEntity)) return;

        LivingEntity livingEntity = (LivingEntity) entity;

        if (livingEntity.recentlyHit <= 0) livingEntity.recentlyHit = 60;
        if (livingEntity.attackingPlayer == null) {
            livingEntity.attackingPlayer = FakePlayerFactory.getMinecraft(((ServerWorld) entity.world));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCombinationComplete(AnvilRepairEvent event) {
        if (event.getIngredientInput() != null && event.getIngredientInput().getItem().equals(ModItems.LORE_TAG.get()) && event.getIngredientInput().getCount() > 1) {
            ItemStack newInput = event.getIngredientInput().copy();
            newInput.shrink(1);

            if (!event.getPlayer().inventory.addItemStackToInventory(newInput)) {
                event.getPlayer().dropItem(newInput, false);
            }

        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        // TODO Fix bug where game crashes if two lore tags are in the anvil
        if (event.getRight().getItem().equals(ModItems.LORE_TAG.get()) && event.getRight().getTag().contains("loreStorage") && !event.getLeft().getItem().equals(ModItems.LORE_TAG.get())) {
            ItemStack tag = event.getRight();
            ItemStack input = event.getLeft();
            ItemStack output = input.copy();

            CompoundNBT outputTag;
            if (output.hasTag()) {
                outputTag = output.getTag();
            } else {
                outputTag = new CompoundNBT();
            }

            ListNBT lore = tag.getTag().getCompound("loreStorage").getList("lore", 8);
            ListNBT newLore = new ListNBT();

            for (INBT inbt : lore) {
                String serialiser = ITextComponent.Serializer.toJson(inbt.toFormattedComponent());
                newLore.add(new StringNBT(serialiser));
            }

            CompoundNBT display;
            if (outputTag.contains("display")) {
                display = outputTag.getCompound("display");
                display.put("Lore", newLore);
            } else {
                display = new CompoundNBT();
                display.put("Lore", newLore);
            }

            outputTag.put("display", display);

            output.setTag(outputTag);

            event.setOutput(output);
            event.setCost(1);
        }
    }


    private void useRoseOnDirt(PlayerInteractEvent.RightClickBlock e) {
        if (!ServerConfig.witherRose.get()) return;
        PlayerEntity p = e.getPlayer();
        World w = p.world;
        BlockPos pos = e.getPos();
        if (p.isSneaking() && !w.isRemote && e.getItemStack().getItem() == Items.WITHER_ROSE && w.getBlockState(pos).getBlock() == Blocks.DIRT) {
            w.setBlockState(pos, ModBlocks.VEXED_EARTH.get().getDefaultState());
        }
    }


}
