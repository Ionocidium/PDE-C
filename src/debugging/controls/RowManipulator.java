package debugging.controls;

import java.util.ArrayList;

import debugging.model.LocalObject;
import debugging.model.RowLocalObject;

public class RowManipulator {

	public int checkDifference(ArrayList<LocalObject> aLocal, ArrayList<RowLocalObject> aRowLocal, LocalObject target)
	{
		int minuend = 0, subtrahend = 0;
		// compare first local variables listed
		minuend = searchLocalVarByIndex(aLocal, target);
		subtrahend = searchTrackingLocalVarByIndex(aRowLocal, target);
		return minuend - subtrahend; 
	}
	
	public int searchLocalVarByIndex(ArrayList<LocalObject> aLocal, LocalObject target)
	{
		for(int i = 0; i < aLocal.size(); i++)
		{
			if(aLocal.get(i).getVariable().equals(target.getVariable()))
			{
				return i;
			}
		}
		return -1;
	}

	public int searchTrackingLocalVarByIndex(ArrayList<RowLocalObject> aLocal, LocalObject target)
	{
		for(int i = 0; i < aLocal.size(); i++)
		{
			if(aLocal.get(i).getLocalVarVal().getVariable().equals(target.getVariable()))
			{
				return i;
			}
		}
		return -1;
	}
}
