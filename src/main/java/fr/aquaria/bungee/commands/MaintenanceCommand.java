package fr.aquaria.bungee.commands;

import fr.aquaria.bungee.Maintenance;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class MaintenanceCommand extends Command {

    private Maintenance maintenance;

    //Constructeur
    public MaintenanceCommand(Maintenance maintenance) {
        super("maintenance", "aquaria.maintenance", "mnt");
        this.maintenance = maintenance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {

            sender.sendMessage(TextComponent.fromLegacyText("" +
                    ChatColor.BLUE + "############# §3Maintenance " + ChatColor.BLUE + "#############\n" +
                    ChatColor.AQUA + "/maintenance " + ChatColor.RED + "<on:off> " + ChatColor.WHITE+ " : " + ChatColor.GRAY+ "Active/Désactive la maintenance\n" +
                    ChatColor.AQUA + "/maintenance " + ChatColor.RED + "<add> <joueur> " + ChatColor.WHITE+ ": " + ChatColor.GRAY+ "Ajoute un joueur à la maintenance\n" +
                    ChatColor.AQUA + "/maintenance " + ChatColor.RED + "<remove> <joueur> " + ChatColor.WHITE+ ": " + ChatColor.GRAY+ "Retire un joueur de la maintenance\n" +
                    ChatColor.BLUE + "######################################"));
            return;
        }

        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("on")) {
                this.maintenance.getConfig().set("maintenance", true);
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GREEN + "» La maintenance a été activée."));
                this.maintenance.saveConfig();
                return;
            }

            else if(args[0].equalsIgnoreCase("off")) {
                this.maintenance.getConfig().set("maintenance", false);
                sender.sendMessage(TextComponent.fromLegacyText( ChatColor.RED + "» La maintenance a été désactivée."));
                this.maintenance.saveConfig();
                return;
            }

            else if(args[0].equalsIgnoreCase("add")) {
                if(args.length == 1) {
                    this.maintenance.getProxy().getPluginManager().dispatchCommand(sender, "mnt");
                    return;
                }

                List<String> playersList = this.maintenance.getConfig().getStringList("allowed-players");
                playersList.add(args[1]);

                maintenance.getConfig().set("allowed-players", null);
                maintenance.getConfig().set("allowed-players", playersList);
                maintenance.saveConfig();

                sender.sendMessage(ChatColor.GREEN+ "» " + args[1] + " " + ChatColor.GREEN + "peut se connecter lors d'une maintenance désormais.");
                return;
            }

            else if(args[0].equalsIgnoreCase("remove")) {
                if(args.length == 1) {
                    this.maintenance.getProxy().getPluginManager().dispatchCommand(sender, "mnt");
                    return;
                }

                List<String> playersList = this.maintenance.getConfig().getStringList("allowed-players");
                if(playersList.contains(args[1])) {
                    playersList.remove(args[1]);
                }

                maintenance.getConfig().set("allowed-players", null);
                maintenance.getConfig().set("allowed-players", playersList);
                maintenance.saveConfig();

                sender.sendMessage(ChatColor.RED + "» " + args[1] + " " + ChatColor.RED + "ne peut plus se connecter lors d'une maintenance désormais.");
                return;
            }

        }

    }
}
