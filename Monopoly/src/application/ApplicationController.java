package application;

import java.util.ArrayList;
import java.util.Optional;

import boxes.Airport;
import boxes.Property;
import boxes.PropertyState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	private Button endTurnButton;

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

		for (int i = 0; i < stackPanes.size(); i++) {
			final int j = i;
			stackPanes.get(i).setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					if (board.getCurrentPlayer().isHuman()) {
						if (board.getBoxes().get(j) instanceof Property) {
							Property property = (Property) board.getBoxes().get(j);
							Alert alert = new Alert(AlertType.NONE);
							alert.setTitle(board.getBoxes().get(j).getName());
							alert.setHeaderText(null);
							alert.setContentText("Valore :" + property.getValue() + "\nProprietario: "
									+ (property.getOwner() == null ? "Nessuno" : property.getOwner().getName())
									+ "\nStato: " + property.getState());

							if (property.getOwner() != null
									&& property.getOwner().getName().equals(board.getCurrentPlayer().getName())) {
								ButtonType addHouse = null;
								ButtonType delHouse = null;
								if (property.getState() != PropertyState.MORTAGED
										&& property.getState() != PropertyState.NO_HOUSES) {
									addHouse = new ButtonType("Aggiungi casa: -$" + property.getHouseCost());
									delHouse = new ButtonType("Rimuovi casa: +$" + property.getHouseCost() / 2);
									alert.getButtonTypes().addAll(addHouse, delHouse);
								}
								ButtonType mortage;
								if (property.getState() == PropertyState.MORTAGED) {
									mortage = new ButtonType("Riacquista: -$" + property.getValue() / 2);
								} else {
									mortage = new ButtonType("Ipoteca: +$" + property.getValue() / 2);
								}
								ButtonType cancel = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);

								alert.getButtonTypes().addAll(mortage, cancel);

								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == addHouse) {
									if (property.getState() == PropertyState.ONE_HOTEL) {
										Alert confAlert = new Alert(AlertType.ERROR);
										confAlert.setTitle("ERRORE");
										confAlert.setHeaderText(null);
										confAlert.setContentText("Non puoi aggiungere altre case, ce ne sono troppe.");
										confAlert.showAndWait();
									} else if (board.getCurrentPlayer().getCash() < property.getHouseCost()) {
										Alert confAlert = new Alert(AlertType.ERROR);
										confAlert.setTitle("ERRORE");
										confAlert.setHeaderText(null);
										confAlert.setContentText(
												"Non puoi aggiungere una casa, non hai abbastanza soldi.");
										confAlert.showAndWait();
									} else {
										Alert confAlert = new Alert(AlertType.CONFIRMATION);
										confAlert.setTitle("ACQUISTO CASA");
										confAlert.setHeaderText(null);
										confAlert.setContentText(
												"Sei sicuro di voler acquistare una casa a " + property.getName()
														+ " per la modica cifra di " + property.getHouseCost() + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().buyHouse(property);
										}
									}
								} else if (result.get() == delHouse) {
									Alert confAlert = new Alert(AlertType.ERROR);
									confAlert.setTitle("ERRORE");
									confAlert.setHeaderText(null);
									confAlert.setContentText("Non puoi aggiungere altre 2, ce ne sono troppe.");
									confAlert.showAndWait();
								} else if (result.get() == mortage) {
									Alert confAlert = new Alert(AlertType.ERROR);
									confAlert.setTitle("ERRORE");
									confAlert.setHeaderText(null);
									confAlert.setContentText("Non puoi aggiungere altre 3, ce ne sono troppe.");
									confAlert.showAndWait();
								} else {
								}
							} else {
								ButtonType cancel = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);
								alert.getButtonTypes().add(cancel);
								alert.showAndWait();
							}

						} else if (board.getBoxes().get(j) instanceof Airport) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle(board.getBoxes().get(j).getName());
							alert.setHeaderText(null);
							alert.setContentText("qualcosa di articolato con botttoni ecc");
							alert.showAndWait();
						}
					}
					refresh();
				}
			});
		}

		endTurnButton.setDisable(true);

	}

	@FXML
	void continueButtonAction(ActionEvent event) {
		Player currentPlayer = board.getCurrentPlayer();
		int previousPosition = currentPlayer.getPosition();

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
		} else if (currentPlayer.getPosition() == 10 && currentPlayer.getDaysInPrison() == 0) {
			if (currentPlayer.getName().equals(player1Name)) {
				canvas.getGraphicsContext2D().clearRect(0, 5, 20, 20);
			} else if (currentPlayer.getName().equals(player2Name)) {
				canvas.getGraphicsContext2D().clearRect(0, canvas.getHeight() - 20 - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player3Name)) {
				canvas.getGraphicsContext2D().clearRect(20, canvas.getHeight() - 20, 20, 20);
			} else if (currentPlayer.getName().equals(player4Name)) {
				canvas.getGraphicsContext2D().clearRect(canvas.getWidth() - 5 - 20, canvas.getHeight() - 20, 20, 20);
			}
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

		if (currentPlayer.getDaysInPrison() == 0) {
			int steps = Dice.launch();
			currentPlayer.setPosition((currentPlayer.getPosition() + steps) % Board.BOXES);
		}

		if (currentPlayer.getPosition() == 30) {
			StackPane next = stackPanes.get(currentPlayer.getPosition());
			canvas = (Canvas) next.getChildren().get(1);
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
			board.getBoxes().get(currentPlayer.getPosition()).onAction(currentPlayer);
		}

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
		} else if (currentPlayer.getPosition() == 10 && currentPlayer.getDaysInPrison() == 0) {
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
		} else if (currentPlayer.getPosition() == 10) {
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

		if (previousPosition > currentPlayer.getPosition() && currentPlayer.getDaysInPrison() == 0) {
			lastEventTextArea.appendText("[" + currentPlayer.getName() + "] Il giocatore " + currentPlayer.getName()
					+ " è passato dal VIA e ritira $200!\n");
			currentPlayer.pickFromBank(200);
		}

		board.getBoxes().get(currentPlayer.getPosition()).onAction(currentPlayer);

		if (!(lastEventString.equals(""))) {
			lastEventTextArea.appendText("[" + currentPlayer.getName() + "] " + lastEventString + "\n");
		}
		lastEventString = "";

		refresh();

		currentPlayerLabel.setText("Turno del giocatore " + board.getCurrentPlayer().getName());

		endTurnButton.setDisable(false);
		continueButton.setDisable(true);

		for (Player player : board.getPlayers()) {
			if (player.getCash() < 0) {
				lastEventTextArea.appendText("Il gioco è finito");
				continueButton.setDisable(true);
				return;
			}
		}
		
		// TODO L'itelligenza fa scambi, compra case ecc
	}

	@FXML
	void endTurnButtonAction(ActionEvent event) {
		board.nextPlayer();

		currentPlayerLabel.setText("Prossimo turno del giocatore " + board.getCurrentPlayer().getName());

		endTurnButton.setDisable(true);
		continueButton.setDisable(false);
	}

	private void refresh() {
		player1CashLabel.setText("Cash " + board.getPlayers().get(0).getCash());
		player2CashLabel.setText("Cash " + board.getPlayers().get(1).getCash());
		player3CashLabel.setText("Cash " + board.getPlayers().get(2).getCash());
		player4CashLabel.setText("Cash " + board.getPlayers().get(3).getCash());

		player1PropertiesLabel.setText("Proprietà " + board.getPlayers().get(0).getProperties());
		player2PropertiesLabel.setText("Proprietà " + board.getPlayers().get(1).getProperties());
		player3PropertiesLabel.setText("Proprietà " + board.getPlayers().get(2).getProperties());
		player4PropertiesLabel.setText("Proprietà " + board.getPlayers().get(3).getProperties());
	}

}
