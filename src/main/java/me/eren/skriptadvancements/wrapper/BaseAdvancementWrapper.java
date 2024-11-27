package me.eren.skriptadvancements.wrapper;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;

/**
 * A wrapper for BaseAdvancement that takes the parent as a String
 */
public class BaseAdvancementWrapper {

    private final String key, parent;
    private final AdvancementDisplay display;
    private final int maxProgression;

    public BaseAdvancementWrapper(String key, AdvancementDisplay display, String parent, int maxProgression) {
        this.key = key;
        this.display = display;
        this.parent = parent;
        this.maxProgression = maxProgression;
    }

    public String getKey() {
        return key;
    }

    public String getParent() {
        return parent;
    }

    public BaseAdvancement withParent(Advancement parent) {
        return new BaseAdvancement(this.key, this.display, parent, this.maxProgression);
    }

}
