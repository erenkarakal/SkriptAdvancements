package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import org.bukkit.event.Event;

public class EffBuildTab extends Effect {

    static {
        Skript.registerEffect(EffBuildTab.class, "(build|finali(z|s)e) [advancement] [tab[s]] %advancementtabs%");
    }

    private Expression<AdvancementTabBuilder> tabs;

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        tabs = (Expression<AdvancementTabBuilder>) exprs[0];
        return true;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void execute(Event event) {
        for (AdvancementTabBuilder tab : tabs.getArray(event)) {
            if (!tab.tab.isInitialised())
                tab.build();
        }
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString(Event event, boolean debug) {
        return "build advancement tabs " + tabs.toString(event, debug);
    }

}
