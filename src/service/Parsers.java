package service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Element;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;

import controller.EventController;

public class Parsers extends AbstractParser
{

	private DefaultParseResult res;
	
	public Parsers(){
		res = new DefaultParseResult(this);
	}
	
	@Override
	public ParseResult parse(RSyntaxDocument doc, String style)
	{
	    EventController eventC = EventController.getEventController();
		res.clearNotices();
		
		int lineCount = doc.getDefaultRootElement().getElementCount();
		res.setParsedLines(0, lineCount - 1);
		
		long compileStart = System.currentTimeMillis();
		try
		{
			File temporary = File.createTempFile("CTempFile", ".tmp");
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(temporary));
			try {
				new DefaultEditorKit().write(out, doc, 0, doc.getLength());
			} catch (BadLocationException ble) {
				ble.printStackTrace();
				throw new IOException(ble.getMessage());
			}
			out.close();
			
			// COMPILE ME HERE using donttouch.bat
			
			//eventC.compile(filePath);
			Element root = doc.getDefaultRootElement();
			COutputCollector stderr = new COutputCollector(
					p.getErrorStream(), this, res, root);
			long compileTime = System.currentTimeMillis() - compileStart;
			res.setParseTime(compileStart);
			//System.out.println(time + "ms");
		}
		catch (IOException ioe)
		{
			res.setError(ioe);
			ioe.printStackTrace();
		}
		
		return null;
	}

  
}
