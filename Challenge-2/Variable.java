public class Variable
{
	private String NAME;
	private Integer value;
	
	public Variable(String startName) // Constructor
	{
		this.NAME = startName;
		this.value = 0;
	}
	
	public void clear() // Sets the value to 0
	{
		this.value = 0;
	}
	
	public void incr() // Increments the value by 1
	{
		this.value += 1;
	}
	
	public void decr() // Decrements the value by 1
	{
		this.value -= 1;
		
		if(this.value < 0)
		{
			this.value = 0;
		}
	}
	
	// Getter for the variables value attribute
	public Integer getValue()
	{
		return this.value;
	}
	
	// Getter for the variables NAME attribute
	public String getName()
	{
		return this.NAME;
	}
}