package me.eren.skriptadvancements.wrapper;

import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;

public class BukkitAdvancementWrapper implements AdvancementWrapper {

    private final Advancement advancement;

    public BukkitAdvancementWrapper(Advancement advancement) {
        this.advancement = advancement;
    }

    @Override
    public NamespacedKey getKey() {
        return advancement.getKey();
    }
}
