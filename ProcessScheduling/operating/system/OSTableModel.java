package operating.system;

import java.awt.FileDialog;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class OSTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -7180079213856043342L;

	// JobType[] customjobType = { new JobType("FCFS"), new JobType("SJF"),
	// new JobType("RR") };

	@SuppressWarnings("unused")
	private static boolean isFileLoaded;
	private static String key = "ProcessSchedulerFile";
	private static String filepath;

	// @SuppressWarnings({ "rawtypes", "unused" })
	// private Vector<Vector> introwData = new Vector<Vector>();
	String[] columnHeader = { "Job Name", "Start Time", "Burst Time",
			"Job Type", "Priority", "Quantum", "Progress" };

	@SuppressWarnings("rawtypes")
	Class[] columnTypes = new Class[] { String.class, Integer.class,
			Integer.class, Integer.class, Integer.class, Integer.class,
			Progress.class };

	@SuppressWarnings("rawtypes")
	static Vector<Vector> rowData = new Vector<Vector>();

	public OSTableModel() throws FileNotFoundException {
		File data = new File(filepath);
		setTableValues(data);
	}

	public int getRowCount() {
		return rowData.size();
	}

	public int getColumnCount() {
		return columnHeader.length;
	}

	public String getColumnName(int x) {
		return columnHeader[x];
	}

	public boolean isCellEditable(int r, int c) {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}

	@SuppressWarnings("unchecked")
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 3)
			for (int i = 0; i < 3; i++)
				if (value.equals(Job.jobType[i]))
					(rowData.elementAt(rowIndex)).setElementAt(i, columnIndex);

		(rowData.elementAt(rowIndex)).setElementAt(value, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return (rowData.elementAt(rowIndex)).elementAt(columnIndex);
	}

	private void setTableValues(File data) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scanme = new Scanner(new FileReader(data));

		scanme.nextLine();
		while (scanme.hasNext()) {
			makeVectorValues(scanme.next(), scanme.nextInt(), scanme.nextInt(),
					scanme.nextInt(), scanme.nextInt(), scanme.nextInt());
		}

		fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	public static void makeVectorValues(String name, int st, int bt, int jt,
			int pr, int ts) {
		List<? extends Object> asList = Arrays.asList(name, String.valueOf(st),
				String.valueOf(bt), jt, String.valueOf(pr), String.valueOf(ts),
				0);
		Vector<Object> tempVector1 = new Vector<Object>(asList);
		rowData.add(tempVector1);

	}

	@SuppressWarnings("rawtypes")
	protected static void newFile() {
		rowData = new Vector<Vector>();

		FileDialog dialogNew = new FileDialog(new JFrame(),
				"Create New Job List");

		dialogNew.setMode(FileDialog.SAVE);
		dialogNew.setVisible(true);
		isFileLoaded = false;
		try {
			if (!dialogNew.getFile().equals("")) {

				String outfile = dialogNew.getFile();

				File inputFile = new File(dialogNew.getDirectory() + outfile);
				filepath = new String(dialogNew.getDirectory() + outfile);
				signFile(inputFile);

				isFileLoaded = true;
			} else
				isFileLoaded = false;
		} catch (Exception f) {
			System.out.println(f.getMessage());
		}// end try-catch

	}

	public static void signFile(File inputFile) throws FileNotFoundException {
		PrintWriter pwOutStream = new PrintWriter(inputFile);

		pwOutStream.print(key + "\n");
		pwOutStream.println("defaultJob 0 10 1 0 0");
		pwOutStream.flush();
		pwOutStream.close();
	}

	@SuppressWarnings("rawtypes")
	protected static void openFile() {
		rowData = new Vector<Vector>();

		FileDialog dialog = new FileDialog(new JFrame(),
				"Open an Existing Job List");
		File inputFile = null;
		isFileLoaded = false;

		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);

		// start try-catch on inputFile opening
		try {
			if (!dialog.getFile().equals("")) {
				inputFile = new File(dialog.getDirectory() + dialog.getFile());
				filepath = new String(dialog.getDirectory() + dialog.getFile());
				if (isPISFile(inputFile))
					isFileLoaded = true;
				else
					isFileLoaded = false;
			}// end if
		}

		catch (Exception x) {
			JOptionPane.showMessageDialog(null,
					"Invalid File! Open a valid Job List.", "Invalid File",
					JOptionPane.ERROR_MESSAGE);
			isFileLoaded = false;
			System.out.println(x.getMessage());
		}// end try-catch
	}

	protected static void saveToFile() throws Throwable {
		// TODO Auto-generated method stub

		FileWriter writer = new FileWriter(new File(filepath));

		writer.write(key + "\n");
		for (int j = 0; j < rowData.size(); j++)
			writer.write("" + (rowData.elementAt(j)).elementAt(0) + " "
					+ (rowData.elementAt(j)).elementAt(1) + " "
					+ (rowData.elementAt(j)).elementAt(2) + " "
					+ (rowData.elementAt(j)).elementAt(3) + " "
					+ (rowData.elementAt(j)).elementAt(4) + " "
					+ (rowData.elementAt(j)).elementAt(5) + "\n");

		writer.flush();
		writer.close();
	}

	private static boolean isPISFile(File inputFile)
			throws FileNotFoundException {
		Scanner fileTestRead = new Scanner(new FileReader(inputFile));

		if (!fileTestRead.next().equals(key)) {
			JOptionPane.showMessageDialog(null,
					"Invalid File! Open a valid Job List.");
			fileTestRead.close();
			return false;
		}
		fileTestRead.close();
		return true;
	}

	public static void delJob(int selectedRows) {
		rowData.removeElementAt(selectedRows);
	}
}