package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;

public class EffRegisterTab extends Effect {

    public static String lastCreatedTab;
    private Expression<String> name;

    static {
        Skript.registerEffect(EffRegisterTab.class, "(create|register) [an|a [new]] [custom] advancement tab named %string%");
    }

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void execute(Event event) {
        String name = this.name.getSingle(event);
        if (name == null || SkriptAdvancements.getAdvancementAPI().isAdvancementTabRegistered(name)) return;

        AdvancementTab tab = SkriptAdvancements.getAdvancementAPI().createAdvancementTab(name);
        ExprTabs.TABS.put(name, new AdvancementTabBuilder(tab));
        lastCreatedTab = name;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString(Event event, boolean debug) {
        return "register an advancement tab named " + this.name;
    }
}
