package application;
//Data Model of MVC design pattern
public class SingleExerciseEntry 
{
	private String ID;
	private String exerciseDate;
	private String exerciseType;
	private double totalDuration;
	public SingleExerciseEntry(String ID,String exerciseDate, String exerciseType, double totalDuration)
	{
		super();
		this.ID=ID;
		this.exerciseDate = exerciseDate;
		this.exerciseType = exerciseType;
		this.totalDuration = totalDuration;
	}
	
	public String getID() 
	{
		return ID;
	}

	public void setID(String iD) 
	{
		ID = iD;
	}

	public String getExerciseDate() 
	{
		return exerciseDate;
	}
	public void setExerciseDate(String exerciseDate) 
	{
		this.exerciseDate = exerciseDate;
	}
	public String getExerciseType() 
	{
		return exerciseType;
	}
	public void setExerciseType(String exerciseType) 
	{
		this.exerciseType = exerciseType;
	}
	public double getTotalDuration() 
	{
		return totalDuration;
	}
	public void setTotalDuration(double totalDuration) 
	{
		this.totalDuration = totalDuration;
	}
	

}
