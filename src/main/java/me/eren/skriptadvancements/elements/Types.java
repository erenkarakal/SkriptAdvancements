package me.eren.skriptadvancements.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;

public class Types {

    static {
        Classes.registerClass(
                new ClassInfo<>(AdvancementFrameType.class, "advancementframetype")
                        .user("advancement frame type")
                        .name("Advancement Frame Type")
                        .description("Represents a custom advancement frame type")
                        .examples("task, goal, challenge")
                        .since("2.0"));
    }

}
