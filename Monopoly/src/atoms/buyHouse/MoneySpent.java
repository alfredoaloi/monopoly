package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("moneySpent")
public class MoneySpent {

	@Param(0)
	private int moneySpent;

	public MoneySpent() {
	}

	public MoneySpent(int moneySpent) {
		super();
		this.moneySpent = moneySpent;
	}

	public int getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

}
