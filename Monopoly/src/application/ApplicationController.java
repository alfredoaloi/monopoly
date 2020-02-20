package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.Board;
import logic.Dice;
import logic.Player;

public class ApplicationController {

	@FXML
	private HBox north;

	@FXML
	private HBox south;

	@FXML
	private VBox east;

	@FXML
	private VBox west;

	@FXML
	private Button continueButton;

	@FXML
	private Label player1NameLabel;

	@FXML
	private Label player2NameLabel;

	@FXML
	private Label player3NameLabel;

	@FXML
	private Label player4NameLabel;

	@FXML
	private Label player1CashLabel;

	@FXML
	private Label player2CashLabel;

	@FXML
	private Label player3CashLabel;

	@FXML
	private Label player4CashLabel;

	@FXML
	private Label player1PropertiesLabel;

	@FXML
	private Label player2PropertiesLabel;

	@FXML
	private Label player3PropertiesLabel;

	@FXML
	private Label player4PropertiesLabel;

	@FXML
	private Label currentPlayerLabel;

	@FXML
	private TextArea lastEventTextArea;

	public static String lastEventString;

	private ArrayList<StackPane> stackPanes;

	private Board board;

	private String player1Name = "VERDE";
	private String player2Name = "ROSSO";
	private String player3Name = "BLU";
	private String player4Name = "GIALLO";

