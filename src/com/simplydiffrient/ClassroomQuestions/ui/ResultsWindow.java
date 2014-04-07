package com.simplydiffrient.ClassroomQuestions.ui;

import com.simplydiffrient.ClassroomQuestions.service.AnswerReceiver;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    ObservableMap<Character, Integer> mData;
    Map<Character, Label> mValueLabels;
    Scene mDisplay;

    public ResultsWindow()
    {
        mReceiver = new AnswerReceiver();
        mData = FXCollections.observableHashMap();
        mData.put('A', 0);
        mData.put('B', 0);
        mData.put('C', 0);
        mData.put('D', 0);
        mValueLabels = new HashMap<Character, Label>(4);
        mValueLabels.put('A', new Label("0"));
        mValueLabels.put('B', new Label("0"));
        mValueLabels.put('C', new Label("0"));
        mValueLabels.put('D', new Label("0"));
        mDisplay = generateScene();

        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("Closing socket.");
                mReceiver.stopListening();
            }
        });
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
                        Integer oldValue = mData.get(c);
                        mData.put(c, oldValue + 1);
                    }
                }


            }
        });

        mData.addListener(new MapChangeListener<Character, Integer>()
        {
            @Override
            public void onChanged(final Change<? extends Character, ? extends Integer> change)
            {
                if (change.wasAdded())
                {
                    Task<Void> updateTask = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception
                        {
                            Character key = change.getKey();
                            //Label label = mValueLabels.get(key);
                            Integer valueAdded = change.getValueAdded();
                            mValueLabels.get(key).setText(valueAdded.toString());
                            //label.setText(valueAdded.toString());
                            return null;
                        }
                    };
                    Thread updateThread = new Thread(updateTask);
                    Platform.runLater(updateThread);
                }
            }
        });

    }

    Scene generateScene()
    {
        BorderPane rootPanel = new BorderPane();

        GridPane responsePanel = new GridPane();
        final String[] answerLabels = {"A", "B" ,"C" ,"D"};

        HBox[] responseBoxes = new HBox[4];
        for (int i = 0; i < 4; i++)
        {
            responseBoxes[i] = new HBox(10);
            Label responseLabel = new Label(answerLabels[i]);
            Label valueLabel = mValueLabels.get(answerLabels[i].charAt(0));
            responseBoxes[i].getChildren().addAll(responseLabel, valueLabel);

        }
        responsePanel.add(responseBoxes[0], 0, 0);
        responsePanel.add(responseBoxes[1], 0, 1);
        responsePanel.add(responseBoxes[2], 1, 0);
        responsePanel.add(responseBoxes[3], 1, 1);

        rootPanel.setCenter(responsePanel);


        return new Scene(rootPanel, 600, 300);
    }

}
