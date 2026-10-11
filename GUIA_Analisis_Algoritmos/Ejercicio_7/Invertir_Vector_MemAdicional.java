package GUIA_Analisis_Algoritmos.Ejercicio_7;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa de consola que invierte un vector creando un arreglo auxiliar con el
 * orden inverso. A diferencia de la versión in situ, este enfoque conserva el
 * vector original y devuelve una nueva estructura con los elementos invertidos.
 */
public class Invertir_Vector_MemAdicional {

    /**
     * Crea y devuelve un nuevo arreglo con los elementos del vector original en orden inverso.
     * La estructura original no se modifica.
     *
     * @param vector Arreglo de enteros que se desea invertir.
     * @return Nuevo arreglo con los elementos en orden inverso. Si el vector de entrada es nulo,
     *         devuelve null.
     */
    public static int[] invertirConMemoriaAdicional(int[] vector) {
        if (vector == null) {
            return null;
        }

        int[] invertido = new int[vector.length];

        for (int i = 0; i < vector.length; i++) {
            invertido[i] = vector[vector.length - 1 - i];
        }

        return invertido;
    }

    /**
     * Punto de entrada del programa. Solicita la cantidad de elementos, lee cada valor,
     * muestra el arreglo original y luego presenta su versión invertida.
     *
     * @param args Argumentos de la línea de comandos. No se utilizan en esta implementación.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de elementos del vector: ");
        int cantidad = leerEnteroValido(scanner);

        while (cantidad < 0) {
            System.out.print("La cantidad de elementos no puede ser negativa. Ingrese nuevamente: ");
            cantidad = leerEnteroValido(scanner);
        }

        int[] vector = new int[cantidad];

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese el valor para la posicion " + i + ": ");
            vector[i] = leerEnteroValido(scanner);
        }

        System.out.println("Vector original: " + imprimirVector(vector));

        int[] vectorInvertido = invertirConMemoriaAdicional(vector);
        System.out.println("Vector invertido: " + imprimirVector(vectorInvertido));

        scanner.close();
    }

    /**
     * Lee un entero válido desde la entrada del usuario.
     *
     * @param scanner Objeto Scanner que se utiliza para leer la entrada.
     * @return Número entero ingresado por el usuario.
     */
    private static int leerEnteroValido(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Intente nuevamente: ");
                scanner.next();
            }
        }
    }

    /**
     * Formatea un arreglo en una cadena legible para mostrarlo en consola.
     *
     * @param vector Arreglo que se desea convertir a texto.
     * @return Representación textual del vector en formato [valor1, valor2, ...].
     */
    private static String imprimirVector(int[] vector) {
        if (vector == null) {
            return "null";
        }

        if (vector.length == 0) {
            return "[]";
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("[");

        for (int i = 0; i < vector.length; i++) {
            resultado.append(vector[i]);
            if (i < vector.length - 1) {
                resultado.append(", ");
            }
        }

        resultado.append("]");
        return resultado.toString();
    }
}
