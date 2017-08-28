package classes;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Nurse extends Staff {

	private ObservableList<Room> roomList;

	//Copy object
	public Nurse(Nurse toCopy){
		super(toCopy.getId(), toCopy.getAge(), toCopy.getFirstName(), toCopy.getName(), toCopy.getGender());
		this.roomList = toCopy.getRoomList();
	}
	public Nurse(int id, int age, String firstName, String lastName, String gender) {

		super(id, age, firstName, lastName, gender);
		roomList = FXCollections.observableArrayList();
		
		Hospital.getInstance().getNurseList().add(this);
	}
	public Nurse(int id, int age, String firstName, String lastName, String gender, ObservableList<AlertObject> alertList) {

		super(id, age, firstName, lastName, gender, alertList);
		roomList = FXCollections.observableArrayList();
		
		Hospital.getInstance().getNurseList().add(this);
	}

	// getter Assigned Rooms
	public ObservableList<Room> getRoomList() {return this.roomList;}
	
	// Methods
	public boolean addRoomToList(Room room) 
	{
		if (this.roomList.size() < 10) 
		{
			for (Room r : this.roomList) 
			{
				if (r.equals(room)) return false;
			}
			if (this.roomList.add(room)) 
			{
				if (!room.getNurseList().contains(this)) room.addNurseToRoom(this);
				return true;
			} 
			else return false;
		} 
		else return false;
	}
	public boolean addRoomsToList(List<Room> roomList) 
	{
		if (this.roomList.size() < 10 && (this.roomList.size()+roomList.size()) <= 10) 
		{
			if (this.roomList.addAll(roomList)) 
			{
				for (Room r : roomList) 
				{
					if (!r.getNurseList().contains(this)) r.addNurseToRoom(this); 
				}
				return true;	
			}
			else return false;
		} 
		else return false;
	}
	public boolean removeRoomFromList(Room room) 
	{
		if (this.roomList.remove(room)) 
		{
			if (room.getNurseList().contains(this)) 
			{
				room.removeNurseFromRoom(this);
			}
			return true;	
		}
		else return false;
	}
	public boolean removeRoomsFromList(List<Room> roomList) 
	{
		if (this.roomList.removeAll(roomList))
		{
			for (Room r : roomList) 
			{
				if (r.getNurseList().contains(this)) r.removeNurseFromRoom(this);
			}
			return true;
		} 
		else return false;
	}
	
	public void delete() 
	{
		for (Room r : this.roomList) 
		{
			r.getNurseList().remove(this);
		}

		Hospital.getInstance().removeNurseFromList(this);
		this.roomList = null;
		this.setDeleted(true);
	}
}
