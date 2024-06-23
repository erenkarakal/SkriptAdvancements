package me.eren.skriptadvancements;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;

import java.util.HashSet;
import java.util.Set;

public class AdvancementTabBuilder {

    public final AdvancementTab tab;
    public RootAdvancement rootAdvancement;
    public final Set<BaseAdvancement> advancements = new HashSet<>();

    public AdvancementTabBuilder(AdvancementTab tab) {
        this.tab = tab;
    }

    public void build() {
        tab.registerAdvancements(rootAdvancement, advancements);
    }

}
