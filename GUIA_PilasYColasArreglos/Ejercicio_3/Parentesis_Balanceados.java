/*
 * PROMPT UTILIZADO:
 * Quiero hacer en Java un programa que revise si los paréntesis de una expresión
 * matemática están balanceados. Utiliza una pila implementada con un arreglo para
 * guardar los paréntesis abiertos.
 *
 * Recorre la expresión de izquierda a derecha: apila cada paréntesis de apertura y,
 * ante uno de cierre, retira el último abierto. Si aparece un cierre y la pila está
 * vacía, la expresión no es válida. Al terminar, es válida solo si no quedan aperturas
 * pendientes. Ignora los caracteres que no sean paréntesis.
 *
 * Usa como ejemplos (5 + 3) * (2 + 1), que debe ser válido, y (5 + 3)) * (2 + 1, que
 * debe ser inválido. Incluye comentarios breves que expliquen cuándo se apila y cuándo
 * se retira un paréntesis. Muestra las expresiones y mensajes claros. Mantén la solución
 * sencilla y sin dependencias externas. Comprueba también expresiones vacía, sin
 * paréntesis, con paréntesis anidados, con un cierre sobrante y con una apertura sin
 * cerrar. Después de las pruebas, permite que el usuario ingrese varias expresiones
 * desde la consola hasta que elija salir; informa claramente si cada una es válida.
 * Corrige cualquier error y vuelve a probar la implementación.
 */
package GUIA_PilasYColasArreglos.Ejercicio_3;

import java.util.Scanner;

public class Parentesis_Balanceados {

	private static final class PilaCaracteres {
		private final char[] elementos;
		private int top = -1;

		private PilaCaracteres(int capacidad) {
			elementos = new char[capacidad];
		}

		private void push(char caracter) {
			if (top == elementos.length - 1) {
				throw new IllegalStateException("La pila de aperturas está llena.");
			}
			elementos[++top] = caracter;
		}

		private char pop() {
			if (isEmpty()) {
				throw new IllegalStateException("No hay aperturas pendientes.");
			}
			return elementos[top--];
		}

		private boolean isEmpty() {
			return top == -1;
		}
	}

	/** Devuelve si todos los paréntesis de la expresión están correctamente emparejados. */
	private static boolean estanBalanceados(String expresion) {
		if (expresion == null) {
			throw new IllegalArgumentException("La expresión no puede ser null.");
		}

		PilaCaracteres aperturas = new PilaCaracteres(expresion.length());
		for (int i = 0; i < expresion.length(); i++) {
			char caracter = expresion.charAt(i);
			if (caracter == '(') {
				// Cada apertura queda pendiente hasta encontrar su cierre correspondiente.
				aperturas.push(caracter);
			} else if (caracter == ')') {
				// Un cierre sin apertura previa no puede emparejarse.
				if (aperturas.isEmpty()) {
					return false;
				}
				aperturas.pop();
			}
		}

		// Si queda alguna apertura en la pila, la expresión no está balanceada.
		return aperturas.isEmpty();
	}

	private static void comprobarCaso(String descripcion, String expresion, boolean esperado) {
		boolean resultado = estanBalanceados(expresion);
		if (resultado != esperado) {
			throw new AssertionError("Falló el caso " + descripcion + ": se esperaba "
					+ esperado + " y se obtuvo " + resultado + ".");
		}
		System.out.println("[Correcto] " + descripcion + ".");
	}

	private static void mostrarResultado(String expresion) {
		boolean balanceados = estanBalanceados(expresion);
		System.out.println("Expresión: " + (expresion.isEmpty() ? "(vacía)" : expresion));
		System.out.println("Resultado: " + (balanceados ? "Válida" : "Inválida") + "\n");
	}

	private static void interactuarConUsuario(Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("--- Validador interactivo ---");
			System.out.println("1. Revisar una expresión");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");
			if (!scanner.hasNextLine()) {
				break;
			}

			String opcion = scanner.nextLine().trim();
			if (opcion.equals("0")) {
				continuar = false;
			} else if (opcion.equals("1")) {
				System.out.print("Escriba la expresión: ");
				if (!scanner.hasNextLine()) {
					continuar = false;
					continue;
				}
				mostrarResultado(scanner.nextLine());
			} else {
				System.out.println("Opción no válida. Escriba 1 para validar o 0 para salir.\n");
			}
		}
		System.out.println("Validador finalizado.");
	}

	public static void main(String[] args) {
		String expresionValida = "(5 + 3) * (2 + 1)";
		String expresionInvalida = "(5 + 3)) * (2 + 1";

		System.out.println("============================================");
		System.out.println(" VALIDACIÓN DE PARÉNTESIS BALANCEADOS");
		System.out.println("============================================\n");
		mostrarResultado(expresionValida);
		mostrarResultado(expresionInvalida);

		System.out.println("--- Verificaciones adicionales ---");
		comprobarCaso("expresión vacía", "", true);
		comprobarCaso("expresión sin paréntesis", "5 + 3 * 2", true);
		comprobarCaso("paréntesis anidados", "((5 + 3) * (2 + 1))", true);
		comprobarCaso("cierre sobrante", "(5 + 3))", false);
		comprobarCaso("apertura sin cerrar", "(5 + 3", false);

		System.out.println("\nAhora puedes comprobar tus propias expresiones.");
		try (Scanner scanner = new Scanner(System.in)) {
			interactuarConUsuario(scanner);
		}
	}
}