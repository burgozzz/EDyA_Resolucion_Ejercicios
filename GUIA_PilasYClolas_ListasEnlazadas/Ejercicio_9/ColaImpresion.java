/*
 * PROMPT INICIAL UTILIZADO
 *
 * Desarrolla un codigo en java que permite la implementacion de la coa de impresiones
 *
 * 1. Estructura: Cola enlazada de objetos `TrabajoImpresion` (nombre de archivo, cantidad de
 *    páginas, usuario).
 * 2. Por qué corresponde usar cola: Porque una impresora debe procesar los trabajos usando FIFO
 *    para mantener la justicia y el orden cronológico; el primer trabajo enviado debe ser el
 *    primero en imprimirse.
 * 3. Operaciones: agregar trabajos, imprimir próximo trabajo (desencolar), consultar el próximo
 *    (peek), mostrar todos los pendientes.
 * 4. Casos especiales: Cola vacía al intentar imprimir.
 * 5. Comentarios: Incluye este prompt como comentario inicial.
 *
 * 
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Implemento una COLA enlazada de objetos TrabajoImpresion (FIFO: el primero que llega es el primero
 * que se imprime). Uso cola porque una impresora debe respetar el ORDEN CRONOLÓGICO y la JUSTICIA:
 * el trabajo enviado antes debe imprimirse antes, igual que una fila. Una pila (LIFO) sería injusta
 * e ilógica: imprimiría primero el último trabajo enviado. La cola se llena por el FINAL (tail) y
 * se procesa por el FRENTE (head).
 *
 * CÓMO FUNCIONA INTERNAMENTE
 * - Cada Nodo guarda un TrabajoImpresion y una referencia al siguiente nodo.
 * - La cola guarda DOS referencias: head (FRENTE, el próximo a imprimir) y tail (FINAL, por donde
 *   se agrega), más un contador size.
 * - POR QUÉ head Y tail: con head solo podría insertar adelante en O(1), pero los trabajos se
 *   agregan por el FINAL; llegar al último nodo desde head obliga a recorrer toda la lista, así que
 *   agregar sería O(n). Con tail (que apunta siempre al último nodo) agregar es O(1): tail.siguiente
 *   = nuevo y luego tail = nuevo. Imprimir sale por head en O(1) porque el frente es el primero que
 *   llegó.
 * - Las operaciones que COMPARAN (contiene) usan equals, porque == compararía referencias.
 *
 * OPERACIONES: agregarTrabajo(TrabajoImpresion) [enqueue], imprimirProximo() [dequeue],
 * proximo() [peek], mostrarPendientes(), estaVacia(), contiene(TrabajoImpresion), getSize().
 *
 * CASOS ESPECIALES: imprimir o consultar el próximo con la cola vacía lanza
 * NoSuchElementException con un mensaje claro (no hay nada que imprimir). También: agregar el primer
 * trabajo (head y tail al mismo nodo); imprimir el último (head Y tail a null); el nodo impreso
 * queda para el Garbage Collector; mostrar vacía muestra "(no hay trabajos pendientes)".
 *
 * COMPLEJIDAD: agregarTrabajo es O(1) gracias a tail (sin tail sería O(n)); imprimirProximo,
 * proximo y estaVacia son O(1) (solo tocan head/tail); contiene y mostrarPendientes son O(n).
 *
 * PRUEBAS: clase de objeto propio TrabajoImpresion (con equals por nombre de archivo + usuario y
 * toString) y un main que demuestra la cola con aserciones: orden FIFO, consultar el próximo,
 * mostrar los pendientes, imprimir hasta vaciar y el caso de cola vacía.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_9;

import java.util.NoSuchElementException;

/**
 * Cola de impresión (FIFO) implementada sobre una lista enlazada simple de {@link TrabajoImpresion}.
 *
 * <p>{@code head} es el FRENTE (el próximo trabajo a imprimir) y {@code tail} es el FINAL (por donde
 * se encolan los trabajos). Tener las dos referencias permite agregar e imprimir en O(1): no hay
 * que recorrer la lista. Con solo {@code head}, agregar sería O(n).</p>
 *
 * <p>La cola respeta el orden cronológico: el primer trabajo enviado es el primero en imprimirse.</p>
 */
