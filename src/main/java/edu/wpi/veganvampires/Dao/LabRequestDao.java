package edu.wpi.veganvampires.Dao;

import edu.wpi.veganvampires.Features.LabRequest;
import edu.wpi.veganvampires.Interfaces.LabRequestImpl;
import edu.wpi.veganvampires.Vdb;
import java.util.ArrayList;
import java.util.List;

public class LabRequestDao implements LabRequestImpl {
  private static ArrayList<LabRequest> allLabRequests;

  /** Initialize the array list */
  public LabRequestDao() {
    allLabRequests = new ArrayList<LabRequest>();
    // TODO: Add info from the database to the local arraylist
  }

  @Override
  public List<LabRequest> getAllLabRequests() {
    return allLabRequests;
  }

  @Override
  public void addLabRequest(
      int userID, int patientID, String firstName, String lastName, String lab) {
    LabRequest labRequest = new LabRequest(userID, patientID, firstName, lastName, lab);

    System.out.println("Adding to local arraylist...");
    allLabRequests.add(labRequest);
    updateLabRequest(labRequest);
  }

  private void updateLabRequest(LabRequest labRequest) {
    System.out.println("Sending to database...");
    Vdb.addLabRequest(labRequest);
  }

  @Override
  public void removeLabRequest() {} // TODO
}
