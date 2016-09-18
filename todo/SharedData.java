package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	
	public Semaphore s;
	
	private int theTime;
	private int alarmTime;
	private boolean alarmOn;
	private int soundAlarmTimeLeft;
	
	private static ClockOutput	output;
	private static ClockInput 	input;
	
	//gör en mutex här som löser när flera vill komma in och ändra i shareddata
	
	public SharedData(ClockOutput o, ClockInput i){		
		output = o;
		input = i;
		
		theTime = 0;
		alarmTime = 0;
		alarmOn = i.getAlarmFlag();
		soundAlarmTimeLeft = 0;
		
		s = new MutexSem();
	}
	
	public void tick() {
		int seconds = theTime % 100;
		int minutes = (theTime/100) % 100;
		int hours = (theTime/10000);
		
		if(seconds >=59) {
			seconds = 0;
			minutes +=1;
		} else {
			seconds += 1;
		}
		
		if(minutes >=60) {
			minutes = 0;
			hours +=1;
		}
		if(hours >= 24) {
			hours =0;
		}
		theTime = seconds + 100*minutes + 10000*hours;
		
		output.showTime(theTime);
		
		if (alarmTime == theTime && alarmOn) {
			soundAlarmTimeLeft = 20;
		}
		
		if(soundAlarmTimeLeft >=1) {
			soundAlarmTimeLeft--;
			output.doAlarm();
		}
		
		
	}
	
	public void setAlarmTime(int value) {
		alarmTime = value;
	}
	
	public void setTheTime(int value) {
		theTime = value;
	}
	
	public void setAlarmOn(boolean value) {
		alarmOn = value;
	}
	
	public void turnOffSoundAlarm() {
		soundAlarmTimeLeft =0;
	}
	
	public ClockOutput output() {
		return output;
	}
	
	public ClockInput input() {
		return input;
	}
	
	public boolean getAlarmOn() {
		return alarmOn;
	}
	
	public int getTheTime() {
		return theTime;
	}
	
	public int getAlarmTime(){
		return alarmTime;
	}
}
