package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("buyHouse")
public class BuyHouse {

	@Param(0)
	private int property;

	@Param(1)
	private int houseCost;

	public BuyHouse() {
	}

	public BuyHouse(int property, int houseCost) {
		super();
		this.property = property;
		this.houseCost = houseCost;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}

}
