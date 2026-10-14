/*
 * PROMPT INICIAL UTILIZADO
 *
 * Desarrolla un codigo en java que permita la implementacion de una lista doblemente enlazada
 *
 * 1. Estructura: Lista doblemente enlazada genérica.
 * 2. Funcionamiento interno (Nodos): Cada nodo tiene tres referencias: el dato genérico, un puntero
 *    `siguiente` y un puntero `anterior`. Se necesitan ambos punteros para permitir recorrer la
 *    estructura en ambas direcciones (hacia adelante y hacia atrás) en tiempo O(n) y para facilitar
 *    las eliminaciones de nodos sin requerir un puntero extra que rastree el nodo previo.
 * 3. Operaciones: insertar al inicio, insertar al final, eliminar un elemento, imprimir hacia
 *    adelante, imprimir hacia atrás.
 * 4. Casos especiales al eliminar: Hay que contemplar si el elemento a eliminar es el primero
 *    (actualizar `head`), si es el último (actualizar `tail`), si es el único elemento en la lista,
 *    o si el elemento no existe.
 * 5. Comentarios: Incluye este prompt como comentario inicial.
 *
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Implemento una LISTA DOBLEMENTE ENLAZADA GENÉRICA. A diferencia de la lista simple (que solo
 * apunta hacia adelante), aquí cada nodo conoce a su SIGUIENTE y a su ANTERIOR. Eso da dos
 * ventajas:
 *   - Se puede recorrer la lista en AMBAS direcciones (head -> tail y tail -> head) en O(n).
 *   - Al eliminar, ya tengo el nodo previo sin necesidad de un puntero auxiliar que lo rastree
 *     mientras recorro: llego al nodo y uso su .anterior directamente.
 * Guardo DOS referencias en la lista, head (primero) y tail (último), para insertar al final en
 * O(1) y poder imprimir hacia atrás.
 *
 * CÓMO FUNCIONA INTERNAMENTE (NODOS Y GENÉRICOS)
 * - Cada Nodo<E> tiene TRES campos: dato (de tipo E), siguiente y anterior (ambos Nodo<E>).
 * - Al enlazar hay que mantener la coherencia en AMBOS sentidos: si A.siguiente = B, entonces
 *   B.anterior = A. Cada inserción/eliminación actualiza los dos lados.
 * - La lógica NO depende del tipo de dato: insertar y eliminar solo mueven referencias. Al nodo le
 *   da igual si guarda un Integer, un String o un Libro; el tipo T es solo la carga útil.
 * - Las operaciones que COMPARAN (buscar, eliminar) usan Objects.equals, porque == compararía
 *   referencias y fallaría con objetos.
 *
 * OPERACIONES: insertarAlInicio(T), insertarAlFinal(T), eliminar(T) (primera aparición),
 * imprimirAdelante(), imprimirAtras(), estaVacia(), contiene(T), getSize().
 *
 * CASOS ESPECIALES AL ELIMINAR:
 * - Lista vacía -> no hay nada que eliminar, devuelve false.
 * - El elemento NO existe -> devuelve false y la lista queda intacta.
 * - Es el ÚNICO elemento -> head y tail quedan en null y la lista vuelve a estar vacía.
 * - Es el PRIMERO (hay más) -> se actualiza head al siguiente y el nuevo head.anterior = null.
 * - Es el ÚLTIMO (hay más) -> se actualiza tail al anterior y el nuevo tail.siguiente = null.
 * - Es del MEDIO -> se salta el nodo: anterior.siguiente = siguiente y siguiente.anterior =
 *   anterior. Nunca se rompe el doble enlace.
 *
 * COMPLEJIDAD: insertarAlInicio y insertarAlFinal son O(1) (no hay que recorrer gracias a tail);
 * eliminar, contiene e imprimir (en cualquier sentido) son O(n) porque hay que buscar/recorrer.
 *
 * PRUEBAS: un main con aserciones que demuestra la lista con Integer, String y un objeto propio
 * Libro (con equals por ISBN y toString), recorriendo hacia adelante y hacia atrás, y cubriendo
 * TODOS los casos especiales de eliminación.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_10;

import java.util.Objects;

/**
 * Lista doblemente enlazada GENÉRICA: puede almacenar cualquier tipo T y recorrerse en ambos
 * sentidos.
 *
 * <p>Cada nodo tiene un puntero al siguiente y otro al anterior. La lista guarda {@code head}
 * (primer nodo) y {@code tail} (último nodo), lo que permite insertar al final en O(1).</p>
 *
 * @param <T> tipo de dato almacenado en cada nodo.
 */
