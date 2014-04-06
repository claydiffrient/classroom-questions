package com.simplydiffrient.ClassroomQuestions.ui;

import com.simplydiffrient.ClassroomQuestions.service.QuestionMessage;
import com.simplydiffrient.ClassroomQuestions.service.QuestionSender;
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

import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The representation that is displayed for teachers.
 *
 * @author Clay Diffrient
 */
public class TeacherWindow
    extends Stage
    implements Runnable
{
    QuestionSender mQuestionSender;
    ResultsWindow mResultsWindow;
    TextField[] mAnswerTextFields;
    TextField mQuestionTextField;
    Scene mDisplayScene;

    /**
     * Constructor
     * @param pGroupNumber The group to join.
     */
    public TeacherWindow(int pGroupNumber)
    {
        mQuestionSender = new QuestionSender(pGroupNumber);
        mAnswerTextFields = new TextField[4];
        for (int i = 0; i < 4; i++)
        {
            mAnswerTextFields[i] = new TextField();
        }
        //Arrays.fill(mAnswerTextFields, new TextField());
        mQuestionTextField = new TextField();
        mDisplayScene = generateScene();

    }

    @Override
    public void run()
    {
        System.out.println("Teacher window starting...");
        setTitle("Classroom Questions - Teacher");
        setScene(mDisplayScene);
        show();
    }

    /**
     * Generates the scene needed to be displayed, unfortunately this is probably a bit long for a good function.
     * @return Scene
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


        /**
         * Set up the question to ask area.
         */
        HBox questionBox = new HBox(10);
        mQuestionTextField.setMinWidth(300);
        Label questionLabel = new Label("Question:");
        questionLabel.setFont(new Font(14));
        questionLabel.setLabelFor(mQuestionTextField);

        Label questionSizeLabel = new Label("0/200");

        questionBox.getChildren().addAll(questionLabel, mQuestionTextField, questionSizeLabel);
        questionBox.setPrefHeight(50);
        BorderPane.setMargin(questionBox, new Insets(12));
        rootPanel.setCenter(questionBox);

        mQuestionTextField.addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(200, questionSizeLabel));
        HBox[] answerBoxes = new HBox[4];
        for (int i = 0; i < 4; i++)
        {
            answerBoxes[i] = new HBox(10);
        }
        // Arrays.fill(answerBoxes, new HBox(10));
        final String[] answerLabels = {"A", "B", "C", "D"};
        for (int i = 0; i < answerBoxes.length; i++)
        {
            Label answerLabel = new Label(answerLabels[i] + ":");
            answerLabel.setFont(new Font(14));
            answerLabel.setLabelFor(mAnswerTextFields[i]);
            Label sizeLabel = new Label("0/50");
            mAnswerTextFields[i].addEventFilter(KeyEvent.KEY_TYPED, new MaxLengthEvent(50, sizeLabel));
            answerBoxes[i].getChildren().addAll(answerLabel, mAnswerTextFields[i], sizeLabel);
        }

        GridPane.setMargin(answerBoxes[0], new Insets(0, 10, 10, 0));
        GridPane.setMargin(answerBoxes[2], new Insets(0, 10, 10, 0));


        VBox answersAndButton = new VBox();

        GridPane answerArea = new GridPane();
        answerArea.add(answerBoxes[0], 0, 0);
        answerArea.add(answerBoxes[1], 0, 1);
        answerArea.add(answerBoxes[2], 1, 0);
        answerArea.add(answerBoxes[3], 1, 1);

        Button sendQuestion = new Button("Ask Question");
        sendQuestion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Create a map for sending the questions.
                Map<String, String> answers = new LinkedHashMap<String, String>();
                for (int i = 0; i < mAnswerTextFields.length; i++) {
                    answers.put(answerLabels[i], mAnswerTextFields[i].getText());
                }
                // Create a question message
                String questionText = mQuestionTextField.getText();
                try {
                    InetAddress responseAddr = InetAddress.getLocalHost();
                    QuestionMessage qm = new QuestionMessage(questionText, answers, responseAddr);
                    mQuestionSender.sendQuestion(qm);
                } catch (Exception ex) {
                    // TODO: Add better error handling.
                }

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
