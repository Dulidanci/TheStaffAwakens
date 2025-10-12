package net.dulidanci.staffmod.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class JsonBuilder {
    private final JsonObject json = new JsonObject();

    public static JsonBuilder create() {
        return new JsonBuilder();
    }

    public JsonBuilder add(String key, String value) {
        json.addProperty(key, value);
        return this;
    }

    public JsonBuilder addObject(String key, JsonObject object) {
        json.add(key, object);
        return this;
    }

    public JsonBuilder addDisplayFromFile(JsonObject object) {
        System.out.println(object.toString());
        json.add("display", JsonHelper.getObject(json, "display"));
        return this;
    }

    public JsonObject build() {
        return json;
    }
}
