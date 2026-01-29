package SpringEjemplo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // ejemplo 1 (recibir un objeto de Spring)
        CalculatorService calculadora1 = (CalculatorService) context.getBean("calculatorService");
        String texto = calculadora1.holaMundo();
        System.out.println(texto);

        // lo podemos obtener de nuevo, pero el objeto que crea Spring es el mismo
        CalculatorService calculadora2 = (CalculatorService) context.getBean("calculatorService");
        texto = calculadora2.holaMundo();
        System.out.println(texto);

        // ejemplo 2 gestor facturas
        // estamos cargando un bean dentro de otro bean
        GestorFacturas gestor = (GestorFacturas) context.getBean("gestorFacturas");
        System.out.println(gestor.nombre);
        System.out.println(gestor.calculatorService.holaMundo());
    }
}
