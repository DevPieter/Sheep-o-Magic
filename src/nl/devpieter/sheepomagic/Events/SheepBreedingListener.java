package nl.devpieter.sheepomagic.Events;

import java.util.List;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class SheepBreedingListener implements Listener {

	private FileConfiguration config;
	private List<DyeColor> colorList;

	public SheepBreedingListener(FileConfiguration config, List<DyeColor> colorList) {
		this.config = config;
		this.colorList = colorList;
	}

	@EventHandler
	public void onSheepBreeding(EntityBreedEvent event) {

		if (!(event.getEntity() instanceof Sheep))
			return;

		if (!(event.getFather() instanceof Sheep))
			return;

		if (!(event.getMother() instanceof Sheep))
			return;

		Sheep sheep = (Sheep) event.getEntity();
		Sheep fatherSheep = (Sheep) event.getFather();
		Sheep motherSheep = (Sheep) event.getMother();

		if (config.getBoolean("breeding.change_babys_color"))
			sheep.setColor(colorList.get(new Random().nextInt(colorList.size())));

		if (config.getBoolean("breeding.spawn_firework")) {
			Firework firework = (Firework) sheep.getWorld().spawnEntity(sheep.getLocation().add(0, 1, 0), EntityType.FIREWORK);
			FireworkMeta fireworkMeta = firework.getFireworkMeta();

			fireworkMeta.setPower(1);
			fireworkMeta.addEffects(FireworkEffect.builder().withColor(motherSheep.getColor().getColor(), fatherSheep.getColor().getColor()).withFade(sheep.getColor().getColor()).flicker(true).with(Type.BALL_LARGE).build());
			firework.setFireworkMeta(fireworkMeta);
		}
	}
}
