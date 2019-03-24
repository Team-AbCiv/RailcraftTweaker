package info.tritusk.modpack.crafttweaker.support.railcraft;

import crafttweaker.IAction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = "railcraft_tweaker", name = "RailcraftTweaker", version = "0.0.0", useMetadata = true)
public final class RailcraftTweaker {

    static List<IAction> delayedActions = new ArrayList<>(16);

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (IAction action : delayedActions) {
            action.apply();
        }
        delayedActions.clear();
    }
}
