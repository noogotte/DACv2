package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StopCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 0;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Stage stage = DAC.getStageManager().get(context.getPlayer());
        if (stage == null) {
            error(DACMessage.CmdStopNoGameToStop);
        }
        stage.stop();
        if (!(stage instanceof Game)) {
            stage.send(DACMessage.JoinStopped);
        }
    }

}
