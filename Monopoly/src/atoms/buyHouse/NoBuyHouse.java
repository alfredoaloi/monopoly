package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("noBuyHouse")
public class NoBuyHouse {

	@Param(0)
	private int property;

	public NoBuyHouse() {
	}

	public NoBuyHouse(int property) {
		super();
		this.property = property;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

}
