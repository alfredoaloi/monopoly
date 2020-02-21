package boxes;

import java.util.Random;

import application.ApplicationController;
import logic.Player;

public class Chance extends Box {

	@Override
	public void onAction(Player player) {
		String string = null;
		int random = new Random().nextInt(10);
		switch (random) {
		case 0:
			string = "Ricevete un sussidio dall'UE, ritirate $20";
			player.pickFromBank(20);
			break;
		case 1:
			string = "Scade il premio dell'assicurazione, pagate $50";
			player.payToBank(50);
			break;
		case 2:
			string = "Avete vinto il Gran Premio della Canzone Europea, ritirate $25";
			player.pickFromBank(25);
			break;
		case 3:
			string = "Siete creditori verso la banca, ritirate $200";
			player.pickFromBank(200);
			break;
		case 4:
			string = "Pagate il conto del dottore $50, pagateli";
			player.payToBank(50);
			break;
		case 5:
			string = "Decisione della Corte di Giustizia in vostro favore, ritirate $50";
			player.pickFromBank(50);
			break;
		case 6:
			string = "Siete stato multato, pagate una multa di $10";
			player.payToBank(10);
			break;
		case 7:
			string = "Ereditate da un lontano parente $100, ritirateli";
			player.pickFromBank(100);
			break;
		case 8:
			string = "Avete vinto il secondo premio in un concorso di bellezza, ritirate $10";
			player.pickFromBank(10);
			break;
		case 9:
			string = "Spese mediche, pagate $100";
			player.payToBank(100);
			break;
		}

		ApplicationController.lastEventString = "PROBABILITA': " + string;
	}

}
