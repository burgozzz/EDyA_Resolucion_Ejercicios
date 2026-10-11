/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que resuelva la multiplicación de dos números enteros sin usar el operador *.
 *
 * Este problema puede resolverse recursivamente porque una multiplicación puede representarse como sumas repetidas.
 * Por ejemplo, 5 * 3 puede verse como 5 + 5 + 5. En cada llamada recursiva se reduce uno de los factores para hacer el problema más pequeño.
 *
 * El caso base es cuando uno de los factores vale 0, porque cualquier número multiplicado por 0 da 0. En ese caso la función debe devolver 0 inmediatamente.
 *
 * El caso recursivo es multiplicar el primer número por el segundo número menos 1, es decir:
 * multiplicar(a, b) = a + multiplicar(a, b - 1)
 * o bien:
 * multiplicar(a, b) = b + multiplicar(a - 1, b)
 *
 * La función debe devolver el producto de ambos factores usando solo recursividad y sin utilizar el operador *.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estos casos:
 * - 3 * 4 = 12
 * - 7 * 0 = 0
 * - 0 * 8 = 0
 * - 5 * 1 = 5
 * - 2 * 6 = 12
 *
 * Además, quiero que el programa se ejecute por consola, que muestre mensajes claros al usuario y que el método recursivo esté bien documentado.
 */

package GUIA_Recursividad.Ejercicio_3;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que calcula la multiplicación de dos enteros usando recursividad y sumas.
 *
 * La idea es convertir la multiplicación en una secuencia de sumas repetidas.
 * Por ejemplo: 5 * 3 = 5 + 5 + 5.
 *
 * En términos recursivos:
 * multiplicar(a, b) = a + multiplicar(a, b - 1)
 * Cuando b == 0, el resultado es 0.
 */
public class Multiplicacion_Mediante_Sumas {

    /**
     * Calcula el producto de dos enteros positivos usando recursividad.
     *
     * Caso base:
     *      Si b == 0 o a == 0, devuelve 0 porque cualquier número multiplicado por 0 es 0.
     *
     * Caso recursivo:
     *      Se reduce uno de los factores en cada llamada:
     *      multiplicar(a, b) = a + multiplicar(a, b - 1)
     *
     * Pila de llamadas:
     *      Para multiplicar(3, 4), se ejecuta:
     *      multiplicar(3, 4)
     *      -> multiplicar(3, 3)
     *      -> multiplicar(3, 2)
     *      -> multiplicar(3, 1)
     *      -> multiplicar(3, 0)
     *      Luego cada llamada retorna y se resuelven las sumas acumuladas.
     *
     * @param a primer factor
     * @param b segundo factor
     * @return producto de a y b
     */
    public static int multiplicar(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }

        // Caso recursivo: se reduce b en 1 cada vez y se suma a tantas veces como indique b.
        return a + multiplicar(a, b - 1);
    }

    /**
     * Lee dos enteros desde consola, valida que sean no negativos y muestra el resultado.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Multiplicación mediante sumas ===");
        int a = leerEnteroNoNegativo(scanner, "Ingrese el primer número: ");
        int b = leerEnteroNoNegativo(scanner, "Ingrese el segundo número: ");

        int resultado = multiplicar(a, b);
        System.out.println("Resultado: " + a + " * " + b + " = " + resultado);

        // Casos de prueba solicitados por el ejercicio:
        System.out.println("Prueba 1: 3 * 4 = " + multiplicar(3, 4));
        System.out.println("Prueba 2: 7 * 0 = " + multiplicar(7, 0));
        System.out.println("Prueba 3: 0 * 8 = " + multiplicar(0, 8));
        System.out.println("Prueba 4: 5 * 1 = " + multiplicar(5, 1));
        System.out.println("Prueba 5: 2 * 6 = " + multiplicar(2, 6));

        scanner.close();
    }

    /**
     * Solicita un número entero no negativo al usuario.
     *
     * @param scanner entrada por consola
     * @param mensaje texto mostrado antes de leer el valor
     * @return número válido ingresado por el usuario
     */
    private static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                if (valor < 0) {
                    System.out.println("Error: el valor debe ser mayor o igual a 0.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero.");
                scanner.next();
            }
        }
    }
}
