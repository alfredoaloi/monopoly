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
		boxes.add(new Property(1, "TERAMO", 60, 50, PropertyState.NO_OWNER, new int[] { 3 }, 2, 4, 10, 30, 90, 160, 250,
				this));
		boxes.add(new Chance());
		boxes.add(new Property(3, "SANREMO", 60, 50, PropertyState.NO_OWNER, new int[] { 1 }, 4, 8, 20, 60, 180, 320,
				450, this));
		boxes.add(new Tax("TASSA PATRIMONIALE"));
		boxes.add(new Airport(5, "AEREOPORTO DI NAPOLI", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 15, 25, 35 }, this));
		boxes.add(new Property(6, "L'AQUILA", 100, 100, PropertyState.NO_OWNER, new int[] { 8, 9 }, 6, 12, 30, 90, 270,
				400, 550, this));
		boxes.add(new Unexpected());
		boxes.add(new Property(8, "TRANI", 100, 100, PropertyState.NO_OWNER, new int[] { 6, 9 }, 6, 12, 30, 90, 270,
				400, 550, this));
		boxes.add(new Property(9, "TORINO", 120, 100, PropertyState.NO_OWNER, new int[] { 6, 8 }, 8, 16, 40, 100, 300,
				450, 600, this));
		boxes.add(new Prison());
		boxes.add(new Property(11, "COSENZA", 140, 100, PropertyState.NO_OWNER, new int[] { 13, 14 }, 10, 20, 50, 150,
				450, 625, 750, this));
		boxes.add(new Chance());
		boxes.add(new Property(13, "MILANO", 140, 100, PropertyState.NO_OWNER, new int[] { 11, 14 }, 10, 20, 50, 150,
				450, 625, 750, this));
		boxes.add(new Property(14, "VIAREGGIO", 160, 100, PropertyState.NO_OWNER, new int[] { 11, 13 }, 12, 24, 60, 180,
				500, 700, 900, this));
		boxes.add(new Airport(15, "AEREOPORTO DI CATANIA", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 5, 25, 35 }, this));
		boxes.add(new Property(16, "TERNI", 180, 100, PropertyState.NO_OWNER, new int[] { 18, 19 }, 14, 28, 70, 200,
				550, 750, 950, this));
		boxes.add(new Chance());
		boxes.add(new Property(18, "MESSINA", 180, 100, PropertyState.NO_OWNER, new int[] { 16, 19 }, 14, 28, 70, 200,
				550, 750, 950, this));
		boxes.add(new Property(19, "FOGGIA", 200, 100, PropertyState.NO_OWNER, new int[] { 16, 18 }, 16, 32, 80, 220,
				600, 800, 1000, this));
		boxes.add(new FreePark());
		boxes.add(new Property(21, "CASERTA", 220, 150, PropertyState.NO_OWNER, new int[] { 22, 24 }, 18, 36, 90, 250,
				700, 875, 1050, this));
		boxes.add(new Property(22, "BRINDISI", 220, 150, PropertyState.NO_OWNER, new int[] { 21, 24 }, 18, 36, 90, 250,
				700, 875, 1050, this));
		boxes.add(new Unexpected());
		boxes.add(new Property(24, "ISCHIA", 240, 150, PropertyState.NO_OWNER, new int[] { 21, 22 }, 20, 40, 100, 300,
				750, 925, 1100, this));
		boxes.add(new Airport(25, "AEREOPORTO DI MILANO", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 5, 15, 35 }, this));
		boxes.add(new Property(26, "MONOPOLI", 260, 150, PropertyState.NO_OWNER, new int[] { 27, 29 }, 22, 44, 110, 330,
				800, 975, 1150, this));
		boxes.add(new Property(27, "ASCOLI PICENO", 260, 150, PropertyState.NO_OWNER, new int[] { 26, 29 }, 22, 44, 110,
				330, 800, 975, 1150, this));
		boxes.add(new Unexpected());
		boxes.add(new Property(29, "ISOLA D'ELBA", 280, 150, PropertyState.NO_OWNER, new int[] { 26, 27 }, 24, 48, 120,
				360, 850, 1025, 1200, this));
		boxes.add(new ToPrison());
		boxes.add(new Property(31, "ANDRIA", 300, 200, PropertyState.NO_OWNER, new int[] { 32, 34 }, 26, 52, 130, 390,
				900, 1100, 1275, this));
		boxes.add(new Property(32, "BARLETTA", 300, 200, PropertyState.NO_OWNER, new int[] { 31, 34 }, 26, 52, 130, 390,
				900, 1100, 1275, this));
		boxes.add(new Chance());
		boxes.add(new Property(34, "FIRENZE", 320, 200, PropertyState.NO_OWNER, new int[] { 31, 32 }, 28, 56, 150, 450,
				1000, 1200, 1400, this));
		boxes.add(new Airport(35, "AEREOPORTO DI ROMA", 200, AirportState.NO_OWNER, 25, 50, 100, 200, null,
				new int[] { 5, 15, 25 }, this));
		boxes.add(new Unexpected());
		boxes.add(new Property(37, "REGGIO CALABRIA", 350, 200, PropertyState.NO_OWNER, new int[] { 39 }, 35, 70, 175,
				500, 1110, 1300, 1500, this));
		boxes.add(new Tax("TASSA DI LUSSO"));
		boxes.add(new Property(39, "CHIETI", 400, 200, PropertyState.NO_OWNER, new int[] { 37 }, 50, 100, 200, 600,
				1400, 1700, 2000, this));
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