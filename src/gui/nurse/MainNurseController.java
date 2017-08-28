package gui.nurse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.AlertObject;
import classes.Nurse;
import gui.Gui;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;

public class MainNurseController implements Initializable{
	
	@FXML
	private ListView<String> patientListView;
	@FXML
	private ListView<String> alertListView;
	@FXML
	private ListView<String> roomListView;
	@FXML
	private Accordion accord;
	@FXML
	private TitledPane patientPane;
	@FXML
	private TabPane tbpane;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	
	private Nurse nurseObject;
	private SingleSelectionModel<Tab> selectionModel;
	ListChangeListener<AlertObject>  AlertListObjectListener;
	private static boolean shutdown = false;
	
	@FXML
	private void alertFromDoctor(AlertObject a){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setContentText("Alert ID: " +a.getId()+ "\n" +
							"Doctor ID: " +a.getdoctor().getId()+ "\n" +
							"Doctor last name: " +a.getdoctor().getName());
		alert.setHeaderText("You have new alert from doctor!");
		alert.initOwner(patientListView.getScene().getWindow());
		alert.showAndWait();
	}
	
	@FXML
	private void alertFromRoom(AlertObject a){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setContentText("Alert ID: " +a.getId()+ "\n" +
							"Room ID: " +a.getRoom().getId()+ "\n" +
							"Assigned doctor: Dr. " +a.getdoctor().getName());
		alert.setHeaderText("You have new alert from room!");
		alert.initOwner(patientListView.getScene().getWindow());
		alert.showAndWait();
	}

	public void initialize(URL url, ResourceBundle rb){
		loadPatientListview();
		loadAlertListView();
		loadRoomListview();
		selectpatientListMenu();
		selectAlarmListMenu();
		selectRoomListMenu();
		selectionModel = tbpane.getSelectionModel();
	}
	
	public void initData(Nurse n){
		setNurseObject(n);
		firstName.setText(getNurseObject().getFirstName());
		lastName.setText(getNurseObject().getName());
		addListener();
		setShutDown(false);
	}
	private void setNurseObject(Nurse nurseObject){
		this.nurseObject = nurseObject;
	}
	public Nurse getNurseObject(){
		return this.nurseObject;
	}
	
	
	private void loadPatientListview(){
		ObservableList<String> patientListData = FXCollections.observableArrayList();
		patientListData.addAll("Vitals", "Add new patient");
		patientListView.setItems(patientListData);
	}
	
	private void loadAlertListView(){
		ObservableList<String> alertListData = FXCollections.observableArrayList();
		alertListData.addAll("Received (Rooms)", "Received (Doctors)");
		alertListView.setItems(alertListData);
	}
	
	private void loadRoomListview(){
		ObservableList<String> roomListData = FXCollections.observableArrayList();
		roomListData.addAll("My rooms");
		roomListView.setItems(roomListData);
	}
	
	public void selectpatientListMenu(){
		patientListView.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			
			if (nv == "Vitals"){
		
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/NursePatientVitals.fxml"));
					Node node = loader.load();
					
					NursePatientVitalsController controller = loader.<NursePatientVitalsController>getController();
					controller.initData(getNurseObject());
					
					Tab tb = new Tab("Vitals", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
				
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (nv == "Add new patient"){
				
				try {
					Gui.showAddNewPatientPage();
				
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	
	}
	
	public void selectAlarmListMenu(){
		alertListView.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			
			if (nv == "Received (Rooms)"){
		
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/NurseReceivedAlarmsRooms.fxml"));
					Node node = loader.load();
					
					NurseReceivedAlarmsRoomsController controller = loader.<NurseReceivedAlarmsRoomsController>getController();
					controller.initData(getNurseObject());
					
					Tab tb = new Tab("Received (Rooms)", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if(nv == "Received (Doctors)"){
				
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/NurseReceivedAlarmsDoctors.fxml"));
					Node node = loader.load();
					
					
					NurseReceivedAlarmsDoctorsController controller = loader.<NurseReceivedAlarmsDoctorsController>getController();
					controller.initData(getNurseObject());
					
					Tab tb = new Tab("Received (Doctor)", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
					}
			
		});
	
	}
	
	public void selectRoomListMenu(){
		roomListView.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			
			if (nv == "My rooms"){
		
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/NurseMyRooms.fxml"));
					Node node = loader.load();
					
					NurseMyRoomsController controller = loader.<NurseMyRoomsController>getController();
					controller.initData(getNurseObject());
					
					Tab tb = new Tab("My rooms", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	}
	
	private void addListener(){
		getNurseObject().getAlertList().addListener(AlertListObjectListener = new ListChangeListener<AlertObject>() {
			@Override
			public void onChanged(ListChangeListener.Change <? extends AlertObject> p){
				while(p.next()){
                    if(p.wasAdded()){
                       if (getLastAlert().getRoom() == null && shutdown == false){
                    	   callDoctorAlertRunnable(getLastAlert());
                     }
                    	   else if (getLastAlert().getRoom() != null && shutdown == false){
                    		   callRoomAlertRunnable(getLastAlert()); 
                       }
                    	   		else if (shutdown == true){
                    	   			// removes listener if scene is closed
                    	   			getNurseObject().getAlertList().removeListener(AlertListObjectListener);
                    	   		}
                    }
				}
			}
		
		});	
	}
	private AlertObject getLastAlert(){
		return getNurseObject().getAlertList().get(getNurseObject().getAlertList().size() - 1);
	}
	
	public static void setShutDown(boolean var){
		shutdown = var;
	}
	// runnable calls popup
	private void callRoomAlertRunnable(AlertObject a){
		 Platform.runLater(new Runnable(){
				@Override
				public void run() {
					alertFromRoom(a);
					}
     		   
     	   });
	}
	
	private void callDoctorAlertRunnable(AlertObject a){
		 Platform.runLater(new Runnable(){
				@Override
				public void run() {
					alertFromDoctor(a);
					}
    		   
    	   });
	}
}
