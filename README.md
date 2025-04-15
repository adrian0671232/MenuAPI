[![](https://jitpack.io/v/nadir-gg/MenuAPI.svg)](https://jitpack.io/#nadir-gg/MenuAPI)

# This is a fork of NoSequel's MenuAPI
If you need better help on creating menus then visit: https://github.com/NoSequel/MenuAPI

Changes:
- [x] All menus now require an InventoryHolder
- [x] You can no longer go to a page that does not exist
- [x] Right clicking a nav button allows you to go to a page selector
- [x] Navigation buttons have sounds now

#Main.java
```java
public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        new MenuHandler(this);

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void openInventory(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) return;
        if (event.getItem().getType() != Material.STICK) return;

        new TestMenu(player).updateMenu();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
```

#TestMenu.java
```java
public class TestMenu extends PaginatedMenu {
    public TestMenu(Player player) {
        super(player, "Bugha", 36, 1, new TestHolder());
    }

    @Override
    public void tick() {
        for(int i = 0; i < 27; i++) {
            this.buttons[i] = new Button(Material.ENDER_PEARL)
                    .setDisplayName("&aSlot: " + i + ChatColor.GRAY + " (" + getPage() + "/" + getMaxPages() + ")")
                    .setClickAction(event -> event.setCancelled(true));
        }
    }
}
```

#TestHolder.java
```java
public class TestHolder implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null;
    }
}
```
