package io.github.nosequel.menu.pagination;

import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.filling.FillingType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

@Getter
@Setter
public abstract class PaginatedMenu extends Menu {

    private NavigationPosition navigationPosition = NavigationPosition.TOP;

    private Button previousPageButton = new Button(Material.MELON)
            .setDisplayName(ChatColor.GREEN + "Previous Page");

    private Button nextPageButton = new Button(Material.MELON)
            .setDisplayName(ChatColor.GREEN + "Next Page");

    private int page = 1;
    private int maxPages;

    /**
     * Constructor to make a new menu object
     *
     * @param player the player to create the menu for
     * @param title  the title to display at the top of the inventory
     * @param size   the size of the inventory
     */
    public PaginatedMenu(Player player, String title, int size, InventoryHolder holder) {
        this(player, title, size, 16, holder);
    }

    /**
     * Constructor to make a new menu object
     *
     * @param player the player to create the menu for
     * @param title  the title to display at the top of the inventory
     * @param size   the size of the inventory
     * @param maxPages the maximum amount of pages
     */
    public PaginatedMenu(Player player, String title, int size, int maxPages, InventoryHolder holder) {
        super(player, title, size, holder);
        this.maxPages = maxPages;
        this.buttons = new Button[size * maxPages];
    }

    /**
     * Navigate to the next menu page
     */
    public void navigateNext() {
        this.page += 1;
        this.updateMenu();
    }

    /**
     * Navigate to the previous menu page
     */
    public void navigatePrevious() {
        this.page = Math.max(1, this.page - 1);
        this.updateMenu();
    }

    /**
     * Update the menu for the player
     */
    @Override
    public void updateMenu() {
        this.updateMenu(this.getButtonsInRange());
        this.updateMenu(this.getButtonsInRange());
    }

    /**
     * Handle clicking on a button
     *
     * @param event the event called
     */
    @Override
    public void click(InventoryClickEvent event) {
        try {
            final Button[] visibleButtons = this.getButtonsInRange();
            final Button clickedButton = visibleButtons[event.getSlot()];

            final Button[] navBar = this.getNavigationBar();

            boolean isNavButton = false;
            for (Button button : navBar) {
                if (button != null && button.equals(clickedButton)) {
                    isNavButton = true;
                    getPlayer().playSound(getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1, 1);
                    break;
                }
            }

            if (event.isRightClick() && isNavButton) {
                new PageSelectorMenu((Player) event.getWhoClicked(), this, this.getHolder()).updateMenu();
                event.setCancelled(true);
                return;
            }

            if (clickedButton != null && clickedButton.equals(previousPageButton) && this.page == 1) {
                event.setCancelled(true);
                return;
            }

            if (clickedButton != null && clickedButton.equals(nextPageButton) && this.maxPages == 1) {
                event.setCancelled(true);
                return;
            }

            if (clickedButton == null) {
                event.setCancelled(true);
                return;
            }

            if (clickedButton.getClickAction() != null) {
                clickedButton.getClickAction().accept(event);
            }

        } catch (IndexOutOfBoundsException e) {
            event.setCancelled(true);
            e.printStackTrace();
        }
    }

    /**
     * Get the filler buttons for the menu
     *
     * @return the filler buttons
     */
    @Override
    public Button[] getFillerButtons() {
        final Button[] buttons = new Button[this.getSize()];

        for (FillingType filler : this.getFillers()) {
            final Button[] fillers = filler.fillMenu(this);

            for (int i = 0; i < fillers.length; i++) {
                if (fillers[i] != null) {
                    for (int page = 0; page < this.maxPages; page++) {
                        this.buttons[(page * this.getSize()) + i] = fillers[i];
                    }
                }
            }
        }

        return buttons;
    }

    /**
     * Get the list of buttons in the
     * range of the current page.
     *
     * @return the list of buttons
     */
    public Button[] getButtonsInRange() {
        return this.navigationPosition.getButtonsInRange(this.getButtons(), this);
    }

    /**
     * Get the list of buttons for the navigation bar.
     * <p>
     * These buttons will be displayed independent
     * of the current page of the menu.
     *
     * @return the list of buttons
     */
    public Button[] getNavigationBar() {
        return this.navigationPosition.getNavigationButtons(this).clone();
    }
}