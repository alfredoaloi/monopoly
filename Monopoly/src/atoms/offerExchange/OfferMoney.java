package atoms.offerExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("offerMoney")
public class OfferMoney {

	@Param(0)
	private int money;

	public OfferMoney() {
	}

	public OfferMoney(int money) {
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
