package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IRollingMachineCrafter;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Objects;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.RollingMachine")
@ZenRegister
public final class RollingMachineSupport {

    @ZenMethod
    public static void addShaped(String name, IItemStack output, IIngredient[][] inputs,
            @Optional(valueLong = IRollingMachineCrafter.DEFAULT_PROCESS_TIME) int time) {
        Crafters.rollingMachine()
                .newRecipe(CraftTweakerMC.getItemStack(output))
                .name("crafttweaker", name)
                .time(time)
                .recipe(new ShapedRecipes(
                        "railcraft-tweaker",
                        3,
                        3,
                        Arrays.stream(inputs)
                                .flatMap(Arrays::stream)
                                .map(CraftTweakerMC::getIngredient)
                                .collect(NonNullList::create, AbstractList::add, AbstractList::addAll),
                        CraftTweakerMC.getItemStack(output)
                ));
    }

    @ZenMethod
    public static void addShapeless(String name, IItemStack output, IIngredient[] inputs,
            @Optional(valueLong = IRollingMachineCrafter.DEFAULT_PROCESS_TIME) int time) {
        Crafters.rollingMachine()
                .newRecipe(CraftTweakerMC.getItemStack(output))
                .name("crafttweaker", name)
                .time(time)
                .recipe(new ShapelessRecipes(
                        "railcraft-tweaker",
                        CraftTweakerMC.getItemStack(output),
                        Arrays.stream(inputs)
                                .map(CraftTweakerMC::getIngredient)
                                .collect(NonNullList::create, AbstractList::add, AbstractList::addAll)
                ));
    }

    @ZenMethod
    public static void remove(String name) {
        Crafters.rollingMachine().getRecipes().removeIf(r -> Objects.toString(r.getRegistryName()).equals(name));
    }
}
