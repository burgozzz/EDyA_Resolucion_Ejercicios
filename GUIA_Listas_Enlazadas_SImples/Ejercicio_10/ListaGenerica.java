/*
 * Prompt inicial utilizado:
 * Desarrolla un codigo en java que permita el uso de una lista generica
 *
 * El problema: tengo mi lista enlazada simple de enteros ya funcionando. Ahora quiero transformarla
 * en una versión GENÉRICA, que guarde cualquier tipo de dato, no solo int. O sea pasar a Nodo<T> y
 * ListaEnlazada<T>, y que funcione con ListaEnlazada<Integer>, ListaEnlazada<String> y
 * ListaEnlazada<Alumno>.
 *
 * Razonamiento a explicar con comentarios (lo central del ejercicio):
 * 1) QUÉ CAMBIA al pasar a genéricos:
 *    - El Nodo pasa a Nodo<T> y su campo dato pasa de int a T.
 *    - La clase pasa a ListaEnlazada<T> y todas sus referencias (head, auxiliares) son Nodo<T>.
 *    - Las operaciones que COMPARAN valores (buscar, eliminar, contarOcurrencias) ya no pueden usar
 *      "==", porque con objetos (String, Alumno) eso compara referencias, no contenido. Hay que usar
 *      equals() (por ejemplo Objects.equals(dato, nodo.getDato())).
 *    - Para imprimir, un objeto T se muestra con String.valueOf(...) o su toString().
 *    - Si el tipo tuviera que ordenarse, habría que pedir T extends Comparable<T> (acá no hace
 *      falta, pero queda aclarado).
 * 2) QUÉ SE MANTIENE IGUAL:
 *    - head, size y TODA la lógica de enlaces: insertar al inicio/final, recorrer con auxiliar,
 *      reenlazar en eliminar, invertir, etc. La mecánica de punteros no cambia en nada.
 * 3) POR QUÉ el algoritmo de recorrido, inserción y eliminación NO depende del tipo de dato: esos
 *    algoritmos solo manipulan REFERENCIAS al nodo siguiente (nodo.siguiente) y comparan posiciones;
 *    nunca miran "qué hay adentro" del nodo. La lista es una estructura de enlaces: le da igual si
 *    el dato es un Integer, un String o un Alumno. El tipo T es solo el "contenido" del nodo; la
 *    forma de conectarlos es siempre la misma.
 *
 * Métodos pedidos (mismos de antes, pero genéricos): insertarAlInicio(T), insertarAlFinal(T),
 * buscar(T) con equals, eliminar(T) con equals (primera aparición), contarOcurrencias(T) con equals,
 * imprimir(), estaVacia(), getSize(). Además creo una clase chica Alumno (con equals y toString)
 * para probar con objetos propios.
 *
 * Casos a contemplar: ListaEnlazada<Integer>, ListaEnlazada<String>, ListaEnlazada<Alumno> (dos
 * Alumno con el mismo legajo deben considerarse el mismo) y lista vacía en los tres tipos.
 *
 * En el main: demostración con aserciones para los tres tipos, mostrando que imprimir/buscar/
 * eliminar funcionan igual sin importar el tipo, más un menú simple sobre la lista de String.
 */
package GUIA_Listas_Enlazadas_SImples.Ejercicio_10;

import java.util.Objects;
import java.util.Scanner;

/**
 * Lista enlazada simple GENÉRICA: puede almacenar cualquier tipo T.
 *
 * <p>La mecánica de enlaces es idéntica a la de la versión de enteros; solo cambia el tipo del dato
 * (de {@code int} a {@code T}) y las comparaciones (de {@code ==} a {@code equals}).</p>
 *
 * @param <T> tipo de dato almacenado en cada nodo.
 */
public class ListaGenerica<T> {

	/**
	 * Nodo genérico: guarda un dato de tipo {@code E} y la referencia al siguiente.
	 *
	 * <p>Es {@code static} y con su propio tipo {@code E} porque no necesita el {@code T} externo:
	 * un nodo genérico sirve para cualquier lista.</p>
	 *
	 * @param <E> tipo de dato del nodo.
	 */
	private static final class Nodo<E> {
		private final E dato;
		private Nodo<E> siguiente;

		private Nodo(E dato) {
			this.dato = dato;
		}

		private E getDato() {
			return dato;
		}

		private Nodo<E> getSiguiente() {
			return siguiente;
		}

		private void setSiguiente(Nodo<E> siguiente) {
			this.siguiente = siguiente;
		}
	}

	/** Referencia al primer nodo. Es null cuando la lista está vacía. */
	private Nodo<T> head;

	/** Cantidad de nodos en la lista. */
	private int size;

