package GUIA_Analisis_Algoritmos.Ejercicio_10;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Implementa el ordenamiento Bubble Sort sobre un vector de enteros.
 * El algoritmo compara pares de elementos contiguos y los intercambia si se
 * encuentran en el orden incorrecto. La lógica de ordenamiento está separada de
 * la interacción con el usuario, facilitando la reutilización del método y el
 * análisis de su rendimiento.
 */
public class BubbleSort {

    /**
     * Encapsula la cantidad de comparaciones e intercambios realizados durante
     * el proceso de ordenamiento.
     */
    public static class EstadisticasOrdenamiento {
        private final int comparaciones;
        private final int intercambios;

        /**
         * @param comparaciones cantidad total de comparaciones efectuadas
         * @param intercambios cantidad total de intercambios realizados
         */
        public EstadisticasOrdenamiento(int comparaciones, int intercambios) {
            this.comparaciones = comparaciones;
            this.intercambios = intercambios;
        }

        public int getComparaciones() {
            return comparaciones;
        }

        public int getIntercambios() {
            return intercambios;
        }
    }

    /**
     * Ordena un vector de enteros mediante Bubble Sort y devuelve el conteo de
     * operaciones realizadas durante el proceso.
     *
     * @param vector arreglo de enteros a ordenar; no puede ser nulo ni contener
     *               elementos nulos
     * @return objeto con la cantidad de comparaciones e intercambios ejecutados
     * @throws IllegalArgumentException si el vector es nulo o contiene valores nulos
     */
    public static EstadisticasOrdenamiento bubbleSort(Integer[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == null) {
                throw new IllegalArgumentException("El vector no puede contener valores nulos.");
            }
        }

        int comparaciones = 0;
        int intercambios = 0;

        for (int i = 0; i < vector.length - 1; i++) {
            for (int j = 0; j < vector.length - 1 - i; j++) {
                comparaciones++; // cada comparación entre elementos cuenta como una operación
                if (vector[j] > vector[j + 1]) {
                    Integer auxiliar = vector[j];
                    vector[j] = vector[j + 1];
                    vector[j + 1] = auxiliar;
                    intercambios++; // cada intercambio modifica el orden del vector
                }
            }
        }

        return new EstadisticasOrdenamiento(comparaciones, intercambios);
    }

    /**
     * Punto de entrada del programa. Se encarga de la interacción con la consola:
     * solicita el tamaño del vector, genera valores aleatorios, los muestra,
     * ordena el arreglo y finalmente informa la cantidad de operaciones realizadas.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tamanio = leerEnteroPositivo(scanner, "Ingrese la cantidad de elementos del vector: ");
        Integer[] vector = generarVectorAleatorio(tamanio);

        System.out.println("Vector generado:");
        mostrarVector(vector);

        try {
            EstadisticasOrdenamiento estadisticas = bubbleSort(vector);
            System.out.println("Vector ordenado con Bubble Sort:");
            mostrarVector(vector);
            System.out.println("Comparaciones realizadas: " + estadisticas.getComparaciones());
            System.out.println("Intercambios realizados: " + estadisticas.getIntercambios());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Solicita al usuario un valor entero positivo, repitiendo la entrada mientras
     * el dato ingresado no sea válido.
     *
     * @param scanner objeto que permite leer desde la consola
     * @param mensaje texto que se muestra antes de solicitar el valor
     * @return un entero mayor que cero
     */
    private static int leerEnteroPositivo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                if (valor <= 0) {
                    System.out.println("Dato inválido: el tamaño debe ser mayor que 0.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Dato inválido: debe ingresar un número entero positivo.");
                scanner.next();
            }
        }
    }

    /**
     * Genera un vector aleatorio de tamaño indicado con valores enteros dentro del
     * rango 0 a 99.
     *
     * @param tamanio cantidad de elementos del vector
     * @return vector generado aleatoriamente
     */
    private static Integer[] generarVectorAleatorio(int tamanio) {
        Random random = new Random();
        Integer[] vector = new Integer[tamanio];

        for (int i = 0; i < tamanio; i++) {
            vector[i] = random.nextInt(100);
        }

        return vector;
    }

    /**
     * Muestra los elementos del vector en una sola línea separados por espacios.
     *
     * @param vector arreglo a imprimir
     */
    private static void mostrarVector(Integer[] vector) {
        for (Integer valor : vector) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }
}
