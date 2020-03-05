package atoms.buyProperty;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("currentPlayer")
public class CurrentPlayer {

	@Param(0)
	private int id;
	
	public CurrentPlayer() {
	}

	public CurrentPlayer(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
