/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que cuente la cantidad de dígitos de un número entero positivo.
 *
 * Este problema puede resolverse recursivamente porque se puede reducir el número usando división entera.
 * Si el número tiene más de un dígito, se puede separar el último dígito y continuar con el resto del número.
 * Cada llamada resuelve un caso más pequeño del mismo problema.
 *
 * El caso base es cuando el número es menor que 10, porque un número menor que 10 tiene exactamente un dígito.
 * En ese caso la función debe devolver 1.
 *
 * El caso recursivo es:
 * contarDigitos(n) = 1 + contarDigitos(n / 10)
 *
 * Esto ocurre porque se cuenta el último dígito actual y luego se reduce el número dividiendo entre 10 hasta llegar a un valor de un solo dígito.
 *
 * La función debe devolver la cantidad total de dígitos del número usando solo recursividad.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estos casos:
 * - 123 -> 3
 * - 7 -> 1
 * - 4050 -> 4
 * - 0 -> 1
 *
 * Además, quiero que el programa se ejecute por consola, muestre mensajes claros al usuario y valide que el número sea positivo antes de contar los dígitos.
 */

package GUIA_Recursividad.Ejercicio_6;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que cuenta la cantidad de dígitos de un número entero positivo usando recursividad.
 *
 * La idea es dividir el número entre 10 en cada llamada y sumar 1 en cada paso.
 * Cuando el número queda en un solo dígito, se retorna 1.
 */
public class Contar_Digitos {

    /**
     * Cuenta la cantidad de dígitos de un número entero positivo usando recursividad.
     *
     * Caso base:
     *      Si n < 10, el número tiene un solo dígito y la función devuelve 1.
     *
     * Caso recursivo:
     *      Se divide el número por 10 y se suma 1 al resultado de la llamada recursiva:
     *      contarDigitos(n) = 1 + contarDigitos(n / 10)
     *
     * Pila de llamadas:
     *      Para contarDigitos(1234):
     *      contarDigitos(1234)
     *      -> contarDigitos(123)
     *      -> contarDigitos(12)
     *      -> contarDigitos(1)
     *      -> caso base
     *      Luego cada llamada vuelve y suma 1.
     *
     * @param n número entero positivo
     * @return cantidad de dígitos de n
     */
    public static int contarDigitos(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El número debe ser positivo o cero.");
        }

        // Caso base: si el número tiene un solo dígito, devuelve 1.
        if (n < 10) {
            return 1;
        }

        // Caso recursivo: se reduce el problema dividiendo por 10 y sumando 1.
        return 1 + contarDigitos(n / 10);
    }

    /**
     * Programa principal por consola.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Contador de dígitos recursivo ===");
        int numero = leerEnteroNoNegativo(scanner, "Ingrese un número entero positivo: ");

        try {
            int resultado = contarDigitos(numero);
            System.out.println("El número " + numero + " tiene " + resultado + " dígito(s).");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nPruebas:");
        System.out.println("123 -> " + contarDigitos(123));
        System.out.println("7 -> " + contarDigitos(7));
        System.out.println("4050 -> " + contarDigitos(4050));
        System.out.println("0 -> " + contarDigitos(0));

        scanner.close();
    }

    /**
     * Lee un número entero no negativo desde consola.
     *
     * @param scanner entrada por consola
     * @param mensaje texto a mostrar al usuario
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
