package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.ICokeOvenCrafter;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("railcraft")
@ZenClass("mos.railcraft.CokeOven")
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
                .fluid(CraftTweakerMC.getLiquidStack(outputFluid))
                .register();
    }
}
