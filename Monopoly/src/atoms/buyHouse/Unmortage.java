package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("unmortage")
public class Unmortage {

	@Param(0)
	private int property;

	@Param(1)
	private int unmortageCost;

	public Unmortage() {
	}

	public Unmortage(int property, int unmortageCost) {
		super();
		this.property = property;
		this.unmortageCost = unmortageCost;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public int getUnmortageCost() {
		return unmortageCost;
	}

	public void setUnmortageCost(int unmortageCost) {
		this.unmortageCost = unmortageCost;
	}

}
