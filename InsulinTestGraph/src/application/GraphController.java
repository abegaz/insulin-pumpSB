package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import java.util.Random;

public class GraphController {
	
	@FXML 
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private LineChart<Number,Number> newChart;
	Random rand = new Random();
	
	private int finalSugar;
	
	public void initChart(ActionEvent event){
		newChart.getData().clear(); //wipes current chart data	
		XYChart.Series<Number,Number> series = new XYChart.Series<>();
			for(int c = 0;c<24;c++){
				if(insulinInjected() == true){
						series.getData().add(new Data<Number, Number>(c,finalSugar));
						newChart.getData().add(series);
						XYChart.Data<Number, Number> dataPoint = series.getData().get(c); 	//returns element at position c in the list
						Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol"); 		//will throw NPE if not placed after (ChartName).getData().add
					       lineSymbol.setStyle("-fx-background-color: #ff0000, #ff0000; -fx-background-insets: 0, 2;\n" + 		//google CSS color codes to change colors
					               "    -fx-background-radius: 3px;\n" +
					               "    -fx-padding: 4px;");				//how large the colored node appears
							
					}
					else{
						series.getData().add(new Data<Number,Number>(c,finalSugar));
						newChart.getData().add(series);
						
					}
			}
		}
			
	
	public boolean insulinInjected(){
		int bloodSugar = rand.nextInt(151)+ 50;
		
		if (bloodSugar > 130){
			finalSugar = bloodSugar;
			return true;
		}
		else{
			finalSugar = bloodSugar;
			return false;
		}
			
	}
	
	public int sugarValue(){
		return finalSugar;
	}
	
}
