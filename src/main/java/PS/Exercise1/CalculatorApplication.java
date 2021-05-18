package PS.Exercise1;

import PS.Exercise1.Exceptions.InvalidTopOfStack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) throws InvalidTopOfStack {
		CalculatorProcessor calculator = new CalculatorProcessor(args);
		calculator.start();
	}

}

/*
(0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ - 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ - 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ - * * * _)
* */