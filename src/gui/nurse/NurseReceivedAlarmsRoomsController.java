package gui.nurse;

import java.net.URL;
import java.util.ResourceBundle;

import classes.AlertObject;
import classes.Nurse;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class NurseReceivedAlarmsRoomsController implements Initializable {
	
	@FXML
	private TableView<AlertObject> alertTable;
	
	@FXML
	private TableColumn<AlertObject, String> room;
	@FXML
	private TableColumn<AlertObject, String> assignedDoctorName;
	@FXML	
	private TableColumn<AlertObject, String> alertID;
	@FXML	
	private TableColumn<AlertObject, String> alertLifetime;
	@FXML
	private Label alertCounter;
	
	
	private Nurse nurseObject;
	private ObservableList<AlertObject> alertsFromRooms = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void initData(Nurse n){
		setNurseObject(n);
		filterAlerts();
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
		// fill the table 
	
		assignedDoctorName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(p.getValue().getdoctor().getName());
		    }
		});
		room.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getRoom().getId()));
		    }
		});
		alertID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
		    }
		});
		alertLifetime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getCurrentLifeTime()));
		    }
		});
		
		alertTable.setItems(alertsFromRooms);
	}
	
	private void filterAlerts(){
		alertsFromRooms.clear();
		for (AlertObject a: getNurseObject().getAlertList()){ 
			if (a.getRoom() != null){
				alertsFromRooms.add(0, a);
			}
		} callAlertCounterRunnable();
	}
	
	
	// listen to the alertlist changes, when changes occur, calls filterAlert method
	private void addListener(){
		getNurseObject().getAlertList().addListener(new ListChangeListener<AlertObject>() {
			@Override
			public void onChanged(ListChangeListener.Change <? extends AlertObject> p){
				while(p.next()){
                    if(p.wasAdded()){
                        filterAlerts();
                    }
                    if(p.wasRemoved()){
                    	filterAlerts();
                    }
                    if(p.wasReplaced()){
                    	filterAlerts();
                    }
                    if(p.wasUpdated()){
                    	filterAlerts();
                    }
             
				}
			}
		
		});
	}
	
	private void callAlertCounterRunnable(){
		 Platform.runLater(new Runnable(){
				@Override
				public void run() {
					alertCounter.setText("Alert count: "+alertsFromRooms.size());
					}
  	   });
	}

}
