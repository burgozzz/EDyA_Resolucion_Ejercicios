/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que imprima un conteo desde n hasta 0.
 *
 * Este problema puede resolverse recursivamente porque la función puede ir reduciendo el valor de n en cada llamada, acercándose al caso base.
 * La idea es que cada llamada resuelva un caso más pequeño del mismo problema.
 *
 * El caso base es cuando n == 0, porque ahí la secuencia termina. En ese caso la función debe imprimir 0 y finalizar la recursión.
 *
 * El caso recursivo es:
 * conteo(n) = imprimir n; conteo(n - 1)
 *
 * Esto ocurre porque en cada llamada se imprime el valor actual y luego se procesa el siguiente valor más pequeño hasta llegar a 0.
 *
 * La función no necesariamente debe devolver un valor; puede simplemente imprimir la secuencia por consola. Lo importante es que el proceso se realice de manera recursiva y que el caso base se alcance en cada ejecución.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estos casos:
 * - n = 5
 * - n = 0
 * - n = 3
 *
 * Además, quiero que el programa se ejecute por consola, muestre la secuencia en orden descendente y explique claramente qué ocurre si se usa n + 1 en lugar de n - 1.
 */

package GUIA_Recursividad.Ejercicio_5;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que imprime un conteo regresivo desde n hasta 0 usando recursividad.
 *
 * La función no devuelve un valor; solo imprime por consola.
 * Esto es válido porque la recursividad puede usarse para producir un efecto lateral,
 * como imprimir una secuencia.
 */
public class Conteo_Regresivo {

    /**
     * Imprime los números desde n hasta 0 de manera recursiva.
     *
     * Caso base:
     *      Si n == 0, se imprime 0 y la recursión termina.
     *
     * Caso recursivo:
     *      Se imprime el valor actual y luego se llama a conteo(n - 1).
     *
     * Pila de llamadas:
     *      Para conteo(3):
     *      conteo(3)
     *      -> conteo(2)
     *      -> conteo(1)
     *      -> conteo(0)
     *      Luego cada llamada vuelve y finaliza la ejecución.
     *
     * @param n valor inicial del conteo
     */
    public static void conteo(int n) {
        if (n < 0) {
            System.out.println("Error: el valor ingresado no puede ser negativo.");
            return;
        }

        // Caso base: cuando n llega a 0, se imprime 0 y se termina la recursión.
        if (n == 0) {
            System.out.print(0);
            return;
        }

        // Caso recursivo: se imprime el valor actual y se reduce el problema con n - 1.
        System.out.print(n + " ");
        conteo(n - 1);
    }

    /**
     * Muestra una explicación sobre qué ocurre si se usa n + 1 en lugar de n - 1.
     */
    public static void explicarProblemaConNMasUno() {
        System.out.println();
        System.out.println("Si se usa n + 1 en lugar de n - 1, la recursión nunca llega al caso base");
        System.out.println("porque el valor de n aumenta en cada llamada y la secuencia se vuelve infinita.");
    }

    /**
     * Programa principal por consola.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Conteo regresivo recursivo ===");
        int n = leerEnteroNoNegativo(scanner, "Ingrese un número entero no negativo: ");

        System.out.print("Conteo desde " + n + " hasta 0: ");
        conteo(n);
        System.out.println();

        System.out.println("\nPruebas:");
        System.out.print("n = 5 -> ");
        conteo(5);
        System.out.println();

        System.out.print("n = 0 -> ");
        conteo(0);
        System.out.println();

        System.out.print("n = 3 -> ");
        conteo(3);
        System.out.println();

        explicarProblemaConNMasUno();
        scanner.close();
    }

    /**
     * Lee un entero no negativo desde consola.
     *
     * @param scanner entrada por consola
     * @param mensaje texto a mostrar al usuario
     * @return valor válido ingresado por el usuario
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
