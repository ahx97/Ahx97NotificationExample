/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    private boolean task1Set = false;
    private boolean task2Set = false;
    private boolean task3Set = false;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end(); //these are to close the threads down so they dont run while the UI isnt up
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
        button1.setText("Start Task 1");
        button2.setText("Start Task 2");
        button3.setText("Start Task 3");
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        if(!task1Set){
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            button1.setText("Stop Task 1");
        
            }
            task1Set = true;
        }else{
            System.out.println("stoped task 1");
            button1.setText("Start task 1");
            textArea.setText("Task1 ended");
            task1Set = false;
            task1.end();
            task1 = null;
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        if(!task2Set){ //task2 has not been clicked
            System.out.println("start task 2");
        
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            
            task2.start();
            button2.setText("Stop Task 2");
            task2Set = true;
        }        
     }else{
            System.out.println("stoped task 2");
            button2.setText("Start task 2");
            task2Set = false;
            task2.end();
            textArea.setText("Task2 ended");
            task2 = null;
        }
        
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        if(!task3Set){
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
            button3.setText("Stop Task 3");
            }
            task3Set = true;
        }else{
            System.out.println("stoped task 3");
            button3.setText("Start task 3");
            task3Set = false;
            task3.end();
            textArea.setText("Task3 ended");
            task3 = null;
        }
    }
        
}
