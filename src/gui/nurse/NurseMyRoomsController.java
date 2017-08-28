package gui.nurse;

import classes.Nurse;
import classes.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class NurseMyRoomsController {

	@FXML
	private  TableView<Room> roomTable;
	
	@FXML
	private TableColumn<Room, String> roomID;
	@FXML
	private TableColumn<Room, String> patientCount;
	@FXML
	private Button refreshButton;
	
	private Nurse nurseObject;
	
	public void initData(Nurse n){
		setNurseObject(n);
		fillTable();
		//addListener();
	}
	
	private void setNurseObject(Nurse nurseObject){
		this.nurseObject = nurseObject;
	}
	private Nurse getNurseObject(){
		return this.nurseObject;
	}
	
	private void fillTable(){
		roomID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
		    }
		});
		patientCount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> p) {
		        return new SimpleStringProperty(Integer.toString(p.getValue().getPatientList().size()));
		    }
		});
		
		roomTable.setItems(getNurseObject().getRoomList());
	}
	
	@FXML
	public void refreshList(ActionEvent event){
		roomTable.refresh();
	}
	
	/*private void addListener(){
		getNurseObject().getRoomList().addListener(new ListChangeListener<Room>() {
			@Override
			public void onChanged(ListChangeListener.Change <? extends Room> p){
				while(p.next()){
                    if(p.wasAdded()){
                    	fillTable();
                    }
                    if(p.wasPermutated()){
                    	fillTable();
                    }
                    if(p.wasRemoved()){
                    	fillTable();
                    }
                    if(p.wasReplaced()){
                    	fillTable();
                    }
                    if(p.wasUpdated()){
                     
                    }
				}
			}
		
		});
	}*/
}