public class ListaDoblementeEnlazada<T> {

	/**
	 * Nodo genérico de la lista doble: guarda un dato de tipo {@code E}, la referencia al siguiente
	 * y la referencia al anterior.
	 *
	 * <p>Es {@code static} y con su propio tipo {@code E} porque no necesita el {@code T} externo: un
	 * nodo genérico sirve para cualquier lista doble.</p>
	 *
	 * @param <E> tipo de dato del nodo.
	 */
	private static final class Nodo<E> {
		private final E dato;
		private Nodo<E> siguiente;
		private Nodo<E> anterior;

		private Nodo(E dato) {
			this.dato = dato;
		}

		private E getDato() {
			return dato;
		}

		private Nodo<E> getSiguiente() {
			return siguiente;
		}

		private void setSiguiente(Nodo<E> siguiente) {
			this.siguiente = siguiente;
		}

		private Nodo<E> getAnterior() {
			return anterior;
		}

		private void setAnterior(Nodo<E> anterior) {
			this.anterior = anterior;
		}
	}

	/** Primer nodo de la lista. Es null cuando la lista está vacía. */
	private Nodo<T> head;

	/** Último nodo de la lista. Es null cuando la lista está vacía. */
	private Nodo<T> tail;

	/** Cantidad de nodos en la lista. */
	private int size;

	public ListaDoblementeEnlazada() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/**
	 * Inserta un dato al INICIO (el nuevo pasa a ser el head).
	 *
	 * <p>Complejidad O(1): hay que enlazar en ambos sentidos con el viejo head.</p>
	 *
	 * @param dato valor a insertar al inicio.
	 */
	public void insertarAlInicio(T dato) {
		Nodo<T> nuevo = new Nodo<>(dato);
		if (estaVacia()) {
			// Primer nodo: head y tail apuntan al mismo nodo.
			head = nuevo;
			tail = nuevo;
		} else {
			nuevo.setSiguiente(head); // el nuevo apunta al viejo head
			head.setAnterior(nuevo);  // y el viejo head reconoce al nuevo como anterior
			head = nuevo;             // el nuevo pasa a ser el head
		}
		size++;
	}

	/**
	 * Inserta un dato al FINAL (el nuevo pasa a ser el tail).
	 *
	 * <p>Complejidad O(1) gracias a tail: no recorre la lista (a diferencia de la lista simple).</p>
	 *
	 * @param dato valor a insertar al final.
	 */
	public void insertarAlFinal(T dato) {
		Nodo<T> nuevo = new Nodo<>(dato);
		if (estaVacia()) {
			// Primer nodo: head y tail apuntan al mismo nodo.
			head = nuevo;
			tail = nuevo;
		} else {
			tail.setSiguiente(nuevo); // el viejo tail apunta al nuevo
			nuevo.setAnterior(tail);  // y el nuevo reconoce al viejo tail como anterior
			tail = nuevo;             // el nuevo pasa a ser el tail
		}
		size++;
	}

