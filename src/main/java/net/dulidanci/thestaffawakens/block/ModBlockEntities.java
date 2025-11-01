package net.dulidanci.thestaffawakens.block;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.entity.StaffWorkbenchBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<StaffWorkbenchBlockEntity> STAFF_WORKBENCH_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench_block_entity"),
                    BlockEntityType.Builder.create(StaffWorkbenchBlockEntity::new, ModBlocks.STAFF_WORKBENCH).build());

    public static void registeringBlockEntities() {
        TheStaffAwakens.LOGGER.info("Registering Block Entities for " + TheStaffAwakens.MOD_ID);
    }
}
