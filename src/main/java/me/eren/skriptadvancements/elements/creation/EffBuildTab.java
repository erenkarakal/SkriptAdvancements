package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class EffBuildTab extends Effect {

    static {
        Skript.registerEffect(EffBuildTab.class, "build [advancement] tab");
    }

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (!getParser().isCurrentSection(SecAdvancementTab.class)) {
            Skript.error("This effect can only be used inside an advancement tab builder.");
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void execute(Event event) {
        SecAdvancementTab.lastCreatedTab.build();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString(Event event, boolean debug) {
        return "build advancement tab";
    }

}
