package edu.wpi.cs3733.d22.teamV.servicerequests;

import edu.wpi.cs3733.d22.teamV.main.RequestSystem;
import edu.wpi.cs3733.d22.teamV.main.Vdb;
import edu.wpi.cs3733.d22.teamV.objects.Employee;
import edu.wpi.cs3733.d22.teamV.observer.DirectionalAssoc;
import java.sql.Timestamp;
import java.time.Instant;

public class RobotRequest extends ServiceRequest {
  private Employee employee;
  private String nodeID;
  private int botID;

  public RobotRequest(int hospitalID, int botID, String nodeID, String details, String status) {
    employee = new Employee(hospitalID);
    this.timeMade = Timestamp.from(Instant.now());
    this.location = RequestSystem.getSystem().getLocation(nodeID);
    this.botID = botID;
    this.nodeID = nodeID;
    this.details = details;
    this.status = status;
    this.type = "Robot Request";
  }

  public Employee getEmployee() {
    return employee;
  }

  public int getEmployeeID() {
    return employee.getEmployeeID();
  }

  public int getBotID() {
    return botID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public String getDetails() {
    return details;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public void update(DirectionalAssoc directionalAssoc) {
    super.update(directionalAssoc);
    Vdb.requestSystem
        .getDao(RequestSystem.Dao.RobotRequest)
        .updateServiceRequest(this, getServiceID());
  }
}
