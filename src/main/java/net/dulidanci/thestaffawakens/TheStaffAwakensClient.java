package net.dulidanci.thestaffawakens;

import net.dulidanci.thestaffawakens.block.ModBlockEntities;
import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.render.block.entity.StaffUpgradeStationBlockEntityRenderer;
import net.dulidanci.thestaffawakens.entity.ModEntities;
import net.dulidanci.thestaffawakens.entity.client.LoyalBeeEntityRenderer;
import net.dulidanci.thestaffawakens.item.ModItems;
import net.dulidanci.thestaffawakens.render.item.StaffItemRenderer;
import net.dulidanci.thestaffawakens.screen.ModScreenHandlers;
import net.dulidanci.thestaffawakens.screen.StaffUpgradeStationEmptyScreen;
import net.dulidanci.thestaffawakens.screen.StaffUpgradeStationStaffScreen;
import net.dulidanci.thestaffawakens.util.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.Item;

public class TheStaffAwakensClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUEPRINT_PLANKS, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.LOYAL_BEE, LoyalBeeEntityRenderer::new);

        HudRenderer.register();

        HandledScreens.register(ModScreenHandlers.STAFF_UPGRADE_STATION_EMPTY_SCREEN_HANDLER, StaffUpgradeStationEmptyScreen::new);
        HandledScreens.register(ModScreenHandlers.STAFF_UPGRADE_STATION_STAFF_SCREEN_HANDLER, StaffUpgradeStationStaffScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.STAFF_UPGRADE_STATION_BLOCK_ENTITY, StaffUpgradeStationBlockEntityRenderer::new);

        TheStaffAwakens.LOGGER.info("Attempting dynamic rendering for staffs! Count: {}", ModItems.STAFFS.size());
        for (Item staff : ModItems.STAFFS) {
            BuiltinItemRendererRegistry.INSTANCE.register(staff, new StaffItemRenderer());
        }
    }
}
