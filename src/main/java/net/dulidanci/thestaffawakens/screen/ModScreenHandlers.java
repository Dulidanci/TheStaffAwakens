package net.dulidanci.thestaffawakens.screen;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<StaffUpgradeStationEmptyScreenHandler> STAFF_UPGRADE_STATION_EMPTY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(TheStaffAwakens.MOD_ID, "staff_upgrade_station_empty"),
                    new ExtendedScreenHandlerType<>(StaffUpgradeStationEmptyScreenHandler::new));

    public static final ScreenHandlerType<StaffUpgradeStationStaffScreenHandler> STAFF_UPGRADE_STATION_STAFF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(TheStaffAwakens.MOD_ID, "staff_upgrade_station_staff"),
                    new ExtendedScreenHandlerType<>(StaffUpgradeStationStaffScreenHandler::new));

    public static void register() {
        TheStaffAwakens.LOGGER.info("Registering Screen Handler for " + TheStaffAwakens.MOD_ID);
    }
}
