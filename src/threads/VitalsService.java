package threads;

import java.util.Random;

import classes.Hospital;
import classes.Patient;
import gui.nurse.NursePatientVitalsController;
import javafx.application.Platform;

public class VitalsService implements Runnable{
	
	@Override
	public void run() {
		Random randomGen = new Random();
		for (Patient patient : Hospital.getInstance().getPatientList()) {
			if(Hospital.getInstance().getPatientList().isEmpty()){
				break;
			}
			if(patient.getRoom() == null || patient.isDeleted() == true){
				continue;
			}

			if(randomGen.nextInt(100) + 1 <= 30){
				if(randomGen.nextInt(100) + 1 < 65){
					patient.getVitals().setRateOfBreathing(patient.getVitals().getRateOfBreathing() + 1);
				} else {
					patient.getVitals().setRateOfBreathing(patient.getVitals().getRateOfBreathing() - 1);
				}
			}
						
			if(randomGen.nextInt(100) + 1 <= 30){
				if(randomGen.nextInt(100) + 1 < 50){
					patient.getVitals().setPulse(patient.getVitals().getPulse() + 1);
				} else {
					patient.getVitals().setPulse(patient.getVitals().getPulse() - 1);
				}
			}
			
			
			if(randomGen.nextInt(100) + 1 <= 10){
				if(randomGen.nextInt(100) + 1 < 50){
					patient.getVitals().setBodyTemperature(patient.getVitals().getBodyTemperature() + 1);
				} else {
					patient.getVitals().setBodyTemperature(patient.getVitals().getBodyTemperature() - 1);
				}
			}
			
			if(randomGen.nextInt(100) + 1 <= 30){
				String[] bloodPressure = patient.getVitals().getBloodPressure().split("/");				
				if(randomGen.nextInt(100) + 1 < 50){
					patient.getVitals().setBloodPressure((Integer.parseInt(bloodPressure[0]) + 10) + "/" + 
														 (Integer.parseInt(bloodPressure[1]) + 5));
				} else {
					patient.getVitals().setBloodPressure((Integer.parseInt(bloodPressure[0]) - 10) + "/" + 
							 							 (Integer.parseInt(bloodPressure[1]) - 5));
				}
			}
						
			if(patient.getAlertSend()){
				continue;
			}
								
			if(patient.getVitals().getCriticalCondition()){
				patient.setAlertSend(true);
				patient.getVitals().sendAlert(patient.getId(), patient.getRoom(), patient.getAssignedDoctor());
			}
		}
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				try {
					NursePatientVitalsController.filterPatientList();
				} catch (Exception e) {
					System.out.println("Vitals refreshed");
				}
				
			}
			
		});
		
	}

}
