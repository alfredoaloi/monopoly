package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("property")
public class Property {

	@Param(0)
	private int id;

	@Param(1)
	private int houseCost;

	public Property() {
	}

	public Property(int id, int houseCost) {
		super();
		this.id = id;
		this.houseCost = houseCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}

}
