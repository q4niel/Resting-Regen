package dev.q4niel.resting_regen

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object RestingRegen: ModInitializer {
	val MOD_ID: String = "resting_regen";

	fun print(value: String): Unit = _logger.info(value);
    private val _logger: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {}
}