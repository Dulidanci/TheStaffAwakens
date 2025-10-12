package net.dulidanci.staffmod.datagen;

import com.google.gson.JsonObject;
import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.cores.CoreTypes;
import net.dulidanci.staffmod.util.json.JsonLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CoreTextureProvider implements DataProvider {
    private final Map<Block, String> inputTextures = new HashMap<>(Map.ofEntries(
            Map.entry(Blocks.ANVIL, "assets/" + StaffMod.MOD_ID + "/textures/block/cores/anvil.png"),
            Map.entry(Blocks.BEEHIVE, "assets/minecraft/textures/block/beehive_front_honey.png"),
            Map.entry(Blocks.BELL, "assets/" + StaffMod.MOD_ID + "/textures/block/cores/bell.png"),
            Map.entry(Blocks.BONE_BLOCK, "assets/minecraft/textures/block/bone_block_side.png"),
            Map.entry(Blocks.END_STONE, "assets/minecraft/textures/block/end_stone.png"),
            Map.entry(Blocks.GLOWSTONE, "assets/minecraft/textures/block/glowstone.png"),
            Map.entry(Blocks.LAPIS_BLOCK, "assets/minecraft/textures/block/lapis_block.png"),
            Map.entry(Blocks.MAGMA_BLOCK, "assets/minecraft/textures/block/magma.png"),
            Map.entry(Blocks.NETHERRACK, "assets/minecraft/textures/block/netherrack.png"),
            Map.entry(Blocks.ACACIA_PLANKS, "assets/minecraft/textures/block/acacia_planks.png"),
            Map.entry(Blocks.BAMBOO_PLANKS, "assets/minecraft/textures/block/bamboo_planks.png"),
            Map.entry(Blocks.BIRCH_PLANKS, "assets/minecraft/textures/block/birch_planks.png"),
            Map.entry(Blocks.CHERRY_PLANKS, "assets/minecraft/textures/block/cherry_planks.png"),
            Map.entry(Blocks.CRIMSON_PLANKS, "assets/minecraft/textures/block/crimson_planks.png"),
            Map.entry(Blocks.DARK_OAK_PLANKS, "assets/minecraft/textures/block/dark_oak_planks.png"),
            Map.entry(Blocks.JUNGLE_PLANKS, "assets/minecraft/textures/block/jungle_planks.png"),
            Map.entry(Blocks.MANGROVE_PLANKS, "assets/minecraft/textures/block/mangrove_planks.png"),
            Map.entry(Blocks.OAK_PLANKS, "assets/minecraft/textures/block/oak_planks.png"),
            Map.entry(Blocks.SPRUCE_PLANKS, "assets/minecraft/textures/block/spruce_planks.png"),
            Map.entry(Blocks.WARPED_PLANKS, "assets/minecraft/textures/block/warped_planks.png"),
            Map.entry(Blocks.TARGET, "assets/minecraft/textures/block/target_side.png"),
            Map.entry(Blocks.TNT, "assets/minecraft/textures/block/tnt_side.png")
    ));
    public final String outputDirectory = "src/main/resources/assets/" + StaffMod.MOD_ID + "/textures/item/cores/";

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {

            for (CoreTypes coreTemplate : CoreTypes.values()) {
                Block coreBlock = coreTemplate.getBlock();
                Identifier coreId = Registries.BLOCK.getId(coreBlock);

                if (!inputTextures.containsKey(coreBlock)) {
                    StaffMod.LOGGER.error("No texture is defined to block {}!", coreBlock);
                    continue;
                }

                String path = inputTextures.get(coreBlock);

                try (InputStream stream = CoreTextureProvider.class.getClassLoader().getResourceAsStream(path)) {
                    if (stream == null) {
                        StaffMod.LOGGER.error("Couldn't load file {}, when generating core texture!", path);
                        continue;
                    }

                    BufferedImage src = ImageIO.read(stream).getSubimage(0, 0, 16, 16);

                    BufferedImage result = new BufferedImage(src.getWidth() * 3 + 64, src.getHeight() * 3 + 64, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D tripled = result.createGraphics();

                    for (int y = 0; y < src.getHeight(); y++) {
                        for (int x = 0; x < src.getWidth(); x++) {
                            for (int j = 0; j < 3; j++) {
                                for (int i = 0; i < 3; i++) {
                                    result.setRGB(3 * x + i + 32, 3 * y + j + 32, src.getRGB(x, y));
                                }
                            }
                        }
                    }

                    tripled.dispose();

                    Path projectRoot = Paths.get("").toAbsolutePath().getParent();

                    Path outputPath = projectRoot.resolve(outputDirectory + coreId.getPath() + ".png");
                    Files.createDirectories(outputPath.getParent());

                    ImageIO.write(result, "png", outputPath.toFile());
                } catch (IOException e) {
                    StaffMod.LOGGER.error("Couldn't generate core texture from file {}", path);
                    throw new RuntimeException(e);
                }
            }
        }, Util.getMainWorkerExecutor());
    }

    @Override
    public String getName() {
        return "CoreTextureProvider";
    }
}
