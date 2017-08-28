package classes;

public class Patient extends Person{
	
	private MedicalIssue medicalIssue;
	private Doctor assignedDoctor;
	private Vitals vitals;
	private Room room;
	private boolean alertSend = false;
	
	//Copy Object
	public Patient(Patient toCopy){
		super(toCopy.getId(), toCopy.getAge() , toCopy.getFirstName(), toCopy.getName(), toCopy.getGender());
		this.assignedDoctor = toCopy.getAssignedDoctor();
		this.medicalIssue = toCopy.getMedicalIssue();
		this.vitals = toCopy.getVitals();
		this.room = toCopy.getRoom();
	}
	
	public Patient(	int id, int age, String firstName, String lastName, String gender, Doctor assignedDoctor){
		
		super(id, age, firstName, lastName, gender);
		this.assignedDoctor = assignedDoctor;
		this.medicalIssue = null;
		this.vitals = new Vitals();
		this.room = null;
		
		assignedDoctor.addPatientToList(this);
		Hospital.getInstance().addPatientToList(this);
	}	
	public Patient(	int id, int age, String firstName, String lastName, String gender, Doctor assignedDoctor, MedicalIssue medicalIssue){
		
		super(id, age, firstName, lastName, gender);
		this.assignedDoctor = assignedDoctor;
		this.medicalIssue = medicalIssue;
		this.vitals = new Vitals();
		this.room = null;
		
		assignedDoctor.addPatientToList(this);
		Hospital.getInstance().addPatientToList(this);
	}		
	public Patient(	int id, int age, String firstName, String lastName, String gender, Doctor assignedDoctor, Room room){
		
		super(id, age, firstName, lastName, gender);
		this.assignedDoctor = assignedDoctor;
		this.medicalIssue = null;
		this.vitals = new Vitals();
		this.room = room;
		
		assignedDoctor.addPatientToList(this);
		room.addPatientToRoom(this);
		Hospital.getInstance().addPatientToList(this);
	}	
	public Patient(	int id, int age, String firstName, String lastName, String gender, Doctor assignedDoctor, MedicalIssue medicalIssue, Vitals vitals){
		
		super(id, age, firstName, lastName, gender);
		this.assignedDoctor = assignedDoctor;
		this.medicalIssue = medicalIssue;
		this.vitals = vitals;
		this.room = null;
		
		assignedDoctor.addPatientToList(this);
		Hospital.getInstance().addPatientToList(this);
	}	
	public Patient(	int id, int age, String firstName, String lastName, String gender, Doctor assignedDoctor, MedicalIssue medicalIssue, Vitals vitals, Room room){
		
		super(id, age, firstName, lastName, gender);
		this.assignedDoctor = assignedDoctor;
		this.medicalIssue = medicalIssue;
		this.vitals = vitals;
		this.room = room;
		
		assignedDoctor.addPatientToList(this);
		room.addPatientToRoom(this);
		Hospital.getInstance().addPatientToList(this);
	}
	// getter setter MedicalIssue
	public MedicalIssue getMedicalIssue(){return this.medicalIssue;}
	public void setMedicalIssue(MedicalIssue medicalIssue) {this.medicalIssue = medicalIssue;}
	
	// getter setter assigned Doctor Id
	public Doctor getAssignedDoctor(){return this.assignedDoctor;}
	public void setAssignedDoctor(Doctor assignedDoctor)
	{		
       if(!this.assignedDoctor.equals(assignedDoctor))
        {
            this.assignedDoctor.removePatientFromList(this);
        }
       
       this.assignedDoctor = assignedDoctor;
       
       if(!assignedDoctor.getPatientList().contains(this))
        {
            assignedDoctor.addPatientToList(this);
        }
    }
	

	// getter Vitals
	public Vitals getVitals() {return this.vitals;}
	
	// reset Vitals
	public void resetVitals() {
		this.alertSend = false;
		this.vitals.setCriticalCondition(false);
		this.vitals.setRateOfBreathing(15);
		this.vitals.setPulse(80);
		this.vitals.setBodyTemperature(37);
		this.vitals.setBloodPressure("120/80");
	}

	// getter setter Room
	public Room getRoom() {return this.room;}	
	public void setRoom(Room room)
	{
		if(this.room != null && !this.room.equals(room))
		{
		    this.room.removePatientFromRoom(this);
		}
       
       this.room = room;
   
		if(room != null && !room.getPatientList().contains(this))
		{
			room.addPatientToRoom(this);
		}
	}
	// getter setter AlertSend
	public boolean getAlertSend(){
		return this.alertSend;
	}
	public void setAlertSend(boolean alertSend){
		this.alertSend = alertSend;
	}
	
	public void delete() 
	{
		this.room.getPatientList().remove(this);
		this.assignedDoctor.getPatientList().remove(this);
		
		this.medicalIssue = null;
		this.assignedDoctor = null;
		this.vitals = null;
		this.room = null;
		
		Hospital.getInstance().removePatientFromList(this);
		this.setDeleted(true);
	}
}
