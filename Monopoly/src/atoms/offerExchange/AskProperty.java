package atoms.offerExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("askProperty")
public class AskProperty {

	@Param(0)
	private int property;

	@Param(1)
	private int value;

	public AskProperty() {
	}

	public AskProperty(int property, int value) {
		super();
		this.property = property;
		this.value = value;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
