package controller;

import javax.swing.JEditorPane;

public class AutoFormat {

	private JEditorPane editorPane;
	
	public AutoFormat(){
		this.editorPane = new JEditorPane();
	}
	
	public AutoFormat(JEditorPane jep){
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
	
	public JEditorPane getEditorPane(){
		return this.editorPane;
	}
	
	public void setEditorPane(JEditorPane jep){
		this.editorPane = jep;
	}
	
}
