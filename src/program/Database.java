package program;
import java.sql.Connection;
import javafx.collections.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import classes.*;
import javafx.collections.ObservableList;
import util.StringBuilder;

/**
 * This class consists of two methods that save or load
 * values from the SQLite database. <br>
 * This class and its functions are used in the RocketHospital project, and are
 * custom made to provide a certain functionality needed.
 * 
 * @author Max Ehringhausen
 * @version 2.3.9
 * @see java.sql
 */
public class Database {

	/**
	 * Loads all data from the SQLite database and puts it into lists.
	 * 
	 * @return A list containing lists <br>
	 * List< doctorList, nurseList, roomList, patientList >
	 */
	@SuppressWarnings("unused")
	public static void load() {

		ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
		ObservableList<Nurse> nurseList = FXCollections.observableArrayList();
		ObservableList<Room> roomList = FXCollections.observableArrayList();
		
		Connection c = null;
		Statement stmt = null;

		try {

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.db");
			
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
			
		try {
			
			//+++++++++++++++++++++++++++++++GET DOCTORS
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT *FROM Person NATURAL JOIN Doctor;");
			while (rs.next()) {
				int doctorId = rs.getInt("ID");
				int doctorAge = rs.getInt("age");
				String doctorFirstName = rs.getString("firstName");
				String doctorLastName = rs.getString("lastName");
				String doctorGender = rs.getString("gender");
				String doctorLogin = rs.getString("login");
				String doctorPassword = rs.getString("password");

				Doctor temp = new Doctor (doctorId, doctorAge, doctorFirstName, doctorLastName, doctorGender);
				temp.setLoginName(doctorLogin);
				temp.setPassword(doctorPassword);
				
				doctorList.add(temp);

			}
			System.out.println("Doctor List created!");
			
			//+++++++++++++++++++++++++++++++GET NURSES
			rs = stmt.executeQuery(
					"SELECT * FROM Person NATURAL JOIN Nurse;");
			while (rs.next()) {
				int nurseId = rs.getInt("id");
				int nurseAge = rs.getInt("age");
				String nurseFirstName = rs.getString("firstName");
				String nurseLastName = rs.getString("lastName");
				String nurseGender = rs.getString("gender");
				String nurseLogin = rs.getString("login");
				String nursePassword = rs.getString("password");
        
				Nurse temp = new Nurse(nurseId, nurseAge, nurseFirstName, nurseLastName, nurseGender);
				temp.setLoginName(nurseLogin);
				temp.setPassword(nursePassword);
				
				nurseList.add(temp);

			}
			System.out.println("NurseList created!");
			
			//+++++++++++++++++++++++++++++++GET ROOMS AND FILL IN NURSES
			rs = stmt.executeQuery(
					"SELECT * FROM Room;");
			while (rs.next()) {
				int roomId = rs.getInt("ID");
				int roomMaxCapacity = rs.getInt("capacity");
				String roomNurseList = rs.getString("nurseList");
				
				if(roomId != 0){
				Room temp = new Room(roomId, roomMaxCapacity);
				if(roomNurseList != null){
					String[] tmp = roomNurseList.split("\\|");
					int[] nurseIdList = new int[tmp.length];

					for(int i = 0;i < tmp.length;i++){
						if(tmp[i] != "|"){
							nurseIdList[i] = Integer.parseInt(tmp[i]);
							}
						}
				
						for(Nurse n : nurseList){
							for(int i = 0;i < nurseIdList.length;i++){
								if(n.getId() ==  nurseIdList[i])
								{
									temp.addNurseToRoom(n);
									n.addRoomToList(temp);
								}
							}
						}
					}
				roomList.add(temp);
				}
			}
			System.out.println("RoomList created!");
			
			//+++++++++++++++++++++++++++++++GET PATIENTS
			rs = stmt.executeQuery(
					"SELECT * FROM Person NATURAL JOIN Patient JOIN MedicalIssue JOIN Vitals " +
					"WHERE Patient.ID = MedicalIssue.patientID AND Patient.ID = Vitals.patientID;");
			while (rs.next()) {
				int patientId = rs.getInt("patientID");
				int patientAge = rs.getInt("age");
				String patientFirstName = rs.getString("firstName");
				String patientLastName = rs.getString("lastName");
				String patientGender = rs.getString("gender");
				int patientDoctorId = rs.getInt("doctorID");
				int patientRoomId = rs.getInt("roomID");
				String patientIssueName = rs.getString("name");
				String patientIssueDesc = rs.getString("description");
				int patientRateOfBreathing = rs.getInt("rateOfBreathing");
				int patientPulse = rs.getInt("pulse");
				int patientBodyTemp = rs.getInt("bodyTemperature");
				String patientBloodPressure = rs.getString("bloodPressure");
				
				//+++++++++++++++++++++++++++++++FIND DOCTOR OF PATIENT
				Doctor patientDoc = null;
				for (Doctor d : doctorList)
				{
					if (d.getId() == patientDoctorId )
					{
						patientDoc = d;
						break;
					}
				}
				
				Patient tempPatient = new Patient(patientId, patientAge, patientFirstName, patientLastName, patientGender, patientDoc,
						new MedicalIssue(patientIssueName, patientIssueDesc), new Vitals(patientRateOfBreathing, patientPulse, patientBodyTemp, patientBloodPressure));
				
				//+++++++++++++++++++++++++++++++ADD PATIENT TO ROOM
				for(Room r : roomList)
				{
					if(r.getId() == patientRoomId){
						r.addPatientToRoom(tempPatient);
						tempPatient.setRoom(r);
					}
				}
			}
			System.out.println("PatientList created!");
			
			//+++++++++++++++++++++++++++++++GET ALERTS
			rs = stmt.executeQuery(
					"SELECT * FROM Alert;");
			while (rs.next()) {
				int alertId = rs.getInt("ID");
				int alertDocId = rs.getInt("doctorID");
				int alertRoomId = rs.getInt("roomID");
				String alertNurseListString = rs.getString("nurseList");
				int alertCurrentLifetime = rs.getInt("currentLifetime");
				
				Room tempRoom = null;
				Doctor tempDoctor = null;
				ObservableList<Nurse> tempNurseList = FXCollections.observableArrayList();
				int[] nurseIdList = null;
				
				if(alertNurseListString != null){
					String[] tmp = alertNurseListString.split("\\|");
					nurseIdList = new int[tmp.length];

					for(int i = 0;i < tmp.length;i++){
						if(tmp[i] != "|"){
							nurseIdList[i] = Integer.parseInt(tmp[i]);
						}
					}
					for (Nurse n : nurseList){
						for(int i = 0;i < nurseIdList.length;i++){
							if(n.getId() ==  nurseIdList[i]){
								tempNurseList.add(n);
							}
						}
					}
				}

				for (Doctor d : doctorList){
					if(d.getId() == alertDocId){
						tempDoctor = d;
					}
				}
				
				for (Room r : roomList){
					if(r.getId() == alertRoomId){
						tempRoom = r;
					}
				}				
				
				AlertObject temp = new AlertObject(alertId, tempRoom, tempDoctor, tempNurseList, alertCurrentLifetime);
								
			}
			System.out.println("AlertLists for Doctors and Nurses created!");
			
			rs.close();
			c.close();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}

	
/**
 * Saves all data to the database by overwriting existing data.
 * 
 * @param doctorList
 * @param nurseList
 * @param roomList
 * @param patientList
 * @throws Exception 
 */

	public static void save (ObservableList<Patient> patientList, ObservableList<Doctor> doctorList, ObservableList<Nurse> nurseList,ObservableList<Room> roomList) throws Exception {
				
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.db");
			c.setAutoCommit(false);
			
			//+++++++++++++++++++++++++++++++DELETE EVERYTHING
			stmt = c.createStatement();
			stmt.executeUpdate("DELETE FROM medicalIssue;");
			stmt.executeUpdate("DELETE FROM Vitals;");
			stmt.executeUpdate("DELETE FROM Alert;");
			stmt.executeUpdate("DELETE FROM Patient;");
			stmt.executeUpdate("DELETE FROM Room;");
			stmt.executeUpdate("DELETE FROM Person;");
			stmt.executeUpdate("DELETE FROM Doctor;");
			stmt.executeUpdate("DELETE FROM Nurse;");
			stmt.executeUpdate("DELETE FROM sqlite_sequence;");
			
			System.out.println("Old data is being removed.");
			
			//+++++++++++++++++++++++++++++++GET THE ROOMS
			int nullRaumId = 0;
			int nullRaumCap = 1000000;
			stmt.executeUpdate("INSERT INTO Room (ID, capacity)VALUES("+ nullRaumId +", "+ nullRaumCap +");");
			for(Room r : roomList){
				stmt.executeUpdate("INSERT INTO Room (ID, capacity, nurseList)VALUES("+ r.getId() +", "+ r.getMaxCapacity() +", '"+ StringBuilder.parseListToString(r.getNurseList()) +"');");
			}
			System.out.println("Rooms are being saved.");
			
			//+++++++++++++++++++++++++++++++GET THE NURSES
			int n = 0;
			while(nurseList.size() > n){						
				stmt.executeUpdate("INSERT INTO Person (ID, firstName, lastName, age, gender)VALUES("+ nurseList.get(n).getId() +", '"+ nurseList.get(n).getFirstName() +"', '"+ nurseList.get(n).getName() +"', "+ nurseList.get(n).getAge() +", '"+ nurseList.get(n).getGender() +"');");
				stmt.executeUpdate("INSERT INTO Nurse (ID, login, password)VALUES("+ nurseList.get(n).getId() +", '"+ nurseList.get(n).getLoginName() +"', '"+ nurseList.get(n).getPassword() +"');");
				n++;
			}
			System.out.println("Nurses are being saved.");
			
			//+++++++++++++++++++++++++++++++GET THE DOCTORS
			n = 0;
			while(doctorList.size() > n){
				stmt.executeUpdate("INSERT INTO Person (ID, firstName, lastName, age, gender)VALUES("+ doctorList.get(n).getId() +", '"+ doctorList.get(n).getFirstName() +"', '"+ doctorList.get(n).getName() +"', "+ doctorList.get(n).getAge() +", '"+ doctorList.get(n).getGender() +"');");
				stmt.executeUpdate("INSERT INTO Doctor (ID, login, password)VALUES("+ doctorList.get(n).getId() +", '"+ doctorList.get(n).getLoginName() +"', '"+ doctorList.get(n).getPassword() +"');");
				n++;
			}
			System.out.println("Doctors are being saved.");
			
			//+++++++++++++++++++++++++++++++GET THE PATIENTS
			n = 0;
			while(patientList.size() > n){
				stmt.executeUpdate("INSERT INTO Person (ID, firstName, lastName, age, gender)VALUES("+ patientList.get(n).getId() +", '"+ patientList.get(n).getFirstName() +"', '"+ patientList.get(n).getName() +"', "+ patientList.get(n).getAge() +", '"+ patientList.get(n).getGender() +"');");
				if(patientList.get(n).getRoom() != null){
					stmt.executeUpdate("INSERT INTO Patient (ID, doctorID, roomID)VALUES("+ patientList.get(n).getId() +", "+ patientList.get(n).getAssignedDoctor().getId() +", "+ patientList.get(n).getRoom().getId() +");");
				}else{
					stmt.executeUpdate("INSERT INTO Patient (ID, doctorID, roomID)VALUES("+ patientList.get(n).getId() +", "+ patientList.get(n).getAssignedDoctor().getId() +", "+ 0 +");");
				}
				n++;
			}
			System.out.println("Patients are being saved.");
			
			//+++++++++++++++++++++++++++++++GET THE PATIENT DATA (VITALS/MEDICAL ISSUES)
			n = 0;
			while(patientList.size() > n){
				stmt.executeUpdate("INSERT INTO medicalIssue (patientID, name, description)VALUES("+ patientList.get(n).getId() +", '"+ patientList.get(n).getMedicalIssue().getMedicalIssueName() +"', '"+ patientList.get(n).getMedicalIssue().getMedicalIssueDescription() +"');");
				stmt.executeUpdate("INSERT INTO Vitals (patientID, rateOfBreathing, pulse, bodyTemperature, bloodPressure)VALUES("+ patientList.get(n).getId() +", "+ patientList.get(n).getVitals().getRateOfBreathing() +", "+ patientList.get(n).getVitals().getPulse() +", "+ patientList.get(n).getVitals().getBodyTemperature() +", '"+ patientList.get(n).getVitals().getBloodPressure() +"');");
				n++;
			}
			System.out.println("Patient data is being saved.");
			
			//+++++++++++++++++++++++++++++++GET THE ALERTS
			List <AlertObject> tempAlertList = Hospital.getInstance().getAlertList();
			for (AlertObject a : tempAlertList){
				if(a.getRoom() == null){
					stmt.executeUpdate("INSERT INTO Alert (ID, doctorID, roomID, currentLifetime, nurseList)VALUES("+ a.getId() +", "+ a.getdoctor().getId() +", "+ 0 +", "+ a.getCurrentLifeTime() +", '"+ StringBuilder.parseListToString(a.getNurseList()) +"');");
				}else{
					stmt.executeUpdate("INSERT INTO Alert (ID, doctorID, roomID, currentLifetime, nurseList)VALUES("+ a.getId() +", "+ a.getdoctor().getId() +", "+ a.getRoom().getId() +", "+ a.getCurrentLifeTime() +", '"+ StringBuilder.parseListToString(a.getNurseList()) +"');");
				}
			}
			System.out.println("Alerts are being saved.");

			c.commit();

			System.out.println("++EVERYTHING NOW SAVED++");
		} catch (Exception e) {
			c.rollback();
			System.out.println(e);
		} finally {
			c.close();
		}
	}

}