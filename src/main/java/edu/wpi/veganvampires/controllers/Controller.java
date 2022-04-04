package edu.wpi.veganvampires.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.veganvampires.main.Vdb;
import edu.wpi.veganvampires.manager.MapManager;
import edu.wpi.veganvampires.objects.Floor;
import edu.wpi.veganvampires.objects.Icon;
import edu.wpi.veganvampires.objects.Location;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public abstract class Controller extends Application {
  private Parent root;
  private Floor currFloor;

  @FXML private Pane mapPane;
  @FXML private ImageView mapImage;

  @FXML
  private JFXComboBox floorDropDown =
      new JFXComboBox<>(
          FXCollections.observableArrayList(
              "Ground Floor",
              "Lower Level 2",
              "Lower Level 1",
              "1st Floor",
              "2nd Floor",
              "3rd Floor"));

  /** Checks the value of the floor drop down and matches it with the corresponding map png */
  @FXML
  private void checkDropDown() {
    String url = floorDropDown.getValue().toString() + ".png";
    mapImage.setImage(new Image(url));
    System.out.println(floorDropDown.getValue());
    getFloor();
  }

  // Sets the mapImage to the corresponding floor dropdown and returns the floor string
  private String getFloor() {
    String result = "";
    switch (floorDropDown.getValue().toString()) {
      case "Ground Floor":
        currFloor = Vdb.mapManager.getFloor("G");
        result = "G";
        break;
      case "Lower Level 1":
        currFloor = Vdb.mapManager.getFloor("L1");
        result = "L1";
        break;
      case "Lower Level 2":
        currFloor = Vdb.mapManager.getFloor("L2");
        result = "L2";
        break;
      case "1st Floor":
        currFloor = Vdb.mapManager.getFloor("1");
        result = "1";
        break;
      case "2nd Floor":
        currFloor = Vdb.mapManager.getFloor("2");
        result = "2";
        break;
      case "3rd Floor":
        currFloor = Vdb.mapManager.getFloor("3");
        result = "3";
        System.out.println("3");
        break;
    }

    populateFloorIconArr();
    return result;
  }

  // Loads the floor's icons
  @FXML
  public void populateFloorIconArr() {
    mapPane.getChildren().clear();
    System.out.println("Here");
    for (Icon icon : currFloor.getIconList()) {
      mapPane.getChildren().add(icon.getImage());
    }
  }

  // Opens and manages the location adding form
  @FXML
  public void openIconFormWindow(MouseEvent event) {
    if (!MapManager.getManager().isRequestWindowOpen()) {
      MapManager.getManager().getTempIcon().setVisible(true);
      MapManager.getManager().getContent().getChildren().clear();
      // X and Y coordinates
      double xPos = event.getX() - 15;
      double yPos = event.getY() - 25;

      // Form
      TextField nodeIDField = new TextField();
      TextField nodeTypeField = new TextField();
      TextField shortNameField = new TextField();
      TextField longNameField = new TextField();
      Button submitIcon = new Button("Add icon");
      Button clearResponse = new Button("Clear Text");
      Button closeButton = new Button("Close");
      Label title = new Label("Add a Location");
      title.setTextFill(Color.WHITE);
      title.setAlignment(Pos.CENTER);
      // title.setAlignment(TextAlignment.CENTER);
      title.setFont(new Font("System Bold", 38));
      title.setWrapText(true);
      HBox titleBox = new HBox(15, title);

      titleBox.setStyle("-fx-background-color: #012D5Aff;");
      titleBox.setAlignment(Pos.CENTER);
      HBox buttonBox = new HBox(submitIcon, clearResponse, closeButton);
      buttonBox.setAlignment(Pos.CENTER);
      buttonBox.setSpacing(15);
      MapManager.getManager().getContent().getChildren().addAll(titleBox, buttonBox);

      nodeIDField.setPromptText("Node ID");
      nodeTypeField.setPromptText("Node Type");
      shortNameField.setPromptText("Short Name");
      longNameField.setPromptText("Long Name");
      nodeIDField.setMinWidth(250);
      nodeTypeField.setMinWidth(250);
      shortNameField.setMinWidth(250);
      longNameField.setMinWidth(250);
      submitIcon.setMinWidth(100);
      clearResponse.setMinWidth(100);

      submitIcon.setOnAction(
          event1 -> {
            if (!nodeIDField.getText().isEmpty()
                && !nodeTypeField.getText().isEmpty()
                && !shortNameField.getText().isEmpty()
                && !longNameField.getText().isEmpty()) {
              addIcon(
                  new Location(
                      nodeIDField.getText(),
                      xPos,
                      yPos,
                      getFloor(),
                      "Tower",
                      nodeTypeField.getText(),
                      longNameField.getText(),
                      shortNameField.getText()));
            } else {
              Text missingFields = new Text("Please fill all fields");
              missingFields.setFill(Color.RED);
              missingFields.setTextAlignment(TextAlignment.CENTER);
              MapManager.getManager().getContent().getChildren().add(missingFields);
              System.out.println("MISSING FIELD");
            }
          });
      clearResponse.setOnAction(
          event1 -> {
            nodeIDField.setText("");
            nodeTypeField.setText("");
            shortNameField.setText("");
            longNameField.setText("");
          });
      closeButton.setOnAction(
          event1 -> {
            MapManager.getManager().closePopUp();
            MapManager.getManager().getTempIcon().setVisible(false);
          });

      MapManager.getManager()
          .getContent()
          .getChildren()
          .addAll(nodeIDField, nodeTypeField, shortNameField, longNameField);
      // borderPane.centerProperty().setValue(content);

      // Place Icon
      MapManager.getManager().getTempIcon().setX(xPos);
      MapManager.getManager().getTempIcon().setY(yPos);
      if (!mapPane.getChildren().contains(MapManager.getManager().getTempIcon())) {
        System.out.println("X:" + xPos + " Y:" + yPos);
        MapManager.getManager().getTempIcon().setFitWidth(30);
        MapManager.getManager().getTempIcon().setFitHeight(30);
        mapPane.getChildren().add(MapManager.getManager().getTempIcon());
      }

      // Scene and Stage
      MapManager.getManager().getStage().setTitle("Add New Location");
      MapManager.getManager().showPopUp();
    }
  }

  // Adds icon to map
  private void addIcon(Location location) {
    MapManager.getManager().closePopUp();
    mapPane.getChildren().remove(MapManager.getManager().getTempIcon());
    MapManager.getManager().getFloor(getFloor()).addIcon(new Icon(location, false));
    MapManager.getManager().getTempIcon().setVisible(false);
    checkDropDown();
  }

  /**
   * Determines if a String is an integer or not
   *
   * @param input is a string
   * @return true if the string is an integer, false if not
   */
  public boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Determines if a String is an double or not
   *
   * @param input is a string
   * @return true if the string is an double, false if not
   */
  public boolean isDouble(String input) {
    try {
      Double.parseDouble(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  // Closes the program
  @Override
  public void stop() {
    System.out.println("Shutting Down");
    Platform.exit();
  }

  // Switches scene to the home page
  @FXML
  public void switchToHome(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getClassLoader().getResource("FXML/home.fxml")));
    switchScene(event);
  }

  // Switches scene to the location database
  @FXML
  public void switchToLocationDB(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getClassLoader().getResource("FXML/LocationDatabase.fxml"));
    root = loader.load();
    LocationController lc = loader.getController();
    lc.setElements();
    lc.resetPage();
    switchScene(event);
  }

  // Switches scene to the service request page
  @FXML
  public void switchToServiceRequest(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/serviceRequest.fxml")));
    switchScene(event);
  }

  // Switches scene to the lab request page
  @FXML
  public void switchToLabRequest(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/LabRequest.fxml")));
    switchScene(event);
    checkDropDown();
  }

  // Switches scene to the medicine delivery page
  @FXML
  public void switchToMedicineDelivery(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/MedicineDelivery.fxml")));
    switchScene(event);
  }

  // Switches scene to the medical equipment page
  @FXML
  public void switchToMedEquipDelivery(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/MedicalEquipmentDelivery.fxml")));
    switchScene(event);
  }

  // Switches scene to the sanitation page
  @FXML
  public void switchToSanitationRequests(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/SanitationRequest.fxml")));
    switchScene(event);
  }

  // Switches scene to the meal delivery page
  @FXML
  public void switchToMealDelivery(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/MealDelivery.fxml")));
    switchScene(event);
  }

  // Switches scene to the laundry request page
  @FXML
  public void switchToLaundryRequest(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/LaundryRequest.fxml")));
    switchScene(event);
  }

  // Switches scene to the religious request page
  @FXML
  public void switchToReligiousRequest(ActionEvent event) throws IOException {
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/ReligiousRequest.fxml")));
    switchScene(event);
  }

  // Switches scene to the religious request page
  @FXML
  public void switchToInternalPatientTransport(ActionEvent event) throws IOException {
    /*
    root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("FXML/ReligiousRequest.fxml")));
    switchScene(event);
    * */
  }

  // Switches scene to the rootW
  @FXML
  void switchScene(ActionEvent event) {
    MapManager.getManager().closePopUp();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
