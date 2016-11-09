package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplifyError {
	
	private static String ERROR = "error: ";
	private static String WARNING = "warning: ";
	
	private static String NOTE_UNDECLARED_IDENTIFIER = "NOTE: Undeclared variables are reported once for every function";
	
	private static String MISSING_SEMICOLON = "There is a missing semicolon (';') before line ";
	private static String MISSING_PARENTHESIS_L = "There is a missing parenthesis ('(') in line ";
	private static String MISSING_PARENTHESIS_R = "There is a missing parenthesis (')') in line ";
	private static String MISSING_BRACE_L = "There is a missing brace ('{') in line ";
	private static String MISSING_BRACE_R = "There is a missing brace ('}') in line ";
	private static String MISSING_INITIALIZE = "There is a missing equals sign ('=') in line ";
	private static String MISSING_EXPRESSION = "There are missing expressions (values, constants, variables or operators) in line ";
	private static String MISSING_STATEMENT = "There is a missing statement in line ";
	private static String MISSING_IF_FROM_ELSE = "There is a missing 'if' statement for 'else' in line ";
	private static String MISSING_TERMINATOR = "There is a missing terminating character in line ";
	private static String UNKNOWN_TYPE = "There is an unknown data type in line ";
	private static String CHARACTER_TOO_LONG = "The value for 'char' is too long in line ";
	private static String UNDECLARED_VARIABLE = "There is an undeclared variable in line ";
	
	private static String FEW_PRINTF = "The function 'printf' is incomplete in line ";
	private static String LEFT_VALUE_NOT_ASSIGNABLE = "You cannot assign the leftmost value in line ";
	private static String INVALID_SUFFIX = "There is an invalid suffix for a data type in line ";
	
	private static final Pattern PATTERN_NOTE_UNDECLARED_IDENTIFIER = Pattern.compile("(note: each undeclared identifier is reported only once for each function it appears in)");
	
	private static final Pattern PATTERN_MISSING_SEMICOLON = Pattern.compile("(error: expected ';')");
	private static final Pattern PATTERN_MISSING_PARENTHESIS_L = Pattern.compile("error: expected identifier or '\\(' before");
	private static final Pattern PATTERN_MISSING_PARENTHESIS_R = Pattern.compile("error: expected '\\)' before");
	private static final Pattern PATTERN_MISSING_BRACE_L = Pattern.compile("(error: expected '\\{' at end of input)|(error: expected declaration specifiers before '\\}' token)");
	private static final Pattern PATTERN_MISSING_BRACE_R = Pattern.compile("(error: expected declaration or statement at end of input)");
	private static final Pattern PATTERN_MISSING_INITIALIZE = Pattern.compile("(error: expected '=')");
	private static final Pattern PATTERN_MISSING_EXPRESSION = Pattern.compile("(error: expected expression before)");
	private static final Pattern PATTERN_MISSING_STATEMENT = Pattern.compile("(error: expected statement before)");
	private static final Pattern PATTERN_MISSING_IF_FROM_ELSE = Pattern.compile("(error: 'else' without a previous 'if')");
	private static final Pattern PATTERN_MISSING_TERMINATOR = Pattern.compile("(warning: missing terminating)|(error: missing terminating)");
	private static final Pattern PATTERN_UNDECLARED_VARIABLE = Pattern.compile("(undeclared \\(first use in this function\\))");
	private static final Pattern PATTERN_UNKNOWN_TYPE = Pattern.compile("(error: unknown type name)");
	private static final Pattern PATTERN_CHARACTER_TOO_LONG = Pattern.compile("(warning: character constant too long for its type)");
	private static final Pattern PATTERN_FEW_PRINTF = Pattern.compile("(error: too few arguments to function 'printf')");
	private static final Pattern PATTERN_LEFT_VALUE_NOT_ASSIGNABLE = Pattern.compile("(error: lvalue required as left operand of assignment)");
	private static final Pattern PATTERN_INVALID_SUFFIX = Pattern.compile("(error: invalid suffix)");

	private static final Pattern PATTERN_PATH_MAIN = Pattern.compile("(?!.c:)(In function 'main':)");
	private static final Pattern PATTERN_PATH = Pattern.compile("((?!.c:)[0-9]+(?=:\\d))"); //pattern for path
	private static final Pattern PATTERN_ERROR = Pattern.compile("((error:)|(warning:)|(note:)) (.*)"); //pattern for error/note/warning

	private String error;

	public SimplifyError(String error) {
		this.error = error;
	}
	
	public String simplify(){

		String line = error;
		String errorDesc = line;
		Matcher m = PATTERN_PATH.matcher(line);
		
		if (m.find()) {

			errorDesc = line;
			line = line.substring(0, line.length()-m.group().length());

			Matcher m2 = PATTERN_ERROR.matcher(errorDesc);
			if (m2.find())
			{
				errorDesc = m2.group();
				errorDesc = convert(errorDesc, m.group());
				//System.out.println(errorDesc);
				return errorDesc;
			}

		}
		
		m = PATTERN_PATH_MAIN.matcher(line);
		
		if (m.find()) {
			errorDesc = "";
			//System.out.println(errorDesc);
			return errorDesc;
		}
		
		return errorDesc;
	}
	
	private String convert(String errorDesc, String lineNumber) {
		Matcher m;
		
		m = PATTERN_NOTE_UNDECLARED_IDENTIFIER.matcher(errorDesc);
		if (m.find()) {
			errorDesc = NOTE_UNDECLARED_IDENTIFIER;
			return errorDesc;
		}
		m = PATTERN_UNDECLARED_VARIABLE.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + UNDECLARED_VARIABLE + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_SEMICOLON.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_SEMICOLON + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_PARENTHESIS_L.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_PARENTHESIS_L + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_PARENTHESIS_R.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_PARENTHESIS_R + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_BRACE_L.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_BRACE_L + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_BRACE_R.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_BRACE_R + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_INITIALIZE.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_INITIALIZE + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_EXPRESSION.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_EXPRESSION + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_STATEMENT.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_STATEMENT + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_IF_FROM_ELSE.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_IF_FROM_ELSE + lineNumber;
			return errorDesc;
		}
		m = PATTERN_MISSING_TERMINATOR.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + MISSING_TERMINATOR + lineNumber;
			return errorDesc;
		}
		m = PATTERN_UNKNOWN_TYPE.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + UNKNOWN_TYPE + lineNumber;
			return errorDesc;
		}
		m = PATTERN_CHARACTER_TOO_LONG.matcher(errorDesc);
		if (m.find()) {
			errorDesc = WARNING + CHARACTER_TOO_LONG + lineNumber;
			return errorDesc;
		}
		m = PATTERN_FEW_PRINTF.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + FEW_PRINTF + lineNumber;
			return errorDesc;
		}
		m = PATTERN_LEFT_VALUE_NOT_ASSIGNABLE.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + LEFT_VALUE_NOT_ASSIGNABLE + lineNumber;
			return errorDesc;
		}
		m = PATTERN_INVALID_SUFFIX.matcher(errorDesc);
		if (m.find()) {
			errorDesc = ERROR + INVALID_SUFFIX + lineNumber;
			return errorDesc;
		}
		//System.out.println(errorDesc);
		return errorDesc;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	

}
