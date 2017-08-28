package gui.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import classes.Doctor;
import classes.Hospital;
import classes.Nurse;
import gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainItemsController implements Initializable {
	private Gui main;
	
	@FXML
	private TextField loginField;
	@FXML
	private Label errorMessage;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label label;
	@FXML
	private Button loginButton;
	
	List<Doctor> doctorList = Hospital.getInstance().getDoctorList();
	List<Nurse> nurseList = Hospital.getInstance().getNurseList();
	
	
	Doctor doctorObject = null;
	Nurse nurseObject = null;
	
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		loginButton.setDefaultButton(true);
	}
	
	@FXML
	
	private void handleLoginButtonAction(ActionEvent event) throws IOException{	
		
		if (isDoctor()){
			clearFields();
			main.showMainDoctor(doctorObject);
		}else if (isNurse()){
			clearFields();
			main.showMainNurse(nurseObject);
		}else {
			errorMessage.setText("Wrong login details");
			errorMessage.setTextFill(Color.RED);
		}
	}
	
	private boolean isDoctor(){
		Iterator<Doctor> iterD = doctorList.iterator();
		String login = loginField.getText();
		String password = passwordField.getText();
		Doctor doctor;
		boolean isDoctor = false;
		
		while (iterD.hasNext()){
			doctor = iterD.next();
			if(login.equals(doctor.getLoginName()) && password.equals(doctor.getPassword())){
				isDoctor = true;
				doctorObject = doctor;
				break;
			}
		}return isDoctor;
	}
	
	private boolean isNurse(){
		Iterator<Nurse> iterN = nurseList.iterator();
		String login = loginField.getText();
		String password = passwordField.getText();
		Nurse nurse;
		boolean isNurse = false;
		
		while (iterN.hasNext()){
			nurse = iterN.next();
			if(login.equals(nurse.getLoginName()) && password.equals(nurse.getPassword())){
				isNurse = true;
				nurseObject = nurse;
				break;
			}
		}return isNurse;
		
	}
	private void clearFields(){
		loginField.clear();
		passwordField.clear();
	}


	
}
