/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java un programa que ordene un arreglo de números enteros utilizando 
 * el algoritmo de Insertion Sort. El archivo debe comenzar con un comentario en la parte 
 * superior donde se indique exactamente el prompt utilizado. La solución debe estar escrita 
 * en Java y ejecutarse por consola. Debe implementar un método llamado insertionSort(int[] 
 * arreglo) que ordene el arreglo de menor a mayor. Debe explicar claramente la lógica del 
 * algoritmo: cómo se toma cada elemento, se compara con los anteriores y se inserta en la 
 * posición correcta. Debe describir la idea general del algoritmo, la entrada, la salida, 
 * el caso base o condición de control y el razonamiento del proceso. Debe incluir comentarios 
 * dentro del código para documentar la lógica principal, especialmente el recorrido del arreglo,
 * la comparación con elementos anteriores, la inserción del valor y la cantidad de desplazamientos
 * que realiza el algoritmo. Debe mostrar por consola el arreglo original, el arreglo ordenado,
 * la cantidad de desplazamientos y mensajes claros para que el usuario entienda qué está haciendo el programa.
 * Debe explicar por qué Insertion Sort funciona mejor con arreglos casi ordenados, porque en ese caso
 * cada elemento suele quedar cerca de su posición final y se requieren menos desplazamientos.
 * Debe validar el resultado con varios casos reales, por ejemplo: 
 * {7, 3, 9, 1, 5}, 
 * {5, 4, 3, 2, 1}, 
 * {1, 2, 3, 4} y 
 * {9, 1, 8, 2, 7}. 
 * En todos los casos, el resultado debe quedar ordenado de menor a mayor. La explicación debe 
 * ser clara, breve y técnica, pensando en una entrega académica. El código debe estar bien 
 * documentado y legible. La solución debe incluir una demostración o validación con pruebas 
 * rápidas al final del programa y mostrar la cantidad de desplazamientos realizados.
 */

package GUIA_Ordenamiento.Ejercicio_3;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Idea general:
 * El algoritmo toma cada elemento del arreglo y lo inserta en la posición correcta
 * dentro de la parte ya ordenada. Se compara con los elementos anteriores hasta
 * encontrar el lugar adecuado.
 *
 * ¿Por qué funciona bien con arreglos casi ordenados?
 * Porque en ese tipo de arreglos la mayoría de los elementos ya están muy cerca de
 * su posición final. Por eso, el valor que se inserta normalmente recorre pocas
 * posiciones hacia la izquierda y realiza menos desplazamientos respecto a un
 * arreglo completamente desordenado.
 */
public class Ordenamiento_X_Insersion {

    /**
     * Guarda la información del resultado del algoritmo.
     */
    private static class ResultadoOrdenamiento {
        private final int[] arregloOrdenado;
        private final int desplazamientos;
        private final int comparaciones;

        public ResultadoOrdenamiento(int[] arregloOrdenado, int desplazamientos, int comparaciones) {
            this.arregloOrdenado = arregloOrdenado;
            this.desplazamientos = desplazamientos;
            this.comparaciones = comparaciones;
        }

        public int[] getArregloOrdenado() {
            return arregloOrdenado;
        }

        public int getDesplazamientos() {
            return desplazamientos;
        }

        public int getComparaciones() {
            return comparaciones;
        }
    }

    /**
     * Ordena un arreglo de enteros usando Insertion Sort.
     *
     * Entrada: arreglo de enteros sin ordenar.
     * Salida: arreglo ordenado en forma ascendente.
     *
     * La idea principal es:
     * 1. Tomar un elemento actual.
     * 2. Compararlo con los elementos anteriores.
     * 3. Desplazar los mayores hacia la derecha.
     * 4. Insertarlo en la posición correcta.
     */
    public static void insertionSort(int[] arreglo) {
        insertionSortConEstadisticas(arreglo);
    }

    /**
     * Variante que devuelve además la cantidad de desplazamientos y comparaciones.
     */
    public static ResultadoOrdenamiento insertionSortConEstadisticas(int[] arreglo) {
        int[] copia = Arrays.copyOf(arreglo, arreglo.length);
        int desplazamientos = 0;
        int comparaciones = 0;

        // Recorremos el arreglo desde el segundo elemento.
        // El primer elemento se toma como parte ya ordenada.
        for (int i = 1; i < copia.length; i++) {
            int valorActual = copia[i];
            int j = i - 1;

            // Mientras el elemento anterior sea mayor que el valor actual,
            // se desplaza hacia la derecha para abrir espacio.
            while (j >= 0) {
                comparaciones++;
                if (copia[j] <= valorActual) {
                    break;
                }
                copia[j + 1] = copia[j];
                desplazamientos++;
                j--;
            }

            // Se inserta el valorActual en la posición correcta.
            copia[j + 1] = valorActual;
        }

        return new ResultadoOrdenamiento(copia, desplazamientos, comparaciones);
    }

    /**
     * Muestra el arreglo por consola.
     */
    private static void mostrarArreglo(String titulo, int[] arreglo) {
        System.out.println(titulo + Arrays.toString(arreglo));
    }

    /**
     * Ejecuta pruebas con varios casos de entrada.
     */
    private static void pruebas() {
        int[][] casos = {
                {7, 3, 9, 1, 5},
                {5, 4, 3, 2, 1},
                {1, 2, 3, 4},
                {9, 1, 8, 2, 7}
        };

        for (int i = 0; i < casos.length; i++) {
            int[] arreglo = Arrays.copyOf(casos[i], casos[i].length);
            ResultadoOrdenamiento resultado = insertionSortConEstadisticas(arreglo);

            System.out.println("\nCaso de prueba " + (i + 1) + ": " + Arrays.toString(casos[i]));
            System.out.println("Resultado ordenado: " + Arrays.toString(resultado.getArregloOrdenado()));
            System.out.println("Desplazamientos: " + resultado.getDesplazamientos());
            System.out.println("Comparaciones: " + resultado.getComparaciones());
        }
    }

    /**
     * Lee un arreglo desde la consola.
     */
    private static int[] leerArreglo(Scanner scanner) {
        System.out.print("Ingrese los números enteros separados por espacios: ");
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Ordenamiento por Inserción (Insertion Sort) ===");
        System.out.println("1) Ingresar arreglo manualmente");
        System.out.println("2) Ejecutar pruebas automáticas");
        System.out.print("Seleccione una opción: ");

        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            int[] arreglo = leerArreglo(scanner);
            mostrarArreglo("Arreglo original: ", arreglo);
            ResultadoOrdenamiento resultado = insertionSortConEstadisticas(arreglo);
            mostrarArreglo("Arreglo ordenado: ", resultado.getArregloOrdenado());
            System.out.println("Desplazamientos realizados: " + resultado.getDesplazamientos());
            System.out.println("Comparaciones realizadas: " + resultado.getComparaciones());
            System.out.println("Observación: Insertion Sort suele rendir mejor con arreglos casi ordenados porque cada elemento se mueve pocas posiciones para alcanzar su lugar correcto.");
        } else {
            pruebas();
        }

        scanner.close();
    }
}
