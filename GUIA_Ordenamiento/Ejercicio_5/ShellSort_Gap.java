/*
 * PROMPT UTILIZADO:
 * Implementa en Java el ejercicio 5 de la guía: ShellSort explicando los gaps.
 * El programa debe ordenar el arreglo {35, 12, 43, 8, 51, 7, 19, 3} usando ShellSort.
 * Utiliza una secuencia de gaps que comience en la mitad del tamaño del arreglo y se
 * reduzca a la mitad en cada etapa hasta llegar a 1.
 * Muestra el arreglo original, el valor del gap en cada etapa, el estado del arreglo
 * después de cada pasada completa correspondiente a ese gap y el arreglo ordenado.
 * Incluye comentarios breves que expliquen qué representa el gap, cómo se comparan y
 * desplazan los elementos separados por esa distancia, y por qué se termina con gap 1.
 * Explica que ShellSort puede mejorar a Insertion Sort porque los gaps permiten mover
 * elementos a distancias mayores y dejar el arreglo parcialmente ordenado antes de la
 * pasada final. No afirmes que siempre será más rápido: el rendimiento depende de los
 * datos y de la secuencia de gaps.
 * Mantén la implementación sencilla, adecuada para un ejercicio introductorio y sin
 * dependencias externas. Verifica que el arreglo de prueba termine ordenado y comprueba
 * también un arreglo vacío, uno con un elemento, uno ya ordenado y otro con valores
 * repetidos. Si una verificación falla o hay errores de compilación, corrige la
 * implementación y vuelve a probarla.
 * La salida debe permitir seguir las etapas del algoritmo y entender cómo los gaps
 * afectan el ordenamiento.
 */
package GUIA_Ordenamiento.Ejercicio_5;

import java.util.Arrays;
import java.util.Scanner;

public class ShellSort_Gap {

	private static void shellSort(int[] numeros, boolean mostrarPasos) {
		int numeroPasada;

		// El gap separa las posiciones que se comparan y desplazan en cada inserción.
		// Se termina en gap 1 para ordenar también los elementos adyacentes.
		for (int gap = numeros.length / 2; gap > 0; gap /= 2) {
			if (mostrarPasos) {
				System.out.println("\nGap = " + gap);
			}

			numeroPasada = 1;
			for (int i = gap; i < numeros.length; i++) {
				int valorActual = numeros[i];
				int posicion = i;

				// Desplazamos hacia la derecha los mayores separados por el gap.
				while (posicion >= gap && numeros[posicion - gap] > valorActual) {
					numeros[posicion] = numeros[posicion - gap];
					posicion -= gap;
				}
				numeros[posicion] = valorActual;

				if (mostrarPasos) {
					System.out.println("Pasada " + numeroPasada++ + ": " + Arrays.toString(numeros));
				}
			}
		}
	}

	private static void verificarCaso(String descripcion, int[] numeros, int[] esperado) {
		shellSort(numeros, false);
		if (!Arrays.equals(numeros, esperado)) {
			throw new AssertionError(descripcion + ": se esperaba " + Arrays.toString(esperado)
					+ " pero se obtuvo " + Arrays.toString(numeros));
		}
		System.out.println("Verificación correcta: " + descripcion + ".");
	}

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

	public static void main(String[] args) {
		int[] numeros = {35, 12, 43, 8, 51, 7, 19, 3};
		int[] esperado = {3, 7, 8, 12, 19, 35, 43, 51};

		System.out.println("Arreglo original: " + Arrays.toString(numeros));
		shellSort(numeros, true);
		System.out.println("\nArreglo ordenado: " + Arrays.toString(numeros));
		if (!Arrays.equals(numeros, esperado)) {
			throw new AssertionError("El arreglo de prueba no quedó ordenado correctamente.");
		}

		System.out.println("\nLos gaps permiten mover elementos a mayor distancia y dejar el arreglo"
				+ " parcialmente ordenado antes del gap 1. Ese último gap ordena elementos"
				+ " adyacentes; la mejora frente a Insertion Sort depende de los datos y los gaps.");

		verificarCaso("arreglo vacío", new int[]{}, new int[]{});
		verificarCaso("un elemento", new int[]{5}, new int[]{5});
		verificarCaso("arreglo ya ordenado", new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
		verificarCaso("valores repetidos", new int[]{4, 2, 4, 1, 2}, new int[]{1, 2, 2, 4, 4});

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("\n¿Desea probar con un arreglo propio?");
			Integer opcion = leerEntero(scanner, "Ingrese 1 para sí o 0 para terminar: ");
			while (opcion != null && opcion != 0 && opcion != 1) {
				System.out.println("Opción no válida.");
				opcion = leerEntero(scanner, "Ingrese 1 para sí o 0 para terminar: ");
			}

			if (opcion == null || opcion == 0) {
				System.out.println("Programa finalizado.");
				return;
			}

			int[] arregloPropio = leerArreglo(scanner);
			if (arregloPropio == null) {
				System.out.println("No se completó el ingreso del arreglo.");
				return;
			}

			System.out.println("Arreglo ingresado: " + Arrays.toString(arregloPropio));
			shellSort(arregloPropio, true);
			System.out.println("Arreglo ordenado: " + Arrays.toString(arregloPropio));
		}
	}
}
