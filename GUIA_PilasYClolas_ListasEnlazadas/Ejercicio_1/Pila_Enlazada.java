/*
 * Prompt inicial utilizado:
 *
 * Desarrolla un codigo en jaga que permita implementar una pila a travez de una lista enlazada
 * 
 * QUÉ ESTRUCTURA USO Y POR QUÉ:
 * Tengo que implementar una PILA (estructura LIFO: el último que entra es el primero que sale) pero
 * NO con un arreglo, sino apoyándome en una lista enlazada simple, o sea con Nodos que se apuntan
 * con referencias. Uso pila y no cola porque en una pila el acceso siempre es por un solo extremo
 * (el tope), y ese comportamiento LIFO es justo lo que necesito (por ejemplo, deshacer acciones).
 * Al guardar los nodos enlazados no necesito tamaño fijo ni desplazar elementos como en un arreglo.
 *
 * CÓMO DEBE FUNCIONAR INTERNAMENTE:
 * - Cada Nodo guarda un dato int y una referencia al siguiente nodo.
 * - La pila guarda UNA sola referencia: head (la cabeza de la lista) y un contador size.
 * - El head representa el TOPE: como los nuevos elementos se agregan adelante, la cabeza siempre
 *   apunta al último que entró, o sea al primero que debe salir. Por eso push y pop no recorren.
 * - Apilar = insertar al INICIO. Desapilar = quitar el INICIO y mover head al siguiente.
 *   Consultar el tope = mirar el dato del head SIN sacarlo.
 *
 * OPERACIONES:
 * apilar(int), desapilar(), tope(), estaVacia(), contiene(int), imprimir(), getSize().
 *
 * CASOS ESPECIALES:
 * desapilar/tope con pila vacía debe lanzar excepción clara; apilar el primer elemento; desapilar el
 * último (head=null, size=0, dejar el nodo para el Garbage Collector); contiene() en pila vacía =
 * false; imprimir pila vacía muestra "(pila vacía)".
 *
 * COMPLEJIDAD: apilar, desapilar, tope y estaVacia son O(1) (solo tocan el head); contiene e
 * imprimir son O(n) (recorren nodo por nodo).
 *
 * DEMOSTRACIÓN: main con aserciones (comprobar/comprobarExcepcion), orden LIFO, contiene presente y
 * ausente, pila vacía, y menú simple con Scanner.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_1;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Pila (LIFO) implementada sobre una lista enlazada simple de enteros.
 *
 * <p>{@code head} apunta siempre al TOPE de la pila (el último elemento apilado). Por eso apilar,
 * desapilar y consultar el tope son O(1): solo mueven o leen la cabeza, sin recorrer.</p>
 */
public class Pila_Enlazada {

