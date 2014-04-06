/**
 * Created by clay on 4/1/14.
 */

package com.simplydiffrient.ClassroomQuestions.ui;

import com.simplydiffrient.ClassroomQuestions.StudentNode;
import com.simplydiffrient.ClassroomQuestions.exceptions.InvalidGroupNumberException;
import com.simplydiffrient.ClassroomQuestions.service.QuestionGetter;
import com.simplydiffrient.ClassroomQuestions.system.IPAnswerDataPair;
import com.simplydiffrient.ClassroomQuestions.system.UnicastSender;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
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

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class StudentApplication
    extends Application {

    StudentNode mStudentNode;
    ObservableList<String> mAnswerChoices;
    TextArea mQuestionArea;
    InetAddress mTeacherAddress;
    Button mSendAnswerButton;

    public static void main(String[] args) {
        if (args.length != 1)
        {
            System.out.println("Usage: StudentApplication {GroupNumber}");
            System.exit(-1);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Act somewhat like a constructor here.
        mAnswerChoices = FXCollections.observableArrayList();
        Parameters params = getParameters();
        List<String> parameters = params.getRaw();
        int nodeNumber = Integer.parseInt(parameters.get(0));
        try
        {
            mStudentNode = new StudentNode(nodeNumber);

        }
        catch (InvalidGroupNumberException ex)
        {
            System.out.println("You chose an invalid group number.");
            ex.printStackTrace();
            System.exit(-1);
        }


        primaryStage.setTitle("Classroom Questions - Student");
        primaryStage.setScene(generateScene());
        primaryStage.show();

        final QuestionGetter getter = new QuestionGetter(nodeNumber);
        getter.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                mAnswerChoices.clear();
                IPAnswerDataPair value = (IPAnswerDataPair) workerStateEvent.getSource().getValue();
                String[] values = value.getAnswerData().split(";");
                mTeacherAddress = value.getIPAddress();
                for (int i = 0; i < values.length; i++)
                {
                    if (i == 0) {
                        mQuestionArea.setText(values[i].split(":")[1]);
                    }
                    else
                    {
                        mAnswerChoices.add(values[i]);
                    }
                }
                mSendAnswerButton.setDisable(false);
                getter.restart();
            }
        });
        getter.start();
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
         * Set up the question area.
         */
        HBox questionBox = new HBox(10);

        mQuestionArea = new TextArea();
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

        /**
         * Set up the answer area
         */
        final HBox answerBox = new HBox(10);

        final ChoiceBox<String> answerChoices = new ChoiceBox<String>(mAnswerChoices);

        Label answerLabel = new Label("Answer:");
        answerLabel.setFont(new Font(14));
        answerLabel.setLabelFor(answerChoices);

        mSendAnswerButton = new Button("Send Answer");
        mSendAnswerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UnicastSender sender = new UnicastSender(mTeacherAddress);
                char toSend = answerChoices.getValue().charAt(0);
                try {
                    //TODO: Capture status code here.
                    sender.sendCharacter(toSend);
                    mSendAnswerButton.setDisable(true);
                } catch (IOException ex) {
                    // TODO: Implement some better error handling.
                }
            }
        });

        answerBox.getChildren().addAll(answerLabel, answerChoices, mSendAnswerButton);
        BorderPane.setMargin(answerBox, new Insets(12, 160, 100, 160));
        BorderPane.setAlignment(answerBox, Pos.CENTER);
        rootPanel.setBottom(answerBox);

        return new Scene(rootPanel, 600, 300);
    }
}
