package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class EffBuildTab extends Effect {

    static {
        Skript.registerEffect(EffBuildTab.class, "build [advancement] tab");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (getParser().isCurrentSection(SecRegisterTab.class)) {
            Skript.error("This effect can only be used inside an advancement tab section");
            return false;
        }
        return true;
    }

    @Override
    protected void execute(Event event) {
        SecRegisterTab.lastCreatedTab.build();
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "build advancement tab";
    }

}
