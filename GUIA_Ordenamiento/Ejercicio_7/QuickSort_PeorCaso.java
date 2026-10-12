/*
 * PROMPT UTILIZADO:
 * Implementa en Java un programa que aplique QuickSort a un arreglo de números enteros
 * ya ordenado, utilizando siempre el primer elemento de cada subarreglo como pivote.
 *
 * Usa como datos de prueba:
 * int[] numeros = {1, 2, 3, 4, 5, 6, 7};
 *
 * El programa debe mostrar el arreglo original y el arreglo ordenado. Debe contar las
 * invocaciones al método QuickSort, incluyendo la llamada inicial y las que reciben
 * subarreglos vacíos o unitarios, y mostrar cuántas fueron recursivas, excluyendo la
 * inicial. Incluye comentarios breves sobre la partición, el caso base y el efecto de
 * elegir el primer elemento como pivote.
 *
 * Explica por qué, para este arreglo ascendente, esa elección produce una partición
 * desequilibrada: un lado queda vacío y el otro conserva casi todos los elementos.
 * Relaciona este comportamiento con el peor rendimiento de QuickSort, sin generalizarlo
 * a todas las estrategias de selección de pivote. Mantén una implementación sencilla y
 * sin dependencias externas.
 *
 * Verifica que el resultado sea [1, 2, 3, 4, 5, 6, 7]. Cuenta cada entrada al método,
 * incluidos los casos base, y realiza las llamadas recursivas para ambos lados de cada
 * partición. Con esa convención, el ejemplo debe producir 13 invocaciones totales: una
 * inicial y 12 recursivas. Comprueba también un arreglo vacío y uno unitario. Corrige
 * cualquier error de compilación o prueba y vuelve a verificar la solución.
 * La salida debe mostrar los datos de entrada, el resultado, el conteo y por qué el
 * pivote elegido genera el peor caso para este arreglo.
 */
package GUIA_Ordenamiento.Ejercicio_7;

import java.util.Arrays;

public class QuickSort_PeorCaso {
	private static int invocacionesTotales;
	private static int invocacionesRecursivas;

	private static void quickSort(int[] numeros, int inicio, int fin, int nivel, boolean mostrarPasos) {
		invocacionesTotales++;
		if (nivel > 0) {
			invocacionesRecursivas++;
		/**
		 * Demuestra el peor caso de QuickSort al ordenar un arreglo ascendente usando
		 * siempre el primer elemento de cada subarreglo como pivote.
		 */
		}
			/** Cuenta todas las entradas a {@link #quickSort(int[], int, int, int)}. */

			/** Cuenta las entradas recursivas, sin incluir la llamada inicial. */
		String sangria = "  ".repeat(nivel);
		if (mostrarPasos) {
			/**
			 * Ordena recursivamente el rango indicado y suma cada entrada al método,
			 * incluidos los subarreglos vacíos y unitarios.
			 *
			 * @param numeros arreglo que se ordena directamente
			 * @param inicio índice inicial del subarreglo
			 * @param fin índice final del subarreglo
			 * @param nivel profundidad recursiva usada para indentar la traza
			 * @param mostrarPasos indica si se imprimen las particiones y los casos base
			 */
			System.out.println(sangria + "Subarreglo: " + mostrarRango(numeros, inicio, fin));
		}

		// Un subarreglo vacío o unitario ya está ordenado y detiene la recursión.
		if (inicio >= fin) {
			if (mostrarPasos) {
				System.out.println(sangria + "Caso base: no se necesita particionar.");
			}
			return;
		}

		// La partición coloca el primer elemento en su posición definitiva.
		int posicionPivote = particionar(numeros, inicio, fin);
		if (mostrarPasos) {
			System.out.println(sangria + "Pivote (primer elemento): " + numeros[posicionPivote]);
			System.out.println(sangria + "Partición izquierda: "
					+ mostrarRango(numeros, inicio, posicionPivote - 1));
			System.out.println(sangria + "Partición derecha: "
					+ mostrarRango(numeros, posicionPivote + 1, fin));
		}

		// Se ordenan recursivamente las particiones izquierda y derecha.
		quickSort(numeros, inicio, posicionPivote - 1, nivel + 1, mostrarPasos);
		quickSort(numeros, posicionPivote + 1, fin, nivel + 1, mostrarPasos);
	}

