package logic;

import java.util.ArrayList;
import java.util.Comparator;

import application.ApplicationController;
import boxes.Airport;
import boxes.AirportState;
import boxes.Box;
import boxes.Chance;
import boxes.FreePark;
import boxes.Prison;
import boxes.Property;
import boxes.PropertyState;
import boxes.Start;
import boxes.Tax;
import boxes.ToPrison;
import boxes.Unexpected;

public class Board {

	public static final int BOXES = 40;

	private boolean endgame = false;
	private ArrayList<Box> boxes;
	private ArrayList<Player> players;
	private int currentPlayerIndex;

	public Board() {
		boxes = new ArrayList<Box>();
		players = new ArrayList<Player>();
		currentPlayerIndex = 0;
	}

	public ArrayList<Box> getBoxes() {
		return boxes;
	}

	public boolean isEndgame() {
		return endgame;
	}

	public void setEndgame(boolean endgame) {
		this.endgame = endgame;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public void createBoard() {
		boxes.add(new Start());
		boxes.add(new Property("TERAMO", 60, 50, PropertyState.NO_OWNER, new int[] { 3 }, 2, 4, 10, 30, 90, 160, 250,
				this));
		boxes.add(new Chance());
		boxes.add(new Property("SANREMO", 60, 50, PropertyState.NO_OWNER, new int[] { 1 }, 4, 8, 20, 60, 180, 320, 450,
				this));
		boxes.add(new Tax("TASSA PATRIMONIALE"));
		boxes.add(new Airport("Aereoporto 1", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 15, 25, 35 }, this));
		boxes.add(new Property("L'AQUILA", 100, 100, PropertyState.NO_OWNER, new int[] { 8, 9 }, 6, 12, 50, 100, 150,
				200, 300, this));
		boxes.add(new Unexpected());
		boxes.add(new Property("TRANI", 100, 100, PropertyState.NO_OWNER, new int[] { 6, 9 }, 6, 12, 50, 100, 150, 200,
				300, this));
		boxes.add(new Property("TORINO", 120, 100, PropertyState.NO_OWNER, new int[] { 6, 8 }, 8, 16, 50, 100, 150, 200,
				300, this));
		boxes.add(new Prison());
		boxes.add(new Property("COSENZA", 140, 100, PropertyState.NO_OWNER, new int[] { 13, 14 }, 10, 20, 50, 100, 150,
				200, 300, this));
		boxes.add(new Chance());
		boxes.add(new Property("MILANO", 140, 100, PropertyState.NO_OWNER, new int[] { 11, 14 }, 10, 20, 50, 100, 150,
				200, 300, this));
		boxes.add(new Property("VIAREGGIO", 160, 100, PropertyState.NO_OWNER, new int[] { 11, 13 }, 12, 24, 50, 100,
				150, 200, 300, this));
		boxes.add(new Airport("Aereoporto 2", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 5, 25, 35 }, this));
		boxes.add(new Property("TERNI", 180, 100, PropertyState.NO_OWNER, new int[] { 18, 19 }, 14, 28, 50, 100, 150,
				200, 300, this));
		boxes.add(new Chance());
		boxes.add(new Property("MESSINA", 180, 100, PropertyState.NO_OWNER, new int[] { 16, 19 }, 14, 28, 50, 100, 150,
				200, 300, this));
		boxes.add(new Property("FOGGIA", 200, 100, PropertyState.NO_OWNER, new int[] { 16, 18 }, 16, 32, 50, 100, 150,
				200, 300, this));
		boxes.add(new FreePark());
		boxes.add(new Property("CASERTA", 220, 150, PropertyState.NO_OWNER, new int[] { 22, 24 }, 18, 36, 50, 100, 150,
				200, 300, this));
		boxes.add(new Property("BRINDISI", 220, 150, PropertyState.NO_OWNER, new int[] { 21, 24 }, 18, 36, 50, 100, 150,
				200, 300, this));
		boxes.add(new Unexpected());
		boxes.add(new Property("ISCHIA", 240, 150, PropertyState.NO_OWNER, new int[] { 21, 22 }, 20, 40, 50, 100, 150,
				200, 300, this));
		boxes.add(new Airport("Aereoporto 3", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 5, 15, 35 }, this));
		boxes.add(new Property("MONOPOLI", 260, 150, PropertyState.NO_OWNER, new int[] { 27, 29 }, 22, 44, 50, 100, 150,
				200, 300, this));
		boxes.add(new Property("ASCOLI PICENO", 260, 150, PropertyState.NO_OWNER, new int[] { 26, 29 }, 22, 44, 50, 100,
				150, 200, 300, this));
		boxes.add(new Unexpected());
		boxes.add(new Property("ISOLA D'ELBA", 280, 150, PropertyState.NO_OWNER, new int[] { 26, 27 }, 24, 48, 50, 100,
				150, 200, 300, this));
		boxes.add(new ToPrison());
		boxes.add(new Property("ANDRIA", 300, 200, PropertyState.NO_OWNER, new int[] { 32, 34 }, 26, 52, 50, 100, 150,
				200, 300, this));
		boxes.add(new Property("BARLETTA", 300, 200, PropertyState.NO_OWNER, new int[] { 31, 34 }, 26, 52, 50, 100, 150,
				200, 300, this));
		boxes.add(new Chance());
		boxes.add(new Property("FIRENZE", 320, 200, PropertyState.NO_OWNER, new int[] { 31, 32 }, 28, 56, 50, 100, 150,
				200, 300, this));
		boxes.add(new Airport("Aereoporto 4", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 5, 15, 25 }, this));
		boxes.add(new Unexpected());
		boxes.add(new Property("REGGIO CALABRIA", 350, 200, PropertyState.NO_OWNER, new int[] { 39 }, 35, 70, 50, 100,
				150, 200, 300, this));
		boxes.add(new Tax("TASSA DI LUSSO"));
		boxes.add(new Property("CHIETI", 400, 200, PropertyState.NO_OWNER, new int[] { 37 }, 50, 100, 50, 100, 150, 200,
				300, this));
	}

