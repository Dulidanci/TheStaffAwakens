package net.dulidanci.thestaffawakens.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StaffUpgradeStationEmptyScreen extends HandledScreen<StaffUpgradeStationEmptyScreenHandler> {
    private static final Identifier EMPTY_TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "textures/gui/staff_upgrade_station_empty.png");

    public StaffUpgradeStationEmptyScreen(StaffUpgradeStationEmptyScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, EMPTY_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(EMPTY_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
