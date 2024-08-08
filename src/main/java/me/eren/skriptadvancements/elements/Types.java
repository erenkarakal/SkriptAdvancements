package me.eren.skriptadvancements.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import me.eren.skriptadvancements.wrapper.EnumWrapper;

public class Types {

    static {
        EnumWrapper<AdvancementFrameType> FRAME_TYPE_ENUM = new EnumWrapper<>(AdvancementFrameType.class);
        Classes.registerClass(FRAME_TYPE_ENUM.getClassInfo("advancementframetype")
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
