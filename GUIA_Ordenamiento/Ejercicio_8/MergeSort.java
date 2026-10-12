/*
 * PROMPT UTILIZADO:
 * Implementa en Java un programa que ordene un arreglo de números enteros usando
 * MergeSort. Utiliza como datos de prueba:
 * int[] numeros = {38, 27, 43, 3, 9, 82, 10};
 *
 * Muestra el arreglo original y el arreglo final ordenado. Muestra cómo se divide el
 * arreglo en subarreglos durante la recursión, cuándo cada subarreglo alcanza la
 * condición de corte (cero o un elemento), y cómo se fusionan los subarreglos junto con
 * el contenido de cada resultado fusionado. Usa indentación según el nivel recursivo.
 * Incluye comentarios breves que expliquen la división, la condición de corte y la
 * fusión ordenada. Explica la diferencia entre dividir el problema y fusionar sus partes.
 * Mantén la implementación sencilla, adecuada para un ejercicio introductorio y sin
 * dependencias externas.
 *
 * Verifica que el resultado sea [3, 9, 10, 27, 38, 43, 82]. Comprueba también arreglos
 * vacío, unitario, ya ordenado, en orden inverso y con valores repetidos. Si alguna
 * prueba falla o hay errores de compilación, corrige la implementación y vuelve a
 * probarla. La salida debe distinguir las divisiones, los casos base y las fusiones.
 */
package GUIA_Ordenamiento.Ejercicio_8;

import java.util.Arrays;

/**
 * Ordena arreglos de enteros con MergeSort y puede mostrar cada división y fusión
 * para hacer visible el proceso recursivo.
 */
public class MergeSort {
	/**
	 * Ordena recursivamente el rango inclusivo indicado.
	 * Primero divide el rango en dos mitades, las ordena por separado y luego las fusiona.
	 * El parámetro {@code nivel} determina la indentación de la traza.
	 *
	 * @param numeros arreglo que se ordena directamente
	 * @param inicio índice inicial del rango
	 * @param fin índice final del rango
	 * @param nivel profundidad actual de la recursión
	 * @param mostrarPasos indica si se imprimen divisiones, casos base y fusiones
	 */
	private static void mergeSort(int[] numeros, int inicio, int fin, int nivel, boolean mostrarPasos) {
		String sangria = "  ".repeat(nivel);

		// Un rango vacío o unitario ya está ordenado y detiene la recursión.
		if (inicio >= fin) {
			if (mostrarPasos) {
				System.out.println(sangria + "Caso base: " + mostrarRango(numeros, inicio, fin));
			}
			return;
		}

		int medio = inicio + (fin - inicio) / 2;
		// Se muestran las mitades antes de resolver cada una recursivamente.
		if (mostrarPasos) {
			System.out.println(sangria + "Dividir " + mostrarRango(numeros, inicio, fin));
			System.out.println(sangria + "  Izquierda: " + mostrarRango(numeros, inicio, medio));
			System.out.println(sangria + "  Derecha:   " + mostrarRango(numeros, medio + 1, fin));
		}

		// La división crea dos problemas menores; cada uno se ordena recursivamente.
		mergeSort(numeros, inicio, medio, nivel + 1, mostrarPasos);
		mergeSort(numeros, medio + 1, fin, nivel + 1, mostrarPasos);

		// La fusión combina las dos mitades ya ordenadas en un solo rango ordenado.
		fusionar(numeros, inicio, medio, fin, nivel, mostrarPasos);
	}

