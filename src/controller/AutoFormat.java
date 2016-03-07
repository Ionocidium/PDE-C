package controller;

import javax.swing.JTextPane;

public class AutoFormat {

	private JTextPane editorPane;
	
	public AutoFormat(){
		this.editorPane = new JTextPane();
	}
	
	public AutoFormat(JTextPane jep){
		this.editorPane = jep;
	}
	
	public boolean checkBrace(){
		boolean result = false;
		int openBracePos = this.editorPane.getText().indexOf("{");
		int currentPos = this.editorPane.getCaretPosition();
		if(openBracePos < currentPos)
		{
			result = true;
		}
		return result;
	}
	
	public boolean checkInnerBrace(){
		boolean result = false;
		int openBracePos = this.editorPane.getText().indexOf("{");
		int currentPos = this.editorPane.getCaretPosition();
		int closeBracePos = this.editorPane.getText().indexOf("}", openBracePos);
		if(openBracePos < currentPos && currentPos <= closeBracePos)
		{
			result = true;
		}
		return result;
	}
	
	public JTextPane getEditorPane(){
		return this.editorPane;
	}
	
	public void setEditorPane(JTextPane jep){
		this.editorPane = jep;
	}
	
}
