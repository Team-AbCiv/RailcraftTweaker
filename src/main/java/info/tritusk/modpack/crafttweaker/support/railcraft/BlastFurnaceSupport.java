package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IBlastFurnaceCrafter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.BlastFurnace")
@ZenRegister
public final class BlastFurnaceSupport {

    @ZenMethod
    public static void addRecipe(String name, IItemStack output, IIngredient input,
                                 @Optional(valueLong = IBlastFurnaceCrafter.SMELT_TIME) int time,
                                 @Optional int slag) {
        RailcraftTweaker.DELAYED_ACTIONS.add(new IAction() {
            @Override
            public void apply() {
                Crafters.blastFurnace()
                        .newRecipe(CraftTweakerMC.getIngredient(input))
                        .name(name)
                        .time(time)
                        .output(CraftTweakerMC.getItemStack(output))
                        .slagOutput(slag)
                        .register();
            }

            @Override
            public String describe() {
                return null;
            }
        });
    }

    @ZenMethod
    public static void removeRecipe(String name) {
        RailcraftTweaker.DELAYED_REMOVALS.add(new PreciseRemoval(name));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, @Optional IIngredient input) {
        RailcraftTweaker.DELAYED_REMOVALS.add(new FuzzyRemoval(output, input));
    }

    private static final class PreciseRemoval implements IAction {

        private final String recipeName;

        PreciseRemoval(String recipeName) {
            this.recipeName = recipeName;
        }

        @Override
        public void apply() {
            Crafters.blastFurnace().getRecipes().removeIf(r -> this.recipeName.equals(r.getName().toString()));
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
            List<IBlastFurnaceCrafter.IRecipe> recipes = Crafters.blastFurnace().getRecipes();
            for (Iterator<IBlastFurnaceCrafter.IRecipe> itr = recipes.iterator(); itr.hasNext(); ) {
                IBlastFurnaceCrafter.IRecipe recipe = itr.next();
                if (recipe.getOutput().isItemEqual(this.output)) {
                    if (input == null || input == Ingredient.EMPTY || recipe.getInput().equals(this.input)) {
                        itr.remove();
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
