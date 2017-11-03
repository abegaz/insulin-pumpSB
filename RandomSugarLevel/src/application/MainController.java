package application;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
	
	@FXML
	private Label myNum;
	public void generateRandom(ActionEvent event) {
		Random rand = new Random();
		int bloodSugar = rand.nextInt(151)+ 50;
		myNum.setText(Integer.toString(bloodSugar));
		
	}
}
