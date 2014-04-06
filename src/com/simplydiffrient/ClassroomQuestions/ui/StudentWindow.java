package com.simplydiffrient.ClassroomQuestions.ui;

import com.simplydiffrient.ClassroomQuestions.service.AnswerSender;
import com.simplydiffrient.ClassroomQuestions.service.QuestionMessage;
import com.simplydiffrient.ClassroomQuestions.service.QuestionReceiver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.Map;

/**
 * Created by clay on 4/5/14.
 */
public class StudentWindow
    extends Stage
    implements Runnable
{
    QuestionReceiver mQuestionReceiver;
    AnswerSender mAnswerSender;
    ObservableList<String> mAnswerChoices;
    TextArea mQuestionArea;
    Scene mDisplayScene;
    InetAddress mResponseAddr;

    public StudentWindow(int pGroupNumber)
    {
        mQuestionReceiver = new QuestionReceiver(pGroupNumber);
        mAnswerChoices = FXCollections.observableArrayList();
        mQuestionArea = new TextArea();
        mDisplayScene = generateScene();
    }

    @Override
    public void run()
    {
        System.out.println("Student window starting...");
        setTitle("Classroom Questions - Student");
        setScene(mDisplayScene);
        show();

        // Start receiving questions
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true)
                {
                    System.out.println("In loop...");
                    final QuestionMessage qm = mQuestionReceiver.getQuestion();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mQuestionArea.setText(qm.getQuestionText());
                            for (Map.Entry<String, String> entry : qm.getAnswers().entrySet())
                            {
                                mAnswerChoices.add(entry.getKey() + ":" + entry.getValue());
                            }
                            mResponseAddr = qm.getResponseAddress();
                        }
                    });
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();


    }

    private Scene generateScene()
    {
        BorderPane rootPanel = new BorderPane();

        // Set up the title on the top.
        HBox titleBox = new HBox(10);
        Text title = new Text("Classroom Questions");
        title.setFont(new Font(20));
        title.setTextAlignment(TextAlignment.CENTER);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        BorderPane.setMargin(titleBox, new Insets(12));
        rootPanel.setTop(titleBox);

        // Set up the question box
        HBox questionBox = new HBox(10);
        mQuestionArea.setEditable(false);
        mQuestionArea.setPrefRowCount(4);
        mQuestionArea.setMaxHeight(100);
        Label questionLabel = new Label("Question:");
        questionLabel.setFont(new Font(14));
        questionLabel.setLabelFor(mQuestionArea);
        questionBox.getChildren().addAll(questionLabel, mQuestionArea);
        questionBox.setPrefHeight(50);
        BorderPane.setMargin(questionBox, new Insets(12));
        rootPanel.setCenter(questionBox);

        //Set up the answer area
        HBox answerArea = new HBox(10);
        final ChoiceBox<String> answerChoices = new ChoiceBox<String>(mAnswerChoices);
        Label answerLabel = new Label("Answer:");
        answerLabel.setFont(new Font(14));
        answerLabel.setLabelFor(answerChoices);
        Button sendAnswer = new Button("Send Answer");
        answerArea.getChildren().addAll(answerLabel, answerChoices, sendAnswer);
        answerArea.setAlignment(Pos.CENTER);
        BorderPane.setMargin(answerArea, new Insets(0, 0, 100, 0));
        rootPanel.setBottom(answerArea);

        sendAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (mResponseAddr != null)
                {
                    char choice = answerChoices.getValue().charAt(0);
                    AnswerSender sender = new AnswerSender(mResponseAddr);
                    sender.send(choice);
                }

            }
        });


        return new Scene(rootPanel, 600, 300);
    }
}
