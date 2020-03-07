package atoms.offerExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("playerMoney")
public class PlayerMoney {

	@Param(0)
	private int player;

	@Param(1)
	private int money;

	public PlayerMoney() {
	}

	public PlayerMoney(int player, int money) {
		super();
		this.player = player;
		this.money = money;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
