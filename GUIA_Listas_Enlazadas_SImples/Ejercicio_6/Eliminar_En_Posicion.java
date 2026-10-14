/*
 * Prompt inicial utilizado:

 * Desarrolla un codigo en java que pueda eliminar un nodo de una lista enlazada a partir de su 
 * posicion, crea el codigo siguiendo
 * El problema: a partir de mi lista enlazada simple de enteros, tengo que implementar el método
 * void eliminarEnPosicion(int posicion) que elimine el nodo que está en esa posición (contando
 * desde 0).
 *
 * Estructura de clases: igual que en los ejercicios anteriores, un Nodo con dato (int) y
 * siguiente (referencia a Nodo), y una clase ListaEnlazada con head y size. A esa clase le agrego
 * el método eliminarEnPosicion.
 *
 * Validación de posiciones inválidas (antes de recorrer): posicion < 0 es inválida y posicion >=
 * size es inválida. Acá, a diferencia de insertarEnPosicion, posicion == size NO es válida, porque
 * no existe un nodo en esa posición para eliminar. Si es inválida, lanza IndexOutOfBoundsException
 * con un mensaje claro y NO modifica la lista ni el size.
 *
 * Razonamiento a explicar con comentarios: para eliminar por posición necesito llegar al nodo
 * ANTERIOR al que quiero borrar (como hicimos con "anterior" en el eliminar por valor): recorro con
 * un auxiliar "anterior" hasta (posicion - 1), que es el nodo de atrás del que quiero eliminar;
 * guardo el nodo a eliminar (aEliminar = anterior.siguiente) y hago que el anterior apunte al
 * SIGUIENTE del eliminado (anterior.siguiente = aEliminar.siguiente). Con eso el nodo queda
 * salteado y sin referencias (inaccesible), así que el Garbage Collector lo puede liberar (en Java
 * no se borra a mano). Para eliminar en la posición p avanzo p-1 veces desde head para pararme en
 * el anterior, y el que borro es anterior.siguiente.
 *
 * Caso especial: eliminar el PRIMER nodo (posicion 0) no tiene "anterior", así que se trata aparte:
 * head = head.siguiente. Sirve también cuando la lista tiene un solo elemento (queda vacía:
 * head = null).
 *
 * Casos a contemplar: lista vacía => excepción; posicion negativa => excepción; posicion >= size =>
 * excepción; eliminar el PRIMER nodo; eliminar uno del MEDIO; eliminar el ÚLTIMO; lista de un solo
 * elemento eliminado en 0 => queda vacía; tras eliminar, decrementar size y que imprimir() refleje
 * el cambio.
 *
 * En el main: demostración con aserciones mostrando la lista antes/después de eliminar el primer
 * nodo, uno del medio, el último y el único elemento, y que posiciones negativas y >= size lancen
 * excepción sin cambiar el size, más un menú por consola para cargar datos y eliminar por posición.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_6;

import java.util.Scanner;

/**
 * Lista enlazada simple de enteros con eliminación por posición.
 *
 * <p>Para eliminar por posición se llega al nodo anterior al que se quiere borrar y se lo hace
 * apuntar al siguiente del eliminado. El nodo salteado queda inaccesible para el Garbage
 * Collector.</p>
 */
public class Eliminar_En_Posicion {

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

