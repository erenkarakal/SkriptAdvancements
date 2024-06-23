package me.eren.skriptadvancements.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;

public class EffDisableVanillaAdvancements extends Effect {

    static {
        Skript.registerEffect(EffDisableVanillaAdvancements.class, "disable all vanilla advancements");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected void execute(Event event) {
        SkriptAdvancements.getAdvancementAPI().disableVanillaAdvancements();
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "disable all vanilla advancements";
    }

}
