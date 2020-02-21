package boxes;

import application.ApplicationController;
import logic.Player;

public class ToPrison extends Box {

	@Override
	public void onAction(Player player) {
		ApplicationController.lastEventString = "Il giocatore " + player.getName() + " va in prigione!";
		player.setPosition(10);
		player.setDaysInPrison(3);
	}

}
