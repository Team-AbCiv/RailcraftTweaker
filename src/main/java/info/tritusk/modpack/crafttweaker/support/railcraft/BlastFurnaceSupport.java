package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IBlastFurnaceCrafter;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
import java.util.List;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.BlastFurnace")
@ZenRegister
public final class BlastFurnaceSupport {

    @ZenMethod
    public static void addRecipe(String name, IItemStack output, IIngredient input,
            @Optional(valueLong = IBlastFurnaceCrafter.SMELT_TIME) int time,
            @Optional int slag) {
        Crafters.blastFurnace()
                .newRecipe(CraftTweakerMC.getIngredient(input))
                .name(name)
                .time(time)
                .output(CraftTweakerMC.getItemStack(output))
                .slagOutput(slag)
                .register();
    }

    @ZenMethod
    public static void removeRecipe(String name) {
        Crafters.blastFurnace().getRecipes().removeIf(r -> name.equals(r.getName().toString()));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, @Optional IIngredient input) {
        CraftTweakerAPI.logWarning("Using BlastFurnace.removeRecipe(IItemStack, @Optional IIngredient) is strongly discouraged. Use the String one whenever possible.");
        List<IBlastFurnaceCrafter.IRecipe> recipes = Crafters.blastFurnace().getRecipes();
        for (Iterator<IBlastFurnaceCrafter.IRecipe> itr = recipes.iterator(); itr.hasNext();) {
            IBlastFurnaceCrafter.IRecipe recipe = itr.next();
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
