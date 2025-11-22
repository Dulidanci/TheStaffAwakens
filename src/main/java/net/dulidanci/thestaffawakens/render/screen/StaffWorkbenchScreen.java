package net.dulidanci.thestaffawakens.render.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.dulidanci.thestaffawakens.item.staffs.StaffTypes;
import net.dulidanci.thestaffawakens.network.ModPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class StaffWorkbenchScreen extends HandledScreen<StaffWorkbenchScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "textures/gui/staff_workbench.png");
    private final StaffWorkbenchScreenHandler handler;
    private final PlayerEntity player;

//    private float delta = 0.0F;

    public StaffWorkbenchScreen(StaffWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
        this.player = inventory.player;
    }

    @Override
    protected void init() {
        super.init();

        this.addDrawableChild(new UpgradeArrowButton((this.width - this.backgroundWidth) / 2 + 57, (this.height - this.backgroundHeight) / 2 + 52));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawItem(context, MinecraftClient.getInstance().world, handler.blockEntity.getRenderStack(),  (width - backgroundWidth) / 2 + 50,  (height - backgroundHeight) / 2 + 28, 0, ModelTransformationMode.GROUND);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void drawItem(DrawContext context, @Nullable World world, ItemStack stack, int x, int y, int z, ModelTransformationMode mode) {
        if (client != null) {
            ItemRenderer itemRenderer = this.client.getItemRenderer();
            MatrixStack matrices = context.getMatrices();

            if (!stack.isEmpty()) {
                BakedModel bakedModel = itemRenderer.getModel(stack, world, null, 0);
                matrices.push();
                matrices.translate((float) (x), (float) (y), (float) (150 + (bakedModel.hasDepth() ? z : 0)));
                if (stack.getItem() instanceof StaffItem staffItem && staffItem.getStaff().getType().equals(StaffTypes.LOG)) matrices.translate(0.0, 3.0, 0.0);

                try {
//                    delta = (delta + 0.2F) % 720F;
                    matrices.multiplyPositionMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                    matrices.scale(48.0F, 48.0F, 48.0F);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                    matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
//                    matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
//                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(delta));
//                    matrices.translate(0, 0, Math.sin(delta * 2F * Math.PI / 360F * 3.5F) / 20F);
                    boolean bl = !bakedModel.isSideLit();
                    if (bl) {
                        DiffuseLighting.disableGuiDepthLighting();
                    }
                    this.client.getItemRenderer().renderItem(stack, mode, false, matrices, context.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, bakedModel);
                    context.draw();
                    if (bl) {
                        DiffuseLighting.enableGuiDepthLighting();
                    }
                } catch (Throwable var12) {
                    CrashReport crashReport = CrashReport.create(var12, "Rendering item");
                    CrashReportSection crashReportSection = crashReport.addElement("Item being rendered");
                    crashReportSection.add("Item Type", () -> String.valueOf(stack.getItem()));
                    crashReportSection.add("Item Damage", () -> String.valueOf(stack.getDamage()));
                    crashReportSection.add("Item NBT", () -> String.valueOf(stack.getNbt()));
                    crashReportSection.add("Item Foil", () -> String.valueOf(stack.hasGlint()));
                    throw new CrashException(crashReport);
                }
                matrices.pop();
            }
        }
    }

    private class UpgradeArrowButton extends TexturedButtonWidget {
        private static final ButtonTextures TEXTURES = new ButtonTextures(
                new Identifier(TheStaffAwakens.MOD_ID, "upgrade_arrow"),
                new Identifier(TheStaffAwakens.MOD_ID, "upgrade_arrow_error"),
                new Identifier(TheStaffAwakens.MOD_ID, "upgrade_arrow_highlighted"),
                new Identifier(TheStaffAwakens.MOD_ID, "upgrade_arrow_highlighted_error"));

        public UpgradeArrowButton(int x, int y) {
            super(x, y, 21, 16, TEXTURES, ButtonWidget::onPress);

            this.setTooltip(Tooltip.of(Text.of("Upgrade Staff")));
        }

        @Override
        public void onPress() {
            int upgradeCost = handler.blockEntity.getUpgradeCost();
            if (handler.blockEntity.canUpgradeStaff() && (player.isCreative() || player.isSpectator() || player.experienceLevel >= upgradeCost)) {
                ModPackets.sendButtonClick(handler.blockEntity.getPos());
                if (!player.isCreative() && !player.isSpectator()) {
                    player.addExperienceLevels(-1);
                }
            }
        }

//        private boolean playerHasEnoughXp() {
//            return player.isCreative() || player.isSpectator() || player.experienceLevel >= upgradeCost;
//        }
    }
}
