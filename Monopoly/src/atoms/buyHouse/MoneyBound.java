package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("moneyBound")
public class MoneyBound {

	@Param(0)
	private int moneyBound;

	public MoneyBound() {
	}

	public MoneyBound(int moneyBound) {
		super();
		this.moneyBound = moneyBound;
	}

	public int getMoneyBound() {
		return moneyBound;
	}

	public void setMoneyBound(int moneyBound) {
		this.moneyBound = moneyBound;
	}

}
