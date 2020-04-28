package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.item.PebbleBagBaseItem;
import me.reconcubed.communityupdate.recipe.ElytraRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, CommunityUpdate.MODID);

    public static final RegistryObject<IRecipeSerializer<?>> ELYTRA_DECORATION = RECIPES.register("crafting_special_elytradecoration", () -> new SpecialRecipeSerializer<>(ElytraRecipe::new));
//    .setRegistryName("crafting_special_elytradecoration")
}
