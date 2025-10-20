package net.dulidanci.staffmod.render.model.staff;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import java.util.function.Function;

public abstract class StaffModel extends Model {
    protected StaffModel(Function<Identifier, RenderLayer> layerFactory) {
        super(layerFactory);
    }

    public abstract Identifier getTexture();

    public abstract Vector3f getCoreCubeOriginPoint();
}
