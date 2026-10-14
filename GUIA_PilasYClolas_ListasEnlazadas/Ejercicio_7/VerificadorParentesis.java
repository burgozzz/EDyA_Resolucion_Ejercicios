/*
 * PROMPT INICIAL UTILIZADO
 *
 * Desarrolla un codigo en java que permita la verificacion de parentesis
 *
 * 1. Estructura: Pila enlazada de caracteres (Character).
 * 2. Por qué corresponde usar pila y funcionamiento: Se usa una pila para guardar los paréntesis de
 *    apertura a medida que se lee la expresión. Al encontrar un paréntesis de cierre, se desapila
 *    el último paréntesis de apertura guardado (LIFO). Si los tipos coinciden y la pila queda vacía
 *    al final, la expresión está balanceada.
 * 3. Operaciones del algoritmo: Leer string carácter por carácter, apilar aperturas, desapilar y
 *    validar con cierres.
 * 4. Pruebas: Validar ejemplos válidos: "(2 + 3) * (5 - 1)", "((a + b) * c)". Validar ejemplos
 *    inválidos: "(2 + 3", "())(".
 * 5. Comentarios: Incluye este prompt como comentario inicial.
 *
 * 
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Uso una PILA enlazada de Character. El problema de los paréntesis balanceados encaja con LIFO: el
 * ÚLTIMO paréntesis que se abre es el PRIMERO que debe cerrarse. Al leer la expresión guardo las
 * aperturas en la pila; cuando aparece un cierre, el que debe hacerle juego es justamente el que
 * está en el TOPE (el último abierto). Una cola (FIFO) no serviría: cerraría con el primero
 * abierto, que no es lo que pide la sintaxis. La lista enlazada evita tamaño fijo.
 *
 * CÓMO FUNCIONA (ALGORITMO)
 * - Recorro la expresión carácter por carácter.
 * - Si el carácter es una APERTURA ( '(', '[' o '{' ): la apilo.
 * - Si es un CIERRE ( ')', ']' o '}' ):
 *     * Si la pila está VACÍA, hay un cierre sin apertura -> NO balanceada.
 *     * Si no, desapilo la última apertura y verifico que COINCIDA con el tipo del cierre.
 *       Si no coinciden -> NO balanceada.
 * - Los demás caracteres (letras, números, operadores, espacios) se ignoran.
 * - Al terminar: la expresión es balanceada SOLO si la pila quedó VACÍA. Si sobraron aperturas,
 *   hay un paréntesis sin cerrar -> NO balanceada.
 *
 * OPERACIONES DEL ALGORITMO: estaBalanceada(String); internamente apila aperturas, desapila al
 * encontrar cierres y valida tipos. Incluye una versión con TRAZA paso a paso para ver el proceso.
 *
 * CASOS ESPECIALES: cadena vacía -> balanceada (true); solo cierres -> false; solo aperturas ->
 * false; cierres cruzados como "([)]" -> false; null -> true (no hay nada que balancear). Al
 * desapilar una pila vacía se lanza EmptyStackException; en el algoritmo esa situación se detecta
 * ANTES con estaVacia() y se traduce a "no balanceada", sin dejar que la excepción escape.
 *
 * COMPLEJIDAD: O(n) en tiempo (una sola pasada por la expresión, y apilar/desapilar son O(1)); O(n)
 * en espacio en el peor caso (todos los caracteres son aperturas). Como buena práctica, se detiene
 * en cuanto detecta un fallo.
 *
 * PRUEBAS: un main con aserciones que valida los ejemplos pedidos ("(2 + 3) * (5 - 1)",
 * "((a + b) * c)" como válidos y "(2 + 3", "())(" como inválidos), más otros casos de borde
 * (vacíos, corchetes y llaves, cierres cruzados).
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_7;

import java.util.EmptyStackException;

/**
 * Verificador de paréntesis (y corchetes/llaves) balanceados usando una PILA enlazada de
 * {@link Character}.
 *
 * <p>El tipo de la pila es {@code Character} (el objeto envoltorio de {@code char}), porque las
 * estructuras de Java trabajan con objetos y Java hace autoboxing automáticamente.</p>
 */
public class VerificadorParentesis {

