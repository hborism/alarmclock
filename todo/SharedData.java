package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	
	private Semaphore s;
	
	private int theTime;
	private int alarmTime;
	private boolean alarmOn;
	private int soundAlarmTimeLeft;
	private int previous;
	
	private static ClockOutput	output;
	
	//gör en mutex här som löser när flera vill komma in och ändra i shareddata
	
	public SharedData(ClockOutput o){		
		output = o;
		
		theTime = 0;
		alarmTime = 0;
		alarmOn = false;
		soundAlarmTimeLeft = 0;
		previous = 0;
		
		s = new MutexSem();
	}
	
	public void tick() {
		//take mutex
		s.take();
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

		s.give(); //give mutex
	}
	
	public void setAlarmTime(int value) {
		s.take();
		alarmTime = value;
		s.give();
	}
	
	public void setTheTime(int value) {
		s.take();
		theTime = value;
		s.give();
	}
	
	public void setAlarmOn(boolean value) {
		s.take();
		alarmOn = value;
		s.give();
	}
	
	public void turnOffSoundAlarm() {
		s.take();
		soundAlarmTimeLeft =0;
		s.give();
	}
	
	public ClockOutput output() {
		return output;
	}
	
	private boolean getAlarmOn() {
		return alarmOn;
	}
	
	public int getTheTime() {
		return theTime;
	}
	
	public int getAlarmTime(){
		return alarmTime;
	}
}
