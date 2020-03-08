package boxes;

import java.util.HashMap;
import java.util.Optional;

import application.ApplicationController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import logic.Board;
import logic.Player;

public class Airport extends Box {

	private int id;
	private int value;
	private AirportState state;
	private HashMap<AirportState, Integer> rentPrice;
	private Player owner;
	private int[] sameGroupPositions;
	private Board board;

	public Airport(int id, String name, int value, AirportState airportState, int oneAirportRent, int twoAirportRent,
			int threeAirportRent, int fourAirportRent, Player owner, int[] sameGroupPositions, Board board) {
		super(name);
		this.id = id;
		this.value = value;
		this.state = airportState;
		rentPrice = new HashMap<>();
		setRentPrice(oneAirportRent, twoAirportRent, threeAirportRent, fourAirportRent);
		this.owner = owner;
		this.sameGroupPositions = sameGroupPositions;
		this.board = board;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRentPrice(int oneAirportRent, int twoAirportRent, int threeAirportRent, int fourAirportRent) {
		rentPrice.put(AirportState.ONE_AIRPORT, oneAirportRent);
		rentPrice.put(AirportState.TWO_AIRPORTS, twoAirportRent);
		rentPrice.put(AirportState.THREE_AIRPORTS, threeAirportRent);
		rentPrice.put(AirportState.FOUR_AIRPORTS, fourAirportRent);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public AirportState getState() {
		return state;
	}

	public void setState(AirportState airportState) {
		this.state = airportState;
	}

	public HashMap<AirportState, Integer> getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(HashMap<AirportState, Integer> rentPrice) {
		this.rentPrice = rentPrice;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int[] getSameGroupPositions() {
		return sameGroupPositions;
	}

	public void setSameGroupPositions(int[] sameGroupPositions) {
		this.sameGroupPositions = sameGroupPositions;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public void onAction(Player player) {
		if (state == AirportState.MORTAGED) {
			// La proprietà è ipotecata, quindi non si paga l'affitto
			ApplicationController.lastEventString = "La proprietà " + this.getName() + " è ipotecata";
			return;
		} else if (state == AirportState.NO_OWNER) {
			if (player.getCash() < this.value) {
				ApplicationController.lastEventString = "Il giocatore " + player.getName()
						+ " non ha abbastanza denaro per acquistare la proprietà " + this.getName();
				return;
			} else {
				if (player.isHuman()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("ACQUISTO PROPRIETA'");
					alert.setHeaderText(null);
					alert.setContentText("Vuoi acquistare " + this.getName() + "?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						ApplicationController.lastEventString = this.getName() + " comprato dal giocatore "
								+ player.getName();
						player.payToBank(this.value);
						this.owner = player;
						this.state = AirportState.ONE_AIRPORT;
					} else {
						System.out.println("In teoria va all'asta");
					}
				} else {
					if (player.aiBuyAirport(this, board)) {
						// Il giocatore se la compra PER IL MOMENTO
						ApplicationController.lastEventString = "Aereoporto " + this.getName()
								+ " comprato dal giocatore " + player.getName();
						player.payToBank(this.value);
						this.owner = player;
						this.state = AirportState.ONE_AIRPORT;
					} else {
						ApplicationController.lastEventString = "Il giocatore " + player.getName()
								+ " ha deciso di non acquistare l'aereoporto!";
					}
				}
				checkAllGroup();
			}

		} else if (owner.equals(player)) {
			// Il giocatore è il proprietario della proprietà
			ApplicationController.lastEventString = "Il giocatore " + player.getName()
					+ " è finito su una sua proprietà";
			return;
		} else {
			ApplicationController.lastEventString = "Il giocatore " + player.getName() + " deve pagare "
					+ this.rentPrice.get(this.state) + " al giocatore " + this.owner.getName();
			player.payToPlayer(this.owner, this.rentPrice.get(this.state));
			return;
		}
	}

	public void checkAllGroup() {
		int count = 1;
		for (int propertyIndex : sameGroupPositions) {
			Airport other = (Airport) board.getBoxes().get(propertyIndex);
			if (other.getOwner() != null && this.owner != null && other.getOwner().getName().equals(owner.getName())) {
				count++;
			}
		}
		for (int propertyIndex : sameGroupPositions) {
			Airport other = (Airport) board.getBoxes().get(propertyIndex);
			if (other.getOwner() != null && this.owner != null && other.getOwner().getName().equals(owner.getName())) {
				if (other.getState() != AirportState.MORTAGED) {
					other.setState(AirportState.values()[count]);
				}
			}
		}
		this.state = AirportState.values()[count];
	}

}
