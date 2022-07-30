package application;

public class BarData 
{
	private String sportName;
	private double duration;
	public BarData(String sportName, double duration) 
	{
		super();
		this.sportName = sportName;
		this.duration = duration;
	}
	public String getSportName() 
	{
		return sportName;
	}
	public void setSportName(String sportName) 
	{
		this.sportName = sportName;
	}
	public double getDuration() 
	{
		return duration;
	}
	public void setDuration(double duration) 
	{
		this.duration = duration;
	}
	
	

}
