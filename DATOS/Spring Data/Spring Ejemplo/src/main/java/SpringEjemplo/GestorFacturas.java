package SpringEjemplo;

public class GestorFacturas {

    CalculatorService calculatorService;
    String nombre;

    public GestorFacturas(CalculatorService calculatorService, String nombre) {
        System.out.println("Ejecutando constructor GestorFacturas");
        this.calculatorService = calculatorService;
        this.nombre = nombre;
    }
}