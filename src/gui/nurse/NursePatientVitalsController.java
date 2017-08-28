package gui.nurse;

import classes.Hospital;
import classes.Nurse;
import classes.Patient;
import classes.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class NursePatientVitalsController {
	@FXML
	private TableView<Patient> vitalsTable;
	
	@FXML
	private TableColumn<Patient, String> patientID;
	@FXML
	private TableColumn<Patient, String> room;
	@FXML
	private TableColumn<Patient, String> rateOfBreathing;
	@FXML
	private TableColumn<Patient, String> pulse;
	@FXML
	private TableColumn<Patient, String> bodyTemperature;
	@FXML
	private TableColumn<Patient, String> bloodPressure;
	

	
	private Nurse nurseObject;
	private static ObservableList<Room> roomList;
	private static ObservableList<Patient> patientList;
	private static ObservableList<Patient> patientListByRoomsByNurse = FXCollections.observableArrayList();
	
	
	
	public void initData(Nurse n){
		setNurseObject(n);
		roomList = (ObservableList<Room>) FXCollections.observableArrayList(getNurseObject().getRoomList());
		patientList = (ObservableList<Patient>) FXCollections.observableArrayList(Hospital.getInstance().getPatientList());
		filterPatientList();
		fillTable();
		addListener();
	}
	
	private void setNurseObject(Nurse nurseObject){
		this.nurseObject = nurseObject;
	}
	private Nurse getNurseObject(){
		return this.nurseObject;
	}
	
	private void fillTable(){
		patientID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
		    }
		});
		room.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getRoom().getId()));
		    }
		});
		rateOfBreathing.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getVitals().getRateOfBreathing()));
		    }
		});
		pulse.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getVitals().getPulse()));
		    }
		});
		bodyTemperature.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getVitals().getBodyTemperature()));
		    }
		});
		bloodPressure.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Patient, String> p) {
		        return new SimpleStringProperty(p.getValue().getVitals().getBloodPressure());
		    }
		});
		
		vitalsTable.setItems(patientListByRoomsByNurse);
	}
	
	public static void filterPatientList(){
		patientListByRoomsByNurse.clear();
		for(Patient p : patientList){
			for (Room r : roomList){
				if(p.getRoom() == r){
					patientListByRoomsByNurse.add(p);
				}
			}
			
		}
	}
	
	
	private void addListener(){
		patientList.addListener(new ListChangeListener<Patient>() {
			@Override
			public void onChanged(ListChangeListener.Change <? extends Patient> p){
				while(p.next()){
                    if(p.wasAdded()){
                        filterPatientList();
                    }
                    if(p.wasPermutated()){
                        filterPatientList();
                    }
                    if(p.wasRemoved()){
                        filterPatientList();
                    }
                    if(p.wasReplaced()){
                        filterPatientList();
                    }
                    if(p.wasUpdated()){
                        filterPatientList();
                    }
				}
			}
		
		});
	}

}
