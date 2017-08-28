package gui.doctor;

import classes.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ViewPatientController {
	
	@FXML
	private Label patientID;
	@FXML
	private Label room;
	@FXML
	private Label age;
	@FXML
	private Label gender;
	@FXML
	private Label bloodPressure;
	@FXML
	private Label rateOfBreathing;
	@FXML
	private Label pulseRate;
	@FXML
	private Label bodyTemperature;
	@FXML
	private Label assignedDoctor;
	@FXML
	private TextFlow medicalIssueName;
	@FXML
	private TextFlow medicalIssueDescription;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	
	private Patient patientObject;
	
	private void setPatientObject(Patient patientObject){
		this.patientObject = patientObject;
	}
	private Patient getPatientObject(){
		return this.patientObject;
	}
	
	public void initData(Patient patientObject){
		setPatientObject(patientObject);
		setPatientData();
		
	}
	
	public void setPatientData(){
		firstName.setText(patientObject.getFirstName());
		lastName.setText(patientObject.getName());
		patientID.setText(Integer.toString(patientObject.getId()));
		if (patientObject.getRoom() == null){
			room.setText("Outpatient");
		}else {
			room.setText(Integer.toString(patientObject.getRoom().getId()));
		}
		age.setText(Integer.toString(patientObject.getAge()));
		gender.setText(patientObject.getGender());
		assignedDoctor.setText(patientObject.getAssignedDoctor().getName());
		Text medIssue = new Text(patientObject.getMedicalIssue().getMedicalIssueName());
		medicalIssueName.getChildren().add(medIssue); // textflow takes nodes as parameter
		Text medIssueDesc = new Text(patientObject.getMedicalIssue().getMedicalIssueDescription());
		medicalIssueDescription.getChildren().add(medIssueDesc); // textflow takes nodes as parameter
		bloodPressure.setText(patientObject.getVitals().getBloodPressure());
		rateOfBreathing.setText(Integer.toString(patientObject.getVitals().getRateOfBreathing()));
		pulseRate.setText(Integer.toString(patientObject.getVitals().getPulse()));
		bodyTemperature.setText(Integer.toString(patientObject.getVitals().getBodyTemperature()));
	}
	

}