	/**
	 * Pila enlazada simple de caracteres. Es {@code static} porque no depende de la instancia del
	 * verificador.
	 *
	 * <p>Es una implementación mínima y autocontenida: solo lo necesario para el algoritmo
	 * (apilar, desapilar, estaVacia).</p>
	 */
	private static final class PilaCaracteres {

		/** Nodo de la pila: un Character y la referencia al siguiente. */
		private static final class Nodo {
			private final Character dato;
			private Nodo siguiente;

			private Nodo(Character dato) {
				this.dato = dato;
			}
		}

		/** Tope de la pila. Es null cuando está vacía. */
		private Nodo tope;

		/** Cantidad de elementos apilados. */
		private int size;

		/**
		 * Apila un carácter (push). O(1): el nuevo nodo pasa a ser el tope.
		 *
		 * @param dato carácter a apilar.
		 */
		private void apilar(Character dato) {
			Nodo nuevo = new Nodo(dato);
			nuevo.siguiente = tope;
			tope = nuevo;
			size++;
		}

		/**
		 * Desapila el tope (pop). O(1): solo mueve el tope al siguiente.
		 *
		 * @return el carácter que estaba en el tope.
		 * @throws EmptyStackException si la pila está vacía.
		 */
		private Character desapilar() {
			if (estaVacia()) {
				throw new EmptyStackException();
			}
			Character dato = tope.dato;
			tope = tope.siguiente;
			size--;
			return dato;
		}

		/**
		 * Indica si la pila está vacía. O(1).
		 *
		 * @return {@code true} si no hay elementos.
		 */
		private boolean estaVacia() {
			return tope == null;
		}

		/** Devuelve la cantidad de elementos apilados. */
		private int getSize() {
			return size;
		}
	}

	/**
	 * Verifica si la expresión tiene los paréntesis, corchetes y llaves balanceados.
	 *
	 * <p>Complejidad O(n): una sola pasada por la expresión.</p>
	 *
	 * @param expresion texto a analizar (puede contener cualquier carácter).
	 * @return {@code true} si cada apertura tiene su cierre correspondiente en el orden correcto.
	 */
	public static boolean estaBalanceada(String expresion) {
		return verificar(expresion, false);
	}

	/**
	 * Igual que {@link #estaBalanceada(String)} pero imprime la TRAZA paso a paso del algoritmo.
	 *
	 * <p>Sirve para estudiar/demostrar cómo trabaja la pila.</p>
	 *
	 * @param expresion texto a analizar.
	 * @return {@code true} si está balanceada.
	 */
	public static boolean estaBalanceadaConTraza(String expresion) {
		return verificar(expresion, true);
	}

	/**
	 * Núcleo del algoritmo. Recorre la expresión y usa la pila para emparejar aperturas y cierres.
	 *
	 * @param expresion texto a analizar.
	 * @param traza     si es {@code true}, imprime cada paso.
	 * @return {@code true} si está balanceada.
	 */
	private static boolean verificar(String expresion, boolean traza) {
		// Consideramos null como "nada que balancear". También la cadena vacía da true.
		if (expresion == null) {
			if (traza) {
				System.out.println("  Expresión null: no hay nada que balancear -> true");
			}
			return true;
		}

		if (traza) {
			System.out.println("  Analizando: \"" + expresion + "\"");
		}

		PilaCaracteres pila = new PilaCaracteres();

		for (int i = 0; i < expresion.length(); i++) {
			char caracter = expresion.charAt(i);

			if (esApertura(caracter)) {
				pila.apilar(caracter);
				if (traza) {
					System.out.println("    pos " + i + ": '" + caracter + "' es APERTURA -> apilar (pila="
							+ pila.getSize() + ")");
				}
			} else if (esCierre(caracter)) {
				if (pila.estaVacia()) {
					// Cierre sin apertura previa: no se puede emparejar.
					if (traza) {
						System.out.println("    pos " + i + ": '" + caracter
								+ "' es CIERRE pero la pila está vacía -> DESBALANCEADA");
					}
					return false;
				}
				char apertura = pila.desapilar();
				if (!coincide(apertura, caracter)) {
					// El tipo de la última apertura no corresponde con este cierre.
					if (traza) {
						System.out.println("    pos " + i + ": '" + caracter + "' cierra '" + apertura
								+ "' -> NO coinciden -> DESBALANCEADA");
					}
					return false;
				}
				if (traza) {
					System.out.println("    pos " + i + ": '" + caracter + "' es CIERRE, desapila '"
							+ apertura + "' (coinciden) (pila=" + pila.getSize() + ")");
				}
			}
			// Cualquier otro carácter se ignora.
		}

		// Balanceada solo si no sobró ninguna apertura sin cerrar.
		boolean balanceada = pila.estaVacia();
		if (traza) {
			System.out.println("    Fin: pila=" + pila.getSize()
					+ (balanceada ? " -> BALANCEADA" : " -> quedaron aperturas sin cerrar: DESBALANCEADA"));
		}
		return balanceada;
	}

