package debugging.model;

import java.util.ArrayList;

public class RowLocalObject {

	private int row;
	private LocalObject localVarVal;
	
	public RowLocalObject(int row, LocalObject localVarVal)
	{
		this.row = row;
		this.localVarVal = localVarVal;
	}
	
	public boolean isConsistent(int row)
	{
		if(this.row == row)
			return true;
		else return false;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the localVarVal
	 */
	public LocalObject getLocalVarVal() {
		return localVarVal;
	}

	/**
	 * @param localVarVal the localVarVal to set
	 */
	public void setLocalVarVal(LocalObject localVarVal) {
		this.localVarVal = localVarVal;
	}
}
