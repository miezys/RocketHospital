package threads;

import classes.Doctor;
import classes.Hospital;
import classes.Nurse;
import classes.Patient;
import classes.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import program.Database;


public class CopyAndSaveAllLists implements Runnable{
	
	private ObservableList<Patient> copyPatientList(ObservableList<Patient> hospitalPatientList){
		ObservableList<Patient> tmpPatientList = FXCollections.observableArrayList();
		
		for (Patient patient : hospitalPatientList) {
			tmpPatientList.add(new Patient(patient));
		}
		
		return tmpPatientList;
	}
	
	private  ObservableList<Doctor> copyDoctorList(ObservableList<Doctor> hospitalDoctorList){
		ObservableList<Doctor> tmpDoctorList = FXCollections.observableArrayList();
		
		for (Doctor doctor : hospitalDoctorList) {
			tmpDoctorList.add(new Doctor(doctor));	
		}
		
		return tmpDoctorList;
	}

	private ObservableList<Nurse> copyNurseList(ObservableList<Nurse> hospitalNurseList){
		ObservableList<Nurse> tmpNurseList = FXCollections.observableArrayList();
		
		for (Nurse nurse : hospitalNurseList) {
			tmpNurseList.add(new Nurse(nurse));		
		}
		
		return tmpNurseList;
	}
	
	private ObservableList<Room> copyRoomList(ObservableList<Room> hospitalRoomList){
		ObservableList<Room> tmpRoomList = FXCollections.observableArrayList();
		
		for (Room room : Hospital.getInstance().getRoomList()) {
			tmpRoomList.add(new Room(room));	
		}
		
		return tmpRoomList;
	}
	
	@Override
	public void run() {
		try {
			Database.save(
					copyPatientList(Hospital.getInstance().getPatientList()),
					copyDoctorList(Hospital.getInstance().getDoctorList()),
					copyNurseList(Hospital.getInstance().getNurseList()), 
					copyRoomList(Hospital.getInstance().getRoomList()));
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
