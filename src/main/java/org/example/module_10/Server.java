package org.example.module_10;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());

        while (true) {
          // Receive request from user
          int prime = inputFromClient.readInt();
  
          // Compute if number is prime
          String notAPrime = "The number is NOT a prime number.";

          String isAPrime = "The number is a prime number.";

          int decision = 0; // if 0 not prime if 1 prime.

          if (prime <= 1){
            decision = 0;
          }else if (prime == 2){
            decision = 1;
          }else{
            for(int x = 2; x < prime; x++){
              if (prime % x == 0){
                decision = 0;
                break;
              }else{
                decision = 1;
              }
            }
          }

          if (decision == 0){
            outputToClient.writeUTF(notAPrime);
          }else{
            outputToClient.writeUTF(isAPrime);
          }

          // Sends answer back to client.
          Platform.runLater(() -> {
            ta.appendText("Number received from client: "
              + prime + '\n');
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
