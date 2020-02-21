package boxes;

import application.ApplicationController;
import logic.Player;

public class Prison extends Box {

	@Override
	public void onAction(Player player) {
		if (player.getDaysInPrison() > 0) {
			ApplicationController.lastEventString = "Al giocatore " + player.getName() + " mancano "
					+ player.getDaysInPrison() + " turni prima di uscire dalla prigione";
			player.setDaysInPrison(player.getDaysInPrison() - 1);
		} else {
			ApplicationController.lastEventString = "Il giocatore " + player.getName() + " sta transitando dalla prigione";
		}
	}

}
