package GUIA_Analisis_Algoritmos.Ejercicio_2;

import java.util.Scanner;

/**
 * Realiza búsquedas secuenciales de valores enteros en vectores desordenados.
 */
public class Buscar_Elemento_Desordenado {
	/**
	 * Busca la primera aparición del valor indicado recorriendo el vector desde el inicio.
	 *
	 * @param vector vector desordenado en el que se realizará la búsqueda
	 * @param valorBuscado valor entero que se desea encontrar
	 * @return el resultado con el estado de la búsqueda, el índice de la primera coincidencia
	 *         (o {@code -1} si no se encontró) y la cantidad de posiciones inspeccionadas
	 * @throws IllegalArgumentException si {@code valorBuscado} o {@code vector} son {@code null}
	 */
	public static ResultadoBusqueda buscarElemento(int[] vector, Integer valorBuscado) {
		if (valorBuscado == null) {
			throw new IllegalArgumentException("No se permiten valores nulos.");
		}
		if (vector == null) {
			throw new IllegalArgumentException("El vector no puede ser nulo.");
		}

		for (int indice = 0; indice < vector.length; indice++) {
			if (vector[indice] == valorBuscado) {
				return new ResultadoBusqueda(true, indice, indice + 1);
			}
		}

		return new ResultadoBusqueda(false, -1, vector.length);
	}

	/**
	 * Ejecuta la interacción por consola para ingresar el vector y el valor que se buscará.
	 * Muestra el resultado de la búsqueda o el aviso correspondiente si el valor es nulo.
	 * No devuelve un valor.
	 *
	 * @param args argumentos de línea de comandos; no se utilizan
	 * @throws java.util.NoSuchElementException si la entrada termina antes de completar los datos
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

			Integer valorBuscado = leerValorBuscado(entrada);
			try {
				ResultadoBusqueda resultado = buscarElemento(vector, valorBuscado);
				System.out.println(resultado.getMensaje());
			} catch (IllegalArgumentException excepcion) {
				System.out.println(excepcion.getMessage());
			}
		}
	}

	/**
	 * Solicita un entero y repite la lectura hasta recibir una entrada válida.
	 *
	 * @param entrada lector de la entrada de consola
	 * @param mensaje indicación que se muestra antes de leer el valor
	 * @return entero ingresado por el usuario
	 * @throws java.util.NoSuchElementException si la entrada termina antes de recibir un entero válido
	 */
	private static int leerEntero(Scanner entrada, String mensaje) {
		while (true) {
			System.out.print(mensaje);
			try {
				return Integer.parseInt(entrada.nextLine().trim());
			} catch (NumberFormatException excepcion) {
				System.out.println("Entrada inválida. Ingrese un número entero.");
			}
		}
	}

	/**
	 * Solicita el valor buscado y permite ingresar un entero o la palabra {@code null}.
	 *
	 * @param entrada lector de la entrada de consola
	 * @return entero ingresado o {@code null} si el usuario escribe esa palabra
	 * @throws java.util.NoSuchElementException si la entrada termina antes de recibir un valor válido
	 */
	private static Integer leerValorBuscado(Scanner entrada) {
		while (true) {
			System.out.print("Ingrese el valor a buscar (o 'null'): ");
			String valor = entrada.nextLine().trim();
			if (valor.equalsIgnoreCase("null")) {
				return null;
			}

			try {
				return Integer.valueOf(valor);
			} catch (NumberFormatException excepcion) {
				System.out.println("Entrada inválida. Ingrese un entero o 'null'.");
			}
		}
	}

	/**
	 * Contiene el resultado de una búsqueda y las posiciones examinadas para obtenerlo.
	 */
	public static final class ResultadoBusqueda {
		private final boolean encontrado;
		private final int indice;
		private final int posicionesRecorridas;

		/**
		 * Crea un resultado de búsqueda.
		 *
		 * @param encontrado {@code true} si se encontró el valor
		 * @param indice índice de la primera coincidencia o {@code -1} si no hubo coincidencia
		 * @param posicionesRecorridas cantidad de posiciones inspeccionadas
		 */
		private ResultadoBusqueda(boolean encontrado, int indice, int posicionesRecorridas) {
			this.encontrado = encontrado;
			this.indice = indice;
			this.posicionesRecorridas = posicionesRecorridas;
		}

		/**
		 * Indica si se encontró el valor buscado.
		 *
		 * @return {@code true} cuando hubo una coincidencia; {@code false} en caso contrario
		 */
		public boolean isEncontrado() {
			return encontrado;
		}

		/**
		 * Devuelve el índice de la primera coincidencia.
		 *
		 * @return índice basado en cero, o {@code -1} si el valor no se encontró
		 */
		public int getIndice() {
			return indice;
		}

		/**
		 * Devuelve cuántas posiciones del vector se inspeccionaron.
		 *
		 * @return cantidad de posiciones examinadas, incluida la coincidencia si existe
		 */
		public int getPosicionesRecorridas() {
			return posicionesRecorridas;
		}

		/**
		 * Genera un mensaje con el resultado de la búsqueda y el conteo del recorrido.
		 *
		 * @return mensaje que indica si se encontró el valor y cuántas posiciones se recorrieron
		 */
		public String getMensaje() {
			if (!encontrado) {
				return "El valor no se encuentra en el vector. Posiciones recorridas: "
						+ posicionesRecorridas;
			}

			return "Valor encontrado en el índice " + indice
					+ ". Posiciones recorridas: " + posicionesRecorridas;
		}
	}
}
