package io.github.nosequel.menu;

import io.github.nosequel.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public enum MenuType {

    HOPPER {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.HOPPER, menu.getTitle());
        }
    },

    INVENTORY {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), menu.getSize(), menu.getTitle());
        }
    },

    FURNACE {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.FURNACE, menu.getTitle());
        }
    },

    BREWING_STAND {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.BREWING, menu.getTitle());
        }
    },

    ENCHANTING {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.ENCHANTING, menu.getTitle());
        }
    },

    BEACON {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.BEACON, menu.getTitle());
        }
    },

    CRAFTING {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.CRAFTING, menu.getTitle());
        }
    },

    DISPENSER {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.DISPENSER, menu.getTitle());
        }
    },

    DROPPER {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.DROPPER, menu.getTitle());
        }
    },

    MERCHANT {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.MERCHANT, menu.getTitle());
        }
    },

    ANVIL {
        @Override
        public Inventory createInventory(Menu menu) {
            return Bukkit.createInventory(menu.getHolder(), InventoryType.ANVIL, menu.getTitle());
        }
    };

    /**
     * Create a new inventory with the menu type
     *
     * @param menu the menu to create it for
     * @return the inventory
     */
    public abstract Inventory createInventory(Menu menu);

}
