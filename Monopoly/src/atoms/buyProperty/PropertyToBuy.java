package atoms.buyProperty;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("propertyToBuy")
public class PropertyToBuy {

	@Param(0)
	private int id;

	@Param(1)
	private int cost;

	public PropertyToBuy() {
	}

	public PropertyToBuy(int id, int cost) {
		super();
		this.id = id;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