	/** Indica si el carácter es un símbolo de apertura. */
	private static boolean esApertura(char caracter) {
		return caracter == '(' || caracter == '[' || caracter == '{';
	}

	/** Indica si el carácter es un símbolo de cierre. */
	private static boolean esCierre(char caracter) {
		return caracter == ')' || caracter == ']' || caracter == '}';
	}

	/** Verifica que una apertura y un cierre sean del mismo tipo. */
	private static boolean coincide(char apertura, char cierre) {
		return (apertura == '(' && cierre == ')')
				|| (apertura == '[' && cierre == ']')
				|| (apertura == '{' && cierre == '}');
	}

	// ------------------------------------------------------------------
	// Demostración automática (pruebas)
	// ------------------------------------------------------------------

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private static void demostrar() {
		System.out.println("=======================================================");
		System.out.println(" VERIFICADOR DE PARÉNTESIS BALANCEADOS (PILA DE Character)");
		System.out.println("=======================================================");

		// ---- Ejemplos VÁLIDOS pedidos ----
		System.out.println("\n--- Expresiones VÁLIDAS (se espera true) ---");
		String valido1 = "(2 + 3) * (5 - 1)";
		String valido2 = "((a + b) * c)";
		comprobar(estaBalanceada(valido1), "\"" + valido1 + "\" debería estar balanceada.");
		comprobar(estaBalanceada(valido2), "\"" + valido2 + "\" debería estar balanceada.");
		System.out.println("  \"" + valido1 + "\" -> true");
		System.out.println("  \"" + valido2 + "\" -> true");

		// ---- Ejemplos INVÁLIDOS pedidos ----
		System.out.println("\n--- Expresiones INVÁLIDAS (se espera false) ---");
		String invalido1 = "(2 + 3";
		String invalido2 = "())(";
		comprobar(!estaBalanceada(invalido1), "\"" + invalido1 + "\" NO debería estar balanceada.");
		comprobar(!estaBalanceada(invalido2), "\"" + invalido2 + "\" NO debería estar balanceada.");
		System.out.println("  \"" + invalido1 + "\" -> false (sobra una apertura)");
		System.out.println("  \"" + invalido2 + "\" -> false (cierre sin apertura y aperturas sin cerrar)");

		// ---- Otros casos de borde ----
		System.out.println("\n--- Casos de borde ---");
		comprobar(estaBalanceada(""), "La cadena vacía está balanceada.");
		comprobar(estaBalanceada("sin parentesis"), "Un texto sin símbolos está balanceado.");
		comprobar(estaBalanceada("((()))"), "Anidación correcta.");
		comprobar(estaBalanceada("([{}])"), "Tipos distintos bien anidados.");
		comprobar(!estaBalanceada(")("), "Cierre antes que apertura.");
		comprobar(!estaBalanceada("([)]"), "Cierres cruzados: no coinciden los tipos.");
		comprobar(!estaBalanceada("("), "Una sola apertura.");
		comprobar(!estaBalanceada(")"), "Un solo cierre.");
		comprobar(estaBalanceada(null), "null se trata como nada que balancear.");
		System.out.println("  \"\", \"sin parentesis\", \"((()))\", \"([{}])\", null -> true");
		System.out.println("  \")(\", \"([)]\", \"(\", \")\" -> false  [Correcto]");

		// ---- Traza paso a paso de un caso inválido ----
		System.out.println("\n--- Traza paso a paso de \"" + invalido2 + "\" ---");
		estaBalanceadaConTraza(invalido2);

		System.out.println("\n[Correcto] La pila de Character detecta correctamente el balanceo.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}
