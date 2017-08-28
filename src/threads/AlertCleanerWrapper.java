package threads;
import java.util.Iterator;

import classes.*;
import javafx.collections.ObservableList;

public class AlertCleanerWrapper 
{
    public static boolean executeAlertCleaner (ObservableList<AlertObject> alertList, int sleepInSeconds, int maxLifeTimeInSeconds)
    {
        setSleepTimer(sleepInSeconds);
        setAlertList(alertList);
        setMaxLifeTimeInSeconds(maxLifeTimeInSeconds);
        
        if (AlertCleaner.getInstance().isAlive())
        {
            // TODO: add logger
            return false;        
        }
                
        AlertCleaner.getInstance().start(); // returnt den Alert Cleaner Thread und startet run()
        return true;
    }
        
    private static void setAlertList (ObservableList<AlertObject> a) //methode bekommt als parameter eine List mit Alerts mit dem namen a
    {
    	AlertCleaner.alertList = a; // weiﬂt die Klassenvariable alertList dem Parameter a zu
    }
    
    // Getter setter AlertCleaner sleep timer
    private static int getSleepTimer() {return AlertCleaner.sleepInMilliseconds;}
    private static void setSleepTimer(int sleepInSeconds) {AlertCleaner.sleepInMilliseconds = sleepInSeconds*1000;}
    
    // Getter Setter AlertCleaner max life time
    private static int getMaxLifeTimeInSeconds() {return AlertCleaner.maxLifeTimeInSeconds;} // maxLifeTime muss noch erstellt werden
    private static void setMaxLifeTimeInSeconds(int maxLifeTimeInSeconds) {AlertCleaner.maxLifeTimeInSeconds = maxLifeTimeInSeconds;}
    
    private static class AlertCleaner implements Runnable
    {
        private static int sleepInMilliseconds;
        private static ObservableList<AlertObject> alertList;
        private static int maxLifeTimeInSeconds;
        private static Thread threadInstance = null; //singelton pattern
        
        private static Thread getInstance ()
        {
            if (threadInstance == null) //check ob es thread gibt
            {
                threadInstance = new Thread (new AlertCleaner());
            }
            return threadInstance;
        }
        
        @Override
        public void run()
        {
            while (!Thread.currentThread().isInterrupted())
            {
                try
                {    
                    Thread.sleep(sleepInMilliseconds);

//                    synchronized (Hospital.getInstance().getAlertList()) 
//                    {
	                    for(Iterator<AlertObject> it = alertList.iterator(); it.hasNext();){
	                    	AlertObject a = it.next();
	                    	if(a.getCurrentLifeTime() >= maxLifeTimeInSeconds){
	                    		a.delete();
	                    		it.remove();
	                    	}
	                    }
//                    }

                    
//                    synchronized (Hospital.getInstance().getAlertList()) 
//                    {
//	                    for(AlertObject a : alertList) // F¸r jedes Objekt aus Alert List wird Alert a nacheinander zugwiesen
//	                    {
//	                        if(a.getCurrentLifeTime() >= 30)
//	                        {
//	                            a.delete();
//	                        }
//	                    }
//                    }
                    
                }
                catch (InterruptedException e)
                {
                    // kills thread if InterruptedException is thrown
                    // TODO: add logging that thread has been interrupted
                    System.out.println("AlertCleaner thread killed! :(");
                }
            }
        }
    }
}
