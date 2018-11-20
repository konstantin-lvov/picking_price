package picking_price;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Output_of_results {

	public void outputOfResults(TextArea res, ArrayList<Stuff> stuffArr, Order order) {

		String temp = String.format("%s        | %s | %s | %s%n", "Название", "Цена за 1 шт", "Количество", "Стоимость");
		res.appendText(temp);

		for (int i = 0; i < stuffArr.size(); i++) {
			if (stuffArr.get(i).isLastStuff()) {
				double cost = (double) Math.round((stuffArr.get(i).getHowMuch() * stuffArr.get(i).getPrice()) * 100) / 100;
				temp = String.format("%-18s%-20.2f%-20.2f%.2f + %.2f = %.2f %n", stuffArr.get(i).getName(),
						stuffArr.get(i).getPrice(), stuffArr.get(i).getHowMuch(),
						cost, order.getDifference(), (cost+order.getDifference()));
				res.appendText(temp);
				stuffArr.get(i).setLastStuff(false);
			} else {
				temp = String.format("%-18s%-20.2f%-20.2f%-20.2f%n", stuffArr.get(i).getName(),
						stuffArr.get(i).getPrice(), stuffArr.get(i).getHowMuch(),
						(double) Math.round((stuffArr.get(i).getHowMuch() * stuffArr.get(i).getPrice()) * 100) / 100);
				res.appendText(temp);
			}
		}
		res.appendText("================================================\n");
	}

}//
