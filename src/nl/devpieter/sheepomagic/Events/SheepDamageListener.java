package nl.devpieter.sheepomagic.Events;

import java.util.List;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SheepDamageListener implements Listener {

	private FileConfiguration config;
	private List<DyeColor> colorList;

	public SheepDamageListener(FileConfiguration config, List<DyeColor> colorList) {
		this.config = config;
		this.colorList = colorList;
	}

	@EventHandler
	public void onSheepDamage(EntityDamageEvent event) {

		if (!(event.getEntity() instanceof Sheep))
			return;

		Sheep sheep = (Sheep) event.getEntity();

		if (config.getBoolean("damage.change_color"))
			sheep.setColor(colorList.get(new Random().nextInt(colorList.size())));

		DustOptions dustOptions = new DustOptions(sheep.getColor().getColor(), 1.0F);

		if (config.getBoolean("damage.spawn_particles"))
			if (sheep.isAdult())
				sheep.getWorld().spawnParticle(Particle.REDSTONE, sheep.getLocation().add(0, 1, 0), 50, 0.4, 0.4, 0.4, dustOptions);
			else
				sheep.getWorld().spawnParticle(Particle.REDSTONE, sheep.getLocation().add(0, 0.5, 0), 50, 0.2, 0.2, 0.2, dustOptions);

		if (config.getBoolean("damage.make_baby"))
			sheep.setBaby();
	}
}
