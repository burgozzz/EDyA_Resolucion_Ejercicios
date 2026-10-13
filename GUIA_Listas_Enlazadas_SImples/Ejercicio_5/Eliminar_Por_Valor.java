/*
 * Prompt inicial utilizado:
 * 
 * Desarrolla un codigo en java que permita eliminar un nodo por su valor, sigue las indicaciones siguientes
 *
 * A partir de mi lista enlazada simple de enteros, tengo que implementar el método
 * boolean eliminar(int dato) que elimine la PRIMERA aparición de ese dato en la lista y devuelva
 * true si lo eliminó, o false si el dato no estaba.
 *
 * Crea un Nodo con dato (int) y siguiente (referencia a Nodo), y una clase ListaEnlazada 
 * con head y size. A esa clase le agrego el método eliminar. Cuando elimino, decremento size.
 *
 * Razonamiento a explicar con comentarios: para eliminar un nodo hay que REENLAZAR sus vecinos, o
 * sea "saltearlo". Como solo avanzo en un sentido, conviene llevar DOS referencias mientras
 * recorro: "anterior" (el nodo previo al que estoy mirando) y "actual" (el nodo que estoy
 * evaluando). Cuando actual.dato == dato, hago anterior.siguiente = actual.siguiente, lo que deja
 * al nodo eliminado fuera de la cadena (ya nadie lo referencia). La referencia "anterior" es
 * imprescindible: sin ella no podría reconectar el nodo de atrás con el de adelante.
 *
 * En Java NO se borra el nodo manualmente (no hay free() como
 * en C). Lo que hago es dejar el nodo INACCESIBLE (romper el enlace que llegaba a él), y así el
 * Garbage Collector lo detecta como basura y libera esa memoria cuando le toque. El objeto sigue
 * un ratito en memoria hasta que actúe el GC.
 *
 * Casos para contemplar a la hora de hacer el codigo, lista vacía => false; eliminar el PRIMER nodo (dato en head) => head =
 * head.siguiente, sin "anterior", se trata aparte; eliminar un nodo del MEDIO => anterior.siguiente
 * = actual.siguiente; eliminar el ÚLTIMO nodo => anterior.siguiente = null; dato INEXISTENTE =>
 * recorro toda la lista y devuelvo false; tras eliminar, decrementar size y que imprimir() refleje
 * el cambio.
 *
 * En el main: demostración con aserciones mostrando la lista antes/después de eliminar el primer
 * nodo, uno del medio, el último, un dato inexistente y en lista vacía, comprobando el booleano y
 * el size, más un menú por consola para cargar datos y eliminar valores.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_5;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con eliminación por valor (primera aparición).
 *
 * <p>Eliminar un nodo no lo borra manualmente: solo se reenlaza la lista para dejarlo
 * inaccesible y que el Garbage Collector libere su memoria.</p>
 */
public class Eliminar_Por_Valor {

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

