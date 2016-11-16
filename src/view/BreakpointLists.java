package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
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
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class BreakpointLists {

	private JFrame frmBreakpointManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BreakpointLists window = new BreakpointLists();
					window.frmBreakpointManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BreakpointLists() {
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
		frmBreakpointManager = new JFrame();
		frmBreakpointManager.setAlwaysOnTop(true);
		frmBreakpointManager.setTitle("Breakpoint Manager");
		frmBreakpointManager.setResizable(false);
		frmBreakpointManager.setBounds(100, 100, 450, 300);
		frmBreakpointManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBreakpointManager.getContentPane().setLayout(null);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "4", "8", "16", "32", "64", "128", "256", "512"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 11, 208, 249);
		frmBreakpointManager.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Options", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(228, 11, 206, 249);
		frmBreakpointManager.getContentPane().add(panel);
		panel.setLayout(new GridLayout(8, 0, 0, 0));
		
		JButton btnAddABreakpoint = new JButton("Add A Breakpoint");
		panel.add(btnAddABreakpoint);
		
		JButton btnRemoveSelected = new JButton("Remove Selected Breakpoint");
		panel.add(btnRemoveSelected);
		
		JButton btnRemoveAll = new JButton("Remove All Breakpoints");
		panel.add(btnRemoveAll);
	}
}
