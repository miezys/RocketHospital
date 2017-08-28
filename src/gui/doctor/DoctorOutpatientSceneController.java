package gui.doctor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Doctor;
import classes.Patient;
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

public class DoctorOutpatientSceneController implements Initializable {
	private Gui main;
	
	@FXML 
	private Button editPatient;
	@FXML 
	private Button viewPatient;
	@FXML
	private TextField filterField;

	
	
	@FXML
	private TableView<Patient> outpatientTable;
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

	

	
	private static Doctor doctorObject;
	private Patient patientObject;
	static ObservableList<Patient> outPatientList = FXCollections.observableArrayList();
	
	@FXML
	private void errorMessage(){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Please select a patient from the list first");
		alert.setHeaderText(null);
		alert.initOwner(outpatientTable.getScene().getWindow());
		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getSelectedPatient();
	}
	
	public void initData(Doctor a){
		setDoctorObject(a);
		filterOutpatientList();
		fillTable();
		patientFieldFilter();
		addListener();
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
		
		
		age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
	    @Override
	    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
	        return new SimpleStringProperty(Integer.toString(p.getValue().getAge()));
	    }
	});
		
		
		outpatientTable.setItems(outPatientList);
		
	
	}
	
	private void patientFieldFilter(){
		// Wrap the ObservableList in a FilteredList (initially display all data).
				FilteredList<Patient> filteredData = new FilteredList<>(outPatientList, p -> true);
				
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
		        sortedData.comparatorProperty().bind(outpatientTable.comparatorProperty());
		        
		     //  Add sorted (and filtered) data to the table.
		        outpatientTable.setItems(sortedData);
				
			}
	
	private void getSelectedPatient(){									//reads the user selection and saves selected Object into nv
		outpatientTable.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			saveSelectedPatientObject(nv);								// calls the method below to save the selected object
		});
		
	}
	private void saveSelectedPatientObject(Patient patientObject){		// method to save selected patient object from the list
		this.patientObject = patientObject;
	}
	private Patient getSelectedPatientObject(){							// method to get selected patient id from the list
		return patientObject;
	}
	
	public static void filterOutpatientList(){
		outPatientList.clear();
		for (Patient p: doctorObject.getPatientList()){
			if((p.getRoom() == null)){
				outPatientList.add(p);
				
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
	
	
	private void addListener(){
		doctorObject.getPatientList().addListener(new ListChangeListener<Patient>() {
			@Override
			public void onChanged(ListChangeListener.Change <? extends Patient> p){
				while(p.next()){
                    if(p.wasAdded()){
                        filterOutpatientList();
                    }
                    if(p.wasPermutated()){
                        filterOutpatientList();
                    }
                    if(p.wasRemoved()){
                        filterOutpatientList();
                    }
                    if(p.wasReplaced()){
                        filterOutpatientList();
                    }
                    if(p.wasUpdated()){
                        filterOutpatientList();
                    }
				}
			}
		
		});
	}

	
	

}
