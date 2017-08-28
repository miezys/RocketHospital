package util;

import java.util.Random;

import classes.MedicalIssue;
import resources.Const;

public class Randomizer 
{
	static Random gen = new Random();
	
	public static String generateGender () 
	{
		if (gen.nextBoolean()) return "male";
		else return "female";
	}
	public static int generateAge (boolean staff)
	{
		int age;
		if (staff) 
		{
			age = gen.nextInt(Const.Randomizer.STAFF_MAX_AGE);
			while (age < Const.Randomizer.STAFF_MIN_AGE) 
			{
				age = gen.nextInt(Const.Randomizer.STAFF_MAX_AGE);
			}
		}
		else age = gen.nextInt(Const.Randomizer.PATIENT_MAX_AGE);
		
		return age;
	}
	public static String generateForeName (boolean male) 
	{
		if (male) return Const.Names.MALE_FORENAMES[gen.nextInt(Const.Names.MALE_FORENAMES.length -1)];
		else return Const.Names.FEMALE_FORENAMES[gen.nextInt(Const.Names.FEMALE_FORENAMES.length -1)];
	}
	public static String generateSurName () 
	{
		return Const.Names.SURNAMES[gen.nextInt(Const.Names.SURNAMES.length -1)];
	}
	public static MedicalIssue generateMedicalIssue () 
	{
		return new MedicalIssue (Const.MedicalIssues.MEDICAL_ISSUES[gen.nextInt(Const.MedicalIssues.MEDICAL_ISSUES.length -1)], "");
	}
	public static int generateNumber (int max) 
	{
		return gen.nextInt(max);
	}
}
