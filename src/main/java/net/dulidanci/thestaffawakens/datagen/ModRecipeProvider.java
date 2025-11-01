package net.dulidanci.thestaffawakens.datagen;

import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.STAFF_WORKBENCH, 1)
                .pattern("ILI")
                .pattern("SCS")
                .pattern("W W")
                .input('I', Items.IRON_INGOT)
                .input('L', Items.LAPIS_LAZULI)
                .input('S', Items.SMOOTH_STONE)
                .input('C', Items.CRYING_OBSIDIAN)
                .input('W', Ingredient.fromTag(ItemTags.LOGS))
                .criterion(hasItem(Items.CRYING_OBSIDIAN), conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(recipeExporter, new Identifier(getRecipeName(ModBlocks.STAFF_WORKBENCH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PERFECTED_STAFF, 1)
                .pattern("  C")
                .pattern(" C ")
                .pattern("C  ")
                .input('C', Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(recipeExporter, new Identifier(getRecipeName(ModItems.PERFECTED_STAFF)));

    }
}
