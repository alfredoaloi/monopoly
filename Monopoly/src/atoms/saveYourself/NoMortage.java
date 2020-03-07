package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("noMortage")
public class NoMortage {

	@Param(0)
	private int property;

	public NoMortage() {
	}

	public NoMortage(int property) {
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
