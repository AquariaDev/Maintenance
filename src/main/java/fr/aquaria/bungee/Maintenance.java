package fr.aquaria.bungee;

import fr.aquaria.bungee.commands.MaintenanceCommand;
import fr.aquaria.bungee.listeners.MaintenanceListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Maintenance extends Plugin {

    private Configuration configuration;
    private File file = new File(getDataFolder(), "maintenance.yml");

    @Override
    public void onLoad() {
        getLogger().info("Chargement du plugin...");
    }

    @Override
    public void onEnable() {
        getLogger().info("Le plugin est activé...");
        try {
            makeConfigAlternative();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Chargement de la config
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            getLogger().severe("Le chargement de la configuration a échouée");
            throw new RuntimeException(e);
        }


        // Commandes
        getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand(this));

        // Listeners
        getProxy().getPluginManager().registerListener(this, new MaintenanceListener(this));

    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            getLogger().severe("Echec lors de la tentative de sauvegarde de la configuration");
            throw new RuntimeException(e);
        }
    }

    public Configuration getConfig() {
        return configuration;
    }

    public void makeConfigAlternative() throws IOException {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), "maintenance.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("maintenance.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Désactivation du plugin...");
    }
}
