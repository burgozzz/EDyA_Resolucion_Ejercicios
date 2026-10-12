/*
 * Prompt inicial utilizado:
	Desarrolla un codigo en java para implementar la busqueda de un elemento en un lista enlazada
	.A partir de una lista enlazada simple de enteros,tengo que implementar un método boolean 
	buscar(int dato) que devuelva true si el dato está en la lista y false si no está.
 *
 * Usa la misma idea del ejercicio anterior con un Nodo que tenga dato
 * (int) y siguiente (referencia a Nodo), y una clase ListaEnlazada con la referencia head y
 * el contador size. A esa clase le agrego el método buscar.
 *
 * Razonamiento a explicar con comentarios:
 * - Por qué en esta lista NO se puede acceder directamente por índice como en un arreglo: los
 *   nodos no están uno al lado del otro en memoria, cada uno solo sabe dónde está el siguiente,
 *   así que no hay forma de "saltar" a la posición N.
 * - Por qué la búsqueda necesita recorrido secuencial: hay que arrancar desde head y avanzar
 *   nodo por nodo con un auxiliar (auxiliar = auxiliar.siguiente) hasta encontrar el dato o
 *   hasta que auxiliar sea null (fin de la lista).
 *
 * buscar(int dato): recorrer desde head con un auxiliar; si coincide, devolver true (cortar
 * ahí); si llego a null sin encontrarlo, devolver false.
 *
 * Casos a contemplar: lista vacía (head == null) => false sin explotar; dato en el primer
 * nodo (head); dato en el medio; dato en el último nodo; dato inexistente; y datos repetidos
 * (devolver true en la primera coincidencia).
 *
 * En el main: demostración con varios enteros probando un dato existente, uno inexistente y
 * búsqueda en lista vacía, más un menú por consola para insertar datos y buscar valores.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_2;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con búsqueda secuencial.
 *
 * <p>A diferencia de un arreglo, los nodos NO están contiguos en memoria: cada nodo solo
 * conoce a su {@code siguiente}. Por eso no se puede acceder por índice y la búsqueda
 * obliga a recorrer la lista desde {@code head}, nodo por nodo, hasta encontrar el dato o
 * llegar a {@code null}.</p>
 */
public class Buscar_Elemento {

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

	public Buscar_Elemento() {
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
	 * Busca un dato recorriendo la lista desde head (búsqueda secuencial).
	 *
	 * <p>No se puede acceder por índice porque los nodos no son contiguos: no existe una
	 * posición N a la que "saltar". Solo se puede avanzar de un nodo a su siguiente. El
	 * recorrido se corta apenas se encuentra el dato; si se llega a null, no está.</p>
	 *
	 * @param dato valor a buscar.
	 * @return {@code true} si el dato aparece al menos una vez; {@code false} en caso contrario.
	 */
	public boolean buscar(int dato) {
		Nodo auxiliar = head; // arranco desde el primer nodo
		while (auxiliar != null) {
			if (auxiliar.dato == dato) {
				return true; // encontrado: corto el recorrido
			}
			auxiliar = auxiliar.siguiente; // avanzo al siguiente nodo
		}
		return false; // llegué a null sin encontrarlo
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

	private static Buscar_Elemento construir(int... datos) {
		Buscar_Elemento lista = new Buscar_Elemento();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" BUSCAR EN LISTA ENLAZADA SIMPLE");
		System.out.println("====================================");

		// Caso 1: lista vacía.
		Buscar_Elemento vacia = new Buscar_Elemento();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		comprobar(!vacia.buscar(10), "Buscar en lista vacía debe devolver false.");
		System.out.println("buscar(10) en lista vacía -> false  [Correcto]");

		// Caso 2: lista con varios elementos.
		Buscar_Elemento lista = construir(10, 20, 30, 40, 50);
		System.out.print("\nLista: ");
		lista.imprimir();

		System.out.println("\nBúsquedas:");
		comprobar(lista.buscar(10), "10 está en el head -> true.");
		System.out.println("  buscar(10) -> " + lista.buscar(10) + "  (primer nodo/head)");
		comprobar(lista.buscar(30), "30 está en el medio -> true.");
		System.out.println("  buscar(30) -> " + lista.buscar(30) + "  (nodo del medio)");
		comprobar(lista.buscar(50), "50 está en el último nodo -> true.");
		System.out.println("  buscar(50) -> " + lista.buscar(50) + "  (último nodo)");
		comprobar(!lista.buscar(99), "99 no existe -> false.");
		System.out.println("  buscar(99) -> " + lista.buscar(99) + "  (no existe)");

		// Caso 3: datos repetidos (debe devolver true en la primera coincidencia).
		Buscar_Elemento repetidos = construir(7, 7, 8);
		System.out.print("\nLista con repetidos: ");
		repetidos.imprimir();
		comprobar(repetidos.buscar(7), "Con datos repetidos debe devolver true.");
		System.out.println("  buscar(7) -> " + repetidos.buscar(7) + "  (repetido)");

		System.out.println("\n[Correcto] Búsqueda secuencial verificada en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Buscar_Elemento lista) {
		System.out.print("  Lista actual: ");
		lista.imprimir();
		System.out.println("  Tamaño: " + lista.getSize() + " | ¿Vacía? " + lista.estaVacia());
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

	/** Menú interactivo para insertar datos y buscar valores. */
	private static void ejecutarMenu(Buscar_Elemento lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Buscar un dato");
			System.out.println("4. Imprimir la lista");
			System.out.println("5. Ver tamaño y estado");
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
					Integer aBuscar = leerEntero(scanner, "Ingrese el entero que desea buscar: ");
					if (aBuscar == null) {
						continuar = false;
						break;
					}
					boolean encontrado = lista.buscar(aBuscar);
					System.out.println("  buscar(" + aBuscar + ") -> " + encontrado
							+ (encontrado ? "  (el dato está en la lista)" : "  (el dato NO está)"));
					break;
				case 4:
					mostrarLista(lista);
					break;
				case 5:
					System.out.println("  Tamaño: " + lista.getSize()
							+ " | ¿Vacía? " + lista.estaVacia());
					break;
				default:
					System.out.println("  Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada. ¡Hasta luego!");
	}

	public static void main(String[] args) {
		demostrar();
		System.out.println("\nAhora podés probar la búsqueda vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Buscar_Elemento(), scanner);
		}
	}
}
