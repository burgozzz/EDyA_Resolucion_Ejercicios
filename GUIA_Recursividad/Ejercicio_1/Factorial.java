/**
 * PROMPT AGENTE DESARROLLADOR
 * Desarrolla en Java un programa por consola que calcule el factorial de un número entero usando recursividad.
 * La solución debe tener una sección de interacción por consola separada de la lógica del algoritmo.
 * El programa debe solicitar al usuario un número entero mayor o igual a 0.
 * Si el valor ingresado es negativo, debe informar que el dato es inválido y volver a solicitarlo.
 * La función recursiva debe calcular el factorial del número ingresado. El caso base en este caso se da cuando n=0.
 * Cuando n=0 se retorna 1, y cuando n=1 se el resultado es 1. El problema se reduce hacia la multiplicacion reiterada
 * más sencilla, la cual es multiplicar por 1. Se deberian utilizar valores de pruebas pequeños ya que si se utilizan muy
 * grandes se podrian ocasionar un overflow.
 * El caso base es: si n == 0 o n == 1, se devuelve 1 porque el factorial de 0 y 1 es 1.
 * El caso recursivo es: factorial(n) = n * factorial(n - 1), donde cada llamada reduce el problema a una instancia
 * más pequeña hasta llegar al caso base.
 * La pila de llamadas se va acumulando con cada invocación: factorial(5) llama a factorial(4), que llama a factorial(3),
 * y así sucesivamente hasta factorial(1), donde se resuelven las llamadas desde el fondo hacia arriba y se van
 * combinando los resultados multiplicando por los valores que faltan.
 */
package GUIA_Recursividad.Ejercicio_1;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Calcula el factorial de un número entero no negativo mediante recursividad.
 * El enfoque recursivo reduce el problema en instancias más pequeñas hasta llegar
 * al caso base, donde el resultado es 1.
 */
public class Factorial {

    /**
     * Calcula el factorial de un número entero utilizando recursión.
     *
     * @param n número a factorizar; debe ser mayor o igual a 0
     * @return resultado del factorial de n
     * @throws IllegalArgumentException si n es negativo o demasiado grande para
     *                                  representarse sin overflow en long
     */
    public static long calcularFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El número no puede ser negativo.");
        }
        if (n > 20) {
            throw new IllegalArgumentException("El valor ingresado es demasiado grande para calcular su factorial sin overflow.");
        }
        if (n == 0 || n == 1) {
            return 1;
        }

        return n * calcularFactorial(n - 1);
    }

    /**
     * Punto de entrada del programa. Solicita el valor, calcula el factorial y
     * muestra el resultado en consola.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numero = leerEnteroNoNegativo(scanner);

        try {
            long resultado = calcularFactorial(numero);
            System.out.println("El factorial de " + numero + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Solicita un entero mayor o igual a cero y repite la validación mientras la
     * entrada no sea válida.
     *
     * @param scanner objeto para leer desde consola
     * @return número entero no negativo
     */
    private static int leerEnteroNoNegativo(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese un número entero mayor o igual a 0: ");
            try {
                int valor = scanner.nextInt();
                if (valor < 0) {
                    System.out.println("Dato inválido: debe ingresar un número mayor o igual a 0.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Dato inválido: debe ingresar un número entero.");
                scanner.next();
            }
        }
    }
}
/* PROMPT CAMBIOS
en caso de que el valor sea muy grande dar un mensaje de error
Realice el cambio ya que el programa no reaccionaba al utilizar un valor grande como el 100
*/ 