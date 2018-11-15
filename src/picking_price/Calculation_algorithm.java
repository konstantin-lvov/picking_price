package picking_price;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Calculation_algorithm {
	private TextArea res = new TextArea();
	private Order order = new Order();
	private ArrayList<Stuff> stuffArr = new ArrayList<Stuff>();
	private Stuff currentStuff = new Stuff("name", false, 1.0);
	Output_of_results oor = new Output_of_results();
	private double interval;
	private String nameBiggestPrice;

	public void calcAlg(ArrayList<Stuff> stuffArr, Order order, TextArea res) {
		this.order = order;
		this.stuffArr = stuffArr;
		this.res = res;

		for (int i = 0; i < stuffArr.size(); i++) {
			interval = order.getDifference() / (stuffArr.size() - i);
			biggestPrice();
			defineStuff();
			currentStuff.setCheck(false);
			calcAmount();

		}

		oor.outputOfResults(res, stuffArr, order);

	}

	// ìåòîä ñ÷èòàþùèé êîëè÷åñòâî
	public void calcAmount() {
		if (currentStuff.isCanDivide() == true) {

			double tempHowMuch = (double) Math.floor((interval / currentStuff.getPrice()) * 100) / 100;// îêðóãëåíî äî 2
																										// çíàêîâ ï. çïò
			currentStuff.setHowMuch(tempHowMuch);
			order.setDifference(
					(double) Math.round((order.getDifference() * 100 - (tempHowMuch * currentStuff.getPrice()) * 100))
							/ 100);
			if (!isThereStill()) {
				currentStuff.setLastStuff(true);
			}

		} else {
			double tempCost = 0;
			while (tempCost <= interval) {
				if(currentStuff.getPrice() > order.getDifference()) {
					break;
				}
				tempCost = tempCost + currentStuff.getPrice();
				currentStuff.setHowMuch(currentStuff.getHowMuch() + 1);

				if ((tempCost + currentStuff.getPrice()) > interval) {
					break;
				}
			}
			order.setDifference((double) Math.round((order.getDifference() * 100 - tempCost * 100)) / 100);
		}
	}

	// ìåòîä íàõîäèò òîâàð ñ íàèáîëüøåé öåíîé
	public void biggestPrice() {
		double price = 0;

		for (Stuff s : stuffArr) {
			if (price < s.getPrice() & s.isCheck() == true & s.isCanDivide() == false) {
				nameBiggestPrice = s.getName();
				price = s.getPrice();

			}
		}

		if (price == 0) {
			for (Stuff s : stuffArr) {
				if (price < s.getPrice() & s.isCheck() == true & s.isCanDivide() == true) {
					nameBiggestPrice = s.getName();
					price = s.getPrice();

				}
			}

		}
		if (price == 0) {
			nameBiggestPrice = "nothing";
		}

	}

	// ìåòîä âûêëþ÷àåò ÷åê
	public void defineStuff() {

		for (Stuff s : stuffArr) {
			if (nameBiggestPrice.equals(s.getName())) {
				currentStuff = s;
			}
		}
	}

	// ìåòîä îïðåäåëÿåò åñòü ëè åùå íå ïîñ÷èòàíûé òîâàð
	public boolean isThereStill() {
		boolean its = false;
		for (Stuff s : stuffArr) {
			if (s.isCheck() == true) {
				its = true;
			}
		}
		return its;
	}

}