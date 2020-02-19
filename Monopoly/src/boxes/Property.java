package boxes;

import java.util.HashMap;

import logic.Player;

public class Property extends Box {

	private int value;
	private PropertyState state;
	private HashMap<PropertyState, Integer> rentPrice;
	private Player owner;

	public Property() {
		super("DEFAULT_PROPERTY");
		value = 0;
		state = PropertyState.NO_OWNER;
		rentPrice = new HashMap<PropertyState, Integer>();
	}

	public Property(String name, int value, PropertyState state, int noHousesRent, int allGroupRent, int oneHouseRent,
			int twoHouseRent, int threeHouseRent, int fourHouseRent, int oneHotelRent) {
		super(name);
		this.value = value;
		this.state = state;
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

	@Override
	public void onAction(Player player) {
		if (state == PropertyState.MORTAGED) {
			// La proprietà è ipotecata, quindi non si paga l'affitto
			System.out.println("Proprietà ipotecata");
			return;
		} else if (state == PropertyState.NO_OWNER) {
			if (player.getCash() < this.value) {
				// non ho abbastanza soldi
				System.out.println(player.getName() + " non ha abbastanza soldi per comprare la proprietà");
				return;
			} else {
				// Il giocatore se la compra PER IL MOMENTO
				System.out.println("Proprietà comprata dal giocatore " + player.getName());
				player.payToBank(this.value);
				this.owner = player;
				this.state = PropertyState.NO_HOUSES;
				player.setProperties(player.getProperties() + 1);
				return;
			}
		} else if (owner.equals(player)) {
			// Il giocatore è il proprietario della proprietà
			System.out.println("Il giocatore è finito su una sua proprietà");
			return;
		} else {
			System.out.println("Il giocatore " + player.getName() + " deve pagare " + this.rentPrice.get(this.state)
					+ " al giocatore " + this.owner.getName());
			player.payToPlayer(this.owner, this.rentPrice.get(this.state));
			return;
		}
	}

}
