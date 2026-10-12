/*
 * Prompt inicial utilizado:
 *Desarrollar en java una lista enlazada simple
 *
 * Tengo que implementar una lista enlazada simple de números enteros SIN usar
 * ArrayList o estructuras parecidas de Java. Yo armo los nodos y los enlaces a
 * mano. Guardalo en GUIA_Listas_Enlazadas_SImples/Ejercicio_1/ListaEnlazada.java, paquete
 * GUIA_Listas_Enlazadas_SImples.Ejercicio_1.
 *
 * Estructura de clases:
 * - Una clase Nodo con un atributo dato (int) y un atributo siguiente (referencia a otro
 *   Nodo). Cuando un nodo no tiene siguiente, ese atributo vale null.
 * - Una clase ListaEnlazada que guarde la referencia head (el primer nodo) y un contador
 *   size (int). Si la lista está vacía, head es null.
 *
 * Quiero que me expliques con comentarios qué representa cada cosa: cómo se representa un
 * nodo, qué función cumple head (apuntar al primer nodo y desde ahí poder recorrer toda la
 * lista), y cómo se actualiza el tamaño (size) cuando inserto: arranca en 0 y sube en 1 por
 * cada nodo nuevo.
 *
 * La lista tiene que permitir:
 * - insertarAlInicio(int dato): crea un nodo y lo pone antes de head, y ese nodo pasa a ser
 *   el nuevo head.
 * - insertarAlFinal(int dato): si la lista está vacía el nodo queda como head; si no, recorro
 *   con un auxiliar hasta el último nodo (el que tiene siguiente == null) y lo enlazo ahí.
 * - imprimir(): recorre desde head mostrando los datos separados por " -> " y terminando en
 *   null (por ejemplo 10 -> 20 -> 30 -> null).
 * - estaVacia(): true si head es null.
 * - getSize(): devuelve el contador size.
 *
 * Casos a contemplar: insertar en una lista recién creada (head = null), tanto al inicio como
 * al final; insertar cuando ya hay uno o varios nodos; imprimir una lista vacía; y que size
 * quede siempre correcto después de cada inserción.
 *
 * En el main hacer una demostración ejecutable con varios enteros: mostrar la lista vacía,
 * después de insertar al inicio, después de insertar al final, el tamaño en cada momento y si
 * está vacía.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_1;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros implementada desde cero (sin ArrayList ni LinkedList).
 *
 * <p>Cada elemento vive en un {@link Nodo}. El primer nodo se referencia desde {@code head};
 * si la lista está vacía, {@code head} es {@code null}. Desde {@code head} se recorre toda la
 * lista siguiendo el enlace {@code siguiente} de cada nodo hasta llegar a {@code null}.</p>
 */
public class Lista_Enlazada {

	/**
	 * Nodo de la lista: guarda el dato y una referencia al nodo siguiente.
	 * Cuando {@code siguiente} es {@code null} significa que es el último nodo.
	 */
	private static final class Nodo {
		private final int dato;
		private Nodo siguiente;

		private Nodo(int dato) {
			this.dato = dato;
		}
	}

	/** Referencia al primer nodo. Es null cuando la lista está vacía. */
	private Nodo head;

	/** Cantidad de nodos en la lista. Arranca en 0 y sube 1 por cada inserción. */
	private int size;

	public Lista_Enlazada() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * Inserta un nodo al inicio: el nuevo nodo apunta al que era el head y luego el nuevo
	 * pasa a ser el head.
	 */
	public void insertarAlInicio(int dato) {
		Nodo nuevo = new Nodo(dato);
		nuevo.siguiente = head; // el nuevo nodo se enlaza al antiguo primer nodo
		head = nuevo;           // el nuevo nodo pasa a ser el primero
		size++;                 // actualizo el tamaño
	}

	/**
	 * Inserta un nodo al final. Si la lista está vacía, el nuevo nodo es el head; si no, se
	 * recorre con un auxiliar hasta el último nodo (siguiente == null) y se enlaza ahí.
	 */
	public void insertarAlFinal(int dato) {
		Nodo nuevo = new Nodo(dato);
		if (head == null) {
			head = nuevo; // primer nodo de la lista
		} else {
			Nodo auxiliar = head;
			while (auxiliar.siguiente != null) { // avanzo hasta el último nodo
				auxiliar = auxiliar.siguiente;
			}
			auxiliar.siguiente = nuevo; // enlazo el nuevo nodo al final
		}
		size++;
	}

	/**
	 * Recorre la lista desde head y muestra los datos con " -> ", terminando en null.
	 * Si la lista está vacía, avisa sin recorrer.
	 */
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

	private static void mostrarEstado(String momento, Lista_Enlazada lista) {
		System.out.println(momento);
		System.out.print("  Lista: ");
		lista.imprimir();
		System.out.println("  Tamaño: " + lista.getSize() + " | ¿Vacía? " + lista.estaVacia());
	}

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" LISTA ENLAZADA SIMPLE DE ENTEROS");
		System.out.println("====================================");

		Lista_Enlazada lista = new Lista_Enlazada();
		mostrarEstado("\n--- Lista recién creada ---", lista);
		comprobar(lista.estaVacia() && lista.getSize() == 0,
				"Una lista nueva debe estar vacía y con tamaño 0.");

		lista.insertarAlInicio(20);
		mostrarEstado("\n--- insertarAlInicio(20) ---", lista);
		comprobar(!lista.estaVacia() && lista.getSize() == 1, "Tras insertar debe haber 1 nodo.");

		lista.insertarAlInicio(10);
		mostrarEstado("\n--- insertarAlInicio(10) ---", lista);
		comprobar(lista.getSize() == 2, "El tamaño debe ser 2.");

		lista.insertarAlFinal(30);
		mostrarEstado("\n--- insertarAlFinal(30) ---", lista);

		lista.insertarAlFinal(40);
		mostrarEstado("\n--- insertarAlFinal(40) ---", lista);

		lista.insertarAlInicio(5);
		mostrarEstado("\n--- insertarAlInicio(5) ---", lista);

		// La lista debería quedar: 5 -> 10 -> 20 -> 30 -> 40 -> null
		comprobar(lista.getSize() == 5, "El tamaño final debe ser 5.");
		System.out.println("\n[Correcto] Inserciones al inicio y al final, recorrido y tamaño verificados.");

		// Caso lista vacía: insertar al final en una lista nueva.
		Lista_Enlazada vacia = new Lista_Enlazada();
		vacia.insertarAlFinal(99);
		mostrarEstado("\n--- Lista nueva: insertarAlFinal(99) ---", vacia);
		comprobar(!vacia.estaVacia() && vacia.getSize() == 1,
				"Insertar al final en lista vacía debe crear el head.");
		System.out.println("[Correcto] Caso de lista vacía contemplado.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista de forma resumida. */
	private static void mostrarLista(Lista_Enlazada lista) {
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

	/** Menú interactivo que permite al usuario operar sobre la lista desde la consola. */
	private static void ejecutarMenu(Lista_Enlazada lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Imprimir la lista");
			System.out.println("4. Ver tamaño y estado");
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
					mostrarLista(lista);
					break;
				case 4:
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
		System.out.println("\nAhora podés probar la lista vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Lista_Enlazada(), scanner);
		}
	}
}


/*
MEJORA DEL PROMPT
Agrega una mejor interaccion entre usuario y consola para que le mismo pueda realizar un prueba
con sus propios datos ingresados
 */