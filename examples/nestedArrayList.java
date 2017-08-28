import java.util.ArrayList;
import java.util.List;
import classes.*;

public class nestedArrayList {

	public List<List> nestedListExample () 
	{
		// Create listCollection and Lists
		List<List> listCollection = new ArrayList<List>();
		List<Patient> patientList = new ArrayList<Patient>();
		List<Doctor> doctorList = new ArrayList<Doctor>();
		List<Nurse> nurseList = new ArrayList<Nurse>();
		
		/*
		 *  Do Stuff here (e.g. fill lists)
		 */
		
		// Add lists to listCollection and return
		listCollection.add(patientList);
		listCollection.add(doctorList);
		listCollection.add(nurseList);
		return listCollection;
	}
}
