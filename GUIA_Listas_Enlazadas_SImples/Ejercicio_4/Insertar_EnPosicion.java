/*
 * Prompt inicial utilizado:
 * Desarrolla un codigo para innsertar un nodo con un dato en la posicion asignada de la lista
 * .A partir de mi lista enlazada simple de enteros, tengo que implementar el método
 * void insertarEnPosicion(int dato, int posicion) que inserte un nodo nuevo con ese dato en la
 * posición indicada (contando desde 0). Tiene que servir para insertar al inicio, en el medio y
 * al final, y validar posiciones inválidas.
 *
 * Crea un Nodo con dato (int) y siguiente (referencia a Nodo),
 * y una clase ListaEnlazada con head y size. A esa clase le agrego insertarEnPosicion.
 *
 * Validación de posiciones, posicion < 0 es inválida y posicion > size es
 * inválida. Acá SÍ se permite posicion == size, porque insertar justo al final es válido (en
 * obtener era inválido posicion >= size; acá el tope es size inclusive). Si es inválida, lanza
 * IndexOutOfBoundsException con un mensaje claro y NO modifica la lista.
 *
 * Razonamiento a explicar con comentarios :el orden correcto
 * de actualización de referencias es nuevo.setSiguiente(actual.getSiguiente()); y luego
 * actual.setSiguiente(nuevo);, donde "actual" es el nodo que queda ANTES de la posición donde
 * quiero insertar. Primero el nodo nuevo "agarra" la referencia al nodo que sigue (así no pierdo
 * el resto de la lista) y recién después el nodo anterior apunta al nuevo. Si lo hago al revés,
 * si primero hago actual.setSiguiente(nuevo), pierdo la referencia al nodo que venía después y
 * cuando quiero hacer nuevo.setSiguiente(actual.getSiguiente()) me termino apuntando a mí mismo
 * (un ciclo) y se corta la lista.
 *
 * Casos especiales: insertar al inicio (posicion 0) actualiza head y sirve tanto con lista vacía
 * como con nodos; insertar en el medio recorre hasta el nodo anterior; insertar al final
 * (posicion == size) engancha el nuevo al último. En todos los casos se incrementa size.
 *
 * Casos a contemplar: lista vacía con posicion 0; inicio de lista con elementos; medio; final
 * (posicion == size); posicion negativa => excepción; posicion > size => excepción; y que tras
 * insertar, size e imprimir() reflejen el cambio.
 *
 * En el main: demostración con aserciones mostrando la lista antes/después de insertar al inicio,
 * en el medio y al final, y que posicion -1 y posicion > size lancen excepción, más un menú por
 * consola para cargar datos e insertar en la posición elegida.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_4;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con inserción en una posición específica.
 *
 * <p>Insertar en el medio exige actualizar referencias en el orden correcto: primero el nodo
 * nuevo engancha al que sigue y después el nodo anterior apunta al nuevo. Hacerlo al revés
 * pierde la cola de la lista y genera un ciclo.</p>
 */
public class Insertar_EnPosicion {

