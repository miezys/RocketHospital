package gui.doctor;

import java.io.IOException;

import classes.AlertObject;
import classes.Doctor;
import classes.Hospital;
import classes.Nurse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DoctorCreateNewAlertController {
	
	@FXML
	private Label confirmationMessage;
	@FXML
	private Button sendAlertButton;
	@FXML
	private ComboBox<String> staffListChoiceBox;
	
	private Doctor doctorObject;
	private ObservableList<Nurse> nurseList = FXCollections.observableArrayList(Hospital.getInstance().getNurseList());
	private ObservableList<String> nurseNameList = FXCollections.observableArrayList();
	private ObservableList<Nurse> nurseListToSendAlertTo = FXCollections.observableArrayList();
	@FXML
	private void initialize(){
		staffListChoiceBox.setItems(nurseNameList);
	}
	
	public void initData(Doctor a){
		setDoctorObject(a);
		getNurseNames();
	}
	
	public void setDoctorObject(Doctor doctor){
		this.doctorObject = doctor;
	}
	
	public Doctor getDoctorObject(){
		return this.doctorObject;
	}
	
	private void getNurseNames(){
		for(Nurse n: nurseList){
			nurseNameList.add(n.getName());
		}
	}
	
	private ObservableList<Nurse> getNurseToBeAlertedList(){
		for (Nurse n : nurseList){
			if (n.getName() == staffListChoiceBox.getSelectionModel().getSelectedItem().toString()){
				nurseListToSendAlertTo.add(n);
			}
		}
		return nurseListToSendAlertTo;
	}
	
	
	@FXML
	private void handleSendAlertButtonAction(ActionEvent event) throws IOException{		
		Stage stage = (Stage) sendAlertButton.getScene().getWindow();
		stage.close();
		new AlertObject(Hospital.getInstance().generateUniqueID("alert"),getDoctorObject(),getNurseToBeAlertedList());		
	}

}
