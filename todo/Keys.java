package todo;

public class Keys extends Thread{
	private SharedData sharedData;
	
	public Keys(SharedData sd) {
		sharedData = sd;
	}
	
	public void run() {
		while (true) {
			sharedData.input().getSemaphoreInstance().take();
			sharedData.s.take();
			sharedData.turnOffSoundAlarm();
			int value = sharedData.input().getValue();
			int choice = sharedData.input().getChoice();	
			if(choice == 0){ //turn on and off the alarm
				boolean alarmOn = sharedData.input().getAlarmFlag();
				sharedData.setAlarmOn(alarmOn);
			}			
			if(choice ==1) { //set alarm time
				sharedData.setAlarmTime(value);
//				System.out.println("button pressed. Choice:" + choice + ". Value: " + value);
//				System.out.println("data recorded. time: " + sharedData.getTheTime() + ". alarm: " + sharedData.getAlarmTime() + ". alarmOn: " + sharedData.getAlarmOn());
				
			}
			if(choice == 2) { //set the time
				sharedData.setTheTime(value);
//				System.out.println("button pressed. Choice:" + choice + ". Value: " + value);
//				System.out.println("data recorded. time: " + sharedData.getTheTime() + ". alarm: " + sharedData.getAlarmTime() + ". alarmOn: " + sharedData.getAlarmOn());
				
			}
			sharedData.s.give();	
		}
	}
}


// alarmclock semaphore take here
