/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que calcule una potencia sin usar Math.pow.
 *
 * Este problema puede resolverse recursivamente porque una potencia puede verse como multiplicaciones sucesivas.
 * Por ejemplo, 2^4 = 2 * 2 * 2 * 2. En cada llamada recursiva se reduce el exponente para hacer el problema más pequeño.
 *
 * El caso base es cuando el exponente es 0, porque cualquier número elevado a la 0 da 1. En ese caso la función debe devolver 1 inmediatamente.
 *
 * El caso recursivo es elevar la base a una potencia menor, es decir:
 * potencia(base, exponente) = base * potencia(base, exponente - 1)
 *
 * La función debe devolver el resultado de la potencia sin usar Math.pow ni ningún operador de potenciación.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estos casos:
 * - 2 ^ 3 = 8
 * - 5 ^ 0 = 1
 * - 3 ^ 1 = 3
 * - 4 ^ 2 = 16
 * - 7 ^ 3 = 343
 *
 * Además, quiero que el programa se ejecute por consola, que muestre mensajes claros al usuario y que el método recursivo esté bien documentado.
 */

package GUIA_Recursividad.Ejercicio_4;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que calcula una potencia usando recursividad.
 *
 * La idea es reducir el exponente en cada llamada:
 * potencia(base, exp) = base * potencia(base, exp - 1)
 *
 * Si el exponente llega a 0, el resultado es 1.
 */
public class Potencia {

    /**
     * Calcula la potencia de una base elevada a un exponente usando recursividad.
     *
     * Caso base:
     *      Si exponente == 0, devuelve 1 porque cualquier valor elevado a 0 es 1.
     *
     * Caso recursivo:
     *      Se reduce el exponente en 1 en cada llamada:
     *      potencia(base, exp) = base * potencia(base, exp - 1)
     *
     * Pila de llamadas:
     *      Para potencia(2, 3), la secuencia es:
     *      potencia(2, 3)
     *      -> potencia(2, 2)
     *      -> potencia(2, 1)
     *      -> potencia(2, 0)
     *      Luego cada llamada retorna y se resuelven las multiplicaciones acumuladas.
     *
     * @param base número base
     * @param exponente exponente entero no negativo
     * @return base elevado a la exponente
     */
    public static int calcularPotencia(int base, int exponente) {
        if (exponente < 0) {
            throw new IllegalArgumentException("El exponente no puede ser negativo.");
        }

        // Caso base: todo número elevado a 0 es 1.
        if (exponente == 0) {
            return 1;
        }

        // Caso recursivo: se reduce el exponente y se multiplica la base por la potencia anterior.
        return base * calcularPotencia(base, exponente - 1);
    }

    /**
     * Programa principal por consola.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Potencia mediante recursión ===");
        int base = leerEnteroNoNegativo(scanner, "Ingrese la base: ");
        int exponente = leerEnteroNoNegativo(scanner, "Ingrese el exponente: ");

        try {
            int resultado = calcularPotencia(base, exponente);
            System.out.println("Resultado: " + base + " ^ " + exponente + " = " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Casos de prueba del ejercicio:
        System.out.println("Prueba 1: 2 ^ 3 = " + calcularPotencia(2, 3));
        System.out.println("Prueba 2: 5 ^ 0 = " + calcularPotencia(5, 0));
        System.out.println("Prueba 3: 3 ^ 1 = " + calcularPotencia(3, 1));
        System.out.println("Prueba 4: 4 ^ 2 = " + calcularPotencia(4, 2));
        System.out.println("Prueba 5: 7 ^ 3 = " + calcularPotencia(7, 3));

        scanner.close();
    }

    /**
     * Lee un entero no negativo desde consola.
     *
     * @param scanner entrada por consola
     * @param mensaje texto a mostrar al usuario
     * @return valor entero no negativo
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
