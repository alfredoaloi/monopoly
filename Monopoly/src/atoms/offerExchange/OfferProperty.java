package atoms.offerExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("offerProperty")
public class OfferProperty {

	@Param(0)
	private int property;

	@Param(1)
	private int value;

	public OfferProperty() {
	}

	public OfferProperty(int property, int value) {
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
