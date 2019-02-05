package info.tritusk.modpack.crafttweaker.support.railcraft;

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
}
