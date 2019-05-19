package jp.kentan.minecraft.worldguardextraflags.flag.handler;


import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;
import jp.kentan.minecraft.worldguardextraflags.util.FlagUtil;
import org.bukkit.entity.Player;

import java.util.Set;

public class GlideFlag extends FlagValueChangeHandler<State> {

    public static final Factory FACTORY = new Factory();

    public static class Factory extends Handler.Factory<GlideFlag> {
        @Override
        public GlideFlag create(Session session) {
            return new GlideFlag(session);
        }
    }

    private GlideFlag(Session session) {
        super(session, FlagUtil.GLIDE);
    }

    private void updateGlide(LocalPlayer localPlayer, State newValue) {
        Player player = ((BukkitPlayer)localPlayer).getPlayer();

        if (newValue == State.DENY && player.isGliding()) {
            player.setGliding(false);
        }
    }

    @Override
    protected void onInitialValue(LocalPlayer player, ApplicableRegionSet set, State value) {
        this.updateGlide(player, value);
    }

    @Override
    protected boolean onSetValue(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, State currentValue, State lastValue, MoveType moveType) {
        this.updateGlide(player, currentValue);

        return true;
    }

    @Override
    protected boolean onAbsentValue(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, State lastValue, MoveType moveType) {
        this.updateGlide(player, null);

        return true;
    }

    @Override
    public boolean onCrossBoundary(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        Player player = ((BukkitPlayer)localPlayer).getPlayer();

        if (toSet.queryState(localPlayer, FlagUtil.GLIDE) == State.DENY && player.isGliding()) {
            player.setGliding(false);
        }

        return true;
    }
}
