package threads;

import java.util.Iterator;
import java.util.List;
import classes.AlertObject;
import classes.Hospital;

public class AlertListIncrementer 
{
	public static void executeAlertListIncrementThread(int intervalInMS) 
	{		
		if (!Incrementer.getInstance().isAlive()) Incrementer.getInstance().start();
	}	
	private static class Incrementer implements Runnable {
		private static int interval = 1000;
		private static Thread threadInstance = null;
		private static List<AlertObject> alertList = Hospital.getInstance().getAlertList();
		// Singleton
		private static Thread getInstance() {
			if (threadInstance == null) {
				threadInstance = new Thread(new Incrementer());
			}
			return threadInstance;
		}

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
//					synchronized (Hospital.getInstance().getAlertList()) {
						if (!alertList.isEmpty() || alertList == null) {
							for (Iterator<AlertObject> it = alertList.iterator(); it.hasNext();) {
								AlertObject a = it.next();
								a.incrementCurrentLifeTime();
							}
							// for (AlertObject a : alertList)
							// {
							// a.incrementCurrentLifeTime();
							// }
						}
						Thread.sleep(interval);
//					}
				} catch (InterruptedException e) {
					System.out.println("Alertlist incrementer killed! :(");
				}
			}
		}
	}
}
