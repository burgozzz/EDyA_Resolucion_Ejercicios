/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java un programa que compare dos algoritmos de ordenamiento: Bubble Sort y Selection Sort, aplicados sobre el mismo arreglo de números enteros.
 *
 * Bubble Sort funciona comparando pares de elementos adyacentes e intercambiándolos si están en desorden. En cada pasada, el número mayor se desplaza hacia el final del arreglo.
 * Selection Sort, en cambio, recorre el arreglo buscando el menor elemento y lo coloca en la posición correcta, sin comparar vecinos directamente.
 *
 * La implementación debe incluir dos métodos en Java:
 * - bubbleSort(int[] array)
 * - selectionSort(int[] array)
 *
 * Ambos deben ordenar el mismo arreglo. El programa debe contar cuántas comparaciones e intercambios realiza cada algoritmo, y debe mostrar los resultados de manera clara por consola.
 *
 * La lógica de la solución debe estar bien documentada con comentarios que expliquen:
 * - la idea general de Bubble Sort,
 * - la idea general de Selection Sort,
 * - cómo se comparan los elementos en cada algoritmo,
 * - por qué Bubble Sort compara vecinos y por qué Selection Sort busca el menor,
 * - cómo se cuentan las comparaciones e intercambios,
 * - la diferencia entre ambos enfoques y por qué una estrategia puede requerir más o menos trabajo.
 *
 * El programa debe recibir un arreglo de números enteros, ordenarlo con ambos algoritmos, mostrar el arreglo original, mostrar el resultado final y mostrar la cantidad de comparaciones e intercambios de cada método.
 *
 * También quiero que el código se ejecute por consola y que muestre mensajes claros para el usuario, para que se pueda verificar fácilmente qué hace cada algoritmo.
 *
 * Quiero validar el resultado con casos como:
 * - arreglo = {7, 3, 9, 1, 5}
 * - arreglo = {5, 4, 3, 2, 1}
 * - arreglo = {1, 2, 3, 4}
 * - arreglo = {9, 1, 8, 2, 7}
 *
 * Los resultados esperados deben ser:
 * - en todos los casos, ambos algoritmos deben dejar el arreglo ordenado de menor a mayor,
 * - la cantidad de comparaciones e intercambios debe mostrarse por separado para Bubble Sort y Selection Sort,
 * - la diferencia entre ambos algoritmos debe quedar reflejada en la salida del programa.
 *
 * La solución debe cumplir con la consigna general: explicar el algoritmo, describir el razonamiento, definir la entrada y salida, incluir comentarios en el código y validar el resultado con casos reales y claros.
 */

package GUIA_Ordenamiento.Ejercicio_2;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Programa que compara dos algoritmos de ordenamiento sobre el mismo arreglo.
 * Se diferencia la estrategia de Bubble Sort y Selection Sort para analizar
 * sus comparaciones e intercambios.
 */
public class Burbuja_Y_Seleccion {

    /**
     * Guarda la información de rendimiento de un algoritmo de ordenamiento.
     */
    private static class ResultadoOrdenamiento {
        private final int[] arregloOrdenado;
        private final int comparaciones;
        private final int intercambios;

        public ResultadoOrdenamiento(int[] arregloOrdenado, int comparaciones, int intercambios) {
            this.arregloOrdenado = arregloOrdenado;
            this.comparaciones = comparaciones;
            this.intercambios = intercambios;
        }

        public int[] getArregloOrdenado() {
            return arregloOrdenado;
        }

        public int getComparaciones() {
            return comparaciones;
        }

        public int getIntercambios() {
            return intercambios;
        }
    }

    /**
     * Ordena un arreglo con Bubble Sort.
     *
     * Idea general:
     * Se recorre el arreglo varias veces. En cada pasada, se comparan elementos
     * adyacentes y, si están desordenados, se intercambian. De esta manera,
     * el mayor valor va moviéndose hacia el final.
     */
    public static ResultadoOrdenamiento bubbleSort(int[] arreglo) {
        int[] copia = Arrays.copyOf(arreglo, arreglo.length);
        int comparaciones = 0;
        int intercambios = 0;

        // Recorremos todas las pasadas del arreglo.
        for (int i = 0; i < copia.length - 1; i++) {
            // En cada pasada, se reduce el rango de comparación.
            for (int j = 0; j < copia.length - 1 - i; j++) {
                comparaciones++; // Se compara el par (j, j + 1).

                // Si el elemento actual es mayor que el siguiente, se intercambian.
                if (copia[j] > copia[j + 1]) {
                    int auxiliar = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = auxiliar;
                    intercambios++;
                }
            }
        }

        return new ResultadoOrdenamiento(copia, comparaciones, intercambios);
    }