	@FXML
	void initialize() {
		lastEventString = "";
		stackPanes = new ArrayList<StackPane>();

		ObservableList<Node> southCopy = FXCollections.observableArrayList();
		southCopy = FXCollections.observableArrayList(south.getChildren());
		FXCollections.reverse(southCopy);
		ObservableList<Node> westCopy = FXCollections.observableArrayList();
		westCopy = FXCollections.observableArrayList(west.getChildren());
		FXCollections.reverse(westCopy);

		for (Node node : southCopy) {
			stackPanes.add((StackPane) node);
		}
		for (Node node : westCopy) {
			stackPanes.add((StackPane) node);
		}
		for (Node node : north.getChildren()) {
			stackPanes.add((StackPane) node);
		}
		for (Node node : east.getChildren()) {
			stackPanes.add((StackPane) node);
		}

		for (StackPane stackPane : stackPanes) {
			stackPane.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					System.out.println(stackPane.toString());
				}
			});
		}

		player1NameLabel.setText("Giocatore " + player1Name);
		player2NameLabel.setText("Giocatore " + player2Name);
		player3NameLabel.setText("Giocatore " + player3Name);
		player4NameLabel.setText("Giocatore " + player4Name);

		player1CashLabel.setText("Cash " + 1500);
		player2CashLabel.setText("Cash " + 1500);
		player3CashLabel.setText("Cash " + 1500);
		player4CashLabel.setText("Cash " + 1500);

		player1PropertiesLabel.setText("Proprietà " + 0);
		player2PropertiesLabel.setText("Proprietà " + 0);
		player3PropertiesLabel.setText("Proprietà " + 0);
		player4PropertiesLabel.setText("Proprietà " + 0);

		currentPlayerLabel.setText("Prossimo turno del giocatore " + player1Name);

		board = new Board();
		board.createBoard();
		Player player1 = new Player(player1Name, 1500, true);
		Player player2 = new Player(player2Name, 1500, false);
		Player player3 = new Player(player3Name, 1500, false);
		Player player4 = new Player(player4Name, 1500, false);
		board.getPlayers().add(player1);
		board.getPlayers().add(player2);
		board.getPlayers().add(player3);
		board.getPlayers().add(player4);

		StackPane primo = stackPanes.get(0);
		Canvas canvas = (Canvas) primo.getChildren().get(1);
		canvas.getGraphicsContext2D().setFill(Color.GREEN);
		canvas.getGraphicsContext2D().fillRect(5, 30, 20, 20);
		canvas.getGraphicsContext2D().setFill(Color.RED);
		canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, 30, 20, 20);
		canvas.getGraphicsContext2D().setFill(Color.BLUE);
		canvas.getGraphicsContext2D().fillRect(5, canvas.getHeight() - 5 - 20, 20, 20);
		canvas.getGraphicsContext2D().setFill(Color.YELLOW);
		canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 5 - 20, 20, 20);
	}

	@FXML
	void continueButtonAction(ActionEvent event) {
		// TODO:caccialo
		boolean prigione = true;
		Player currentPlayer = board.getCurrentPlayer();

		StackPane previous = stackPanes.get(currentPlayer.getPosition());
		Canvas canvas = (Canvas) previous.getChildren().get(1);
		if (currentPlayer.getPosition() >= 0 && currentPlayer.getPosition() <= 9) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(5, 30, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, 30, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(5, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 5 - 20, 20,
						20);
			}
		} else if (currentPlayer.getPosition() == 10 && !prigione) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(0, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(0, canvas.getHeight() - 20 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(20, canvas.getHeight() - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 20, 20, 20);
			}
		} else if (currentPlayer.getPosition() == 10 && prigione) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(20 + 5, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(20 + 5, canvas.getHeight() - 20 - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 20 - 5 - 20,
						20, 20);
			}
		} else if (currentPlayer.getPosition() <= 20) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 30 - 20, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 30 - 20, canvas.getHeight() - 5 - 20, 20,
						20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(5, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(5, 5, 20, 20);
			}
		} else if (currentPlayer.getPosition() <= 30) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 30 - 20, 20,
						20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(5, canvas.getHeight() - 30 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(5, 5, 20, 20);
			}
		} else {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(30, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(30, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 5 - 20, 20,
						20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, 5, 20, 20);
			}
		}

		int steps = Dice.launch();
		currentPlayer.setPosition((currentPlayer.getPosition() + steps) % Board.BOXES);

		canvas.getGraphicsContext2D().setFill(Color.GREEN);
		canvas.getGraphicsContext2D().setFill(Color.RED);
		canvas.getGraphicsContext2D().setFill(Color.BLUE);
		canvas.getGraphicsContext2D().setFill(Color.YELLOW);

		StackPane next = stackPanes.get(currentPlayer.getPosition());
		canvas = (Canvas) next.getChildren().get(1);
		if (currentPlayer.getPosition() >= 0 && currentPlayer.getPosition() <= 9) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().setFill(Color.GREEN);
				canvas.getGraphicsContext2D().fillRect(5, 30, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().setFill(Color.RED);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, 30, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().setFill(Color.BLUE);
				canvas.getGraphicsContext2D().fillRect(5, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().setFill(Color.YELLOW);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 5 - 20, 20, 20);
			}
		} else if (currentPlayer.getPosition() == 10 && !prigione) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().setFill(Color.GREEN);
				canvas.getGraphicsContext2D().fillRect(0, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().setFill(Color.RED);
				canvas.getGraphicsContext2D().fillRect(0, canvas.getHeight() - 20 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().setFill(Color.BLUE);
				canvas.getGraphicsContext2D().fillRect(20, canvas.getHeight() - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().setFill(Color.YELLOW);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 20, 20, 20);
			}
		} else if (currentPlayer.getPosition() == 10 && prigione) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().setFill(Color.GREEN);
				canvas.getGraphicsContext2D().fillRect(20 + 5, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().setFill(Color.RED);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().setFill(Color.BLUE);
				canvas.getGraphicsContext2D().fillRect(20 + 5, canvas.getHeight() - 20 - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().setFill(Color.YELLOW);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 20 - 5 - 20, 20,
						20);
			}
		} else if (currentPlayer.getPosition() <= 20) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().setFill(Color.GREEN);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 30 - 20, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().setFill(Color.RED);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 30 - 20, canvas.getHeight() - 5 - 20, 20,
						20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().setFill(Color.BLUE);
				canvas.getGraphicsContext2D().fillRect(5, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().setFill(Color.YELLOW);
				canvas.getGraphicsContext2D().fillRect(5, 5, 20, 20);
			}
		} else if (currentPlayer.getPosition() <= 30) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().setFill(Color.GREEN);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 30 - 20, 20,
						20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().setFill(Color.RED);
				canvas.getGraphicsContext2D().fillRect(5, canvas.getHeight() - 30 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().setFill(Color.BLUE);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().setFill(Color.YELLOW);
				canvas.getGraphicsContext2D().fillRect(5, 5, 20, 20);
			}
		} else {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().setFill(Color.GREEN);
				canvas.getGraphicsContext2D().fillRect(30, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().setFill(Color.RED);
				canvas.getGraphicsContext2D().fillRect(30, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().setFill(Color.BLUE);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 5 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().setFill(Color.YELLOW);
				canvas.getGraphicsContext2D().fillRect(canvas.getWidth() - 5 - 20, 5, 20, 20);
			}
		}

		board.getBoxes().get(currentPlayer.getPosition()).onAction(currentPlayer);

		if (!(lastEventString.equals(""))) {
			lastEventTextArea.appendText(lastEventString + "\n");
		}
		lastEventString = "";

		player1CashLabel.setText("Cash " + board.getPlayers().get(0).getCash());
		player2CashLabel.setText("Cash " + board.getPlayers().get(1).getCash());
		player3CashLabel.setText("Cash " + board.getPlayers().get(2).getCash());
		player4CashLabel.setText("Cash " + board.getPlayers().get(3).getCash());

		player1PropertiesLabel.setText("Proprietà " + board.getPlayers().get(0).getProperties());
		player2PropertiesLabel.setText("Proprietà " + board.getPlayers().get(1).getProperties());
		player3PropertiesLabel.setText("Proprietà " + board.getPlayers().get(2).getProperties());
		player4PropertiesLabel.setText("Proprietà " + board.getPlayers().get(3).getProperties());

		// altre cose

		for (Player player : board.getPlayers()) {
			if (player.getCash() < 0) {
				lastEventTextArea.appendText("Il gioco è finito");
				continueButton.setDisable(true);
				return;
			}
		}

		board.nextPlayer();

		currentPlayerLabel.setText("Prossimo turno del giocatore " + board.getCurrentPlayer().getName());
	}

}
