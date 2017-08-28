package classes;

public class Person {

	private int id, age;	
	private String 	lastName, firstName, gender;
	private boolean isDeleted;
	
	protected Person(int id, int age, String firstName, String lastName, String gender)
	{
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.age = age;
		this.isDeleted = false;
	}
		
	// getter setter ID
	public int getId(){return id;}
	
	//getter setter last name
	public String getName()	{return this.lastName;}	
	public void setName(String name){this.lastName = name;}
	
	//getter setter first name
	public String getFirstName(){return this.firstName;}
	public void setFirstName(String firstName){this.firstName = firstName;}

	//getter setter gender
	public String getGender(){return this.gender;}
	public void setGender(String gender){this.gender = gender;}
	
	//getter setter age
	public int getAge(){return this.age;}
	public void setAge(int age){this.age = age;}
	
	// getter setter is Deleted
	public boolean isDeleted () {return this.isDeleted;}
	public void setDeleted (boolean isDeleted) {this.isDeleted = isDeleted;}
}
