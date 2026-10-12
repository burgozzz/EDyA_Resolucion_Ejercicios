/*
 * Prompt inicial utilizado:
 *Desarrolla un codigo en java para poder obtener un elemento a partir de su posicion en una lista enlazada
 .A partir de una lista enlazada simple de enteros, tengo
 * que implementar un método int obtener(int posicion) que devuelva el dato guardado en esa
 * posición (contando desde 0), validando las posiciones inválidas.
 *
 * Estructura de clases: lo mismo de los ejercicios anteriores, un Nodo con dato (int) y
 * siguiente (referencia a Nodo), y una clase ListaEnlazada con head y size. A esa clase le
 * agrego el método obtener.
 *
 * Razonamiento a explicar con comentarios (lo importante del ejercicio):
 * - Aunque el método reciba una "posición", la lista enlazada NO tiene acceso directo como un
 *   arreglo. En un arreglo iría directo a arreglo[posicion], pero acá los nodos no están
 *   contiguos y solo conocemos head, así que no hay forma de "saltar" a la posición N.
 * - Por eso hay que RECORRER nodo por nodo: arranco un auxiliar en head y un contador en 0, y
 *   voy avanzando (auxiliar = auxiliar.siguiente, contador++) hasta que el contador llegue a la
 *   posición pedida. El nodo donde me detengo es el que guarda el dato que debo devolver.
 *
 * Validación de posiciones inválidas (antes de recorrer): posicion < 0 es inválida y
 * posicion >= size es inválida (no existe ese nodo). Como el método devuelve int (no puede
 * devolver null), lanza IndexOutOfBoundsException con un mensaje claro. Conviene validar ANTES
 * de recorrer para no perder tiempo avanzando sobre una posición que sabemos que no existe.
 *
 * Casos a contemplar: posición negativa (-1) => excepción; posición >= size (5 con size 3) =>
 * excepción; lista vacía => cualquier posición es inválida; posición 0 => dato del head;
 * posición del medio; última posición válida (size - 1).
 *
 * En el main: demostración con aserciones (10 -> 20 -> 30 -> 40, obtener(0), obtener(2),
 * obtener(size-1), y que obtener(-1) y obtener(size) lancen excepción), más un menú por consola
 * para cargar datos y pedir una posición.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_3;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con acceso por posición mediante recorrido secuencial.
 *
 * <p>Aunque {@link #obtener(int)} reciba una posición, la lista NO tiene acceso directo como
 * un arreglo: los nodos no son contiguos en memoria y solo se conoce {@code head}, así que la
 * única forma de llegar a la posición N es recorrer los nodos uno por uno.</p>
 */
public class Obtener_Elemento {