	/**
	 * Particiona el rango usando su primer elemento como pivote. Al terminar,
	 * los valores menores quedan antes del pivote y los demás quedan después.
	 *
	 * @return índice definitivo del pivote
	 */
	private static int particionar(int[] numeros, int inicio, int fin) {
		int pivote = numeros[inicio];
		int siguienteMenor = inicio + 1;

		// Agrupamos los elementos menores que el pivote antes de ubicarlo.
		for (int actual = inicio + 1; actual <= fin; actual++) {
			if (numeros[actual] < pivote) {
				intercambiar(numeros, actual, siguienteMenor);
				siguienteMenor++;
			}
		}

		int posicionPivote = siguienteMenor - 1;
		intercambiar(numeros, inicio, posicionPivote);
		return posicionPivote;
	}

	/** Intercambia dos elementos del arreglo. */
	private static void intercambiar(int[] numeros, int primero, int segundo) {
		int temporal = numeros[primero];
		numeros[primero] = numeros[segundo];
		numeros[segundo] = temporal;
	}

	private static String mostrarRango(int[] numeros, int inicio, int fin) {
		if (inicio > fin) {
			return "[]";
		}
		return Arrays.toString(Arrays.copyOfRange(numeros, inicio, fin + 1));
	}

	/** Restablece los contadores antes de iniciar una ejecución independiente. */
	private static void reiniciarContadores() {
		invocacionesTotales = 0;
		invocacionesRecursivas = 0;
	}

	/** Muestra los contadores con la convención usada por este ejercicio. */
	private static void mostrarContadores() {
		System.out.println("Invocaciones totales (incluye inicial y casos base): " + invocacionesTotales);
		System.out.println("Invocaciones recursivas (excluye la inicial): " + invocacionesRecursivas);
	}

	/** Ordena una copia de entrada y comprueba que coincida con el resultado esperado. */
	private static void verificarCaso(String descripcion, int[] entrada, int[] esperado) {
		int[] resultado = Arrays.copyOf(entrada, entrada.length);
		reiniciarContadores();
		quickSort(resultado, 0, resultado.length - 1, 0, false);
		if (!Arrays.equals(resultado, esperado)) {
			throw new AssertionError(descripcion + ": se esperaba " + Arrays.toString(esperado)
					+ " pero se obtuvo " + Arrays.toString(resultado));
		}
		System.out.println("[Correcto] " + descripcion + ".");
	}

	/** Ejecuta el ejemplo ascendente y comprueba el conteo y los casos base. */
	public static void main(String[] args) {
		int[] numeros = {1, 2, 3, 4, 5, 6, 7};
		int[] esperado = {1, 2, 3, 4, 5, 6, 7};

		System.out.println("============================================");
		System.out.println(" QUICK SORT | ANÁLISIS DEL PEOR CASO");
		System.out.println("============================================");
		System.out.println("\n--- Prueba con arreglo ascendente ---");
		System.out.println("Entrada : " + Arrays.toString(numeros));
		reiniciarContadores();
		quickSort(numeros, 0, numeros.length - 1, 0, true);
		System.out.println("Resultado: " + Arrays.toString(numeros));

		if (!Arrays.equals(numeros, esperado)) {
			throw new AssertionError("El arreglo de prueba no quedó ordenado correctamente.");
		}
		if (invocacionesTotales != 13 || invocacionesRecursivas != 12) {
			throw new AssertionError("Conteo incorrecto: se esperaban 13 invocaciones totales"
					+ " y 12 recursivas, pero se obtuvieron " + invocacionesTotales
					+ " totales y " + invocacionesRecursivas + " recursivas.");
		}

		mostrarContadores();
		System.out.println("[Correcto] El arreglo y el conteo coinciden con lo esperado.");
		System.out.println("\n¿Por qué es el peor caso?");
		System.out.println("Al estar ordenado, el primer elemento es el menor. Cada partición deja"
				+ " un lado vacío y el otro con casi todos los elementos. La recursión queda"
				+ " desequilibrada y QuickSort realiza mucho más trabajo con esta elección de pivote.");

		System.out.println("\n--- Verificaciones adicionales ---");
		verificarCaso("arreglo vacío", new int[]{}, new int[]{});
		verificarCaso("arreglo unitario", new int[]{5}, new int[]{5});
		// Código eliminado para interacción por consola
	}
}
