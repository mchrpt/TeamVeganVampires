package edu.wpi.veganvampires.Interfaces;

import edu.wpi.veganvampires.EquipmentDelivery;

import java.sql.SQLException;
import java.util.ArrayList;
 
public interface EquipmentDeliveryImpl {

  ArrayList<EquipmentDelivery> getAllEquipmentDeliveries();

  void addEquipmentDelivery(String location, String equipment, String notes, int quantity) throws SQLException;

  void removeEquipmentDelivery(String equipment) throws SQLException;
}
