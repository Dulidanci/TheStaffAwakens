package net.dulidanci.thestaffawakens.entity.client;

import net.dulidanci.thestaffawakens.entity.custom.LoyalBeeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LoyalBeeEntityRenderer extends MobEntityRenderer<LoyalBeeEntity, BeeEntityModel<LoyalBeeEntity>> {
    private static final Identifier ANGRY_TEXTURE = new Identifier("minecraft", "textures/entity/bee/bee_angry.png");
    private static final Identifier ANGRY_NECTAR_TEXTURE = new Identifier("minecraft", "textures/entity/bee/bee_angry_nectar.png");
    private static final Identifier PASSIVE_TEXTURE = new Identifier("minecraft", "textures/entity/bee/bee.png");
    private static final Identifier NECTAR_TEXTURE = new Identifier("minecraft", "textures/entity/bee/bee_nectar.png");

    public LoyalBeeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BeeEntityModel<>(context.getPart(EntityModelLayers.BEE)), 0.4F);
    }

    @Override
    public Identifier getTexture(LoyalBeeEntity loyalBee) {
        if (loyalBee.hasAngerTime()) {
            return loyalBee.hasNectar() ? ANGRY_NECTAR_TEXTURE : ANGRY_TEXTURE;
        } else {
            return loyalBee.hasNectar() ? NECTAR_TEXTURE : PASSIVE_TEXTURE;
        }
    }
}