package top.iseason.blockdelayremover;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import top.iseason.blockdelayremover.Listener.BlockRemoverListener;
import top.iseason.blockdelayremover.Uitls.BlockRemover;

import java.io.File;

/**
 * @author Iseason
 */
public class BlockDelayRemover extends JavaPlugin {
    private static BlockDelayRemover plugin;
    private static FileConfiguration config;
    private static int period;
    private static BukkitTask remover;

    public static FileConfiguration getMyConfig() {
        return config;
    }

    public static BlockDelayRemover getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        setupConfig();
        getServer().getPluginManager().registerEvents(new BlockRemoverListener(config), this);
        remover = new BlockRemover().runTaskTimer(this, 0L, period);
    }

    @Override
    public void onDisable() {
        BlockRemover.removeAll();
        remover.cancel();
    }

    private void setupConfig() {
        File file = new File(getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        config = plugin.getConfig();
        period = config.getInt("BlockRemover.CheckPeriod");
    }
}
