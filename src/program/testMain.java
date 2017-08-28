package program;
import classes.Hospital;

public class testMain {

	public static void main(String[] args) 
	{

		Hospital hospital = Hospital.getInstance();
		
		hospital.populate(true, true, true, true);
//		Database.save();
	}
}
