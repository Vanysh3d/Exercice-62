package fr.flambwind.main;

import fr.flambwind.commands.CommandAdmin;
import fr.flambwind.commands.CommandGeneral;
import fr.flambwind.listeners.SpawnListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private String twitterUrl;
    private String discordUrl;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("plugin enable");

        //config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //initialisation des listeners
        getServer().getPluginManager().registerEvents(new SpawnListeners(this), this);

        //setup des commandes
        getCommand("annonce").setExecutor(new CommandAdmin(this));
        getCommand("setspawn").setExecutor(new CommandAdmin(this));
        getCommand("setwarp").setExecutor(new CommandAdmin(this));
        getCommand("reseaux").setExecutor(new CommandGeneral(this));
        getCommand("spawn").setExecutor(new CommandGeneral(this));
        getCommand("warp").setExecutor(new CommandGeneral(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("plugin disable");
    }

}
