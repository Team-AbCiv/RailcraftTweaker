package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

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
    @ZenMethod
    public static void addFuel(ILiquidStack liquid, int heatValuePerBucket) {
        RailcraftTweaker.DELAYED_ACTIONS.add(new IAction() {
            @Override
            public void apply() {
                FluidFuelManager.addFuel(CraftTweakerMC.getLiquidStack(liquid), heatValuePerBucket);
            }

            @Override
            public String describe() {
                return null;
            }
        });
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
    @ZenMethod
    public static void addFuel(ILiquidDefinition liquidType, int heatValuePerBucket) {
        RailcraftTweaker.DELAYED_ACTIONS.add(new IAction() {
            @Override
            public void apply() {
                FluidFuelManager.addFuel(CraftTweakerMC.getFluid(liquidType), heatValuePerBucket);
            }

            @Override
            public String describe() {
                return null;
            }
        });
    }

    @ZenMethod
    public static void removeFuel(ILiquidStack liquid) {
        removeFuel(liquid.getDefinition());
    }

    @ZenMethod
    public static void removeFuel(ILiquidDefinition liquidType) {
        RailcraftTweaker.DELAYED_REMOVALS.add(new FuelRemoval(liquidType));
    }

    @SuppressWarnings("unchecked")
	private static final class FuelRemoval implements IAction {

        static Map<FluidStack, Integer> fuelRegistryRef;

        static {
            try {
                fuelRegistryRef = (Map<FluidStack, Integer>) FluidFuelManager.class.getDeclaredField("boilerFuel").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fuelRegistryRef = Collections.emptyMap();
            }
        }

        private final Fluid fluidType;

        private FuelRemoval(ILiquidDefinition fluidType) {
            this.fluidType = CraftTweakerMC.getFluid(fluidType);
        }

        @Override
        public void apply() {
            for (Iterator<Map.Entry<FluidStack, Integer>> itr = fuelRegistryRef.entrySet().iterator(); itr.hasNext();) {
                Map.Entry<FluidStack, Integer> entry = itr.next();
                if (entry.getKey().getFluid() == this.fluidType) {
                    itr.remove();
                    return;
                }
            }
        }

        @Override
        public String describe() {
            return String.format(Locale.ENGLISH, "Remove '%s' as valid Railcraft boiler fuel", this.fluidType.getName());
        }
    }
}
