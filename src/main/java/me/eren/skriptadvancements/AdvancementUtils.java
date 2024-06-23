package me.eren.skriptadvancements;

import org.bukkit.Material;

public class AdvancementUtils {

    public static String getTexture(Material material) {
        if (material.isBlock() && material.isSolid()) {
            return "textures/block/" + material.getTranslationKey().split("minecraft\\.")[1] + ".png";
        }
        return "texture/block/dirt.png";
    }

}
