package GUIA_Analisis_Algoritmos.Ejercicio_3;

import java.util.Scanner;

/**
 * Implementa la búsqueda binaria en un vector ordenado de enteros.
 *
 * <p>La estrategia es apropiada cuando los datos ya están ordenados, porque cada
 * comparación permite descartar una mitad del vector y reducir el espacio de búsqueda.
 * Esto convierte la búsqueda en una operación de complejidad O(log n), mucho más
 * eficiente que recorrer secuencialmente todos los elementos.</p>
 */
public class Buscar_Elemento_Ordenado {

    /**
     * Busca un valor entero en un vector ordenado usando la estrategia de búsqueda binaria.
     *
     * <p>Se mantiene un rango de búsqueda definido por los índices izquierdo y derecho.
     * Cada iteración calcula el punto medio del rango y compara ese valor con el objetivo.
     * Si el valor buscado es menor que el elemento central, el rango se desplaza hacia la
     * mitad izquierda; si es mayor, se desplaza hacia la derecha. Si coincide, se devuelve
     * el índice encontrado y la cantidad de comparaciones realizadas.</p>
     *
     * @param vector vector ordenado en el que se realizará la búsqueda
     * @param valorBuscado valor que se desea encontrar
     * @return un objeto {@link ResultadoBusqueda} con el estado de la búsqueda, el índice y
     *         la cantidad de comparaciones realizadas
     * @throws IllegalArgumentException si el vector recibido es {@code null}
     */
    public static ResultadoBusqueda buscarElemento(int[] vector, int valorBuscado) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        if (vector.length == 0) {
            return new ResultadoBusqueda(false, -1, 0);
        }

        int izquierda = 0;
        int derecha = vector.length - 1;
        int comparaciones = 0;

        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            comparaciones++;

            if (vector[medio] == valorBuscado) {
                return new ResultadoBusqueda(true, medio, comparaciones);
            }

            if (valorBuscado < vector[medio]) {
                derecha = medio - 1;
            } else {
                izquierda = medio + 1;
            }
        }

        return new ResultadoBusqueda(false, -1, comparaciones);
    }

    /**
     * Ejecuta la interacción por consola para ingresar el vector y el valor buscado.
     *
     * <p>La entrada del vector exige que los elementos se ingresen en orden ascendente.
     * Esto garantiza que la búsqueda binaria sea válida, ya que la estrategia asume que
     * el arreglo está ordenado. Si el usuario ingresa un valor menor que el anterior,
     * el programa vuelve a solicitarlo hasta que el orden sea correcto.</p>
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
                System.out.println("El vector está vacío. No puede buscarse.");
                return;
            }

            System.out.println("Ingrese los elementos del vector en orden ascendente:");
            int valorAnterior = Integer.MIN_VALUE;
            for (int indice = 0; indice < vector.length; indice++) {
                int valor;
                do {
                    valor = leerEntero(entrada, "Elemento " + (indice + 1) + ": ");
                    if (valor < valorAnterior) {
                        System.out.println("El valor debe ser mayor o igual que el anterior para mantener el orden ascendente.");
                    }
                } while (valor < valorAnterior);

                vector[indice] = valor;
                valorAnterior = valor;
            }

            int valorBuscado = leerEntero(entrada, "Ingrese el valor a buscar: ");

            ResultadoBusqueda resultado = buscarElemento(vector, valorBuscado);
            System.out.println(resultado.getMensaje());
        }
    }

    /**
     * Lee un entero desde la consola y repite la solicitud hasta que la entrada sea válida.
     *
     * <p>Este método centraliza la validación de la entrada para evitar errores por texto no
     * numérico. Si el usuario ingresa algo distinto a un entero, se muestra un mensaje y se
     * solicita nuevamente el valor.</p>
     *
     * @param entrada lector de entrada
     * @param mensaje texto que se muestra antes de la lectura
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

    /**
     * Encapsula el resultado de una búsqueda binaria.
     *
     * <p>Este contenedor permite devolver de manera ordenada si el valor fue encontrado,
     * en qué índice apareció y cuántas comparaciones fueron necesarias. La información es
     * útil tanto para mostrar el resultado al usuario como para analizar la eficiencia del
     * algoritmo.</p>
     */
    public static final class ResultadoBusqueda {
        private final boolean encontrado;
        private final int indice;
        private final int comparaciones;

        /**
         * Crea un resultado de búsqueda con la información relevante del proceso.
         *
         * @param encontrado {@code true} si el valor fue localizado, {@code false} en caso contrario
         * @param indice posición donde apareció el valor; si no existe, se usa {@code -1}
         * @param comparaciones cantidad de comparaciones realizadas durante la búsqueda
         */
        public ResultadoBusqueda(boolean encontrado, int indice, int comparaciones) {
            this.encontrado = encontrado;
            this.indice = indice;
            this.comparaciones = comparaciones;
        }

        /**
         * Indica si el valor buscado fue encontrado en el vector.
         *
         * @return {@code true} si hay coincidencia, {@code false} si no la hubo
         */
        public boolean isEncontrado() {
            return encontrado;
        }

        /**
         * Devuelve el índice en el que se encontró el valor.
         *
         * @return índice basado en cero o {@code -1} si no se encontró el valor
         */
        public int getIndice() {
            return indice;
        }

        /**
         * Devuelve la cantidad de comparaciones realizadas durante la búsqueda.
         *
         * @return número de comparaciones realizadas
         */
        public int getComparaciones() {
            return comparaciones;
        }

        /**
         * Genera un mensaje legible con el resultado final de la búsqueda.
         *
         * @return texto con el resultado de la operación y la cantidad de comparaciones
         */
        public String getMensaje() {
            if (!encontrado) {
                return "El valor no se encuentra en el vector. Se realizaron "
                        + comparaciones + " comparaciones.";
            }

            return "El valor fue encontrado en el índice " + indice
                    + ". Se realizaron " + comparaciones + " comparaciones.";
        }
    }
}
