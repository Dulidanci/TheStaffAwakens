package net.dulidanci.thestaffawakens.block;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.entity.StaffUpgradeStationBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<StaffUpgradeStationBlockEntity> STAFF_UPGRADE_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TheStaffAwakens.MOD_ID, "staff_upgrade_station_be"),
                    FabricBlockEntityTypeBuilder.create(StaffUpgradeStationBlockEntity::new,
                            ModBlocks.STAFF_UPGRADE_STATION).build());

    public static void registeringBlockEntities() {
        TheStaffAwakens.LOGGER.info("Registering Block Entities for " + TheStaffAwakens.MOD_ID);
    }
}
