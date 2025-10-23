package net.dulidanci.thestaffawakens.datagen;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.item.cores.CoreTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;


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
            Map.entry(Blocks.ANVIL, "assets/" + TheStaffAwakens.MOD_ID + "/textures/block/cores/anvil.png"),
            Map.entry(Blocks.BEEHIVE, "assets/minecraft/textures/block/beehive_front_honey.png"),
            Map.entry(Blocks.BELL, "assets/" + TheStaffAwakens.MOD_ID + "/textures/block/cores/bell.png"),
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
    public final String outputDirectory = "src/main/resources/assets/" + TheStaffAwakens.MOD_ID + "/textures/item/cores/";

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return CompletableFuture.runAsync(() -> {

            for (CoreTypes coreTemplate : CoreTypes.values()) {
                if (coreTemplate == CoreTypes.AIR) continue;

                Block coreBlock = coreTemplate.getBlock();
                Identifier coreId = Registries.BLOCK.getId(coreBlock);

                if (!inputTextures.containsKey(coreBlock)) {
                    TheStaffAwakens.LOGGER.error("No texture is defined to block {}!", coreBlock);
                    continue;
                }

                String path = inputTextures.get(coreBlock);

                try (InputStream stream = CoreTextureProvider.class.getClassLoader().getResourceAsStream(path)) {
                    if (stream == null) {
                        TheStaffAwakens.LOGGER.error("Couldn't load file {}, when generating core texture!", path);
                        continue;
                    }

                    BufferedImage src = ImageIO.read(stream).getSubimage(0, 0, 16, 16);

                    drawCentered(src, coreId, path);
                    drawShifted(src, coreId, path);

                } catch (IOException e) {
                    TheStaffAwakens.LOGGER.error("Couldn't generate core texture from file {}", path);
                    throw new RuntimeException(e);
                }
            }
        }, Util.getMainWorkerExecutor());
    }

    private void drawShifted(BufferedImage src, Identifier coreId, String path) {
        try {
            BufferedImage result = new BufferedImage(28, 28, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = result.createGraphics();

            graphics.drawImage(src, 4, 6, null);

            graphics.dispose();

            Path projectRoot = Paths.get("").toAbsolutePath().getParent();

            Path outputPath = projectRoot.resolve(outputDirectory + coreId.getPath() + "_shifted.png");
            Files.createDirectories(outputPath.getParent());

            ImageIO.write(result, "png", outputPath.toFile());
        } catch (IOException e) {
            TheStaffAwakens.LOGGER.error("Couldn't generate shifted core texture from file {}", path);
            throw new RuntimeException(e);
        }
    }

    private void drawCentered(BufferedImage src, Identifier coreId, String path) {
        try {
            BufferedImage result = new BufferedImage(28, 28, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = result.createGraphics();

            graphics.drawImage(src, 6, 6, null);

            graphics.dispose();

            Path projectRoot = Paths.get("").toAbsolutePath().getParent();

            Path outputPath = projectRoot.resolve(outputDirectory + coreId.getPath() + ".png");
            Files.createDirectories(outputPath.getParent());

            ImageIO.write(result, "png", outputPath.toFile());
        } catch (IOException e) {
            TheStaffAwakens.LOGGER.error("Couldn't generate centered core texture from file {}", path);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "CoreTextureProvider";
    }
}