    /**
     * Ordena un arreglo con Selection Sort.
     *
     * Idea general:
     * Se busca el menor elemento del arreglo y se lo coloca en la posición actual.
     * A diferencia de Bubble Sort, no se comparan vecinos; se selecciona el mínimo.
     */
    public static ResultadoOrdenamiento selectionSort(int[] arreglo) {
        int[] copia = Arrays.copyOf(arreglo, arreglo.length);
        int comparaciones = 0;
        int intercambios = 0;

        // Se toma una posición como inicio del orden parcial.
        for (int i = 0; i < copia.length - 1; i++) {
            int indiceMinimo = i;

            // Se busca el menor valor desde la posición actual hasta el final.
            for (int j = i + 1; j < copia.length; j++) {
                comparaciones++; // Se compara el valor actual con el mínimo encontrado.

                if (copia[j] < copia[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }

            // Si se encontró un menor valor, se intercambia con la posición actual.
            if (indiceMinimo != i) {
                int auxiliar = copia[i];
                copia[i] = copia[indiceMinimo];
                copia[indiceMinimo] = auxiliar;
                intercambios++;
            }
        }

        return new ResultadoOrdenamiento(copia, comparaciones, intercambios);
    }

    /**
     * Lee un arreglo desde la entrada estándar.
     */
    private static int[] leerArreglo(Scanner scanner) {
        System.out.print("Ingrese números enteros separados por espacios: ");
        String entrada = scanner.nextLine();

        if (entrada == null || entrada.trim().isEmpty()) {
            return new int[0];
        }

        String[] partes = entrada.trim().split("\\s+");
        int[] arreglo = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {
            arreglo[i] = Integer.parseInt(partes[i]);
        }

        return arreglo;
    }

    /**
     * Muestra la comparación visual entre ambos algoritmos.
     */
    private static void compararAlgoritmos(int[] arreglo) {
        System.out.println("\nArreglo original: " + Arrays.toString(arreglo));

        ResultadoOrdenamiento resultadoBubble = bubbleSort(arreglo);
        ResultadoOrdenamiento resultadoSelection = selectionSort(arreglo);

        System.out.println("\n=== Resultado Bubble Sort ===");
        System.out.println("Arreglo ordenado: " + Arrays.toString(resultadoBubble.getArregloOrdenado()));
        System.out.println("Comparaciones: " + resultadoBubble.getComparaciones());
        System.out.println("Intercambios: " + resultadoBubble.getIntercambios());

        System.out.println("\n=== Resultado Selection Sort ===");
        System.out.println("Arreglo ordenado: " + Arrays.toString(resultadoSelection.getArregloOrdenado()));
        System.out.println("Comparaciones: " + resultadoSelection.getComparaciones());
        System.out.println("Intercambios: " + resultadoSelection.getIntercambios());
    }

    /**
     * Ejecuta pruebas con casos típicos para validar que ambos algoritmos ordenan correctamente.
     */
    private static void probarCasos() {
        int[][] casos = {
                {7, 3, 9, 1, 5},
                {5, 4, 3, 2, 1},
                {1, 2, 3, 4},
                {9, 1, 8, 2, 7}
        };

        for (int i = 0; i < casos.length; i++) {
            System.out.println("\nCaso de prueba " + (i + 1) + ": " + Arrays.toString(casos[i]));

            ResultadoOrdenamiento bubble = bubbleSort(casos[i]);
            ResultadoOrdenamiento selection = selectionSort(casos[i]);

            System.out.println("Bubble Sort: " + Arrays.toString(bubble.getArregloOrdenado())
                    + " | comparaciones=" + bubble.getComparaciones()
                    + " | intercambios=" + bubble.getIntercambios());

            System.out.println("Selection Sort: " + Arrays.toString(selection.getArregloOrdenado())
                    + " | comparaciones=" + selection.getComparaciones()
                    + " | intercambios=" + selection.getIntercambios());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Comparación de Bubble Sort y Selection Sort ===");
        System.out.println("1) Ingresar arreglo manualmente");
        System.out.println("2) Ejecutar pruebas automáticas");
        System.out.print("Seleccione una opción: ");

        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            int[] arreglo = leerArreglo(scanner);
            compararAlgoritmos(arreglo);
        } else if (opcion.equals("2")) {
            probarCasos();
        } else {
            System.out.println("Opción inválida. Se ejecutarán las pruebas automáticas.");
            probarCasos();
        }

        scanner.close();
    }
}

