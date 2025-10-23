package net.dulidanci.thestaffawakens.util.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.*;
import java.util.Optional;

public class JsonLoader {
    public static JsonObject loadMinecraftJsonExpecting(Identifier path) {
        try {
            Optional<Resource> res = MinecraftClient.getInstance().getResourceManager().getResource(path);

            if (res.isEmpty()) {
                throw new RuntimeException("JSON resource not found: " + path);
            }

            try (InputStreamReader reader = new InputStreamReader(res.get().getInputStream())) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON: " + path, e);
        }
    }
}
