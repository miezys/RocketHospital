package classes;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Staff extends Person {
	
	private String loginName;
	private String password;
	private ObservableList<AlertObject> alertList;

	protected Staff(int id, int age, String firstName, String lastName, String gender)
	{
		super (id, age, firstName, lastName, gender);
		this.loginName = firstName;
		this.password = lastName;
		this.alertList = FXCollections.observableArrayList();
	}
	protected Staff(int id, int age, String firstName, String lastName, String gender, ObservableList<AlertObject> alertList)
	{
		super (id, age, firstName, lastName, gender);
		this.loginName = firstName;
		this.password = lastName;
		this.alertList = alertList;
	}
	protected Staff(int id, int age, String firstName, String lastName, String gender, String loginName)
	{
		super (id, age, firstName, lastName, gender);
		this.loginName = loginName;
		this.password = lastName;
		this.alertList = FXCollections.observableArrayList();
	}
	protected Staff(int id, int age, String firstName, String lastName, String gender, String loginName, String password)
	{
		super (id, age, firstName, lastName, gender);
		this.loginName = loginName;
		this.password = password;
		this.alertList = FXCollections.observableArrayList();
	}
	protected Staff(int id, int age, String firstName, String lastName, String gender, String loginName, String password, ObservableList<AlertObject> alertList)
	{
		super (id, age, firstName, lastName, gender);
		this.loginName = loginName;
		this.password = password;
		this.alertList = alertList;
	}
	
	// getter setter Login
	// TODO: remove getPassword and getLoginName. SECURITY ISSUE!
	
	public String getLoginName(){return this.loginName;}	
	public void setLoginName(String loginName){this.loginName = loginName;}	
	
	public String getPassword(){return this.password;}	
	public void setPassword(String password){this.password = password;}	

	//getter setter alert list
	public ObservableList<AlertObject> getAlertList(){return this.alertList;}
	public void setAlertList(ObservableList<AlertObject> alertList){this.alertList = alertList;}
	
	// Methods alert list
	public boolean addAlertToList(AlertObject alertObject)
	{
		if (this.alertList.add(alertObject)) return true;
		else return false;
	}
	public boolean addAlertsToList(List<AlertObject> alertList)
	{
		if (this.alertList.addAll(alertList)) return true;
		else return false;
	}
	public boolean removeAlertFromList(AlertObject alertObject)
	{
		if (this.alertList.remove(alertObject)) return true;
		else return false;
	}
	public boolean removeAlertsFromList(List<AlertObject> alertList)
	{
		if (this.alertList.removeAll(alertList)) return true;
		else return false;
	}
}
