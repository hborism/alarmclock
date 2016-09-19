package todo;
import done.*;

public class Keys extends Thread{
	private SharedData sharedData;
	private ClockInput clockInput;
	private int previous;
	
	public Keys(SharedData sd, ClockInput i) {
		sharedData = sd;
		clockInput = i;
		previous = 0;
	}
	
	public void run() {
		while (true) {
			clockInput.getSemaphoreInstance().take(); //key pressed!
			
			int value = clockInput.getValue();
			int choice = clockInput.getChoice();
			boolean alarmOn = clockInput.getAlarmFlag();
						
			sharedData.turnOffSoundAlarm(); //turn off alarm sound
			
			sharedData.setAlarmOn(alarmOn); //set alarm to what the flag indicates
			
			if(choice == 0){ 
				if(previous == 1) {
					sharedData.setAlarmTime(value);
//					System.out.println("button pressed. Choice:" + choice + ". Value: " + value);
//					System.out.println("data recorded. time: " + sharedData.getTheTime() + ". alarm: " + sharedData.getAlarmTime() + ". alarmOn: " + sharedData.getAlarmOn());
				}
				if(previous ==2) {
					sharedData.setTheTime(value);
//					System.out.println("button pressed. Choice:" + choice + ". Value: " + value);
//					System.out.println("data recorded. time: " + sharedData.getTheTime() + ". alarm: " + sharedData.getAlarmTime() + ". alarmOn: " + sharedData.getAlarmOn());
				}
				previous = 0;
			}	
			
			if(choice ==1) { //set alarm time
				previous=1;
			}
			if(choice == 2) { //set the time
				previous=2;
			}
		}
	}
}


// alarmclock semaphore take here
