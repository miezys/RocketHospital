package gui.doctor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.AlertObject;
import classes.Doctor;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public class MainDoctorController implements Initializable {
	
	private Gui main;
	@FXML
	private ListView<String> patientListView;
	@FXML
	private ListView<String> alertListView;
	@FXML
	private Accordion accord;
	@FXML
	private TitledPane patientPane;
	@FXML
	private Button addNewPatientButton;
	@FXML
	private BorderPane InpatientListcontent;
	@FXML
	private TabPane tbpane;
	@FXML
	private TextField filterField;
	@FXML
	private Label doctorFirstName;
	@FXML
	private Label donctorLastName;
	
	private SingleSelectionModel<Tab> selectionModel;
	private Doctor doctor;
	private static boolean shutdown = false;
	ListChangeListener<AlertObject>  AlertListObjectListener;
	
	public void setDoctorObject(Doctor doctor){
		this.doctor = doctor;
	}
	
	public Doctor getDoctorObject(){
		return this.doctor;
	}
	
	@FXML
	private void alertMessage(AlertObject a){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setContentText("Alert ID: " +a.getId()+ "\n" +"Room ID: " +a.getRoom().getId());
		alert.setHeaderText("You have new alert!");
		alert.initOwner(patientListView.getScene().getWindow());
		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		loadPatientListview();
		loadAlertListview();
		selectpatientListMenu();
		selectAlertListMenu();
		selectionModel = tbpane.getSelectionModel();
		setShutDown(false);
	}
	
	// gets doctor object from gui.java (which is online)
	public void initData(Doctor a){
		setDoctorObject(a);
		doctorFirstName.setText(getDoctorObject().getFirstName());
		donctorLastName.setText(getDoctorObject().getName());
		addListener();
	}
	
	
	
	private void loadPatientListview(){
		ObservableList<String> patientListData = FXCollections.observableArrayList();
		patientListData.addAll("Inpatients","Outpatients");
		patientListView.setItems(patientListData);
	}
	
	private void loadAlertListview(){
		ObservableList<String> alertListData = FXCollections.observableArrayList();
		alertListData.addAll("Received","Create new");
		alertListView.setItems(alertListData);
	}
	
	public void selectpatientListMenu(){
		patientListView.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			
			if (nv == "Inpatients"){
		
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/DoctorInpatientScene.fxml"));
					Node node = loader.load();
					
					DoctorInpatientSceneController controller = loader.<DoctorInpatientSceneController>getController();
					controller.initData(getDoctorObject());
					
					Tab tb = new Tab("Inpatients", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
					
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(nv == "Outpatients"){
				
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/DoctorOutpatientScene.fxml"));
					Node node = loader.load();
					
					DoctorOutpatientSceneController controller = loader.<DoctorOutpatientSceneController>getController();
					controller.initData(getDoctorObject());
					
					Tab tb = new Tab("Outpatients", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
	}
	
	
	public void selectAlertListMenu(){
		alertListView.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) ->{
			
			if (nv == "Received"){
		
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Gui.class.getResource("/resources/fxmlViews/DoctorReceivedAlerts.fxml"));
					Node node = loader.load();
					
					DoctorReceivedAlertsController controller = loader.<DoctorReceivedAlertsController>getController();
					controller.initData(getDoctorObject());
					
					Tab tb = new Tab("Received Alerts", node);
					tbpane.getTabs().add(tb);
					selectionModel.selectLast();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	else if(nv == "Create new"){
				
		try {
			main.doctorCreateNewAlert(getDoctorObject());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			}
				
		
			
		});
	
	}
	
	private void addListener(){
		getDoctorObject().getAlertList().addListener(AlertListObjectListener = new ListChangeListener<AlertObject>() {
			
			@Override
			public void onChanged(ListChangeListener.Change <? extends AlertObject> p){
				while(p.next()){
					if(p.wasAdded()){
						if (getLastAlert().getRoom() != null && shutdown == false){
							// if alert has room and stage is open call popup
							callPopUpRunnable(getLastAlert());
							}
						else if (shutdown == true){
							// removes listener if scene is closed
							getDoctorObject().getAlertList().removeListener(AlertListObjectListener);
						}
						}
					}
				}
			});
	}
	
	// returns last alert added to the list
	private AlertObject getLastAlert(){
		return getDoctorObject().getAlertList().get(getDoctorObject().getAlertList().size() - 1);
	}
	
	public static void setShutDown(boolean var){
		shutdown = var;
	}
	// runnable calls popup
	private void callPopUpRunnable(AlertObject a){
		 Platform.runLater(new Runnable(){
				@Override
				public void run() {
					alertMessage(a);
					}
     		   
     	   });
	}

}

