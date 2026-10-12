/*
 * PROMPT UTILIZADO:
 * Implementa en Java QuickSort usando siempre el primer elemento como pivote. Trabaja
 * sobre la clase QuickSort del archivo QuickSort.java.
 *
 * Utiliza como datos de prueba:
 * int[] numeros = {8, 3, 1, 7, 0, 10, 2};
 *
 * Muestra el arreglo original y el arreglo final ordenado. En cada llamada recursiva,
 * elige como pivote el primer elemento del subarreglo actual. Después de particionar,
 * muestra el pivote elegido, el subarreglo izquierdo y el subarreglo derecho. Indica
 * claramente el caso base que detiene la recursión cuando el subarreglo tiene cero o un
 * elemento. Incluye comentarios breves que expliquen la partición, las llamadas
 * recursivas y cómo el algoritmo aplica la estrategia de dividir y vencerás. Organiza
 * los mensajes con indentación según el nivel recursivo y mantén la implementación
 * sencilla y adecuada para un ejercicio introductorio.
 *
 * Verifica que el arreglo de prueba quede ordenado como [0, 1, 2, 3, 7, 8, 10].
 * Comprueba también arreglos vacío, unitario, ya ordenado, en orden inverso y con valores
 * repetidos. Si una prueba falla o hay errores de compilación, corrige la implementación
 * y vuelve a probarla. La salida debe permitir observar cómo se elige el primer elemento
 * de cada subarreglo como pivote y cómo se forman las particiones antes de las llamadas
 * recursivas.
 */
package GUIA_Ordenamiento.Ejercicio_6;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Ordena arreglos de enteros con QuickSort, eligiendo como pivote el primer elemento
 * del subarreglo y mostrando el proceso recursivo cuando se solicita.
 */
public class QuickSort {

	/**
	 * Ordena una sección del arreglo y, opcionalmente, muestra sus particiones.
	 *
	 * @param numeros arreglo que se ordena directamente
	 * @param inicio primer índice del subarreglo
	 * @param fin último índice del subarreglo
	 * @param nivel profundidad de la llamada, usada para indentar la salida
	 * @param mostrarPasos indica si se imprime el proceso de partición
	 */
	private static void quickSort(int[] numeros, int inicio, int fin, int nivel, boolean mostrarPasos) {
		String sangria = "  ".repeat(nivel);

		// Un subarreglo vacío o unitario ya está ordenado y no necesita más llamadas.
		if (inicio >= fin) {
			if (mostrarPasos) {
				System.out.println(sangria + "Caso base: " + mostrarRango(numeros, inicio, fin));
			}
			return;
		}

		int pivote = numeros[inicio];
		int izquierda = inicio + 1;
		int derecha = fin;

		if (mostrarPasos) {
			System.out.println(sangria + "Subarreglo actual: " + mostrarRango(numeros, inicio, fin));
			System.out.println(sangria + "Pivote (primer elemento): " + pivote);
		}

		// Los dos índices buscan valores que quedaron en el lado incorrecto del pivote.
		while (izquierda <= derecha) {
			// Avanzamos por los valores que pertenecen al lado izquierdo.
			while (izquierda <= derecha && numeros[izquierda] <= pivote) {
				izquierda++;
			}
			// Retrocedemos por los valores que pertenecen al lado derecho.
			while (izquierda <= derecha && numeros[derecha] > pivote) {
				derecha--;
			}
			// Si ambos índices no se cruzaron, intercambiamos los valores mal ubicados.
			if (izquierda < derecha) {
				intercambiar(numeros, izquierda, derecha);
			}
		}

		// Ubicamos el pivote entre los valores menores o iguales y los valores mayores.
		intercambiar(numeros, inicio, derecha);
		if (mostrarPasos) {
			System.out.println(sangria + "Pivote ubicado: " + numeros[derecha]);
			System.out.println(sangria + "Subarreglo izquierdo: " + mostrarRango(numeros, inicio, derecha - 1));
			System.out.println(sangria + "Subarreglo derecho: " + mostrarRango(numeros, derecha + 1, fin));
		}

		// Dividir y vencer: ordenamos recursivamente las dos partes que dejó la partición.
		quickSort(numeros, inicio, derecha - 1, nivel + 1, mostrarPasos);
		quickSort(numeros, derecha + 1, fin, nivel + 1, mostrarPasos);
	}

