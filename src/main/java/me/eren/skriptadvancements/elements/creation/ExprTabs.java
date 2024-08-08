package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ExprTabs extends SimpleExpression<String> {

    public static Map<String, AdvancementTabBuilder> TABS = new HashMap<>();

    static {
        for (AdvancementTab tab : SkriptAdvancements.getAdvancementAPI().getTabs()) {
            TABS.put(tab.getNamespace(), new AdvancementTabBuilder(tab));
        }

        Skript.registerExpression(ExprTabs.class, String.class, ExpressionType.SIMPLE, "all [of] [the] [registered] [advancement] tabs");
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event) {
        return TABS.keySet().toArray(String[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "all the registered advancement tabs";
    }
}
