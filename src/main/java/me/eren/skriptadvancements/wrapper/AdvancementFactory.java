package me.eren.skriptadvancements.wrapper;

public class AdvancementFactory {

    public static AdvancementWrapper wrap(com.fren_gor.ultimateAdvancementAPI.advancement.Advancement advancement) {
        return new CustomAdvancementWrapper(advancement);
    }

    public static AdvancementWrapper wrap(org.bukkit.advancement.Advancement advancement) {
        return new BukkitAdvancementWrapper(advancement);
    }

}