	/** Nodo de la lista: guarda el dato y una referencia al nodo siguiente. */
	private static final class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}

		private Nodo getSiguiente() {
			return siguiente;
		}

		private void setSiguiente(Nodo siguiente) {
			this.siguiente = siguiente;
		}
	}

	/** Referencia al primer nodo. Es null cuando la lista está vacía. */
	private Nodo head;

	/** Cantidad de nodos en la lista. */
	private int size;

	public Insertar_EnPosicion() {
		this.head = null;
		this.size = 0;
	}

	/** Inserta un nodo al inicio (el nuevo pasa a ser el head). */
	public void insertarAlInicio(int dato) {
		Nodo nuevo = new Nodo(dato);
		nuevo.setSiguiente(head);
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
			while (auxiliar.getSiguiente() != null) {
				auxiliar = auxiliar.getSiguiente();
			}
			auxiliar.setSiguiente(nuevo);
		}
		size++;
	}

	/**
	 * Inserta un nodo con {@code dato} en la posición indicada (0 = head).
	 *
	 * <p>Se permite {@code posicion == size} para insertar justo al final. Las posiciones
	 * inválidas son {@code posicion < 0} y {@code posicion > size}.</p>
	 *
	 * @param dato     valor a almacenar.
	 * @param posicion índice donde insertar, de 0 a {@code size} inclusive.
	 * @throws IndexOutOfBoundsException si {@code posicion < 0} o {@code posicion > size}.
	 */
	public void insertarEnPosicion(int dato, int posicion) {
		// Valido ANTES de tocar la lista: si la posición no sirve, no modifico nada.
		if (posicion < 0 || posicion > size) {
			throw new IndexOutOfBoundsException(
					"Posición inválida: " + posicion + ". Rango válido: 0 a " + size);
		}

		Nodo nuevo = new Nodo(dato);

		if (posicion == 0) {
			// Caso inicio: el nuevo pasa a ser head (sirve con lista vacía o con nodos).
			nuevo.setSiguiente(head);
			head = nuevo;
		} else {
			// Busco el nodo ANTERIOR a la posición donde quiero insertar.
			Nodo actual = head;
			for (int i = 0; i < posicion - 1; i++) {
				actual = actual.getSiguiente();
			}
			// ORDEN CORRECTO de actualización de referencias (no perder la cola!):
			// 1) el nuevo engancha al que sigue;
			nuevo.setSiguiente(actual.getSiguiente());
			// 2) recién ahora el anterior apunta al nuevo.
			actual.setSiguiente(nuevo);
			// Si invirtiera el orden, actual.setSiguiente(nuevo) primero haría que
			// actual.getSiguiente() ya sea "nuevo", formando un ciclo y perdiendo el resto.
		}

		size++;
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
			auxiliar = auxiliar.getSiguiente();
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

	private static Insertar_EnPosicion construir(int... datos) {
		Insertar_EnPosicion lista = new Insertar_EnPosicion();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" INSERTAR EN POSICIÓN ESPECÍFICA");
		System.out.println("====================================");

		// Lista vacía + insertar en 0.
		Insertar_EnPosicion vacia = new Insertar_EnPosicion();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		vacia.insertarEnPosicion(7, 0);
		System.out.print("insertarEnPosicion(7, 0) -> ");
		vacia.imprimir();
		comprobar(vacia.getSize() == 1 && !vacia.estaVacia(), "Insertar en lista vacía debe dejar 1 nodo.");

		// Lista base 10 -> 20 -> 30.
		Insertar_EnPosicion lista = construir(10, 20, 30);
		System.out.print("\nLista inicial: ");
		lista.imprimir();
		comprobar(lista.getSize() == 3, "La lista base debe tener tamaño 3.");

		System.out.println("\nInsertar al inicio (posicion 0):");
		lista.insertarEnPosicion(5, 0);
		System.out.print("  insertarEnPosicion(5, 0) -> ");
		lista.imprimir();
		comprobar(lista.getSize() == 4, "Tras insertar al inicio el tamaño debe ser 4.");

		System.out.println("\nInsertar en el medio (posicion 2):");
		lista.insertarEnPosicion(15, 2);
		System.out.print("  insertarEnPosicion(15, 2) -> ");
		lista.imprimir();
		comprobar(lista.getSize() == 5, "Tras insertar en el medio el tamaño debe ser 5.");

		System.out.println("\nInsertar al final (posicion == size):");
		int posicionFinal = lista.getSize();
		lista.insertarEnPosicion(99, posicionFinal);
		System.out.print("  insertarEnPosicion(99, " + posicionFinal + ") -> ");
		lista.imprimir();
		comprobar(lista.getSize() == 6, "Tras insertar al final el tamaño debe ser 6.");

		System.out.println("\nPosiciones inválidas (no deben modificar la lista):");
		int sizeAntes = lista.getSize();
		comprobarExcepcion(() -> lista.insertarEnPosicion(1, -1), "posicion -1 debe lanzar excepción.");
		System.out.println("  insertarEnPosicion(1, -1) -> IndexOutOfBoundsException  [Correcto]");
		comprobarExcepcion(() -> lista.insertarEnPosicion(1, lista.getSize() + 1),
				"posicion > size debe lanzar excepción.");
		System.out.println("  insertarEnPosicion(1, size+1) -> IndexOutOfBoundsException  [Correcto]");
		comprobar(lista.getSize() == sizeAntes, "Las posiciones inválidas no deben modificar la lista.");

		System.out.println("\n[Correcto] Inserción en posición verificada en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Insertar_EnPosicion lista) {
		System.out.print("  Lista actual: ");
		lista.imprimir();
		System.out.println("  Tamaño: " + lista.getSize()
				+ " | Posiciones válidas para insertar: 0 a " + lista.getSize());
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

	/** Menú interactivo para cargar datos e insertar en una posición. */
	private static void ejecutarMenu(Insertar_EnPosicion lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Insertar en una posición");
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
					Integer dato = leerEntero(scanner, "Ingrese el entero a insertar: ");
					if (dato == null) {
						continuar = false;
						break;
					}
					Integer posicion = leerEntero(scanner, "Ingrese la posición (0 a "
							+ lista.getSize() + "): ");
					if (posicion == null) {
						continuar = false;
						break;
					}
					try {
						lista.insertarEnPosicion(dato, posicion);
						System.out.println("  Insertado " + dato + " en la posición " + posicion);
						mostrarLista(lista);
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
		System.out.println("\nAhora podés insertar en la posición que quieras con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Insertar_EnPosicion(), scanner);
		}
	}
}
