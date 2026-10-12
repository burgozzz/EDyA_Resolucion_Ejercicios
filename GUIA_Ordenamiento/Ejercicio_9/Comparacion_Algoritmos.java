/*
 * PROMPT UTILIZADO:
 * Implementa en Java un programa que compare Bubble Sort, Selection Sort e Insertion Sort.
 * Usa como datos de prueba {8, 3, 5, 1, 4, 2}. Cada algoritmo debe ordenar una copia
 * independiente del mismo arreglo de menor a mayor y mostrar el resultado, la cantidad
 * de comparaciones y las operaciones realizadas.
 *
 * Cuenta en Bubble Sort las comparaciones entre vecinos y los intercambios realizados.
 * En Selection Sort cuenta las comparaciones al buscar el menor restante y solo los
 * intercambios reales. En Insertion Sort cuenta las comparaciones de la clave con los
 * elementos anteriores y los desplazamientos hacia la derecha, sin contar la inserción
 * final de la clave como desplazamiento.
 *
 * Incluye comentarios breves que expliquen la estrategia de cada algoritmo, presenta
 * los resultados con títulos claros y mantén la solución sencilla, sin dependencias
 * externas. Verifica que los tres resultados sean {1, 2, 3, 4, 5, 8}, agrega ciertas 
 * verificaciones que creas necesarias
 */
package GUIA_Ordenamiento.Ejercicio_9;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Compara tres algoritmos de ordenamiento usando el mismo arreglo de entrada y
 * mostrando sus comparaciones e intercambios o desplazamientos.
 */
public class Comparacion_Algoritmos {
	/** Guarda las comparaciones y la operación característica de un algoritmo. */
	private static class Estadisticas {
		private int comparaciones;
		private int operaciones;
	}

	/**
	 * Ordena mediante comparaciones entre vecinos. Cuenta cada comparación y cada
	 * intercambio; termina antes si una pasada completa no requiere intercambios.
	 */
	private static Estadisticas bubbleSort(int[] numeros) {
		Estadisticas estadisticas = new Estadisticas();

		// Bubble Sort compara vecinos y lleva los mayores hacia el final del arreglo.
		for (int i = 0; i < numeros.length - 1; i++) {
			boolean huboIntercambio = false;
			for (int j = 0; j < numeros.length - 1 - i; j++) {
				estadisticas.comparaciones++;
				if (numeros[j] > numeros[j + 1]) {
					intercambiar(numeros, j, j + 1);
					estadisticas.operaciones++;
					huboIntercambio = true;
				}
			}
			if (!huboIntercambio) {
				break;
			}
		}
		return estadisticas;
	}

	/**
	 * Busca el menor elemento de la sección restante y lo coloca en su posición.
	 * Cuenta las comparaciones de búsqueda y solo los intercambios que se realizan.
	 */
	private static Estadisticas selectionSort(int[] numeros) {
		Estadisticas estadisticas = new Estadisticas();

		// Selection Sort busca el menor de la parte restante y lo coloca en su posición.
		for (int i = 0; i < numeros.length - 1; i++) {
			int indiceMenor = i;
			for (int j = i + 1; j < numeros.length; j++) {
				estadisticas.comparaciones++;
				if (numeros[j] < numeros[indiceMenor]) {
					indiceMenor = j;
				}
			}

			if (indiceMenor != i) {
				intercambiar(numeros, i, indiceMenor);
				estadisticas.operaciones++;
			}
		}
		return estadisticas;
	}

	/**
	 * Inserta cada clave en la parte previamente ordenada. Cuenta las comparaciones
	 * entre la clave y elementos anteriores, y los desplazamientos hacia la derecha.
	 */
	private static Estadisticas insertionSort(int[] numeros) {
		Estadisticas estadisticas = new Estadisticas();

		// Insertion Sort inserta cada clave en la posición correcta de la parte ordenada.
		for (int i = 1; i < numeros.length; i++) {
			int clave = numeros[i];
			int j = i - 1;

			while (j >= 0) {
				// Se cuenta cada comparación real entre la clave y un elemento anterior.
				estadisticas.comparaciones++;
				if (numeros[j] <= clave) {
					break;
				}
				numeros[j + 1] = numeros[j];
				estadisticas.operaciones++;
				j--;
			}
			numeros[j + 1] = clave;
		}
		return estadisticas;
	}

	/** Intercambia dos elementos de un arreglo. */
	private static void intercambiar(int[] numeros, int primero, int segundo) {
		int temporal = numeros[primero];
		numeros[primero] = numeros[segundo];
		numeros[segundo] = temporal;
	}

	private static void mostrarResultado(String nombre, String nombreOperacion,
			int[] numeros, Estadisticas estadisticas) {
		System.out.println("\n" + nombre);
		System.out.println("Arreglo ordenado: " + Arrays.toString(numeros));
		System.out.println("Comparaciones: " + estadisticas.comparaciones);
		System.out.println(nombreOperacion + ": " + estadisticas.operaciones);
	}

