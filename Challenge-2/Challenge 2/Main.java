import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main
{
	private static Variable[] variableArray = null;
	
	public static void main(String args[])
	{
		String[] codeArray = GetTextFromFile();
		runCode(codeArray);
		System.out.println("End of code");
	}
	
	// Reads the bb file and returns the text as an array where each element is a line from the file
	private static String[] GetTextFromFile()
	{
		String[] fileTextArray = new String[] {"FILE_TEXT_ARRAY_EMPTY"};
		String fileString;
		
		try
		{
			fileString = new String(Files.readAllBytes(Paths.get(".\\bb.txt")));
			fileString = fileString.replaceAll("\n", "").replaceAll("\r", "");
			
			fileTextArray = fileString.split(";");
			
			for(Integer i = 0; i < fileTextArray.length; i++)
			{
				fileTextArray[i] = fileTextArray[i].trim();
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR during file reading");
		}
		
		return fileTextArray;
	}
	
	// Checks if the variable has already been used and if not, creates and adds it to the array
	private static Variable getVariable(String variableName)
	{
		Variable variable;
		
		if(variableArray != null)
		{
			for(Variable i : variableArray)
			{
				if(variableName.equals(i.getName()))
				{
					return i;
				}
			}
			
			Integer arrayLength = variableArray.length;
			Variable[] tempArray = new Variable[arrayLength + 1];
			
			for(Integer i = 0; i < variableArray.length; i++)
			{
				tempArray[i] = variableArray[i];
			}
			
			variable = new Variable(variableName);
			tempArray[arrayLength] = variable;
			variableArray = tempArray;
		}
		else
		{
			variable = new Variable(variableName);
			variableArray = new Variable[] {variable};
		}
		
		return variable;
	}
	
	// Prints the state of all variables
	private static void printVariableStates()
	{
		for(Variable i : variableArray)
		{
			System.out.print(i.getName() + ": " + i.getValue() + ", ");
		}
		
		System.out.print("\n");
	}
	
	// Takes an array containing the lines of code and runs them
	private static void runCode(String[] codeArray)
	{
		String line;
		String first;
		Variable variable;
		
		for(int i = 0; i < codeArray.length; i++)
		{
			line = codeArray[i];
			first = line.substring(0, 1);
			
			System.out.println(line);
			
			switch(first)
			{
				case "c":
					// Code for clear command
					variable = getVariable(line.substring(6));
					variable.clear();
					printVariableStates();
					break;
				
				case "i":
					// Code for increment command
					variable = getVariable(line.substring(5));
					variable.incr();
					printVariableStates();
					break;
				
				case "d":
					// Code for decrement command
					variable = getVariable(line.substring(5));
					variable.decr();
					printVariableStates();
					break;
				
				case "w":
					// Code for while loop
					int whileCounter = 1;
					int endCounter = 0;
					
					variable = getVariable(line.substring(6, line.length() - 9));
					String[] subCode;
					int endIndex = codeArray.length;
					
					for(int j = i + 1; j < codeArray.length; j++)
					{
						
						if(codeArray[j].equals("end"))
						{
							endCounter++;
							
							if(whileCounter == endCounter)
							{
								endIndex = j;
								break;
							}
						}
						else if(codeArray[j].substring(0, 5).equals("while"))
						{
							whileCounter++;
						}
					}
					
					subCode = Arrays.copyOfRange(codeArray, i + 1, endIndex);
					i = endIndex;
					
					while(variable.getValue() > 0)
					{
						runCode(subCode);
					}
					
					System.out.println("end");
					
					break;
			}
		}
	}
}