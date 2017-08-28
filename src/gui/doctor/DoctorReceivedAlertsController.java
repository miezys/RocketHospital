package gui.doctor;

import classes.AlertObject;
import classes.Doctor;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DoctorReceivedAlertsController {
	
	@FXML
	private TableView<AlertObject> alertTable;
	
	@FXML
	private TableColumn<AlertObject, String> alertID;
	@FXML
	private TableColumn<AlertObject, String> roomID;
	@FXML
	private TableColumn<AlertObject, String> doctorID;
	@FXML	
	private TableColumn<AlertObject, String> alertLifetime;
	@FXML
	private Label alertCounter;
	
	
	private Doctor doctorObject;
	ObservableList<AlertObject> allertListByDoctor = FXCollections.observableArrayList();
	
	public void setDoctorObject(Doctor doctor){
		this.doctorObject = doctor;
	}
	
	public Doctor getDoctorObject(){
		return this.doctorObject;
	}
	
	public void initData(Doctor a){
		setDoctorObject(a);
		filterAlerts();
		addListener();
		fillTable();
	}
	
	private void fillTable(){
		alertID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
		    }
		});
		roomID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getRoom().getId()));
		    }
		});
		doctorID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getdoctor().getId()));
		    }
		});
		alertLifetime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AlertObject, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<AlertObject, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getCurrentLifeTime()));
		    }
		});
		alertTable.setItems(allertListByDoctor);
	}
	
		
		private void filterAlerts(){
			allertListByDoctor.clear();
			for (AlertObject a : getDoctorObject().getAlertList()){
				if (getDoctorObject() == a.getdoctor() && a.getRoom() != null){
					allertListByDoctor.add(0, a);
				}
			}callAlertCounterRunnable();
		}
		
		private void addListener(){
			getDoctorObject().getAlertList().addListener(new ListChangeListener<AlertObject>() {
				@Override
				public void onChanged(ListChangeListener.Change <? extends AlertObject> p){
					while(p.next()){
	                    if(p.wasAdded()){
	                        filterAlerts();
	                    }
	                    if(p.wasPermutated()){
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
						alertCounter.setText("Alert count: "+allertListByDoctor.size());
						}
	 	   });
		}

}
