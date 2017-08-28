package gui.doctor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.ws.Action;
import gui.*;
import classes.Hospital;
import classes.Patient;
import classes.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPatientController implements Initializable {

	// personal details
	
		@FXML
		private TextField age;
		@FXML
		private TextField firstName;
		@FXML
		private TextField lastName;
		@FXML
		private ComboBox<String> patientGender;
		@FXML
		private TextArea medicallIssueDescription;
		@FXML
		private TextField problem;
		@FXML
		private Button cancelButton;
		@FXML
		private Button saveButton;
		@FXML
		private ComboBox<String> room;
		
		Patient patientObject;
		
		ObservableList<String> genderList = FXCollections.observableArrayList("male", "female");	//add values to the choice box
		ObservableList<String> roomIdList = FXCollections.observableArrayList();
		ObservableList<Room> roomList = Hospital.getInstance().getRoomList();
		
		@FXML
		private void confirmationMessage(){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Patient data has been saved");
			alert.setHeaderText(null);
			alert.initOwner(age.getScene().getWindow());
			alert.showAndWait();
		}
		
		@FXML
		private void errorMessage(String message){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(message);
			alert.setHeaderText(null);
			alert.initOwner(age.getScene().getWindow());
			alert.showAndWait();
		}
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			patientGender.setItems(genderList);
		}
		
		public void setPatientObject(Patient patientObject){
			this.patientObject = patientObject;
		}
		public Patient getPatientObject(){
			return this.patientObject;
		}
		
		public void initData(Patient patientObject){
			setPatientObject(patientObject);
			firstName.setText(patientObject.getFirstName());
			lastName.setText(patientObject.getName());
			age.setText(Integer.toString(patientObject.getAge()));
			medicallIssueDescription.setText(patientObject.getMedicalIssue().getMedicalIssueDescription());
			problem.setText(patientObject.getMedicalIssue().getMedicalIssueName());
			patientGender.setValue(patientObject.getGender());
			getRoomIdList();
			if (patientObject.getRoom() == null){
				room.setValue("Outpatient");
			}
			else if (patientObject.getRoom() != null){
				room.setValue(Integer.toString(patientObject.getRoom().getId()));
			}
			room.setItems(roomIdList);
		}
		
		@FXML
		private void handleCancelButtonAction(ActionEvent event){								// cancel button ActionHandler
			closeStage();
		}
		
		@FXML
		private void handleSaveButtonAction(ActionEvent event){	
			
			if (allFieldsFilled()){
			
					if(saveEditedData()){
							closeStage();
							confirmationMessage();
						try {
								DoctorInpatientSceneController.filterInpatientList();
							} catch (Exception e) {
								System.out.println("Inpatients window closed");
							}
						try {
								DoctorOutpatientSceneController.filterOutpatientList();
							} catch (Exception e) {
								System.out.println("Outpatients window closed");
							}
						}
						else if (!saveEditedData()){
								errorMessage("Patient data could not be saved");
							}
			}
			else if(!allFieldsFilled()){
				errorMessage("All fields must be filled");
			}
			
		
		}
		
		// returns room object by roomID from choicebox
		private Room getRoom(int roomID){
			Room roomObject = null;
			for (Room r: roomList){
				if(r.getId() == roomID){
					roomObject = r;
					break;
				}
			}return roomObject;
		}
		// room id list for choicebox, adds the roomIDs from rooms with free capacity and from selected patients
		private void getRoomIdList(){
				roomIdList.add("Outpatient");
				
				if (getPatientObject().getRoom() == null){
					for (Room r : roomList){
						if (r.getFreeCapacity() != 0){
							roomIdList.add(Integer.toString(r.getId()));
						}
					}
				}
					else if (getPatientObject().getRoom() != null){
						for (Room r : roomList){
							if (r.getFreeCapacity() != 0){
								roomIdList.add(Integer.toString(r.getId()));
							}
							else if (getPatientObject().getRoom().getId() == r.getId()){
								roomIdList.add(Integer.toString(r.getId()));
							}
								
						}
				}
		}
		
		private boolean saveEditedData(){
			try {
				getPatientObject().setFirstName(firstName.getText());
				getPatientObject().setName(lastName.getText());
				getPatientObject().setAge(Integer.parseInt(age.getText()));
				getPatientObject().getMedicalIssue().setMedicalIssueName(problem.getText());
				getPatientObject().getMedicalIssue().setMedicalIssueDescription(medicallIssueDescription.getText());
				// change room
				if (room.getSelectionModel().getSelectedItem().equals("Outpatient")){
					// choicebox selection = outpatient => setroom null
					getPatientObject().setRoom(null);
				}
				else if (!room.getSelectionModel().getSelectedItem().equals("Outpatient")) {
				// choicebox selection =  roomID => setroom roomObject
					getPatientObject().setRoom(getRoom(Integer.parseInt(room.getSelectionModel().getSelectedItem())));
				}
				getPatientObject().setGender(patientGender.getSelectionModel().getSelectedItem());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
			
			
		}
		
		private void closeStage(){
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
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

