package me.eren.skriptadvancements;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import me.eren.skriptadvancements.elements.creation.SecAdvancementTab;
import me.eren.skriptadvancements.wrapper.BaseAdvancementWrapper;
import me.eren.skriptadvancements.wrapper.RootAdvancementWrapper;

import java.util.HashMap;
import java.util.HashSet;

public class AdvancementTabBuilder {

    private final String tabName;
    private RootAdvancementWrapper rootAdvancement;
    private final HashMap<String, BaseAdvancementWrapper> advancements = new HashMap<>();

    public AdvancementTabBuilder(String tabName) {
        this.tabName = tabName;
    }

    /**
     * @return The name of the advancement tab
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * Sets the root advancement
     * @param root The advancement to set it to
     */
    public void setRoot(RootAdvancementWrapper root) {
        this.rootAdvancement = root;
    }

    /**
     * Adds an advancement to the builder, overwriting any existing advancement
     * @param id The ID of the advancement
     * @param advancement The advancement to register
     */
    public void addAdvancement(String id, BaseAdvancementWrapper advancement) {
        advancements.put(id, advancement);
    }

    /**
     * Registers the advancement tab, overwriting any existing tab
     */
    public void build() {
        UltimateAdvancementAPI api = SkriptAdvancements.getAdvancementAPI();
        if (api.isAdvancementTabRegistered(tabName) && api.getAdvancementTab(tabName).isInitialised()) {
            api.unregisterAdvancementTab(tabName);
        }
        AdvancementTab tab = api.createAdvancementTab(tabName);
        RootAdvancement root = rootAdvancement.withTab(tab);
        HashMap<String, BaseAdvancement> createdAdvancements = new HashMap<>();
        for (BaseAdvancementWrapper advancement : advancements.values()) {
            Advancement parent = createdAdvancements.containsKey(advancement.getParent())
                    ? createdAdvancements.get(advancement.getParent()) : root;
            createdAdvancements.put(advancement.getKey(), advancement.withParent(parent));
        }
        tab.registerAdvancements(root, new HashSet<>(createdAdvancements.values()));
        SecAdvancementTab.lastCreatedTab = null;
    }

}
