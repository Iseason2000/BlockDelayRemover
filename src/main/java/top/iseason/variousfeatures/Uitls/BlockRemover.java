package top.iseason.variousfeatures.Uitls;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class BlockRemover extends BukkitRunnable {
    private static HashMap<Block, BlockRemover> blockSet = new HashMap<>();
    private Block block;

    public BlockRemover(Block block) {
        this.block = block;
        blockSet.put(this.block, this);
    }
    public static void remove(Block block) {
        BlockRemover blockRemover = blockSet.get(block);
        if (blockRemover == null) {
            return;
        }
        blockRemover.cancel();
        blockSet.remove(block);
    }
    public static void removeAll(){
        for (BlockRemover next : blockSet.values()) {
            next.run();
            next.cancel();
        }
    }

    @Override
    public void run() {
        if (block != null) {
            block.setType(Material.AIR);
        }
    }
}
