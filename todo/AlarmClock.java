package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput	input;
	private static ClockOutput	output;
	private static Semaphore	sem; 


	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		
	
	}
	

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon 
	// each keypress. To be modified in the lab.
	public void run() {
		
		SharedData sd = new SharedData(output, input);
			
		Thread t, k;
		t= new Time(sd);
		t.start();
		
		k= new Keys(sd);
		k.start();
		
//		while (true) {
//			sem.take();
//			output.doAlarm();
//			System.out.println(input.getValue());
//			System.out.println(input.getAlarmFlag());
//		}
	}
}
