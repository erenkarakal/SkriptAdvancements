package me.eren.skriptadvancements.elements;

import ch.njol.skript.registrations.Classes;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
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
    }

}
