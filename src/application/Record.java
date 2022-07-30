package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record 
{
	private final StringProperty id;
    private final StringProperty userName;
    private final StringProperty exerciseDate;
    private final StringProperty exerciseType;
    private final StringProperty  duration;
    
    public Record()
    {
        id = new SimpleStringProperty(this, "id");
        userName = new SimpleStringProperty(this, "userName");
        exerciseDate = new SimpleStringProperty(this, "exerciseDate");
        exerciseType = new SimpleStringProperty(this, "exerciseType");
        duration = new SimpleStringProperty(this, "duration");
    }
    
    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }
 
    public StringProperty userNameProperty() { return userName; }
    public String getUserName() { return userName.get(); }
    public void setUserName(String newUserName) { userName.set(newUserName); }
 
    public StringProperty exerciseDateProperty() { return exerciseDate; }
    public String getExerciseDate() { return exerciseDate.get(); }
    public void setExerciseDate(String newExerciseDate) { exerciseDate.set(newExerciseDate); }
    
    public StringProperty exerciseTypeProperty() { return exerciseType; }
    public String getExerciseType() { return exerciseType.get(); }
    public void setExerciseType(String newExerciseType) { exerciseType.set(newExerciseType); }
    
    public StringProperty durationProperty() { return duration; }
    public String getDuration() { return duration.get(); }
    public void setDuration(String newDuration) { duration.set(newDuration); }
    
    
}


 
