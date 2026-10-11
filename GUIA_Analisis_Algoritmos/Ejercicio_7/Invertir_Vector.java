package GUIA_Analisis_Algoritmos.Ejercicio_7;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa de consola que invierte un vector modificando el arreglo original.
 * La estrategia aplicada es la inversión in situ, intercambiando los elementos
 * de los extremos y avanzando hacia el centro hasta cubrir todo el arreglo.
 */
public class Invertir_Vector {

    /**
     * Invierte un arreglo en el mismo espacio de memoria, sin crear un vector auxiliar.
     * Los valores ubicados en los extremos se intercambian repetidamente hasta llegar
     * al centro del arreglo.
     *
     * @param vector Arreglo de enteros que será invertido en el mismo objeto.
     */
    public static void invertirInSitu(int[] vector) {
        if (vector == null || vector.length < 2) {
            return;
        }

        int izquierda = 0;
        int derecha = vector.length - 1;

        while (izquierda < derecha) {
            int auxiliar = vector[izquierda];
            vector[izquierda] = vector[derecha];
            vector[derecha] = auxiliar;
            izquierda++;
            derecha--;
        }
    }

    /**
     * Punto de entrada del programa. Solicita al usuario la cantidad de elementos,
     * lee el vector, lo muestra antes de invertir y posteriormente imprime el arreglo
     * ya invertido.
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

        invertirInSitu(vector);
        System.out.println("Vector invertido: " + imprimirVector(vector));

        scanner.close();
    }

    /**
     * Lee un entero desde la entrada estándar y repite la solicitud si el valor ingresado
     * no es válido.
     *
     * @param scanner Objeto Scanner conectado a la entrada del usuario.
     * @return Entero válido ingresado por el usuario.
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
     * Formatea un arreglo en una cadena legible para mostrarlo por consola.
     *
     * @param vector Arreglo que se desea representar como texto.
     * @return Cadena con el contenido del vector en formato [valor1, valor2, ...].
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
