package GUIA_Analisis_Algoritmos.Ejercicio_8;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Programa de consola que genera una matriz aleatoria de dimensiones ingresadas por el usuario
 * y calcula la suma total de todos sus elementos recorriéndola una sola vez.
 *
 * La lógica de cálculo está separada de la interacción con el usuario para que pueda reutilizarse
 * en otros contextos sin depender de la entrada por consola.
 */
public class Sumar_Elementos_Matriz {

    /**
     * Suma todos los elementos de una matriz rectangular recorriendo cada posición exactamente una vez.
     * Además, cuenta cuántas operaciones se realizaron durante el recorrido.
     *
     * @param matriz matriz de enteros a recorrer
     * @return un arreglo con dos posiciones: [0] la suma total y [1] la cantidad de operaciones
     * @throws IllegalArgumentException si la matriz es nula
     */
    public static int[] sumarElementosMatriz(int[][] matriz) {
        if (matriz == null) {
            throw new IllegalArgumentException("La matriz no puede ser nula.");
        }

        int suma = 0;
        int operaciones = 0;

        // Se recorre la matriz completa: cada acceso a una celda representa una operación
        // de lectura y, al mismo tiempo, se acumula el valor en la suma total.
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                suma += matriz[fila][columna];
                operaciones++;
            }
        }

        // Se devuelve tanto el total acumulado como la cantidad de tareas realizadas,
        // permitiendo analizar la complejidad del algoritmo desde la salida del programa.
        return new int[] {suma, operaciones};
    }

    /**
     * Punto de entrada del programa. Solicita las dimensiones, genera la matriz aleatoria,
     * la muestra y luego calcula la suma total.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Se leen las dimensiones de la matriz y se validan para evitar valores no permitidos.
        int filas = leerEnteroPositivo(scanner, "Ingrese la cantidad de filas de la matriz: ");
        int columnas = leerEnteroPositivo(scanner, "Ingrese la cantidad de columnas de la matriz: ");

        // Se genera la estructura de datos que será recorrida para calcular la suma total.
        int[][] matriz = generarMatrizAleatoria(filas, columnas);

        System.out.println("Matriz generada:");
        mostrarMatriz(matriz);

        try {
            // La suma y la cantidad de operaciones se obtienen desde la lógica reutilizable,
            // dejando la consola solo responsable de presentar resultados.
            int[] resultado = sumarElementosMatriz(matriz);
            int suma = resultado[0];
            int operaciones = resultado[1];

            System.out.println("La suma total de los elementos de la matriz es: " + suma);
            System.out.println("Cantidad de operaciones realizadas: " + operaciones);
        } catch (IllegalArgumentException e) {
            System.out.println("Dato inválido: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Solicita al usuario un entero mayor que cero y repite la pregunta si la entrada no es válida.
     *
     * @param scanner objeto Scanner asociado a la entrada estándar
     * @param mensaje texto que se muestra antes de solicitar el dato
     * @return un número entero positivo
     */
    /**
     * Valida que la entrada del usuario sea un entero positivo y repite la solicitud en caso contrario.
     * Esta validación evita tamaños no permitidos como 0, negativos o valores no numéricos.
     *
     * @param scanner flujo de entrada del usuario
     * @param mensaje texto que se muestra antes de la lectura
     * @return valor entero mayor que cero
     */
    private static int leerEnteroPositivo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                if (valor <= 0) {
                    System.out.println("Dato inválido: el valor debe ser mayor que 0.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Dato inválido: debe ingresar un número entero.");
                scanner.next();
            }
        }
    }

    /**
     * Genera una matriz aleatoria con valores enteros comprendidos entre 0 y 9.
     *
     * @param filas cantidad de filas de la matriz
     * @param columnas cantidad de columnas de la matriz
     * @return matriz aleatoria creada en memoria
     */
    private static int[][] generarMatrizAleatoria(int filas, int columnas) {
        Random random = new Random();
        int[][] matriz = new int[filas][columnas];

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                matriz[fila][columna] = random.nextInt(10);
            }
        }

        return matriz;
    }

    /**
     * Muestra la matriz en formato de filas y columnas para facilitar la visualización en consola.
     *
     * @param matriz matriz a imprimir
     */
    private static void mostrarMatriz(int[][] matriz) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                System.out.print(matriz[fila][columna] + " ");
            }
            System.out.println();
        }
    }
}
