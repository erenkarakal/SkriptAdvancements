package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.conditions.base.PropertyCondition;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import org.jetbrains.annotations.NotNull;

public class CondIsInitialized extends PropertyCondition<AdvancementTabBuilder> {

    static {
        register(CondIsInitialized.class, "[already] initialized", "advancementtabs");
    }

    @Override
    public boolean check(AdvancementTabBuilder builder) {
        return builder.tab.isInitialised();
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "initialized";
    }

}
