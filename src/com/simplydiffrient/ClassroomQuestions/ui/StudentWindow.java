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
 * The window that students interact with.
 * @author Clay Diffrient
 * @version 1.0.0
 */
public class StudentWindow
    extends Stage
    implements Runnable
{

    /**
     * Holds the QuestionReceiver needed for the underlying operations.
     * @see com.simplydiffrient.ClassroomQuestions.service.QuestionReceiver
     */
    QuestionReceiver mQuestionReceiver;

    /**
     * Holds the answer choices that are possible.
     */
    ObservableList<String> mAnswerChoices;

    /**
     * The text area which question text will appear in.
     */
    TextArea mQuestionArea;

    /**
     * The scene shown in the window
     */
    Scene mDisplayScene;

    /**
     * Holds the address that replies will be sent to.
     */
    InetAddress mResponseAddr;

    /**
     * The button used to send data back to the teacher with.
     */
    Button mSendAnswerButton;

    /**
     * Constructor
     * @param pGroupNumber The group to join.
     */
    public StudentWindow(int pGroupNumber)
    {
        mQuestionReceiver = new QuestionReceiver(pGroupNumber);
        mAnswerChoices = FXCollections.observableArrayList();
        mQuestionArea = new TextArea();
        mSendAnswerButton = new Button("Send Answer");
        mSendAnswerButton.setDisable(true);
        mDisplayScene = generateScene();

    }

    /**
     * Runs all the operations for the window.
     */
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
                    // Make sure that this all ends up on the application thread.
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mQuestionArea.setText(qm.getQuestionText());
                            mAnswerChoices.clear();
                            for (Map.Entry<String, String> entry : qm.getAnswers().entrySet())
                            {
                                mAnswerChoices.add(entry.getKey() + ":" + entry.getValue());
                            }
                            mResponseAddr = qm.getResponseAddress();
                            mSendAnswerButton.setDisable(false);

                        }
                    });
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();


    }

    /**
     * Creates the scene to be displayed.
     * @return The scene
     */
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

        answerArea.getChildren().addAll(answerLabel, answerChoices, mSendAnswerButton);
        answerArea.setAlignment(Pos.CENTER);
        BorderPane.setMargin(answerArea, new Insets(0, 0, 100, 0));
        rootPanel.setBottom(answerArea);

        mSendAnswerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (mResponseAddr != null)
                {
                    if (!answerChoices.getValue().isEmpty())
                    {
                        char choice = answerChoices.getValue().charAt(0);
                        AnswerSender sender = new AnswerSender(mResponseAddr);
                        sender.send(choice);
                        mSendAnswerButton.setDisable(true);
                    }
                    else
                    {
                        //TODO: Add something visual as an error.
                        System.out.println("Error: You need to make a choice of answer first...");
                    }
                }

            }
        });


        return new Scene(rootPanel, 600, 300);
    }
}
