package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Element;

import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;

public class SimplifyError {
	
	private static String MISSING_SEMICOLON = "There is a missing semicolon (';') in line ";
	private static String MISSING_PARENTHESIS_L = "There is a missing parenthesis ('(') in line ";
	private static String MISSING_PARENTHESIS_R = "There is a missing parenthesis (')') in line ";
	private static String MISSING_BRACE_L = "There is a missing parenthesis ('{') in line ";
	private static String MISSING_BRACE_R = "There is a missing parenthesis ('}') in line ";
	
	private static final Pattern ERROR_PATTERN = Pattern.compile("((?!.c:)[0-9]+(?=:\\d))");
	private static final Pattern ERROR_PATTERN2 = Pattern.compile("((error:)|(warning:)) (.*)");
	
	private String error;

	public SimplifyError(String error) {
		this.error = error;
	}
	
	public String simplify(){

		String line = error;
		String errorDesc = line;
		Matcher m = ERROR_PATTERN.matcher(line);
		

		if (m.find()) {

			errorDesc = line;
			line = line.substring(0, line.length()-m.group().length());

			Matcher m2 = ERROR_PATTERN2.matcher(errorDesc);
			if (m2.find())
			{
				errorDesc = m2.group();
				//System.out.println(errorDesc);
				return errorDesc + "\n";
			}

		}
		
		return errorDesc;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	

}
