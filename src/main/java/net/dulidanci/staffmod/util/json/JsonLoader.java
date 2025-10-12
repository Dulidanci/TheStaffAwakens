package net.dulidanci.staffmod.util.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;
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

    public static JsonObject loadJson(String name) throws IOException {
        return new JsonLoader().getJson(name);
    }

    public static JsonObject loadJson(Path path) throws IOException {
        return new JsonLoader().getJson(path.toString());
    }

    private JsonObject getJson(String name) throws IOException {
        InputStream stream = getClass().getClassLoader()
                .getResourceAsStream(name);
        if (stream == null)
            throw new IOException("Resource not found in Minecraft jar!");
        return JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
    }

    @Nullable
    public static JsonObject loadMinecraftJsonOptionally(Identifier path){
        try {
            Optional<Resource> res = MinecraftClient.getInstance().getResourceManager().getResource(path);

            if (res.isEmpty()) {
                return null;
            }

            try (InputStreamReader reader = new InputStreamReader(res.get().getInputStream())) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON: " + path, e);
        }
    }
}
