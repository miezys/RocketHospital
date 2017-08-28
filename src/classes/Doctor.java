package classes;

import java.util.List;
import javafx.collections.*;

public class Doctor extends Staff{

	private ObservableList<Patient> patientList;
	
	//Copy Object
	public Doctor(Doctor toCopy){
		super(toCopy.getId(), toCopy.getAge(), toCopy.getFirstName(), toCopy.getName(), toCopy.getGender());
		this.patientList = toCopy.getPatientList();
	}
	public Doctor(int id, int age, String firstName, String lastName, String gender){
		
		super(id, age, firstName, lastName, gender);
		this.patientList = FXCollections.observableArrayList();
		
		Hospital.getInstance().getDoctorList().add(this);
	}
	public Doctor(int id, int age, String firstName, String lastName, String gender, ObservableList<Patient> patientList){
		
		super(id, age, firstName, lastName, gender);
		this.patientList = patientList;
		
		for (Patient p : patientList) 
		{
			p.setAssignedDoctor(this);
		}
		
		Hospital.getInstance().getDoctorList().add(this);
	}
	
	// Methods patient ObservableList
	public boolean addPatientToList(Patient patient)
	{
		if (this.patientList.add(patient)) 
		{
			if (!patient.getAssignedDoctor().equals(this)) 
			{
				patient.setAssignedDoctor(this);
			} 
			return true;	
		}
		else return false;
	}
	public boolean addPatientsToList(List<Patient> patientList)
	{
		if (this.patientList.addAll(patientList)) 
		{
			for (Patient p : patientList) 
			{				
				if (!p.getAssignedDoctor().equals(this)) 
				{
					p.setAssignedDoctor(this);
				}
			}
			return true;
		} 
		else return false;
	}
	public boolean removePatientFromList(Patient patient)
	{
		if (this.patientList.remove(patient)) 
		{
			if (patient.getAssignedDoctor().equals(this)) 
			{
				patient.setAssignedDoctor(null);
			} 
			return true;	
		}
		else return false;
	}
	public boolean removePatientsFromList(List<Patient> patientList)
	{
		if (this.patientList.removeAll(patientList)) 
		{
			for (Patient p : patientList) 
			{				
				if (p.getAssignedDoctor().equals(this)) 
				{
					p.setAssignedDoctor(null);
				}
			}
			return true;
		} 
		else return false;
	}
	
	// getter setter patient ObservableList
	public ObservableList<Patient> getPatientList(){return this.patientList;}
	public void setPatientList(ObservableList<Patient> patientList){this.patientList = patientList;}
	
	public void delete (Doctor doctor)
	{
		for (Patient p : this.patientList) 
		{
			p.setAssignedDoctor(doctor);
		}
		this.patientList = null;
		Hospital.getInstance().removeDoctorFromList(this);
		this.setDeleted(true);
	}
	
	// Methods 
	public void sendAlert (ObservableList<Nurse> nurseList) 
	{
		new AlertObject (Hospital.getInstance().generateUniqueID("alert"), this, nurseList);		
	}
}
