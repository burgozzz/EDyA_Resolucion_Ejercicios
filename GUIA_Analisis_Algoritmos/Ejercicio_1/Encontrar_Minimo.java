package GUIA_Analisis_Algoritmos.Ejercicio_1;
import java.util.OptionalInt;

/**
 * Proporciona una operación para encontrar el menor valor de un vector de enteros.
 */
public class Encontrar_Minimo {

	/**
	 * Busca el valor mínimo recorriendo el vector una sola vez.
	 *
	 * @param vector vector de enteros que se desea analizar; no debe ser {@code null}
	 * @return el mínimo dentro de un {@link OptionalInt}, o vacío si el vector no tiene elementos
	 * @implNote La complejidad temporal es O(n) y la espacial es O(1).
	 */
	public static OptionalInt encontrarMinimo(int[] vector) {
		if (vector.length == 0) {
			return OptionalInt.empty();
		}

		int minimo = vector[0];
		for (int indice = 1; indice < vector.length; indice++) {
			if (vector[indice] < minimo) {
				minimo = vector[indice];
			}
		}

		return OptionalInt.of(minimo);
	}
}
