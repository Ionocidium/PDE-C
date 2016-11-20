package debugging.controls;

import debugging.model.LocalObject;

public class LocalVariableListExtractor {

	public LocalObject getLocal(String line)
	{
		return new LocalObject(extractVars(line), extractVals(line));
	}
	
	public String extractVars(String line)
    {
        return line.substring(0, line.indexOf("=")).trim();
    }
    
    public String extractVals(String line)
    {
        return line.substring(line.indexOf("=") + 1).trim();
    }
}
