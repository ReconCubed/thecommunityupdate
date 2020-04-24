package me.reconcubed.communityupdate.client.gui;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.container.FishNetContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FishNetScreen extends ContainerScreen<FishNetContainer> implements IHasContainer<FishNetContainer> {
    private static final ResourceLocation guiTexture = new ResourceLocation(CommunityUpdate.MODID, "textures/gui/fish_net_gui.png");

    public FishNetScreen(FishNetContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;

        this.xSize = 175;
        this.ySize = 167;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(guiTexture);
        blit((width - xSize)/2, (height - ySize)/2, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, 73.0f, 4210752);
    }
}
