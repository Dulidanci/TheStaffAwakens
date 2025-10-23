package net.dulidanci.thestaffawakens.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import oshi.util.tuples.Pair;

public class HudRenderer {
    private static final Identifier ICON_TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "textures/gui/mana_point.png");

    public static void register() {
        TheStaffAwakens.LOGGER.info("Registering HudRenderer for " + TheStaffAwakens.MOD_ID);
        HudRenderCallback.EVENT.register((DrawContext context, float tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                if (!client.player.isCreative() && !client.player.isSpectator()) {
                    int x = 10;
                    int y = client.getWindow().getScaledHeight() - 30;

                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.setShaderTexture(0, ICON_TEXTURE);

                    context.drawTexture(ICON_TEXTURE, x, y, 0, 0, 16, 16, 16, 16);

                    Pair<Double, Double> playerMana = ManaSupplier.tickingPlayerStats(client.player);
                    String text = "Mana points: " + playerMana.getA() + " / " + playerMana.getB();
                    context.drawText(client.textRenderer, text, x + 20, y + 4, 0xFFFFFF, true);
                }
            }
        });
    }
}
