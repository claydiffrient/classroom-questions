package com.simplydiffrient.ClassroomQuestions.ui;/**
 * Created by clay on 4/2/14.
 */

import com.simplydiffrient.ClassroomQuestions.ui.ResultsWindow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TeacherApplication
    extends Application
{
    TextField mQuestionTextBox;
    TextField mAnswerA;
    TextField mAnswerB;
    TextField mAnswerC;
    TextField mAnswerD;
    ResultsWindow mResults;



    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: TeacherApplication {GroupNumber}");
            System.exit(-1);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        Scene display = generateScene();
        primaryStage.setScene(display);
        primaryStage.show();
    }

    /**
     * Generates the scene needed to be displayed, unfortunately this is probably a bit long for a good function.
     * @return Scene
     */
    private Scene generateScene()
    {
        BorderPane rootPanel = new BorderPane();

        /**
         * Set up the title on the top.
         */
        HBox titleBox = new HBox(10);
        Text title = new Text("Classroom Questions");
        title.setFont(new Font(20));
        title.setTextAlignment(TextAlignment.CENTER);
        titleBox.getChildren().add(title);
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        BorderPane.setMargin(titleBox, new Insets(12));
        rootPanel.setTop(titleBox);

        /**
         * Set up the question to ask area.
         */
        HBox questionBox = new HBox(10);
        mQuestionTextBox = new TextField();
        mQuestionTextBox.setMinWidth(300);
        Label questionLabel = new Label("Question:");
        questionLabel.setFont(new Font(14));
        questionLabel.setLabelFor(mQuestionTextBox);

        Label sizeLabel = new Label("0/200");

        questionBox.getChildren().addAll(questionLabel, mQuestionTextBox, sizeLabel);
        questionBox.setPrefHeight(50);
        BorderPane.setMargin(questionBox, new Insets(12));
        rootPanel.setCenter(questionBox);

        mQuestionTextBox.addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(200, sizeLabel));

        /**
         * Set up the answers area
         */
        HBox answerA = new HBox(10);
        HBox answerB = new HBox(10);
        HBox answerC = new HBox(10);
        HBox answerD = new HBox(10);

        mAnswerA = new TextField();
        mAnswerB = new TextField();
        mAnswerC = new TextField();
        mAnswerD = new TextField();

        Label ansA = new Label("A:");
        Label ansB = new Label("B:");
        Label ansC = new Label("C:");
        Label ansD = new Label("D:");

        ansA.setFont(new Font(14));
        ansA.setLabelFor(mAnswerA);
        ansB.setFont(new Font(14));
        ansB.setLabelFor(mAnswerB);
        ansC.setFont(new Font(14));
        ansC.setLabelFor(mAnswerC);
        ansD.setFont(new Font(14));
        ansD.setLabelFor(mAnswerD);

        Label aSize = new Label("0/50");
        Label bSize = new Label("0/50");
        Label cSize = new Label("0/50");
        Label dSize = new Label("0/50");

        mAnswerA.addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(50, aSize));
        mAnswerB.addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(50, bSize));
        mAnswerC.addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(50, cSize));
        mAnswerD.addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(50, dSize));

        answerA.getChildren().addAll(ansA, mAnswerA, aSize);
        answerB.getChildren().addAll(ansB, mAnswerB, bSize);
        answerC.getChildren().addAll(ansC, mAnswerC, cSize);
        answerD.getChildren().addAll(ansD, mAnswerD, dSize);

        GridPane.setMargin(answerA, new Insets(0,10,10,0));
        GridPane.setMargin(answerC, new Insets(0,10,10,0));


        VBox answersAndButton = new VBox();

        GridPane answerArea = new GridPane();
        answerArea.add(answerA, 0, 0);
        answerArea.add(answerB, 0, 1);
        answerArea.add(answerC, 1, 0);
        answerArea.add(answerD, 1, 1);

        Button sendQuestion = new Button("Ask Question");
        sendQuestion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ResultsWindow().run();
            }
        });

        VBox.setMargin(sendQuestion, new Insets(15, 0, 0, 125));

        answersAndButton.getChildren().addAll(answerArea, sendQuestion);

        BorderPane.setMargin(answersAndButton, new Insets(12, 12, 100, 100));

        rootPanel.setBottom(answersAndButton);



        return new Scene(rootPanel, 600, 300);
    }

    private class MaxLengthEvent
        implements EventHandler<KeyEvent>
    {
        private int mLength;
        private Label mUpdateLabel;

        public MaxLengthEvent(int pLength)
        {
            mLength = pLength;
        }

        public MaxLengthEvent(int pLength, Label pUpdateLabel)
        {
            mLength = pLength;
            mUpdateLabel = pUpdateLabel;
        }

        @Override
        public void handle (KeyEvent pEvent)
        {
            TextField textField = (TextField) pEvent.getSource();
            String text = textField.getText();
            if (mUpdateLabel != null)
            {
                String value = (text.length() + 1) + "/" + mLength;
                mUpdateLabel.setText(value);
            }
            if (text.length() >= mLength)
            {
                pEvent.consume();
            }
        }
    }
}
