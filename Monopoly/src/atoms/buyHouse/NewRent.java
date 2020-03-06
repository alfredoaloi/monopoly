package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("newRent")
public class NewRent {

	@Param(0)
	private int property;

	@Param(1)
	private int newRent;

	public NewRent() {
	}

	public NewRent(int property, int newRent) {
		super();
		this.property = property;
		this.newRent = newRent;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public int getNewRent() {
		return newRent;
	}

	public void setNewRent(int newRent) {
		this.newRent = newRent;
	}

}
