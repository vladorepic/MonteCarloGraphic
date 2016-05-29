/*
 * Code-Framework verwendet von Bradley Richards,
 * jedoch Threads mit Runnable ausgetauscht, da kein Ergebnis vom Thread erwartet wird. 
 * URL: https://moodle.fhnw.ch/course/view.php?id=12867
 * Threads > lecture09_threads > JavaFX_threads
 * Verwendete Ressourcen: http://docs.oracle.com/javafx/2/charts/scatter-chart.htm
 * Unterstützung/Hints durch Raphael Lückl
 * 
 * */

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.chart.XYChart;

public class FXthreadsModel {
    private SimpleIntegerProperty value;
    private SimpleDoubleProperty valueTrue;
    private SimpleIntegerProperty valueFalse;
    private SimpleDoubleProperty lblPi;
    private SimpleDoubleProperty lblx;
    private SimpleDoubleProperty lbly;
    private volatile BooleanProperty stop;

    Thread ourTaskThread = null;
    private FXthreadsView view;

    protected FXthreadsModel() {
        value = new SimpleIntegerProperty(0);
        valueTrue = new SimpleDoubleProperty(0);
        valueFalse = new SimpleIntegerProperty(0);
        lblPi = new SimpleDoubleProperty(0);
        lblx = new SimpleDoubleProperty(0);
        lbly = new SimpleDoubleProperty(0);
        stop = new SimpleBooleanProperty(true);
        
        // Code-Zeilen 34-40: Hilfe von Raphael Lückl
        stop.addListener(change -> {
            if (stop.get()) {
                view.btnClick.textProperty().set("Berechnung weiterführen");
            } else {
                view.btnClick.textProperty().set("Berechnung pausieren");
            }
        });
    }

    public void setView(FXthreadsView view) {
        this.view = view;
    }

    public SimpleIntegerProperty getValue() {
        return value;
    }
    
    public SimpleDoubleProperty getValueTrue(){
        return valueTrue;
    }
    
    public SimpleIntegerProperty getValueFalse() {
        return valueFalse;
    }
    
    public SimpleDoubleProperty getValuelblPi() {
        return lblPi;
    }
    
    public SimpleDoubleProperty getValuelblx() {
        return lblx;
    }
    
    public SimpleDoubleProperty getValuelbly() {
        return lbly;
    }
    
    public final int getValueValue() {
        return value.get();
    }

    public final double getValueValueTrue() {
        return valueTrue.get();
    }
    
    public void startStop() {
        if (ourTaskThread == null) {
            OurTask task = new OurTask();
            ourTaskThread = new Thread(task, "Generierung von Zufallskoordinaten");
        }
        if (stop.get()) {
            stop.set(false);
            ourTaskThread.start();
        } else {
            ourTaskThread.interrupt();
            ourTaskThread = null;
            stop.set(true);
        }
    }

    public BooleanProperty booleanProperty() {
        return this.stop;
    }

    private class OurTask implements Runnable {
        @Override
        public void run() {
            while (!stop.get()) {
                
                // Generierung von Zufallskoordinaten X und Y (Double zwischen 0 und 1)
                final double x = Math.random();
                final double y = Math.random();
                

                // Counter value zählt die Anzahl generierte Punkte
                value.set(value.get()+1);
                
                // Setze die zufällig erstellten X und Y Koordinaten ins Label für GUI
                lblx.set(x);
                lbly.set(y);
                
                // Überprüfung ob Zufallskoordinate innerhalb oder ausserhalb des Kreisviertels
                if(x * x + y * y <1){
                    
                    // Code-Zeile 104 + 111: Hint mit Platform.runLater() innerhalb des IFs von Raphael Lückl
                    // XYChart.Data verwendet wie beschrieben bei http://docs.oracle.com/javafx/2/charts/scatter-chart.htm
                    Platform.runLater(() -> view.series1.getData().add(new XYChart.Data(x, y)));
                    
                    // Counter valueTrue zählt die Anzahl Punkte innerhalb des Kreisviertels
                    valueTrue.set(valueTrue.get()+1);
                } else {
                    
                    // XYChart.Data verwendet wie beschrieben bei http://docs.oracle.com/javafx/2/charts/scatter-chart.htm
                    Platform.runLater(() -> view.series2.getData().add(new XYChart.Data(x, y)));
                    
                    // Counter valueFalse zählt die Anzahl Punkte ausserhalb des Kreisviertels
                    valueFalse.set(valueFalse.get()+1);
                }
                
                // Schätzung von pi
                final double pi = (valueTrue.get()/value.get())*4;
                
                // Setze Pi ins Label für GUI
                lblPi.set(pi);
                
                try {
                    // Damit Animation schöner, verlangsamt auf 0.01 Sekunden
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }

    }
}
