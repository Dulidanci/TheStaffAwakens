package net.dulidanci.thestaffawakens.render.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
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
    private float delta = 0.0F;

    public StaffWorkbenchScreen(StaffWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    protected void init() {
        super.init();
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
        drawMouseoverTooltip(context, mouseX, mouseY);
        drawItem(context, MinecraftClient.getInstance().world, handler.blockEntity.getRenderStack(), width / 2, height / 2 - backgroundHeight / 4, 0, ModelTransformationMode.GROUND);
    }

    private void drawItem(DrawContext context, @Nullable World world, ItemStack stack, int x, int y, int z, ModelTransformationMode mode) {
        if (client != null) {
            ItemRenderer itemRenderer = this.client.getItemRenderer();
            MatrixStack matrices = context.getMatrices();

            if (!stack.isEmpty()) {
                BakedModel bakedModel = itemRenderer.getModel(stack, world, null, 0);
                matrices.push();
                matrices.translate((float) (x), (float) (y), (float) (150 + (bakedModel.hasDepth() ? z : 0)));

                try {
                    delta = (delta + 0.2F) % 720F;
                    matrices.multiplyPositionMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                    matrices.scale(48.0F, 48.0F, 48.0F);
                    matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(delta));
                    matrices.translate(0, 0, Math.sin(delta * 2F * Math.PI / 360F * 3.5F) / 20F);
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
            }
        }
    }
}
