package com.simplydiffrient.ClassroomQuestions.ui;

import com.simplydiffrient.ClassroomQuestions.service.AnswerReceiver;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by clay on 4/5/14.
 */
public class ResultsWindow
    extends Stage
    implements Runnable
{
    AnswerReceiver mReceiver;
    BarChart<String, Number> mBarChart;
    XYChart.Series<String, Number> mBarSeries;
    ObservableList<XYChart.Data<String, Number>> mBarData;

    Map<Character, XYChart.Data<String, Number>> mData;

    Scene mDisplay;

    public ResultsWindow()
    {
        mReceiver = new AnswerReceiver();
        mBarData = FXCollections.observableArrayList();
        mBarSeries = new XYChart.Series<String, Number>(mBarData);
        mBarChart = generateBarChart();
        mDisplay = generateScene();

        mData = new HashMap<Character, XYChart.Data<String, Number>>();
        mData.put('A', new XYChart.Data<String, Number>("A", 0));
        mData.put('B', new XYChart.Data<String, Number>("B", 0));
        mData.put('C', new XYChart.Data<String, Number>("B", 0));
        mData.put('D', new XYChart.Data<String, Number>("B", 0));
        for (Map.Entry<Character, XYChart.Data<String, Number>> entry : mData.entrySet())
        {
            mBarData.add(entry.getValue());
        }


    }

    @Override
    public void run()
    {
        setTitle("Results");
        setScene(mDisplay);
        show();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                System.out.println("Listen has been called.");
                mReceiver.listen();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        ObservableList<Character> rawData = mReceiver.getResponses();
        rawData.addListener(new ListChangeListener<Character>() {
            @Override
            public void onChanged(Change<? extends Character> change) {
                while(change.next())
                {
                    System.out.println("Changed.");
                    for (Character c : change.getAddedSubList())
                    {
                        XYChart.Data<String, Number> val = mData.get(c);
                        val.setYValue(val.getYValue().intValue() + 1);
                    }
                }


            }
        });

    }

    Scene generateScene()
    {
        BorderPane rootPanel = new BorderPane();
        rootPanel.setCenter(mBarChart);
        return new Scene(rootPanel, 600, 300);
    }

    BarChart<String, Number> generateBarChart()
    {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis, FXCollections.observableArrayList(mBarSeries));
        bc.setTitle("Results");
        return bc;
    }

}
