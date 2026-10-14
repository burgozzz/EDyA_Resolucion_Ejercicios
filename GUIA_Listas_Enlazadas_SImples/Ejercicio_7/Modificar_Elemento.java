/*
 * Prompt inicial utilizado:
 *
 * Desarrolla un codigo en java que permita modificar el elemento de un nodo que se encuentra
 * en una lista enlazada
 * El problema: a partir de mi lista enlazada simple de enteros, tengo que implementar el método
 * void modificar(int posicion, int nuevoDato) que cambie el dato almacenado en la posición
 * indicada (contando desde 0) por nuevoDato.
 *
 * Estructura de clases: igual que en los ejercicios anteriores, un Nodo y una clase ListaEnlazada
 * con head y size. A esa clase le agrego el método modificar. Un detalle importante: en los
 * ejercicios anteriores el dato del Nodo lo puse final porque nunca cambiaba, pero acá SÍ necesito
 * cambiarlo, así que el campo dato NO es final (uso un setDato).
 *
 * Validación de la posición ANTES de recorrer: posicion < 0 es inválida y posicion >= size es
 * inválida. Si es inválida, lanza IndexOutOfBoundsException con un mensaje claro y NO modifica nada.
 *
 * Razonamiento a explicar con comentarios: la diferencia entre modificar
 * el DATO de un nodo y modificar la REFERENCIA al siguiente.
 * - Modificar el dato: cambio el valor que guarda el nodo (nodo.dato = nuevoDato). La ESTRUCTURA
 *   de la lista y todos los enlaces "siguiente" quedan EXACTAMENTE igual; la cantidad de nodos
 *   (size) no cambia; solo cambia el contenido de un nodo.
 * - Modificar la referencia: cambiar nodo.siguiente altera cómo se conectan los nodos, o sea
 *   cambia la estructura de la lista (es lo que hacíamos al insertar o eliminar). Puede reordenar,
 *   cortar o perder nodos.
 * En este método SOLO toco el dato, nunca el enlace, y tampoco cambia size.
 *
 * Cómo funciona modificar: recorro desde head con un auxiliar y un contador hasta la posición
 * (igual que en obtener); cuando llego, hago auxiliar.dato = nuevoDato y no toco ningún "siguiente".
 *
 * Casos a contemplar: lista vacía => excepción; posicion negativa => excepción; posicion >= size =>
 * excepción; modificar la posición 0 (head); una posición del medio; la última (size - 1); y que
 * tras modificar, imprimir() muestre el valor nuevo y que size NO cambie.
 *
 * En el main: demostración con aserciones mostrando la lista antes/después de modificar en 0, en el
 * medio y en la última posición, y que posiciones negativas y >= size lancen excepción sin cambiar
 * la lista, más un menú por consola para cargar datos y modificar por posición.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_7;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con modificación del dato por posición.
 *
 * <p>Modificar cambia únicamente el dato de un nodo: NO toca las referencias {@code siguiente},
 * así que la estructura de la lista y su tamaño quedan igual.</p>
 */
public class Modificar_Elemento {

	/** Nodo de la lista: guarda el dato y una referencia al nodo siguiente. */
	private static final class Nodo {
		// OJO: dato NO es final, porque este ejercicio lo modifica.
		private int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}

		private int getDato() {
			return dato;
		}

