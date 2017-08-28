package classes;

import java.io.IOException;
import java.io.PrintWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import resources.Const;
import util.Randomizer;
/**
 * 
 * Class contains all Lists we need in our Hospital. 
 * 
 */
public class Hospital {

	private ObservableList<Patient> patientList;
	private ObservableList<Doctor> doctorList;
	private ObservableList<Nurse> nurseList;
	private ObservableList<Room> roomList;
	private ObservableList<AlertObject> alertList;
	
	private static Hospital instance = null;
	
	private Hospital ()
	{
		try {
			patientList = FXCollections.observableArrayList();
			doctorList = FXCollections.observableArrayList();
			nurseList = FXCollections.observableArrayList();
			roomList = FXCollections.observableArrayList();
			alertList = FXCollections.observableArrayList();
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Method that returns a hospital object. if instance is null, this method creates a new object.
	 * 
	 * @return An instance of the Hospital Object
	 */
	public static Hospital getInstance () 
	{
		if (instance == null) 
		{
			instance = new Hospital();
		}
		return instance;
	}
	
	// getter setter
	// getter setter patientList
	public ObservableList<Patient> getPatientList () 
	{
		return this.patientList;
	}
	public boolean addPatientToList (Patient p) 
	{
		if (this.patientList.add(p)) return true;
		return false;
	}
	public boolean addPatientsToList (ObservableList<Patient> pList) 
	{
		if (this.patientList.addAll(pList)) return true;
		return false;
	}
	public boolean removePatientFromList (Patient p) 
	{
		if (this.patientList.remove(p)) return true;
		return false;
	}
	public boolean removePatientsFromList (ObservableList<Patient> pList) 
	{
		if (this.patientList.removeAll(pList)) return true;
		return false;
	}
	// getter setter doctorList
	public ObservableList<Doctor> getDoctorList () 
	{
		return this.doctorList;
	}
	public boolean addDoctorToList (Doctor d) 
	{
		if (this.doctorList.add(d)) return true;
		return false;
	}
	public boolean addDoctorsToList (ObservableList<Doctor> dList) 
	{
		if (this.doctorList.addAll(dList)) return true;
		return false;
	}
	public boolean removeDoctorFromList (Doctor d) 
	{
		if (this.doctorList.remove(d)) return true;
		return false;
	}
	public boolean removeDoctorsFromList (ObservableList<Doctor> dList) 
	{
		if (this.doctorList.removeAll(dList)) return true;
		return false;
	}
	// getter setter nurseList
	public ObservableList<Nurse> getNurseList () 
	{
		return this.nurseList;
	}
	public boolean addNurseToList (Nurse n) 
	{
		if (this.nurseList.add(n)) return true;
		return false;
	}
	public boolean addNursesToList (ObservableList<Nurse> nList) 
	{
		if (this.nurseList.addAll(nList)) return true;
		return false;
	}
	public boolean removeNurseFromList (Nurse n) 
	{
		if (this.nurseList.remove(n)) return true;
		return false;
	}
	public boolean removeNursesFromList (ObservableList<Nurse> nList) 
	{
		if (this.nurseList.removeAll(nList)) return true;
		return false;
	}
	// getter setter roomList
	public ObservableList<Room> getRoomList () 
	{
		return this.roomList;
	}
	public boolean addRoomToList (Room r) 
	{
		if (this.roomList.add(r)) return true;
		return false;
	}
	public boolean addRoomsToList (ObservableList<Room> rList) 
	{
		if (this.roomList.addAll(rList)) return true;
		return false;
	}
	// getter setter alertList
	public ObservableList<AlertObject> getAlertList () 
	{
		return this.alertList;
	}
	public boolean addAlertToList (AlertObject a) 
	{
		if (this.alertList.add(a)) return true;
		return false;
	}
	public boolean addAlertsToList (ObservableList<AlertObject> aList) 
	{
		if (this.alertList.addAll(aList)) return true;
		return false;
	}
	public boolean removeAlertFromList (AlertObject a) 
	{
		if (this.alertList.remove(a)) return true;
		return false;
	}
	public boolean removeAlertsFromList (ObservableList<AlertObject> aList) 
	{
		if (this.alertList.removeAll(aList)) return true;
		return false;
	}
	
	// Methods
	// generate unique IDs
	public int generateUniqueID (String s) 
	{
		int id;
		switch (s.toLowerCase()) 
		{
			case "doctor": 
			{
				if (this.doctorList.size() > 0) {id = doctorList.get(doctorList.size()-1).getId(); id++;}
				else {id = 100000;}
			}
			break;
			case "nurse": 
			{
				if (nurseList.size() > 0) {id = nurseList.get(nurseList.size()-1).getId(); id++;id++;}
				else {id = 200000;}
			}
			break;
			case "patient":
			{
				if (patientList.size() > 0) {id = patientList.get(patientList.size()-1).getId();id++;}
				else {id = 300000;}
			}
			break;
			case "alert":
			{
				if (alertList.size() > 0) {id = alertList.get(alertList.size()-1).getId();id++;}
				else {id = 500000;}
			}
			break;
			case "room":
			{
				if (roomList.size() > 0) {id = roomList.get(roomList.size()-1).getId();id++;}
				else {id = 100;}
			}
			break;
		default:
			id = -1;
		}
		return id;
	}
	
	// populate the hospital randomly for each boolean = true.
	public void populate(boolean populateWithPatients, boolean populateWithNurses, boolean populateWithDoctors, boolean populateWithRooms) 
	{
		if (populateWithRooms) 
		{
			int amountRooms;
			int roomCapacity;
			
			amountRooms = roomCapacity = 0;
			roomList.clear();
			
			while (amountRooms < Const.Randomizer.HOSPITAL_MIN_ROOMS)
			{
				amountRooms = Randomizer.generateNumber(Const.Randomizer.HOSPITAL_MAX_ROOMS);
			}
			
			for (int i = 0; i < amountRooms; i++) 
			{
				while (roomCapacity < Const.Randomizer.ROOM_MIN_CAPACITY) 
				{
					roomCapacity = Randomizer.generateNumber(Const.Randomizer.ROOM_MAX_CAPACITY); 
				}
				new Room (roomCapacity);
			}
		}
		
		if (populateWithNurses)
		{
			int id, age, amountNurses, roomOffset;
			String foreName, surName, gender;
			Room room;
		
			amountNurses = roomOffset = 0;
			room = roomList.get(0);
			
			for (Nurse n : nurseList) 
			{
				n.delete();
			}
						
			if (roomList.size() < Const.Randomizer.HOSPITAL_ROOMS_PER_NURSE) 
			{
				while (amountNurses < Const.Randomizer.HOSPITAL_MIN_NURSES_PER_ROOM) 
				{
					amountNurses = Const.Randomizer.HOSPITAL_ROOMS_PER_NURSE;
				}
			}
			else 
			{
				while (amountNurses < Const.Randomizer.HOSPITAL_MIN_NURSES_PER_ROOM) 
				{
					amountNurses = Const.Randomizer.HOSPITAL_ROOMS_PER_NURSE;
				}
				System.out.println("Roomlist: "+roomList.size());
				System.out.println("amountNurses before: "+amountNurses);
				amountNurses *= Math.ceil((float)roomList.size()/Const.Randomizer.HOSPITAL_ROOMS_PER_NURSE);
				System.out.println("amountNurses after: "+amountNurses);
			}
			
			for (int i = 0; i < amountNurses; i++) 
			{
				id = generateUniqueID("nurse");
				age = Randomizer.generateAge(true);
				gender = Randomizer.generateGender();
				surName = Randomizer.generateSurName();
				
				if (gender.equals("male")) foreName = Randomizer.generateForeName(true);
				else foreName = Randomizer.generateForeName(false);
				
				new Nurse (id, age, foreName, surName, gender);
			}	
			
			for (int i = 0; i < roomList.size(); i++) 
			{
				room = roomList.get(i);
				room.addNurseToRoom(nurseList.get(roomOffset+0));				
				if (roomOffset+1 < nurseList.size()) room.addNurseToRoom(nurseList.get(roomOffset+1));
				if (roomOffset+2 < nurseList.size())room.addNurseToRoom(nurseList.get(roomOffset+2));
				if (roomOffset+3 < nurseList.size())room.addNurseToRoom(nurseList.get(roomOffset+3));
				
				System.out.println("offset: "+roomOffset);
				if (i != 0 && i % Const.Randomizer.HOSPITAL_ROOMS_PER_NURSE == 0) roomOffset += Const.Randomizer.HOSPITAL_ROOMS_PER_NURSE;
			}		
		}
		
		if (populateWithDoctors) 
		{
			int id, age, amountDoctors;
			String foreName, surName, gender;
			
			for (Doctor d : doctorList) 
			{
				d.delete(null);
			}
			
			amountDoctors = roomList.size();
			
			for (int i = 0; i < amountDoctors; i++) 
			{
				id = generateUniqueID("doctor");
				age = Randomizer.generateAge(true);
				gender = Randomizer.generateGender();
				surName = Randomizer.generateSurName();
				
				if (gender.equals("male")) foreName = Randomizer.generateForeName(true);
				else foreName = Randomizer.generateForeName(false);
				
				new Doctor (id, age, foreName, surName, gender);
			}
		}
		
		if (populateWithPatients) 
		{
			int id, age;
			String foreName, surName, gender;
			MedicalIssue medicalIssue;
			Patient patient;
			Room room;
			Doctor doctor;
			
			for (Patient p : patientList) 
			{
				p.delete();
			}
			
			for (int j = 0; j < roomList.size(); j++) 
			{
				room = roomList.get(j);
				doctor = doctorList.get(j);
				
				for (int i = 0; i < Math.ceil((float)room.getMaxCapacity()/2); i++) 
				{
					id = generateUniqueID("patient");
					age = Randomizer.generateAge(true);
					gender = Randomizer.generateGender();
					surName = Randomizer.generateSurName();
					medicalIssue = Randomizer.generateMedicalIssue();
					
					if (gender.equals("male")) foreName = Randomizer.generateForeName(true);
					else foreName = Randomizer.generateForeName(false);
					
					patient = new Patient (id, age, foreName, surName, gender, doctor, room);
					patient.setMedicalIssue(medicalIssue);
				}
			}
		}
	}
	
	public void printCurrentLoginData () 
	{
		try
		{
		    PrintWriter writer = new PrintWriter("logins.txt", "UTF-8");
		    writer.println("Doctors:");
		    for (Doctor d : doctorList) 
		    {
		    	writer.println(d.getLoginName()+" : "+d.getPassword());
		    }
		    writer.println("");
		    writer.println("Nurses:");
		    for (Nurse n : nurseList) 
		    {
		    	writer.println(n.getLoginName()+" : "+n.getPassword());
		    }
		    writer.close();
		} catch (IOException e) 
		{
		   System.out.println ("unable to write current login data");
		}
	}
}
