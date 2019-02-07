package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;

@ModOnly("railcraft")
@ZenClass("mods.railcraft.FluidFuel")
@ZenRegister
public final class FluidFuelSupport {

    /**
     * Add new liquid fuel as valid fuel for Railcraft. For reference, creosote
     * has value of 4800, while BuildCraft Fuel has value of 48000.
     *
     * @param liquid The liquid type, size doesn't matter
     * @param heatValuePerBucket Desired heat value per bucket (1000 mB)
     *
     * @see FluidFuelManager#addFuel(FluidStack, int)
     */
    public static void addFuel(ILiquidStack liquid, int heatValuePerBucket) {
        FluidFuelManager.addFuel(CraftTweakerMC.getLiquidStack(liquid), heatValuePerBucket);
    }

    /**
     * Add new liquid fuel as valid fuel for Railcraft. For reference, creosote
     * has value of 4800, while BuildCraft Fuel has value of 48000.
     *
     * @param liquidType The liquid type
     * @param heatValuePerBucket Desired heat value per bucket (1000 mB)
     *
     * @see FluidFuelManager#addFuel(Fluid, int)
     */
    public static void addFuel(ILiquidDefinition liquidType, int heatValuePerBucket) {
        FluidFuelManager.addFuel(CraftTweakerMC.getFluid(liquidType), heatValuePerBucket);
    }

    // TODO removeFuel(ILiquidStack), requires reflection, dunno what's the deal
    // TODO removeFuel(ILiquidDefinition), requires reflection, dunno what's the deal
}
