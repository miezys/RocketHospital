package util;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import classes.*;

public class StringBuilder {

	public static String parseListToString(List randomList)
	{
		String ret = "";
		
		if (randomList != null || !randomList.isEmpty()) 
		{
			if (randomList.get(0) instanceof Person || randomList.get(0) instanceof Room) 
			{		
				try 
				{
					Class<?> c = Class.forName(randomList.get(0).getClass().getName());
					Method m = c.getMethod("getId", null);
					
					for (int i = 0; i < randomList.size(); i++)
					{
						ret += m.invoke(randomList.get(i), null).toString()+"|";
					}
				}
				catch (ClassNotFoundException e)
				{
				    System.out.println("Class not found exception: "+e);
				}
				catch (IllegalAccessException e) 
				{
				    System.out.println("Illegal access exception: "+e);
				} 
				catch (NoSuchMethodException e) 
				{
				    System.out.println("Method not found exception: "+e);
				} 
				catch (IllegalArgumentException e) 
				{
				    System.out.println("Illegal argument exception: "+e);
				} 
				catch (InvocationTargetException e) 
				{
				    System.out.println("Invocation target exception: "+e);
				}
			}
			else return "-1";
		}
		else return "-1";
		
		return ret;
	}
	
	public static List<String> parseStringToList(String lineSeperatedString) 
	{
		List<String> ret = new ArrayList<String>();
		if (lineSeperatedString.contains("|"))
		{
			String[] substrings = lineSeperatedString.split("|");
			
			for (int i = 0; i < substrings.length; i+=2) 
			{
				ret.add(substrings[i]);
			}
			return ret;
		}	
		else return null;
	}
}
