package gui.nurse;

import java.net.URL;
import java.util.ResourceBundle;

import classes.Doctor;
import classes.Hospital;
import classes.MedicalIssue;
import classes.Patient;
import classes.Room;
import classes.Vitals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NurseAddNewPatientController implements Initializable {


	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private ComboBox<String> patientGender;
	@FXML
	private ComboBox<Integer> patientAge;
	@FXML
	private ComboBox<String> doctor;
	@FXML
	private ComboBox<String> room;
	@FXML
	private TextArea medicallIssueDescription;
	@FXML
	private TextField problem;
	@FXML
	private Button cancelButton;
	@FXML
	private Button saveButton;
	
	
	Patient patientObject;
	//private Doctor doctorObject;
	
	ObservableList<String> genderList = FXCollections.observableArrayList("male", "female");	//add values to the choice box
	ObservableList<Doctor> doctorList = (ObservableList<Doctor>) FXCollections.observableArrayList(Hospital.getInstance().getDoctorList());
	ObservableList<Room> roomList = (ObservableList<Room>) FXCollections.observableArrayList(Hospital.getInstance().getRoomList());
	ObservableList<String> doctorNameList = FXCollections.observableArrayList();
	ObservableList<String> roomIDsList = FXCollections.observableArrayList();
	ObservableList<Integer> ageList = FXCollections.observableArrayList();
	
	@FXML
	private void confirmationMessage(String message){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.initOwner(saveButton.getScene().getWindow());
		alert.show();
	}
	
	@FXML
	private void errorMessage(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.initOwner(saveButton.getScene().getWindow());
		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		collectDoctorNames();
		collectRoomIds();
		patientGender.setItems(genderList);
		patientGender.setVisibleRowCount(10);
		doctor.setItems(doctorNameList);
		doctor.setVisibleRowCount(10);
		room.setItems(roomIDsList);
		room.setVisibleRowCount(10);
		patientAge.setItems(getAgeList());
		patientAge.setVisibleRowCount(10);
		patientGender.getSelectionModel().selectFirst();
		patientAge.getSelectionModel().selectFirst();
		doctor.getSelectionModel().selectFirst();
		room.getSelectionModel().selectFirst();
	}
	
	@FXML	// action to be taken after "Save" button is called
	private void handleSaveButtonAction(ActionEvent event){
		
		if (allFieldsFilled()){
				if (room.getSelectionModel().getSelectedItem().toString().equals("Add as outpatient")){
			
						if(savePatientAsOutpatient()){
									closeAddPatientWindow();
									confirmationMessage("Patient has been saved as outpatient");
									
							}
							else if (!savePatientAsOutpatient()){
									errorMessage("Patient data could not be saved");
								}
					}
		
					else if (!room.getSelectionModel().getSelectedItem().toString().equals("Add as outpatient")){
							if(savePatientAsInpatient()){
									closeAddPatientWindow();
									confirmationMessage("Patient has been saved as inpatient");
										
								}
								else if (!savePatientAsInpatient()){
										errorMessage("Patient data could not be saved");
									}
						}
			}
		else {
			errorMessage("Please fill in all fields");
		}
	
	
	}
		
	
		
	@FXML // cancell button action
	private void handleCancelButtonAction(ActionEvent event){								// cancel button ActionHandler
		closeAddPatientWindow();
	}
	// saves patient as inpatient (with room object)
	private boolean savePatientAsInpatient(){
		try {
			int patID = Hospital.getInstance().generateUniqueID("patient");
			int patAge = (int) patientAge.getSelectionModel().getSelectedItem();
			String patFirstName = firstName.getText();
			String patLastName = lastName.getText();
			String patGender = patientGender.getSelectionModel().getSelectedItem().toString();
			Doctor patAssignedDoctor = getAssignedDoctorObject(doctor.getSelectionModel().getSelectedItem().toString());
			MedicalIssue patMedicalIssue = setMedicalIssue(problem.getText(), medicallIssueDescription.getText());
			Vitals patVitals = new Vitals();
			Room patRoom = getRoomObjectByID(Integer.parseInt(room.getSelectionModel().getSelectedItem().toString()));
			new Patient(patID,patAge,patFirstName,patLastName,patGender,patAssignedDoctor,patMedicalIssue,patVitals,patRoom);
			return true;
		} catch (NumberFormatException e) {
			return false;
			}
		}
	// saves patient as outpatient (without room object)
	private boolean savePatientAsOutpatient(){
		try {
			int patID = Hospital.getInstance().generateUniqueID("patient");
			int patAge = (int) patientAge.getSelectionModel().getSelectedItem();
			String patFirstName = firstName.getText();
			String patLastName = lastName.getText();
			String patGender = patientGender.getSelectionModel().getSelectedItem().toString();
			Doctor patAssignedDoctor = getAssignedDoctorObject(doctor.getSelectionModel().getSelectedItem().toString());
			MedicalIssue patMedicalIssue = setMedicalIssue(problem.getText(), medicallIssueDescription.getText());
			Vitals patVitals = new Vitals();
			//calls patient constructor
			new Patient(patID,patAge,patFirstName,patLastName,patGender,patAssignedDoctor,patMedicalIssue,patVitals);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	// creates new medicall issue object and returns it
	private MedicalIssue setMedicalIssue(String name, String description){
		MedicalIssue newIssue = new MedicalIssue(name, description);
		return newIssue;
	}
	// return doctor object by doctor Name, which was choosen on a choose box
	private Doctor getAssignedDoctorObject(String doctorName){
		Doctor doc = null;
		for (Doctor d : doctorList){
			if(d.getName() == doctorName){
				doc = d;
				break;
			}
		}return doc;
	}
	// return room object, which ID was choosen on choose box
	private Room getRoomObjectByID(int roomID){
		Room roo = null;
		for (Room r : roomList){
			if(r.getId() == roomID){
				roo = r;
			}
		}return roo;
	}
	// collects doctornames from doctorlist to fill the choose box
	private void collectDoctorNames(){
		for (Doctor d : doctorList ){
			doctorNameList.add(d.getName());
		}
	}
	// collects the roomids from roomlist to fill the choose box
	private void collectRoomIds(){
		roomIDsList.add("Add as outpatient");
		for (Room r: roomList){
			if(r.getFreeCapacity() != 0){
				roomIDsList.add(Integer.toString(r.getId()));
			}
		}
	}
	
	private void closeAddPatientWindow(){
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	private ObservableList<Integer> getAgeList(){
		for (int i=0; i<150; i++){
			ageList.add(i);
		}
		return ageList;
	}
	
	private boolean allFieldsFilled(){
	
		if (firstName.getText().equals("") ||
			lastName.getText().equals("") ||
			medicallIssueDescription.getText().equals("") ||
			problem.getText().equals("")
			){
			return false;
		}
		else return true;
	}
}

