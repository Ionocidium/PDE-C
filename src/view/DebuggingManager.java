package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import controller.EventController;
import debugging.controls.RowManipulator;
import debugging.model.LocalObject;
import debugging.model.RowLocalObject;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class DebuggingManager {

	private JFrame frmBreakpointManager;
	private JPanel breakpointPanel, variablePanel, watchPanel;
	private JButton btnStepOver, btnContinue, btnTrackVars, btnStop; // Variables Tab
	private JButton btnAddABreakpoint, btnRemoveSelected, btnRemoveAll; // Variables Tab
//    private ArrayList<RowLocalObject> watchList2 = new ArrayList<RowLocalObject>();
    private HashMap<String, String> varVals = new HashMap<String, String>();
	private static DebuggingManager instance = null;
	final private RowManipulator rm = new RowManipulator();
//	Unstable version of Tracking Variables.
//	final private DefaultTableCellRenderer tracker = new DefaultTableCellRenderer(){
//        @Override
//        public Component getTableCellRendererComponent(JTable table,
//                Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//
//            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
//            if(!watchList2.isEmpty())
//            {
//            	boolean existing = false;
//            	for(int i = 0; i < watchList2.size(); i++)
//            	{
//            		if(row == watchList2.get(i).getRow() && table.getValueAt(watchList2.get(i).getRow(), 0).equals(watchList2.get(i).getLocalVarVal().getVariable())) existing = true;
//            	}
//	            if (existing) {
//	            	setBackground(new Color(53, 208, 53));
//	                setForeground(Color.BLACK);
//	            } else {
//	                setBackground(Color.WHITE);
//	                setForeground(Color.BLACK);
//	            }
//            }
//            return this;
//        }
//	};
	final private String[] varColumnNames = 
	{
	    "Variable Name", "Value"
	};
	private Object[][] varData = {
            {"Nothing to display because debug is not active."}};
	private DefaultTableModel variableModel;
	
	private JList<Integer> bpList;
	private DefaultListModel<Integer> lmbp;
	private JTable varTable;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					BreakpointLists window = getInstance();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static DebuggingManager getInstance()
	{
	  if (instance == null)
	  {
		instance = new DebuggingManager();
	  }
	  
	  return instance;
	}
	
	public void openMe(){
		if(!frmBreakpointManager.isVisible())
			frmBreakpointManager.setVisible(true);
		else frmBreakpointManager.requestFocus();
	}

	public void resetDebuggingTable(){
		varTable.setModel(variableModel);
	}
	
	public void modifyDebugging(HashMap<String, String> aLocal){
		setVarVals(aLocal);
		Map<String, String> map = new TreeMap<String, String>(aLocal); // sorts keys in ascending order ref: http://stackoverflow.com/questions/7860822/sorting-hashmap-based-on-keys
	
		DefaultTableModel listDebugging = new DefaultTableModel(varColumnNames, 0){
        	
        	@Override
        	public boolean isCellEditable(int row, int column){return false;}
        	
        };
        
        // iterate over all the map items ref: http://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-map
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			Object[] localVarData = {entry.getKey(), entry.getValue()};
			listDebugging.addRow(localVarData);
		}
		varTable.setModel(listDebugging);
	}
	
	public void modifyBreakpoints(){
		DefaultListModel<Integer> listBp = new DefaultListModel<Integer>();
		MainWindowView mwv = MainWindowView.getInstance();
		ArrayList<Integer> bp = mwv.getBreakpoints();
		for(int i = 0; i < bp.size(); i++)
			listBp.addElement(bp.get(i) + 1);
		bpList.setModel(listBp);
	}
	
	/**
	 * Create the application.
	 */
	private DebuggingManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		setLmbp(new DefaultListModel<Integer>());
		frmBreakpointManager = new JFrame();
		frmBreakpointManager.setAlwaysOnTop(true);
		frmBreakpointManager.setTitle("Debugging Manager");
		frmBreakpointManager.setResizable(false);
		frmBreakpointManager.setBounds(100, 100, 720, 480);
		frmBreakpointManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBreakpointManager.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmBreakpointManager.getContentPane().add(tabbedPane);
		
		breakpointPanel = new JPanel();
		tabbedPane.addTab("Breakpoints", null, breakpointPanel, null);
		GridBagLayout gbl_breakpointPanel = new GridBagLayout();
		gbl_breakpointPanel.columnWidths = new int[]{262, 0, 0, 0};
		gbl_breakpointPanel.rowHeights = new int[]{197, 0};
		gbl_breakpointPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_breakpointPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		breakpointPanel.setLayout(gbl_breakpointPanel);
		
		JPanel bpListPane = new JPanel();
		bpListPane.setToolTipText("Displays all breakpoints.");
		bpListPane.setBorder(new TitledBorder(null, "Breakpoint List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_bpListPane = new GridBagConstraints();
		gbc_bpListPane.insets = new Insets(0, 0, 0, 5);
		gbc_bpListPane.fill = GridBagConstraints.BOTH;
		gbc_bpListPane.gridx = 0;
		gbc_bpListPane.gridy = 0;
		breakpointPanel.add(bpListPane, gbc_bpListPane);
		bpListPane.setLayout(new BorderLayout(0, 0));
		

		
		bpList = new JList<Integer>();
		bpList.setToolTipText("Displays all breakpoints.");
		
		JScrollPane bpListScrollPane = new JScrollPane(bpList);
		bpListScrollPane.setToolTipText("Displays all breakpoints.");
		bpListPane.add(bpListScrollPane, BorderLayout.CENTER);
		
		JPanel bpOptionPane = new JPanel();
		GridBagConstraints gbc_bpOptionPane = new GridBagConstraints();
		gbc_bpOptionPane.fill = GridBagConstraints.VERTICAL;
		gbc_bpOptionPane.gridx = 2;
		gbc_bpOptionPane.gridy = 0;
		breakpointPanel.add(bpOptionPane, gbc_bpOptionPane);
		bpOptionPane.setBorder(new TitledBorder(null, "Options", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagLayout gbl_bpOptionPane = new GridBagLayout();
		gbl_bpOptionPane.columnWidths = new int[]{60, 91, 76, 0};
		gbl_bpOptionPane.rowHeights = new int[]{24, 0, 0};
		gbl_bpOptionPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_bpOptionPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		bpOptionPane.setLayout(gbl_bpOptionPane);
		
		btnAddABreakpoint = new JButton("Add A Breakpoint");
		btnAddABreakpoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindowView mwv = MainWindowView.getInstance();
				int value = MainWindowView.eventController.addbreakpoint(frmBreakpointManager, mwv.getGut(), mwv.getBreakpoints()) + 1;
				if(value > 0)
				{
					modifyBreakpoints();
					if(mwv.getBreakpoints().size() > 0) {
						mwv.getDelbreakpointButton().setEnabled(true);
						mwv.getDelallbreakpointButton().setEnabled(true);
					}
				}
			}
		});
		GridBagConstraints gbc_btnAddABreakpoint = new GridBagConstraints();
		gbc_btnAddABreakpoint.fill = GridBagConstraints.BOTH;
		gbc_btnAddABreakpoint.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddABreakpoint.gridx = 0;
		gbc_btnAddABreakpoint.gridy = 0;
		bpOptionPane.add(btnAddABreakpoint, gbc_btnAddABreakpoint);
		
		btnRemoveSelected = new JButton("Remove Selected Breakpoint");
		btnRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Integer> selected = bpList.getSelectedValuesList();
				if(selected.size() > 0)
				{
					MainWindowView mwv = MainWindowView.getInstance();
					for(int i = 0; i < selected.size(); i++)
					{
						MainWindowView.eventController.silentDeleteBreakpoint(mwv.getGut(), mwv.getBreakpoints(), selected.get(i));
						if(mwv.getBreakpoints().size() == 0) {
							mwv.getDelbreakpointButton().setEnabled(false);
							mwv.getDelallbreakpointButton().setEnabled(false);
						}
					}
					modifyBreakpoints();
				}
			}
		});
		GridBagConstraints gbc_btnRemoveSelected = new GridBagConstraints();
		gbc_btnRemoveSelected.fill = GridBagConstraints.BOTH;
		gbc_btnRemoveSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSelected.gridx = 1;
		gbc_btnRemoveSelected.gridy = 0;
		bpOptionPane.add(btnRemoveSelected, gbc_btnRemoveSelected);
		
		btnRemoveAll = new JButton("Remove All Breakpoints");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindowView mwv = MainWindowView.getInstance();
				MainWindowView.eventController.deleteallbreakpoint(mwv.getGut(), mwv.getBreakpoints());
				mwv.getDelbreakpointButton().setEnabled(false);
				mwv.getDelallbreakpointButton().setEnabled(false);
				modifyBreakpoints();
			}
		});
		GridBagConstraints gbc_btnRemoveAll = new GridBagConstraints();
		gbc_btnRemoveAll.fill = GridBagConstraints.BOTH;
		gbc_btnRemoveAll.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemoveAll.gridx = 2;
		gbc_btnRemoveAll.gridy = 0;
		bpOptionPane.add(btnRemoveAll, gbc_btnRemoveAll);
		
		JPanel bpHelperPane = new JPanel();
		bpHelperPane.setBorder(new TitledBorder(null, "Help", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_bpHelperPane = new GridBagConstraints();
		gbc_bpHelperPane.gridwidth = 3;
		gbc_bpHelperPane.fill = GridBagConstraints.BOTH;
		gbc_bpHelperPane.gridx = 0;
		gbc_bpHelperPane.gridy = 1;
		bpOptionPane.add(bpHelperPane, gbc_bpHelperPane);
		bpHelperPane.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtBpHelper = new JTextArea();
		txtBpHelper.setEditable(false);
		bpHelperPane.add(txtBpHelper, BorderLayout.CENTER);
		txtBpHelper.setWrapStyleWord(true);
		txtBpHelper.setLineWrap(true);
		txtBpHelper.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtBpHelper.setText("How To Use Breakpoints" + System.getProperty("line.separator") + 
				System.getProperty("line.separator") + 
				"To add a breakpoint in your code, click on a line of code in your program (to move the typing cursor to that line) where you want the breakpoint to be, then press CTRL+F5. You may also click on the Add Breakpoint button above. Doing so will add the line number of that code to the list of breakpoints. You may have as many breakpoints as you want." + 
				System.getProperty("line.separator") + System.getProperty("line.separator") + 
				"To remove a breakpoint, select the line number of the breakpoint you want to remove from the list of breakpoints, then click on the Remove Selected Breakpoint button. Alternatively, you may remove all breakponts from your program by clicking the Remove All Breakpoints." + 
				System.getProperty("line.separator") + System.getProperty("line.separator") + 
				"How to Use the Debugger" + System.getProperty("line.separator") + 
				System.getProperty("line.separator") + 
				"The debugger is a useful tool in tracing and debugging your work. This tool allows you to run your code partially and view the values of the variables in your program along the way. " + 
				System.getProperty("line.separator") + System.getProperty("line.separator") + 
				"Before you start debugging, let's first set up your breakpoints. A breakpoint is an intentional stopping or pausing location in a program, put in place for debugging purposes. If you run your code in debug mode, your code will automatically stop when it reaches a breakpoint, which will allow you to view the values of your variables at certain points of your program!");
		variablePanel = new JPanel();
		tabbedPane.addTab("Variables", null, variablePanel, null);
		variablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane varListScrollPane = new JScrollPane(varTable);
		varListScrollPane.setToolTipText("Displays all local variables.");
		variablePanel.add(varListScrollPane, BorderLayout.CENTER);
		variableModel = new DefaultTableModel(varData, varColumnNames){
        	
        	@Override
        	public boolean isCellEditable(int row, int column){return false;}
        	
        };
		varTable = new JTable(variableModel);
		varTable.setCellSelectionEnabled(true);
//		varTable.setDefaultRenderer(Object.class, tracker); // unstable
		varListScrollPane.setViewportView(varTable);
		
		JPanel varOptionsPane = new JPanel();
		varOptionsPane.setBorder(new TitledBorder(null, "Options", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		variablePanel.add(varOptionsPane, BorderLayout.EAST);
		GridBagLayout gbl_varOptionsPane = new GridBagLayout();
		gbl_varOptionsPane.columnWidths = new int[]{120, 120, 0};
		gbl_varOptionsPane.rowHeights = new int[]{24, 24, 0, 0};
		gbl_varOptionsPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_varOptionsPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		varOptionsPane.setLayout(gbl_varOptionsPane);
		
		btnStepOver = new JButton("Step Over");
		btnStepOver.setEnabled(false);
		GridBagConstraints gbc_btnStepOver = new GridBagConstraints();
		gbc_btnStepOver.fill = GridBagConstraints.BOTH;
		gbc_btnStepOver.insets = new Insets(0, 0, 5, 5);
		gbc_btnStepOver.gridx = 0;
		gbc_btnStepOver.gridy = 0;
		varOptionsPane.add(btnStepOver, gbc_btnStepOver);
		
		btnContinue = new JButton("Start");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				MainWindowView mwv = MainWindowView.getInstance();
				MainWindowView.eventController.debugToggler();
				MainWindowView.eventController.debugInit(mwv.getFilePath());
			}
		});
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.fill = GridBagConstraints.BOTH;
		gbc_btnContinue.insets = new Insets(0, 0, 5, 0);
		gbc_btnContinue.gridx = 1;
		gbc_btnContinue.gridy = 0;
		varOptionsPane.add(btnContinue, gbc_btnContinue);
		
		btnTrackVars = new JButton("Track Variable");
		btnTrackVars.setEnabled(false);
		GridBagConstraints gbc_btnTrackVars = new GridBagConstraints();
		gbc_btnTrackVars.fill = GridBagConstraints.BOTH;
		gbc_btnTrackVars.insets = new Insets(0, 0, 5, 5);
		gbc_btnTrackVars.gridx = 0;
		gbc_btnTrackVars.gridy = 1;
		varOptionsPane.add(btnTrackVars, gbc_btnTrackVars);
		
		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.fill = GridBagConstraints.BOTH;
		gbc_btnStop.insets = new Insets(0, 0, 5, 0);
		gbc_btnStop.gridx = 1;
		gbc_btnStop.gridy = 1;
		varOptionsPane.add(btnStop, gbc_btnStop);
		
		JPanel varHelperPane = new JPanel();
		varHelperPane.setBorder(new TitledBorder(null, "Help", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_varHelperPane = new GridBagConstraints();
		gbc_varHelperPane.gridwidth = 2;
		gbc_varHelperPane.fill = GridBagConstraints.BOTH;
		gbc_varHelperPane.gridx = 0;
		gbc_varHelperPane.gridy = 2;
		varOptionsPane.add(varHelperPane, gbc_varHelperPane);
		varHelperPane.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtVarHelper = new JTextArea();
		varHelperPane.add(txtVarHelper);
		txtVarHelper.setTabSize(0);
		txtVarHelper.setWrapStyleWord(true);
		txtVarHelper.setLineWrap(true);
		txtVarHelper.setEditable(false);
		txtVarHelper.setText("Now that you have set up your breakpoints, we can now begin debugging your code. "
				+ "You may click on the Start button above to start running your code. "
				+ "You will notice that the program will stop at the first breakpoint encountered (hopefully you have properly set up your breakpoints!)."
				+ System.getProperty("line.separator") + System.getProperty("line.separator")
				+ "To the left, you will now see the variables present in your code, with their respective current values on the line marked with the breakpoint. "
				+ "From here, you may click Step Over to go to the next line, or click Continue to proceed to the next breakpoint."
				+ System.getProperty("line.separator") + System.getProperty("line.separator")
				+ "You may also mark certain variables so you can track their values. "
				+ "Click on a variable in the table, then click Track Variable. "
				+ "This will highlight the variable so you can observe its value more clearly as you run your code in debug mode.");
		txtVarHelper.setFont(new Font("Tahoma", Font.PLAIN, 11));
		watchPanel = new JPanel();
		tabbedPane.addTab("Watches", null, watchPanel, null);
		watchPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		watchPanel.add(panel, BorderLayout.CENTER);
		
		JLabel lblUnderConstruction = new JLabel("Under Construction. Feature Coming Soon");
		panel.add(lblUnderConstruction);
	}

	/**
	 * @return the btnStepOver
	 */
	public JButton getBtnStepOver() {
		return btnStepOver;
	}

	/**
	 * @param btnStepOver the btnStepOver to set
	 */
	public void setBtnStepOver(JButton btnStepOver) {
		this.btnStepOver = btnStepOver;
	}

	/**
	 * @return the btnContinue
	 */
	public JButton getBtnContinue() {
		return btnContinue;
	}

	/**
	 * @param btnContinue the btnContinue to set
	 */
	public void setBtnContinue(JButton btnContinue) {
		this.btnContinue = btnContinue;
	}

	/**
	 * @return the btnTrackVars
	 */
	public JButton getBtnTrackVars() {
		return btnTrackVars;
	}

	/**
	 * @param btnTrackVars the btnTrackVars to set
	 */
	public void setBtnTrackVars(JButton btnTrackVars) {
		this.btnTrackVars = btnTrackVars;
	}

	/**
	 * @return the btnStop
	 */
	public JButton getBtnStop() {
		return btnStop;
	}

	/**
	 * @param btnStop the btnStop to set
	 */
	public void setBtnStop(JButton btnStop) {
		this.btnStop = btnStop;
	}

	/**
	 * @return the btnAddABreakpoint
	 */
	public JButton getBtnAddABreakpoint() {
		return btnAddABreakpoint;
	}

	/**
	 * @param btnAddABreakpoint the btnAddABreakpoint to set
	 */
	public void setBtnAddABreakpoint(JButton btnAddABreakpoint) {
		this.btnAddABreakpoint = btnAddABreakpoint;
	}

	/**
	 * @return the btnRemoveSelected
	 */
	public JButton getBtnRemoveSelected() {
		return btnRemoveSelected;
	}

	/**
	 * @param btnRemoveSelected the btnRemoveSelected to set
	 */
	public void setBtnRemoveSelected(JButton btnRemoveSelected) {
		this.btnRemoveSelected = btnRemoveSelected;
	}

	/**
	 * @return the btnRemoveAll
	 */
	public JButton getBtnRemoveAll() {
		return btnRemoveAll;
	}

	/**
	 * @param btnRemoveAll the btnRemoveAll to set
	 */
	public void setBtnRemoveAll(JButton btnRemoveAll) {
		this.btnRemoveAll = btnRemoveAll;
	}

	/**
	 * @return the varTable
	 */
	public JTable getVarTable() {
		return varTable;
	}

	/**
	 * @param varTable the varTable to set
	 */
	public void setVarTable(JTable varTable) {
		this.varTable = varTable;
	}

	public DefaultListModel<Integer> getLmbp() {
		return lmbp;
	}

	public void setLmbp(DefaultListModel<Integer> lmbp) {
		this.lmbp = lmbp;
	}

//	/**
//	 * @return the watchList2
//	 */
//	public ArrayList<RowLocalObject> getWatchList2() {
//		return watchList2;
//	}

	/**
	 * @return the bpList
	 */
	public JList<Integer> getBpList() {
		return bpList;
	}

	public HashMap<String, String> getVarVals() {
		return varVals;
	}

	public void setVarVals(HashMap<String, String> varVals) {
		this.varVals = varVals;
	}

}
