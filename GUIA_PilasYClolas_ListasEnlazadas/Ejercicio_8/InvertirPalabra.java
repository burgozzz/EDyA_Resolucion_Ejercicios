/*
 * PROMPT INICIAL UTILIZADO
 *
 * Desarrolla un codigo en java que permita invertir una palabra
 * 
 * 1. Estructura: Pila enlazada de caracteres.
 * 2. Por qué corresponde usar pila: Porque la política LIFO permite invertir el orden. Al apilar
 *    todos los caracteres de una palabra secuencialmente, el primer carácter queda en el fondo y el
 *    último en el tope. Al desapilar, los caracteres salen en el orden inverso al que entraron.
 * 3. Operaciones: Recibir palabra, apilar caracteres, desapilar construyendo la nueva palabra.
 * 4. Prueba: Demostrar que "algoritmo" se convierte en "omtirogla".
 * 5. Comentarios: Incluye este prompt como comentario inicial.
 *
 *
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Uso una PILA enlazada de caracteres. Invertir el orden es el caso de uso clásico de LIFO: al
 * apilar los caracteres de izquierda a derecha, el PRIMERO queda en el fondo y el ÚLTIMO en el
 * tope. Como la pila solo deja salir por el tope, al desapilar aparecen en orden inverso (el último
 * apilado sale primero), y así se reconstruye la palabra al revés. Una cola (FIFO) devolvería los
 * caracteres en el mismo orden (no invertiría nada). La lista enlazada evita tamaño fijo.
 *
 * CÓMO FUNCIONA (ALGORITMO)
 * - Recorro la palabra carácter por carácter y los APILO todos.
 * - Luego DESAPILO uno por uno y los voy agregando a un StringBuilder: como salen del tope (el
 *   último primero), quedan en orden inverso.
 * - El resultado es la palabra invertida.
 *
 * OPERACIONES: invertir(String) [recibe, apila y desapila]; la clase interna PilaCaracteres ofrece
 * apilar, desapilar, estaVacia y getSize.
 *
 * CASOS ESPECIALES: cadena vacía -> devuelve ""; cadena de un solo carácter -> el mismo carácter;
 * null -> null (no hay nada que invertir); palabras con espacios o mayúsculas se invierten tal cual
 * (carácter por carácter). Al desapilar una pila vacía se lanza EmptyStackException, pero el
 * algoritmo solo desapila mientras quedan elementos, así que nunca ocurre.
 *
 * COMPLEJIDAD: O(n) en tiempo (n apilados + n desapilados) y O(n) en espacio (la pila guarda los n
 * caracteres). Apilar y desapilar son O(1) cada uno.
 *
 * PRUEBAS: un main con aserciones que demuestra el ejemplo pedido ("algoritmo" -> "omtirogla") y
 * otros casos (vacío, un carácter, palíndromos, frases con espacios).
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_8;

import java.util.EmptyStackException;

/**
 * Invierte palabras usando una PILA enlazada de caracteres.
 *
 * <p>La pila trabaja con {@code char} (tipo primitivo), que es lo natural para procesar texto
 * carácter por carácter. El orden de salida LIFO produce la inversión.</p>
 */
public class InvertirPalabra {

	/**
	 * Pila enlazada simple de caracteres. Es {@code static} porque no depende de la instancia de
	 * {@link InvertirPalabra}.
	 */
	private static final class PilaCaracteres {

		/** Nodo de la pila: un char y la referencia al siguiente. */
		private static final class Nodo {
			private final char dato;
			private Nodo siguiente;

			private Nodo(char dato) {
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
		private void apilar(char dato) {
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
		private char desapilar() {
			if (estaVacia()) {
				throw new EmptyStackException();
			}
			char dato = tope.dato;
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
	 * Invierte una palabra (o cualquier texto) usando una pila.
	 *
	 * <p>Complejidad O(n): apila los n caracteres y luego los desapila (cada operación es O(1)).</p>
	 *
	 * @param palabra texto a invertir.
	 * @return el texto con los caracteres en orden inverso; {@code ""} si la entrada es vacía;
	 *         {@code null} si la entrada es {@code null}.
	 */
	public static String invertir(String palabra) {
		if (palabra == null) {
			return null;
		}

		PilaCaracteres pila = new PilaCaracteres();

		// 1) Apilar todos los caracteres, de izquierda a derecha.
		for (int i = 0; i < palabra.length(); i++) {
			pila.apilar(palabra.charAt(i));
		}

		// 2) Desapilar: salen del último al primero, es decir, al revés.
		StringBuilder invertida = new StringBuilder(palabra.length());
		while (!pila.estaVacia()) {
			invertida.append(pila.desapilar());
		}

		return invertida.toString();
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
		System.out.println("======================================================");
		System.out.println(" INVERTIR UNA PALABRA CON PILA (LIFO)");
		System.out.println("======================================================");

		// ---- Prueba pedida ----
		System.out.println("\n--- Prueba pedida ---");
		String original = "algoritmo";
		String invertida = invertir(original);
		System.out.println("  \"" + original + "\" -> \"" + invertida + "\"");
		comprobar(invertida.equals("omtirogla"),
				"\"algoritmo\" debe invertirse como \"omtirogla\".");

		// ---- Otros casos ----
		System.out.println("\n--- Otros casos ---");
		comprobar(invertir("").equals(""), "La cadena vacía se invierte como cadena vacía.");
		comprobar(invertir("a").equals("a"), "Un solo carácter se mantiene.");
		comprobar(invertir("java").equals("avaj"), "\"java\" -> \"avaj\".");
		comprobar(invertir("reconocer").equals("reconocer"), "Un palíndromo queda igual.");
		comprobar(invertir("hola mundo").equals("odnum aloh"), "Invierte también los espacios.");
		comprobar(invertir(null) == null, "null se invierte como null.");
		System.out.println("  \"\"        -> \"\"");
		System.out.println("  \"a\"       -> \"a\"");
		System.out.println("  \"java\"    -> \"avaj\"");
		System.out.println("  \"reconocer\" -> \"reconocer\" (palíndromo)");
		System.out.println("  \"hola mundo\" -> \"odnum aloh\"");
		System.out.println("  null      -> null  [Correcto]");

		// ---- Invertir dos veces vuelve al original (propiedad útil) ----
		System.out.println("\n--- Invertir dos veces vuelve al original ---");
		String doble = invertir(invertir("estructuras"));
		comprobar(doble.equals("estructuras"), "Invertir dos veces restaura la palabra.");
		System.out.println("  invertir(invertir(\"estructuras\")) = \"" + doble + "\"  [Correcto]");

		System.out.println("\n[Correcto] La pila invierte correctamente cualquier palabra (LIFO).");
	}

	public static void main(String[] args) {
		demostrar();
	}
}
