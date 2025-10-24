package net.dulidanci.thestaffawakens.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class MobUtilities {
    public static void setNoAI(LivingEntity entity, boolean noAI) {
        NbtCompound nbt = new NbtCompound();
        entity.writeNbt(nbt);
        nbt.putBoolean("NoAI", noAI);
        entity.readNbt(nbt);
    }
}