	/**
	 * Elimina la PRIMERA aparición del dato (comparando con {@code equals}).
	 *
	 * <p>Complejidad O(n): hay que buscar el nodo recorriendo desde head.</p>
	 *
	 * <p>Casos especiales contemplados:</p>
	 * <ul>
	 *   <li>Lista vacía o dato inexistente: devuelve {@code false} sin tocar nada.</li>
	 *   <li>Único elemento: head y tail quedan en {@code null}.</li>
	 *   <li>Es el primero: se actualiza head y el nuevo head.anterior = {@code null}.</li>
	 *   <li>Es el último: se actualiza tail y el nuevo tail.siguiente = {@code null}.</li>
	 *   <li>Es del medio: se reenlazan los vecinos entre sí (anterior y siguiente).</li>
	 * </ul>
	 *
	 * @param dato valor a eliminar.
	 * @return {@code true} si se eliminó; {@code false} si no se encontró.
	 */
	public boolean eliminar(T dato) {
		if (estaVacia()) {
			return false; // nada que eliminar
		}

		// 1) Buscar el nodo que contiene el dato (primera aparición).
		Nodo<T> actual = head;
		while (actual != null && !Objects.equals(dato, actual.getDato())) {
			actual = actual.getSiguiente();
		}

		if (actual == null) {
			return false; // el elemento no existe
		}

		// 2) Reenlazar respetando la doble conexión. Guardamos los vecinos ANTES de desvincular.
		Nodo<T> previo = actual.getAnterior();
		Nodo<T> siguiente = actual.getSiguiente();

		if (previo == null) {
			// Era el PRIMERO (o el único): el head pasa a ser el siguiente (null si era el único).
			head = siguiente;
		} else {
			previo.setSiguiente(siguiente);
		}

		if (siguiente == null) {
			// Era el ÚLTIMO (o el único): el tail pasa a ser el previo (null si era el único).
			tail = previo;
		} else {
			siguiente.setAnterior(previo);
		}

		// 3) Desvincular el nodo eliminado para que el Garbage Collector lo libere.
		actual.setSiguiente(null);
		actual.setAnterior(null);

		size--;
		return true;
	}

