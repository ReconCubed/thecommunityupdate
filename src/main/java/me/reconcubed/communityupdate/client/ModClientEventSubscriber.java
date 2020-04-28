package me.reconcubed.communityupdate.client;


import com.google.common.collect.Lists;
import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.client.gui.FishNetScreen;
import me.reconcubed.communityupdate.client.render.ElytraBanner;
import me.reconcubed.communityupdate.config.ClientConfig;
import me.reconcubed.communityupdate.init.ModBlocks;
import me.reconcubed.communityupdate.init.ModContainerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = CommunityUpdate.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventSubscriber {

    @SubscribeEvent
    public static void color(ModelRegistryEvent e) {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        IBlockColor waterColour = (blockState, iEnviromentBlockReader, blockPos, i) -> BiomeColors.getWaterColor(iEnviromentBlockReader, blockPos);
        IBlockColor iBlockColor = (blockState, iEnviromentBlockReader, blockPos, i) -> Integer.decode(ClientConfig.color.get());

        blockColors.register(iBlockColor, ModBlocks.VEXED_EARTH.get());
        blockColors.register(waterColour, ModBlocks.WISHING_WELL.get());

        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        final IItemColor itemBlockColor = (stack, tintIndex) -> {
            final BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(state, null, null, tintIndex);
        };
        itemColors.register(itemBlockColor, ModBlocks.VEXED_EARTH.get());
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.FISH_NET.get(), FishNetScreen::new);
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) throws NoSuchFieldException, IllegalAccessException {
        Field skinMapField = EntityRendererManager.class.getDeclaredField("skinMap");
        skinMapField.setAccessible(true);

        Map skinMap = (Map)skinMapField.get(Minecraft.getInstance().getRenderManager());

        PlayerRenderer playerRendererDefault = (PlayerRenderer) skinMap.get("default");

        PlayerRenderer playerRendererSlim = (PlayerRenderer) skinMap.get("slim");

        Field layerRenderersField = LivingRenderer.class.getDeclaredField("layerRenderers");

        layerRenderersField.setAccessible(true);

        List layersDefault = (List) layerRenderersField.get(playerRendererDefault);
        List layersSlim = (List) layerRenderersField.get(playerRendererSlim);

        List newLayersDefault = Lists.newArrayList();
        List newLayersSlim = Lists.newArrayList();

        newLayersDefault.add(new ElytraBanner<>(playerRendererDefault, playerRendererDefault));
        newLayersSlim.add(new ElytraBanner<>(playerRendererSlim, playerRendererSlim));

        Iterator layerIterator = layersDefault.iterator();

        Object layer;
        while(layerIterator.hasNext()) {
            layer = layerIterator.next();
            if (!(layer instanceof ElytraLayer)) {
                newLayersDefault.add((LayerRenderer)layer);
            }
        }

        layerIterator = layersSlim.iterator();

        while(layerIterator.hasNext()) {
            layer = layerIterator.next();
            if (!(layer instanceof ElytraLayer)) {
                newLayersSlim.add((LayerRenderer)layer);
            }
        }

        layerRenderersField.set(playerRendererDefault, newLayersDefault);
        layerRenderersField.set(playerRendererSlim, newLayersSlim);
        skinMap.replace("default", playerRendererDefault);
        skinMap.replace("slim", playerRendererSlim);
        skinMapField.set(Minecraft.getInstance().getRenderManager(), skinMap);

    }

}
