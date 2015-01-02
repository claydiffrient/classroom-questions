package com.simplydiffrient.ClassroomQuestions.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Window that asks for a group number for the application.
 * It is used by both the Student Application and the Teacher Application.
 * This is essentially the main entry point into the application as a whole.
 *
 * @author Clay Diffrient
 * @version 1.0.0
 */
public class GroupSelectionWindow
    extends Application
{
    /**
     * This is the main entry point to the application from the commandline.
     * @param args Commandline arguments, none currently.
     */
    public static void main(String[] args)
    {
        System.out.println("Classroom Questions starting...");
        launch(args);
    }

    /**
     * Starts the application running.
     * @param stage The JavaFX stage to use
     * @throws Exception
     */
    @Override
    public void start(Stage stage)
        throws Exception
    {
        stage.setTitle("Classroom Questions - Group Selection");
        Scene mainScene = generateScene();
        stage.setScene(mainScene);
        stage.show();
        System.out.println("Group selection window displayed.");
    }

    /**
     * Generates the scene for the application.
     * @return The scene to be placed on the stage.
     */
    private Scene generateScene()
    {
        BorderPane rootPanel = new BorderPane();

        // Set up the title.
        Label title = new Label("What group do you wish to join?");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(10, 0, 10, 0));
        rootPanel.setTop(title);

        // Set up the response area.
        VBox responseArea = new VBox();
        HBox responseEntryArea = new HBox();
        final TextField response = new TextField();
        Label group = new Label("Group Number:");
        group.setLabelFor(response);
        responseEntryArea.getChildren().addAll(group, response);
        responseEntryArea.setAlignment(Pos.CENTER);
        final Label errorLabel = new Label("");
        responseArea.setAlignment(Pos.CENTER);
        responseArea.getChildren().addAll(responseEntryArea, errorLabel);
        BorderPane.setAlignment(responseArea, Pos.CENTER);
        rootPanel.setCenter(responseArea);

        // Set up the buttons
        HBox buttonArea = new HBox();
        Button teacherButton = new Button("Teacher");
        HBox.setMargin(teacherButton, new Insets(2, 10, 2, 2));
        Button studentButton = new Button("Student");
        HBox.setMargin(studentButton, new Insets(2, 10, 2, 2));
        buttonArea.getChildren().addAll(teacherButton, studentButton);
        BorderPane.setMargin(buttonArea, new Insets(0, 0, 10, 0));
        buttonArea.setAlignment(Pos.CENTER);
        rootPanel.setBottom(buttonArea);

        // Add some function to the buttons
        teacherButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if ((response.getText() == null) || (response.getText().isEmpty()))
                {
                    errorLabel.setText("You must provide a group number");
                }
                else
                {
                    int value = 1;
                    try
                    {
                        value = Integer.parseInt(response.getText());
                    }
                    catch (NumberFormatException ex)
                    {
                        errorLabel.setText("You must enter a number.");
                    }
                    if ((value > 255) || (value < 1))
                    {
                        errorLabel.setText("Group numbers must be between 1 and 255");
                    }
                    else
                    {
                        new TeacherWindow(value).run();
                    }
                }
            }
        });

        studentButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                if ((response.getText() == null) || (response.getText().isEmpty()))
                {
                    errorLabel.setText("You must provide a group number");
                }
                else
                {
                    int value = 1;
                    try
                    {
                        value = Integer.parseInt(response.getText());
                    }
                    catch (NumberFormatException ex)
                    {
                        errorLabel.setText("You must enter a number.");
                    }
                    if ((value > 255) || (value < 1))
                    {
                        errorLabel.setText("Group numbers must be between 1 and 255");
                    }
                    else
                    {
                        new StudentWindow(value).run();
                    }
                }
            }
        });

        return new Scene(rootPanel, 350, 150);

    }
}
