package net.glowstone.inventory;


import net.glowstone.EventFactory;
import net.glowstone.entity.GlowPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class EnchantmentManager {
    private final GlowPlayer player;
    private final int[] enchantments;
    private final Map<Enchantment, Integer> e = null;
    private InventoryView enchantingView = null;
    private GlowEnchantingInventory inventory;
    private Location location;

    private final Random random = new Random();

    public EnchantmentManager(GlowPlayer player) {
        this.player = player;
        this.enchantments = new int[3];
    }

    public void onWindowOpened(int viewId, GlowInventoryView view) {
        if (view.getType() != InventoryType.ENCHANTING) {
            throw new IllegalArgumentException("EnchantmentManager#onWindowOpened called with view=" + view + ", expected ENCHANTING.");
        }
        this.enchantingView = view;
        this.inventory = (GlowEnchantingInventory) view.getTopInventory();
        this.location = inventory.getLocation();
    }

    public void onWindowClosed() {
        this.enchantingView = null;
        this.inventory = null;
        this.location = null;
    }

    public boolean isPlayerEnchanting() {
        return enchantingView != null;
    }

    public void onPlayerEnchant(int clicked) {
        if (!isPlayerEnchanting()) throw new IllegalStateException("Cannot enchant, when nor inventory is open.");

        ItemStack item = inventory.getItem();

        //Checking for malicious clients
        //TODO: better responsing for malicious clients
        Validate.isTrue(clicked >= 0 && clicked <= 3, "Malicious client");
        if (!canEnchant(item)) {
            //TODO
            return;
        }

        int level = enchantments[clicked];

        if (player.getGameMode() != GameMode.CREATIVE) {
            //Checking level and resource
            if (player.getLevel() < level || inventory.getResource() == null || inventory.getResource().getAmount() < clicked) {
                return; //malicious client
            }
        }

        Map<Enchantment, Integer> enchants = calculateEnchants(item, level);

        EnchantItemEvent event = EventFactory.callEvent(new EnchantItemEvent(player, enchantingView, location.getBlock(), item.clone(), level, enchants, clicked));
        if (event.isCancelled() || (player.getGameMode() != GameMode.CREATIVE && event.getExpLevelCost() > player.getLevel()))
            return;

        boolean isBook = item.getType() == Material.BOOK;

        if (isBook)
            item.setType(Material.ENCHANTED_BOOK);

        for (Map.Entry<Enchantment, Integer> enchantment : event.getEnchantsToAdd().entrySet()) {
            try {
                if (isBook) {
                    //TODO:
                    continue;
                } else {
                    item.addUnsafeEnchantment(enchantment.getKey(), enchantment.getValue());
                }
            } catch (IllegalArgumentException e) {
                //ignore, since plugins are allowed to add enchantments that can't be applied
            }
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            player.setLevel(player.getLevel() - event.getExpLevelCost()); //TODO
            inventory.getResource().setAmount(inventory.getResource().getAmount() - clicked);
        }
    }

    private Map<Enchantment, Integer> calculateEnchants(ItemStack item, int level) {
        int i = getI(item);
        if (i <= 0) return null;
        i /= 2;
        i = 1 + random.nextInt((i >> 1) + 1) + random.nextInt((i >> 1) + 1);
        int j = i + level;
        float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
        int k = (int) (j * (1.0F + f) + 0.5F);
        if (k < 1) {
            k = 1;
        }

        Map<Enchantment, Integer> map = getPossibleEnchants(item, k);
        if (map.isEmpty()) return null;

        Map.Entry<Enchantment, Integer> choosen = null; //TODO: choose wheighted
        if (choosen == null) return null;

        Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(choosen.getKey(), choosen.getValue());

        while (random.nextInt(50) <= k) {
            Iterator<Enchantment> it = map.keySet().iterator();
            while (it.hasNext()) {
                Enchantment e = it.next();
                int n = 1;
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    if (entry.getKey().conflictsWith(e)) {
                        n = 0;
                        break;
                    }
                }
                if (n == 0)
                    it.remove();
            }
            if (!map.isEmpty()) {
                enchants.put(null, null); //TODO add whiehgted from map
            }
            k >>= 1;
        }

        return enchants;
    }

    private static int getI(ItemStack item) {
        switch (item.getType()) {
            case BOOK:
            case BOW:
            case FISHING_ROD:
                return 1;

            case WOOD_PICKAXE:
            case WOOD_AXE:
                //case ...
                return 15;
            case STONE_AXE:
                return 5;
            case IRON_AXE:
                return 14;
            case DIAMOND_AXE:
                return 10;
            case GOLD_AXE:
                return 22;

            case CHAINMAIL_CHESTPLATE:
                return 12;
            case DIAMOND_CHESTPLATE:
                return 10;
            case IRON_CHESTPLATE:
                return 9;
            case GOLD_CHESTPLATE:
                return 25;
            case LEATHER_CHESTPLATE:
                return 15;


            default:
                return 0;
        }
    }

    private static Map<Enchantment, Integer> getPossibleEnchants(ItemStack item, int i) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();

        for (Enchantment enchantment : Enchantment.values()) {
            if (enchantment.canEnchantItem(item) || item.getType() == Material.BOOK) {
                for (int level = enchantment.getStartLevel(); level <= enchantment.getMaxLevel(); level++) {
                    if ((i >= enchantment.a(level)) && (i <= enchantment.b(level))) {
                        enchantments.put(enchantment, level);
                    }
                }
            }
        }

        return enchantments;
    }

    public void updatePossibilities() {
        ItemStack item = inventory.getItem();
        ItemStack resource = inventory.getResource();

        if (item == null || resource == null || resource.getType() != Material.INK_SACK || resource.getDurability() != 4 || !canEnchant(item)) {
            clearView();
        } else {
            calculateNewValues();
        }
    }

    private void calculateNewValues() {
        int realBookshelfs = getBookshelfCount(location);
        int countBookshelf = Math.min(15, realBookshelfs);

        for (int i = 0; i < enchantments.length; i++) {
            if (!canEnchant(inventory.getItem())) {
                enchantments[i] = 0;
                continue;
            }

            int j = random.nextInt(8) + 1 + (countBookshelf >> 1) + random.nextInt(countBookshelf + 1);
            if (i == 0) {
                enchantments[i] = Math.max(j / 3, 1);
            } else if (i == 1) {
                enchantments[i] = j * 2 / 3 + 1;
            } else {
                enchantments[i] = Math.max(j, countBookshelf * 2);
            }
        }

        PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(player, enchantingView, location.getBlock(), inventory.getItem(), enchantments, realBookshelfs);
        event.setCancelled(false); //TODO
        EventFactory.callEvent(event);
        if (event.isCancelled()) {
            for (int i = 0; i < enchantments.length; i++)
                enchantments[i] = 0;
        }

        update();
    }

    private void clearView() {
        player.setWindowProperty(InventoryView.Property.ENCHANT_BUTTON1, 0);
        player.setWindowProperty(InventoryView.Property.ENCHANT_BUTTON2, 0);
        player.setWindowProperty(InventoryView.Property.ENCHANT_BUTTON3, 0);
    }

    private void update() {
        player.setWindowProperty(InventoryView.Property.ENCHANT_BUTTON1, enchantments[0]);
        player.setWindowProperty(InventoryView.Property.ENCHANT_BUTTON2, enchantments[1]);
        player.setWindowProperty(InventoryView.Property.ENCHANT_BUTTON3, enchantments[2]);
    }

    private boolean canEnchant(ItemStack item) {
        Enchantment[] enchantments = Enchantment.values();
        for (Enchantment enchantment : enchantments) {
            if (enchantment.canEnchantItem(item))
                return true;
        }
        return false;
    }

    private static int getBookshelfCount(Location location) {
        int count = 0;

        for (int y = 0; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (z == 0 && x == 0) continue;
                    Location loc = location.clone();
                    loc.add(x, 0, z);
                    if (loc.getBlock().isEmpty()) {
                        loc.add(0, 1, 0);
                        if (loc.getBlock().isEmpty()) {
                            setLocation(loc, location);

                            //diagonal and straight
                            loc.add(x * 2, y, z * 2);
                            if (loc.getBlock().getType() == Material.BOOKSHELF) {
                                count++;
                            }

                            if (x != 0 && z != 0) {
                                //one block diagonal and one straight
                                setLocation(loc, location);
                                loc.add(x * 2, y, z);
                                if (loc.getBlock().getType() == Material.BOOKSHELF) {
                                    ++count;
                                }

                                setLocation(loc, location);
                                loc.add(x, y, z * 2);
                                if (loc.getBlock().getType() == Material.BOOKSHELF) {
                                    ++count;
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    private static void setLocation(Location modify, Location source) {
        modify.setX(source.getX());
        modify.setY(source.getY());
        modify.setZ(source.getZ());
    }
}
