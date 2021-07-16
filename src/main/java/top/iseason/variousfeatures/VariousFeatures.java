package top.iseason.variousfeatures;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.iseason.variousfeatures.Listener.BlockRemoverListener;
import top.iseason.variousfeatures.Listener.HeightLimiterListener;
import top.iseason.variousfeatures.Uitls.BlockRemover;

/**
 * @author Iseason
 */
public class VariousFeatures extends JavaPlugin {
    private static VariousFeatures plugin;
    private static FileConfiguration config;

    public static FileConfiguration getMyConfig() {
        return config;
    }

    public static VariousFeatures getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        setupConfig();
        getServer().getPluginManager().registerEvents(new BlockRemoverListener(config), this);
        getServer().getPluginManager().registerEvents(new HeightLimiterListener(config), this);
    }

    @Override
    public void onDisable() {
        BlockRemover.removeAll();
    }
    private void setupConfig() {
        saveDefaultConfig();
        config = plugin.getConfig();
    }
}
