package jp.kentan.minecraft.worldguardextraflags.flag.handler;


import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import jp.kentan.minecraft.worldguardextraflags.util.FlagUtil;
import org.bukkit.entity.Player;

import java.util.Set;

public class RiptideFlag extends Handler {

    public static final Factory FACTORY = new Factory();

    public static class Factory extends Handler.Factory<RiptideFlag> {
        @Override
        public RiptideFlag create(Session session) {
            return new RiptideFlag(session);
        }
    }

    private RiptideFlag(Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        Player player = ((BukkitPlayer)localPlayer).getPlayer();
        return toSet.testState(localPlayer, FlagUtil.RIPTIDE) || !player.isRiptiding();
    }

}
