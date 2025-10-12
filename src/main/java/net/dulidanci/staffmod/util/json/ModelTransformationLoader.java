package net.dulidanci.staffmod.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.joml.Vector3f;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModelTransformationLoader {
    private static final Map<Identifier, ModelTransformation> CACHE = new ConcurrentHashMap<>();

    public static ModelTransformation getOrLoadModelTransformation(Identifier id) {
        if (CACHE.containsKey(id)) {
            return CACHE.get(id);
        }

        JsonObject json = JsonLoader.loadMinecraftJsonExpecting(id);
//        System.out.println(json);
        JsonObject display = JsonHelper.getObject(json, "display");
//        System.out.println(display);
        ModelTransformation transformation = DisplayDeserializer.loadFromJson(display);

        CACHE.put(id, transformation);

        return transformation;
    }

    private static class DisplayDeserializer {
        public static ModelTransformation loadFromJson(JsonObject displayJson) {
            Transformation[] transformations = new Transformation[ModelTransformationMode.values().length];

            for (ModelTransformationMode mode : ModelTransformationMode.values()) {
                if (displayJson.has(mode.asString())) {
                    JsonObject object = displayJson.getAsJsonObject(mode.asString());

                    Vector3f rotation = parseVec(object, "rotation", new Vector3f());
                    Vector3f translation = parseVec(object, "translation", new Vector3f());
                    Vector3f scale = parseVec(object, "scale", new Vector3f(1.0f, 1.0f, 1.0f));

                    transformations[mode.ordinal()] = new Transformation(rotation, translation, scale);
                } else {
                    transformations[mode.ordinal()] = Transformation.IDENTITY;
                }
            }

            return new ModelTransformation(
                    transformations[ModelTransformationMode.THIRD_PERSON_LEFT_HAND.ordinal()],
                    transformations[ModelTransformationMode.THIRD_PERSON_RIGHT_HAND.ordinal()],
                    transformations[ModelTransformationMode.FIRST_PERSON_LEFT_HAND.ordinal()],
                    transformations[ModelTransformationMode.FIRST_PERSON_RIGHT_HAND.ordinal()],
                    transformations[ModelTransformationMode.HEAD.ordinal()],
                    transformations[ModelTransformationMode.GUI.ordinal()],
                    transformations[ModelTransformationMode.GROUND.ordinal()],
                    transformations[ModelTransformationMode.FIXED.ordinal()]
            );
        }

        private static Vector3f parseVec(JsonObject object, String key, Vector3f def) {
            if (!object.has(key)) return def;
            JsonArray array = object.getAsJsonArray(key);
            return new Vector3f(
                    array.get(0).getAsFloat(),
                    array.get(1).getAsFloat(),
                    array.get(2).getAsFloat()
            );
        }

    }
}
