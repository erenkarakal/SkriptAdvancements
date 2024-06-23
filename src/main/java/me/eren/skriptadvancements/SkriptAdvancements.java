package me.eren.skriptadvancements;

import ch.njol.skript.Skript;
import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class SkriptAdvancements extends JavaPlugin {

    private static AdvancementMain advancementMain;
    private static UltimateAdvancementAPI advancementAPI;

    @Override
    public void onLoad() {
        advancementMain = new AdvancementMain(this);
        advancementMain.load();
    }

    @Override
    public void onEnable() {
        advancementMain.enableSQLite(new File(getDataFolder(), "database.db"));
        advancementAPI = UltimateAdvancementAPI.getInstance(this);

        try {
            Skript.registerAddon(this).loadClasses("me.eren.skriptadvancements.elements");
        } catch (IOException e) {
            throw new RuntimeException("Error while enabling the addon.", e);
        }
    }

    @Override
    public void onDisable() {
        advancementMain.disable();
    }

    public static UltimateAdvancementAPI getAdvancementAPI() {
        return advancementAPI;
    }

}