//	public static void main(String[] args) {
//		Board board = new Board();
//		board.createBoard();
//
//		board.getPlayers().add(new Player("Alfredo", 1500));
//		board.getPlayers().add(new Player("Sara", 1500));
//		board.getPlayers().add(new Player("Roberta", 1500));
//		board.getPlayers().add(new Player("Ottavio", 1500));
//
//		while (!(board.isEndgame())) {
//			for (Player player : board.getPlayers()) {
//				if (board.isEndgame()) {
//					break;
//				}
//				System.out.println("Sta giocando " + player.getName());
//				int steps = Dice.launch();
//				System.out.println(player.getName() + " ha fatto " + steps);
//				player.setPosition((player.getPosition() + steps) % Board.BOXES);
//				System.out.println(player.getName() + " è finito sulla casella "
//						+ board.getBoxes().get(player.getPosition()).getName());
//				board.getBoxes().get(player.getPosition()).onAction(player);
//				System.out.println("Il giocatore " + player.getName() + " ha finito - " + "Cash: " + player.getCash()
//						+ " - Properties: " + player.getProperties());
//
//				for (Player p : board.getPlayers()) {
//					if (p.getCash() < 0) {
//						board.setEndgame(true);
//						break;
//					}
//				}
//			}
//		}
//
//		for (Player player : board.getPlayers()) {
//			System.out.println(player.getName() + " " + player.getCash());
//		}
//	}

	public void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % 4;
	}

	public void finalPlacement() {
		for (Player player : players) {
			for (Box box : boxes) {
				if (box instanceof Property) {
					Property property = (Property) box;
					if (property.getOwner() != null && property.getOwner().getName().equals(player.getName())) {
						while (property.getState() != PropertyState.NO_HOUSES
								&& property.getState() != PropertyState.ALL_GROUP
								&& property.getState() != PropertyState.MORTAGED) {
							player.sellHouse(property);
						}
						if (property.getState() == PropertyState.MORTAGED) {
							player.setCash(player.getCash() + (property.getValue() / 2));
						} else {
							player.setCash(player.getCash() + property.getValue());
						}
					}
				} else if (box instanceof Airport) {
					Airport airport = (Airport) box;
					if (airport.getOwner() != null && airport.getOwner().getName().equals(player.getName())) {
						if (airport.getState() == AirportState.MORTAGED) {
							player.setCash(player.getCash() + (airport.getValue() / 2));
						} else {
							player.setCash(player.getCash() + airport.getValue());
						}
					}
				}
			}
		}

		players.sort(new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				return -(new Integer(o1.getCash()).compareTo(o2.getCash()));
			}
		});

		StringBuilder builder = new StringBuilder();
		int cont = 1;
		for (Player player : players) {
			builder.append(cont + "° - " + player.getName() + " - $" + player.getCash() + "\n");
			cont++;
		}
		ApplicationController.lastEventString = builder.toString();
	}

}