package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import boxes.Airport;
import boxes.AirportState;
import boxes.Box;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
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
	private Button endTurnButton;

	@FXML
	private Button exchangeButton;

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
														+ " per la modica cifra di $" + property.getHouseCost() + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().buyHouse(property);
										}
									}
								} else if (result.get() == delHouse) {
									if (property.getState() == PropertyState.ALL_GROUP) {
										Alert confAlert = new Alert(AlertType.ERROR);
										confAlert.setTitle("ERRORE");
										confAlert.setHeaderText(null);
										confAlert.setContentText("Non puoi viendere case, non ce ne sono.");
										confAlert.showAndWait();
									} else {
										Alert confAlert = new Alert(AlertType.CONFIRMATION);
										confAlert.setTitle("VENDITA CASA");
										confAlert.setHeaderText(null);
										confAlert.setContentText(
												"Sei sicuro di voler vendere una casa a " + property.getName()
														+ " per ricavare $" + property.getHouseCost() / 2 + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().sellHouse(property);
										}
									}
								} else if (result.get() == mortage) {
									if (property.getState() != PropertyState.NO_HOUSES
											&& property.getState() != PropertyState.ALL_GROUP
											&& property.getState() != PropertyState.MORTAGED) {
										Alert confAlert = new Alert(AlertType.ERROR);
										confAlert.setTitle("ERRORE");
										confAlert.setHeaderText(null);
										confAlert
												.setContentText("Non puoi ipotecare la proprietà, prima vendi le case");
										confAlert.showAndWait();
									} else if (property.getState() != PropertyState.MORTAGED) {
										Alert confAlert = new Alert(AlertType.CONFIRMATION);
										confAlert.setTitle("IPOTECA PROPRIETA'");
										confAlert.setHeaderText(null);
										confAlert.setContentText("Sei sicuro di voler ipotecare " + property.getName()
												+ " per ricavare $" + property.getValue() / 2 + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().mortageProperty(property);
										}
									} else {
										Alert confAlert = new Alert(AlertType.CONFIRMATION);
										confAlert.setTitle("RIACQUISTA PROPRIETA'");
										confAlert.setHeaderText(null);
										confAlert
												.setContentText("Sei sicuro di voler riacquistare " + property.getName()
														+ " alla modica cifra di $" + property.getValue() / 2 + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().unMortageProperty(property);
										}
									}
								} else {
								}
							} else {
								ButtonType cancel = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);
								alert.getButtonTypes().add(cancel);
								alert.showAndWait();
							}

						} else if (board.getBoxes().get(j) instanceof Airport) {
							Airport airport = (Airport) board.getBoxes().get(j);
							Alert alert = new Alert(AlertType.NONE);
							alert.setTitle(board.getBoxes().get(j).getName());
							alert.setHeaderText(null);
							alert.setContentText("Valore :" + airport.getValue() + "\nProprietario: "
									+ (airport.getOwner() == null ? "Nessuno" : airport.getOwner().getName())
									+ "\nStato: " + airport.getState());

							if (airport.getOwner() != null
									&& airport.getOwner().getName().equals(board.getCurrentPlayer().getName())) {
								ButtonType mortage;
								if (airport.getState() == AirportState.MORTAGED) {
									mortage = new ButtonType("Riacquista: -$" + airport.getValue() / 2);
								} else {
									mortage = new ButtonType("Ipoteca: +$" + airport.getValue() / 2);
								}
								ButtonType cancel = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);

								alert.getButtonTypes().addAll(mortage, cancel);

								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == mortage) {
									if (airport.getState() != AirportState.MORTAGED) {
										Alert confAlert = new Alert(AlertType.CONFIRMATION);
										confAlert.setTitle("IPOTECA AEREOPORTO");
										confAlert.setHeaderText(null);
										confAlert.setContentText("Sei sicuro di voler ipotecare " + airport.getName()
												+ " per ricavare $" + airport.getValue() / 2 + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().mortageAirport(airport);
										}
									} else {
										Alert confAlert = new Alert(AlertType.CONFIRMATION);
										confAlert.setTitle("RIACQUISTA AEREOPORTO");
										confAlert.setHeaderText(null);
										confAlert.setContentText("Sei sicuro di voler riacquistare " + airport.getName()
												+ " alla modica cifra di $" + airport.getValue() / 2 + "?");
										Optional<ButtonType> confResult = confAlert.showAndWait();
										if (confResult.get() == ButtonType.OK) {
											board.getCurrentPlayer().unMortageAirport(airport);
										}
									}
								} else {
								}
							} else {
								ButtonType cancel = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);
								alert.getButtonTypes().add(cancel);
								alert.showAndWait();
							}
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

		if (board.getCurrentPlayer().isHuman()) {
			exchangeButton.setVisible(true);
		} else {
			exchangeButton.setVisible(false);
		}
	}

	@FXML
	void exchangeButtonAction(ActionEvent event) {
		List<String> choices = new ArrayList<>();
		for (Player other : board.getPlayers()) {
			if (!(other.getName().equals(board.getCurrentPlayer().getName()))) {
				choices.add(other.getName());
			}
		}

		ChoiceDialog<String> dialog = new ChoiceDialog<String>(choices.get(0), choices);
		dialog.setTitle("SCEGLI GIOCATORE");
		dialog.setHeaderText(null);
		dialog.setContentText("Scegli il giocatore con il quale effettuare uno scambio");

		Player otherPlayer = null;
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			for (Player other : board.getPlayers()) {
				if (other.getName().equals(result.get())) {
					otherPlayer = other;
					break;
				}
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("LA MIA OFFERTA");
			alert.setHeaderText(null);
			alert.setContentText(null);
			VBox vBox = new VBox();
			TextField cash = new TextField("0");
			vBox.getChildren().add(cash);
			for (Box box : board.getBoxes()) {
				if (box instanceof Property) {
					Property property = (Property) box;
					if (property.getOwner() != null
							&& property.getOwner().getName().equals(board.getCurrentPlayer().getName())) {
						vBox.getChildren().add(new CheckBox(property.getName() + " - $" + property.getValue()));
					}
				} else if (box instanceof Airport) {
					Airport airport = (Airport) box;
					if (airport.getOwner() != null
							&& airport.getOwner().getName().equals(board.getCurrentPlayer().getName())) {
						vBox.getChildren().add(new CheckBox(airport.getName() + " - $" + airport.getValue()));
					}
				}
			}
			alert.getDialogPane().setContent(vBox);
			Optional<ButtonType> alertResult = alert.showAndWait();
			if (alertResult.get() == ButtonType.OK) {
				if (!(Pattern.matches("\\d+", cash.getText()))) {
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("ERRORE");
					error.setHeaderText(null);
					error.setContentText("Errore, inserisci numeri");
					error.showAndWait();
					return;
				} else if (Integer.parseInt(cash.getText()) > board.getCurrentPlayer().getCash()) {
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("ERRORE");
					error.setHeaderText(null);
					error.setContentText("Errore, non puoi offrire più soldi di quanti ne hai");
					error.showAndWait();
				}
				Alert otherAlert = new Alert(AlertType.CONFIRMATION);
				otherAlert.setTitle("L'OFFERTA DI " + result.get());
				otherAlert.setHeaderText(null);
				otherAlert.setContentText(null);
				VBox otherVBox = new VBox();
				TextField otherCash = new TextField("0");
				otherVBox.getChildren().add(otherCash);
				for (Box box : board.getBoxes()) {
					if (box instanceof Property) {
						Property property = (Property) box;
						if (property.getOwner() != null
								&& property.getOwner().getName().equals(otherPlayer.getName())) {
							otherVBox.getChildren()
									.add(new CheckBox(property.getName() + " - $" + property.getValue()));
						}
					} else if (box instanceof Airport) {
						Airport airport = (Airport) box;
						if (airport.getOwner() != null && airport.getOwner().getName().equals(otherPlayer.getName())) {
							otherVBox.getChildren().add(new CheckBox(airport.getName() + " - $" + airport.getValue()));
						}
					}
				}
				otherAlert.getDialogPane().setContent(otherVBox);
				Optional<ButtonType> finalResult = otherAlert.showAndWait();
				if (finalResult.get() == ButtonType.OK) {
					if (!(Pattern.matches("\\d+", otherCash.getText()))) {
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("ERRORE");
						error.setHeaderText(null);
						error.setContentText("Errore, inserisci numeri");
						error.showAndWait();
						return;
					} else if (Integer.parseInt(cash.getText()) > board.getCurrentPlayer().getCash()) {
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("ERRORE");
						error.setHeaderText(null);
						error.setContentText(
								"Errore, " + otherPlayer.getName() + " non può offrire più soldi di quanti ne ha");
						error.showAndWait();
					}
					int myCashOffer = Integer.parseInt(cash.getText());
					int hisCashOffer = Integer.parseInt(otherCash.getText());
					ArrayList<Property> myProperties = new ArrayList<>();
					ArrayList<Airport> myAirports = new ArrayList<>();
					if (vBox.getChildren().size() > 1) {
						for (int i = 1; i < vBox.getChildren().size(); i++) {
							CheckBox checkBox = (CheckBox) vBox.getChildren().get(i);
							if (checkBox.isSelected()) {
								for (Box box : board.getBoxes()) {
									if (box instanceof Property && checkBox.getText().contains(box.getName())) {
										Property property = (Property) box;
										myProperties.add(property);
										break;
									} else if (box instanceof Airport && checkBox.getText().contains(box.getName())) {
										Airport airport = (Airport) box;
										myAirports.add(airport);
										break;
									}
								}
							}
						}
					}
					ArrayList<Property> hisProperties = new ArrayList<>();
					ArrayList<Airport> hisAirports = new ArrayList<>();
					if (otherVBox.getChildren().size() > 1) {
						for (int i = 1; i < otherVBox.getChildren().size(); i++) {
							CheckBox checkBox = (CheckBox) otherVBox.getChildren().get(i);
							if (checkBox.isSelected()) {
								for (Box box : board.getBoxes()) {
									if (box instanceof Property && checkBox.getText().contains(box.getName())) {
										Property property = (Property) box;
										hisProperties.add(property);
										break;
									} else if (box instanceof Airport && checkBox.getText().contains(box.getName())) {
										Airport airport = (Airport) box;
										hisAirports.add(airport);
										break;
									}
								}
							}
						}
					}
					System.out.println(myProperties);
					System.out.println(myAirports);
					System.out.println(hisProperties);
					System.out.println(hisAirports);

					if (otherPlayer.manageExchange(myCashOffer, hisCashOffer, myProperties, myAirports, hisProperties,
							hisAirports)) {
						Alert finalAlert = new Alert(AlertType.INFORMATION);
						finalAlert.setTitle("OFFERTA ACCETTATA");
						finalAlert.setHeaderText(null);
						finalAlert.setContentText(
								"Il giocatore " + otherPlayer.getName() + " ha accettato la tua offerta");
						finalAlert.showAndWait();
					} else {
						Alert finalAlert = new Alert(AlertType.INFORMATION);
						finalAlert.setTitle("OFFERTA RIFIUTATA");
						finalAlert.setHeaderText(null);
						finalAlert.setContentText(
								"Il giocatore " + otherPlayer.getName() + " ha rifiutato la tua offerta");
						finalAlert.showAndWait();
					}
				}
			}
		}
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
