package greeting.printer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImpresionController {

	@GetMapping("/api")
	public String impresion() {
		return "Hola";
	}
}
