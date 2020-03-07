package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("moneyLeft")
public class MoneyLeft {

	@Param(0)
	private int moneyLeft;

	public MoneyLeft() {
	}

	public MoneyLeft(int moneyLeft) {
		super();
		this.moneyLeft = moneyLeft;
	}

	public int getMoneyLeft() {
		return moneyLeft;
	}

	public void setMoneyLeft(int moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

}
