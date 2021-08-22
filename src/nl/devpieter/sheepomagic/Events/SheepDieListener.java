package nl.devpieter.sheepomagic.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SheepDieListener implements Listener {

	private FileConfiguration config;

	public SheepDieListener(FileConfiguration config) {
		this.config = config;
	}

	@EventHandler
	public void onSheepDie(EntityDeathEvent event) {

		if (!(event.getEntity() instanceof Sheep))
			return;

		Sheep sheep = (Sheep) event.getEntity();

		if (!config.getBoolean("die.summon_lightning"))
			return;

		if (config.getBoolean("die.only_if_raining")) {
			if (sheep.getWorld().hasStorm())
				strikeLightning(sheep, config);
		} else
			strikeLightning(sheep, config);

	}

	private static void strikeLightning(Sheep sheep, FileConfiguration config) {
		if (config.getBoolean("die.real_lightning"))
			sheep.getWorld().strikeLightning(sheep.getLocation());
		else
			sheep.getWorld().strikeLightningEffect(sheep.getLocation());
	}
}
