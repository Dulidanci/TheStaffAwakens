package net.dulidanci.staffmod.enchantments;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.enchantments.custom.MagicalEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final Enchantment MAGICAL = registerEnchantment("magical", new MagicalEnchantment());

    public static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(StaffMod.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        StaffMod.LOGGER.info("Registering ModEnchantments for " + StaffMod.MOD_ID);
    }
}
