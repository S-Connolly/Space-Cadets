import java.util.Scanner;
import java.net.*;
import java.io.*;

public class ReadFromURL
{
	public static void main(String args[])
	{
		System.out.println("Enter the email ID: ");
		Scanner scanner = new Scanner(System.in);
		String emailID = scanner.nextLine();
		
		String name = getName(emailID);
		System.out.println("Name: " + name);
	}
	
	public static String getName(String emailID) // Gets the name of the ID holder
	{
		String name = "empty";
		
		try
		{
			String webAddress = new String("https://www.ecs.soton.ac.uk/people/" + emailID);
			URL url = new URL(webAddress);
			
			URLConnection urlConnection = url.openConnection();
			
			BufferedReader bufferedReader 
			= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String line;
			StringBuilder wholePage = new StringBuilder();
			while((line = bufferedReader.readLine()) != null)
			{
				wholePage.append(line);
			}
			
			Integer start = wholePage.indexOf("property=\"name\">", 0);
			Integer end = wholePage.indexOf("<", start);
			
			name = wholePage.substring(start + 16, end);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return name;
	}
}
