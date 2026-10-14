/*
 * Prompt inicial utilizado:
 * 
 * Desarrolla un codigo en java que permita contar las ocurrencias de un valor dentro de una lista
 * enlazada
 * El problema: a partir de mi lista enlazada simple de enteros, tengo que implementar el método
 * int contarOcurrencias(int dato) que recorra TODA la lista y me diga cuántas veces aparece ese
 * valor.
 *
 * Estructura de clases: igual que en los ejercicios anteriores, un Nodo con dato (int) y siguiente
 * (referencia a Nodo), y una clase ListaEnlazada con head y size. A esa clase le agrego el método
 * contarOcurrencias.
 *
 * Razonamiento a explicar con comentarios: POR QUÉ NO se debe cortar el
 * recorrido al encontrar la primera coincidencia. Es lo contrario de buscar(): en buscar()
 * devolvía true apenas encontraba el dato y cortaba, porque solo importaba "está o no está". Acá
 * necesito saber CUÁNTAS veces aparece, así que no puedo frenar en la primera: recorro la lista
 * completa de principio a fin, sumando 1 cada vez que el dato coincide, y recién al terminar
 * (cuando auxiliar llega a null) devuelvo el contador total. Si cortara en la primera coincidencia,
 * siempre daría 1 y estaría mal para los repetidos.
 *
 * Cómo funciona contarOcurrencias: arranco un contador en 0 y un auxiliar en head; recorro con
 * while (auxiliar != null): si auxiliar.dato == dato, contador++; avanzo siempre (auxiliar =
 * auxiliar.siguiente), sin cortar nunca; al salir del while devuelvo el contador.
 *
 * Casos a contemplar: lista vacía => 0; dato que no aparece => 0; una sola aparición => 1; varias
 * apariciones => la cantidad exacta (ejemplo de la guía: 10 -> 20 -> 10 -> 30 -> 10 con el valor 10
 * => 3); todos los nodos con el mismo valor => size; dato repetido => comprobar que NO se corta en
 * el primero.
 *
 * En el main: demostración con aserciones incluyendo el ejemplo de la guía (10 -> 20 -> 10 -> 30 ->
 * 10, contarOcurrencias(10) == 3), un valor inexistente (0) y lista vacía (0), más un menú por
 * consola para cargar datos y contar ocurrencias del valor ingresado.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_8;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con conteo de ocurrencias de un valor.
 *
 * <p>A diferencia de una búsqueda, acá NO se corta en la primera coincidencia: hay que recorrer
 * toda la lista para contar todas las apariciones.</p>
 */
public class Contar_Ocurrencias {

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

	public Contar_Ocurrencias() {
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
	 * Cuenta cuántas veces aparece {@code dato} en toda la lista.
	 *
	 * <p>No se corta el recorrido en la primera coincidencia (a diferencia de buscar): si lo
	 * cortara, para valores repetidos devolvería siempre 1. Por eso se recorre la lista completa y
	 * se cuenta cada aparición.</p>
	 *
	 * @param dato valor a contar.
	 * @return cantidad de veces que aparece el valor; 0 si no aparece o la lista está vacía.
	 */
	public int contarOcurrencias(int dato) {
		int contador = 0;
		Nodo auxiliar = head;
		// Recorro TODA la lista: no corto al encontrar la primera coincidencia.
		while (auxiliar != null) {
			if (auxiliar.dato == dato) {
				contador++; // encontré una aparición, la cuento y SIGO recorriendo.
			}
			auxiliar = auxiliar.getSiguiente(); // avanzo siempre, sin frenar.
		}
		return contador;
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

	private static Contar_Ocurrencias construir(int... datos) {
		Contar_Ocurrencias lista = new Contar_Ocurrencias();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" CONTAR OCURRENCIAS DE UN VALOR");
		System.out.println("====================================");

		// Ejemplo de la guía: 10 -> 20 -> 10 -> 30 -> 10, contar el 10 => 3.
		Contar_Ocurrencias ejemplo = construir(10, 20, 10, 30, 10);
		System.out.print("\nEjemplo de la guía: ");
		ejemplo.imprimir();
		comprobar(ejemplo.contarOcurrencias(10) == 3, "El 10 aparece 3 veces.");
		System.out.println("  contarOcurrencias(10) -> " + ejemplo.contarOcurrencias(10)
				+ "  (no se corta en la primera!)");

		System.out.println("\nOtros casos con la misma lista 10 -> 20 -> 10 -> 30 -> 10:");
		comprobar(ejemplo.contarOcurrencias(20) == 1, "El 20 aparece 1 vez.");
		System.out.println("  contarOcurrencias(20) -> " + ejemplo.contarOcurrencias(20) + "  (una sola vez)");
		comprobar(ejemplo.contarOcurrencias(30) == 1, "El 30 aparece 1 vez.");
		System.out.println("  contarOcurrencias(30) -> " + ejemplo.contarOcurrencias(30) + "  (una sola vez)");
		comprobar(ejemplo.contarOcurrencias(99) == 0, "El 99 no aparece.");
		System.out.println("  contarOcurrencias(99) -> " + ejemplo.contarOcurrencias(99) + "  (no aparece)");

		// Lista vacía.
		Contar_Ocurrencias vacia = new Contar_Ocurrencias();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		comprobar(vacia.contarOcurrencias(10) == 0, "En lista vacía debe devolver 0.");
		System.out.println("  contarOcurrencias(10) -> " + vacia.contarOcurrencias(10) + "  [Correcto]");

		// Todos los nodos iguales: debe devolver size.
		Contar_Ocurrencias todos = construir(5, 5, 5, 5);
		System.out.print("\nTodos los nodos iguales (5, 5, 5, 5): ");
		todos.imprimir();
		comprobar(todos.contarOcurrencias(5) == todos.getSize(),
				"Si todos son 5, debe contar todos (size).");
		System.out.println("  contarOcurrencias(5) -> " + todos.contarOcurrencias(5)
				+ "  (= size = " + todos.getSize() + ")");

		System.out.println("\n[Correcto] Conteo de ocurrencias verificado en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Contar_Ocurrencias lista) {
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

	/** Menú interactivo para cargar datos y contar ocurrencias. */
	private static void ejecutarMenu(Contar_Ocurrencias lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Contar ocurrencias de un valor");
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
					Integer aContar = leerEntero(scanner, "Ingrese el entero a contar: ");
					if (aContar == null) {
						continuar = false;
						break;
					}
					int ocurrencias = lista.contarOcurrencias(aContar);
					System.out.println("  El valor " + aContar + " aparece " + ocurrencias
							+ " vez/veces en la lista.");
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
		System.out.println("\nAhora podés contar ocurrencias vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Contar_Ocurrencias(), scanner);
		}
	}
}