public class ColaImpresion {

	/** Nodo de la lista enlazada: un TrabajoImpresion y la referencia al siguiente. */
	private static final class Nodo {
		private final TrabajoImpresion dato;
		private Nodo siguiente;

		private Nodo(TrabajoImpresion dato) {
			this.dato = dato;
		}
	}

	/** FRENTE de la cola: el próximo a imprimir. Es null cuando no hay trabajos. */
	private Nodo head;

	/** FINAL de la cola: por acá se agregan. Es null cuando no hay trabajos. */
	private Nodo tail;

	/** Cantidad de trabajos pendientes. */
	private int size;

	public ColaImpresion() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/**
	 * Agrega un trabajo al FINAL de la cola (enqueue).
	 *
	 * <p>Complejidad O(1) gracias a tail: {@code tail.siguiente = nuevo} y {@code tail = nuevo}. Sin
	 * la referencia tail habría que recorrer toda la lista (O(n)).</p>
	 *
	 * @param trabajo trabajo de impresión que se envía.
	 */
	public void agregarTrabajo(TrabajoImpresion trabajo) {
		Nodo nuevo = new Nodo(trabajo);
		if (estaVacia()) {
			// Primer trabajo: head y tail apuntan al mismo único nodo.
			head = nuevo;
			tail = nuevo;
		} else {
			tail.siguiente = nuevo; // enlazo el nuevo después del último
			tail = nuevo;           // y el nuevo pasa a ser el final
		}
		size++;
	}

	/**
	 * Imprime el PRÓXIMO trabajo, es decir el del frente (dequeue).
	 *
	 * <p>Complejidad O(1): solo mueve head al siguiente. El nodo impreso queda inaccesible para que
	 * lo libere el Garbage Collector (no se "borra" a mano).</p>
	 *
	 * <p>Si al imprimir se vacía la cola, tail también vuelve a null; de lo contrario quedaría
	 * apuntando a un nodo que ya no pertenece a la cola.</p>
	 *
	 * @return el trabajo que estaba en el frente (el que se imprimió).
	 * @throws NoSuchElementException si no hay trabajos pendientes.
	 */
	public TrabajoImpresion imprimirProximo() {
		if (estaVacia()) {
			throw new NoSuchElementException("No hay trabajos en la cola de impresión.");
		}
		TrabajoImpresion impreso = head.dato;
		head = head.siguiente; // el frente pasa a ser el siguiente trabajo
		if (head == null) {
			tail = null; // se vació la cola: tail también debe quedar en null
		}
		size--;
		return impreso;
	}

	/**
	 * Consulta cuál es el PRÓXIMO trabajo sin imprimirlo (peek).
	 *
	 * <p>Complejidad O(1): solo lee head.</p>
	 *
	 * @return el trabajo del frente.
	 * @throws NoSuchElementException si no hay trabajos pendientes.
	 */
	public TrabajoImpresion proximo() {
		if (estaVacia()) {
			throw new NoSuchElementException("No hay trabajos en la cola de impresión.");
		}
		return head.dato;
	}

	/**
	 * Indica si no hay trabajos pendientes.
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si la cola está vacía.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si un trabajo está en la cola (contains), recorriendo desde el frente hacia el final.
	 *
	 * <p>Complejidad O(n): recorre nodo por nodo.</p>
	 *
	 * <p>Usa {@code equals} para comparar CONTENIDO; {@code ==} compararía referencias.</p>
	 *
	 * @param trabajo trabajo a buscar.
	 * @return {@code true} si está en la cola.
	 */
	public boolean contiene(TrabajoImpresion trabajo) {
		Nodo auxiliar = head;
		while (auxiliar != null) {
			if (java.util.Objects.equals(trabajo, auxiliar.dato)) {
				return true;
			}
			auxiliar = auxiliar.siguiente;
		}
		return false;
	}

