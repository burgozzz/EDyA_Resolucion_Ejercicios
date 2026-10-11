package GUIA_Analisis_Algoritmos.Ejercicio_5;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Cuenta cuántas veces aparece cada valor dentro de un vector de enteros.
 *
 * <p>El problema exige no solo saber si un valor existe, sino también la frecuencia total
 * con la que aparece. Por eso la estrategia consiste en recorrer todo el vector y acumular
 * la cantidad de apariciones de cada elemento en un mapa de frecuencias. Esta solución
 * resulta natural y eficiente para tareas de conteo de valores.</p>
 */
public class Ocurrencias {

    /**
     * Cuenta la frecuencia de cada valor dentro de un vector.
     *
     * <p>Se recorre el arreglo completo porque cada posición puede contener un valor que aún
     * no fue contabilizado. Si se omite alguna posición, el total final no sería correcto,
     * porque faltaría integrar una parte de las ocurrencias reales del vector. El algoritmo
     * usa un mapa donde la clave es el valor y el valor asociado es la cantidad de veces que
     * se ha visto hasta el momento.</p>
     *
     * @param vector vector de enteros a analizar
     * @return mapa en el que cada clave es un valor y cada valor asociado es la cantidad de veces
     *         que aparece en el vector
     * @throws IllegalArgumentException si el vector recibido es {@code null}
     */
    public static Map<Integer, Integer> contarOcurrencias(int[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        Map<Integer, Integer> frecuencias = new HashMap<>();

        for (int valor : vector) {
            frecuencias.put(valor, frecuencias.getOrDefault(valor, 0) + 1);
        }

        return frecuencias;
    }

    /**
     * Ejecuta la interacción por consola para ingresar el vector y mostrar los resultados.
     *
     * <p>Este método se encarga de validar la entrada del usuario y de invocar la lógica de
     * conteo. La separación entre la lectura de datos y el cálculo central permite reutilizar
     * la lógica de conteo en otras partes del programa sin depender del flujo de consola.</p>
     */
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in)) {
            int cantidad;
            do {
                cantidad = leerEntero(entrada, "Ingrese la cantidad de elementos del vector: ");
                if (cantidad < 0) {
                    System.out.println("La cantidad no puede ser negativa.");
                }
            } while (cantidad < 0);

            int[] vector = new int[cantidad];

            if (cantidad == 0) {
                System.out.println("El vector está vacío. No hay elementos para contar.");
                return;
            }

            for (int indice = 0; indice < vector.length; indice++) {
                vector[indice] = leerEntero(entrada, "Ingrese el elemento " + (indice + 1) + ": ");
            }

            Map<Integer, Integer> frecuencias = contarOcurrencias(vector);

            System.out.println("Frecuencias:");
            for (Map.Entry<Integer, Integer> entradaMapa : frecuencias.entrySet()) {
                System.out.println("Valor " + entradaMapa.getKey() + ": " + entradaMapa.getValue() + " veces");
            }
        }
    }

    /**
     * Lee un entero desde la consola y repite la solicitud hasta que la entrada sea válida.
     *
     * <p>Este método centraliza la validación de entrada para evitar errores por contenido no
     * numérico. Si el usuario ingresa un valor que no puede convertirse a entero, se muestra un
     * mensaje informativo y se solicita nuevamente la entrada.</p>
     *
     * @param entrada lector de entrada
     * @param mensaje texto mostrado antes de leer el valor
     * @return entero válido ingresado por el usuario
     */
    private static int leerEntero(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();

            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException excepcion) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }
}
