package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cashEarned")
public class CashEarned {

	@Param(0)
	private int cashEarned;

	public CashEarned() {
	}

	public CashEarned(int cashEarned) {
		super();
		this.cashEarned = cashEarned;
	}

	public int getCashEarned() {
		return cashEarned;
	}

	public void setCashEarned(int cashEarned) {
		this.cashEarned = cashEarned;
	}

}