	/**
	 * Muestra todos los trabajos pendientes, desde el FRENTE (próximo a imprimir) hacia el FINAL.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void mostrarPendientes() {
		if (estaVacia()) {
			System.out.println("(no hay trabajos pendientes)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo auxiliar = head;
		while (auxiliar != null) {
			if (auxiliar == head) {
				texto.append("[PRÓXIMO] "); // el primero de la impresión es el próximo a imprimir
			}
			texto.append(auxiliar.dato);
			texto.append(auxiliar.siguiente != null ? " -> " : " -> null");
			if (auxiliar.siguiente != null) {
				texto.append(" ");
			}
			auxiliar = auxiliar.siguiente;
		}
		texto.append("  (frente a la izquierda)");
		System.out.println(texto);
	}

	/** Devuelve la cantidad de trabajos pendientes. */
	public int getSize() {
		return size;
	}

	// ------------------------------------------------------------------
	// Demostración automática (pruebas)
	// ------------------------------------------------------------------

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private static void comprobarExcepcion(Runnable accion, Class<? extends Throwable> esperada,
			String mensaje) {
		try {
			accion.run();
		} catch (Throwable error) {
			if (esperada.isInstance(error)) {
				return;
			}
			throw new AssertionError(mensaje + " (se lanzó " + error.getClass().getSimpleName() + ")");
		}
		throw new AssertionError(mensaje + " (no se lanzó ninguna excepción)");
	}

