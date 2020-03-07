package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("noSellHouse")
public class NoSellHouse {

	@Param(0)
	private int property;

	public NoSellHouse() {
	}

	public NoSellHouse(int property) {
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
