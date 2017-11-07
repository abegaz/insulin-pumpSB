package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class MainController {

    @FXML
    private LineChart<?, ?> LineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    public void btn(ActionEvent event) {
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	series.getData().add(new XYChart.Data<String, Number>("asd", 55));
    	
    }

}
