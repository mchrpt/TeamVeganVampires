package edu.wpi.veganvampires.interfaces;

import edu.wpi.veganvampires.objects.ReligiousRequest;
import java.util.List;

public interface ReligiousRequestImpl {

  List<ReligiousRequest> getAllReligiousRequest();

  void addReligiousRequest(
      String firstName,
      String lastName,
      int patientID,
      int userID,
      boolean Christian,
      boolean Jewish,
      boolean Protestant,
      boolean Islam,
      boolean Muslim,
      boolean Buddhist,
      boolean Hindu,
      boolean Other,
      String specialRequests);

  List<ReligiousRequest> allReligiousRequest();

  void addReligousRequest(
      String firstName,
      String lastName,
      int patientID,
      int userID,
      boolean Christian,
      boolean Jewish,
      boolean Protestant,
      boolean Islam,
      boolean Muslim,
      boolean Buddhist,
      boolean Hindu,
      boolean Other,
      String specialRequests);

  void removeReligousRequest();
}