		private void setDato(int dato) {
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

	public Modificar_Elemento() {
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
	 * Cambia el dato del nodo que está en la posición indicada (0 = head).
	 *
	 * <p>Solo se modifica el DATO del nodo; las referencias {@code siguiente} no se tocan, por lo
	 * que la estructura de la lista y {@code size} quedan iguales.</p>
	 *
	 * @param posicion   índice del nodo a modificar, de 0 a {@code size - 1}.
	 * @param nuevoDato  nuevo valor a almacenar.
	 * @throws IndexOutOfBoundsException si {@code posicion < 0} o {@code posicion >= size}.
	 */
	public void modificar(int posicion, int nuevoDato) {
		// Valido ANTES de recorrer: si la posición no existe no tiene sentido avanzar.
		if (posicion < 0 || posicion >= size) {
			throw new IndexOutOfBoundsException(
					"Posición inválida: " + posicion + ". Rango válido: 0 a " + (size - 1));
		}

		// Recorro nodo por nodo hasta la posición pedida (sin acceso directo).
		Nodo auxiliar = head;
		int contador = 0;
		while (contador < posicion) {
			auxiliar = auxiliar.getSiguiente();
			contador++;
		}

		// Modifico SOLO el dato del nodo. No toco auxiliar.siguiente:
		// si cambiara el enlace estaría alterando la ESTRUCTURA de la lista, no su contenido.
		auxiliar.setDato(nuevoDato);
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
			texto.append(auxiliar.getDato()).append(" -> ");
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

	private static Modificar_Elemento construir(int... datos) {
		Modificar_Elemento lista = new Modificar_Elemento();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" MODIFICAR ELEMENTO POR POSICIÓN");
		System.out.println("====================================");

		Modificar_Elemento lista = construir(10, 20, 30, 40);
		System.out.print("\nLista inicial: ");
		lista.imprimir();
		int sizeInicial = lista.getSize();

		System.out.println("\nModificar la posicion 0 (head):");
		lista.modificar(0, 99);
		System.out.print("  modificar(0, 99) -> ");
		lista.imprimir();
		comprobar(lista.getSize() == sizeInicial, "Modificar no debe cambiar size.");

		System.out.println("\nModificar una posición del MEDIO (posicion 2):");
		lista.modificar(2, 77);
		System.out.print("  modificar(2, 77) -> ");
		lista.imprimir();

		System.out.println("\nModificar la ÚLTIMA posición (size-1):");
		lista.modificar(lista.getSize() - 1, 55);
		System.out.print("  modificar(size-1, 55) -> ");
		lista.imprimir();
		comprobar(lista.getSize() == sizeInicial, "Tras modificar, size debe seguir igual.");

		System.out.println("\nPosiciones inválidas (no deben modificar la lista):");
		int sizeAntes = lista.getSize();
		comprobarExcepcion(() -> lista.modificar(-1, 0), "posicion -1 debe lanzar excepción.");
		System.out.println("  modificar(-1, 0) -> IndexOutOfBoundsException  [Correcto]");
		comprobarExcepcion(() -> lista.modificar(lista.getSize(), 0),
				"posicion >= size debe lanzar excepción.");
		System.out.println("  modificar(size, 0) -> IndexOutOfBoundsException  [Correcto]");
		comprobar(lista.getSize() == sizeAntes, "Las posiciones inválidas no deben cambiar size.");

		Modificar_Elemento vacia = new Modificar_Elemento();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		comprobarExcepcion(() -> vacia.modificar(0, 1), "En lista vacía debe lanzar excepción.");
		System.out.println("  modificar(0, 1) en lista vacía -> IndexOutOfBoundsException  [Correcto]");

		System.out.println("\n[Correcto] Modificación por posición verificada en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Modificar_Elemento lista) {
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

	/** Menú interactivo para cargar datos y modificar por posición. */
	private static void ejecutarMenu(Modificar_Elemento lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Modificar por posición");
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
					Integer posicion = leerEntero(scanner, "Ingrese la posición a modificar: ");
					if (posicion == null) {
						continuar = false;
						break;
					}
					Integer nuevoDato = leerEntero(scanner, "Ingrese el nuevo dato: ");
					if (nuevoDato == null) {
						continuar = false;
						break;
					}
					try {
						lista.modificar(posicion, nuevoDato);
						System.out.println("  Posición " + posicion + " modificada a " + nuevoDato);
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
		System.out.println("\nAhora podés modificar posiciones vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Modificar_Elemento(), scanner);
		}
	}
}
