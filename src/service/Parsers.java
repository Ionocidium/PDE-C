package service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Element;

import org.fife.rsta.ac.OutputCollector;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;

import configuration.LocalConfiguration;
import controller.EventController;

/**
 * Parses C code from <code>RSyntaxTextArea</code>.
 * 
 * <p>
 *  Based on <a href=http://svn.fifesoft.com/viewvc-1.0.5/bin/cgi/viewvc.cgi/RSTALanguageSupport/trunk/src/org/fife/rsta/ac/perl/PerlParser.java?root=RSyntaxTextArea&revision=194&content-type=text%2Fplain&pathrev=425>PerlParser.java</a>
 * </p>
 * 
 * @author In Yong S. Lee
 *
 */
public class Parsers extends AbstractParser
{

	private DefaultParseResult res;
	private LocalConfiguration local;
	
	/**
	 * The default constructor of C Parser.
	 */
	public Parsers()
	{
		res = new DefaultParseResult(this);
		local = LocalConfiguration.getInstance();
	}

	/**
	 * Parses the code and returns the parse results given by the current compiler used by the current <code>LocalConfiguration</code>.
	 * @param doc The current <code>RSyntaxDocument</code> to use.
	 * @param style The current style to use, which is <code>SyntaxConstants.SYNTAX_STYLE_C</code>, by default in <code>MainWindowView</code>.
	 * @return the <code>ParseResult</code> from the parsed C code, based on the current compiler used.
	 */
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
			File temporary = File.createTempFile("CTempFile", ".c");
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(temporary));
			try
			{
				new DefaultEditorKit().write(out, doc, 0, doc.getLength());
			}
			catch (BadLocationException ble)
			{
				ble.printStackTrace();
				throw new IOException(ble.getMessage());
			}
			out.close();
			
			// COMPILE ME HERE
			Runtime rt = Runtime.getRuntime();
			String cmds[] = {local.getGccPath(), temporary.getAbsolutePath()};
		 	Process proc = rt.exec(cmds);
			//eventC.compile(filePath);
			Element root = doc.getDefaultRootElement();
			OutputCollector stdout = new OutputCollector(proc.getInputStream(), false);
			Thread t_oc = new Thread(stdout);
			t_oc.start();
			COutputCollector stderr = new COutputCollector(
					proc.getErrorStream(), this, res, root);
			Thread t_coc = new Thread(stderr);
			t_coc.start();
			try
			{
				t_coc.join(10000);
				t_oc.join(10000);
				if (t_oc.isAlive())
				{
					t_oc.interrupt();
				}
				else
				{
					proc.waitFor();
				}
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
			long compileTime = System.currentTimeMillis() - compileStart;
			res.setParseTime(compileTime);
			temporary.deleteOnExit();
			//System.out.println(time + "ms");
		}
		catch (IOException ioe)
		{
			res.setError(ioe);
			ioe.printStackTrace();
		}
		
		return res;
	}

}
