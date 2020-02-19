package logic;

import java.util.ArrayList;

import boxes.Airport;
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
		boxes.add(new Property("TERAMO", 60, PropertyState.NO_OWNER, 2, 20, 50, 100, 150, 200, 300));
		boxes.add(new Chance());
		boxes.add(new Property("SANREMO", 60, PropertyState.NO_OWNER, 4, 20, 50, 100, 150, 200, 300));
		boxes.add(new Tax("TASSA PATRIMONIALE"));
		boxes.add(new Airport());
		boxes.add(new Property("L'AQUILA", 100, PropertyState.NO_OWNER, 6, 20, 50, 100, 150, 200, 300));
		boxes.add(new Unexpected());
		boxes.add(new Property("TRANI", 100, PropertyState.NO_OWNER, 6, 20, 50, 100, 150, 200, 300));
		boxes.add(new Property("TORINO", 120, PropertyState.NO_OWNER, 8, 20, 50, 100, 150, 200, 300));
		boxes.add(new Prison());
		boxes.add(new Property("COSENZA", 140, PropertyState.NO_OWNER, 10, 20, 50, 100, 150, 200, 300));
		boxes.add(new Chance());
		boxes.add(new Property("MILANO", 140, PropertyState.NO_OWNER, 10, 20, 50, 100, 150, 200, 300));
		boxes.add(new Property("VIAREGGIO", 160, PropertyState.NO_OWNER, 12, 20, 50, 100, 150, 200, 300));
		boxes.add(new Airport());
		boxes.add(new Property("TERNI", 180, PropertyState.NO_OWNER, 14, 20, 50, 100, 150, 200, 300));
		boxes.add(new Chance());
		boxes.add(new Property("MESSINA", 180, PropertyState.NO_OWNER, 14, 20, 50, 100, 150, 200, 300));
		boxes.add(new Property("FOGGIA", 200, PropertyState.NO_OWNER, 16, 20, 50, 100, 150, 200, 300));
		boxes.add(new FreePark());
		boxes.add(new Property("CASERTA", 220, PropertyState.NO_OWNER, 18, 20, 50, 100, 150, 200, 300));
		boxes.add(new Property("BRINDISI", 220, PropertyState.NO_OWNER, 18, 20, 50, 100, 150, 200, 300));
		boxes.add(new Unexpected());
		boxes.add(new Property("ISCHIA", 240, PropertyState.NO_OWNER, 20, 20, 50, 100, 150, 200, 300));
		boxes.add(new Airport());
		boxes.add(new Property("MONOPOLI", 260, PropertyState.NO_OWNER, 22, 20, 50, 100, 150, 200, 300));
		boxes.add(new Property("ASCOLI PICENO", 260, PropertyState.NO_OWNER, 22, 20, 50, 100, 150, 200, 300));
		boxes.add(new Unexpected());
		boxes.add(new Property("ISOLA D'ELBA", 280, PropertyState.NO_OWNER, 24, 20, 50, 100, 150, 200, 300));
		boxes.add(new ToPrison());
		boxes.add(new Property("ANDRIA", 300, PropertyState.NO_OWNER, 26, 20, 50, 100, 150, 200, 300));
		boxes.add(new Property("BARLETTA", 300, PropertyState.NO_OWNER, 26, 20, 50, 100, 150, 200, 300));
		boxes.add(new Chance());
		boxes.add(new Property("FIRENZE", 320, PropertyState.NO_OWNER, 28, 20, 50, 100, 150, 200, 300));
		boxes.add(new Airport());
		boxes.add(new Unexpected());
		boxes.add(new Property("REGGIO CALABRIA", 350, PropertyState.NO_OWNER, 35, 20, 50, 100, 150, 200, 300));
		boxes.add(new Tax("TASSA DI LUSSO"));
		boxes.add(new Property("CHIETI", 400, PropertyState.NO_OWNER, 50, 20, 50, 100, 150, 200, 300));
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

}