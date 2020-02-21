package boxes;

import java.util.HashMap;
import java.util.Optional;

import application.ApplicationController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import logic.Board;
import logic.Player;

public class Property extends Box {

	private int value;
	private PropertyState state;
	private HashMap<PropertyState, Integer> rentPrice;
	private Player owner;
	private int[] sameGroupPositions;
	private Board board;

	public Property(String name, int value, PropertyState state, int[] sameGroupPositions, int noHousesRent,
			int allGroupRent, int oneHouseRent, int twoHouseRent, int threeHouseRent, int fourHouseRent,
			int oneHotelRent, Board board) {
		super(name);
		this.value = value;
		this.state = state;
		this.sameGroupPositions = sameGroupPositions;
		this.board = board;
		this.rentPrice = new HashMap<PropertyState, Integer>();
		setRentPrice(noHousesRent, allGroupRent, oneHouseRent, twoHouseRent, threeHouseRent, fourHouseRent,
				oneHotelRent);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public PropertyState getState() {
		return state;
	}

	public void setState(PropertyState state) {
		this.state = state;
	}

	public HashMap<PropertyState, Integer> getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(HashMap<PropertyState, Integer> rentPrice) {
		this.rentPrice = rentPrice;
	}

	public void setRentPrice(int noHousesRent, int allGroupRent, int oneHouseRent, int twoHouseRent, int threeHouseRent,
			int fourHouseRent, int oneHotelRent) {
		rentPrice.put(PropertyState.NO_HOUSES, noHousesRent);
		rentPrice.put(PropertyState.ALL_GROUP, allGroupRent);
		rentPrice.put(PropertyState.ONE_HOUSE, oneHouseRent);
		rentPrice.put(PropertyState.TWO_HOUSES, twoHouseRent);
		rentPrice.put(PropertyState.THREE_HOUSES, threeHouseRent);
		rentPrice.put(PropertyState.FOUR_HOUSES, fourHouseRent);
		rentPrice.put(PropertyState.ONE_HOTEL, oneHotelRent);
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

	@Override
	public void onAction(Player player) {
		if (state == PropertyState.MORTAGED) {
			// La proprietà è ipotecata, quindi non si paga l'affitto
			ApplicationController.lastEventString = "La proprietà " + this.getName() + " è ipotecata";
			return;
		} else if (state == PropertyState.NO_OWNER) {
			if (player.getCash() < this.value) {
				ApplicationController.lastEventString = "Il giocatore " + player.getName()
						+ " non ha abbastanza denaro per acquistare la proprietà " + this.getName();
				return;
			} else {
				if (player.isHuman()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("ACQUISTO PROPRIETA'");
					alert.setHeaderText(null);
					alert.setContentText("Vuoi acquistare la proprietà " + this.getName() + "?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						ApplicationController.lastEventString = "Proprietà " + this.getName()
								+ " comprata dal giocatore " + player.getName();
						player.payToBank(this.value);
						this.owner = player;
						this.state = PropertyState.NO_HOUSES;
						player.setProperties(player.getProperties() + 1);
					} else {
						System.out.println("In teoria va all'asta");
					}
				} else {
					// Il giocatore se la compra PER IL MOMENTO
					ApplicationController.lastEventString = "Proprietà " + this.getName() + " comprata dal giocatore "
							+ player.getName();
					player.payToBank(this.value);
					this.owner = player;
					this.state = PropertyState.NO_HOUSES;
					player.setProperties(player.getProperties() + 1);
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

	private void checkAllGroup() {
		for (int propertyIndex : sameGroupPositions) {
			Property other = (Property) board.getBoxes().get(propertyIndex);
			if (other.getOwner() == null || this.owner == null
					|| !(other.getOwner().getName().equals(owner.getName()))) {
				return;
			}
		}
		for (int propertyIndex : sameGroupPositions) {
			Property other = (Property) board.getBoxes().get(propertyIndex);
			other.setState(PropertyState.ALL_GROUP);
		}
		this.state = PropertyState.ALL_GROUP;
		System.out.println("tutto il gruppo");
	}

}
