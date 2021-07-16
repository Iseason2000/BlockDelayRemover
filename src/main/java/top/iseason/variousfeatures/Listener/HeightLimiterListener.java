package top.iseason.variousfeatures.Listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.HashSet;
import java.util.List;


/**
 * @author Iseason
 */
public class HeightLimiterListener implements Listener {
    private int maxHeight;
    private int minHeight;
    private HashSet<String> worldSet;  //启用的世界

    public HeightLimiterListener(FileConfiguration config) {
        maxHeight = config.getInt("HeightLimiter.MaxHeight");
        minHeight = config.getInt("HeightLimiter.MinHeight");
        List<String> worldSetList = config.getStringList("BlockRemover.Worlds");
        worldSet = new HashSet<>(worldSetList);
    }

    private boolean checkHeight(Location loc, int modify) {
        int blockY = loc.getBlockY();
        if (blockY > maxHeight - modify) {
            return false;
        }
        return blockY >= minHeight + modify;
    }

    private boolean checkWorld(World world) {
        return worldSet.contains(world.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!checkWorld(player.getWorld())) {
            return;
        }
        if (!checkHeight(event.getBlockPlaced().getLocation(), 0)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStructureGrowEvent(StructureGrowEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!checkWorld(event.getWorld())) {
            return;
        }
        for (BlockState block : event.getBlocks()) {
            if (!checkHeight(block.getLocation(), 0)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockMultiPlaceEvent(BlockMultiPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!checkWorld(player.getWorld())) {
            return;
        }
        List<BlockState> replacedBlockStates = event.getReplacedBlockStates();
        for (BlockState replacedBlockState : replacedBlockStates) {
            if (!checkHeight(replacedBlockState.getLocation(), 0)) {
                event.setCancelled(true);
                return;
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        if (event.isCancelled()) {
            return;
        }
        List<Block> blocks = event.getBlocks();
        if (!checkHeight(event.getBlock().getLocation(), 1)) {
            event.setCancelled(true);
            return;
        }
        if (blocks.isEmpty()) {
            return;
        }
        if (!checkWorld(blocks.get(0).getWorld())) {
            return;
        }
        for (Block block : blocks) {
            Location location = block.getLocation();
            if (!checkHeight(location, 1)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBlockFormEvent(EntityBlockFormEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Location location = event.getBlock().getLocation();
        World world = location.getWorld();
        if (world == null) {
            return;
        }
        if (!checkWorld(world)) {
            return;
        }
        if (!checkHeight(location, 0)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockGrowEvent(BlockGrowEvent event) {
        if (event.isCancelled()) {
            return;
        }
        BlockState newState = event.getNewState();
        if (!checkWorld(newState.getWorld())) {
            return;
        }
        if (!checkHeight(newState.getLocation(), 1)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockFormEvent(BlockFormEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        if (!checkWorld(block.getWorld())) {
            return;
        }
        if (!checkHeight(block.getLocation(), 0)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockSpreadEvent(BlockSpreadEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        if (!checkWorld(block.getWorld())) {
            return;
        }
        if (!checkHeight(block.getLocation(), 0)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockFromToEvent(BlockFromToEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getToBlock();
        if (!checkWorld(block.getWorld())) {
            return;
        }
        if (!checkHeight(block.getLocation(), 0)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        if (!checkWorld(block.getWorld())) {
            return;
        }
        if (!block.getType().name().contains("DISPENSER")) {
            return;
        }
        if (!event.getItem().getType().name().contains("SHULKER_BOX")) {
            return;
        }
        if (!checkHeight(block.getLocation(), 1)) {
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event){
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        if (!checkWorld(block.getWorld())) {
            return;
        }
        if (!checkHeight(block.getLocation(), 1)) {
            event.setCancelled(true);
        }
    }
}
