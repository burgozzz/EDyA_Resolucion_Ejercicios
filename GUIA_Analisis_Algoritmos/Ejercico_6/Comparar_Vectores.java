package GUIA_Analisis_Algoritmos.Ejercico_6;

import java.util.Scanner;

/**
 * Compara dos vectores de enteros y ofrece una interacción para ingresarlos por consola.
 * La comparación considera tanto la longitud como los valores en sus posiciones respectivas.
 */
public class Comparar_Vectores {

	/**
	 * Determina si dos vectores contienen los mismos valores en el mismo orden.
	 * La comparación termina al encontrar la primera diferencia.
	 *
	 * @param primerVector primer vector que se desea comparar
	 * @param segundoVector segundo vector que se desea comparar
	 * @return {@code true} si ambos vectores tienen la misma longitud y contenido; {@code false} en caso contrario
	 * @throws IllegalArgumentException si alguno de los vectores es {@code null}
	 */
	public static boolean sonIguales(int[] primerVector, int[] segundoVector) {
		if (primerVector == null || segundoVector == null) {
			throw new IllegalArgumentException("Los vectores no pueden ser nulos.");
		}

		if (primerVector.length != segundoVector.length) {
			return false;
		}

		for (int indice = 0; indice < primerVector.length; indice++) {
			if (primerVector[indice] != segundoVector[indice]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Coordina la lectura de ambos vectores y muestra el resultado de la comparación.
	 * Si las longitudes son distintas, informa la diferencia sin solicitar los elementos del segundo vector.
	 *
	 * @param args argumentos de línea de comandos; no se utilizan
	 * @throws java.util.NoSuchElementException si la entrada estándar se agota durante la lectura
	 */
	public static void main(String[] args) {
		try (Scanner entrada = new Scanner(System.in)) {
			int[] primerVector = leerVector(entrada, "primer");
			int cantidadSegundo = leerCantidad(entrada, "segundo");

			if (primerVector.length != cantidadSegundo) {
				System.out.println("Los vectores no pueden ser iguales: tienen tamaños diferentes.");
				return;
			}

			int[] segundoVector = leerElementos(entrada, cantidadSegundo, "segundo");

			if (sonIguales(primerVector, segundoVector)) {
				System.out.println("Los vectores son iguales.");
			} else {
				System.out.println("Los vectores son diferentes.");
			}
		}
	}

	/**
	 * Solicita el tamaño y los elementos de un vector.
	 *
	 * @param entrada lector asociado a la entrada de consola
	 * @param nombreVector nombre usado para identificar el vector en los mensajes
	 * @return vector con los enteros ingresados
	 * @throws java.util.NoSuchElementException si la entrada estándar se agota durante la lectura
	 */
	private static int[] leerVector(Scanner entrada, String nombreVector) {
		int cantidad = leerCantidad(entrada, nombreVector);
		return leerElementos(entrada, cantidad, nombreVector);
	}

	/**
	 * Solicita una cantidad de elementos y vuelve a pedirla mientras sea negativa.
	 *
	 * @param entrada lector asociado a la entrada de consola
	 * @param nombreVector nombre usado para identificar el vector en el mensaje
	 * @return una cantidad mayor o igual que cero
	 * @throws java.util.NoSuchElementException si la entrada estándar se agota durante la lectura
	 */
	private static int leerCantidad(Scanner entrada, String nombreVector) {
		int cantidad;
		do {
			cantidad = leerEntero(entrada,
					"Ingrese la cantidad de elementos del " + nombreVector + " vector: ");
			if (cantidad < 0) {
				System.out.println("La cantidad no puede ser negativa.");
			}
		} while (cantidad < 0);

		return cantidad;
	}

	/**
	 * Lee la cantidad indicada de valores y los almacena en un nuevo vector.
	 *
	 * @param entrada lector asociado a la entrada de consola
	 * @param cantidad número de elementos que se deben leer
	 * @param nombreVector nombre usado para identificar cada solicitud
	 * @return vector de longitud {@code cantidad} con los valores ingresados
	 * @throws java.util.NoSuchElementException si la entrada estándar se agota antes de completar la lectura
	 */
	private static int[] leerElementos(Scanner entrada, int cantidad, String nombreVector) {
		int[] vector = new int[cantidad];
		for (int indice = 0; indice < vector.length; indice++) {
			vector[indice] = leerEntero(entrada,
					"Ingrese el elemento " + (indice + 1) + " del " + nombreVector + " vector: ");
		}

		return vector;
	}

	/**
	 * Solicita un entero y repite la solicitud cuando el texto ingresado no tiene formato entero.
	 *
	 * @param entrada lector asociado a la entrada de consola
	 * @param mensaje indicación que se muestra antes de leer el valor
	 * @return entero ingresado por el usuario
	 * @throws java.util.NoSuchElementException si la entrada estándar se agota durante la lectura
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
}
