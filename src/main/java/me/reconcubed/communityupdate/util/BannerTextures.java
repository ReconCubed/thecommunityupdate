package me.reconcubed.communityupdate.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BannerTextures {
    public static final BannerTextures.Cache BANNER_DESIGNS = new BannerTextures.Cache("B", new ResourceLocation("textures/entity/banner_base.png"), "textures/entity/banner/");
    public static final BannerTextures.Cache SHIELD_DESIGNS = new BannerTextures.Cache("S", new ResourceLocation("textures/entity/shield_base.png"), "textures/entity/shield/");
    public static final ResourceLocation SHIELD_BASE_TEXTURE = new ResourceLocation("textures/entity/shield_base_nopattern.png");
    public static final ResourceLocation BANNER_BASE_TEXTURE = new ResourceLocation("textures/entity/banner/base.png");

    public BannerTextures() {}

    @OnlyIn(Dist.CLIENT)
    static class CacheEntry {
        public long lastUseMillis;
        public ResourceLocation textureLocation;

        private CacheEntry() {
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Cache {
        private final Map<String, BannerTextures.CacheEntry> cacheMap = Maps.newLinkedHashMap();
        private final ResourceLocation cacheResourceLocation;
        private final String cacheResourceBase;
        private final String cacheId;

        public Cache(String id, ResourceLocation baseResource, String resourcePath) {
            this.cacheId = id;
            this.cacheResourceLocation = baseResource;
            this.cacheResourceBase = resourcePath;
        }

        @Nullable
        public ResourceLocation getResourceLocation(String id, List<BannerPattern> patternList, List<DyeColor> colorList) {
            if (id.isEmpty()) {
                return null;
            } else {
                id = this.cacheId + id;
                BannerTextures.CacheEntry BannerTexturesCustom$cacheentry = (BannerTextures.CacheEntry)this.cacheMap.get(id);
                if (BannerTexturesCustom$cacheentry == null) {
                    if (this.cacheMap.size() >= 256 && !this.freeCacheSlot()) {
                        return BannerTextures.BANNER_BASE_TEXTURE;
                    }

                    List<String> list = Lists.newArrayList();
                    Iterator var6 = patternList.iterator();

                    while(var6.hasNext()) {
                        BannerPattern bannerpattern = (BannerPattern)var6.next();
                        list.add(this.cacheResourceBase + bannerpattern.getFileName() + ".png");
                    }

                    BannerTexturesCustom$cacheentry = new BannerTextures.CacheEntry();
                    BannerTexturesCustom$cacheentry.textureLocation = new ResourceLocation(id);
                    Minecraft.getInstance().getTextureManager().loadTexture(BannerTexturesCustom$cacheentry.textureLocation, new LayeredColorMaskTexture(this.cacheResourceLocation, list, colorList));
                    this.cacheMap.put(id, BannerTexturesCustom$cacheentry);
                }

                BannerTexturesCustom$cacheentry.lastUseMillis = System.currentTimeMillis();
                return BannerTexturesCustom$cacheentry.textureLocation;
            }
        }

        private boolean freeCacheSlot() {
            long i = System.currentTimeMillis();
            Iterator iterator = this.cacheMap.keySet().iterator();

            BannerTextures.CacheEntry BannerTexturesCustom$cacheentry;
            do {
                if (!iterator.hasNext()) {
                    return this.cacheMap.size() < 256;
                }

                String s = (String)iterator.next();
                BannerTexturesCustom$cacheentry = (BannerTextures.CacheEntry)this.cacheMap.get(s);
            } while(i - BannerTexturesCustom$cacheentry.lastUseMillis <= 5000L);

            Minecraft.getInstance().getTextureManager().deleteTexture(BannerTexturesCustom$cacheentry.textureLocation);
            iterator.remove();
            return true;
        }
    }

}
