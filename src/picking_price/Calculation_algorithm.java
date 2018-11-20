package picking_price;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

public class Calculation_algorithm {
	private TextArea res = new TextArea();
	private Order order = new Order();
	private ArrayList<Stuff> stuffArr = new ArrayList<Stuff>();
	private Stuff currentStuff = new Stuff("name", false, 1.0);
	Output_of_results oor = new Output_of_results();
	private double interval;
	private String nameBiggestPrice;
	private double minValue;

	public void calcAlg(ArrayList<Stuff> stuffArr, Order order, TextArea res, boolean stuffPriority, double minValue) {
		this.order = order;
		this.stuffArr = stuffArr;
		this.res = res;
		this.minValue = minValue;

		for (int i = 0; i < stuffArr.size(); i++) {

			biggestPrice();
			defineStuff();
			if (stuffPriority == true) {
				if (currentStuff.isCanDivide()) {
					interval = order.getDifference() / (stuffArr.size() - i);
				} else {
					interval = order.getDifference() - intervalFix();
				}
			} else {
				interval = order.getDifference() / (stuffArr.size() - i);
			}
			currentStuff.setCheck(false);
			calcAmount();

		}

		oor.outputOfResults(res, stuffArr, order);

	}

	public void calcAmount() {
		if (currentStuff.isCanDivide() == true) {

			double tempHowMuch = (double) Math.floor((interval / currentStuff.getPrice()) * 100) / 100;
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
				if (currentStuff.getPrice() > order.getDifference()) {
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

	public void defineStuff() {

		for (Stuff s : stuffArr) {
			if (nameBiggestPrice.equals(s.getName())) {
				currentStuff = s;
			}
		}
	}

	public boolean isThereStill() {
		boolean its = false;
		for (Stuff s : stuffArr) {
			if (s.isCheck() == true) {
				its = true;
				break;
			}
		}
		return its;
	}

	public double intervalFix() {
		double fix = 0;
		for (Stuff s : stuffArr) {
			if (s.isCheck() == true) {

				if (s.isCanDivide() == true) {
					fix += minValue;
				} else {
					boolean temp = true;
					double percent = 100;
					while (temp) {

						if ((order.getDifference() / 100) * percent < minValue) {
							fix += (order.getDifference() / 100) * percent;
							temp = false;
							break;
						} else {
							percent = percent - (percent / 100) * 20;
						}
					}
				}
			}
		}
		return fix;
	}

}
