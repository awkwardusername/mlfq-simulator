package operating.system;

import java.util.PriorityQueue;
import java.util.Vector;

public class ProcessScheduler {

	public static int globalProgressTotal;

	@SuppressWarnings("rawtypes")
	private static Vector<Vector> jobProcess = OSTableModel.rowData;

	private static PriorityQueue<Job> processQueue;
	// private static Stack<Object> processStack = null;

	private static Job[] jobs;

	public static void runProcessScheduler() {

		determineGlobalTime();
		makeJobObjects();
		sortByStarttime();
		sortProcessess();
	}

	private static void sortByStarttime() {
		// TODO actual MLQ algorithm
		int monitorBurst = 0;
		for (int j = 0; j < jobs.length; j++) {
			for (int k = 0; k < jobs.length - (j + 1); k++) {
				if (jobs[k].getPriority() > jobs[k + 1].getPriority()) {
					Job temp = jobs[k];
					jobs[k] = jobs[k + 1];
					jobs[k + 1] = temp;
				}

				// monitorBurst += jobs[k].getBursttime();

				if (jobs[k].getStarttime() > jobs[k + 1].getStarttime())
					if (monitorBurst <= jobs[k].getBursttime()) {
						Job temp = jobs[k];
						jobs[k] = jobs[k + 1];
						jobs[k + 1] = temp;
					}
				monitorBurst += jobs[k].getBursttime();
			}
			// monitorBurst += jobs[j].getBursttime();
		}

		for (int t = 0; t < jobs.length; t++)
			System.out.print(jobs[t].getName() + " " + jobs[t].getStarttime()
					+ " " + jobs[t].getBursttime() + " "
					+ jobs[t].getPriority() + "\n");

	}

	private static void makeJobObjects() {

		jobs = new Job[jobProcess.size()];

		for (int j = 0; j < jobProcess.size(); j++)
			jobs[j] = new Job(String.valueOf((jobProcess.elementAt(j))
					.elementAt(0)), Integer.valueOf(String.valueOf((jobProcess
					.elementAt(j)).elementAt(1))), Integer.valueOf(String
					.valueOf((jobProcess.elementAt(j)).elementAt(2))),
					Integer.valueOf(String.valueOf((jobProcess.elementAt(j))
							.elementAt(3))), Integer.valueOf(String
							.valueOf((jobProcess.elementAt(j)).elementAt(4))),
					Integer.valueOf(String.valueOf((jobProcess.elementAt(j))
							.elementAt(5))));

	}

	private static void sortProcessess() {
		processQueue = new PriorityQueue<Job>();

		// processQueue.addAll(jobs);

		while (!processQueue.isEmpty())
			System.out.print((processQueue.poll()).getName() + " ");

	}

	// private synchronized static void process(Job job, int j) {
	// // TODO Auto-generated method stub
	// if(processQueue.isEmpty())
	// processQueue.
	// if(job.getStarttime() > ((Job)processQueue.peek()).getStarttime());
	//
	//
	// }

	private static void determineGlobalTime() {
		for (int i = 0; i < jobProcess.size(); i++)
			globalProgressTotal += Integer.parseInt(String.valueOf((jobProcess
					.elementAt(i)).elementAt(2)));
	}

}
