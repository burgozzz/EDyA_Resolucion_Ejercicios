/*
 * Prompt inicial utilizado:
 *
 * Desarrolla un codigo en java que permita invertir la lista completa de una lista enlazada
 * El problema: a partir de mi lista enlazada simple de enteros, tengo que implementar el método
 * void invertir() que dé vuelta la lista en el lugar (in place), sin crear otra lista ni usar
 * estructuras auxiliares como arreglos o ArrayList. Ejemplo: 10 -> 20 -> 30 -> 40 -> null debe
 * quedar 40 -> 30 -> 20 -> 10 -> null.
 *
 * Estructura de clases: igual que en los ejercicios anteriores, un Nodo con dato (int) y siguiente
 * (referencia a Nodo), y una clase ListaEnlazada con head y size. A esa clase le agrego el método
 * invertir.
 *
 * Razonamiento a explicar con comentarios: qué referencias
 * auxiliares hacen falta para invertir SIN perder nodos. Necesito TRES: "anterior" (el nodo que va
 * a quedar como siguiente del actual, arranca en null), "actual" (el nodo que estoy procesando,
 * arranca en head) y "siguiente" (para no perder el resto de la lista antes de romper el enlace).
 * El orden de actualización de referencias es fundamental:
 *   mientras actual != null:
 *     1) siguiente = actual.siguiente;   // GUARDO el resto ANTES de tocar nada
 *     2) actual.siguiente = anterior;    // INVIERTO el enlace (apunta hacia atrás)
 *     3) anterior = actual;              // avanzo anterior
 *     4) actual = siguiente;             // avanzo actual usando lo que guardé
 *   al final, head = anterior (el último nodo original).
 * Si hiciera actual.siguiente = anterior ANTES de guardar siguiente, perdería la referencia al
 * resto de la lista y no podría seguir avanzando (se cortaría la lista). Por eso el paso 1 va
 * SIEMPRE primero.
 *
 * Casos a contemplar: lista vacía => no hace nada (head = null); un solo nodo => queda igual (es su
 * propia inversión); dos nodos => se intercambian; varios nodos (ejemplo de la guía 10 -> 20 -> 30
 * -> 40 => 40 -> 30 -> 20 -> 10); al invertir NO cambia size; después de invertir, imprimir() debe
 * mostrar el orden al revés.
 *
 * En el main: demostración con aserciones mostrando la lista antes/después de invertir con el
 * ejemplo de la guía, y probando lista vacía, un solo nodo y dos nodos, comprobando que size no
 * cambia, más un menú por consola para cargar datos e invertir.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_9;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con inversión in place.
 *
 * <p>Para invertir se usan tres referencias auxiliares ({@code anterior}, {@code actual},
 * {@code siguiente}) y se van dando vuelta los enlaces uno por uno. Guardar {@code siguiente} antes
 * de romper el enlace es imprescindible para no perder el resto de la lista.</p>
 */
public class Invertir_Lista {

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

	public Invertir_Lista() {
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
	 * Invierte la lista en el lugar (in place), sin estructuras auxiliares.
	 *
	 * <p>Se recorren los nodos dando vuelta cada enlace {@code siguiente}. Se necesitan tres
	 * referencias: {@code anterior}, {@code actual} y {@code siguiente}. El orden importa:
	 * primero se guarda {@code siguiente} (el resto de la lista), recién después se da vuelta el
	 * enlace; si se hiciera al revés, se perdería el resto de la lista.</p>
	 */
	public void invertir() {
		Nodo anterior = null; // el nuevo "siguiente" del nodo actual (arranca en null)
		Nodo actual = head;   // nodo que estoy procesando (arranca en head)

		while (actual != null) {
			Nodo siguiente = actual.getSiguiente(); // 1) GUARDO el resto ANTES de tocar nada
			actual.setSiguiente(anterior);          // 2) INVIERTO el enlace (apunta hacia atrás)
			anterior = actual;                      // 3) avanzo "anterior"
			actual = siguiente;                     // 4) avanzo "actual" usando lo que guardé
		}

		// El último nodo original quedó en "anterior": ahora es el nuevo head.
		head = anterior;
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

	private static Invertir_Lista construir(int... datos) {
		Invertir_Lista lista = new Invertir_Lista();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" INVERTIR LISTA ENLAZADA SIMPLE");
		System.out.println("====================================");

		// Ejemplo de la guía.
		Invertir_Lista lista = construir(10, 20, 30, 40);
		int sizeInicial = lista.getSize();
		System.out.print("\nAntes:   ");
		lista.imprimir();
		lista.invertir();
		System.out.print("Después: ");
		lista.imprimir();
		comprobar(lista.getSize() == sizeInicial, "Invertir no debe cambiar size.");
		System.out.println("  (size sigue siendo " + lista.getSize() + ")");

		// Lista vacía.
		Invertir_Lista vacia = new Invertir_Lista();
		System.out.print("\nLista vacía - antes: ");
		vacia.imprimir();
		vacia.invertir();
		System.out.print("  invertir() -> después: ");
		vacia.imprimir();
		comprobar(vacia.estaVacia(), "Invertir una lista vacía la deja vacía.");

		// Un solo nodo.
		Invertir_Lista unico = construir(7);
		System.out.print("\nUn solo nodo - antes: ");
		unico.imprimir();
		unico.invertir();
		System.out.print("  invertir() -> después: ");
		unico.imprimir();
		comprobar(unico.getSize() == 1, "Un nodo invertido sigue siendo 1.");

		// Dos nodos.
		Invertir_Lista dos = construir(1, 2);
		System.out.print("\nDos nodos - antes: ");
		dos.imprimir();
		dos.invertir();
		System.out.print("  invertir() -> después: ");
		dos.imprimir();
		comprobar(dos.getSize() == 2, "Dos nodos invertidos siguen siendo 2.");

		System.out.println("\n[Correcto] Inversión verificada en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Invertir_Lista lista) {
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

	/** Menú interactivo para cargar datos e invertir la lista. */
	private static void ejecutarMenu(Invertir_Lista lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Invertir la lista");
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
					lista.invertir();
					System.out.println("  Lista invertida.");
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
		System.out.println("\nAhora podés invertir la lista vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Invertir_Lista(), scanner);
		}
	}
}
