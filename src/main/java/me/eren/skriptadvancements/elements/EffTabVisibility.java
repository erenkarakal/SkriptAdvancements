package me.eren.skriptadvancements.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffTabVisibility extends Effect {

    private boolean shouldHide;
    private Expression<String> tabs;
    private Expression<Player> players;

    static {
        Skript.registerEffect(EffTabVisibility.class, "(:hide|show) [advancement] tab[s] %strings% (from|to) %players%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        shouldHide = parseResult.hasTag("hide");
        tabs = (Expression<String>) expressions[0];
        players = (Expression<Player>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Player[] players = this.players.getAll(event);
        for (String tab : tabs.getAll(event)) {
            AdvancementTab advTab = SkriptAdvancements.getAdvancementAPI().getAdvancementTab(tab);
            if (advTab == null)
                continue;
            if (shouldHide)
                advTab.hideTab(players);
            else
                advTab.showTab(players);
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        if (shouldHide)
            return "hide advancement tab(s) " + tabs + " from " + players;
        else
            return "show advancement tab(s) " + tabs + " to " + players;
    }


}
