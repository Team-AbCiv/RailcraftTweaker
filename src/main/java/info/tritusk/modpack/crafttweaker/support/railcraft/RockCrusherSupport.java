package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IRockCrusherCrafter;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.RockCrusher")
@ZenRegister
public final class RockCrusherSupport {

    @ZenMethod
    public static void addRecipe(String name, WeightedItemStack[] outputs, IIngredient input) {
        IRockCrusherCrafter.IRockCrusherRecipeBuilder recipe = Crafters.rockCrusher()
                .makeRecipe(CraftTweakerMC.getIngredient(input))
                .name(name);
        for (int i = 0; i < Math.min(outputs.length, 9); i++) {
            if (outputs[i] != null) {
                WeightedItemStack actual = outputs[i];
                recipe = recipe.addOutput(CraftTweakerMC.getItemStack(actual.getStack()), actual.getChance());
            }
        }
        recipe.register();
    }
}
