package wf.garnier.devoxx;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/")
	public String publicPage() {
		return "Hello World!";
	}

	@GetMapping("private")
	public String privatePage() {
		return "Mmmmh, this should be private 🤔";
	}

}
