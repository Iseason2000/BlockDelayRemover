package top.iseason.blockdelayremover.Listener;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import top.iseason.blockdelayremover.Uitls.BlockRemover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Iseason
 */
public class BlockRemoverListener implements Listener {

    //启用的世界
    private final HashSet<String> worldSet;
    //玩家白名单
    private final HashSet<String> playerSet;
    //方块白名单
    private final ArrayList<String> blockTypeList;

    public BlockRemoverListener(FileConfiguration config) {
        List<String> worldSetList = config.getStringList("BlockRemover.Worlds");
        worldSet = new HashSet<>(worldSetList);
        List<String> playerSetList = config.getStringList("BlockRemover.Players");
        playerSet = new HashSet<>(playerSetList);
        blockTypeList = new ArrayList<>(config.getStringList("BlockRemover.Blocks"));
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
        BlockRemover.addBlock(block);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!worldSet.contains(player.getWorld().getName())) {
            return;
        }
        Block block = event.getBlock();
        //包含判断存在
        BlockRemover.remove(block);
    }
}
