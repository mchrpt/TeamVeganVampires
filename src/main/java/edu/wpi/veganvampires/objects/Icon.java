package edu.wpi.veganvampires.objects;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Icon {
  private double xCoord;
  private double yCoord;
  @FXML private ImageView image;
  private ArrayList<ServiceRequest> requestsArr;

  public Icon(Location location) {
    this.xCoord = location.getXCoord();
    this.yCoord = location.getYCoord();
    this.requestsArr = new ArrayList<>();
    image = new ImageView("icon.png");
    image.setFitWidth(40);
    image.setFitHeight(40);
    image.setX(xCoord / 1.9);
    image.setY(yCoord / 2.2);
    image.setOnMouseClicked(
        event -> {
          // Display requests/info
          BorderPane borderPane = new BorderPane();
          Scene scene = new Scene(borderPane, 300, 300);
          Stage stage = new Stage();
          stage.setScene(scene);
          stage.setTitle(location.getShortName());
          if (!stage.isShowing()) {
            stage.show();
          }
          VBox vBox = new VBox();
          Text locationName = new Text(location.getLongName());
          vBox.getChildren().add(locationName);
          for (ServiceRequest request : requestsArr) {
            Accordion accordion =
                new Accordion(
                    new TitledPane(request.getRequestName() + ": " + request.getStatus(), null));
            vBox.getChildren().add(accordion);
          }
          borderPane.getChildren().add(vBox);
        });
  }

  public Icon(Location location, ArrayList<ServiceRequest> requestsArr) {
    System.out.println("ICON");
    setXY(location);
    this.requestsArr = requestsArr;
    if (requestsArr.isEmpty()) {
      System.out.println(1);
      image = new ImageView("icon.png");
    } else {
      System.out.println(2);
      image = new ImageView("markedIcon.png");
    }
    image.setFitWidth(60);
    image.setFitHeight(60);
    image.setX(xCoord);
    image.setY(yCoord);
    // rectangle = new Rectangle(xCoord, yCoord, image.getFitWidth(), image.getFitHeight());
    image.setOnMouseClicked(
        event -> {
          // Display requests/info
        });
  }

  // Calculates where X and Y should be
  private void setXY(Location location) {
    this.xCoord = location.getXCoord();
    this.yCoord = location.getYCoord();
  }
}