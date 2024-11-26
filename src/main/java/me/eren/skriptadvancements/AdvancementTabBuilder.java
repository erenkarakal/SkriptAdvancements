package me.eren.skriptadvancements;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;

import java.util.HashMap;
import java.util.HashSet;

public class AdvancementTabBuilder {

    private final AdvancementTab tab;
    private RootAdvancement rootAdvancement;
    private final HashMap<String, BaseAdvancement> advancements = new HashMap<>();

    public AdvancementTabBuilder(AdvancementTab tab) {
        this.tab = tab;
    }

    /**
     * @return The advancement tab
     */
    public AdvancementTab getTab() {
        return tab;
    }

    /**
     * @return The root advancement
     */
    public RootAdvancement getRoot() {
        return rootAdvancement;
    }

    /**
     * Sets the root advancement
     * @param root The advancement to set it to
     */
    public void setRoot(RootAdvancement root) {
        this.rootAdvancement = root;
    }

    /**
     * Adds an advancement to the builder, overwriting any existing advancement
     * @param id The ID of the advancement
     * @param advancement The advancement to register
     */
    public void addAdvancement(String id, BaseAdvancement advancement) {
        this.advancements.put(id, advancement);
    }

    /**
     * @param id The ID of the advancement
     * @return The advancement if it exists, otherwise the root advancement
     */
    public Advancement getAdvancement(String id) {
        if (advancements.containsKey(id)) return advancements.get(id);
        return rootAdvancement;
    }

    /**
     * Registers the advancement tab, overwriting any existing tab
     */
    public void build() {
//        UltimateAdvancementAPI api = SkriptAdvancements.getAdvancementAPI();
//        String rootNamespace = rootAdvancement.getKey().getNamespace();
//        if (api.isAdvancementTabRegistered(rootNamespace)) {
//            // temporary workaround
//            AdvancementTab oldTab = api.getAdvancementTab(rootNamespace);
//            if (!oldTab.isInitialised()) {
//                oldTab.registerAdvancements(new RootAdvancement(oldTab, rootNamespace,
//                        new AdvancementDisplay(Material.STONE, "a", AdvancementFrameType.GOAL, false, false, 5, 5), AdvancementUtils.getTexture(Material.STONE)),
//                        Collections.emptySet());
//            }
//            api.unregisterAdvancementTab(rootNamespace);
//        }
        tab.registerAdvancements(rootAdvancement, new HashSet<>(advancements.values()));
    }

}
