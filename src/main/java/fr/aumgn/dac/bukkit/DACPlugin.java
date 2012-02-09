package fr.aumgn.dac.bukkit;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.command.DACCommand;
import fr.aumgn.dac.exception.WorldEditNotLoaded;
import fr.aumgn.dac.game.mode.DACGameModes;
import fr.aumgn.dac.game.mode.default_.DefaultGameMode;
import fr.aumgn.dac.game.mode.training.TrainingGameMode;

public class DACPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		PluginManager pm = Bukkit.getPluginManager();

		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		if (!new File(getDataFolder(), "messages.yml").exists()) {
			saveResource("messages.yml", false);
		}

		Plugin worldEdit = pm.getPlugin("WorldEdit");
		if (!(worldEdit instanceof WorldEditPlugin)) {
			throw new WorldEditNotLoaded();
		}

		DACCommand dacCommand = new DACCommand();
		Bukkit.getPluginCommand("dac").setExecutor(dacCommand);

		DACListener dacPlayerListener = new DACListener();
		pm.registerEvents(dacPlayerListener, this);

		DAC.init(this, (WorldEditPlugin)worldEdit);
		
		DACGameModes.register(DefaultGameMode.class);
		DACGameModes.register(TrainingGameMode.class);

		getLogger().info(getDescription().getName() + " loaded.");
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		DAC.getArenas().dump();
		getLogger().info(getDescription().getName() + " unloaded.");
	}

}