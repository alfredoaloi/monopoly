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

	private ArrayList<StackPane> stackPanes;

	private Board board;

	private String player1Name = "1";
	private String player2Name = "2";
	private String player3Name = "3";
	private String player4Name = "4";

	@FXML
	void initialize() {
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

		board = new Board();
		board.createBoard();
		Player player1 = new Player(player1Name, 1500);
		Player player2 = new Player(player2Name, 1500);
		Player player3 = new Player(player3Name, 1500);
		Player player4 = new Player(player4Name, 1500);
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

		// altre cose

		board.nextPlayer();
	}
}
