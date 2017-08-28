package gui.doctor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gui.Gui;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import classes.*;

public class DoctorInpatientSceneController implements Initializable {
	private Gui main;
	
	@FXML 
	private Button editPatient;
	@FXML 
	private Button viewPatient;
	@FXML
	private TextField filterField;
	@FXML
	private Button resetVitalsButton;
	
	
	@FXML
	private TableView<Patient> inpatientTable;
	@FXML
	private TableColumn<Patient, String> patientID;
	@FXML
	private TableColumn<Patient, String> firstName;
	@FXML
	private TableColumn<Patient, String> lastName;
	@FXML	
	private TableColumn<Patient, String> patientGender;
	@FXML
	private TableColumn<Patient, String> age;
	@FXML
	private TableColumn<Patient, String> problem;
	@FXML
	private TableColumn<Patient, String> room;
	@FXML
	private Button alertButton;
	
	

	
	private static Doctor doctorObject;
	private Patient patientObject;
	static ObservableList<Patient> inPatientList = FXCollections.observableArrayList();
	
	@FXML
	private void errorMessage(){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Please select a patient from the list first");
		alert.setHeaderText(null);
		alert.initOwner(inpatientTable.getScene().getWindow());
		alert.showAndWait();
	}
	
	@FXML
	private void confirmationMessage(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setContentText("Vitals for selected patient has been resetted");
		alert.setHeaderText(null);
		alert.initOwner(inpatientTable.getScene().getWindow());
		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getSelectedPatient();
	}
	
	public void initData(Doctor a){
		setDoctorObject(a);	
		filterInpatientList();
		addListener();
		fillTable();
		patientFieldFilter();	
	}
	
	public void setDoctorObject(Doctor doctor){
		this.doctorObject = doctor;
	}
	
	public Doctor getDoctorObject(){
		return this.doctorObject;
	}
	
	
	private void fillTable(){
		patientID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
		    }
		});
		firstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(p.getValue().getFirstName());
		    }
		});
		lastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(p.getValue().getName());
		    }
		});
		patientGender.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(p.getValue().getGender());
		    }
		});
		problem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(p.getValue().getMedicalIssue().getMedicalIssueName());
		    }
		});
		room.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getRoom().getId()));
		    }
		});
		age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
	    @Override
	    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
	        return new SimpleStringProperty(Integer.toString(p.getValue().getAge()));
	    }
	});
		
		

		inpatientTable.setItems(inPatientList);

	}
	
	private void patientFieldFilter(){
		// Wrap the ObservableList in a FilteredList (initially display all data).
				FilteredList<Patient> filteredData = new FilteredList<>(inPatientList, p -> true);
				
				 //Set the filter Predicate whenever the filter changes.
				filterField.textProperty().addListener((observable, oldValue, newValue) -> {
		            filteredData.setPredicate(person -> {
		                //If filter text is empty, display all persons.
		                if (newValue == null || newValue.isEmpty()) {
		                    return true;
		                }

		                // Compare first name and last name of every person with filter text.
		                String lowerCaseFilter = newValue.toLowerCase();

		                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
		                    return true; // Filter matches first name.
		                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
		                    return true; // Filter matches last name.
		                }
		                return false; // Does not match.
		            });
		        });
				
				  // Wrap the FilteredList in a SortedList. 
		        SortedList<Patient> sortedData = new SortedList<>(filteredData);
		        
		     // Bind the SortedList comparator to the TableView comparator.
		        sortedData.comparatorProperty().bind(inpatientTable.comparatorProperty());
		        
		     //  Add sorted (and filtered) data to the table.
		        inpatientTable.setItems(sortedData);
				
			}
	
	private void getSelectedPatient(){									//reads the user selection and saves selected Object into nv
		inpatientTable.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			saveSelectedPatientObject(nv);								// calls the method below to save the selected object
		});
		
	}
	private void saveSelectedPatientObject(Patient patientObject){			// method to save selected patient object from the list
		this.patientObject = patientObject;
	}
	
	private Patient getSelectedPatientObject(){							// method to get selected patient id from the list
		return patientObject;
	}
	
	public static void filterInpatientList(){
		inPatientList.clear();
		for (Patient p: doctorObject.getPatientList()){
			if((p.getRoom() != null)){
				inPatientList.add(p);
			}
		}
	}

	
	@FXML
	private void handleEditPatientButtonAction(ActionEvent event) throws IOException{
		try{
			main.editPatientPage(getSelectedPatientObject());
		}
		catch(Exception e){
			errorMessage();
		}
									
	}
	@FXML
	private void handleviewPatientButtonAction(ActionEvent event) throws IOException{	
		try{
			main.showViewPatientPage(getSelectedPatientObject());	
		}
		catch(Exception e){
			errorMessage();
		}
	}
	@FXML
	private void resetVitals(ActionEvent event){
		// reset vitals from here
		try{
			getSelectedPatientObject().resetVitals();
			confirmationMessage();
		} catch(Exception e) {
			errorMessage();
		}
		
	}


	private void addListener(){
		doctorObject.getPatientList().addListener(new ListChangeListener<Patient>() {
			@Override
			public void onChanged(ListChangeListener.Change <? extends Patient> p){
				while(p.next()){
                    if(p.wasAdded()){
                        filterInpatientList();
                    }
                    if(p.wasPermutated()){
                        filterInpatientList();
                    }
                    if(p.wasRemoved()){
                        filterInpatientList();
                    }
                    if(p.wasReplaced()){
                        filterInpatientList();
                    }
                    if(p.wasUpdated()){
                        filterInpatientList();
                    }
				}
			}
		
		});
	}
	
	
	
}
