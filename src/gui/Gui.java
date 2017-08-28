package gui;

import java.io.IOException;
import classes.Doctor;
import classes.Hospital;
import classes.Nurse;
import classes.Patient;
import gui.doctor.DoctorCreateNewAlertController;
import gui.doctor.EditPatientController;
import gui.doctor.MainDoctorController;
import gui.doctor.ViewPatientController;
import gui.nurse.MainNurseController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import resources.*;


public class Gui extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	
	private static Stage doctorStage;
	private static Stage nurseStage;


	
	private Hospital rocketHospital;
	
	public static void launch () 
	{
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Gui.primaryStage = primaryStage;
		Gui.primaryStage.setTitle("Rocket Hospital V1.0");
		
		showMainView();
		showMainItems();
	}

	public static void showMainView() throws IOException{	
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Gui.class.getResource(Paths.mainViewPath));
			mainLayout = loader.load();
			Scene scene = new Scene(mainLayout);		
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.resizableProperty().setValue(Boolean.FALSE);
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
	}

	public static void showMainItems() throws IOException{							
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/MainItems.fxml"));
		AnchorPane mainItems = loader.load();
		mainLayout.setCenter(mainItems);
	}
	
	
	public static void showMainDoctor(Doctor doctorObject) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/MainDoctor.fxml"));
		AnchorPane mainDoctor = loader.load();
		
		MainDoctorController controller = loader.<MainDoctorController>getController();
		controller.initData(doctorObject);

		doctorStage = new Stage();
		doctorStage.setTitle("Doctor Page");
		doctorStage.initModality(Modality.WINDOW_MODAL);

		Scene scene = new Scene(mainDoctor);
		doctorStage.setScene(scene);
		doctorStage.show();
		
		doctorStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		  {
		      public void handle(WindowEvent e){
		          System.out.println("test");  
		          try {
		        	   controller.setShutDown(true);
		          } 
		          catch (Exception e1) {
		               e1.printStackTrace();
		          }
		      }
		   });
	}
	
	
	public static void showMainNurse(Nurse nurseObject) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/MainNurse.fxml"));
		AnchorPane mainNurse = loader.load();
		
		MainNurseController controller = loader.<MainNurseController>getController();
		controller.initData(nurseObject);

		nurseStage = new Stage();
		nurseStage.setTitle("Nurse Page");
		nurseStage.initModality(Modality.WINDOW_MODAL);

		Scene scene = new Scene(mainNurse);
		nurseStage.setScene(scene);
		nurseStage.show();
		
		nurseStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		  {
		      public void handle(WindowEvent e){
		          System.out.println("test");  
		          try {
		        	   controller.setShutDown(true);
		          } 
		          catch (Exception e1) {
		               e1.printStackTrace();
		          }
		      }
		   });
	

	}

	public static void showAddNewPatientPage() throws IOException{					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/NurseAddNewPatient.fxml"));
		BorderPane addNewpatient = loader.load();

		Stage addDialogStage = new Stage();
		addDialogStage.setTitle("Add new Patient");
		addDialogStage.initModality(Modality.WINDOW_MODAL);
		addDialogStage.resizableProperty().setValue(Boolean.FALSE);

		Scene scene = new Scene(addNewpatient);
		addDialogStage.setScene(scene);
		addDialogStage.show();

	}

	public static void editPatientPage(Patient patientObject) throws IOException{					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/EditPatient.fxml"));
		BorderPane editPatient = loader.load();
		
		EditPatientController controller = loader.<EditPatientController>getController();
		controller.initData(patientObject);

		Stage addDialogStage = new Stage();
		addDialogStage.setTitle("Edit Patient");
		addDialogStage.initModality(Modality.WINDOW_MODAL);
		addDialogStage.resizableProperty().setValue(Boolean.FALSE);

		Scene scene = new Scene(editPatient);
		addDialogStage.setScene(scene);
		addDialogStage.show();

	}

	public static void showViewPatientPage(Patient patientObject) throws IOException{					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/ViewPatient.fxml"));
		BorderPane viewPatient= loader.load();
		
		ViewPatientController controller = loader.<ViewPatientController>getController();
		controller.initData(patientObject);

		Stage addDialogStage = new Stage();
		addDialogStage.setTitle("View patient");
		addDialogStage.initModality(Modality.WINDOW_MODAL);
		addDialogStage.resizableProperty().setValue(Boolean.FALSE);

		Scene scene = new Scene(viewPatient);
		addDialogStage.setScene(scene);
		addDialogStage.show();

	}

	public static void doctorCreateNewAlert(Doctor doctorObject) throws IOException{					
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Gui.class.getResource("/resources/fxmlViews/DoctorCreateNewAlert.fxml"));
		BorderPane newAlert = loader.load();
		
		DoctorCreateNewAlertController controller = loader.<DoctorCreateNewAlertController>getController();
		controller.initData(doctorObject);

		Stage addDialogStage = new Stage();
		addDialogStage.setTitle("Create new alert");
		addDialogStage.initModality(Modality.WINDOW_MODAL);
		
		addDialogStage.resizableProperty().setValue(Boolean.FALSE);

		Scene scene = new Scene(newAlert);
		addDialogStage.setScene(scene);
		addDialogStage.showAndWait();

	}


	


	

}
