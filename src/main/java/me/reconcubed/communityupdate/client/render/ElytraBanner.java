package me.reconcubed.communityupdate.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import me.reconcubed.communityupdate.util.BannerTextures;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.ArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElytraBanner<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation elytraTexture = new ResourceLocation("textures/entity/elytra.png");
    private static final BannerTextures.Cache ELYTRA = new BannerTextures.Cache("elyta_", new ResourceLocation("textures/entity/elytra_base.png"), "textures/entity/elytra/");
    private final BannerTileEntity renderBanner = new BannerTileEntity();
    protected final LivingRenderer renderPlayer;
    private final ElytraModel<T> elytraModel = new ElytraModel();

    public ElytraBanner(IEntityRenderer<T, M> entityRendererIn, LivingRenderer renderPlayer) {
        super(entityRendererIn);
        this.renderPlayer = renderPlayer;
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemStack = entityIn.getItemStackFromSlot(EquipmentSlotType.CHEST);

        if (itemStack.getItem() == Items.ELYTRA) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            if (entityIn instanceof AbstractClientPlayerEntity) {
                AbstractClientPlayerEntity clientPlayerEntity = (AbstractClientPlayerEntity) entityIn;

                if (itemStack.hasTag() && itemStack.getTag().contains("BlockEntityTag")) {
                    this.renderBanner.loadFromItemStack(itemStack, DyeColor.byId(itemStack.getTag().getCompound("BlockEntityTag").getInt("Base")));
                    ResourceLocation texture = ELYTRA.getResourceLocation(this.renderBanner.getPatternResourceLocation(), this.renderBanner.getPatternList(), this.renderBanner.getColorList());
                    this.renderPlayer.bindTexture(texture);
                } else if (clientPlayerEntity.isPlayerInfoSet() && clientPlayerEntity.getLocationElytra() != null) {
                    this.renderPlayer.bindTexture(clientPlayerEntity.getLocationElytra());
                } else if (clientPlayerEntity.hasPlayerInfo() && clientPlayerEntity.getLocationCape() != null && clientPlayerEntity.isWearing(PlayerModelPart.CAPE)) {
                    this.renderPlayer.bindTexture(clientPlayerEntity.getLocationCape());
                } else {
                    this.renderPlayer.bindTexture(elytraTexture);
                }

                GlStateManager.pushMatrix();
                GlStateManager.translatef(0.0F, 0.0F, 0.125F);
                this.elytraModel.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                this.elytraModel.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                if (itemStack.isEnchanted()) {
                    ArmorLayer.func_215338_a(this::bindTexture, entityIn, this.elytraModel, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();

            }

        }

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
