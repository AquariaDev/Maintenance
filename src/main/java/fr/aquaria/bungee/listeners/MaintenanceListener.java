package fr.aquaria.bungee.listeners;

import fr.aquaria.bungee.Maintenance;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class MaintenanceListener implements Listener {

    private Maintenance maintenance;
    public MaintenanceListener(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    @EventHandler
    public void onConnect(PreLoginEvent e) {
        if(maintenance.getConfig().getBoolean("maintenance") == true) {
            if(!maintenance.getConfig().getStringList("allowed-players").contains(e.getConnection().getName())) {
                e.setCancelled(true);
                e.setCancelReason(TextComponent.fromLegacyText("" +
                        ChatColor.RED + "Le serveur est en maintenance !\n" +
                        "\n" +
                        ChatColor.RED + "Vous n'êtes pas autorisés à rentrer sur le serveur étant donné que la maintenance est activée.\n\n" +
                        "Rejoignez notre serveur discord : " + ChatColor.UNDERLINE + "https://discord.gg/kfEQrKq5d9\n" +
                        ChatColor.RED + "Merci de votre compréhension, l'Équipe d'Aquaria."));
            }
        }
    }

}
