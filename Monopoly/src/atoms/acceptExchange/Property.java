package atoms.acceptExchange;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("property")
public class Property {

	@Param(0)
	private int owner;

	@Param(1)
	private int id;

	public Property() {
	}

	public Property(int owner, int id) {
		super();
		this.owner = owner;
		this.id = id;
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

}