	/**
	 * Crea copias independientes para comparar los tres métodos sobre la misma entrada.
	 * Si se entrega un resultado esperado, también lo verifica; de lo contrario comprueba
	 * que los tres resultados estén en orden ascendente.
	 */
	private static void compararYMostrar(String titulo, int[] entrada, int[] esperado) {
		int[] burbuja = Arrays.copyOf(entrada, entrada.length);
		int[] seleccion = Arrays.copyOf(entrada, entrada.length);
		int[] insercion = Arrays.copyOf(entrada, entrada.length);

		Estadisticas estadisticasBurbuja = bubbleSort(burbuja);
		Estadisticas estadisticasSeleccion = selectionSort(seleccion);
		Estadisticas estadisticasInsercion = insertionSort(insercion);

		System.out.println("\n--- " + titulo + " ---");
		System.out.println("Arreglo original: " + Arrays.toString(entrada));
		mostrarResultado("Bubble Sort", "Intercambios", burbuja, estadisticasBurbuja);
		mostrarResultado("Selection Sort", "Intercambios", seleccion, estadisticasSeleccion);
		mostrarResultado("Insertion Sort", "Desplazamientos", insercion, estadisticasInsercion);

		if (esperado != null) {
			if (!Arrays.equals(burbuja, esperado)
					|| !Arrays.equals(seleccion, esperado)
					|| !Arrays.equals(insercion, esperado)) {
				throw new AssertionError("Los algoritmos no produjeron el resultado esperado.");
			}
			System.out.println("\n[Correcto] Los tres resultados coinciden con el esperado.");
		} else if (!estaOrdenado(burbuja) || !estaOrdenado(seleccion) || !estaOrdenado(insercion)) {
			throw new AssertionError("Al menos un algoritmo no ordenó el arreglo ingresado.");
		} else {
			System.out.println("\n[Correcto] Los tres resultados están ordenados.");
		}
	}

	/** Devuelve true si cada elemento es menor o igual que el siguiente. */
	private static boolean estaOrdenado(int[] numeros) {
		for (int i = 1; i < numeros.length; i++) {
			if (numeros[i - 1] > numeros[i]) {
				return false;
			}
		}
		return true;
	}

	/** Lee un entero y vuelve a solicitarlo si la entrada no es numérica. */
	private static Integer leerEntero(Scanner scanner, String mensaje) {
		while (true) {
			System.out.print(mensaje);
			if (!scanner.hasNext()) {
				return null;
			}
			if (scanner.hasNextInt()) {
				return scanner.nextInt();
			}
			System.out.println("Entrada no válida. Ingrese un número entero.");
			scanner.next();
		}
	}

	/** Solicita una cantidad no negativa y luego lee los enteros del arreglo. */
	private static int[] leerArreglo(Scanner scanner) {
		Integer cantidad = leerEntero(scanner, "Cantidad de números (0 o más): ");
		while (cantidad != null && cantidad < 0) {
			System.out.println("La cantidad no puede ser negativa.");
			cantidad = leerEntero(scanner, "Ingrese una cantidad válida: ");
		}
		if (cantidad == null) {
			return null;
		}

		int[] numeros = new int[cantidad];
		for (int i = 0; i < numeros.length; i++) {
			Integer numero = leerEntero(scanner, "Número " + (i + 1) + ": ");
			if (numero == null) {
				return null;
			}
			numeros[i] = numero;
		}
		return numeros;
	}

	/** Verifica que los tres algoritmos ordenen una copia de un caso de prueba. */
	private static void verificarCaso(String descripcion, int[] entrada, int[] esperado) {
		int[] burbuja = Arrays.copyOf(entrada, entrada.length);
		int[] seleccion = Arrays.copyOf(entrada, entrada.length);
		int[] insercion = Arrays.copyOf(entrada, entrada.length);

		bubbleSort(burbuja);
		selectionSort(seleccion);
		insertionSort(insercion);

		if (!Arrays.equals(burbuja, esperado)
				|| !Arrays.equals(seleccion, esperado)
				|| !Arrays.equals(insercion, esperado)) {
			throw new AssertionError("Falló la verificación: " + descripcion);
		}
		System.out.println("[Correcto] " + descripcion + " con los tres algoritmos.");
	}

	/** Ejecuta la comparación fija, sus pruebas automáticas y la opción de entrada propia. */
	public static void main(String[] args) {
		int[] numeros = {8, 3, 5, 1, 4, 2};
		int[] esperado = {1, 2, 3, 4, 5, 8};

		System.out.println("============================================");
		System.out.println(" COMPARACIÓN DE ALGORITMOS DE ORDENAMIENTO");
		System.out.println("============================================");
		compararYMostrar("Ejemplo", numeros, esperado);

		System.out.println("\nLos métodos usan estrategias distintas: Bubble compara vecinos; Selection"
				+ " busca el menor restante; Insertion inserta cada clave en la parte ordenada.");

		System.out.println("\n--- Verificaciones adicionales ---");
		verificarCaso("arreglo vacío", new int[]{}, new int[]{});
		verificarCaso("arreglo unitario", new int[]{5}, new int[]{5});
		verificarCaso("arreglo ya ordenado", new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
		verificarCaso("arreglo en orden inverso", new int[]{4, 3, 2, 1}, new int[]{1, 2, 3, 4});
		verificarCaso("valores repetidos", new int[]{3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 3});

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("\n--- Comparación personalizada ---");
			System.out.println("1. Ingresar otro arreglo");
			System.out.println("0. Finalizar");
			Integer opcion = leerEntero(scanner, "Seleccione una opción: ");
			while (opcion != null && opcion != 0 && opcion != 1) {
				System.out.println("Opción no válida. Elija 1 o 0.");
				opcion = leerEntero(scanner, "Seleccione una opción: ");
			}

			if (opcion == null || opcion == 0) {
				System.out.println("Programa finalizado.");
				return;
			}

			int[] arregloUsuario = leerArreglo(scanner);
			if (arregloUsuario == null) {
				System.out.println("No se completó la lectura del arreglo.");
				return;
			}
			compararYMostrar("Resultados para el arreglo ingresado", arregloUsuario, null);
		}
	}
}
