package greeting;

import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.print.PrintException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.DocumentException;

import greeting.printer.Impresion;

@Controller
public class Printer {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@GetMapping("/acceso")
	public String parametros(
			@RequestParam(name="impresor") String impresor, 
			@RequestParam(name="copias") int copias,
			@RequestParam(name="ruta") String ruta,
			@RequestParam(name="documento") String documento,			
			Model model) throws PrinterException, PrintException, URISyntaxException, IOException, DocumentException, InterruptedException {
		model.addAttribute("impresor", impresor);
		model.addAttribute("copias", copias);
		model.addAttribute("ruta", ruta);
		model.addAttribute("documento", documento);
		
		 PrinterServices ps = new PrinterServices(impresor, ruta, documento, copias);
	        
	     ps.printFile();
		
		return "start";
	}
	
	@GetMapping("/impresion")
	public String getImpresion(
		@RequestParam(name="printer") String printer, 
		@RequestParam(name="copies") int copies,
		@RequestParam(name="path") String path,
		@RequestParam(name="file") String file,	
		Model model
	) throws PrinterException, PrintException, URISyntaxException, IOException, DocumentException, InterruptedException{
		Impresion i = new Impresion();
		
		i.setCopias(copies);
		i.setImpresor(printer);
		i.setDocumento(file);
		i.setRuta(path);
		i.setEstado("imprimiendo");
		i.setError("0");
		
		System.out.println("------- PRINTER PARAMETES ----------");
        System.out.println("Printer : "+ printer);
        System.out.println("Path    : "+ path);
        System.out.println("File    : "+ file);
        System.out.println("Copies  : "+ copies);
        System.out.println("------------------------------------");
        
        PrinterServices ps = new PrinterServices(printer, path, file, copies);
        
        String result =  ps.printFile();
		
		//return ResponseEntity.ok(i);
        model.addAttribute("impresor", printer);
		model.addAttribute("copias", copies);
		model.addAttribute("ruta", path);
		model.addAttribute("documento", file);
		model.addAttribute("result", result);
		
		return "start";
	}
}

