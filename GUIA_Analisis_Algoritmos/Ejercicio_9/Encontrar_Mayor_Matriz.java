package GUIA_Analisis_Algoritmos.Ejercicio_9;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Programa de consola que genera una matriz aleatoria y determina el mayor
 * valor contenido en ella. Dado que la matriz no está ordenada, el algoritmo
 * debe recorrer cada una de sus posiciones para comparar el valor actual con
 * el máximo encontrado hasta el momento.
 */
public class Encontrar_Mayor_Matriz {

    /**
     * Recorre la matriz completa para identificar el elemento de mayor valor.
     * Como la estructura no tiene un orden específico, cada posición debe ser
     * revisada una por una.
     *
     * @param matriz matriz bidimensional con al menos una fila y una columna
     * @return el valor máximo contenido dentro de la matriz
     * @throws IllegalArgumentException si la matriz es nula o vacía
     */
    public static int encontrarMayorMatriz(int[][] matriz) {
        if (matriz == null) {
            throw new IllegalArgumentException("La matriz no puede ser nula.");
        }

        if (matriz.length == 0 || matriz[0].length == 0) {
            throw new IllegalArgumentException("La matriz no puede estar vacía.");
        }

        int mayor = matriz[0][0];

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                if (matriz[fila][columna] > mayor) {
                    mayor = matriz[fila][columna];
                }
            }
        }

        return mayor;
    }

    /**
     * Punto de entrada del programa. Solicita el tamaño de la matriz, la genera
     * con valores aleatorios, la muestra por consola y finalmente imprime el
     * mayor elemento encontrado.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int filas = leerEnteroPositivo(scanner, "Ingrese la cantidad de filas de la matriz: ");
        int columnas = leerEnteroPositivo(scanner, "Ingrese la cantidad de columnas de la matriz: ");

        int[][] matriz = generarMatrizAleatoria(filas, columnas);

        System.out.println("Matriz generada:");
        mostrarMatriz(matriz);

        try {
            int mayor = encontrarMayorMatriz(matriz);
            System.out.println("El mayor valor de la matriz es: " + mayor);
        } catch (IllegalArgumentException e) {
            System.out.println("Dato inválido: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Solicita un valor entero mayor que cero al usuario y repite la operación
     * hasta que la entrada sea válida.
     *
     * @param scanner objeto que permite leer la entrada del usuario
     * @param mensaje texto presentado al usuario antes de ingresar el dato
     * @return un entero positivo mayor que cero
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
     * Genera una matriz bidimensional de tamaño indicado con valores aleatorios
     * comprendidos entre 0 y 99.
     *
     * @param filas cantidad de filas de la matriz
     * @param columnas cantidad de columnas de la matriz
     * @return matriz generada aleatoriamente
     */
    private static int[][] generarMatrizAleatoria(int filas, int columnas) {
        Random random = new Random();
        int[][] matriz = new int[filas][columnas];

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                matriz[fila][columna] = random.nextInt(100);
            }
        }

        return matriz;
    }

    /**
     * Muestra la estructura completa de la matriz en la salida estándar.
     * Cada fila se imprime en una línea y cada valor se presenta separado por un espacio.
     *
     * @param matriz matriz que será mostrada por consola
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
