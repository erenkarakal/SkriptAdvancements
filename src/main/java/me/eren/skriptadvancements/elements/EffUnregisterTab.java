package me.eren.skriptadvancements.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;

public class EffUnregisterTab extends Effect {

    private Expression<String> tabs;

    static {
        Skript.registerEffect(EffUnregisterTab.class, "unregister [advancement] tab[s] %strings%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        tabs = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        for (String tab : tabs.getAll(event)) {
            AdvancementTab advTab = SkriptAdvancements.getAdvancementAPI().getAdvancementTab(tab);
            if (advTab == null)
                continue;
            SkriptAdvancements.getAdvancementAPI().unregisterAdvancementTab(tab);
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "unregister advancement tab(s) " + tabs;
    }

}
