package logic;

import java.io.File;
import java.util.ArrayList;

import application.ApplicationController;
import atoms.acceptExchange.Accept;
import atoms.acceptExchange.Money;
import atoms.acceptExchange.NoAccept;
import atoms.acceptExchange.OfferProperty;
import atoms.buyHouse.BuyHouse;
import atoms.buyHouse.MoneyBound;
import atoms.buyHouse.MoneySpent;
import atoms.buyHouse.Mortaged;
import atoms.buyHouse.NoBuyHouse;
import atoms.buyHouse.NoUnmortage;
import atoms.buyHouse.Unmortage;
import atoms.buyProperty.Buy;
import atoms.buyProperty.CurrentPlayer;
import atoms.buyProperty.CurrentPlayerMoney;
import atoms.buyProperty.NoBuy;
import atoms.buyProperty.PropertyToBuy;
import boxes.Airport;
import boxes.AirportState;
import boxes.Box;
import boxes.Property;
import boxes.PropertyState;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class Player {

	private int id;
	private String name;
	private int cash;
	private int position;
	private int properties;
	private boolean human;
	private int daysInPrison;

	public Player(int id, String name, int cash, boolean human) {
		super();
		this.id = id;
		this.name = name;
		this.cash = cash;
		this.human = human;
		position = 0;
		properties = 0;
		daysInPrison = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean manageExchange(Player currentPlayer, int myCashOffer, int hisCashOffer,
			ArrayList<Property> myProperties, ArrayList<Airport> myAirports, ArrayList<Property> hisProperties,
			ArrayList<Airport> hisAirports, Board board) {
		boolean accepted = aiAcceptExchange(currentPlayer, myCashOffer, hisCashOffer, myProperties, myAirports,
				hisProperties, hisAirports, board);

		if (accepted) {
			currentPlayer.cash += hisCashOffer;
			currentPlayer.cash -= myCashOffer;
			this.cash += myCashOffer;
			this.cash -= hisCashOffer;
			for (Property property : myProperties) {
				property.setOwner(this);
				property.checkAllGroup();
			}
			for (Airport airport : myAirports) {
				airport.setOwner(this);
				airport.checkAllGroup();
			}
			for (Property property : hisProperties) {
				property.setOwner(currentPlayer);
				property.checkAllGroup();
			}
			for (Airport airport : hisAirports) {
				airport.setOwner(this);
				airport.checkAllGroup();
			}
			ApplicationController.lastEventString = "Scambio accettato da " + this.name;
			return true;
		} else {
			return false;
		}
	}

	public void tryToSaveYourself(Board board) {
		StringBuilder builder = new StringBuilder();
		for (Box box : board.getBoxes()) {
			if (box instanceof Property) {
				Property property = (Property) box;
				if (property.getOwner() != null && property.getOwner().getName().equals(this.getName())) {
					while (property.getState() != PropertyState.NO_HOUSES
							&& property.getState() != PropertyState.ALL_GROUP
							&& property.getState() != PropertyState.MORTAGED) {
						sellHouse(property);
						builder.append(
								"Il giocatore " + this.name + " ha venduto una casa a " + property.getName() + "\n");
						if (this.cash > 0) {
							builder.append("Il giocatore " + this.name + " ha racimolato abbastanza soldi!\n");
							ApplicationController.lastEventString = builder.toString();
							return;
						}
					}
					mortageProperty(property);
					builder.append(
							"Il giocatore " + this.name + " ha ipotecato la proprietà " + property.getName() + "\n");
					if (this.cash > 0) {
						builder.append("Il giocatore " + this.name + " ha racimolato abbastanza soldi!\n");
						ApplicationController.lastEventString = builder.toString();
						return;
					}
				}
			} else if (box instanceof Airport) {
				Airport airport = (Airport) box;
				if (airport.getOwner() != null && airport.getOwner().getName().equals(this.getName())
						&& airport.getState() != AirportState.MORTAGED) {
					mortageAirport(airport);
					builder.append(
							"Il giocatore " + this.name + " ha ipotecato l'aereoporto " + airport.getName() + "\n");
					if (this.cash > 0) {
						builder.append("Il giocatore " + this.name + " ha racimolato abbastanza soldi!\n");
						ApplicationController.lastEventString = builder.toString();
						return;
					}
				}
			}
		}
		if (this.cash < 0) {
			builder.append("Il giocatore " + this.name + " non ce la fa a racimolare altri soldi!\n");
			ApplicationController.lastEventString = builder.toString();
		}
	}

	public boolean aiBuyProperty(Property property, Board board) {
		String encodingResource = "encodings" + File.separator + "buyProperty.dlv";
		Handler handler = new DesktopHandler(new DLV2DesktopService("C:\\Users\\laolr\\Desktop\\dlv2"));
		InputProgram facts = new ASPInputProgram();
		try {
			facts.addObjectInput(new CurrentPlayer(this.id));
			facts.addObjectInput(new CurrentPlayerMoney(this.cash));
			facts.addObjectInput(new PropertyToBuy(property.getId(), property.getValue()));
			for (Box box : board.getBoxes()) {
				if (box instanceof Property) {
					Property temp = (Property) box;
					if (temp.getOwner() == null) {
						facts.addObjectInput(new atoms.buyProperty.Property(5, temp.getId()));
					} else {
						facts.addObjectInput(new atoms.buyProperty.Property(temp.getOwner().getId(), temp.getId()));
					}
				} else if (box instanceof Airport) {
					Airport temp = (Airport) box;
					if (temp.getOwner() == null) {
						facts.addObjectInput(new atoms.buyProperty.Property(5, temp.getId()));
					} else {
						facts.addObjectInput(new atoms.buyProperty.Property(temp.getOwner().getId(), temp.getId()));
					}
				}
				ASPMapper.getInstance().registerClass(Buy.class);
				ASPMapper.getInstance().registerClass(NoBuy.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);

		Output o = handler.startSync();

		AnswerSets answers = (AnswerSets) o;
		System.out.println(answers.getAnswersets().size());
		if (answers.getAnswersets().size() > 1) {
			return false;
		}
		for (AnswerSet a : answers.getAnswersets()) {
			System.out.println(a.toString());
			try {
				for (Object obj : a.getAtoms()) {
					if (obj instanceof Buy) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	public boolean aiBuyAirport(Airport airport, Board board) {
		String encodingResource = "encodings" + File.separator + "buyProperty.dlv";
		Handler handler = new DesktopHandler(new DLV2DesktopService("C:\\Users\\laolr\\Desktop\\dlv2"));
		InputProgram facts = new ASPInputProgram();
		try {
			facts.addObjectInput(new CurrentPlayer(this.id));
			facts.addObjectInput(new CurrentPlayerMoney(this.cash));
			facts.addObjectInput(new PropertyToBuy(airport.getId(), airport.getValue()));
			for (Box box : board.getBoxes()) {
				if (box instanceof Property) {
					Property temp = (Property) box;
					if (temp.getOwner() == null) {
						facts.addObjectInput(new atoms.buyProperty.Property(5, temp.getId()));
					} else {
						facts.addObjectInput(new atoms.buyProperty.Property(temp.getOwner().getId(), temp.getId()));
					}
				} else if (box instanceof Airport) {
					Airport temp = (Airport) box;
					if (temp.getOwner() == null) {
						facts.addObjectInput(new atoms.buyProperty.Property(5, temp.getId()));
					} else {
						facts.addObjectInput(new atoms.buyProperty.Property(temp.getOwner().getId(), temp.getId()));
					}
				}
				ASPMapper.getInstance().registerClass(Buy.class);
				ASPMapper.getInstance().registerClass(NoBuy.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);

		Output o = handler.startSync();

		AnswerSets answers = (AnswerSets) o;
		System.out.println(answers.getAnswersets().size());
		if (answers.getAnswersets().size() > 1) {
			return false;
		}
		for (AnswerSet a : answers.getAnswersets()) {
			System.out.println(a.toString());
			try {
				for (Object obj : a.getAtoms()) {
					if (obj instanceof Buy) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	public boolean aiAcceptExchange(Player currentPlayer, int myCashOffer, int hisCashOffer,
			ArrayList<Property> myProperties, ArrayList<Airport> myAirports, ArrayList<Property> hisProperties,
			ArrayList<Airport> hisAirports, Board board) {
		String encodingResource = "encodings" + File.separator + "acceptExchange.dlv";
		Handler handler = new DesktopHandler(new DLV2DesktopService("C:\\Users\\laolr\\Desktop\\dlv2"));
		InputProgram facts = new ASPInputProgram();
		try {
			facts.addObjectInput(new Money(0, hisCashOffer));
			facts.addObjectInput(new Money(1, myCashOffer));
			for (Property property : myProperties) {
				facts.addObjectInput(new OfferProperty(1, property.getId(), property.getValue()));
			}
			for (Airport airport : myAirports) {
				facts.addObjectInput(new OfferProperty(1, airport.getId(), airport.getValue()));
			}
			for (Property property : hisProperties) {
				facts.addObjectInput(new OfferProperty(0, property.getId(), property.getValue()));
			}
			for (Airport airport : hisAirports) {
				facts.addObjectInput(new OfferProperty(0, airport.getId(), airport.getValue()));
			}
			for (Box box : board.getBoxes()) {
				if (box instanceof Property) {
					Property temp = (Property) box;
					if (temp.getOwner() == null) {
						facts.addObjectInput(new atoms.acceptExchange.Property(5, temp.getId()));
					} else {
						facts.addObjectInput(new atoms.acceptExchange.Property(temp.getOwner().getId(), temp.getId()));
					}
				} else if (box instanceof Airport) {
					Airport temp = (Airport) box;
					if (temp.getOwner() == null) {
						facts.addObjectInput(new atoms.acceptExchange.Property(5, temp.getId()));
					} else {
						facts.addObjectInput(new atoms.acceptExchange.Property(temp.getOwner().getId(), temp.getId()));
					}
				}
				ASPMapper.getInstance().registerClass(Accept.class);
				ASPMapper.getInstance().registerClass(NoAccept.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);

		Output o = handler.startSync();

		AnswerSets answers = (AnswerSets) o;
		System.out.println(answers.getAnswersets().size());
		if (answers.getAnswersets().size() > 1) {
			return false;
		}
		for (AnswerSet a : answers.getAnswersets()) {
			System.out.println(a.toString());
			try {
				for (Object obj : a.getAtoms()) {
					if (obj instanceof Accept) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	public void aiBuyHouses(Board board) {
		int moneyBound = this.cash / 2;
		int moneySpent = 0;

		String encodingResource = "encodings" + File.separator + "buyHouse.dlv";
		Handler handler = new DesktopHandler(new DLV2DesktopService("C:\\Users\\laolr\\Desktop\\dlv2"));
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);

		while (true) {
			InputProgram facts = new ASPInputProgram();
			try {
				facts.addObjectInput(new MoneyBound(moneyBound - moneySpent));
				for (Box box : board.getBoxes()) {
					if (box instanceof Property) {
						Property temp = (Property) box;
						if (temp.getOwner() != null && temp.getOwner().getName().equals(this.name)) {
							if (temp.getState() == PropertyState.MORTAGED) {
								facts.addObjectInput(new Mortaged(temp.getId(), temp.getValue() / 2));
							} else if (temp.getState() != PropertyState.NO_HOUSES
									&& temp.getState() != PropertyState.ONE_HOTEL) {
								facts.addObjectInput(new atoms.buyHouse.Property(temp.getId(), temp.getHouseCost()));
							}
						}
					} else if (box instanceof Airport) {
						Airport temp = (Airport) box;
						if (temp.getOwner() != null && temp.getOwner().getName().equals(this.name)) {
							if (temp.getState() == AirportState.MORTAGED) {
								facts.addObjectInput(new Mortaged(temp.getId(), temp.getValue() / 2));
							}
						}
					}
					ASPMapper.getInstance().registerClass(MoneySpent.class);
					ASPMapper.getInstance().registerClass(BuyHouse.class);
					ASPMapper.getInstance().registerClass(NoBuyHouse.class);
					ASPMapper.getInstance().registerClass(Unmortage.class);
					ASPMapper.getInstance().registerClass(NoUnmortage.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.addProgram(facts);

			Output o = handler.startSync();

			AnswerSets answers = (AnswerSets) o;
			if (answers.getAnswersets().size() == 0) {
				break;
			}
			AnswerSet a = answers.getAnswersets().get(0);
			System.out.println(a.toString());
			try {
				StringBuilder builder = new StringBuilder();
				for (Object obj : a.getAtoms()) {
					if (obj instanceof BuyHouse) {
						BuyHouse buyHouse = (BuyHouse) obj;
						Property property = (Property) board.getBoxes().get(buyHouse.getProperty());
						buyHouse(property);
						builder.append(this.name + " acquista una casa a " + property.getName() + "\n");
					} else if (obj instanceof Unmortage) {
						Unmortage unmortage = (Unmortage) obj;
						Box box = board.getBoxes().get(unmortage.getProperty());
						if (box instanceof Property) {
							Property property = (Property) box;
							unMortageProperty(property);
							builder.append(this.name + " riacquista la proprietà " + property.getName() + "\n");
						} else if (box instanceof Airport) {
							Airport airport = (Airport) box;
							unMortageAirport(airport);
							builder.append(this.name + " riacquista l'aereporto " + airport.getName() + "\n");
						}
					} else if (obj instanceof MoneySpent) {
						MoneySpent moneySpentClass = (MoneySpent) obj;
						moneySpent += moneySpentClass.getMoneySpent();
					}
					ApplicationController.lastEventString = builder.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.removeProgram(facts);
		}
	}

}
