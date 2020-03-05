package atoms.buyProperty;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("buy")
public class Buy {

	@Param(0)
	private int property;
	
	public Buy() {
	}

	public Buy(int property) {
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
