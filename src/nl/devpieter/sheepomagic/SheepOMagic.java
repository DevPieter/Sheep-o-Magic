package nl.devpieter.sheepomagic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import nl.devpieter.sheepomagic.Events.SheepBreedingListener;
import nl.devpieter.sheepomagic.Events.SheepDamageListener;
import nl.devpieter.sheepomagic.Events.SheepDieListener;
import nl.devpieter.sheepomagic.Events.SheepRegrowWoolListener;

public class SheepOMagic extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();

		FileConfiguration configuration = getConfig();

		List<DyeColor> colorList = new ArrayList<DyeColor>();
		for (String color : getConfig().getStringList("colors"))
			if (org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils.isValidEnum(DyeColor.class, color.toUpperCase()))
				colorList.add(DyeColor.valueOf(color.toUpperCase()));

		if (colorList.isEmpty()) {
			getServer().getConsoleSender().sendMessage("[Sheep o' Magic]" + ChatColor.RED + " No valid dye colors were found in the config file!");
			getServer().getConsoleSender().sendMessage("[Sheep o' Magic]" + ChatColor.DARK_AQUA + " Supported colors: BLACK, BLUE, BROWN, CYAN, GRAY, GREEN, LIGHT_BLUE, LIGHT_GRAY, LIME, MAGENTA, ORANGE, PINK, PURPLE, RED, WHITE, YELLOW");
			getServer().getConsoleSender().sendMessage("[Sheep o' Magic]" + ChatColor.DARK_AQUA + " Using all the dye colors.");
			colorList = Arrays.asList(DyeColor.values());
		}

		getServer().getPluginManager().registerEvents(new SheepDamageListener(configuration, colorList), this);
		getServer().getPluginManager().registerEvents(new SheepRegrowWoolListener(configuration), this);
		getServer().getPluginManager().registerEvents(new SheepBreedingListener(configuration, colorList), this);
		getServer().getPluginManager().registerEvents(new SheepDieListener(configuration), this);

	}
}
