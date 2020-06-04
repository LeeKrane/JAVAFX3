package labor24.worker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LeeKrane
 */
class WorkerTest {
	@Test
	public void reporter_run_casualRunThrough_allWorkersDone () throws InterruptedException {
		new Thread(new Reporter(100)).start();
		Thread.sleep(28000);
		assertEquals(100, Stadium.DONE.workerCount);
	}
	
	@Test
	public void stadium_getNextStadium_correctRequests_correctReturnValues () {
		new Thread(new Worker(){
			@Override
			public void run () {
				currentStadium = Stadium.STD1;
				assertEquals(Stadium.STD2, currentStadium.getNextStadium());
				currentStadium = Stadium.STD2;
				assertEquals(Stadium.STD3, currentStadium.getNextStadium());
				currentStadium = Stadium.STD3;
				assertEquals(Stadium.DONE, currentStadium.getNextStadium());
				currentStadium = Stadium.DONE;
				assertEquals(Stadium.DONE, currentStadium.getNextStadium());
			}
		}).start();
	}
	
	@Test
	public void stadium_incrementStadium_correctRequests_correctReturnValues () {
		new Thread(new Worker(){
			@Override
			public void run () {
				assertEquals(Stadium.STD1, currentStadium);
				Stadium.incrementStadium(this);
				assertEquals(Stadium.STD2, currentStadium);
				Stadium.incrementStadium(this);
				assertEquals(Stadium.STD3, currentStadium);
				Stadium.incrementStadium(this);
				assertEquals(Stadium.DONE, currentStadium);
				Stadium.incrementStadium(this);
				assertEquals(Stadium.DONE, currentStadium);
			}
		}).start();
	}
	
	@Test
	public void stadium_incrementStadium_incrementFromNull_incrementedToSTD1 () {
		new Thread(new Worker(){
			@Override
			public void run () {
				currentStadium = null;
				Stadium.incrementStadium(this);
				assertEquals(Stadium.STD1, currentStadium);
			}
		}).start();
	}
}