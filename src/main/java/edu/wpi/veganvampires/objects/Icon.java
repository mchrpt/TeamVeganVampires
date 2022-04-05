package edu.wpi.veganvampires.objects;

import edu.wpi.veganvampires.manager.MapManager;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Icon {
  private Location location;
  private double xCoord;
  private double yCoord;
  @FXML private ImageView image;
  private ArrayList<ServiceRequest> requestsArr;
  private boolean isEquipment;

  public Icon(Location location, boolean isEquipment) {
    this.xCoord = location.getXCoord();
    this.yCoord = location.getYCoord();
    this.requestsArr = new ArrayList<>();
    if (isEquipment) {
      image = new ImageView("Equipment.png");
    } else {
      image = new ImageView("icon.png");
    }
    image.setFitWidth(30);
    image.setFitHeight(30);
    image.setX((xCoord / 1.28) - 15);
    image.setY((yCoord / 1.28) - 15);
    this.location = location;
    image.setOnMouseClicked(
        event -> {
          MapManager.getManager().openIconRequestWindow(this);
        });
  }

  public Icon(Location location, boolean isEquipment, ArrayList<ServiceRequest> requestsArr) {
    this.xCoord = location.getXCoord();
    this.yCoord = location.getYCoord();
    this.requestsArr = requestsArr;
    if (isEquipment) {
      image = new ImageView("Equipment.png");
    } else {
      image = new ImageView("markedIcon.png");
    }
    image.setFitWidth(30);
    image.setFitHeight(30);
    image.setX((xCoord / 1.28) - 15);
    image.setY((yCoord / 1.28) - 15);
    this.location = location;
    image.setOnMouseClicked(
        event -> {
          MapManager.getManager().openIconRequestWindow(this);
        });
  }

  public void addToRequests(ServiceRequest request) {
    requestsArr.add(request);
    image.setImage(new Image("markedIcon.png"));
  }

  public void removeRequests(ServiceRequest request) {
    requestsArr.remove(request);
    if (requestsArr.size() == 0) {
      image.setImage(new Image("icon.png"));
    }
  }
}
