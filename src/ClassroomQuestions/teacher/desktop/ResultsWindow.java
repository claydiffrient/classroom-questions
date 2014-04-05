package ClassroomQuestions.teacher.desktop;
/**
 * Created by clay on 4/2/14.
 */

import ClassroomQuestions.teacher.service.ResultReceiver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ResultsWindow
    extends Stage
    implements Runnable
{
    BarChart<String, Number> mBarChart;
    ObservableList<XYChart.Series<String,Number>> mResponseData;
    ResultReceiver mReceiver;

    public ResultsWindow()
    {
        mResponseData = FXCollections.observableArrayList();
        mBarChart = generateBarChart();
        mReceiver = new ResultReceiver();
        addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
               mReceiver
            }
        });

    }

    @Override
    public void run()
    {
        System.out.println("Run called");
        setTitle("Results");
        Scene mainScene = generateScene();
        setScene(mainScene);

        mReceiver.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                char response = ((String) workerStateEvent.getSource().getValue()).charAt(0);
                if (response == 'A') {
                    mResponseData.get(0).getData().add(new XYChart.Data<String, Number>("A", 1));
                }

               /* XYChart.Series<String, Number> aVal = new XYChart.Series<String, Number>();
                XYChart.Series<String, Number> bVal = new XYChart.Series<String, Number>();
                XYChart.Series<String, Number> cVal = new XYChart.Series<String, Number>();
                XYChart.Series<String, Number> dVal = new XYChart.Series<String, Number>();

                for (XYChart.Series<String, Number> obj : mBarChart.getData())
                {
                    if (obj.getName() == "A")
                    {
                        aVal = obj;
                    }
                    if (obj.getName() == "B")
                    {
                        bVal = obj;
                    }
                    if (obj.getName() == "C")
                    {
                        cVal = obj;
                    }
                    if (obj.getName() == "D")
                    {
                        dVal = obj;
                    }
                }

                switch (response) {
                    case 'A':
                        aVal.getData().add(new XYChart.Data<String,Number>("A", 1));
                        break;
                    case 'B':
                        bVal.getData().add(new XYChart.Data<String,Number>("B", 1));
                        break;
                    case 'C':
                        cVal.getData().add(new XYChart.Data<String,Number>("C", 1));
                        break;
                    case 'D':
                        dVal.getData().add(new XYChart.Data<String,Number>("D", 1));
                        break;
                }
                mReceiver.restart(); */
            }
        });
        mReceiver.start();
        show();
    }

    private Scene generateScene()
    {
        BorderPane rootPanel = new BorderPane();

        rootPanel.setTop(mBarChart);

        /*Label resultLabel = new Label("Results");
        resultLabel.setFont(new Font(20));
        resultLabel.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setMargin(resultLabel, new Insets(12));
        rootPanel.setTop(resultLabel); */

        return new Scene(rootPanel, 600, 300);
    }

    private BarChart<String, Number> generateBarChart()
    {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
        chart.setTitle("Responses");
        xAxis.setLabel("Possible Choices");
        yAxis.setLabel("# of Responses");

        XYChart.Series<String, Number> aResults = new XYChart.Series<String, Number>();
        aResults.setName("A");
        XYChart.Series<String, Number> bResults = new XYChart.Series<String, Number>();
        bResults.setName("B");
        XYChart.Series<String, Number> cResults = new XYChart.Series<String, Number>();
        cResults.setName("C");
        XYChart.Series<String, Number> dResults = new XYChart.Series<String, Number>();
        dResults.setName("D");


        mResponseData.addAll(aResults, bResults, cResults, dResults);
        chart.setData(mResponseData);

        return chart;
    }
}
