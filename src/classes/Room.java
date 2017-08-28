package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class Room {

	private int id, maxCapacity;
	private ObservableList<Patient> patientList;
	private ObservableList<Nurse> nurseList;
	
	//Copy Object
	public Room(Room toCopy){
		this.id = toCopy.getId();
		this.maxCapacity = toCopy.getMaxCapacity();
		this.patientList = toCopy.getPatientList();
		this.nurseList = toCopy.getNurseList();
	}
	public Room(int maxCapacity) 
	{
		this.id = Hospital.getInstance().generateUniqueID("room");
		this.maxCapacity = maxCapacity;
		this.patientList = FXCollections.observableArrayList();
		this.nurseList = FXCollections.observableArrayList();
		
		Hospital.getInstance().getRoomList().add(this);
	}
	public Room(int id, int maxCapacity)
	{
		this.id = id;
		this.maxCapacity = maxCapacity;
		this.patientList = FXCollections.observableArrayList();
		this.nurseList = FXCollections.observableArrayList();
		
		Hospital.getInstance().getRoomList().add(this);
	}
	
	// Methods
	// Add & remove patient
	public boolean addPatientToRoom(Patient patient)
	{
		if (getFreeCapacity() > 0)
		{
			if (this.patientList.add(patient)) 
			{
				if (patient.getRoom() == null || !patient.getRoom().equals(this)) 
				{
					patient.setRoom(this);
					return true;	
				}
			} 
			else return false;
		}
		return false;
	}
	public boolean addPatientsToRoom(List<Patient> patientList)
	{
		if (getFreeCapacity() >= patientList.size())
		{
			if (this.patientList.addAll(patientList)) 
			{
				for (Patient p : patientList) 
				{
					if (p.getRoom() == null || !p.getRoom().equals(this)) p.setRoom(this);
				}
				return true;	
			}
			else return false;
		}
		return false;
	}
	public boolean removePatientFromRoom(Patient patient)
	{
		if (this.patientList.remove(patient)) 
		{
			if (patient.getRoom().equals(this)) patient.setRoom(null);
			return true;
		} 
		else return false;
	}
	public boolean removePatientsFromRoom(List<Patient> patientList)
	{
		if (this.patientList.removeAll(patientList)) 
		{
			for (Patient p : patientList) 
			{
				if (p.getRoom().equals(this)) p.setRoom(null);
			}
			return true;	
		}
		else return false;
	}
	
	// Add & remove Nurse
	public boolean addNurseToRoom(Nurse nurse)
	{
		if (this.nurseList.add(nurse)) 
		{
			if (!nurse.getRoomList().contains(this)) nurse.addRoomToList(this);
			return true;
		} 
		else return false;
	}
	public boolean addNursesToRoom(List<Nurse> nurseList)
	{
		if (this.nurseList.addAll(nurseList)) 
		{
			for (Nurse n : nurseList) 
			{
				if (!n.getRoomList().contains(this)) n.addRoomToList(this);
			}
			return true;	
		}
		else return false;
	}
	public boolean removeNurseFromRoom(Nurse nurse)
	{
		if (this.nurseList.remove(nurse)) 
		{
			if (nurse.getRoomList().contains(this)) nurse.addRoomToList(this);
			return true;
		} 
		else return false;
	}
	public boolean removeNursesFromRoom(List<Nurse> nurseList)
	{
		if (this.nurseList.removeAll(nurseList)) 
		{
			for (Nurse n : nurseList) 
			{
				if (n.getRoomList().contains(this)) n.removeRoomFromList(this);
			}
			return true;	
		}
		else return false;
	}
	
	// getter id
	public int getId() {return this.id;}

	// getter max capacity
	public int getMaxCapacity() {return this.maxCapacity;}
	
	// getter current capacity
	public int getOccupiedCapacity () {return patientList.size();}
	
	// getter free capacity
	public int getFreeCapacity () {return (getMaxCapacity() - patientList.size());}
	
	// getter patient list
	public ObservableList<Patient> getPatientList() {return this.patientList;}
	
	// getter nurse list
	public ObservableList<Nurse> getNurseList() {return this.nurseList;}
}