	private static void demostrar() {
		System.out.println("=========================================================");
		System.out.println(" COLA DE IMPRESIÓN (FIFO)");
		System.out.println("=========================================================");

		// ---- Cola vacía ----
		System.out.println("\n--- Cola vacía ---");
		ColaImpresion vacia = new ColaImpresion();
		comprobar(vacia.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(vacia.getSize() == 0, "El tamaño inicial debe ser 0.");
		System.out.print("  mostrarPendientes(): ");
		vacia.mostrarPendientes();
		// CASO ESPECIAL: imprimir sin trabajos debe lanzar una excepción clara.
		comprobarExcepcion(vacia::imprimirProximo, NoSuchElementException.class,
				"Imprimir sin trabajos debe lanzar NoSuchElementException.");
		comprobarExcepcion(vacia::proximo, NoSuchElementException.class,
				"Consultar el próximo sin trabajos debe lanzar NoSuchElementException.");
		System.out.println("  imprimirProximo() y proximo() lanzan NoSuchElementException  [Correcto]");

		// ---- Enviar trabajos a la cola ----
		System.out.println("\n--- Se envían trabajos a la impresora ---");
		ColaImpresion cola = new ColaImpresion();
		TrabajoImpresion informe = new TrabajoImpresion("informe.pdf", 12, "ana");
		TrabajoImpresion tesis = new TrabajoImpresion("tesis.docx", 120, "bruno");
		TrabajoImpresion apunte = new TrabajoImpresion("apunte.pdf", 3, "carla");
		cola.agregarTrabajo(informe);
		cola.agregarTrabajo(tesis);
		cola.agregarTrabajo(apunte); // último en enviarse = final
		System.out.print("  Pendientes: ");
		cola.mostrarPendientes();
		comprobar(cola.proximo().equals(informe), "El próximo debe ser informe.pdf (el primero enviado).");
		comprobar(cola.getSize() == 3, "Debe haber 3 trabajos.");
		System.out.println("  proximo() = informe.pdf, size = 3  [Correcto]");

		// ---- Buscar un trabajo (equals por nombre + usuario) ----
		System.out.println("\n--- contiene() ---");
		TrabajoImpresion tesisOtraInstancia = new TrabajoImpresion("tesis.docx", 120, "bruno");
		comprobar(cola.contiene(tesisOtraInstancia), "Debe encontrar tesis.docx por contenido (equals).");
		comprobar(!cola.contiene(new TrabajoImpresion("otro.pdf", 1, "diego")),
				"No debe contener un trabajo inexistente.");
		System.out.println("  contiene() compara por contenido, no por referencia  [Correcto]");

		// ---- Imprimir en orden de llegada (FIFO) ----
		System.out.println("\n--- Imprimir en orden de llegada (FIFO) ---");
		TrabajoImpresion primero = cola.imprimirProximo();
		comprobar(primero.equals(informe), "Primero se imprime informe.pdf (el primero enviado).");
		System.out.println("  Impreso: " + primero);
		comprobar(cola.proximo().equals(tesis), "Ahora el próximo es tesis.docx.");
		TrabajoImpresion segundo = cola.imprimirProximo();
		comprobar(segundo.equals(tesis), "Luego se imprime tesis.docx.");
		System.out.println("  Impreso: " + segundo);
		System.out.print("  Pendientes: ");
		cola.mostrarPendientes();
		comprobar(cola.proximo().equals(apunte), "Queda apunte.pdf al frente.");
		System.out.println("  Orden FIFO respetado  [Correcto]");

		// ---- Imprimir el último trabajo (head y tail a null) ----
		System.out.println("\n--- Imprimir el último trabajo ---");
		comprobar(cola.imprimirProximo().equals(apunte), "Por último se imprime apunte.pdf.");
		comprobar(cola.estaVacia(), "La cola debe quedar vacía.");
		comprobar(cola.getSize() == 0, "El tamaño debe volver a 0.");
		comprobarExcepcion(cola::imprimirProximo, NoSuchElementException.class,
				"Al quedar vacía, imprimir de nuevo debe lanzar NoSuchElementException.");
		System.out.println("  head=null, tail=null, size=0, imprimir vuelve a lanzar excepción  [Correcto]");

		// ---- Reutilizar la cola tras vaciarla ----
		System.out.println("\n--- Reutilizar la cola ---");
		TrabajoImpresion foto = new TrabajoImpresion("foto.png", 1, "diego");
		cola.agregarTrabajo(foto);
		comprobar(cola.proximo().equals(foto) && cola.getSize() == 1,
				"Tras vaciarse, head y tail deben permitir volver a agregar.");
		System.out.println("  agregarTrabajo(foto.png) -> proximo()=foto.png, size=1  [Correcto]");

		System.out.println("\n[Correcto] La impresora procesa los trabajos en orden de llegada (FIFO) y "
				+ "maneja la cola vacía.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}

/**
 * Trabajo de impresión que se encola.
 *
 * <p>Guarda el nombre del archivo, la cantidad de páginas y el usuario que lo envió. Define
 * {@code equals} por nombre de archivo y usuario (dos trabajos del mismo archivo enviados por el
 * mismo usuario son iguales) y {@code toString} para poder mostrarlos. Sin {@code equals}, la
 * búsqueda por contenido ({@code contiene}) compararía referencias.</p>
 */
class TrabajoImpresion {

	private final String nombreArchivo;
	private final int cantidadPaginas;
	private final String usuario;

	TrabajoImpresion(String nombreArchivo, int cantidadPaginas, String usuario) {
		this.nombreArchivo = nombreArchivo;
		this.cantidadPaginas = cantidadPaginas;
		this.usuario = usuario;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public int getCantidadPaginas() {
		return cantidadPaginas;
	}

	public String getUsuario() {
		return usuario;
	}

	/** Dos trabajos son iguales si tienen el mismo archivo y el mismo usuario. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof TrabajoImpresion)) {
			return false;
		}
		TrabajoImpresion trabajo = (TrabajoImpresion) otro;
		return java.util.Objects.equals(nombreArchivo, trabajo.nombreArchivo)
				&& java.util.Objects.equals(usuario, trabajo.usuario);
	}

	/** Coherente con equals: el hashCode depende del archivo y del usuario. */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(nombreArchivo, usuario);
	}

	/** Representación legible para mostrar la cola. */
	@Override
	public String toString() {
		return nombreArchivo + " (" + cantidadPaginas + " pág., " + usuario + ")";
	}
}