	public Eliminar_En_Posicion() {
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
	 * Elimina el nodo que está en la posición indicada (0 = head).
	 *
	 * <p>El nodo no se borra a mano: se reenlaza la lista para saltearlo, quedando inaccesible
	 * para que el Garbage Collector libere su memoria.</p>
	 *
	 * @param posicion índice del nodo a eliminar, de 0 a {@code size - 1}.
	 * @throws IndexOutOfBoundsException si {@code posicion < 0} o {@code posicion >= size}.
	 */
	public void eliminarEnPosicion(int posicion) {
		// Valido ANTES de tocar la lista: acá posicion == size NO es válida.
		if (posicion < 0 || posicion >= size) {
			throw new IndexOutOfBoundsException(
					"Posición inválida: " + posicion + ". Rango válido: 0 a " + (size - 1));
		}

		if (posicion == 0) {
			// Caso primer nodo: no hay "anterior", muevo head.
			// Si era el único nodo, head queda null y la lista vacía.
			head = head.getSiguiente();
		} else {
			// Busco el nodo ANTERIOR al que quiero eliminar (posicion - 1).
			Nodo anterior = head;
			for (int i = 0; i < posicion - 1; i++) {
				anterior = anterior.getSiguiente();
			}
			Nodo aEliminar = anterior.getSiguiente();
			// El anterior "saltea" al eliminado apuntando a su siguiente.
			// Sirve para el medio (apunta a otro nodo) y para el último (apunta a null).
			// aEliminar queda sin referencias -> Garbage Collector.
			anterior.setSiguiente(aEliminar.getSiguiente());
		}

		size--;
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

	private static Eliminar_En_Posicion construir(int... datos) {
		Eliminar_En_Posicion lista = new Eliminar_En_Posicion();
		for (int dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" ELIMINAR NODO POR POSICIÓN");
		System.out.println("====================================");

		// Eliminar el PRIMER nodo.
		Eliminar_En_Posicion primero = construir(10, 20, 30, 40);
		System.out.print("\nEliminar el PRIMER nodo (posicion 0) - antes: ");
		primero.imprimir();
		primero.eliminarEnPosicion(0);
		System.out.print("  eliminarEnPosicion(0), después: ");
		primero.imprimir();
		comprobar(primero.getSize() == 3, "Tras eliminar el primero, size debe ser 3.");

		// Eliminar un nodo del MEDIO.
		Eliminar_En_Posicion medio = construir(10, 20, 30, 40);
		System.out.print("\nEliminar un nodo del MEDIO (posicion 2) - antes: ");
		medio.imprimir();
		medio.eliminarEnPosicion(2);
		System.out.print("  eliminarEnPosicion(2), después: ");
		medio.imprimir();
		comprobar(medio.getSize() == 3, "Tras eliminar del medio, size debe ser 3.");

		// Eliminar el ÚLTIMO nodo.
		Eliminar_En_Posicion ultimo = construir(10, 20, 30, 40);
		System.out.print("\nEliminar el ÚLTIMO nodo (posicion size-1) - antes: ");
		ultimo.imprimir();
		ultimo.eliminarEnPosicion(ultimo.getSize() - 1);
		System.out.print("  eliminarEnPosicion(size-1), después: ");
		ultimo.imprimir();
		comprobar(ultimo.getSize() == 3, "Tras eliminar el último, size debe ser 3.");

		// Lista de un solo elemento.
		Eliminar_En_Posicion unico = construir(7);
		System.out.print("\nLista de un solo elemento - antes: ");
		unico.imprimir();
		unico.eliminarEnPosicion(0);
		System.out.print("  eliminarEnPosicion(0), después: ");
		unico.imprimir();
		comprobar(unico.getSize() == 0 && unico.estaVacia(), "Con un solo elemento debe quedar vacía.");

		// Posiciones inválidas.
		Eliminar_En_Posicion invalidas = construir(10, 20, 30);
		System.out.println("\nPosiciones inválidas (no deben modificar la lista):");
		int sizeAntes = invalidas.getSize();
		comprobarExcepcion(() -> invalidas.eliminarEnPosicion(-1), "posicion -1 debe lanzar excepción.");
		System.out.println("  eliminarEnPosicion(-1) -> IndexOutOfBoundsException  [Correcto]");
		comprobarExcepcion(() -> invalidas.eliminarEnPosicion(invalidas.getSize()),
				"posicion >= size debe lanzar excepción.");
		System.out.println("  eliminarEnPosicion(size) -> IndexOutOfBoundsException  [Correcto]");
		comprobar(invalidas.getSize() == sizeAntes, "Las posiciones inválidas no deben cambiar size.");

		Eliminar_En_Posicion vacia = new Eliminar_En_Posicion();
		System.out.print("\nLista vacía: ");
		vacia.imprimir();
		comprobarExcepcion(() -> vacia.eliminarEnPosicion(0), "En lista vacía debe lanzar excepción.");
		System.out.println("  eliminarEnPosicion(0) en lista vacía -> IndexOutOfBoundsException  [Correcto]");

		System.out.println("\n[Correcto] Eliminación por posición verificada en todos los casos.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola
	// ------------------------------------------------------------------

	/** Muestra el estado actual de la lista. */
	private static void mostrarLista(Eliminar_En_Posicion lista) {
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

	/** Menú interactivo para cargar datos y eliminar por posición. */
	private static void ejecutarMenu(Eliminar_En_Posicion lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la lista enlazada ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Eliminar por posición");
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
					Integer posicion = leerEntero(scanner, "Ingrese la posición a eliminar: ");
					if (posicion == null) {
						continuar = false;
						break;
					}
					try {
						lista.eliminarEnPosicion(posicion);
						System.out.println("  Eliminado el nodo de la posición " + posicion);
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
		System.out.println("\nAhora podés eliminar posiciones vos mismo con el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Eliminar_En_Posicion(), scanner);
		}
	}
}