	/** Nodo de la lista enlazada: un dato int y la referencia al siguiente. */
	private static final class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}
	}

	/** Cabeza de la lista = TOPE de la pila. Es null cuando la pila está vacía. */
	private Nodo head;

	/** Cantidad de elementos apilados. */
	private int size;

	public Pila_Enlazada() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * Apila un elemento (push). Inserta un nodo al INICIO de la lista.
	 *
	 * <p>Complejidad O(1): el nuevo nodo pasa a ser el head y apunta al anterior head.</p>
	 *
	 * @param dato valor a apilar.
	 */
	public void apilar(int dato) {
		Nodo nuevo = new Nodo(dato);
		nuevo.siguiente = head; // el nuevo tope apunta al tope anterior
		head = nuevo;           // y pasa a ser el nuevo head (tope)
		size++;
	}

	/**
	 * Desapila el tope (pop) y devuelve su dato.
	 *
	 * <p>Complejidad O(1): solo mueve el head al siguiente. El nodo removido queda inaccesible para
	 * que lo libere el Garbage Collector (no se "borra" a mano).</p>
	 *
	 * @return el dato que estaba en el tope.
	 * @throws NoSuchElementException si la pila está vacía.
	 */
	public int desapilar() {
		if (estaVacia()) {
			throw new NoSuchElementException("No se puede desapilar: la pila está vacía.");
		}
		int dato = head.dato;
		head = head.siguiente; // el tope siguiente pasa a ser el tope
		size--;
		return dato;
	}

	/**
	 * Devuelve el dato del tope (peek) sin desapilar.
	 *
	 * <p>Complejidad O(1): solo lee el head.</p>
	 *
	 * @return el dato del tope.
	 * @throws NoSuchElementException si la pila está vacía.
	 */
	public int tope() {
		if (estaVacia()) {
			throw new NoSuchElementException("No se puede consultar el tope: la pila está vacía.");
		}
		return head.dato;
	}

	/**
	 * Indica si la pila no tiene elementos.
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si la pila está vacía.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si la pila contiene un valor (búsqueda secuencial desde el tope hacia abajo).
	 *
	 * <p>Complejidad O(n): recorre nodo por nodo; la lista enlazada no tiene acceso directo.</p>
	 *
	 * @param dato valor a buscar.
	 * @return {@code true} si aparece al menos una vez.
	 */
	public boolean contiene(int dato) {
		Nodo auxiliar = head;
		while (auxiliar != null) {
			if (auxiliar.dato == dato) {
				return true;
			}
			auxiliar = auxiliar.siguiente;
		}
		return false;
	}

	/**
	 * Imprime el contenido de la pila desde el tope hacia la base.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void imprimir() {
		if (estaVacia()) {
			System.out.println("(pila vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo auxiliar = head;
		while (auxiliar != null) {
			texto.append(auxiliar.dato).append(" -> ");
			auxiliar = auxiliar.siguiente;
		}
		texto.append("null  (tope a la izquierda)");
		System.out.println(texto);
	}

	/** Devuelve la cantidad de elementos apilados. */
	public int getSize() {
		return size;
	}

	// ------------------------------------------------------------------
	// Demostración automática
	// ------------------------------------------------------------------

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private static void comprobarExcepcion(Runnable accion, Class<? extends Throwable> esperada,
			String mensaje) {
		try {
			accion.run();
		} catch (Throwable error) {
			if (esperada.isInstance(error)) {
				return;
			}
			throw new AssertionError(mensaje + " (se lanzó " + error.getClass().getSimpleName() + ")");
		}
		throw new AssertionError(mensaje + " (no se lanzó ninguna excepción)");
	}

	private static void demostrar() {
		System.out.println("==========================================");
		System.out.println(" PILA ENLAZADA DE ENTEROS (LIFO)");
		System.out.println("==========================================");

		Pila_Enlazada pila = new Pila_Enlazada();

		// --- Pila vacía ---
		System.out.println("\n--- Pila vacía ---");
		comprobar(pila.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(pila.getSize() == 0, "El tamaño inicial debe ser 0.");
		comprobar(!pila.contiene(1), "No debe contener ningún valor.");
		System.out.print("  imprimir(): ");
		pila.imprimir();
		comprobarExcepcion(pila::desapilar, NoSuchElementException.class,
				"Desapilar en pila vacía debe lanzar excepción.");
		comprobarExcepcion(pila::tope, NoSuchElementException.class,
				"Consultar tope en pila vacía debe lanzar excepción.");
		System.out.println("  estaVacia()=true, size=0, contiene(1)=false, desapilar/tope lanzan  [Correcto]");

		// --- Apilar ---
		System.out.println("\n--- Apilar 10, 20, 30 ---");
		pila.apilar(10);
		pila.apilar(20);
		pila.apilar(30); // último en entrar = tope
		System.out.print("  Contenido (tope a la izquierda): ");
		pila.imprimir();
		comprobar(pila.tope() == 30, "El tope debe ser 30 (el último apilado).");
		comprobar(pila.getSize() == 3, "El tamaño debe ser 3.");
		System.out.println("  tope()=30  [Correcto]");

		// --- Buscar (contiene) ---
		System.out.println("\n--- contiene() ---");
		comprobar(pila.contiene(20), "Debe contener el 20.");
		comprobar(!pila.contiene(99), "No debe contener el 99.");
		System.out.println("  contiene(20)=true, contiene(99)=false  [Correcto]");

		// --- Desapilar (orden LIFO) ---
		System.out.println("\n--- Desapilar y comprobar orden LIFO ---");
		comprobar(pila.desapilar() == 30, "Primero sale el 30.");
		comprobar(pila.desapilar() == 20, "Luego sale el 20.");
		comprobar(pila.tope() == 10, "Queda el 10 en el tope.");
		System.out.println("  desapilar()=30, desapilar()=20, tope()=10  [Correcto]");

		// --- Desapilar el último elemento ---
		System.out.println("\n--- Desapilar el último elemento ---");
		comprobar(pila.desapilar() == 10, "Sale el 10.");
		comprobar(pila.estaVacia(), "Debe quedar vacía.");
		comprobar(pila.getSize() == 0, "El tamaño debe volver a 0.");
		System.out.println("  head=null, size=0, estaVacia()=true  [Correcto]");

		// --- Reutilizar la pila tras vaciarla ---
		System.out.println("\n--- Reutilizar la pila ---");
		pila.apilar(7);
		comprobar(pila.tope() == 7 && pila.getSize() == 1, "La pila se puede volver a usar.");
		System.out.println("  apilar(7) -> tope()=7, size=1  [Correcto]");

		System.out.println("\n[Correcto] Todas las operaciones cumplen el comportamiento LIFO esperado.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	private static void ejecutarMenu(Pila_Enlazada pila, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú (Pila enlazada de enteros) ---");
			System.out.println("1. Apilar");
			System.out.println("2. Desapilar");
			System.out.println("3. Ver tope");
			System.out.println("4. ¿Contiene un valor?");
			System.out.println("5. Imprimir pila");
			System.out.println("6. Ver tamaño");
			System.out.println("7. ¿Está vacía?");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");

			String opcion = scanner.hasNextLine() ? scanner.nextLine().trim() : "0";
			try {
				switch (opcion) {
					case "1": {
						System.out.print("Ingrese el entero a apilar: ");
						pila.apilar(Integer.parseInt(scanner.nextLine().trim()));
						pila.imprimir();
						break;
					}
					case "2":
						System.out.println("  Desapilado: " + pila.desapilar());
						pila.imprimir();
						break;
					case "3":
						System.out.println("  Tope: " + pila.tope());
						break;
					case "4": {
						System.out.print("Ingrese el valor a buscar: ");
						int valor = Integer.parseInt(scanner.nextLine().trim());
						System.out.println("  contiene(" + valor + ") -> " + pila.contiene(valor));
						break;
					}
					case "5":
						pila.imprimir();
						break;
					case "6":
						System.out.println("  Tamaño: " + pila.getSize());
						break;
					case "7":
						System.out.println("  estaVacia() -> " + pila.estaVacia());
						break;
					case "0":
						continuar = false;
						break;
					default:
						System.out.println("  Opción no válida. Elija una opción del menú.");
				}
			} catch (NumberFormatException e) {
				System.out.println("  Entrada inválida: debe ingresar un número entero.");
			} catch (NoSuchElementException e) {
				System.out.println("  " + e.getMessage());
			}
		}
		System.out.println("\nSimulación finalizada. ¡Hasta luego!");
	}

	public static void main(String[] args) {
		demostrar();
		System.out.println("\nAhora podés probar la pila vos mismo en el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Pila_Enlazada(), scanner);
		}
	}
}
