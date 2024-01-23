package fr.flambwind.commands;

import fr.flambwind.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CommandAdmin implements CommandExecutor {

    public CommandAdmin(Main main) {
        this.main = main;
    }

    private final Main main;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        //commande annonce (Broadcast)
        if(command.getName().equalsIgnoreCase("annonce")){

            // commande sans arguments
            if(args.length ==0){
                sender.sendMessage("La commande est : /annonce <message>");
            }

            // commande avec plus d'un argument
            if(args.length >=1 ){

                StringBuilder bc = new StringBuilder();

                for(String part : args){
                    bc.append(part + " ");
                }

                Bukkit.broadcastMessage("§c[ANNONCE] " + "§r" + bc.toString());
            }
        }

        //sauvegarde de la localisation du joueur afin d'y appliquer le spawnpoint
        if(command.getName().equalsIgnoreCase("setspawn")){
            //instanciation du joueur SI joueur effectue la commande
            if(sender instanceof Player){
                Player player =(Player)sender;

                Location location = player.getLocation();

                main.getConfig().set("spawn", location);

                main.saveConfig();

                player.sendMessage("Spawn location set successfully");
            }else {
                System.out.println("you need to be on the server to execute this command");
            }
        }

        //sauvegarde et création des warps avec obligatoirement 1 argument
        if(command.getName().equalsIgnoreCase("setwarp")){
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    Location location = player.getLocation();

                    final File file = new File(main.getDataFolder(), "warps.yml");
                    final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                    String warpName = args[0];

                    configuration.set(warpName + ".world", location.getWorld().getName());
                    configuration.set(warpName + ".x", location.getX());
                    configuration.set(warpName + ".y", location.getY());
                    configuration.set(warpName + ".z", location.getZ());
                    configuration.set(warpName + ".yaw", location.getYaw());
                    configuration.set(warpName + ".pitch", location.getPitch());

                    try {
                        configuration.save(file);
                        player.sendMessage("Warp sauvegardé correctement");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage("Pas plus d'un argument");
                }
            }
        }


        return true;
    }
}
