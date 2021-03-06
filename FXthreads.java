/*
 * MVC-Framework verwendet von Bradley Richards,
 * jedoch Threads mit Runnable ausgetauscht, da kein Ergebnis vom Thread erwartet wird. 
 * URL: https://moodle.fhnw.ch/course/view.php?id=12867
 * Threads > lecture09_threads > JavaFX_threads
 * 
 * Verwendete Ressourcen: http://docs.oracle.com/javafx/2/charts/scatter-chart.htm
 * 
 * Unterstützung/Hints durch Raphael Lückl
 * 
 * */

import javafx.application.Application;
import javafx.stage.Stage;

public class FXthreads extends Application {
	private FXthreadsView view;
	private FXthreadsController controller;
	private FXthreadsModel model;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Note the dependencies between model, view and controller. Additionally,
	 * the view needs the primaryStage created by JavaFX.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Initialisierung des GUI
		model = new FXthreadsModel();
		view = new FXthreadsView(primaryStage, model);
		model.setView(view);
		controller = new FXthreadsController(model, view);

		// GUI darstellen nachdem die Initialisierung abgeschlossen ist
		view.start();
	}

	/**
	 * The stop method is the opposite of the start method. It provides an
	 * opportunity to close down the program gracefully, when the program has
	 * been closed.
	 */
	@Override
	public void stop() {
		if (view != null)
			view.stop();
	}
}
