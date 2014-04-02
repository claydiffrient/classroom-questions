/**
 * Created by clay on 4/1/14.
 */

package ClassroomQuestions.studentui;

import ClassroomQuestions.StudentNode;
import ClassroomQuestions.exceptions.InvalidGroupNumberException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.border.Border;
import java.net.DatagramPacket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class StudentApplication
    extends Application {

    StudentNode mStudentNode;
    ObservableList<String> mAnswerChoices;
    TextArea mQuestionArea;

    private class QuestionResponder
       implements Observer
    {
        public void update(Observable obj, Object arg)
        {
            if (arg instanceof String)
            {
                String value = (String) arg;
                String[] values = value.split(";");
                for (int i = 0; i < values.length; i++)
                {
                    if (i == 0) {
                        mQuestionArea.setText(values[i]);
                    }
                    else
                    {
                        mAnswerChoices.add(values[i]);
                    }
                }
            }
        }
    }


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
        try
        {
            mStudentNode = new StudentNode(Integer.parseInt(parameters.get(0)));

        }
        catch (InvalidGroupNumberException ex)
        {
            System.out.println("You chose an invalid group number.");
            ex.printStackTrace();
            System.exit(-1);
        }

        QuestionResponder responder = new QuestionResponder();
        mStudentNode.addObserver(responder);

        primaryStage.setTitle("Classroom Questions - Student");
        primaryStage.setScene(generateScene());
        primaryStage.show();
        Thread thread = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                mStudentNode.run();
                return null;
            }
        });
        thread.setDaemon(true);
        thread.start();
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
        HBox answerBox = new HBox(10);

        ChoiceBox answerChoices = new ChoiceBox(mAnswerChoices);

        Label answerLabel = new Label("Answer:");
        answerLabel.setFont(new Font(14));
        answerLabel.setLabelFor(answerChoices);

        Button sendAnswerButton = new Button("Send Answer");

        answerBox.getChildren().addAll(answerLabel, answerChoices, sendAnswerButton);
        BorderPane.setMargin(answerBox, new Insets(12, 160, 100, 160));
        BorderPane.setAlignment(answerBox, Pos.CENTER);
        rootPanel.setBottom(answerBox);

        return new Scene(rootPanel, 600, 300);
    }
}
