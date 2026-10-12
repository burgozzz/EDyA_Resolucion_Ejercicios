/*
 * PROMPT:
 * Implementa en Java Selection Sort para ordenar alfabéticamente un arreglo de nombres.
 * Usa como prueba {"Lucia", "Ana", "Pedro", "Juan"}. Compara los nombres como texto,
 * carácter por carácter, sin distinguir mayúsculas y minúsculas, y explica en comentarios
 * que la comparación entre Strings es distinta de comparar valores numéricos.
 * Muestra el arreglo original y el ordenado. Agrega comentarios breves sobre la búsqueda
 * del menor elemento restante, el intercambio y el avance de cada pasada. Mantén la solución
 * sencilla, sin dependencias externas, e interactúa con el usuario para solicitar nombres.
 * Verifica también un arreglo vacío, uno con un nombre y nombres repetidos.
 */
package GUIA_Ordenamiento.Ejercicio_4;

import java.util.Arrays;
import java.util.Scanner;

public class Ordenamientos_Nombres {

	public static void selectionSort(String[] nombres) {
		for (int i = 0; i < nombres.length - 1; i++) {
			int indiceMenor = i;

			// Buscamos el menor nombre en la parte que todavía no está ordenada.
			for (int j = i + 1; j < nombres.length; j++) {
				// La comparación es carácter por carácter, no numérica.
				// Ignoramos mayúsculas y minúsculas para mantener el orden alfabético.
				if (nombres[j].compareToIgnoreCase(nombres[indiceMenor]) < 0) {
					indiceMenor = j;
				}
			}

			// Intercambiamos el menor encontrado con la primera posición restante.
			String temporal = nombres[i];
			nombres[i] = nombres[indiceMenor];
			nombres[indiceMenor] = temporal;
			// Al terminar la pasada, avanzamos a la siguiente posición restante.
		}
	}

	private static void verificarCaso(String[] entrada, String[] esperado) {
		selectionSort(entrada);
		if (!Arrays.equals(entrada, esperado)) {
			throw new AssertionError("Se esperaba " + Arrays.toString(esperado)
					+ " pero se obtuvo " + Arrays.toString(entrada));
		}
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("¿Cuántos nombres desea ordenar? ");
			if (!scanner.hasNextInt()) {
				System.out.println("Ingrese una cantidad válida.");
				return;
			}

			int cantidad = scanner.nextInt();
			scanner.nextLine();
			if (cantidad < 0) {
				System.out.println("La cantidad no puede ser negativa.");
				return;
			}

			String[] nombresIngresados = new String[cantidad];
			for (int i = 0; i < nombresIngresados.length; i++) {
				System.out.print("Ingrese el nombre " + (i + 1) + ": ");
				nombresIngresados[i] = scanner.nextLine();
			}

			System.out.println("Arreglo original: " + Arrays.toString(nombresIngresados));
			selectionSort(nombresIngresados);
			System.out.println("Arreglo ordenado: " + Arrays.toString(nombresIngresados));
		}

		String[] nombresPrueba = {"Lucia", "Ana", "Pedro", "Juan"};
		System.out.println("\nEjemplo solicitado:");
		System.out.println("Arreglo original: " + Arrays.toString(nombresPrueba));
		selectionSort(nombresPrueba);
		System.out.println("Arreglo ordenado: " + Arrays.toString(nombresPrueba));

		verificarCaso(new String[]{}, new String[]{});
		verificarCaso(new String[]{"Lucia"}, new String[]{"Lucia"});
		verificarCaso(new String[]{"Ana", "Pedro", "Ana"},
				new String[]{"Ana", "Ana", "Pedro"});
		System.out.println("Casos vacío, un nombre y nombres repetidos: correctos.");
	}
}
