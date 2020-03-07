package atoms.offerExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("property")
public class Property {

	@Param(0)
	private int owner;

	@Param(1)
	private int id;

	@Param(2)
	private int value;

	public Property() {
	}

	public Property(int owner, int id, int value) {
		super();
		this.owner = owner;
		this.id = id;
		this.value = value;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
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