	/** Nodo de la lista: guarda el dato y una referencia al nodo siguiente. */
	private static final class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}
	}

	/** Referencia al primer nodo. Es null cuando la lista está vacía. */
	private Nodo head;

	/** Cantidad de nodos en la lista. */
	private int size;

	public Obtener_Elemento() {
		this.head = null;
		this.size = 0;
	}

	/** Inserta un nodo al inicio (el nuevo pasa a ser el head). */
	public void insertarAlInicio(int dato) {
		Nodo nuevo = new Nodo(dato);
		nuevo.siguiente = head;
		head = nuevo;
		size++;
	}

	/** Inserta un nodo al final, recorriendo hasta el último nodo. */
	public void insertarAlFinal(int dato) {
		Nodo nuevo = new Nodo(dato);
		if (head == null) {
			head = nuevo;
		} else {
			Nodo auxiliar = head;
			while (auxiliar.siguiente != null) {
				auxiliar = auxiliar.siguiente;
			}
			auxiliar.siguiente = nuevo;
		}
		size++;
	}

	/**
	 * Devuelve el dato almacenado en la posición indicada (0 = head).
	 *
	 * <p>A diferencia de un arreglo ({@code arreglo[posicion]}), acá no se puede ir directo a la
	 * posición: los nodos no son contiguos y solo conocemos {@code head}. Por eso se recorre con
	 * un auxiliar y un contador hasta llegar a la posición pedida.</p>
	 *
	 * @param posicion índice del nodo a consultar, desde 0 hasta {@code size - 1}.
	 * @return el dato guardado en esa posición.
	 * @throws IndexOutOfBoundsException si {@code posicion < 0} o {@code posicion >= size}.
	 */
	public int obtener(int posicion) {
		// Valido ANTES de recorrer: si la posición no existe no tiene sentido avanzar.
		if (posicion < 0 || posicion >= size) {
			throw new IndexOutOfBoundsException(
					"Posición inválida: " + posicion + ". Rango válido: 0 a " + (size - 1));
		}

		Nodo auxiliar = head; // arranco desde el primer nodo
		int contador = 0;
		// Avanzo nodo por nodo hasta llegar a la posición pedida (sin acceso directo).
		while (contador < posicion) {
			auxiliar = auxiliar.siguiente;
			contador++;
		}
		return auxiliar.dato;
	}

	/** Recorre la lista desde head y muestra los datos con " -> ", terminando en null. */
	public void imprimir() {
		if (estaVacia()) {
			System.out.println("(lista vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo auxiliar = head;
		while (auxiliar != null) {
			texto.append(auxiliar.dato).append(" -> ");
			auxiliar = auxiliar.siguiente;
		}
		texto.append("null");
		System.out.println(texto);
	}

	/** La lista está vacía cuando no hay ningún nodo referenciado por head. */
	public boolean estaVacia() {
		return head == null;
	}

	/** Devuelve la cantidad de nodos almacenados. */
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

	/** Comprueba que una operación lance IndexOutOfBoundsException. */
	private static void comprobarExcepcion(Runnable operacion, String mensaje) {
		try {
			operacion.run();
			throw new AssertionError(mensaje + " (no lanzó excepción)");
		} catch (IndexOutOfBoundsException esperada) {
			// Comportamiento esperado.
		}
	}

	private static Obtener_Elemento construir(int... datos) {
		Obtener_Elemento lista = new Obtener_Elemento();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" OBTENER ELEMENTO POR POSICIÓN");
		System.out.println("====================================");

		Obtener_Elemento lista = construir(10, 20, 30, 40);
		System.out.print("\nLista: ");
		lista.imprimir();
		System.out.println("Tamaño: " + lista.getSize());

		System.out.println("\nPosiciones válidas:");
		comprobar(lista.obtener(0) == 10, "obtener(0) debe devolver 10 (head).");
		System.out.println("  obtener(0) -> " + lista.obtener(0) + "  (head)");
		comprobar(lista.obtener(2) == 30, "obtener(2) debe devolver 30 (medio).");
		System.out.println("  obtener(2) -> " + lista.obtener(2) + "  (posición del medio)");
		comprobar(lista.obtener(lista.getSize() - 1) == 40, "obtener(size-1) debe devolver 40.");
		System.out.println("  obtener(size-1) -> " + lista.obtener(lista.getSize() - 1)
				+ "  (última posición)");

		System.out.println("\nPosiciones inválidas (deben lanzar excepción):");
		comprobarExcepcion(() -> lista.obtener(-1), "obtener(-1) debe lanzar excepción.");
		System.out.println("  obtener(-1) -> IndexOutOfBoundsException  [Correcto]");
		comprobarExcepcion(() -> lista.obtener(lista.getSize()), "obtener(size) debe lanzar excepción.");
		System.out.println("  obtener(size) -> IndexOutOfBoundsException  [Correcto]");

		Obtener_Elemento vacia = new Obtener_Elemento();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		comprobarExcepcion(() -> vacia.obtener(0), "En lista vacía obtener(0) debe lanzar excepción.");
		System.out.println("  obtener(0) en lista vacía -> IndexOutOfBoundsException  [Correcto]");

		System.out.println("\n[Correcto] Acceso por posición verificado en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Obtener_Elemento lista) {
		System.out.print("  Lista actual: ");
		lista.imprimir();
		System.out.println("  Tamaño: " + lista.getSize()
				+ " | Posiciones válidas: 0 a " + (lista.getSize() - 1));
	}

	/** Lee un entero del usuario; repite el pedido si la entrada no es un número. */
	private static Integer leerEntero(Scanner scanner, String mensaje) {
		while (scanner.hasNextLine()) {
			System.out.print(mensaje);
			String entrada = scanner.nextLine().trim();
			try {
				return Integer.valueOf(entrada);
			} catch (NumberFormatException exception) {
				System.out.println("  Entrada no válida. Ingrese un número entero.");
			}
		}
		return null;
	}

	/** Menú interactivo para cargar datos y obtener un valor por posición. */
	private static void ejecutarMenu(Obtener_Elemento lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Obtener elemento por posición");
			System.out.println("4. Imprimir la lista");
			System.out.println("5. Ver tamaño");
			System.out.println("0. Salir");

			Integer opcion = leerEntero(scanner, "Seleccione una opción: ");
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					Integer alInicio = leerEntero(scanner, "Ingrese el entero a insertar al inicio: ");
					if (alInicio == null) {
						continuar = false;
						break;
					}
					lista.insertarAlInicio(alInicio);
					System.out.println("  Insertado al inicio: " + alInicio);
					mostrarLista(lista);
					break;
				case 2:
					Integer alFinal = leerEntero(scanner, "Ingrese el entero a insertar al final: ");
					if (alFinal == null) {
						continuar = false;
						break;
					}
					lista.insertarAlFinal(alFinal);
					System.out.println("  Insertado al final: " + alFinal);
					mostrarLista(lista);
					break;
				case 3:
					Integer posicion = leerEntero(scanner, "Ingrese la posición a consultar: ");
					if (posicion == null) {
						continuar = false;
						break;
					}
					try {
						System.out.println("  obtener(" + posicion + ") -> " + lista.obtener(posicion));
					} catch (IndexOutOfBoundsException exception) {
						System.out.println("  Error: " + exception.getMessage());
					}
					break;
				case 4:
					mostrarLista(lista);
					break;
				case 5:
					System.out.println("  Tamaño: " + lista.getSize());
					break;
				default:
					System.out.println("  Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada. ¡Hasta luego!");
	}

	public static void main(String[] args) {
		demostrar();
		System.out.println("\nAhora podés consultar posiciones vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Obtener_Elemento(), scanner);
		}
	}
}
