package ltd.rymc.form.quickshop.listener;

import ltd.rymc.form.quickshop.QuickShopForm;
import ltd.rymc.form.quickshop.event.ShopClickEvent;
import ltd.rymc.form.quickshop.forms.QuickShopMainForm;
import ltd.rymc.form.quickshop.forms.QuickShopSettingForm;
import ltd.rymc.form.quickshop.shop.QuickShop;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.geysermc.floodgate.api.FloodgateApi;

public class ShopListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();

            // Check if FloodgateApi is available and player is valid
            if (!isFloodgatePlayer(player)) return;

            Block block = event.getClickedBlock();
            if (block == null) return;

            // Check if ShopHandler is available
            if (QuickShopForm.getShopHandler() == null) return;

            QuickShop shop = QuickShopForm.getShopHandler().getQuickShop(block);
            if (shop == null) return;

            Action action = event.getAction();

            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (player.isSneaking()) {
                    shop.openPreview(player);
                }
                return;
            }

            if (!action.equals(Action.RIGHT_CLICK_BLOCK)) return;

            Material blockType = block.getType();

            if (blockType.equals(Material.CHEST) || blockType.equals(Material.TRAPPED_CHEST)) return;

            // Additional null check for shop owner
            if (shop.getOwner() == null) return;

            if (player.isOp() || shop.getOwner().equals(player.getUniqueId())) {
                new QuickShopSettingForm(player, null, shop).send();
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerAnimationEvent(PlayerAnimationEvent event) {
        try {
            Player player = event.getPlayer();

            if (!isFloodgatePlayer(player)) return;

            GameMode gameMode = player.getGameMode();
            if (!gameMode.equals(GameMode.ADVENTURE)) return;

            Block block = player.getTargetBlock(null, 5);

            if (QuickShopForm.getShopHandler() == null) return;

            QuickShop shop = QuickShopForm.getShopHandler().getQuickShop(block);
            if (shop == null) return;

            if (!player.isSneaking()) return;

            Material blockType = block.getType();

            if (blockType.equals(Material.CHEST) || blockType.equals(Material.TRAPPED_CHEST)) return;

            shop.openPreview(player);
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void ShopClickEvent(ShopClickEvent event) {
        try {
            if (event == null) return;

            QuickShop shop = event.getShop();
            if (shop == null) return;

            Player player = event.getPlayer();
            if (player == null) return;

            if (shop.isEmpty()) return;

            new QuickShopMainForm(player, null, shop).send();
        } catch (Exception ignored) {
        }
    }

    // Helper method to safely check FloodgateApi
    private boolean isFloodgatePlayer(Player player) {
        try {
            FloodgateApi api = FloodgateApi.getInstance();
            if (api == null) return false;
            return api.isFloodgatePlayer(player.getUniqueId());
        } catch (Exception e) {
            // If FloodgateApi fails, assume it's not a floodgate player
            return false;
        }
    }
}