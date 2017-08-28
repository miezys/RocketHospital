package classes;

public class Vitals {

	private int rateOfBreathing, pulse, bodyTemperature;
	private String bloodPressure;
	private boolean criticalCondition = false;
	
	public Vitals ()
	{
		this.rateOfBreathing = 15;
		this.pulse = 80;
		this.bodyTemperature = 37;
		this.bloodPressure = "120/80";
	}
	
	public Vitals (int rateOfBreathing, int pulse, int bodyTemperature, String bloodPressure) 
	{
		this.rateOfBreathing = rateOfBreathing;
		this.pulse = pulse;
		this.bodyTemperature = bodyTemperature;
		this.bloodPressure = bloodPressure;
	}
	
	//getter setter rate of breathing
	public int getRateOfBreathing(){return this.rateOfBreathing;}
	public void setRateOfBreathing(int rateOfBreathing) {
		this.rateOfBreathing = rateOfBreathing;
		if(this.rateOfBreathing < 12 || this.rateOfBreathing > 70) {
			this.criticalCondition = true;
		} else {
			this.criticalCondition = false;
		}
	}
	
	// getter setter pulse
	public int getPulse(){return this.pulse;}
	public void setPulse(int pulse) {
		this.pulse = pulse;
		if(this.pulse < 40 || this.pulse > 100) {
			this.criticalCondition = true;
		} else {
			this.criticalCondition = false;
		}
	}
	// getter setter body temperature
	public int getBodyTemperature(){return this.bodyTemperature;}
	public void setBodyTemperature(int bodyTemperature) {
		this.bodyTemperature = bodyTemperature;
		if(this.bodyTemperature < 34 || this.bodyTemperature > 39) {
			this.criticalCondition = true;
		} else {
			this.criticalCondition = false;
		}
	}
	
	// getter setter blood pressure
	public String getBloodPressure(){return this.bloodPressure;}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
		String[] BP = this.bloodPressure.split("/");
		if(Integer.valueOf(BP[0]) < 90 || Integer.valueOf(BP[1]) < 70 ||
		   Integer.valueOf(BP[0]) >= 180 || Integer.valueOf(BP[1]) > 110) {
			this.criticalCondition = true;
		} else {
			this.criticalCondition = false;
		}
	}
	
	public boolean getCriticalCondition(){
		return this.criticalCondition;
	}
	public void setCriticalCondition(boolean criticalCondition){
		this.criticalCondition = criticalCondition;
	}
	// alert send method
	public void sendAlert(int patientID, Room room, Doctor doctor) 
	{		
		new AlertObject (Hospital.getInstance().generateUniqueID("alert"),patientID , room, doctor, room.getNurseList());
	}
}
