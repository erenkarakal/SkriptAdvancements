package me.eren.skriptadvancements.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.eren.skriptadvancements.AdvancementTabBuilder;

public class Types {

    static {
        Classes.registerClass(
                new ClassInfo<>(AdvancementFrameType.class, "advancementframetype")
                        .user("advancement frame type")
                        .name("Advancement Frame Type")
                        .description("Represents a custom advancement frame type")
                        .examples("task, goal, challenge")
                        .since("2.0"));

        Classes.registerClass(
                new ClassInfo<>(AdvancementTabBuilder.class, "advancementtab")
                        .user("advancement tab")
                        .name("Advancement Tab")
                        .description("Represents a custom advancement tab")
                        .examples("") // TODO
                        .since("") // TODO
        );
    }

}
