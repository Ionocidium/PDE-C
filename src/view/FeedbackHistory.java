package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import controller.EventController;
import model.Feedback;
import service.Parsers;

public class FeedbackHistory extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Feedback> feedback = new ArrayList<Feedback>();
	private Path filePath;
	private JPanel container;
	private EventController eventController;
	private RSyntaxTextArea editor;
	
	private int fontSize = 16;
	private String fontStyle;

	public FeedbackHistory() {
		container = new JPanel();
		filePath = null;
		initialize();
	}
	
	private void initialize() {
		container.setLayout(new GridLayout(0,1));
		eventController = EventController.getEventController();
		editor = new RSyntaxTextArea();
		fontStyle = editor.getFont().getFamily();
		add(container);
	}
	
	public void readFile(Path feedbackFilePath) {
		
	}
	
	public void addFeedback(Feedback feedback, Path filePath, RSyntaxTextArea editorPane) {
		JButton sub = new JButton();
		sub.setHorizontalAlignment(SwingConstants.NORTH_EAST);
		sub.setFont(new Font(fontStyle, Font.PLAIN, 12));
		sub.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), sub.getBorder()));
		sub.setPreferredSize(new Dimension (this.getWidth()-30, 50));
		if (!feedback.getError().trim().isEmpty())
		{
			if(countErrors(htmlConvert(feedback.getError())) > 1)
			sub.setText("Compilation failed. " + countErrors(htmlConvert(feedback.getError())) + " errors are detected.");
			
			else if(countErrors(htmlConvert(feedback.getError())) == 1)
			sub.setText("Compilation failed. An error is detected.");
			
			sub.setBackground(Color.RED);
		}
		else
		{
			sub.setText("Compilation Complete!");
			sub.setBackground(Color.GREEN);
			eventController.runProgram(filePath);
		}
		
		sub.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			  {
				JSplitPane container = new JSplitPane(JSplitPane.VERTICAL_SPLIT){
				    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					private final int location = 300;
				    {
				        setDividerLocation( location );
				    }
				    @Override
				    public int getDividerLocation() {
				        return location ;
				    }
				    @Override
				    public int getLastDividerLocation() {
				        return location ;
				    }
				};
				
				JFrame feedbackWindow = new JFrame("Feedback");
				//feedbackWindow.setAlwaysOnTop(true);
				feedbackWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				feedbackWindow.setVisible(true);
				feedbackWindow.setSize(700,550);
				feedbackWindow.setResizable(false);
				feedbackWindow.setLocationRelativeTo(null);
				
				Parsers p = new Parsers();
				
				editor.addParser(p);
				editor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
				editor.setCodeFoldingEnabled(true);
				editor.setFont(new Font(fontStyle, Font.PLAIN, fontSize));
				RTextScrollPane scrollPane = new RTextScrollPane(editor);
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setIconRowHeaderEnabled(true);
				JComponent.setDefaultLocale(null);
				scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				scrollPane.setWheelScrollingEnabled(true);
				scrollPane.revalidate();
				Gutter gut = scrollPane.getGutter();
				
				Font monospace = new Font(fontStyle, Font.PLAIN, fontSize);
				for(int i = 0; i < gut.getComponentCount(); i++)
				{
					gut.getComponent(i).setFont(monospace);
				}
				gut.setBookmarkingEnabled(true);
				
				editor.setEditable(false);
				editor.setText(feedback.getCode());
				
				JTextArea errorLog = new JTextArea();
				errorLog.setEditable(false);
				errorLog.setText(feedback.getError());
				
				JPanel containerRecover = new JPanel();
				
				JButton recoverCode = new JButton("");
				recoverCode.setText("Recover Code");
				recoverCode.setToolTipText("Recovers Code Based on Selected Compile History");
				recoverCode.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				recoverCode.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				recoverCode.setPreferredSize(new Dimension(120, 35));
				recoverCode.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {			
						int confirmed = JOptionPane.showConfirmDialog(null, "Overwrite current code in editor?", "Recover Code", JOptionPane.YES_NO_OPTION);
					    if (confirmed == JOptionPane.YES_OPTION) 
					    {
					    	editorPane.setText(feedback.getCode());
					    	feedbackWindow.dispose();
					    }
					}
				});
				containerRecover.add(recoverCode);
				container.setTopComponent(scrollPane);
				JSplitPane bottomContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT){
				    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					private final int location = 500;
				    {
				        setDividerLocation( location );
				    }
				    @Override
				    public int getDividerLocation() {
				        return location ;
				    }
				    @Override
				    public int getLastDividerLocation() {
				        return location ;
				    }
				};
				bottomContainer.setTopComponent(new JScrollPane(errorLog));
				bottomContainer.setBottomComponent(containerRecover);
				container.setBottomComponent(bottomContainer);
				feedbackWindow.add(container);
			  }
		});
		
		container.add(sub);
		this.feedback.add(feedback);
	}
	
	private int countErrors(String s) {
		int count = 0;
		String[] c = s.split("<br />");
		count = c.length - 2;
		return count;
	}

	private String htmlConvert(String s) {
	    s = s.replaceAll("(\r\n|\n)", "<br />");
	    s = "<html>" + s + "</html>";
		return s;
	}

	public ArrayList<Feedback> getFeedback() {
		return feedback;
	}
	
	public void setFeedback(ArrayList<Feedback> feedback) {
		this.feedback = feedback;
	}

}