	public Eliminar_Por_Valor() {
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
	 * Elimina la primera aparición de {@code dato} y devuelve si hubo eliminación.
	 *
	 * <p>El nodo no se borra "a mano": solo se reenlaza la lista para saltearlo, dejándolo
	 * inaccesible. A partir de ahí el Garbage Collector lo considera basura y libera su memoria
	 * (por eso el objeto puede seguir un rato en memoria hasta que el GC se ejecute).</p>
	 *
	 * @param dato valor a eliminar.
	 * @return {@code true} si se eliminó la primera aparición; {@code false} si no estaba.
	 */
	public boolean eliminar(int dato) {
		// Caso lista vacía: no hay nada que eliminar.
		if (head == null) {
			return false;
		}

		// Caso especial: el dato está en el PRIMER nodo (no hay "anterior").
		if (head.dato == dato) {
			// Al mover head al siguiente, el nodo viejo queda inaccesible -> GC.
			head = head.getSiguiente();
			size--;
			return true;
		}

		// Caso general: llevo "anterior" y "actual" para poder saltear el nodo encontrado.
		Nodo anterior = head;
		Nodo actual = head.getSiguiente();
		while (actual != null) {
			if (actual.dato == dato) {
				// Reenlazo: el anterior apunta al siguiente del eliminado.
				// Sirve para el medio (apunta a otro nodo) y para el último (apunta a null).
				// El nodo "actual" queda sin referencias -> Garbage Collector.
				anterior.setSiguiente(actual.getSiguiente());
				size--;
				return true;
			}
			anterior = actual;
			actual = actual.getSiguiente();
		}

		// Recorrí toda la lista y no estaba.
		return false;
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

	private static Eliminar_Por_Valor construir(int... datos) {
		Eliminar_Por_Valor lista = new Eliminar_Por_Valor();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" ELIMINAR NODO POR VALOR");
		System.out.println("====================================");

		// Caso lista vacía.
		Eliminar_Por_Valor vacia = new Eliminar_Por_Valor();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		comprobar(!vacia.eliminar(10), "En lista vacía eliminar debe devolver false.");
		System.out.println("  eliminar(10) -> false  [Correcto]");

		// Caso eliminar el PRIMER nodo.
		Eliminar_Por_Valor primero = construir(10, 20, 30, 40);
		System.out.print("\nEliminar el PRIMER nodo - antes: ");
		primero.imprimir();
		comprobar(primero.eliminar(10), "Eliminar el primer nodo debe devolver true.");
		System.out.print("  eliminar(10) -> true, después: ");
		primero.imprimir();
		comprobar(primero.getSize() == 3, "Tras eliminar el primero, size debe ser 3.");

		// Caso eliminar un nodo del MEDIO.
		Eliminar_Por_Valor medio = construir(10, 20, 30, 40);
		System.out.print("\nEliminar un nodo del MEDIO - antes: ");
		medio.imprimir();
		comprobar(medio.eliminar(30), "Eliminar un nodo del medio debe devolver true.");
		System.out.print("  eliminar(30) -> true, después: ");
		medio.imprimir();
		comprobar(medio.getSize() == 3, "Tras eliminar del medio, size debe ser 3.");

		// Caso eliminar el ÚLTIMO nodo.
		Eliminar_Por_Valor ultimo = construir(10, 20, 30, 40);
		System.out.print("\nEliminar el ÚLTIMO nodo - antes: ");
		ultimo.imprimir();
		comprobar(ultimo.eliminar(40), "Eliminar el último nodo debe devolver true.");
		System.out.print("  eliminar(40) -> true, después: ");
		ultimo.imprimir();
		comprobar(ultimo.getSize() == 3, "Tras eliminar el último, size debe ser 3.");

		// Caso dato INEXISTENTE.
		Eliminar_Por_Valor inexistente = construir(10, 20, 30);
		System.out.print("\nDato INEXISTENTE - antes: ");
		inexistente.imprimir();
		comprobar(!inexistente.eliminar(99), "Eliminar un dato inexistente debe devolver false.");
		System.out.print("  eliminar(99) -> false, sin cambios: ");
		inexistente.imprimir();
		comprobar(inexistente.getSize() == 3, "Un dato inexistente no debe cambiar size.");

		// Caso primera aparición con repetidos.
		Eliminar_Por_Valor repetidos = construir(10, 20, 10, 30);
		System.out.print("\nRepetidos (elimina la primera aparición) - antes: ");
		repetidos.imprimir();
		comprobar(repetidos.eliminar(10), "Con repetidos debe eliminar la primera aparición.");
		System.out.print("  eliminar(10) -> true, después: ");
		repetidos.imprimir();

		System.out.println("\n[Correcto] Eliminación por valor verificada en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Eliminar_Por_Valor lista) {
		System.out.print("  Lista actual: ");
		lista.imprimir();
		System.out.println("  Tamaño: " + lista.getSize());
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

	/** Menú interactivo para cargar datos y eliminar valores. */
	private static void ejecutarMenu(Eliminar_Por_Valor lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Eliminar por valor");
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
					Integer aEliminar = leerEntero(scanner, "Ingrese el entero a eliminar: ");
					if (aEliminar == null) {
						continuar = false;
						break;
					}
					boolean eliminado = lista.eliminar(aEliminar);
					System.out.println("  eliminar(" + aEliminar + ") -> " + eliminado
							+ (eliminado ? "  (eliminado)" : "  (el dato no estaba)"));
					mostrarLista(lista);
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
		System.out.println("\nAhora podés eliminar valores vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Eliminar_Por_Valor(), scanner);
		}
	}
}
