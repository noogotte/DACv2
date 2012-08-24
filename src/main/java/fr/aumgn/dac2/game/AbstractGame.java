package fr.aumgn.dac2.game;

import static fr.aumgn.dac2.utils.DACUtil.*;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;

public abstract class AbstractGame implements Game {

    protected final DAC dac;
    protected final Arena arena;
    protected final Listener listener;

    public AbstractGame(DAC dac, Arena arena) {
        this.dac = dac;
        this.arena = arena;
        this.listener = new GameListener(this);
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[] { listener };
    }

    @Override
    public void onNewTurn() {
    }

    protected void tpBeforeJump(GamePlayer player) {
        if (dac.getConfig().getTpBeforeJump()) {
            player.teleport(arena.getDiving().toLocation(arena.getWorld()));
        }
    }

    protected void tpAfterJump(final GamePlayer player) {
        if (dac.getConfig().getTpAfterJump()) {
            int delay = dac.getConfig().getTpAfterSuccessDelay();
            if (delay > 0) {
                player.setNoDamageTicks(TICKS_PER_SECONDS);
                Bukkit.getScheduler().scheduleSyncDelayedTask(dac.getPlugin(),
                        new Runnable() {
                    @Override
                    public void run() {
                        player.tpToStart();
                    }
                });
            } else {
                player.tpToStart();
            }
        } else {
            player.setNoDamageTicks(TICKS_PER_SECONDS);
        }
    }
}