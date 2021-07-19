package top.iseason.blockdelayremover.Uitls;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import top.iseason.blockdelayremover.BlockDelayRemover;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Iseason
 */
public class BlockRemover extends BukkitRunnable {
    private static LinkedHashMap<Block, Long> blockMap = new LinkedHashMap<>();
    long removeTime = BlockDelayRemover.getMyConfig().getInt("BlockRemover.Period") * 1000L;

    public static void addBlock(Block block) {
        blockMap.put(block, System.currentTimeMillis());
    }

    public static void remove(Block block) {

        blockMap.remove(block);
    }

    public static void removeAll() {
        for (Block block : blockMap.keySet()) {
            block.setType(Material.AIR);
        }
        blockMap.clear();
    }

    @Override
    public void run() {
        if (blockMap.isEmpty()) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Iterator<Map.Entry<Block, Long>> iterator = blockMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Block, Long> next = iterator.next();
            Long time = next.getValue();
            //时间不够，退出
            if (currentTimeMillis - time < removeTime) {
                return;
            }
            next.getKey().setType(Material.AIR);
            iterator.remove();
        }


    }
}
