package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IRockCrusherCrafter;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Locale;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.RockCrusher")
@ZenRegister
public final class RockCrusherSupport {

    @ZenMethod
    public static void addRecipe(String name, WeightedItemStack[] outputs, IIngredient input) {
        RailcraftTweaker.DELAYED_ACTIONS.add(new IAction() {
            @Override
            public void apply() {
                IRockCrusherCrafter.IRockCrusherRecipeBuilder recipe = Crafters.rockCrusher()
                        .makeRecipe(CraftTweakerMC.getIngredient(input))
                        .name(name);
                for (int i = 0; i < Math.min(outputs.length, 9); i++) {
                    if (outputs[i] != null) {
                        WeightedItemStack output = outputs[i];
                        recipe = recipe.addOutput(CraftTweakerMC.getItemStack(output.getStack()), output.getChance());
                    }
                }
                recipe.register();
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
    public static void removeRecipe(IItemStack input) {
        RailcraftTweaker.DELAYED_REMOVALS.add(new FuzzyRemoval(CraftTweakerMC.getItemStack(input)));
    }

    private static final class PreciseRemoval implements IAction {

        private final String recipeName;

        PreciseRemoval(String recipeName) {
            this.recipeName = recipeName;
        }

        @Override
        public void apply() {
            Crafters.rockCrusher().getRecipes().removeIf(r -> this.recipeName.equals(r.getName().toString()));
        }

        @Override
        public String describe() {
            return String.format(Locale.ENGLISH, "Remove Rock Crusher recipe '%s'", this.recipeName);
        }
    }

    private static final class FuzzyRemoval implements IAction {

        private final ItemStack input;

        FuzzyRemoval(ItemStack input) {
            this.input = input;
        }

        @Override
        public void apply() {
            Crafters.rockCrusher()
                    .getRecipes()
                    .removeIf(recipe -> recipe.getInput().apply(this.input));
        }

        @Override
        public String describe() {
            return null;
        }
    }
}
