package jp.kentan.minecraft.worldguardextraflags.flag.handler;


import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;
import jp.kentan.minecraft.worldguardextraflags.util.FlagUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GlideFlag extends FlagValueChangeHandler<State> {

    public static final Factory FACTORY = new Factory();

    public static class Factory extends Handler.Factory<GlideFlag> {
        @Override
        public GlideFlag create(Session session) {
            return new GlideFlag(session);
        }
    }

    private State mCurrentValue;

    GlideFlag(Session session) {
        super(session, FlagUtil.GLIDE);
    }

    private void updateGlide(Player player, State newValue, World world) {
        if (getSession().getManager().hasBypass(player, world) || newValue == null) {
            mCurrentValue = null;
            return;
        }

        if (newValue == State.DENY && player.isGliding()) {
            player.setGliding(false);
        }

        mCurrentValue = newValue;
    }

    @Override
    protected void onInitialValue(Player player, ApplicableRegionSet set, State value) {
        this.updateGlide(player, value, player.getWorld());
    }

    @Override
    protected boolean onSetValue(Player player, Location from, Location to, ApplicableRegionSet toSet, State currentValue, State lastValue, MoveType moveType) {
        this.updateGlide(player, currentValue, to.getWorld());

        return true;
    }

    @Override
    protected boolean onAbsentValue(Player player, Location from, Location to, ApplicableRegionSet toSet, State lastValue, MoveType moveType) {
        this.updateGlide(player, null, player.getWorld());

        return true;
    }

    @Override
    public boolean testMoveTo(Player player, Location from, Location to, ApplicableRegionSet toSet, MoveType moveType) {
        if (mCurrentValue == State.DENY && player.isGliding()) {
            player.setGliding(false);
        }

        return true;
    }
}
