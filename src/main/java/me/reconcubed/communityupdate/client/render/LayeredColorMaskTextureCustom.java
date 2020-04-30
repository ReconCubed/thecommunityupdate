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
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
                NativeImage nativeimage1 = new NativeImage(nativeimage.getWidth(), nativeimage.getHeight(), false);
        ) {
            nativeimage1.copyImageData(nativeimage);

            for (int i = 0; i < 17 && i < this.listTextures.size() && i < this.listDyeColors.size(); ++i) {
                String s = this.listTextures.get(i);
                if (s != null) {
                    try (
                            NativeImage nativeimage2 = net.minecraftforge.client.MinecraftForgeClient.getImageLayer(new ResourceLocation(s), manager);
                    ) {
                        int swappedColorValue = this.listDyeColors.get(i).getSwappedColorValue();
                        if (nativeimage2.getWidth() == nativeimage1.getWidth() && nativeimage2.getHeight() == nativeimage1.getHeight()) {
                            for (int height = 0; height < nativeimage2.getHeight(); ++height) {
                                for (int width = 0; width < nativeimage2.getWidth(); ++width) {

                                    int pixelRGBA = nativeimage2.getPixelRGBA(width, height);

                                    if (nativeimage2.getPixelLuminanceOrAlpha(width, height) == 0) {

                                        int color = (pixelRGBA & 255) << 24 & -16777216;

                                        nativeimage1.setPixelRGBA(width, height, color);
                                    } else if ((pixelRGBA & -16777216) != 0) {
                                        int shiftedPixelRGBA = (pixelRGBA & 255) << 24 & -16777216;
                                        int pixelRGBA1 = nativeimage.getPixelRGBA(width, height);
                                        int multipliedColor = MathHelper.multiplyColor(pixelRGBA1, swappedColorValue) & 16777215;
                                        nativeimage1.blendPixel(width, height, shiftedPixelRGBA | multipliedColor);
                                    }

                                }
                            }
                        }
                    }
                }
            }

            TextureUtil.prepareImage(this.getGlTextureId(), nativeimage1.getWidth(), nativeimage1.getHeight());
            GlStateManager.pixelTransfer(3357, Float.MAX_VALUE);
            nativeimage1.uploadTextureSub(0, 0, 0, false);
            GlStateManager.pixelTransfer(3357, 0.0F);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load layered color mask image", (Throwable) ioexception);
        }

    }
}