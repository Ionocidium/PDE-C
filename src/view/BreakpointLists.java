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

public class BreakpointLists {

	private JFrame frmBreakpointManager;
	private static BreakpointLists instance = null;
	
	private JList<Integer> bpList;
	private DefaultListModel<Integer> lmbp;

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

	public static BreakpointLists getInstance()
	{
	  if (instance == null)
	  {
		instance = new BreakpointLists();
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
	private BreakpointLists() {
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
		frmBreakpointManager.setTitle("Breakpoint Manager");
		frmBreakpointManager.setResizable(false);
		frmBreakpointManager.setBounds(100, 100, 450, 300);
		frmBreakpointManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBreakpointManager.getContentPane().setLayout(null);
		
		bpList = new JList<Integer>();
		
		JScrollPane scrollPane = new JScrollPane(bpList);
		scrollPane.setBounds(10, 11, 208, 249);
		frmBreakpointManager.getContentPane().add(scrollPane);
		
		JPanel optionPane = new JPanel();
		optionPane.setBorder(new TitledBorder(null, "Options", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		optionPane.setBounds(228, 11, 206, 249);
		frmBreakpointManager.getContentPane().add(optionPane);
		optionPane.setLayout(new GridLayout(8, 0, 0, 0));
		
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
		optionPane.add(btnAddABreakpoint);
		
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
		optionPane.add(btnRemoveSelected);
		
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
		optionPane.add(btnRemoveAll);
	}
}
