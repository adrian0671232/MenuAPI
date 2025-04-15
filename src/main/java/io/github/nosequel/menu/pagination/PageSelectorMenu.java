package io.github.nosequel.menu.pagination;

import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class PageSelectorMenu extends Menu {

    private final PaginatedMenu parent;

    public PageSelectorMenu(Player player, PaginatedMenu parent, InventoryHolder holder) {
        super(player, "Select a Page", calculateSize(parent.getMaxPages()), holder);
        this.parent = parent;
    }

    private static int calculateSize(int pages) {
        int rows = 2 + ((pages > 7) ? (int) Math.ceil((pages - 7) / 7.0) : 0);
        return Math.min(rows * 9, 54);
    }

    @Override
    public void tick() {
        this.buttons[0] = new Button(Material.BED)
                .setDisplayName(ChatColor.RED + "Back to Menu")
                .setClickAction(event -> {
                    parent.updateMenu();
                    getPlayer().playSound(getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1, 1);
                    event.setCancelled(true);
                });

        int pages = parent.getMaxPages();
        int page = 1;

        for (int row = 1; page <= pages && row < (getSize() / 9); row++) {
            for (int col = 1; col <= 7 && page <= pages; col++) {
                int slot = (row * 9) + col;

                final int currentPage = page;

                this.buttons[slot] = new Button(Material.BOOK)
                        .setDisplayName(ChatColor.GOLD + "Page " + currentPage)
                        .setClickAction(event -> {
                            parent.setPage(currentPage);
                            parent.updateMenu();
                            getPlayer().playSound(getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1, 1);
                            event.setCancelled(true);
                        });

                page++;
            }
        }
    }
}