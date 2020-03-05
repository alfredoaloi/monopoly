package atoms.buyProperty;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("noBuy")
public class NoBuy {

	@Param(0)
	private int property;

	public NoBuy() {
	}

	public NoBuy(int property) {
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
