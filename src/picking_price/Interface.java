package picking_price;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Interface extends Application {

	private Order order = new Order();
	private ObservableList<Stuff> ob = FXCollections.observableArrayList();
	private TableView table = new TableView();
	private Add_row Ar = new Add_row();
	private Edit_row Er = new Edit_row();
	private Delete_row Dr = new Delete_row();
	private boolean failMustField = false;
	private boolean failActuallyField = false;
	private int stuffAmount = 0;
	private int rowInd = 0;
	private int addCount = 0;
	private int editCount = 0;
	private boolean stuffPriority = false;
	private double minValue = 0;
	private boolean failMinValue = false;

	public static void main(String[] args) {
			launch(args);

	}

	Stuff readStuff = new Stuff("1", false, 1.2);

	@Override
	public void start(Stage stage) throws Exception {
		try {
		FileReader fr = new FileReader("stuff.txt");
		Scanner scan = new Scanner(fr);

		while (scan.hasNextLine()) {
			scan.nextLine();
			stuffAmount++;
		}
		fr.close();
		scan.close();
		for (int i = 0; i < stuffAmount; i++) {

			ob.add(new Stuff(readStuff.readFromFile("name", i), Boolean.valueOf(readStuff.readFromFile("canDivide", i)),
					Double.valueOf(readStuff.readFromFile("price", i))));
		}
		} catch (Exception e) {
			File file = new File("stuff.txt");
			file.createNewFile();
		}

		order.setDifference(0);
		Label diffLabel = new Label("Разница: " + order.getDifference());

		HBox boxOne = new HBox();
		TextField mustField = new TextField();
		Label mustLabel = new Label(" - какую сумму нужно получить?");
		mustLabel.setFont(new Font("Arial", 16));
		boxOne.getChildren().addAll(mustField, mustLabel);
		boxOne.setMargin(mustLabel, new Insets(5, 5, 5, 5));

		

		HBox boxTwo = new HBox();
		TextField actuallyField = new TextField();
		Label actuallyLabel = new Label(" - какая сумма есть на данный момент?");
		actuallyLabel.setFont(new Font("Arial", 16));
		boxTwo.getChildren().addAll(actuallyField, actuallyLabel);
		boxTwo.setMargin(actuallyLabel, new Insets(5, 5, 5, 5));
		
		HBox boxThree = new HBox();

		final Label label = new Label("Товары для подбора:");
		label.setFont(new Font("Arial", 20));

		label.setMinWidth(260);

		Button add = new Button("add");
		add.setMinWidth(100);
		Button edit = new Button("edit");
		edit.setMinWidth(100);
		Button delete = new Button("delete");
		delete.setMinWidth(100);

		add.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!table.getSelectionModel().isEmpty()) {
					rowInd = table.getSelectionModel().getSelectedIndex();
				}
				if (addCount == 0) {
					Ar.addRowWindow(ob, stage, table, rowInd);
					addCount++;
				}
				Ar.showSecondWindow(ob, rowInd);
			}
		});
		edit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!table.getSelectionModel().isEmpty()) {
					rowInd = table.getSelectionModel().getSelectedIndex();
				}
				if (editCount == 0) {
					Er.editRowWindow(ob, stage, table, rowInd);
					editCount++;
				}
				Er.showSecondWindow(ob, rowInd);
			}
		});
		delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rowInd = table.getSelectionModel().getSelectedIndex();
				ob.remove(rowInd);
				Dr.deleteRowFromFile(ob);
			}
		});

		boxThree.getChildren().addAll(label, add, edit, delete);

		table.setEditable(true);
		table.setMaxHeight(200);

		TableColumn select = new TableColumn("Выбрать");
		select.setMinWidth(142);
		TableColumn name = new TableColumn("Название");
		name.setMinWidth(230);
		name.setStyle("-fx-alignment: CENTER;");
		TableColumn price = new TableColumn("Цена за 1 шт.");
		price.setMinWidth(200);
		price.setStyle("-fx-alignment: CENTER;");

		select.setCellValueFactory(new Callback<CellDataFeatures<Stuff, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(CellDataFeatures<Stuff, Boolean> param) {
				Stuff stuff = param.getValue();
				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(stuff.isCheck());

				booleanProp.addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						stuff.setCheck(newValue);
					}
				});
				return booleanProp;
			}
		});
		select.setCellFactory(new Callback<TableColumn<Stuff, Boolean>, //
				TableCell<Stuff, Boolean>>() {
			@Override
			public TableCell<Stuff, Boolean> call(TableColumn<Stuff, Boolean> p) {
				CheckBoxTableCell<Stuff, Boolean> cell = new CheckBoxTableCell<Stuff, Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});

		name.setCellValueFactory(new PropertyValueFactory<Stuff, String>("name"));
		price.setCellValueFactory(new PropertyValueFactory<Stuff, String>("price"));
		table.setItems(ob);

		table.getColumns().addAll(select, name, price);
		Label noData = new Label("Таблица пуста.");
		table.setPlaceholder(noData);

		Button calc = new Button("Посчитать");
		
		HBox boxFour = new HBox();
		boxFour.setSpacing(5);
		boxFour.setMinHeight(35);
		CheckBox cbPriority = new CheckBox();
		Label lPriority = new Label("Отдавать приоритет товару с большей ценой");
		TextField tfMinValue = new TextField ();
		tfMinValue.setMaxWidth(60);
		tfMinValue.setText(String.valueOf(minValue));
		Label lMinValue = new Label(" - min на каждую позицию\nрекомендуется 10% от разницы");
		boxFour.getChildren().addAll(cbPriority, lPriority, tfMinValue, lMinValue);
		
		cbPriority.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(cbPriority.isSelected()) {
					stuffPriority = true;
				} else {
					stuffPriority = false;
				}
			}
		});
		
		tfMinValue.textProperty().addListener(new ChangeListener <String>() {
			@Override
			public void changed(ObservableValue <? extends String> obv, String oldValue, String newValue) {
				try {
					failMinValue = false;
					tfMinValue.setStyle("-fx-text-inner-color: black;");
					minValue = Double.parseDouble(newValue);
				} catch (Exception e) {
					tfMinValue.setStyle("-fx-text-inner-color: red;");
					failMinValue = true;
				}
			}
		});
		
		mustField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obv, String oldValue, String newValue) {
				try {
					failMustField = false;
					mustField.setStyle("-fx-text-inner-color: black;");
					order.setMustToBe(Double.parseDouble(newValue));
					order.setDifference(
							(double) Math.round((order.getMustToBe() * 100 - order.getActualyHave() * 100)) / 100);
					diffLabel.setText("Разница: " + String.valueOf(order.getDifference()));
					tfMinValue.setText(String.valueOf((double)Math.round(order.getDifference()/10)));
					minValue = (double) Math.round(order.getDifference()/10);
				} catch (Exception e) {
					mustField.setStyle("-fx-text-inner-color: red;");
					failMustField = true;
				}
			}
		});
		
		actuallyField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obv, String oldValue, String newValue) {
				try {
					failActuallyField = false;
					actuallyField.setStyle("-fx-text-inner-color: black;");
					order.setActualyHave(Double.parseDouble(newValue));
					order.setDifference(
							(double) Math.round((order.getMustToBe() * 100 - order.getActualyHave() * 100)) / 100);
					diffLabel.setText("Разница: " + String.valueOf(order.getDifference()));
					tfMinValue.setText(String.valueOf( (double) Math.round(order.getDifference()/10)));
					minValue = (double) Math.round(order.getDifference()/10);
				} catch (Exception e) {
					failActuallyField = true;
					actuallyField.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		TextArea res = new TextArea();
		res.setEditable(false);
		res.setMinHeight(200);
		res.setMaxHeight(200);

		calc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (failMustField | failActuallyField) {
					res.appendText("Исправь все ошибки и попробую снова!\n");
				} else {
					Calculation_algorithm cA = new Calculation_algorithm();
					ArrayList<Stuff> stuffArr = new ArrayList<Stuff>();
					for (Stuff s : ob) {

						if (s.isCheck() == true) {
							stuffArr.add(s);
						}

					}

					cA.calcAlg(stuffArr, order, res, stuffPriority, minValue);
					for(Stuff s: ob) {
						s.setCheck(false);
						s.setHowMuch(0);
					}
					table.refresh();
					order.setDifference(
							(double) Math.round((order.getMustToBe() * 100 - order.getActualyHave() * 100)) / 100);
					diffLabel.setText("Разница: " + String.valueOf(order.getDifference()));

				}
			}

		});

		final VBox root = new VBox();
		root.setSpacing(5);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.getChildren().addAll(boxOne, boxTwo, diffLabel, boxThree, table, boxFour, calc, res);

		Scene scene = new Scene(root);
		stage.setIconified(true);
		stage.setTitle("WMBI - what must be includ");
		stage.setWidth(600);
		stage.setHeight(600);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

	}

}//
