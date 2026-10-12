/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que invierta un String.
 *
 * Este problema puede resolverse recursivamente porque una cadena puede reducirse eliminando su último carácter.
 * Si se separa el último carácter del resto de la cadena, el problema queda en una subcadena más pequeña hasta llegar a un caso base.
 *
 * El caso base es cuando la cadena está vacía o tiene un solo carácter, porque en esos casos la cadena ya está invertida o no necesita más cambios.
 * En ese caso la función debe devolver la misma cadena.
 *
 * El caso recursivo es:
 * invertirPalabra(cadena) = ultimoCaracter(cadena) + invertirPalabra(cadena.substring(0, longitud - 1))
 *
 * Esto ocurre porque se toma el último carácter de la cadena y se coloca al principio del resultado, mientras la subcadena restante se vuelve más pequeña en cada llamada.
 * Al volver de la recursión, se reconstruye la palabra invertida.
 *
 * La función debe devolver el String invertido usando solo recursividad.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estas palabras:
 * - "hola" -> "aloh"
 * - "reconocer" -> "reconocer"
 * - "java" -> "avaj"
 * - "arbol" -> "lobra"
 *
 * Además, quiero que el programa se ejecute por consola, muestre mensajes claros al usuario y valide que la entrada no esté vacía.
 */

package GUIA_Recursividad.Ejercicio_8;

import java.util.Scanner;

/**
 * Programa que invierte una palabra usando recursividad.
 */
public class Invertir_Palabra {

    /**
     * Invierte una cadena de forma recursiva.
     *
     * Caso base:
     *      Si la cadena está vacía o tiene un solo carácter, se devuelve la misma cadena.
     *
     * Caso recursivo:
     *      Se toma el último carácter y se concatena con la inversión del prefijo:
     *      invertirPalabra(cadena) = ultimoCaracter(cadena) + invertirPalabra(cadena.substring(0, longitud - 1))
     *
     * Pila de llamadas:
     *      Para "hola":
     *      invertirPalabra("hola")
     *      -> invertirPalabra("hol")
     *      -> invertirPalabra("ho")
     *      -> invertirPalabra("h")
     *      -> caso base
     *      Luego cada llamada vuelve y arma la palabra invertida.
     *
     * @param cadena palabra a invertir
     * @return cadena invertida
     */
    public static String invertirPalabra(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            throw new IllegalArgumentException("La cadena no puede estar vacía.");
        }

        // Caso base: si la cadena tiene un solo carácter, no necesita más cambios.
        if (cadena.length() <= 1) {
            return cadena;
        }

        // Caso recursivo: toma el último carácter y concatena la inversión del resto.
        return cadena.charAt(cadena.length() - 1) + invertirPalabra(cadena.substring(0, cadena.length() - 1));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Invertir palabra recursiva ===");

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
            String resultado = invertirPalabra(palabra);
            System.out.println("La palabra invertida es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nPruebas:");
        System.out.println("\"hola\" -> " + invertirPalabra("hola"));
        System.out.println("\"reconocer\" -> " + invertirPalabra("reconocer"));
        System.out.println("\"java\" -> " + invertirPalabra("java"));
        System.out.println("\"arbol\" -> " + invertirPalabra("arbol"));

        scanner.close();
    }
}
