package classes;

import javafx.collections.*;

public class AlertObject {

	private int id, patientID;
	private Room room;
	private Doctor doctor;
	private ObservableList<Nurse> nurseList;
	private int currentLifeTime;
	private boolean isDeleted;
	
	// Constructor for vitals.sendAlert().
	public AlertObject (int id, Room room, Doctor doctor)
	{
		this.id = id;
		this.room = room;
		this.doctor = doctor;
		this.nurseList = room.getNurseList();
		this.currentLifeTime = 0;
		this.isDeleted = false;

		addAlertToLists(doctor, this.nurseList);
	}
	// Constructor for doctor.sendAlert().
	public AlertObject (int id, Doctor doctor, ObservableList<Nurse> nurseList)
	{
		this.id = id;
		this.room = null;
		this.doctor = doctor;
		this.nurseList = nurseList;
		this.currentLifeTime = 0;
		this.isDeleted = false;

		addAlertToLists(doctor, nurseList);
	}
	
	public AlertObject (int id, int patientID, Room room, Doctor doctor, ObservableList<Nurse> nurseList) 
	{
		this.id = id;
		this.patientID = patientID;
		this.room = room;
		this.doctor = doctor;
		this.nurseList = nurseList;
		this.currentLifeTime = 0;
		this.isDeleted = false;

		addAlertToLists(doctor, nurseList);
	}
	// Constructor for database.load().
	public AlertObject (int id, Room room, Doctor doctor, ObservableList<Nurse> nurseList, int currentLifeTime) 
	{
		this.id = id;
		this.room = room;
		this.doctor = doctor;
		this.nurseList = nurseList;
		this.currentLifeTime = currentLifeTime;
		this.isDeleted = false;
		
		addAlertToLists(doctor, nurseList);
	}

	// Add alert to lists
	private void addAlertToLists(Doctor doctor, ObservableList<Nurse> nurseList) 
	{
		doctor.addAlertToList(this);
		for (Nurse n : nurseList) 
		{
			n.addAlertToList(this);
		}			
		Hospital.getInstance().addAlertToList(this);
	}
	
	// Getter id
	public int getId(){return this.id;}
	
	// Getter patientID
	public int getPatientID() {
		return this.patientID;
	}

	// Getter room
	public Room getRoom () {return this.room;}
	
	// Getter doctor
	public Doctor getdoctor () {return this.doctor;}
	
	// Getter nurse
	public ObservableList<Nurse> getNurseList () {return this.nurseList;}
	
	// Getter current life time
	public int getCurrentLifeTime() { return this.currentLifeTime; }
	
	// getter setter is Deleted
	public boolean isDeleted () {return this.isDeleted;}
	public void setDeleted (boolean isDeleted) {this.isDeleted = isDeleted;}
		
	// currentLifeTime++ every second
	public synchronized void incrementCurrentLifeTime() {currentLifeTime++;}
	
	// Set all references to null
	public void delete() 
	{	
		this.doctor.removeAlertFromList(this);
		
		for (Nurse n : this.nurseList) 
		{
			n.removeAlertFromList(this);
		}		
		
		this.nurseList = null;
		this.room = null;		
		this.doctor = null;
		this.isDeleted = true;
	}
}