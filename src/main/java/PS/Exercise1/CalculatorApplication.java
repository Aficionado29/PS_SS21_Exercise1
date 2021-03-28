package PS.Exercise1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runCalculatorProcessing() {
		CalculatorProcessor calculator = new CalculatorProcessor();
		calculator.run();
	}

}
