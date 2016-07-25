package view;

import javax.swing.JPanel;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfViewer.PDFViewerBean;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;;

public class PDFViewer extends JPanel {

	/**
	 * Create the panel.
	 * @throws PDFException 
	 */
	public PDFViewer() throws PDFException {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		PDFViewerBean pPDF = new PDFViewerBean();
		pPDF.getToolbar().getjbOpen().setVisible(true);
		pPDF.loadPDF("C://Users//Raymund Pua//Documents//GitHub//PDE-C-Server//resource//sample.pdf");
		scrollPane.add(pPDF);
		
		
		
	}

}
