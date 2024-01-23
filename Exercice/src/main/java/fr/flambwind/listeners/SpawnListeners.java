package fr.flambwind.listeners;

import fr.flambwind.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListeners implements Listener {

    private final Main main;

    public SpawnListeners(Main main) {
        this.main = main;
    }

    //La première fois qu'un joueur rejoint le serveur il est téléporté au spawnpoint
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if(!player.hasPlayedBefore()) {

            Location location = main.getConfig().getLocation("spawn");

            if (location != null) {

                player.teleport(location);

            }
        }
    }

    //si un joueur meurt il est retéléporté au spawnpoint
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){

        Location location = main.getConfig().getLocation("spawn");

        if (location != null) {

            event.setRespawnLocation(location);

        }

    }
}
