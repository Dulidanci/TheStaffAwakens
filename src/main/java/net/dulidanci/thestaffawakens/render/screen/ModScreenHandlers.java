package net.dulidanci.thestaffawakens.render.screen;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<StaffWorkbenchScreenHandler> STAFF_WORKBENCH_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(TheStaffAwakens.MOD_ID, "staff_workbench"),
                    new ExtendedScreenHandlerType<>(StaffWorkbenchScreenHandler::new));

    public static void register() {
        TheStaffAwakens.LOGGER.info("Registering Screen Handler for " + TheStaffAwakens.MOD_ID);
    }
}
