package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IBlastFurnaceCrafter;
import mods.railcraft.api.crafting.ICokeOvenCrafter;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
import java.util.List;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.CokeOven")
@ZenRegister
public final class CokeOvenSupport {

    @ZenMethod
    public static void addRecipe(String name, IItemStack output, IIngredient input,
            @Optional(valueLong = ICokeOvenCrafter.DEFAULT_COOK_TIME) int time,
            @Optional ILiquidStack outputFluid) {
        Crafters.cokeOven().newRecipe(CraftTweakerMC.getIngredient(input))
                .name(name)
                .output(CraftTweakerMC.getItemStack(output))
                .time(time)
                // TODO (3TUSK): The JEI compatibility of Coke Oven actually expects fluid output being preset.
                //   should we guard against null here?
                .fluid(CraftTweakerMC.getLiquidStack(outputFluid))
                .register();
    }

    @ZenMethod
    public static void removeRecipe(String name) {
        Crafters.cokeOven().getRecipes().removeIf(r -> name.equals(r.getName().toString()));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, @Optional IIngredient input) {
        CraftTweakerAPI.logWarning("Using CokeOven.removeRecipe(IItemStack, @Optional IIngredient) is strongly discouraged. Use the String one whenever possible.");
        List<ICokeOvenCrafter.IRecipe> recipes = Crafters.cokeOven().getRecipes();
        for (Iterator<ICokeOvenCrafter.IRecipe> itr = recipes.iterator(); itr.hasNext();) {
            ICokeOvenCrafter.IRecipe recipe = itr.next();
            if (recipe.getOutput().isItemEqual(CraftTweakerMC.getItemStack(output))) {
                if (input == null) {
                    itr.remove();
                } else {
                    if (recipe.getInput().equals(CraftTweakerMC.getIngredient(input))) {
                        itr.remove();
                    }
                }
            }
        }
    }
}
