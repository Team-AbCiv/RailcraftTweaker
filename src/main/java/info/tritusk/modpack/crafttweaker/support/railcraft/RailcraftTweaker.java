package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.IAction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.ArrayList;

@Mod(modid = "railcraft_tweaker", name = "RailcraftTweaker", version = "0.1.0", useMetadata = true)
public final class RailcraftTweaker {

    static final ArrayList<IAction> DELAYED_REMOVALS = new ArrayList<>(), DELAYED_ACTIONS = new ArrayList<>();

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (IAction action : DELAYED_REMOVALS) {
            action.apply();
        }
        for (IAction action : DELAYED_ACTIONS) {
            action.apply();
        }
        DELAYED_REMOVALS.clear();
        DELAYED_REMOVALS.trimToSize();
        DELAYED_ACTIONS.clear();
        DELAYED_ACTIONS.trimToSize();
    }
}
