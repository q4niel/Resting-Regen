package dev.q4niel

import com.moandjiezana.toml.Toml
import java.io.File

data class ModConfigFile (
    val healTickerLimit: Long = 100,
    val horizontalBlockDistance: Double = 20.0,
    val verticalBlockDistance: Double = 10.0
)

object ModConfig {
    var _config: ModConfigFile = ModConfigFile();
    val _cfgFile_: File = File("config/${RestingRegen.modID_}.toml");

    fun get(): ModConfigFile = _config;

    fun init() {
        if (!_cfgFile_.exists()) return;

        val toml = Toml().read(_cfgFile_);

        _config = ModConfigFile (
            toml.getLong("heal_ticker_limit", _config.healTickerLimit),
            toml.getDouble("horizontal_block_distance", _config.horizontalBlockDistance),
            toml.getDouble("vertical_block_distance", _config.verticalBlockDistance)
        );
    }
}