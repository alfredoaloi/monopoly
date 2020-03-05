package atoms.buyProperty;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("currentPlayerMoney")
public class CurrentPlayerMoney {

	@Param(0)
	private int money;

	public CurrentPlayerMoney() {
	}
	
	public CurrentPlayerMoney(int money) {
		super();
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