	/**
	 * Combina dos rangos adyacentes que ya están ordenados y escribe el resultado
	 * ordenado en el rango original del arreglo.
	 *
	 * @param numeros arreglo que contiene las dos mitades y recibe la fusión
	 * @param inicio primer índice de la mitad izquierda
	 * @param medio último índice de la mitad izquierda
	 * @param fin último índice de la mitad derecha
	 * @param nivel profundidad usada para indentar los mensajes
	 * @param mostrarPasos indica si se muestran las mitades y el resultado combinado
	 */
	private static void fusionar(int[] numeros, int inicio, int medio, int fin,
			int nivel, boolean mostrarPasos) {
		int[] izquierda = Arrays.copyOfRange(numeros, inicio, medio + 1);
		int[] derecha = Arrays.copyOfRange(numeros, medio + 1, fin + 1);
		String sangria = "  ".repeat(nivel);

		if (mostrarPasos) {
			System.out.println(sangria + "Fusionar " + Arrays.toString(izquierda)
					+ " con " + Arrays.toString(derecha));
		}

		int indiceIzquierda = 0;
		int indiceDerecha = 0;
		int indiceResultado = inicio;

		// Comparamos los elementos actuales y copiamos primero el menor.
		while (indiceIzquierda < izquierda.length && indiceDerecha < derecha.length) {
			if (izquierda[indiceIzquierda] <= derecha[indiceDerecha]) {
				numeros[indiceResultado++] = izquierda[indiceIzquierda++];
			} else {
				numeros[indiceResultado++] = derecha[indiceDerecha++];
			}
		}

		// Copiamos los elementos que hayan quedado pendientes en cualquiera de las mitades.
		while (indiceIzquierda < izquierda.length) {
			numeros[indiceResultado++] = izquierda[indiceIzquierda++];
		}
		while (indiceDerecha < derecha.length) {
			numeros[indiceResultado++] = derecha[indiceDerecha++];
		}

		if (mostrarPasos) {
			System.out.println(sangria + "Resultado de la fusión: "
					+ mostrarRango(numeros, inicio, fin));
		}
	}

	/** Devuelve el contenido de un rango inclusivo; un rango vacío se representa como []. */
	private static String mostrarRango(int[] numeros, int inicio, int fin) {
		if (inicio > fin) {
			return "[]";
		}
		return Arrays.toString(Arrays.copyOfRange(numeros, inicio, fin + 1));
	}

	/** Ejecuta el algoritmo sobre una copia y compara el resultado con el esperado. */
	private static void verificarCaso(String descripcion, int[] entrada, int[] esperado) {
		int[] resultado = Arrays.copyOf(entrada, entrada.length);
		mergeSort(resultado, 0, resultado.length - 1, 0, false);
		if (!Arrays.equals(resultado, esperado)) {
			throw new AssertionError(descripcion + ": se esperaba " + Arrays.toString(esperado)
					+ " pero se obtuvo " + Arrays.toString(resultado));
		}
		System.out.println("[Correcto] " + descripcion + ".");
	}

	/** Ejecuta la demostración principal y verifica el ejemplo y los casos límite. */
	public static void main(String[] args) {
		int[] numeros = {38, 27, 43, 3, 9, 82, 10};
		int[] esperado = {3, 9, 10, 27, 38, 43, 82};

		System.out.println("============================================");
		System.out.println(" MERGE SORT | DIVIDIR Y FUSIONAR");
		System.out.println("============================================");
		System.out.println("\nArreglo original: " + Arrays.toString(numeros));
		mergeSort(numeros, 0, numeros.length - 1, 0, true);
		System.out.println("\nArreglo final ordenado: " + Arrays.toString(numeros));

		if (!Arrays.equals(numeros, esperado)) {
			throw new AssertionError("El arreglo de prueba no quedó ordenado correctamente.");
		}
		System.out.println("[Correcto] El resultado coincide con el esperado.");
		System.out.println("\nDividir separa el arreglo en partes más pequeñas. Fusionar combina"
				+ " las partes ya ordenadas para reconstruir el arreglo completo en orden.");

		System.out.println("\n--- Verificaciones adicionales ---");
		verificarCaso("arreglo vacío", new int[]{}, new int[]{});
		verificarCaso("arreglo unitario", new int[]{5}, new int[]{5});
		verificarCaso("arreglo ya ordenado", new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
		verificarCaso("arreglo en orden inverso", new int[]{4, 3, 2, 1}, new int[]{1, 2, 3, 4});
		verificarCaso("valores repetidos", new int[]{3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 3});
	}
}
