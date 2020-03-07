package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("property")
public class Property {

	@Param(0)
	private int id;

	@Param(1)
	private int oldRent;

	@Param(2)
	private int newRent;

	public Property() {
	}

	public Property(int id, int oldRent, int newRent) {
		super();
		this.id = id;
		this.oldRent = oldRent;
		this.newRent = newRent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOldRent() {
		return oldRent;
	}

	public void setOldRent(int oldRent) {
		this.oldRent = oldRent;
	}

	public int getNewRent() {
		return newRent;
	}

	public void setNewRent(int newRent) {
		this.newRent = newRent;
	}

}
