package GUIA_Analisis_Algoritmos.Ejercicio_4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Detecta elementos duplicados en un vector usando un enfoque con bucles anidados.
 *
 * <p>Esta estrategia compara cada elemento con todos los demás elementos del vector.
 * Si el valor aparece más de una vez, se registra como duplicado. Es una solución simple
 * y directa, pero con complejidad O(n²) en el caso general.</p>
 */
public class Duplicados_For {

    /**
     * Busca todos los valores duplicados dentro del vector usando comparación directa.
     *
     * <p>Se recorre cada posición del arreglo y se compara con el resto del vector.
     * Cuando un valor vuelve a aparecer, se incrementa su frecuencia en un mapa y se
     * evita contar la misma repetición varias veces mediante un arreglo de elementos ya
     * procesados. Este enfoque es simple y directo, pero su costo crece cuadráticamente
     * con la cantidad de elementos.</p>
     *
     * @param vector vector de enteros a analizar
     * @return un objeto {@link ResultadoDuplicados} con el estado del análisis y la
     *         frecuencia de cada valor repetido
     * @throws IllegalArgumentException si el vector recibido es {@code null}
     */
    public static ResultadoDuplicados detectarDuplicados(int[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        Map<Integer, Integer> frecuencias = new HashMap<>();
        boolean[] procesados = new boolean[vector.length];

        for (int i = 0; i < vector.length; i++) {
            if (procesados[i]) {
                continue;
            }

            int valorActual = vector[i];
            int cantidad = 1;

            for (int j = i + 1; j < vector.length; j++) {
                if (vector[j] == valorActual) {
                    cantidad++;
                    procesados[j] = true;
                }
            }

            if (cantidad > 1) {
                frecuencias.put(valorActual, cantidad);
            }

            procesados[i] = true;
        }

        List<Integer> valoresDuplicados = new ArrayList<>(frecuencias.keySet());
        return new ResultadoDuplicados(!frecuencias.isEmpty(), valoresDuplicados, frecuencias);
    }

    /**
     * Ejecuta la interacción por consola para ingresar el vector y mostrar el resultado.
     *
     * <p>Este método se encarga de validar la entrada del usuario y de invocar la lógica
     * de detección de duplicados. La separación entre la parte de entrada/salida y la
     * lógica del algoritmo facilita reutilizar el código en otras operaciones o pruebas.</p>
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
     * @param mensaje texto mostrado antes de leer el valor
     * @return número entero válido ingresado por el usuario
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
     * Encapsula la información obtenida al detectar duplicados dentro de un vector.
     *
     * <p>Permite almacenar de forma ordenada si existe al menos un valor repetido,
     * cuáles son esos valores y cuántas veces aparecen en el arreglo.</p>
     */
    public static final class ResultadoDuplicados {
        private final boolean tieneDuplicados;
        private final List<Integer> valoresDuplicados;
        private final Map<Integer, Integer> frecuencias;

        public ResultadoDuplicados(boolean tieneDuplicados, List<Integer> valoresDuplicados,
                                  Map<Integer, Integer> frecuencias) {
            this.tieneDuplicados = tieneDuplicados;
            this.valoresDuplicados = valoresDuplicados;
            this.frecuencias = frecuencias;
        }

        public boolean isTieneDuplicados() {
            return tieneDuplicados;
        }

        public List<Integer> getValoresDuplicados() {
            return valoresDuplicados;
        }

        public Map<Integer, Integer> getFrecuencias() {
            return frecuencias;
        }

        /**
         * Genera un mensaje legible con la información de duplicados encontrados.
         *
         * @return texto indicando si hay duplicados y qué valores aparecen repetidos
         */
        public String getMensaje() {
            if (!tieneDuplicados) {
                return "El vector no contiene elementos repetidos.";
            }

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("El vector tiene elementos duplicados. ");
            mensaje.append("Valores repetidos: ");

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
