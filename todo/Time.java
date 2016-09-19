package todo;
import done.*;

public class Time extends Thread{
	private SharedData sharedData;
	
	public Time(SharedData sd) {
		sharedData = sd;
	}
	
	public void run() {
		long t, t0, diff;
		t = t0 = System.currentTimeMillis();
		while(true){
			t += 1000;
			diff = t-System.currentTimeMillis();
			if (diff > 0)
				try {
					sleep(diff);
					sharedData.tick();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
