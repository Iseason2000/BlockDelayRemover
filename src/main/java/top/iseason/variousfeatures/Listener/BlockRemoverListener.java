package top.iseason.variousfeatures.Listener;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import top.iseason.variousfeatures.Uitls.BlockRemover;
import top.iseason.variousfeatures.VariousFeatures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Iseason
 */
public class BlockRemoverListener implements Listener {

    private HashSet<String> worldSet;            //启用的世界
    private HashSet<String> playerSet;           //玩家白名单
    private ArrayList<String> blockTypeList;      //方块白名单
    private int delay;

    public BlockRemoverListener(FileConfiguration config) {
        List<String> worldSetList = config.getStringList("BlockRemover.Worlds");
        worldSet = new HashSet<>(worldSetList);
        List<String> playerSetList = config.getStringList("BlockRemover.Players");
        playerSet = new HashSet<>(playerSetList);
        blockTypeList = new ArrayList<>(config.getStringList("BlockRemover.Blocks"));
        delay = config.getInt("BlockRemover.Period") * 20;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!worldSet.contains(player.getWorld().getName())) {
            return;
        }
        if (playerSet.contains(player.getName())) {
            return;
        }
        Block block = event.getBlockPlaced();
        String blockTypeName = block.getType().name();
        for (String name : blockTypeList) {
            if (blockTypeName.contains(name)) {
                return;
            }
        }
        new BlockRemover(block).runTaskLater(VariousFeatures.getInstance(), delay);
    }
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!worldSet.contains(player.getWorld().getName())) {
            return;
        }
        Block block = event.getBlock();
        BlockRemover.remove(block);     //包含判断存在
    }
}
