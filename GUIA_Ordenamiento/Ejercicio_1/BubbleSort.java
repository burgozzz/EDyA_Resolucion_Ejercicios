/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java un programa que ordene un arreglo de números enteros usando Bubble Sort.
 *
 * En este caso, el algoritmo debe ser Bubble Sort. La idea principal es comparar pares de elementos adyacentes y,
 * si están en desorden, intercambiarlos. Con cada pasada, el elemento mayor se desplaza hacia el final del arreglo,
 * porque va “burbujeando” hacia la derecha. El proceso se repite hasta que todo el arreglo quede ordenado.
 *
 * El programa debe recibir un arreglo de números enteros, mostrar el arreglo original, luego mostrar el estado del arreglo
 * después de cada pasada del algoritmo y finalmente mostrar el arreglo ordenado.
 *
 * La solución debe estar escrita en Java y debe incluir comentarios explicando:
 * - la idea general del algoritmo,
 * - cómo funciona la comparación entre elementos adyacentes,
 * - por qué el elemento mayor se mueve al final en cada pasada,
 * - el uso de intercambios,
 * - la lógica de cada iteración y la condición de terminación.
 *
 * El código debe ser claro, estar bien documentado y funcionar por consola. Debe mostrar mensajes entendibles para el usuario
 * y la salida debe permitir verificar el proceso paso a paso.
 * 
 * También quiero que el programa muestre claramente cada paso del ordenamiento para evidenciar cómo avanza la burbuja en cada pasada.
 */

package GUIA_Ordenamiento.Ejercicio_1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Programa que ordena un arreglo de enteros utilizando Bubble Sort.
 * El algoritmo compara elementos adyacentes y los intercambia si están desordenados,
 * haciendo que los valores mayores se desplacen hacia el final del arreglo.
 */
public class BubbleSort {

    /**
     * Ordena el arreglo usando Bubble Sort y muestra el estado después de cada pasada.
     *
     * Idea general del algoritmo:
     * La comparación entre elementos vecinos permite empujar el valor más grande hacia el final
     * en cada iteración. Cuando eso ocurre, el elemento mayor ya no necesita compararse nuevamente
     * en las pasadas siguientes.
     *
     * El intercambio es necesario cuando un elemento a la izquierda es mayor que el de la derecha.
     * Esto provoca que el mayor "burbujee" hasta el final.
     *
     * La condición de terminación es que se realicen n - 1 pasadas, o bien que en una pasada no se produzca
     * ningún intercambio, indicando que el arreglo ya está ordenado.
     */
    public static void bubbleSort(int[] arreglo) {
        int n = arreglo.length;

        for (int pasada = 0; pasada < n - 1; pasada++) {
            boolean huboIntercambio = false;

            // Se recorren los elementos adyacentes y se comparan dos a dos.
            for (int i = 0; i < n - 1 - pasada; i++) {
                if (arreglo[i] > arreglo[i + 1]) {
                    // Intercambio de elementos para llevar el mayor hacia el final.
                    int aux = arreglo[i];
                    arreglo[i] = arreglo[i + 1];
                    arreglo[i + 1] = aux;
                    huboIntercambio = true;
                }
            }

            System.out.println("Pasada " + (pasada + 1) + ": " + Arrays.toString(arreglo));

            // Si no hubo intercambio en esta pasada, el arreglo ya está ordenado.
            if (!huboIntercambio) {
                System.out.println("El arreglo ya está ordenado. Se termina la ejecución.");
                break;
            }
        }
    }

    /**
     * Muestra el arreglo con un formato claro para la consola.
     */
    public static void mostrarArreglo(String titulo, int[] arreglo) {
        System.out.println(titulo + Arrays.toString(arreglo));
    }

    /**
     * Lee una lista de números enteros desde la consola con formato "1 2 3 4".
     */
    public static int[] leerArreglo(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese los números enteros separados por espacios: ");
            String entrada = scanner.nextLine();

            if (entrada == null || entrada.trim().isEmpty()) {
                System.out.println("Error: el arreglo no puede estar vacío.");
                continue;
            }

            String[] partes = entrada.trim().split("\\s+");
            int[] arreglo = new int[partes.length];

            try {
                for (int i = 0; i < partes.length; i++) {
                    arreglo[i] = Integer.parseInt(partes[i]);
                }
                return arreglo;
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese solo números enteros.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Ordenamiento Bubble Sort ===");
        int[] arreglo = leerArreglo(scanner);

        mostrarArreglo("Arreglo original: ", arreglo);
        bubbleSort(arreglo);
        mostrarArreglo("Arreglo ordenado: ", arreglo);
        
        scanner.close();
    }
}
