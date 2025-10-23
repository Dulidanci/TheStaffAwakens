package net.dulidanci.thestaffawakens.enchantments.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class MagicalEnchantment extends Enchantment {
    public MagicalEnchantment() {
        super(
            Rarity.UNCOMMON,
            EnchantmentTarget.WEARABLE,
            new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}
        );
    }

    @Override
    public int getMinPower(int level) {
        return 10 + level * 5;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


}
