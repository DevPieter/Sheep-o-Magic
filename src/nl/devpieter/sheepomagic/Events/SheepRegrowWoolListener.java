package nl.devpieter.sheepomagic.Events;

import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SheepRegrowWoolListener implements Listener {

	private FileConfiguration config;

	public SheepRegrowWoolListener(FileConfiguration config) {
		this.config = config;
	}

	@EventHandler
	public void onSheepRegrowWool(SheepRegrowWoolEvent event) {

		Sheep sheep = event.getEntity();

		if (config.getBoolean("regrow_wool.always_make_adult"))
			sheep.setAdult();

		if (config.getBoolean("regrow_wool.give_regeneration.use"))
			sheep.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, config.getInt("regrow_wool.give_regeneration.give") * 20, config.getInt("regrow_wool.give_regeneration.amplifier") + 1));

		if (config.getBoolean("regrow_wool.spawn_particles"))
			sheep.getWorld().spawnParticle(Particle.HEART, sheep.getLocation().add(0, 1, 0), 10, 0.3, 0.5, 0.3);
	}

}
