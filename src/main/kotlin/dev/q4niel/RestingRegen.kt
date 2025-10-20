package dev.q4niel

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.world.GameRules
import org.slf4j.LoggerFactory

object RestingRegen : ModInitializer {
    val modID_: String = "resting_regen";

    private val _logger_ = LoggerFactory.getLogger(modID_);
    fun print(string: String): Unit = _logger_.info(string);

    val healTickerLimit: Int = 100;
    val horizontalBlockDistance: Double = 20.0;
    val verticalBlockDistance: Double = 10.0;

	override fun onInitialize() {
        ServerWorldEvents.LOAD.register { server, world ->
            world.gameRules.get(GameRules.NATURAL_REGENERATION).set(false, world.server);
        }
    }
}