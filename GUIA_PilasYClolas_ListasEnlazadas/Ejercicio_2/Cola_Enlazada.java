/*
 * Prompt inicial utilizado:
 *
 * Desarrolla un codigo en java que permita la implementacion de una cola a partir de una lista
 * enlazada
 * 
 * QUÉ ESTRUCTURA USO Y POR QUÉ:
 * Tengo que implementar una COLA (estructura FIFO: el primero que entra es el primero que sale) con
 * una lista enlazada simple (Nodos que se apuntan con referencias). Uso cola y no pila porque una
 * cola se atiende por los dos extremos: se agrega por un lado y se saca por el otro, en orden de
 * llegada (como una fila de personas o una cola de impresión).
 *
 * CÓMO DEBE FUNCIONAR INTERNAMENTE:
 * - Cada Nodo guarda un dato int y una referencia al siguiente.
 * - La cola guarda DOS referencias: head (FRENTE, por donde se desencola) y tail (FINAL, por donde
 *   se encola), más un contador size.
 * - POR QUÉ head Y tail (y qué pasaría con SOLO head): con head solo puedo insertar adelante en
 *   O(1), pero la cola se llena por el FINAL; llegar al último nodo desde head obliga a recorrer
 *   toda la lista, así que encolar sería O(n). Con tail (que apunta siempre al último nodo) encolar
 *   es O(1): tail.siguiente = nuevo y luego tail = nuevo. Desencolar sale por head en O(1) porque
 *   el frente es el primero que llegó.
 * - Encolar = insertar al FINAL usando tail. Desencolar = quitar el PRIMERO (head) y mover head.
 *
 * OPERACIONES:
 * encolar(int), desencolar(), frente(), estaVacia(), contiene(int), imprimir(), getSize().
 *
 * CASOS ESPECIALES:
 * encolar el primer elemento (head y tail al mismo nodo); desencolar el último (head Y tail a null);
 * desencolar/frente con cola vacía lanza excepción clara (NoSuchElementException); el nodo que sale
 * queda para el Garbage Collector; contiene() en vacía = false; imprimir vacía muestra "(cola vacía)".
 *
 * COMPLEJIDAD: encolar es O(1) gracias a tail (sin tail sería O(n)); desencolar, frente y estaVacia
 * son O(1); contiene e imprimir son O(n).
 *
 * DEMOSTRACIÓN: main con aserciones (comprobar/comprobarExcepcion), orden FIFO, contiene presente y
 * ausente, cola vacía, vaciar la cola y volver a encolar, y menú simple con Scanner.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_2;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Cola (FIFO) implementada sobre una lista enlazada simple de enteros.
 *
 * <p>{@code head} es el FRENTE (se desencola por acá) y {@code tail} es el FINAL (se encola por
 * acá). Tener las dos referencias permite encolar y desencolar en O(1): no hay que recorrer la
 * lista. Con solo {@code head}, encolar sería O(n).</p>
 */
public class Cola_Enlazada {

