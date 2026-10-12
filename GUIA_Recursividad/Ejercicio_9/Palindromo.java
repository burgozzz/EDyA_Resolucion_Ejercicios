/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que determine si una palabra es palíndromo.
 *
 * Este problema puede resolverse recursivamente porque un palíndromo puede verificarse comparando el primer y el último carácter de la palabra.
 * Si son iguales, el problema se reduce a comprobar la subcadena interna sin esos extremos, hasta llegar a un caso base.
 *
 * El caso base es cuando la cadena está vacía o tiene un solo carácter, porque en esos casos la palabra ya se considera palíndromo.
 * También hay otro caso base importante: si el primer y último carácter son distintos, la función debe devolver false inmediatamente.
 *
 * El caso recursivo es:
 * esPalindromo(cadena) = (primero == ultimo) && esPalindromo(cadena.substring(1, longitud - 1))
 *
 * Esto ocurre porque se comparan los extremos de la palabra y, si coinciden, se elimina un carácter de cada lado y se sigue evaluando la subcadena interna.
 * Al volver de la recursión, se determina si toda la palabra es un palíndromo.
 *
 * La función debe devolver true si la palabra es palíndromo y false si no lo es, usando solo recursividad.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estas palabras:
 * - "reconocer" -> true
 * - "hola" -> false
 * - "ana" -> true
 * - "radar" -> true
 *
 * Además, quiero que el programa se ejecute por consola, muestre mensajes claros al usuario y valide que la entrada no esté vacía.
 */

package GUIA_Recursividad.Ejercicio_9;

import java.util.Scanner;

/**
 * Programa que determina si una palabra es palíndromo usando recursividad.
 */
public class Palindromo {

    /**
     * Verifica si una cadena es palíndromo mediante recursividad.
     *
     * Caso base:
     *      Si la cadena está vacía o tiene un solo carácter, entonces es palíndromo.
     *      Si el primer y último carácter son distintos, también se devuelve false.
     *
     * Caso recursivo:
     *      Se comparan los extremos y, si coinciden, se evalúa la subcadena interna:
     *      esPalindromo(cadena) = (primero == ultimo) && esPalindromo(cadena.substring(1, longitud - 1))
     *
     * Pila de llamadas:
     *      Para "reconocer":
     *      esPalindromo("reconocer")
     *      -> esPalindromo("econoce")
     *      -> esPalindromo("cono")
     *      -> esPalindromo("c")
     *      -> caso base
     *      Luego cada llamada devuelve true si todos los pares coinciden.
     *
     * @param cadena palabra a evaluar
     * @return true si es palíndromo, false si no lo es
     */
    public static boolean esPalindromo(String cadena) {
        if (cadena == null || cadena.trim().isEmpty()) {
            throw new IllegalArgumentException("La entrada no puede estar vacía.");
        }

        // Caso base: cadena vacía o con un solo carácter.
        if (cadena.length() <= 1) {
            return true;
        }

        // Caso base: si los extremos no coinciden, no es palíndromo.
        if (cadena.charAt(0) != cadena.charAt(cadena.length() - 1)) {
            return false;
        }

        // Caso recursivo: comparar el primer y último carácter y reducir la cadena.
        return esPalindromo(cadena.substring(1, cadena.length() - 1));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Verificador de palíndromos recursivo ===");

        String palabra;
        while (true) {
            System.out.print("Ingrese una palabra no vacía: ");
            palabra = scanner.nextLine();

            if (palabra == null || palabra.trim().isEmpty()) {
                System.out.println("Error: la entrada no puede estar vacía.");
            } else {
                break;
            }
        }

        try {
            boolean resultado = esPalindromo(palabra);
            System.out.println("¿La palabra es palíndromo? " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nPruebas:");
        System.out.println("\"reconocer\" -> " + esPalindromo("reconocer"));
        System.out.println("\"hola\" -> " + esPalindromo("hola"));
        System.out.println("\"ana\" -> " + esPalindromo("ana"));
        System.out.println("\"radar\" -> " + esPalindromo("radar"));

        scanner.close();
    }
}
