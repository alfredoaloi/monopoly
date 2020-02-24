package boxes;

import application.ApplicationController;
import logic.Player;

public class Tax extends Box {

	public Tax(String name) {
		super(name);
	}

	@Override
	public void onAction(Player player) {
		player.setCash(player.getCash() - 200);
		ApplicationController.lastEventString = this.getName() + ", il giocatore " + player.getName() + " paga $200";
	}

}