	/** Nodo de la lista enlazada: un dato int y la referencia al siguiente. */
	private static final class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}
	}

	/** FRENTE de la cola: por acá se desencola. Es null cuando la cola está vacía. */
	private Nodo head;

	/** FINAL de la cola: por acá se encola. Es null cuando la cola está vacía. */
	private Nodo tail;

	/** Cantidad de elementos encolados. */
	private int size;

	public Cola_Enlazada() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/**
	 * Encola un elemento al FINAL (enqueue).
	 *
	 * <p>Complejidad O(1) gracias a tail: {@code tail.siguiente = nuevo} y {@code tail = nuevo}. Sin
	 * la referencia tail habría que recorrer toda la lista (O(n)).</p>
	 *
	 * @param dato valor a encolar.
	 */
	public void encolar(int dato) {
		Nodo nuevo = new Nodo(dato);
		if (estaVacia()) {
			// Primer elemento: head y tail apuntan al mismo único nodo.
			head = nuevo;
			tail = nuevo;
		} else {
			tail.siguiente = nuevo; // enlazo el nuevo después del último
			tail = nuevo;           // y el nuevo pasa a ser el final
		}
		size++;
	}

	/**
	 * Desencola el FRENTE (dequeue) y devuelve su dato.
	 *
	 * <p>Complejidad O(1): solo mueve head al siguiente. El nodo que sale queda inaccesible para que
	 * lo libere el Garbage Collector (no se "borra" a mano).</p>
	 *
	 * <p>Si al sacar el último elemento la cola queda vacía, tail también vuelve a null; de lo
	 * contrario quedaría apuntando a un nodo que ya no pertenece a la cola.</p>
	 *
	 * @return el dato que estaba en el frente.
	 * @throws NoSuchElementException si la cola está vacía.
	 */
	public int desencolar() {
		if (estaVacia()) {
			throw new NoSuchElementException("No se puede desencolar: la cola está vacía.");
		}
		int dato = head.dato;
		head = head.siguiente; // el frente pasa a ser el siguiente nodo
		if (head == null) {
			tail = null; // se vació la cola: tail también debe quedar en null
		}
		size--;
		return dato;
	}

	/**
	 * Devuelve el dato del FRENTE sin desencolar.
	 *
	 * <p>Complejidad O(1): solo lee head.</p>
	 *
	 * @return el dato del frente.
	 * @throws NoSuchElementException si la cola está vacía.
	 */
	public int frente() {
		if (estaVacia()) {
			throw new NoSuchElementException("No se puede consultar el frente: la cola está vacía.");
		}
		return head.dato;
	}

	/**
	 * Indica si la cola no tiene elementos.
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si la cola está vacía.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si la cola contiene un valor (recorrido secuencial desde el frente).
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
	 * Imprime el contenido de la cola desde el FRENTE hacia el FINAL.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void imprimir() {
		if (estaVacia()) {
			System.out.println("(cola vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo auxiliar = head;
		while (auxiliar != null) {
			texto.append(auxiliar.dato).append(" -> ");
			auxiliar = auxiliar.siguiente;
		}
		texto.append("null  (frente a la izquierda)");
		System.out.println(texto);
	}

	/** Devuelve la cantidad de elementos encolados. */
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
		System.out.println(" COLA ENLAZADA DE ENTEROS (FIFO)");
		System.out.println("==========================================");

		Cola_Enlazada cola = new Cola_Enlazada();

		// --- Cola vacía ---
		System.out.println("\n--- Cola vacía ---");
		comprobar(cola.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(cola.getSize() == 0, "El tamaño inicial debe ser 0.");
		comprobar(!cola.contiene(1), "No debe contener ningún valor.");
		System.out.print("  imprimir(): ");
		cola.imprimir();
		comprobarExcepcion(cola::desencolar, NoSuchElementException.class,
				"Desencolar en cola vacía debe lanzar excepción.");
		comprobarExcepcion(cola::frente, NoSuchElementException.class,
				"Consultar frente en cola vacía debe lanzar excepción.");
		System.out.println("  estaVacia()=true, size=0, contiene(1)=false, desencolar/frente lanzan  [Correcto]");

		// --- Encolar ---
		System.out.println("\n--- Encolar 10, 20, 30 ---");
		cola.encolar(10);
		cola.encolar(20);
		cola.encolar(30); // último en entrar = final
		System.out.print("  Contenido (frente a la izquierda): ");
		cola.imprimir();
		comprobar(cola.frente() == 10, "El frente debe ser 10 (el primero que entró).");
		comprobar(cola.getSize() == 3, "El tamaño debe ser 3.");
		System.out.println("  frente()=10  [Correcto]");

		// --- Buscar (contiene) ---
		System.out.println("\n--- contiene() ---");
		comprobar(cola.contiene(20), "Debe contener el 20.");
		comprobar(!cola.contiene(99), "No debe contener el 99.");
		System.out.println("  contiene(20)=true, contiene(99)=false  [Correcto]");

		// --- Desencolar (orden FIFO) ---
		System.out.println("\n--- Desencolar y comprobar orden FIFO ---");
		comprobar(cola.desencolar() == 10, "Primero sale el 10 (el primero que entró).");
		comprobar(cola.desencolar() == 20, "Luego sale el 20.");
		comprobar(cola.frente() == 30, "Queda el 30 en el frente.");
		System.out.println("  desencolar()=10, desencolar()=20, frente()=30  [Correcto]");

		// --- Desencolar el último elemento (head y tail a null) ---
		System.out.println("\n--- Desencolar el último elemento ---");
		comprobar(cola.desencolar() == 30, "Sale el 30.");
		comprobar(cola.estaVacia(), "Debe quedar vacía.");
		comprobar(cola.getSize() == 0, "El tamaño debe volver a 0.");
		System.out.println("  head=null, tail=null, size=0, estaVacia()=true  [Correcto]");

		// --- Reutilizar la cola tras vaciarla ---
		System.out.println("\n--- Reutilizar la cola ---");
		cola.encolar(7);
		comprobar(cola.frente() == 7 && cola.getSize() == 1,
				"Tras vaciarse, head y tail deben permitir volver a encolar.");
		System.out.println("  encolar(7) -> frente()=7, size=1  [Correcto]");

		System.out.println("\n[Correcto] Todas las operaciones cumplen el comportamiento FIFO esperado.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	private static void ejecutarMenu(Cola_Enlazada cola, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú (Cola enlazada de enteros) ---");
			System.out.println("1. Encolar");
			System.out.println("2. Desencolar");
			System.out.println("3. Ver frente");
			System.out.println("4. ¿Contiene un valor?");
			System.out.println("5. Imprimir cola");
			System.out.println("6. Ver tamaño");
			System.out.println("7. ¿Está vacía?");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");

			String opcion = scanner.hasNextLine() ? scanner.nextLine().trim() : "0";
			try {
				switch (opcion) {
					case "1": {
						System.out.print("Ingrese el entero a encolar: ");
						cola.encolar(Integer.parseInt(scanner.nextLine().trim()));
						cola.imprimir();
						break;
					}
					case "2":
						System.out.println("  Desencolado: " + cola.desencolar());
						cola.imprimir();
						break;
					case "3":
						System.out.println("  Frente: " + cola.frente());
						break;
					case "4": {
						System.out.print("Ingrese el valor a buscar: ");
						int valor = Integer.parseInt(scanner.nextLine().trim());
						System.out.println("  contiene(" + valor + ") -> " + cola.contiene(valor));
						break;
					}
					case "5":
						cola.imprimir();
						break;
					case "6":
						System.out.println("  Tamaño: " + cola.getSize());
						break;
					case "7":
						System.out.println("  estaVacia() -> " + cola.estaVacia());
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
		System.out.println("\nAhora podés probar la cola vos mismo en el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Cola_Enlazada(), scanner);
		}
	}
}
