package greeting;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.JEditorPane;

import org.apache.coyote.http11.filters.BufferedInputFilter;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ByteBuffer;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ch.qos.logback.core.util.Loader;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class PrinterServices {
    
    private String printerName = null;
    private String printerPath = null;
    private String printerFile = null;
    private int printerCopies = 1;
    
    public PrinterServices(String Name,String Path, String File, int Copies){
        this.printerName = Name;
        this.printerPath = Path;
        this.printerFile = File;
        this.printerCopies = Copies;
    }
    
    public String printFile() throws PrinterException, PrintException, IOException{
        
        /*
        * Set Printer Resources
        */
        // Looking for a printer already installed on the system
    	/*String st = null;
        PDDocument document = null;
        document = PDDocument.load(new File("DSA000179.pdf"));
        document.getClass();
        if (!document.isEncrypted()) {
	        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	        stripper.setSortByPosition(true);
	        PDFTextStripper Tstripper = new PDFTextStripper();
	        this.st = Tstripper.getText(document);
	        System.out.println("Text:" + this.st);
        }   */   
        
        PrintService ps = setPrintSerivce(this.printerName);
        if( ps == null ) {
            return "Impresor podria no estar conectado : "+ this.printerName;
        }

        // Set Printer attributes
        PrintRequestAttributeSet  pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(printerCopies));
        
        // Pritner Job
        DocPrintJob job = ps.createPrintJob();
        
        // Read Printer File
        File input = new File("hola.png");
        FileInputStream fis = new FileInputStream(input);
        
        //Creating a PDF Document

        
        //URL url = new URL("http://localhost:8081/index.php/welcome/index/demo.txt");
        
        DocFlavor myFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc pdfDoc = new SimpleDoc(fis, myFormat, null);
        
        job.print( pdfDoc, null);
        
        
        // Cut papaer
        /*DocPrintJob job2 = ps.createPrintJob();
        byte[] bytes = {27, 100, 3};
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };
        DocFlavor cutFormat = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc cutDoc = new SimpleDoc(bytes, cutFormat, null);
 
        job2.print( cutDoc, null);
         */
        return "Impresion en proceso : ";
    }
    
    public static PrintService setPrintSerivce(String argPrintServiceName) throws PrinterException{
        
        PrintService psr = null;
        
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        
        int i = 0;
        
        for (i=0; i< printServices.length; i++){
            if(printServices[i].getName().equalsIgnoreCase(argPrintServiceName)){
                psr = printServices[i];
                break;
            }
        }
        
        if(i == printServices.length){
        
            //throw new PrinterException("Invalid print service name :" + argPrintServiceName );
        }
        /*
         *         int count = 0;
        for(DocFlavor docFlavor : ps.getSupportedDocFlavors()) {
        	System.out.println("--> "+ docFlavor.toString());
        	if(docFlavor.toString().contains("txt")) {
        		count++;
        	}
        }
        if(count == 0) {
        	System.out.println("TXT NO SUPPORTED BY PRINTER"+ ps.getName());
        	//System.exit(1);
        }else {
        	System.out.println("TXT SUPPORTED BY PRINTER"+ ps.getName());
        }
		
        */
        
        return psr;
    }
}
