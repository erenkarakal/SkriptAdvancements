package me.eren.skriptadvancements.wrapper;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.NamespacedKey;

public class CustomAdvancementWrapper implements AdvancementWrapper {

    private final Advancement advancement;

    public CustomAdvancementWrapper(Advancement advancement) {
        this.advancement = advancement;
    }

    @Override
    public NamespacedKey getKey() {
        return advancement.getKey().toNamespacedKey();
    }
}
