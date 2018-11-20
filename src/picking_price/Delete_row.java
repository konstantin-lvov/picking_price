package picking_price;

import java.io.File;
import java.io.FileWriter;

import javafx.collections.ObservableList;

public class Delete_row {
	
	public void deleteRowFromFile(ObservableList <Stuff> ob) {
		try {
			File file = new File("stuff.txt");
			file.delete();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			for(int i = 0; i < ob.size(); i++) {
				fw.append(i + " " + ob.get(i).getName() + " " + ob.get(i).isCanDivide() + " " + ob.get(i).getPrice());
				fw.append("\r\n");
			}
			fw.flush();
			fw.close();
		}catch (Exception e) {
			
		}
	}
	
}
