package operating.system;

public class Job {

	final static int FCFS = 1;
	final static int SJF = 2;
	final static int RR = 3;

	final static String[] jobType = { "FCFS", "SJF", "RR" };

	private String name;
	private int starttime;
	private int bursttime;
	private int jobtype;
	private int priority;
	private int timeslice;
	private boolean isProcessed;
	private int progressTime;

	public Job() {
		setName("Job " + (int) Math.random() * 10000);
		setStarttime(0);
		setBursttime(1);
		setJobtype(FCFS);
		setPriority(0);
		setProcessed(false);
		setProgressTime(0);
	}

	public Job(String name, int starttime, int bursttime, int jobtype,
			int priority, int timeslice) {
		setName(name);
		setStarttime(starttime);
		setBursttime(bursttime);
		setJobtype(jobtype);
		setPriority(priority);
		setTimeslice(timeslice);
		setProcessed(false);
		setProgressTime(0);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the starttime
	 */
	public int getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *            the starttime to set
	 */
	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the bursttime
	 */
	public int getBursttime() {
		return bursttime;
	}

	/**
	 * @param bursttime
	 *            the bursttime to set
	 */
	public void setBursttime(int bursttime) {
		this.bursttime = bursttime;
	}

	/**
	 * @return the jobtype
	 */
	public int getJobtype() {
		return jobtype;
	}

	/**
	 * @param jobtype
	 *            the jobtype to set
	 */
	public void setJobtype(int jobtype) {
		this.jobtype = jobtype;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the isProcessed
	 */
	public boolean isProcessed() {
		return isProcessed;
	}

	/**
	 * @param isProcessed
	 *            the isProcessed to set
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/**
	 * @return the timeslice
	 */
	public int getTimeslice() {
		return timeslice;
	}

	/**
	 * @param timeslice
	 *            the timeslice to set
	 */
	public void setTimeslice(int timeslice) {
		this.timeslice = timeslice;
	}

	public int getProgressTime() {
		return progressTime;
	}

	public void setProgressTime(int progressTime) {
		this.progressTime = progressTime;
	}

	public void increaseProgress() {
		this.progressTime += 1;
	}

	public void decreasePriority() {
		this.priority -= 1;
	}
}
