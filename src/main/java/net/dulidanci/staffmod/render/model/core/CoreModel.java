package net.dulidanci.staffmod.render.model.core;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public abstract class CoreModel extends Model {
    public CoreModel(Function<Identifier, RenderLayer> layerFactory) {
        super(layerFactory);
    }

    public abstract Identifier getTexture();
}
