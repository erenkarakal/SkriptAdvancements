package me.eren.skriptadvancements.wrapper;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;

/**
 * A wrapper for RootAdvancement that lets you set the tab after creation
 */
public class RootAdvancementWrapper {

    private final String key, backgroundTexture;
    private final AdvancementDisplay display;
    private final int maxProgression;

    public RootAdvancementWrapper(String key, AdvancementDisplay display, String backgroundTexture, int maxProgression) {
        this.key = key;
        this.display = display;
        this.backgroundTexture = backgroundTexture;
        this.maxProgression = maxProgression;
    }

    public RootAdvancement withTab(AdvancementTab tab) {
        return new RootAdvancement(tab, this.key, this.display, this.backgroundTexture, this.maxProgression);
    }

}
