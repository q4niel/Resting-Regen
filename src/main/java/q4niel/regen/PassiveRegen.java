package q4niel.regen;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PassiveRegen implements ModInitializer {
	public static final String MOD_ID = "regen";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerWorldEvents.LOAD.register((server, world) -> {
			GameRules.BooleanRule rule = world.getGameRules().get(GameRules.NATURAL_REGENERATION);
			rule.set(false, world.getServer());
		});
	}
}