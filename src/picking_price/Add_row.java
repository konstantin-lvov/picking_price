package picking_price;

import java.util.StringTokenizer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Add_row {
	private Stage stage = new Stage();
	private StringTokenizer strt;
	private Delete_row dr = new Delete_row();
	private String tempName;
	private double tempPrice = 0;
	private boolean tempDivide;
	private boolean nameFail = false;
	private boolean priceFail = false;
	private int count = 0;
	private int rowInd;
	Label lName = new Label("Название: ");
	TextField tfName = new TextField();
	Label lPrice = new Label("Цена за шт.: ");
	TextField tfPrice = new TextField();
	Label lDivide = new Label("Весовой товар: ");
	CheckBox cbDivide = new CheckBox();

	public void addRowWindow(ObservableList<Stuff> ob, Stage st, TableView table, int ri) {
		rowInd = ri;

		VBox root = new VBox();

		tfName.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obv, String oldValue, String newValue) {
				try {
					nameFail = false;
					tfName.setStyle("-fx-text-inner-color: black;");
					tempName = newValue;
				} catch (Exception e) {
					nameFail = true;
					tfName.setStyle("-fx-text-inner-color: red;");
				}
			}
		});
		tfPrice.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obv, String oldValue, String newValue) {
				try {
					priceFail = false;
					tfPrice.setStyle("-fx-text-inner-color: black;");
					tempPrice = Double.parseDouble(newValue);
				} catch (Exception e) {
					priceFail = true;
					tfPrice.setStyle("-fx-text-inner-color: red;");
				}
			}
		});
		cbDivide.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (cbDivide.isSelected()) {
					tempDivide = true;
				} else {
					tempDivide = false;
				}
			}
		});

		Button ok = new Button("Ok");
		Button cancel = new Button("Cancel");

		ok.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					strt = new StringTokenizer(tempName, " ");
					while (strt.hasMoreTokens()) {
						strt.nextToken();
						count++;
						if (count > 1) {
							count = 0;
							nameFail = true;
							throw new Exception();
						}
					}

					if (!tempName.isEmpty() & tempPrice != 0) {
						ob.add(new Stuff(tempName, tempDivide, tempPrice));
						dr.deleteRowFromFile(ob);
						count = 0;
						stage.hide();
					} else {
						if (tempName.isEmpty()) {
							nameFail = true;
						}
						if (tempPrice != 0) {
							priceFail = true;
						}
						throw new Exception();
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					if (nameFail) {
						tfName.setStyle("-fx-text-inner-color: red;");
					}
					if (priceFail) {
						tfPrice.setStyle("-fx-text-inner-color: red;");
					}
				}
			}
		});
		cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				stage.hide();
			}
		});
		VBox first = new VBox();
		first.getChildren().addAll(lName, tfName);
		first.setPadding(new Insets(10));
		VBox second = new VBox();
		second.getChildren().addAll(lPrice, tfPrice);
		second.setPadding(new Insets(10));
		HBox third = new HBox();
		third.getChildren().addAll(lDivide, cbDivide);
		third.setPadding(new Insets(10));
		HBox forth = new HBox();
		forth.getChildren().addAll(ok, cancel);
		forth.setPadding(new Insets(10));
		forth.setSpacing(10);

		root.getChildren().addAll(first, second, third, forth);
		Scene scene = new Scene(root, 300, 250);
		stage.initOwner(st);
		stage.setTitle("Добавить");
		stage.setScene(scene);
		stage.initModality(Modality.WINDOW_MODAL);

	}

	public void showSecondWindow(ObservableList<Stuff> ob, int ri) {
		tempName = "";
		tempPrice = 0;
		tempDivide = false;
		tfName.setText("");
		tfPrice.setText("");
		cbDivide.setSelected(false);

		rowInd = ri;
		stage.show();
	}

}//
