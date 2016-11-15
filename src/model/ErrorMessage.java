package model;

public class ErrorMessage {

	private String type;
	private String message;
	private String line;
	
	public ErrorMessage(String type, String message, String line) {
		this.type = type;
		this.message = message;
		this.line = line;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	public String getErrorMessage() {
		return this.getType() + this.getMessage() + this.getLine();
	}
	
}
