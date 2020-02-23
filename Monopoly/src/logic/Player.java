package logic;

import java.util.ArrayList;

import boxes.Airport;
import boxes.AirportState;
import boxes.Property;
import boxes.PropertyState;

public class Player {

	private String name;
	private int cash;
	private int position;
	private int properties;
	private boolean human;
	private int daysInPrison;

	public Player(String name, int cash, boolean human) {
		super();
		this.name = name;
		this.cash = cash;
		this.human = human;
		position = 0;
		properties = 0;
		daysInPrison = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getProperties() {
		return properties;
	}

	public void setProperties(int properties) {
		this.properties = properties;
	}

	public boolean isHuman() {
		return human;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}

	public int getDaysInPrison() {
		return daysInPrison;
	}

	public void setDaysInPrison(int daysInPrison) {
		this.daysInPrison = daysInPrison;
	}

	public void payToBank(int amount) {
		this.cash -= amount;
	}

	public void pickFromBank(int amount) {
		this.cash += amount;
	}

	public void payToPlayer(Player player, int amount) {
		this.cash -= amount;
		player.cash += amount;
	}

	public void buyHouse(Property property) {
		this.cash -= property.getHouseCost();
		if (property.getState() == PropertyState.ALL_GROUP) {
			property.setState(PropertyState.ONE_HOUSE);
		} else if (property.getState() == PropertyState.ONE_HOUSE) {
			property.setState(PropertyState.TWO_HOUSES);
		} else if (property.getState() == PropertyState.TWO_HOUSES) {
			property.setState(PropertyState.THREE_HOUSES);
		} else if (property.getState() == PropertyState.THREE_HOUSES) {
			property.setState(PropertyState.FOUR_HOUSES);
		} else if (property.getState() == PropertyState.FOUR_HOUSES) {
			property.setState(PropertyState.ONE_HOTEL);
		}
	}

	public void sellHouse(Property property) {
		this.cash += (property.getHouseCost() / 2);
		if (property.getState() == PropertyState.ONE_HOUSE) {
			property.setState(PropertyState.ALL_GROUP);
		} else if (property.getState() == PropertyState.TWO_HOUSES) {
			property.setState(PropertyState.ONE_HOUSE);
		} else if (property.getState() == PropertyState.THREE_HOUSES) {
			property.setState(PropertyState.TWO_HOUSES);
		} else if (property.getState() == PropertyState.FOUR_HOUSES) {
			property.setState(PropertyState.THREE_HOUSES);
		} else if (property.getState() == PropertyState.ONE_HOTEL) {
			property.setState(PropertyState.FOUR_HOUSES);
		}
	}

	public void mortageProperty(Property property) {
		this.cash += (property.getValue() / 2);
		property.setState(PropertyState.MORTAGED);
	}

	public void unMortageProperty(Property property) {
		this.cash -= (property.getValue() / 2);
		property.setState(PropertyState.NO_HOUSES);
		property.checkAllGroup();
	}

	public void mortageAirport(Airport airport) {
		this.cash += (airport.getValue() / 2);
		airport.setState(AirportState.MORTAGED);
	}

	public void unMortageAirport(Airport airport) {
		this.cash -= (airport.getValue() / 2);
		airport.setState(AirportState.ONE_AIRPORT);
		airport.checkAllGroup();
	}

	public boolean manageExchange(int myCashOffer, int hisCashOffer, ArrayList<Property> myProperties,
			ArrayList<Airport> myAirports, ArrayList<Property> hisProperties, ArrayList<Airport> hisAirports) {
		return true;
	}

}
