package boxes;

import logic.Player;

public class Unexpected extends Box {

	@Override
	public void onAction(Player player) {
		System.out.println("imprevisti");
	}

}