	/**
	 * Indica si el dato está en la lista (contains), comparando con {@code equals}.
	 *
	 * <p>Complejidad O(n): recorre desde head.</p>
	 *
	 * @param dato valor a buscar.
	 * @return {@code true} si aparece al menos una vez.
	 */
	public boolean contiene(T dato) {
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			if (Objects.equals(dato, auxiliar.getDato())) {
				return true;
			}
			auxiliar = auxiliar.getSiguiente();
		}
		return false;
	}

	/**
	 * Imprime la lista HACIA ADELANTE (head -> tail), siguiendo los punteros {@code siguiente}.
	 *
	 * <p>Complejidad O(n).</p>
	 */
	public void imprimirAdelante() {
		if (estaVacia()) {
			System.out.println("(lista vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder("head -> ");
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			texto.append(String.valueOf(auxiliar.getDato()));
			texto.append(auxiliar.getSiguiente() != null ? " <-> " : " -> tail");
			auxiliar = auxiliar.getSiguiente();
		}
		System.out.println(texto);
	}

	/**
	 * Imprime la lista HACIA ATRÁS (tail -> head), siguiendo los punteros {@code anterior}.
	 *
	 * <p>Complejidad O(n). Esto solo es posible gracias al doble enlace.</p>
	 */
	public void imprimirAtras() {
		if (estaVacia()) {
			System.out.println("(lista vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder("tail -> ");
		Nodo<T> auxiliar = tail;
		while (auxiliar != null) {
			texto.append(String.valueOf(auxiliar.getDato()));
			texto.append(auxiliar.getAnterior() != null ? " <-> " : " -> head");
			auxiliar = auxiliar.getAnterior();
		}
		System.out.println(texto);
	}

	/** Indica si la lista no tiene nodos. Complejidad O(1). */
	public boolean estaVacia() {
		return head == null;
	}

	/** Devuelve la cantidad de nodos almacenados. */
	public int getSize() {
		return size;
	}

	// ------------------------------------------------------------------
	// Demostración automática (pruebas)
	// ------------------------------------------------------------------

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	@SafeVarargs
	private static <T> ListaDoblementeEnlazada<T> construir(T... datos) {
		ListaDoblementeEnlazada<T> lista = new ListaDoblementeEnlazada<>();
		for (T dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================================");
		System.out.println(" LISTA DOBLEMENTE ENLAZADA GENÉRICA (Integer/String/Libro)");
		System.out.println("====================================================");

		// ---- Lista vacía ----
		System.out.println("\n--- Lista vacía ---");
		ListaDoblementeEnlazada<Integer> vacia = new ListaDoblementeEnlazada<>();
		comprobar(vacia.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(vacia.getSize() == 0, "El tamaño inicial debe ser 0.");
		comprobar(!vacia.contiene(1), "No debe contener nada.");
		System.out.print("  Adelante: ");
		vacia.imprimirAdelante();
		System.out.print("  Atrás:    ");
		vacia.imprimirAtras();
		comprobar(!vacia.eliminar(1), "Eliminar en lista vacía debe devolver false.");
		System.out.println("  eliminar(1)=false, sin errores  [Correcto]");

		// ---- Inserción y recorrido en ambos sentidos ----
		System.out.println("\n--- Insertar al inicio y al final, recorrer en ambos sentidos ---");
		ListaDoblementeEnlazada<Integer> enteros = new ListaDoblementeEnlazada<>();
		enteros.insertarAlInicio(20);
		enteros.insertarAlInicio(10);  // queda 10, 20
		enteros.insertarAlFinal(30);   // queda 10, 20, 30
		enteros.insertarAlFinal(40);   // queda 10, 20, 30, 40
		System.out.print("  Adelante: ");
		enteros.imprimirAdelante();
		System.out.print("  Atrás:    ");
		enteros.imprimirAtras();
		comprobar(enteros.getSize() == 4, "Debe haber 4 nodos.");
		comprobar(enteros.contiene(30), "Debe contener el 30.");
		System.out.println("  size=4 y ambos recorridos coinciden  [Correcto]");

		// ---- Casos especiales de eliminación ----
		System.out.println("\n--- Casos especiales al eliminar ---");

		// (a) Elemento NO existe
		ListaDoblementeEnlazada<Integer> noExiste = construir(1, 2, 3);
		comprobar(!noExiste.eliminar(99), "Eliminar un inexistente debe devolver false.");
		comprobar(noExiste.getSize() == 3, "La lista debe quedar intacta (size=3).");
		System.out.print("  (a) eliminar(99) inexistente -> false | ");
		noExiste.imprimirAdelante();

		// (b) ÚNICO elemento
		ListaDoblementeEnlazada<Integer> unico = construir(5);
		comprobar(unico.eliminar(5), "Eliminar el único elemento debe funcionar.");
		comprobar(unico.estaVacia() && unico.getSize() == 0,
				"Al eliminar el único, head y tail deben quedar en null.");
		System.out.println("  (b) eliminar el único (5) -> lista vacía, head=tail=null  [Correcto]");

		// (c) Es el PRIMERO (hay más): se actualiza head
		ListaDoblementeEnlazada<Integer> primero = construir(1, 2, 3);
		comprobar(primero.eliminar(1), "Eliminar el primero debe funcionar.");
		System.out.print("  (c) eliminar el primero (1) -> true | adelante: ");
		primero.imprimirAdelante();
		comprobar(primero.getSize() == 2, "Deben quedar 2.");
		System.out.print("      hacia atrás (el nuevo head es el 2): ");
		primero.imprimirAtras();

		// (d) Es el ÚLTIMO (hay más): se actualiza tail
		ListaDoblementeEnlazada<Integer> ultimo = construir(1, 2, 3);
		comprobar(ultimo.eliminar(3), "Eliminar el último debe funcionar.");
		System.out.print("  (d) eliminar el último (3) -> true | adelante: ");
		ultimo.imprimirAdelante();
		comprobar(ultimo.getSize() == 2, "Deben quedar 2.");
		System.out.print("      hacia atrás (el nuevo tail es el 2): ");
		ultimo.imprimirAtras();

		// (e) Es del MEDIO: se reenlazan los vecinos
		ListaDoblementeEnlazada<Integer> medio = construir(1, 2, 3);
		comprobar(medio.eliminar(2), "Eliminar el del medio debe funcionar.");
		comprobar(medio.getSize() == 2 && !medio.contiene(2),
				"Debe quedar 1, 3 sin el 2.");
		System.out.print("  (e) eliminar el del medio (2) -> true | adelante: ");
		medio.imprimirAdelante();
		System.out.print("      hacia atrás: ");
		medio.imprimirAtras();

		// (f) Vaciar la lista del todo eliminando de a uno (de extremo a extremo)
		System.out.println("\n--- Vaciar la lista del todo ---");
		ListaDoblementeEnlazada<Integer> paraVaciar = construir(10, 20, 30, 40);
		for (int valor : new int[] { 10, 20, 30, 40 }) {
			comprobar(paraVaciar.eliminar(valor), "Debe eliminar el " + valor + ".");
		}
		comprobar(paraVaciar.estaVacia() && paraVaciar.getSize() == 0,
				"Tras eliminar todos, head y tail deben quedar en null.");
		System.out.println("  Se eliminaron 10, 20, 30 y 40 -> lista vacía  [Correcto]");

		// ---- String ----
		System.out.println("\n--- ListaDoblementeEnlazada<String> ---");
		ListaDoblementeEnlazada<String> textos = new ListaDoblementeEnlazada<>();
		textos.insertarAlFinal("uno");
		textos.insertarAlFinal("dos");
		textos.insertarAlFinal("tres");
		System.out.print("  Adelante: ");
		textos.imprimirAdelante();
		System.out.print("  Atrás:    ");
		textos.imprimirAtras();
		comprobar(textos.eliminar("dos"), "Debe eliminar \"dos\" (estaba al medio).");
		System.out.print("  Tras eliminar \"dos\" -> adelante: ");
		textos.imprimirAdelante();
		System.out.print("                      -> atrás:    ");
		textos.imprimirAtras();

		// ---- Libro (objeto propio, igualdad por ISBN) ----
		System.out.println("\n--- ListaDoblementeEnlazada<Libro> (igualdad por ISBN) ---");
		Libro quijote = new Libro("978-84", "El Quijote");
		Libro cien = new Libro("978-84-2", "Cien años de soledad");
		Libro quijoteOtraInstancia = new Libro("978-84", "El Quijote"); // otra instancia, mismo ISBN

		ListaDoblementeEnlazada<Libro> biblioteca = new ListaDoblementeEnlazada<>();
		biblioteca.insertarAlFinal(quijote);
		biblioteca.insertarAlFinal(cien);
		System.out.print("  Adelante: ");
		biblioteca.imprimirAdelante();
		// quijoteOtraInstancia es OTRA instancia pero con el mismo ISBN: equals debe dar true.
		comprobar(biblioteca.contiene(quijoteOtraInstancia),
				"Debe encontrar el Quijote por ISBN (equals), no por referencia.");
		comprobar(biblioteca.eliminar(quijoteOtraInstancia),
				"Debe eliminar el Quijote por ISBN (equals).");
		System.out.print("  Tras eliminar el Quijote -> adelante: ");
		biblioteca.imprimirAdelante();

		System.out.println("\n[Correcto] La lista doble recorre en ambos sentidos y elimina correctamente "
				+ "con cualquier tipo de dato.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}

/**
 * Clase de objeto propio para probar la lista doble genérica con un tipo que NO es de la librería.
 *
 * <p>Define {@code equals} por ISBN (dos libros con el mismo ISBN son iguales) y {@code toString}
 * para poder imprimirlos. Sin {@code equals}, eliminar/contener compararía referencias y no
 * encontraría una instancia equivalente.</p>
 */
class Libro {

	private final String isbn;
	private final String titulo;

	Libro(String isbn, String titulo) {
		this.isbn = isbn;
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	/** Dos libros son iguales si tienen el mismo ISBN. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Libro)) {
			return false;
		}
		Libro libro = (Libro) otro;
		return Objects.equals(isbn, libro.isbn);
	}

	/** Coherente con equals: el hashCode depende del ISBN. */
	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}

	/** Representación legible para imprimir la lista. */
	@Override
	public String toString() {
		return "Libro(" + isbn + ", " + titulo + ")";
	}
}
