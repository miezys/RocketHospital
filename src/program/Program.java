package program;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import classes.*;
import gui.*;
import threads.AlertCleanerWrapper;
import threads.AlertListIncrementer;
import threads.CopyAndSaveAllLists;
import threads.VitalsService;

public class Program extends Gui
{	
	public static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	public static void main(String[] args) throws Exception 
	{		
		try 
		{
			//This creates a new instance of the Hospital class.
			Hospital hospital = Hospital.getInstance();
			try {} catch(Exception e) {}
			//This loads the data from the database.
			Database.load();
			
			hospital.printCurrentLoginData();
			
			// Save everything every 5 seconds ... yeah .. not a very good idea
			ScheduledExecutorService saveService = Executors.newSingleThreadScheduledExecutor();
			ScheduledExecutorService vitalService = Executors.newSingleThreadScheduledExecutor();
					
			saveService.scheduleAtFixedRate(new CopyAndSaveAllLists(), 0, 60, TimeUnit.SECONDS);
				
			vitalService.scheduleAtFixedRate(new VitalsService(), 0, 5, TimeUnit.SECONDS);		
			
			// This starts the thread for incrementing the Hospital.alertList currentLifeTme.
			AlertListIncrementer.executeAlertListIncrementThread(1000);
			
			// starts the thread for killing alerts older than x seconds. 
			AlertCleanerWrapper.executeAlertCleaner(hospital.getAlertList(), 5, 120);
			
			// only if program starts in debug mode
			if (isDebug) 
			{
				int i = 0;
				for (Patient p : hospital.getPatientList()) 
				{
					System.out.println("Index: "+i);
					System.out.println("ID: "+p.getId());
					System.out.println("Name: "+p.getFirstName()+" "+p.getName());
					i++;
				}
				
				//for testing
				for (Patient patient : Hospital.getInstance().getPatientList()) {
					patient.getVitals().setCriticalCondition(false);
					patient.setAlertSend(false);
					patient.getVitals().setBloodPressure("120/80");
					patient.getVitals().setBodyTemperature(37);
					patient.getVitals().setPulse(80);
					patient.getVitals().setRateOfBreathing(15);
				}
			}
			
			// This launches the GUI.
			Gui.launch(args);
					
			// This saves the data and exits the program after GUI has been closed.
			Database.save(Hospital.getInstance().getPatientList(), Hospital.getInstance().getDoctorList(), Hospital.getInstance().getNurseList(), Hospital.getInstance().getRoomList());
			System.exit(0);
		}
		catch (Exception e) 
		{
			System.out.println("Exception in Main: "+e);
		}
	}
}
