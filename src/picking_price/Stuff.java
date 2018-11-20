package picking_price;

import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Stuff {
	private boolean check = false;
	private String name;
	private boolean canDivide;
	private double price;
	private double howMuch = 0;
	private boolean lastStuff = false;
	
	public Stuff(String name, boolean canDivide, double price) {
		super();
		this.name = name;
		this.canDivide = canDivide;
		this.price = price;
	}

	public String readFromFile(String id, int lineValue) throws Exception {
		int line = 0;
		String temp = "";
		String tempLine = "";
		String tempTok = "";
		FileReader fr = new FileReader("stuff.txt");
		Scanner scan = new Scanner(fr);

		while (scan.hasNextLine()) {
			tempLine = scan.nextLine();
			StringTokenizer strt = new StringTokenizer(tempLine, " ");
			int count = 0;
			while (strt.hasMoreTokens()) {

				if (count == 0) {
					line = Integer.parseInt(strt.nextToken());
					count++;
					continue;
				}
				if (count == 1) {
					name = strt.nextToken();
					count++;
					continue;
				}
				if (count == 2) {
					tempTok = strt.nextToken();
					if (tempTok.equals("false")) {
						canDivide = false;
						count++;
						continue;
					} else {
						canDivide = true;
						count++;
						continue;
					}
				}
				if (count == 3) {
					price = Double.parseDouble(strt.nextToken());
					count++;
					continue;
				}
			}

			if (id.equals("name")) {
				temp = name;
			}
			if (id.equals("canDivide")) {
				temp = String.valueOf(canDivide);
			}
			if (id.equals("price")) {
				temp = String.valueOf(price);
			}
			if(line == lineValue) {
				break;
			}
		}
		

		fr.close();
		scan.close();
		return temp;
	}

	public void addToFile(String id, String value) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanDivide() {
		return canDivide;
	}

	public void setCanDivide(boolean canDivide) {
		this.canDivide = canDivide;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public double getHowMuch() {
		return howMuch;
	}

	public void setHowMuch(double howMuch) {
		this.howMuch = howMuch;
	}

	public boolean isLastStuff() {
		return lastStuff;
	}

	public void setLastStuff(boolean lastStuff) {
		this.lastStuff = lastStuff;
	}
}//
