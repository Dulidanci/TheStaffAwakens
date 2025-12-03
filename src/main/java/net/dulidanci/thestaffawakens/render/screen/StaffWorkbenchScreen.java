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
import net.minecraft.client.gui.widget.*;
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
    private static final Identifier STATS_TEXT_FIELD_TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/stats_text_field");
    private static final Identifier STATS_TEXT_FIELD_DISABLED_TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/stats_text_field_disabled");
    private static final Identifier UPGRADE_TEXT_FIELD_TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/upgrade_text_field");
    private static final Identifier UPGRADE_TEXT_FIELD_DISABLED_TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/upgrade_text_field_disabled");
    private final PlayerEntity player;
    private UpgradeArrowButton upgradeArrow;
    public int manaCost;
    public int rechargeTime;
    public int upgradeCost;

    public StaffWorkbenchScreen(StaffWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.player = inventory.player;
    }

    @Override
    protected void init() {
        super.init();

        this.upgradeArrow = this.addDrawableChild(new UpgradeArrowButton((this.width - this.backgroundWidth) / 2 + 57, (this.height - this.backgroundHeight) / 2 + 52));
    }

    public void update() {
        upgradeArrow.update();
    }

    public boolean shouldRenderStats() {
        return handler.blockEntity.shouldRenderStats();
    }

    public boolean shouldRenderUpgrades() {
        return handler.blockEntity.shouldRenderUpgrades();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        update();

        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawItem(context, MinecraftClient.getInstance().world, handler.blockEntity.getRenderStack(),  (width - backgroundWidth) / 2 + 50,  (height - backgroundHeight) / 2 + 28, 0, ModelTransformationMode.GROUND);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        if (shouldRenderStats()) {
//            int mana = handler.getManaCost();
            int mana = 8;
            int time = 5;
            new TextWidget(109, 11, 56, 10, Text.translatable("gui.thestaffawakens.staff_workbench.line_1"), this.textRenderer)
                    .alignLeft()
                    .render(context, mouseX, mouseY, 0);
            new TextWidget(109, 21, 56, 10, Text.translatable("gui.thestaffawakens.staff_workbench.line_2", mana), this.textRenderer)
                    .alignRight()
                    .render(context, mouseX, mouseY, 0);
            new TextWidget(109, 31, 56, 10, Text.translatable("gui.thestaffawakens.staff_workbench.line_3"), this.textRenderer)
                    .alignLeft()
                    .render(context, mouseX, mouseY, 0);
            new TextWidget(109, 41, 56, 10, Text.translatable("gui.thestaffawakens.staff_workbench.line_4", time), this.textRenderer)
                    .alignRight()
                    .render(context, mouseX, mouseY, 0);
        }

        if (shouldRenderUpgrades()) {
            int level = 10;
            new TextWidget(109, 57, 56, 10, Text.translatable("gui.thestaffawakens.staff_workbench.line_5"), this.textRenderer)
                    .alignLeft()
                    .render(context, mouseX, mouseY, 0);
            new TextWidget(109, 67, 56, 10, Text.translatable("gui.thestaffawakens.staff_workbench.line_6", level), this.textRenderer)
                    .alignRight()
                    .render(context, mouseX, mouseY, 0);
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        context.drawGuiTexture(shouldRenderStats() ? STATS_TEXT_FIELD_TEXTURE : STATS_TEXT_FIELD_DISABLED_TEXTURE,
                x + 107, y + 8, 60, 44);

        context.drawGuiTexture(shouldRenderUpgrades() ? UPGRADE_TEXT_FIELD_TEXTURE : UPGRADE_TEXT_FIELD_DISABLED_TEXTURE,
                x + 107, y + 54, 60, 24);
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
                    matrices.multiplyPositionMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                    matrices.scale(48.0F, 48.0F, 48.0F);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                    matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
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
                new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/upgrade_arrow"),
                new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/upgrade_arrow_error"),
                new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/upgrade_arrow_highlighted"),
                new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench/upgrade_arrow_highlighted_error"));
        private boolean previousTooltip;


        public UpgradeArrowButton(int x, int y) {
            super(x, y, 21, 16, TEXTURES, ButtonWidget::onPress);

            this.previousTooltip = false;
            this.updateButtonState();
            this.updateTooltip();
        }

        public void update() {
            this.updateButtonState();
            this.checkTooltip();
        }

        @Override
        public void onPress() {
            upgradeCost = handler.blockEntity.getUpgradeCost();
            if (handler.blockEntity.canUpgradeStaff() && playerHasEnoughXp()) {
                ModPackets.sendButtonClick(handler.blockEntity.getPos());
                if (!player.isCreative() && !player.isSpectator()) {
                    player.addExperienceLevels(-upgradeCost);
                }
            }

            this.setFocused(false);
        }

        private boolean playerHasEnoughXp() {
            return player.isCreative() || player.isSpectator() || player.experienceLevel >= upgradeCost;
        }

        public void updateButtonState() {
            this.active = handler.blockEntity.canUpgrade() && playerHasEnoughXp();
        }

        public void checkTooltip() {
            if (this.active != previousTooltip) {
                updateTooltip();
            }
        }

        public void updateTooltip() {
            if (this.active) {
                this.setTooltip(Tooltip.of(Text.of("Upgrade Staff")));
                previousTooltip = true;
            } else {
                this.setTooltip(Tooltip.of(Text.of("Can not upgrade Staff")));
                previousTooltip = false;
            }
        }
    }
}
