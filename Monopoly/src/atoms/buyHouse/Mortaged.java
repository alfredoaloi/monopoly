package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("mortaged")
public class Mortaged {

	@Param(0)
	private int id;

	@Param(1)
	private int unmortageCost;

	public Mortaged() {
	}

	public Mortaged(int id, int unmortageCost) {
		super();
		this.id = id;
		this.unmortageCost = unmortageCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnmortageCost() {
		return unmortageCost;
	}

	public void setUnmortageCost(int unmortageCost) {
		this.unmortageCost = unmortageCost;
	}

}
