package net.dulidanci.thestaffawakens.network;

import io.netty.buffer.Unpooled;
import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.entity.StaffWorkbenchBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModPackets {
    public static final Identifier STAFF_UPGRADE = new Identifier(TheStaffAwakens.MOD_ID, "staff_upgrade");

    public static void sendButtonClick(BlockPos pos) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        ClientPlayNetworking.send(STAFF_UPGRADE, buf);
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(STAFF_UPGRADE,
                (minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
                    BlockPos pos = packetByteBuf.readBlockPos();

                    minecraftServer.execute(() -> {
                        var be = serverPlayerEntity.getWorld().getBlockEntity(pos);
                        if (be instanceof StaffWorkbenchBlockEntity staffWorkbench) {
                            staffWorkbench.attemptUpgradingStaff();
                        }
                    });
                });
    }
}
