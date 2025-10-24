package net.dulidanci.thestaffawakens.enchantments;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.enchantments.custom.MagicalEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final Enchantment MAGICAL = registerEnchantment("magical", new MagicalEnchantment());

    public static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(TheStaffAwakens.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        TheStaffAwakens.LOGGER.info("Registering ModEnchantments for " + TheStaffAwakens.MOD_ID);
    }
}
