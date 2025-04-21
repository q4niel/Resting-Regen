package dev.q4niel.resting_regen

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.GameRules
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object RestingRegen: ModInitializer {
	val MOD_ID: String = "resting_regen";

	fun print(value: String): Unit = _logger.info(value);
    private val _logger: Logger = LoggerFactory.getLogger(MOD_ID)

	fun serverExec(runnable: Runnable): Unit? = _server?.execute(runnable);
	private var _server: MinecraftServer? = null;

	fun getHealTickerLimit(): Int = _healTickerLimit;
	private val _healTickerLimit: Int = 100;

	fun getHorizontalBlockDistance(): Double = _horizontalBlockDistance;
	private val _horizontalBlockDistance: Double = 20.0;

	fun getVerticalBlockDistance(): Double = _verticalBlockDistance;
	private val _verticalBlockDistance: Double = 10.0;

	override fun onInitialize() {
		ServerWorldEvents.LOAD.register {
			server: MinecraftServer,
			world: ServerWorld
			->
			world
				.gameRules
				.get(GameRules.NATURAL_REGENERATION)
				.set(false, world.server)
			;
		};

		ServerLifecycleEvents.SERVER_STARTED.register {
			server: MinecraftServer
			->
			_server = server;
		};
	}
}