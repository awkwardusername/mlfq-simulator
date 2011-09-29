package operating.system;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

public class ProcessSchedulerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelToolbar;
	private JButton btnNew;
	private JButton btnOpen;
	private JButton btnSave;
	private JButton btnNewjob;
	private JButton btnDeljob;
	private JButton btnGenjob;
	private JButton btnRun;
	private JSeparator separator;
	private JSeparator separator_1;
	private JPanel panelCharts;
	private JProgressBar progressBar;
	private JPanel panelGanttChart;
	private JScrollPane scrollPane;
	private JTable jobTable;
	private TableColumn jobTypeCol;
	private TableColumn progressCol;
	private JComboBox cmbJobType;
	private JSlider runSpeed;
	private Random random = new Random(1000);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcessSchedulerGUI frame = new ProcessSchedulerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProcessSchedulerGUI() {
		setTitle("Multi-level Queue Emulator");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				ProcessSchedulerGUI.class.getResource("/icons/mlfqs-32.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			panelToolbar = new JPanel();
			panelToolbar.setName("panelToolbar");
			contentPane.add(panelToolbar, BorderLayout.NORTH);
			{
				{
					btnOpen = new JButton("");
					btnOpen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							OSTableModel.openFile();
							enableComponents();
							try {
								tableInit();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					btnNew = new JButton("");
					btnNew.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnNew.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							OSTableModel.newFile();
							enableComponents();
							try {
								tableInit();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					btnNew.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/new.png")));
					btnNew.setName("btnNew");
					btnOpen.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnOpen.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/open.png")));
					btnOpen.setName("btnOpen");
				}
				{
					btnSave = new JButton("");
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								OSTableModel.saveToFile();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (Throwable e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					btnSave.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnSave.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/save.png")));
					btnSave.setName("btnSave");
				}
				{
					btnNewjob = new JButton("");
					btnNewjob.setEnabled(false);
					btnNewjob.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO newJob();
							newJob();
						}
					});
					btnNewjob.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnNewjob.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/new-process.png")));
					btnNewjob.setName("btnNewjob");
				}
				{
					btnDeljob = new JButton("");
					btnDeljob.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							OSTableModel.delJob(jobTable.getSelectedRow());
							jobTable.repaint();
						}
					});
					btnDeljob.setEnabled(false);
					btnDeljob.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnDeljob.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/del-process.png")));
					btnDeljob.setName("btnDeljob");
				}
				{
					btnGenjob = new JButton("");
					btnGenjob.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO generateJob();
							randomJob();
						}
					});
					btnGenjob.setEnabled(false);
					btnGenjob.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnGenjob.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/rand.png")));
					btnGenjob.setName("btnGenjob");
				}
				{
					btnRun = new JButton("");
					btnRun.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							disableComponents();
							progressBar
									.setMaximum(ProcessScheduler.globalProgressTotal);
							ProcessScheduler.runProcessScheduler();
						}
					});
					btnRun.setEnabled(false);
					btnRun.setBorder(new EmptyBorder(4, 4, 4, 4));
					btnRun.setIcon(new ImageIcon(ProcessSchedulerGUI.class
							.getResource("/icons/play.png")));
					btnRun.setName("btnRun");
				}
			}
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setName("separator");
			separator_1 = new JSeparator();
			separator_1.setOrientation(SwingConstants.VERTICAL);
			separator_1.setName("separator");
			ToolTipManager.sharedInstance().setInitialDelay(0);
			ToolTipManager.sharedInstance().setReshowDelay(0);
			runSpeed = new JSlider();
			runSpeed.setEnabled(false);
			runSpeed.addMouseWheelListener(new MouseWheelListener() {
				public void mouseWheelMoved(MouseWheelEvent e) {
					if (e.getWheelRotation() > 0)
						runSpeed.setValue(runSpeed.getValue() - 50);
					else
						runSpeed.setValue(runSpeed.getValue() + 50);
				}
			});
			runSpeed.setSnapToTicks(true);
			runSpeed.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					runSpeed.setToolTipText(String.valueOf(runSpeed.getValue())
							+ " ms");
				}
			});
			runSpeed.setFont(new Font("SansSerif", Font.PLAIN, 8));
			runSpeed.setPaintTicks(true);
			runSpeed.setValue(100);
			runSpeed.setPaintLabels(true);
			runSpeed.setMaximum(1000);
			runSpeed.setLabelTable(runSpeed.createStandardLabels(200));
			runSpeed.setName("runSpeed");
			GroupLayout gl_panelToolbar = new GroupLayout(panelToolbar);
			gl_panelToolbar
					.setHorizontalGroup(gl_panelToolbar.createParallelGroup(
							Alignment.LEADING)
							.addGroup(
									gl_panelToolbar
											.createSequentialGroup()
											.addComponent(btnNew)
											.addGap(1)
											.addComponent(btnOpen)
											.addGap(1)
											.addComponent(btnSave)
											.addGap(1)
											.addComponent(separator,
													GroupLayout.PREFERRED_SIZE,
													4,
													GroupLayout.PREFERRED_SIZE)
											.addGap(1)
											.addComponent(btnNewjob)
											.addGap(1)
											.addComponent(btnDeljob)
											.addGap(1)
											.addComponent(btnGenjob)
											.addGap(1)
											.addComponent(separator_1,
													GroupLayout.PREFERRED_SIZE,
													4,
													GroupLayout.PREFERRED_SIZE)
											.addGap(1)
											.addComponent(btnRun)
											.addPreferredGap(
													ComponentPlacement.RELATED)
											.addComponent(runSpeed,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addGap(150)));
			gl_panelToolbar
					.setVerticalGroup(gl_panelToolbar
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelToolbar
											.createSequentialGroup()
											.addGroup(
													gl_panelToolbar
															.createParallelGroup(
																	Alignment.TRAILING,
																	false)
															.addComponent(
																	runSpeed,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	btnDeljob,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	btnGenjob,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	btnNew,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	btnOpen,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	btnSave,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	separator,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	40,
																	Short.MAX_VALUE)
															.addComponent(
																	btnNewjob,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	separator_1,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	40,
																	Short.MAX_VALUE)
															.addComponent(
																	btnRun,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE))
											.addContainerGap()));
			panelToolbar.setLayout(gl_panelToolbar);
		}
		{
			panelCharts = new JPanel();
			panelCharts.setName("panelCharts");
			contentPane.add(panelCharts, BorderLayout.SOUTH);
			panelCharts.setLayout(new BorderLayout(0, 0));
			{
				progressBar = new JProgressBar();
				progressBar.setPreferredSize(new Dimension(150, 35));
				progressBar.setMaximumSize(new Dimension(32767, 35));
				progressBar.setBounds(new Rectangle(0, 0, 0, 35));
				progressBar.setMinimumSize(new Dimension(10, 35));
				progressBar.setName("progressBar");
				panelCharts.add(progressBar);
			}
			{
				panelGanttChart = new JPanel();
				panelGanttChart.setName("panelGanttChart");
				panelCharts.add(panelGanttChart, BorderLayout.NORTH);
			}
		}
		{
			cmbJobType = new JComboBox(Job.jobType);
			scrollPane = new JScrollPane();
			scrollPane.setName("scrollPane");
			contentPane.add(scrollPane, BorderLayout.CENTER);
		}
	}

	protected void newJob() {
		OSTableModel.makeVectorValues("job", 0, 0, 0, 0, 0);
		jobTable.repaint();
		scrollPane.setViewportView(jobTable);
	}

	protected void randomJob() {
		OSTableModel.makeVectorValues("Job" + random.nextInt(100),
				random.nextInt(20), random.nextInt(100), random.nextInt(3), 0,
				0);
		jobTable.repaint();
		scrollPane.setViewportView(jobTable);
	}

	private void tableInit() throws FileNotFoundException {
		jobTable = new JTable();
		jobTable.setModel(new OSTableModel());
		jobTable.getColumnModel().getColumn(6).setPreferredWidth(239);
		jobTypeCol = jobTable.getColumnModel().getColumn(3);
		jobTypeCol.setCellEditor(new ComboBoxCellEditor(cmbJobType));
		jobTypeCol.setCellRenderer(new ComboTableCellRenderer());
		progressCol = jobTable.getColumnModel().getColumn(6);
		progressCol.setCellRenderer(new ProgressRenderer(0, 100));
		jobTable.setName("jobTable");
		jobTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(jobTable);
	}

	protected void enableComponents() {
		btnSave.setEnabled(true);
		btnNewjob.setEnabled(true);
		btnGenjob.setEnabled(true);
		btnDeljob.setEnabled(true);
		btnRun.setEnabled(true);
		runSpeed.setEnabled(true);
	}

	protected void disableComponents() {
		btnSave.setEnabled(false);
		btnNewjob.setEnabled(false);
		btnGenjob.setEnabled(false);
		btnDeljob.setEnabled(false);
		btnRun.setEnabled(false);
	}

}
