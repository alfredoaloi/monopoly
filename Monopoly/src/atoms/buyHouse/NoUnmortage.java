package atoms.buyHouse;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("noUnmortage")
public class NoUnmortage {

	@Param(0)
	private int property;

	public NoUnmortage() {
	}

	public NoUnmortage(int property) {
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
