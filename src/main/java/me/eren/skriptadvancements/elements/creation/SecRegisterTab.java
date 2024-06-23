package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;

import java.util.List;

public class SecRegisterTab extends EffectSection {

    public static AdvancementTabBuilder lastCreatedTab;
    private Expression<String> tabNameExpr;

    static {
        Skript.registerSection(SecRegisterTab.class, "(create|register) [an] advancement tab named %string%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed,
                        SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
        tabNameExpr = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    protected TriggerItem walk(Event event) {
        String tabName = tabNameExpr.getSingle(event);

        if (tabName == null) return null;
        if (SkriptAdvancements.getAdvancementAPI().isAdvancementTabRegistered(tabName)) return null;

        AdvancementTab tab = SkriptAdvancements.getAdvancementAPI().createAdvancementTab(tabName);
        lastCreatedTab = new AdvancementTabBuilder(tab);
        return getNext();
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "register an advancement tab named " + tabNameExpr;
    }
}
