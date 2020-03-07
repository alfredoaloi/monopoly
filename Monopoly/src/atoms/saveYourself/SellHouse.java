package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("sellHouse")
public class SellHouse {

	@Param(0)
	private int property;

	public SellHouse() {
	}

	public SellHouse(int property) {
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
