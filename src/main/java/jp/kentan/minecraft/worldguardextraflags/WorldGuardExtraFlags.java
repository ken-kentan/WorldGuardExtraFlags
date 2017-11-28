package jp.kentan.minecraft.worldguardextraflags;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import jp.kentan.minecraft.worldguardextraflags.flag.handler.GlideFlag;
import jp.kentan.minecraft.worldguardextraflags.util.FlagUtil;
import org.bukkit.plugin.java.JavaPlugin;


public class WorldGuardExtraFlags extends JavaPlugin {

    private static WorldGuardPlugin sWorldGuardPlugin;

    @Override
    public void onLoad() {
        sWorldGuardPlugin = (WorldGuardPlugin) this.getServer().getPluginManager().getPlugin("WorldGuard");

        sWorldGuardPlugin.getFlagRegistry().register(FlagUtil.GLIDE);
    }

    @Override
    public void onEnable() {
        sWorldGuardPlugin.getSessionManager().registerHandler(GlideFlag.FACTORY, null);
    }

    @Override
    public void onDisable() {
        sWorldGuardPlugin.getSessionManager().unregisterHandler(GlideFlag.FACTORY);
    }
}
