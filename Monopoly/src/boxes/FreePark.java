package boxes;

import logic.Player;

public class FreePark extends Box {

	@Override
	public void onAction(Player player) {
		System.out.println("parcheggio gratis");
	}

}