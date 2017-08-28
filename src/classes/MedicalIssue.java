package classes;

public class MedicalIssue {

	private String name;
	private String description;
	
	public MedicalIssue(String name, String description) 
	{
		this.name = name;
		this.description = description;
	}
	
	// getter setter name
	public String getMedicalIssueName () {return this.name;}
	public void setMedicalIssueName (String newName) {this.name = newName;}

	// getter setter description
	public String getMedicalIssueDescription () {return this.description;}
	public void setMedicalIssueDescription (String newDescription) {this.description = newDescription;}
}