	public ListaGenerica() {
		this.head = null;
		this.size = 0;
	}

	/** Inserta un nodo al inicio (el nuevo pasa a ser el head). */
	public void insertarAlInicio(T dato) {
		Nodo<T> nuevo = new Nodo<>(dato);
		nuevo.setSiguiente(head);
		head = nuevo;
		size++;
	}

	/** Inserta un nodo al final, recorriendo hasta el último nodo. */
	public void insertarAlFinal(T dato) {
		Nodo<T> nuevo = new Nodo<>(dato);
		if (head == null) {
			head = nuevo;
		} else {
			Nodo<T> auxiliar = head;
			while (auxiliar.getSiguiente() != null) {
				auxiliar = auxiliar.getSiguiente();
			}
			auxiliar.setSiguiente(nuevo);
		}
		size++;
	}

	/**
	 * Busca un dato comparando con {@code equals} (no con {@code ==}).
	 *
	 * @param dato valor a buscar.
	 * @return {@code true} si aparece al menos una vez.
	 */
	public boolean buscar(T dato) {
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			// equals compara CONTENIDO; == compararía referencias (mal para objetos).
			if (Objects.equals(dato, auxiliar.getDato())) {
				return true;
			}
			auxiliar = auxiliar.getSiguiente();
		}
		return false;
	}

	/**
	 * Elimina la primera aparición del dato (comparando con {@code equals}) y decrementa size.
	 *
	 * @param dato valor a eliminar.
	 * @return {@code true} si se eliminó; {@code false} si no estaba.
	 */
	public boolean eliminar(T dato) {
		if (head == null) {
			return false;
		}

		if (Objects.equals(dato, head.getDato())) {
			head = head.getSiguiente();
			size--;
			return true;
		}

		Nodo<T> anterior = head;
		Nodo<T> actual = head.getSiguiente();
		while (actual != null) {
			if (Objects.equals(dato, actual.getDato())) {
				anterior.setSiguiente(actual.getSiguiente());
				size--;
				return true;
			}
			anterior = actual;
			actual = actual.getSiguiente();
		}
		return false;
	}

	/**
	 * Cuenta cuántas veces aparece el dato recorriendo toda la lista (usa {@code equals}).
	 *
	 * @param dato valor a contar.
	 * @return cantidad de apariciones (0 si no aparece o la lista está vacía).
	 */
	public int contarOcurrencias(T dato) {
		int contador = 0;
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			if (Objects.equals(dato, auxiliar.getDato())) {
				contador++;
			}
			auxiliar = auxiliar.getSiguiente();
		}
		return contador;
	}

	/** Muestra los datos usando String.valueOf (que a su vez usa toString). */
	public void imprimir() {
		if (estaVacia()) {
			System.out.println("(lista vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			texto.append(String.valueOf(auxiliar.getDato())).append(" -> ");
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

	@SafeVarargs
	private static <T> ListaGenerica<T> construir(T... datos) {
		ListaGenerica<T> lista = new ListaGenerica<>();
		for (T dato : datos) {
			lista.insertarAlFinal(dato);
		}
		return lista;
	}

	private static void demostrar() {
		System.out.println("============================================");
		System.out.println(" LISTA ENLAZADA GENÉRICA (Integer/String/Alumno)");
		System.out.println("============================================");

		// ---- Integer ----
		System.out.println("\n--- ListaEnlazada<Integer> ---");
		ListaGenerica<Integer> enteros = construir(10, 20, 30, 10);
		System.out.print("Enteros: ");
		enteros.imprimir();
		comprobar(enteros.buscar(20), "Debe encontrar el 20.");
		comprobar(enteros.contarOcurrencias(10) == 2, "El 10 aparece 2 veces.");
		comprobar(enteros.eliminar(20), "Debe eliminar el 20.");
		System.out.print("  buscar(20)=true, contar(10)=2, eliminar(20)=true -> ");
		enteros.imprimir();

		// ---- String ----
		System.out.println("\n--- ListaEnlazada<String> ---");
		ListaGenerica<String> textos = construir("hola", "mundo", "hola");
		System.out.print("Textos: ");
		textos.imprimir();
		comprobar(textos.buscar("mundo"), "Debe encontrar \"mundo\".");
		comprobar(textos.contarOcurrencias("hola") == 2, "\"hola\" aparece 2 veces.");
		comprobar(textos.eliminar("hola"), "Debe eliminar el primer \"hola\".");
		System.out.print("  buscar(\"mundo\")=true, contar(\"hola\")=2, eliminar(\"hola\")=true -> ");
		textos.imprimir();

		// ---- Alumno (objeto propio, igualdad por legajo) ----
		System.out.println("\n--- ListaEnlazada<Alumno> (igualdad por equals) ---");
		Alumno ana = new Alumno(1234, "Ana");
		Alumno luis = new Alumno(9999, "Luis");
		Alumno anaDistinta = new Alumno(1234, "Ana"); // otra instancia, mismo legajo

		ListaGenerica<Alumno> alumnos = construir(ana, luis);
		System.out.print("Alumnos: ");
		alumnos.imprimir();
		// anaDistinta es OTRA instancia pero con el mismo legajo: equals debe dar true.
		comprobar(alumnos.buscar(anaDistinta), "Debe encontrar por legajo (equals), no por referencia.");
		comprobar(alumnos.eliminar(anaDistinta), "Debe eliminar por legajo (equals).");
		System.out.print("  buscar(Alumno 1234)=true, eliminar(Alumno 1234)=true -> ");
		alumnos.imprimir();

		// ---- Lista vacía en los tres tipos ----
		System.out.println("\n--- Lista vacía en los tres tipos ---");
		ListaGenerica<Integer> vaciaEnteros = new ListaGenerica<>();
		ListaGenerica<String> vaciaTextos = new ListaGenerica<>();
		ListaGenerica<Alumno> vaciaAlumnos = new ListaGenerica<>();
		comprobar(vaciaEnteros.estaVacia() && !vaciaEnteros.buscar(1), "Integer vacía.");
		comprobar(vaciaTextos.estaVacia() && !vaciaTextos.buscar("x"), "String vacía.");
		comprobar(vaciaAlumnos.estaVacia() && !vaciaAlumnos.buscar(ana), "Alumno vacía.");
		System.out.println("  Integer, String y Alumno: todas vacías y sin errores  [Correcto]");

		System.out.println("\n[Correcto] Los mismos algoritmos funcionan con cualquier tipo de dato.");
	}

	// ------------------------------------------------------------------
	// Interacción por consola (sobre una lista de String)
	// ------------------------------------------------------------------

	private static void mostrarLista(ListaGenerica<String> lista) {
		System.out.print("  Lista actual: ");
		lista.imprimir();
		System.out.println("  Tamaño: " + lista.getSize());
	}

	private static String leerTexto(Scanner scanner, String mensaje) {
		System.out.print(mensaje);
		return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
	}

	/** Menú interactivo simple sobre una lista genérica de String. */
	private static void ejecutarMenu(ListaGenerica<String> lista, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú (ListaEnlazada<String>) ---");
			System.out.println("1. Insertar al inicio");
			System.out.println("2. Insertar al final");
			System.out.println("3. Buscar");
			System.out.println("4. Eliminar (primera aparición)");
			System.out.println("5. Contar ocurrencias");
			System.out.println("6. Imprimir la lista");
			System.out.println("7. Ver tamaño");
			System.out.println("0. Salir");

			String opcion = leerTexto(scanner, "Seleccione una opción: ");
			if (opcion == null || opcion.equals("0")) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case "1": {
					String dato = leerTexto(scanner, "Ingrese el texto a insertar al inicio: ");
					if (dato == null) {
						continuar = false;
						break;
					}
					lista.insertarAlInicio(dato);
					mostrarLista(lista);
					break;
				}
				case "2": {
					String dato = leerTexto(scanner, "Ingrese el texto a insertar al final: ");
					if (dato == null) {
						continuar = false;
						break;
					}
					lista.insertarAlFinal(dato);
					mostrarLista(lista);
					break;
				}
				case "3": {
					String dato = leerTexto(scanner, "Ingrese el texto a buscar: ");
					if (dato == null) {
						continuar = false;
						break;
					}
					System.out.println("  buscar(\"" + dato + "\") -> " + lista.buscar(dato));
					break;
				}
				case "4": {
					String dato = leerTexto(scanner, "Ingrese el texto a eliminar: ");
					if (dato == null) {
						continuar = false;
						break;
					}
					System.out.println("  eliminar(\"" + dato + "\") -> " + lista.eliminar(dato));
					mostrarLista(lista);
					break;
				}
				case "5": {
					String dato = leerTexto(scanner, "Ingrese el texto a contar: ");
					if (dato == null) {
						continuar = false;
						break;
					}
					System.out.println("  contarOcurrencias(\"" + dato + "\") -> "
							+ lista.contarOcurrencias(dato));
					break;
				}
				case "6":
					mostrarLista(lista);
					break;
				case "7":
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
		System.out.println("\nAhora podés probar la lista genérica vos mismo (con String) en el menú.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new ListaGenerica<>(), scanner);
		}
	}
}
