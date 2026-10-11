package GUIA_Analisis_Algoritmos.Ejercicio_4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Detecta elementos duplicados en un vector utilizando una estructura de elementos vistos.
 *
 * <p>La estrategia con {@link HashSet} permite registrar los valores ya observados y, al
 * encontrarse un valor que ya existe, indicar que hay una repetición. Esto mejora la
 * eficiencia frente a un enfoque con bucles anidados, porque cada elemento se procesa
 * de manera casi constante en promedio.</p>
 */
public class Duplicados_HashSet {

    /**
     * Detecta los valores duplicados usando un conjunto para controlar los elementos ya vistos.
     *
     * <p>Se recorre el vector una sola vez. Si el valor ya estaba dentro del conjunto,
     * se incrementa su frecuencia; si no, se agrega al conjunto para futuras comprobaciones.
     * El enfoque reduce el costo de comparación y permite identificar duplicados con un
     * rendimiento promedio de O(n).</p>
     *
     * @param vector vector de enteros a analizar
     * @return objeto {@link ResultadoDuplicados} con la información de los valores repetidos
     * @throws IllegalArgumentException si el vector recibido es {@code null}
     */
    public static ResultadoDuplicados detectarDuplicados(int[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        Set<Integer> elementosVistos = new HashSet<>();
        Map<Integer, Integer> frecuencias = new HashMap<>();

        for (int valor : vector) {
            if (elementosVistos.contains(valor)) {
                frecuencias.put(valor, frecuencias.getOrDefault(valor, 1) + 1);
            } else {
                elementosVistos.add(valor);
            }
        }

        return new ResultadoDuplicados(!frecuencias.isEmpty(), frecuencias);
    }

    /**
     * Ejecuta la interacción por consola para ingresar el vector y mostrar el resultado.
     *
     * <p>Este método valida la entrada del usuario y luego invoca a la lógica de detección
     * de duplicados. La separación entre la capa de lectura y la lógica del algoritmo facilita
     * que el mismo cálculo pueda reutilizarse fuera de la consola.</p>
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

            for (int indice = 0; indice < vector.length; indice++) {
                vector[indice] = leerEntero(entrada, "Ingrese el elemento " + (indice + 1) + ": ");
            }

            ResultadoDuplicados resultado = detectarDuplicados(vector);
            System.out.println(resultado.getMensaje());
        }
    }

    /**
     * Lee un entero desde la consola y repite la solicitud hasta que la entrada sea válida.
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
     * Encapsula el resultado del análisis de duplicados.
     *
     * <p>Contiene la bandera que indica si hay repetidos y un mapa con la frecuencia
     * exacta de cada valor duplicado.</p>
     */
    public static final class ResultadoDuplicados {
        private final boolean tieneDuplicados;
        private final Map<Integer, Integer> frecuencias;

        public ResultadoDuplicados(boolean tieneDuplicados, Map<Integer, Integer> frecuencias) {
            this.tieneDuplicados = tieneDuplicados;
            this.frecuencias = frecuencias;
        }

        public boolean isTieneDuplicados() {
            return tieneDuplicados;
        }

        public Map<Integer, Integer> getFrecuencias() {
            return frecuencias;
        }

        /**
         * Genera un mensaje legible con el resultado del análisis de duplicados.
         *
         * @return texto con la información de repetición detectada
         */
        public String getMensaje() {
            if (!tieneDuplicados) {
                return "El vector no contiene elementos repetidos.";
            }

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("El vector tiene elementos duplicados. Valores repetidos: ");

            for (Map.Entry<Integer, Integer> entrada : frecuencias.entrySet()) {
                mensaje.append(entrada.getKey())
                        .append(" (aparece ")
                        .append(entrada.getValue())
                        .append(" veces)");
            }

            return mensaje.toString().trim();
        }
    }
}
