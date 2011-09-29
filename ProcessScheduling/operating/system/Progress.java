package operating.system;

public class Progress {

	private int progress;
	private int max;

	public Progress() {
		this(0, 100);
	}

	public Progress(int p, int m) {
		setProgress(p);
		setMax(m);
	}

	public void setProgress(int p) {
		progress = p;
	}

	public void setMax(int m) {
		max = m;
	}

	public int getProgress() {
		return progress;
	}

	public int getMax() {
		return max;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Progress [progress=" + progress + "]";
	}

}
