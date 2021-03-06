import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class FXthreadsView {
    private FXthreadsModel model;
    private Stage stage;

	protected Label lblNumber;
	protected Label lblNumberTrue;
	protected Label lblNumberFalse;
	protected Label lblPi;
	protected Label lblText;
	protected Button btnClick;

	ScatterChart<Number,Number> scatterChart;
	protected NumberAxis xAxis, yAxis;

	XYChart.Series series1;
	XYChart.Series series2;

	protected double x, y, pi;
	
	protected Label lblx;
	protected Label lbly;

	protected FXthreadsView(Stage stage, FXthreadsModel model) {
		this.stage = stage;
		this.model = model;
		
		stage.setTitle("Monte Carlo Grafik");
		
		BorderPane root = new BorderPane();
		
		VBox vbox = new VBox();
		root.setRight(vbox);
		
		lblx = new Label();
		lblx.setText("X = " + Double.toString(model.getValue().get()));
		lblx.setId("x");
		
		lbly = new Label();
		lbly.setText("Y = " + Double.toString(model.getValue().get()));
		lbly.setId("y");
		
		lblNumber = new Label();
		lblNumber.setText("Anz. Punkte generiert = " + Integer.toString(model.getValue().get()));
		lblNumber.setId("lblNumber");
		
		lblNumberTrue = new Label();
		lblNumberTrue.setText("Anz. Punkte innerhalb = " + Integer.toString(model.getValue().get()));
		lblNumberTrue.setId("lblNumberTrue");
		
		lblNumberFalse = new Label();
		lblNumberFalse.setText("Anz. Punkte ausserhalb = " + Integer.toString(model.getValue().get()));
		lblNumberFalse.setId("lblNumberFalse");
		
		lblPi = new Label();
		lblPi.setText("Aktuelle Sch\u00e4tzung von \u03C0 = " + Double.toString(model.getValue().get()));
		lblPi.setId("lblPi");
		
		lblText = new Label();
		lblText.setText("Letzte zuf\u00e4llig generierte Koordinaten:");
		lblText.setId("lblText");
		
		btnClick = new Button();
		btnClick.setText("Berechnung starten");
		btnClick.setId("btnClick");
		
		vbox.setSpacing(10);
		vbox.getChildren().addAll(btnClick, lblText, lblx, lbly, lblNumber, lblNumberTrue, lblNumberFalse, lblPi);
		vbox.setPadding(new Insets(100, 50, 10, 50));
		vbox.setMinWidth(500);
		
		xAxis = new NumberAxis(0, 1, 0.01);
		yAxis = new NumberAxis(0, 1, 0.01);
		scatterChart = new ScatterChart<Number,Number>(xAxis,yAxis);
		xAxis.setLabel("X");
		yAxis.setLabel("Y");
		scatterChart.setTitle("Kreisviertel");
		root.setCenter(scatterChart);
		
		series1 = new XYChart.Series();
		series1.setName("innerhalb");
		
		series2 = new XYChart.Series();
		series2.setName("ausserhalb");
		
		/* Mit Hilfe der nachfolgenden 2 Zeilen erstelle ich Initial-Werte im Koordinatensystem
		 * welche ausserhalb des sichtbaren Feldes liegen. Ansonsten würden die Symbole in
		 * der Legende nicht angezeigt. Details siehe hier:
		 * http://stackoverflow.com/questions/30171290/javafx-scatterchart-does-not-display-legend-symbol-when-initialized-with-empty-d
		 * */ 
		series1.getData().add(new XYChart.Data<>(-1,-1));
		series2.getData().add(new XYChart.Data<>(2,2));
		
		scatterChart.getData().addAll(series1,series2);

		Scene scene = new Scene(root, 1180, 725);
		scene.getStylesheets().add("Style.css");
		stage.setScene(scene);;
	}

	public ScatterChart<Number, Number> getChart() {
		return this.scatterChart;
	}
	public XYChart.Series getSeries1() {
		return series1;
	}

	public XYChart.Series getSeries2() {
		return series2;
	}

	public void start() {
		stage.show();
	}
	
	/**
	 * Stopping the view - just make it invisible
	 */
	public void stop() {
		stage.hide();
	}
	
	/**
	 * Getter for the stage, so that the controller can access window events
	 */
	public Stage getStage() {
		return stage;
	}
}