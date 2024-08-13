package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprTab extends SimpleExpression<AdvancementTabBuilder> {

    static {
        Skript.registerExpression(ExprTab.class, AdvancementTabBuilder.class, ExpressionType.COMBINED, "[advancement] tab (named|of name) %string%");
    }

    private Expression<String> name;

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected AdvancementTabBuilder @NotNull [] get(@NotNull Event event) {
        String name = this.name.getSingle(event);
        if (name == null || !ExprTabs.TABS.containsKey(name)) return new AdvancementTabBuilder[0];
        return new AdvancementTabBuilder[]{ExprTabs.TABS.get(name)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends AdvancementTabBuilder> getReturnType() {
        return AdvancementTabBuilder.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "advancement tab named " + this.name.toString(event, debug);
    }
}