	/** Intercambia dos posiciones del arreglo. */
	private static void intercambiar(int[] numeros, int primero, int segundo) {
		int temporal = numeros[primero];
		numeros[primero] = numeros[segundo];
		numeros[segundo] = temporal;
	}

	/** Devuelve como texto los elementos entre los índices indicados. */
	private static String mostrarRango(int[] numeros, int inicio, int fin) {
		if (inicio > fin) {
			return "[]";
		}
		return Arrays.toString(Arrays.copyOfRange(numeros, inicio, fin + 1));
	}

	/** Ejecuta QuickSort sobre una copia y compara el resultado con el esperado. */
	private static void verificarCaso(String descripcion, int[] entrada, int[] esperado) {
		int[] resultado = Arrays.copyOf(entrada, entrada.length);
		quickSort(resultado, 0, resultado.length - 1, 0, false);
		if (!Arrays.equals(resultado, esperado)) {
			throw new AssertionError(descripcion + ": se esperaba " + Arrays.toString(esperado)
					+ " pero se obtuvo " + Arrays.toString(resultado));
		}
		System.out.println("Verificación correcta: " + descripcion + ".");
	}

	/** Lee un entero y vuelve a preguntar ante entradas no numéricas. */
	private static Integer leerEntero(Scanner scanner, String mensaje) {
		while (true) {
			System.out.print(mensaje);
			if (!scanner.hasNext()) {
				return null;
			}
			if (scanner.hasNextInt()) {
				return scanner.nextInt();
			}

			System.out.println("Entrada no válida. Escriba un número entero.");
			scanner.next();
		}
	}

	/** Solicita la cantidad y los valores de un arreglo introducido por el usuario. */
	private static int[] leerArreglo(Scanner scanner) {
		Integer cantidad = leerEntero(scanner, "¿Cuántos números desea ordenar? ");
		while (cantidad != null && cantidad < 0) {
			System.out.println("La cantidad no puede ser negativa.");
			cantidad = leerEntero(scanner, "Ingrese una cantidad igual o mayor que cero: ");
		}
		if (cantidad == null) {
			return null;
		}

		int[] numeros = new int[cantidad];
		for (int i = 0; i < numeros.length; i++) {
			Integer numero = leerEntero(scanner, "Ingrese el número " + (i + 1) + ": ");
			if (numero == null) {
				return null;
			}
			numeros[i] = numero;
		}
		return numeros;
	}

	/** Ejecuta la demostración fija, las pruebas y el menú para datos propios. */
	public static void main(String[] args) {
		int[] numeros = {8, 3, 1, 7, 0, 10, 2};
		int[] esperado = {0, 1, 2, 3, 7, 8, 10};

		System.out.println("=== QuickSort: pivote en el primer elemento ===");
		System.out.println("Arreglo original: " + Arrays.toString(numeros));
		quickSort(numeros, 0, numeros.length - 1, 0, true);
		System.out.println("Arreglo final ordenado: " + Arrays.toString(numeros));
		if (!Arrays.equals(numeros, esperado)) {
			throw new AssertionError("El arreglo de prueba no quedó ordenado correctamente.");
		}
		System.out.println("Verificación correcta: arreglo de prueba.");

		verificarCaso("arreglo vacío", new int[]{}, new int[]{});
		verificarCaso("arreglo unitario", new int[]{5}, new int[]{5});
		verificarCaso("arreglo ya ordenado", new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
		verificarCaso("arreglo en orden inverso", new int[]{4, 3, 2, 1}, new int[]{1, 2, 3, 4});
		verificarCaso("valores repetidos", new int[]{3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 3});

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("\n¿Desea ordenar otro arreglo?");
			Integer opcion = leerEntero(scanner, "Ingrese 1 para sí o 0 para terminar: ");
			while (opcion != null && opcion != 0 && opcion != 1) {
				System.out.println("Opción no válida. Seleccione 1 o 0.");
				opcion = leerEntero(scanner, "Ingrese 1 para sí o 0 para terminar: ");
			}

			if (opcion == null || opcion == 0) {
				System.out.println("Programa finalizado.");
				return;
			}

			int[] arregloUsuario = leerArreglo(scanner);
			if (arregloUsuario == null) {
				System.out.println("No se completó el ingreso del arreglo.");
				return;
			}

			System.out.println("Arreglo ingresado: " + Arrays.toString(arregloUsuario));
			quickSort(arregloUsuario, 0, arregloUsuario.length - 1, 0, true);
			System.out.println("Arreglo ordenado: " + Arrays.toString(arregloUsuario));
		}
	}
}
