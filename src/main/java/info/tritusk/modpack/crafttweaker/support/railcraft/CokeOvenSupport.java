package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.ICokeOvenCrafter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
        RailcraftTweaker.delayedActions.add(new PreciseRemoval(name));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, @Optional IIngredient input) {
        RailcraftTweaker.delayedActions.add(new FuzzyRemoval(output, input));
    }

    private static final class PreciseRemoval implements IAction {

        private final String recipeName;

        PreciseRemoval(String recipeName) {
            this.recipeName = recipeName;
        }

        @Override
        public void apply() {
            Crafters.cokeOven().getRecipes().removeIf(r -> this.recipeName.equals(r.getName().toString()));
        }

        @Override
        public String describe() {
            return String.format(Locale.ENGLISH, "Remove Coke Oven recipe '%s'", this.recipeName);
        }
    }

    private static final class FuzzyRemoval implements IAction {

        private final ItemStack output;
        private final Ingredient input;

        FuzzyRemoval(IItemStack output, IIngredient input) {
            this.output = CraftTweakerMC.getItemStack(output);
            this.input = CraftTweakerMC.getIngredient(input);
        }

        @Override
        public void apply() {
            CraftTweakerAPI.logWarning("Using CokeOven.removeRecipe(IItemStack, @Optional IIngredient) is strongly discouraged. Use the String one whenever possible.");
            List<ICokeOvenCrafter.IRecipe> recipes = Crafters.cokeOven().getRecipes();
            for (Iterator<ICokeOvenCrafter.IRecipe> itr = recipes.iterator(); itr.hasNext();) {
                ICokeOvenCrafter.IRecipe recipe = itr.next();
                if (recipe.getOutput().isItemEqual(this.output)) {
                    if (input == null || input == Ingredient.EMPTY) {
                        itr.remove();
                    } else {
                        if (recipe.getInput().equals(this.input)) {
                            itr.remove();
                        }
                    }
                }
            }
        }

        @Override
        public String describe() {
            return null;
        }
    }
}
