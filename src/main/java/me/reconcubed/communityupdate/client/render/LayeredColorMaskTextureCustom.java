package me.reconcubed.communityupdate.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.item.DyeColor;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

// Cheers to TheGreyGhost on Minecraft Forge Forums for help with this one!
@OnlyIn(Dist.CLIENT)
public class LayeredColorMaskTextureCustom extends Texture {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ResourceLocation textureLocation;
    private final List<String> listTextures;
    private final List<DyeColor> listDyeColors;

    public LayeredColorMaskTextureCustom(ResourceLocation textureLocationIn, List<String> p_i46101_2_, List<DyeColor> p_i46101_3_) {
        this.textureLocation = textureLocationIn;
        this.listTextures = p_i46101_2_;
        this.listDyeColors = p_i46101_3_;
    }

    public void loadTexture(IResourceManager manager) throws IOException {
        try (
                IResource iresource = manager.getResource(this.textureLocation);
                NativeImage baseElytra = NativeImage.read(iresource.getInputStream());
                NativeImage overlaidElytra = new NativeImage(baseElytra.getWidth(), baseElytra.getHeight(), false);
        ) {
            overlaidElytra.copyImageData(baseElytra);

            for (int i = 0; i < 17 && i < this.listTextures.size() && i < this.listDyeColors.size(); ++i) {
                String bannerTextureRL = this.listTextures.get(i);
                if (bannerTextureRL != null) {
                    try (
                            NativeImage bannerLayer = net.minecraftforge.client.MinecraftForgeClient.getImageLayer(new ResourceLocation(bannerTextureRL), manager);
                    ) {
                        int bannerLayerColour = this.listDyeColors.get(i).getSwappedColorValue();
                        if (bannerLayer.getWidth() == overlaidElytra.getWidth() && bannerLayer.getHeight() == overlaidElytra.getHeight()) {
                            for (int height = 0; height < bannerLayer.getHeight(); ++height) {
                                for (int width = 0; width < bannerLayer.getWidth(); ++width) {

                                    int alphaBanner = bannerLayer.getPixelRGBA(width, height) & 0xff;  // extract the red channel, could have used green or blue also.
                                    int alphaElytra = baseElytra.getPixelLuminanceOrAlpha(width, height) & 0xff;
                                    //  algorithm is:
                                    //  if elytra pixel is transparent, do nothing
                                    //  otherwise:
                                    //    the banner blend layer is a greyscale which is converted to a transparency:
                                    //     blend the banner's colour into elytra pixel using the banner blend transparency

                                    if (alphaElytra != 0 && alphaBanner != 0) {
                                        int elytraPixelRGBA = baseElytra.getPixelRGBA(width, height);
                                        int multipliedColorRGB = MathHelper.multiplyColor(elytraPixelRGBA, bannerLayerColour) & 0xFFFFFF;
                                        int multipliedColorRGBA = multipliedColorRGB | (alphaBanner << 24);
                                        overlaidElytra.blendPixel(width, height, multipliedColorRGBA);
                                    }

                                }
                            }
                        }
                    }
                }
            }

            TextureUtil.prepareImage(this.getGlTextureId(), overlaidElytra.getWidth(), overlaidElytra.getHeight());
            GlStateManager.pixelTransfer(GL11.GL_ALPHA_BIAS, 0.0F);
            overlaidElytra.uploadTextureSub(0, 0, 0, false);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load layered color mask image", (Throwable) ioexception);
        }

    }
}
