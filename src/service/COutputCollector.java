package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Element;

import org.fife.rsta.ac.OutputCollector;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;

public class COutputCollector extends OutputCollector{
	
	private Parsers parser;
	private DefaultParseResult result;
	private Element root;

	private static final Pattern ERROR_PATTERN = Pattern.compile("((?!.c:)[0-9]+(?=:\\d))");
	
	public COutputCollector(InputStream in, Parsers cParser,
								DefaultParseResult res, Element root) {
		super(in);
		this.parser = cParser;
		this.result = res;
		this.root = root;
	}
	
	protected void handleLineRead(String line) throws IOException {

		Matcher m = ERROR_PATTERN.matcher(line);

		if (m.find()) {

			line = line.substring(0, line.length()-m.group().length());

			int lineNumber = Integer.parseInt(m.group(1)) - 1;
			Element elem = root.getElement(lineNumber);
			int start = elem.getStartOffset();
			int end = elem.getEndOffset();

			DefaultParserNotice pn = new DefaultParserNotice(
					parser, line, lineNumber, start, end-start);

			result.addNotice(pn);

		}

	}

}
