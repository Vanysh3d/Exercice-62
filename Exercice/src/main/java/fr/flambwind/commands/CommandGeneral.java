package fr.flambwind.commands;

import fr.flambwind.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandGeneral implements CommandExecutor {

    private final Main main;

    public CommandGeneral(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        // Définir les valeurs directement dans la classe
        String twitterUrl = "https://twitter.com/FlambWind";
        String discordUrl = "https://discord.com/invite/yy5NcJ4";

        //instanciation du joueur SI joueur effectue la commande
        if (sender instanceof Player) {
            Player player = (Player) sender;


            //commande lien vers reseaux
            if (command.getName().equalsIgnoreCase("reseaux")) {

                player.sendMessage("§e===================================");
                player.sendMessage("Retrouvez-nous sur Twitter : " + "§b" + twitterUrl);
                player.sendMessage("");
                player.sendMessage("Rejoignez notre serveur Discord : " + "§b" + discordUrl);
                player.sendMessage("§e===================================");
            }

            if (command.getName().equalsIgnoreCase("spawn")){

                Location location = main.getConfig().getLocation("spawn");

                if (location != null){

                    player.teleport(location);

                    player.sendMessage("téléportation au spawn");
                }else {
                    player.sendMessage("spawnpoint non défini");
                }
            }

            if(command.getName().equalsIgnoreCase("warp")){

                final File file = new File(main.getDataFolder(), "warps.yml");
                final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                String warpName = args[0];
                ConfigurationSection configurationSection = configuration.getConfigurationSection(warpName);

                if(args.length == 0){
                    player.sendMessage("Spécifiez le nom d'un warp");

                    return false;
                }

                if(args.length == 1){
                    if (configuration.contains(warpName)) {
                        final World world = Bukkit.getWorld(configurationSection.getString("world"));

                        final double x = configurationSection.getDouble("x");
                        final double y = configurationSection.getDouble("y");
                        final double z = configurationSection.getDouble("z");
                        final float yaw = (float) configurationSection.getDouble("yaw");
                        final float pitch = (float) configurationSection.getDouble("pitch");

                        final Location warpTeleportation = new Location(world,x,y,z, yaw, pitch);

                        player.teleport(warpTeleportation);
                        player.sendMessage("téléportation au warp réussi");
                    } else {
                    player.sendMessage(ChatColor.YELLOW + "Ce warp n'existe pas ou n'est pas encore accessible");
                }
                }
            }
        }

        return true;
    }
}
