package net.dulidanci.staffmod.datagen;

import com.google.gson.JsonObject;

public class JsonBuilder {
    private final JsonObject json = new JsonObject();

    public static JsonBuilder create() {
        return new JsonBuilder();
    }

    public JsonBuilder add(String key, String value) {
        json.addProperty(key, value);
        return this;
    }

    public JsonObject build() {
        return json;
    }
}
