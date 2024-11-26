package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;

import java.util.List;

public class SecAdvancementTab extends Section {

    public static AdvancementTabBuilder lastCreatedTab;
    private Expression<String> name;

    static {
        Skript.registerSection(SecAdvancementTab.class, "(create|register) [an|a [new]] [custom] advancement tab (named|with id) %string%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
        name = (Expression<String>) expressions[0];
        loadCode(sectionNode);
        return true;
    }

    @Override
    protected TriggerItem walk(Event event) {
        String name = this.name.getSingle(event);
        AdvancementTab tab = SkriptAdvancements.getAdvancementAPI().createAdvancementTab(name);
        lastCreatedTab = new AdvancementTabBuilder(tab);
        return walk(event, true);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString(Event event, boolean debug) {
        return "register an advancement tab named " + this.name;
    }
}
