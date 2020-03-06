package atoms.acceptExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("offerProperty")
public class OfferProperty {

	@Param(0)
	private int offerer;

	@Param(1)
	private int id;

	@Param(2)
	private int value;

	public OfferProperty() {
	}

	public OfferProperty(int offerer, int id, int value) {
		super();
		this.offerer = offerer;
		this.id = id;
		this.value = value;
	}

	public int getOfferer() {
		return offerer;
	}

	public void setOfferer(int offerer) {
		this.offerer = offerer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
