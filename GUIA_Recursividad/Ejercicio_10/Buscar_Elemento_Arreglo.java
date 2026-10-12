/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que busque un número dentro de un arreglo, sin usar ciclos.
 *
 * Este problema puede resolverse recursivamente porque un arreglo se puede recorrer desde una posición determinada hacia el final.
 * Si se parte desde la posición actual, el problema se reduce a buscar en una porción más pequeña del arreglo hasta llegar a un caso base.
 *
 * El caso base es cuando el valor buscado se encuentra en la posición actual, porque entonces la función debe devolver la posición donde está.
 * También hay otro caso base importante: cuando se llega al final del arreglo sin encontrar el valor, la función debe devolver -1.
 *
 * El caso recursivo es:
 * buscarElemento(arreglo, indice, valor) = si arreglo[indice] == valor entonces indice; si no, buscarElemento(arreglo, indice + 1, valor)
 *
 * Esto ocurre porque se compara el elemento actual con el valor buscado; si no coincide, se avanza al siguiente índice.
 * El problema se vuelve más pequeño cada vez porque la búsqueda empieza en una posición posterior hasta llegar al final del arreglo.
 *
 * La función debe devolver la posición del elemento dentro del arreglo o -1 si no existe, usando solo recursividad.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estos arreglos:
 * - arreglo = {3, 8, 12, 5, 9}, valor = 12 -> 2
 * - arreglo = {7, 1, 4, 9}, valor = 9 -> 3
 * - arreglo = {10, 20, 30}, valor = 50 -> -1
 * - arreglo = {5, 7, 8, 9}, valor = 5 -> 0
 *
 * Además, quiero que el programa se ejecute por consola, muestre mensajes claros al usuario y valide que el arreglo no esté vacío y que la posición de inicio sea válida.
 */

package GUIA_Recursividad.Ejercicio_10;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Programa que busca un elemento dentro de un arreglo usando recursividad.
 */
public class Buscar_Elemento_Arreglo {

    /**
     * Busca un valor dentro de un arreglo recursivamente.
     *
     * Caso base:
     *      Si el elemento actual coincide con el valor buscado, se devuelve la posición actual.
     *      Si el índice llega al final del arreglo, se devuelve -1.
     *
     * Caso recursivo:
     *      Si el elemento actual no coincide, se llama a la misma función con el siguiente índice:
     *      buscarElemento(arreglo, indice, valor) = si arreglo[indice] == valor entonces indice; si no, buscarElemento(arreglo, indice + 1, valor)
     *
     * Pila de llamadas:
     *      Para arreglo [3,8,12,5,9] y valor 12:
     *      buscarElemento(0)
     *      -> buscarElemento(1)
     *      -> buscarElemento(2)
     *      -> caso base: encuentra 12 en la posición 2
     *      Luego se devuelve la posición final.
     *
     * @param arreglo arreglo con elementos enteros
     * @param indice posición actual de la búsqueda
     * @param valor valor buscado
     * @return posición del valor o -1 si no existe
     */
    public static int buscarElemento(int[] arreglo, int indice, int valor) {
        if (arreglo == null || arreglo.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacío.");
        }

        if (indice < 0 || indice >= arreglo.length) {
            return -1;
        }

        // Caso base: si se encontró el valor en la posición actual.
        if (arreglo[indice] == valor) {
            return indice;
        }

        // Caso recursivo: avanzar al siguiente índice.
        return buscarElemento(arreglo, indice + 1, valor);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Búsqueda recursiva en arreglo ===");

        int[] arreglo;
        while (true) {
            System.out.print("Ingrese los elementos del arreglo separados por espacios: ");
            String entrada = scanner.nextLine();

            if (entrada == null || entrada.trim().isEmpty()) {
                System.out.println("Error: el arreglo no puede estar vacío.");
                continue;
            }

            String[] partes = entrada.trim().split("\\s+");
            arreglo = new int[partes.length];

            try {
                for (int i = 0; i < partes.length; i++) {
                    arreglo[i] = Integer.parseInt(partes[i]);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese solo números enteros.");
            }
        }

        System.out.print("Ingrese el valor a buscar: ");
        int valor = scanner.nextInt();

        System.out.print("Ingrese la posición inicial de búsqueda (0 para comenzar desde el principio): ");
        int indiceInicial = scanner.nextInt();

        if (indiceInicial < 0 || indiceInicial >= arreglo.length) {
            System.out.println("Error: la posición inicial debe estar dentro del arreglo.");
            scanner.close();
            return;
        }

        try {
            int resultado = buscarElemento(arreglo, indiceInicial, valor);
            if (resultado == -1) {
                System.out.println("El valor " + valor + " no se encontró en el arreglo.");
            } else {
                System.out.println("El valor " + valor + " se encontró en la posición: " + resultado);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nPruebas:");
        int[] a1 = {3, 8, 12, 5, 9};
        int[] a2 = {7, 1, 4, 9};
        int[] a3 = {10, 20, 30};
        int[] a4 = {5, 7, 8, 9};

        System.out.println("arreglo = " + Arrays.toString(a1) + ", valor = 12 -> " + buscarElemento(a1, 0, 12));
        System.out.println("arreglo = " + Arrays.toString(a2) + ", valor = 9 -> " + buscarElemento(a2, 0, 9));
        System.out.println("arreglo = " + Arrays.toString(a3) + ", valor = 50 -> " + buscarElemento(a3, 0, 50));
        System.out.println("arreglo = " + Arrays.toString(a4) + ", valor = 5 -> " + buscarElemento(a4, 0, 5));

        scanner.close();
    }
}
