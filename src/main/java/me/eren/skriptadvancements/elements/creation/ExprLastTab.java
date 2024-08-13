package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprLastTab extends SimpleExpression<AdvancementTabBuilder> {

    static {
        Skript.registerExpression(ExprLastTab.class, AdvancementTabBuilder.class, ExpressionType.SIMPLE, "[the] last [created|registered] [advancement] tab");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
        return true;
    }

    @Override
    protected @Nullable AdvancementTabBuilder[] get(Event event) {
        return new AdvancementTabBuilder[]{ExprTabs.TABS.get(EffRegisterTab.lastCreatedTab)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends AdvancementTabBuilder> getReturnType() {
        return AdvancementTabBuilder.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "last advancement tab";
    }
}
