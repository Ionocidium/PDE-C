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
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import controller.EventController;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class DebuggingManager {

	private JFrame frmBreakpointManager;
	private JPanel breakpointPanel, variablePanel, watchPanel;
	private static DebuggingManager instance = null;
	final private String[] varColumnNames = 
	{
	    "Variable Name", "Value"
	};
	private Object[][] varData = {
            {"Nothing to display because debug is not active."}};;
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
	
	public void modifyMe(){
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
		lmbp = new DefaultListModel<Integer>();
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
		bpListPane.setBorder(new TitledBorder(null, "Breakpoint List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_bpListPane = new GridBagConstraints();
		gbc_bpListPane.insets = new Insets(0, 0, 0, 5);
		gbc_bpListPane.fill = GridBagConstraints.BOTH;
		gbc_bpListPane.gridx = 0;
		gbc_bpListPane.gridy = 0;
		breakpointPanel.add(bpListPane, gbc_bpListPane);
		bpListPane.setLayout(new BorderLayout(0, 0));
		

		
		bpList = new JList<Integer>();
		
		JScrollPane bpListScrollPane = new JScrollPane(bpList);
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
		
		JButton btnAddABreakpoint = new JButton("Add A Breakpoint");
		btnAddABreakpoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindowView mwv = MainWindowView.getInstance();
				int value = MainWindowView.eventController.addbreakpoint(frmBreakpointManager, mwv.getGut(), mwv.getBreakpoints()) + 1;
				if(value > 0)
				{
					lmbp.addElement(value);
					bpList.setModel(lmbp);
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
		
		JButton btnRemoveSelected = new JButton("Remove Selected Breakpoint");
		btnRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Integer> selected = bpList.getSelectedValuesList();
				if(selected.size() > 0)
				{
					MainWindowView mwv = MainWindowView.getInstance();
					for(int i = 0; i < selected.size(); i++)
					{
						lmbp.removeElement(selected.get(i));
						MainWindowView.eventController.silentDeleteBreakpoint(mwv.getGut(), mwv.getBreakpoints(), selected.get(i));
						if(mwv.getBreakpoints().size() == 0) {
							mwv.getDelbreakpointButton().setEnabled(false);
							mwv.getDelallbreakpointButton().setEnabled(false);
						}
					}
					bpList.setModel(lmbp);
				}
			}
		});
		GridBagConstraints gbc_btnRemoveSelected = new GridBagConstraints();
		gbc_btnRemoveSelected.fill = GridBagConstraints.BOTH;
		gbc_btnRemoveSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSelected.gridx = 1;
		gbc_btnRemoveSelected.gridy = 0;
		bpOptionPane.add(btnRemoveSelected, gbc_btnRemoveSelected);
		
		JButton btnRemoveAll = new JButton("Remove All Breakpoints");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindowView mwv = MainWindowView.getInstance();
				lmbp = new DefaultListModel<Integer>();
				bpList.setModel(new DefaultListModel<Integer>());
				MainWindowView.eventController.deleteallbreakpoint(mwv.getGut(), mwv.getBreakpoints());
				mwv.getDelbreakpointButton().setEnabled(false);
				mwv.getDelallbreakpointButton().setEnabled(false);
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
		bpHelperPane.add(txtBpHelper, BorderLayout.CENTER);
		txtBpHelper.setWrapStyleWord(true);
		txtBpHelper.setLineWrap(true);
		txtBpHelper.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtBpHelper.setText("How To Use Breakpoints\r\n\r\nTo add a breakpoint in your code, click on a line of code in your program (to move the typing cursor to that line) where you want the breakpoint to be, then press CTRL+F5. You may also click on the Add Breakpoint button above. Doing so will add the line number of that code to the list of breakpoints. You may have as many breakpoints as you want.\r\n\r\nTo remove a breakpoint, select the line number of the breakpoint you want to remove from the list of breakpoints, then click on the Remove Selected Breakpoint button. Alternatively, you may remove all breakponts from your program by clicking the Remove All Breakpoints.");
		variablePanel = new JPanel();
		tabbedPane.addTab("Variables", null, variablePanel, null);
		variablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane varListScrollPane = new JScrollPane(varTable);
		variablePanel.add(varListScrollPane, BorderLayout.CENTER);
		variableModel = new DefaultTableModel(varData, varColumnNames){
        	
        	@Override
        	public boolean isCellEditable(int row, int column){return false;}
        	
        };
		varTable = new JTable(variableModel);
		varTable.setCellSelectionEnabled(true);
		varListScrollPane.setViewportView(varTable);
		
		JPanel varOptionsPane = new JPanel();
		varOptionsPane.setBorder(new TitledBorder(null, "Options", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		variablePanel.add(varOptionsPane, BorderLayout.EAST);
		GridBagLayout gbl_varOptionsPane = new GridBagLayout();
		gbl_varOptionsPane.columnWidths = new int[]{90, 63, 0};
		gbl_varOptionsPane.rowHeights = new int[]{24, 24, 0, 0};
		gbl_varOptionsPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_varOptionsPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		varOptionsPane.setLayout(gbl_varOptionsPane);
		
		JButton btnStepOver_1 = new JButton("Step Over");
		GridBagConstraints gbc_btnStepOver_1 = new GridBagConstraints();
		gbc_btnStepOver_1.fill = GridBagConstraints.BOTH;
		gbc_btnStepOver_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnStepOver_1.gridx = 0;
		gbc_btnStepOver_1.gridy = 0;
		varOptionsPane.add(btnStepOver_1, gbc_btnStepOver_1);
		
		JButton btnContinue_1 = new JButton("Continue");
		GridBagConstraints gbc_btnContinue_1 = new GridBagConstraints();
		gbc_btnContinue_1.fill = GridBagConstraints.BOTH;
		gbc_btnContinue_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnContinue_1.gridx = 1;
		gbc_btnContinue_1.gridy = 0;
		varOptionsPane.add(btnContinue_1, gbc_btnContinue_1);
		
		JButton btnTrackVars = new JButton("Track Variable");
		GridBagConstraints gbc_btnTrackVars = new GridBagConstraints();
		gbc_btnTrackVars.fill = GridBagConstraints.BOTH;
		gbc_btnTrackVars.insets = new Insets(0, 0, 5, 5);
		gbc_btnTrackVars.gridx = 0;
		gbc_btnTrackVars.gridy = 1;
		varOptionsPane.add(btnTrackVars, gbc_btnTrackVars);
		
		JButton btnStop_1 = new JButton("Stop");
		GridBagConstraints gbc_btnStop_1 = new GridBagConstraints();
		gbc_btnStop_1.fill = GridBagConstraints.BOTH;
		gbc_btnStop_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnStop_1.gridx = 1;
		gbc_btnStop_1.gridy = 1;
		varOptionsPane.add(btnStop_1, gbc_btnStop_1);
		
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
		txtVarHelper.setText("How to Use the Debugger\r\n\r\nThe debugger is a useful tool in tracing and debugging your work. This tool allows you to run your code partially and view the values of the variables in your program along the way. \r\n\r\nBefore you start debugging, let's first set up your breakpoints. A breakpoint is an intentional stopping or pausing location in a program, put in place for debugging purposes. If you run your code in debug mode, your code will automatically stop when it reaches a breakpoint, which will allow you to view the values of your variables at certain points of your program!");
		txtVarHelper.setFont(new Font("Tahoma", Font.PLAIN, 11));
		watchPanel = new JPanel();
		tabbedPane.addTab("Watches", null, watchPanel, null);
		watchPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		watchPanel.add(panel, BorderLayout.CENTER);
		
		JLabel lblUnderConstruction = new JLabel("Under Construction. Feature Coming Soon");
		panel.add(lblUnderConstruction);
	}
}
