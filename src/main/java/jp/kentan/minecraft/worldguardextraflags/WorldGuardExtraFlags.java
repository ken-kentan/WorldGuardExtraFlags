package jp.kentan.minecraft.worldguardextraflags;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.session.SessionManager;
import jp.kentan.minecraft.worldguardextraflags.flag.handler.GlideFlag;
import jp.kentan.minecraft.worldguardextraflags.flag.handler.RiptideFlag;
import jp.kentan.minecraft.worldguardextraflags.util.FlagUtil;
import org.bukkit.plugin.java.JavaPlugin;


public class WorldGuardExtraFlags extends JavaPlugin {

    private SessionManager sessionManager;

    @Override
    public void onLoad() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        registry.register(FlagUtil.GLIDE);
        registry.register(FlagUtil.RIPTIDE);
    }

    @Override
    public void onEnable() {
        sessionManager = WorldGuard.getInstance().getPlatform().getSessionManager();
        sessionManager.registerHandler(GlideFlag.FACTORY, null);
        sessionManager.registerHandler(RiptideFlag.FACTORY, null);
    }

    @Override
    public void onDisable() {
        sessionManager.unregisterHandler(GlideFlag.FACTORY);
        sessionManager.unregisterHandler(RiptideFlag.FACTORY);
    }
}
