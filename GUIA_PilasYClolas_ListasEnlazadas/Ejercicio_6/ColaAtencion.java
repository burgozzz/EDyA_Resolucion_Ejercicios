/*
 * PROMPT INICIAL UTILIZADO
 *
 *Desarrolla un codigo en java que permita simular la cola de atencion a clientes a traves de 
 su implementacion con una cola
 *
 * 1. Estructura: Cola enlazada de objetos `Cliente` (con nombre, número de turno, motivo de
 *    consulta).
 * 2. Por qué corresponde usar cola: Porque la estructura adecuada para atención al público es una
 *    cola (FIFO), garantizando equidad. El orden de llegada se representa insertando clientes por
 *    el final de la lista (`tail`) y atendiéndolos por el frente (`head`).
 * 3. Operaciones: agregar cliente a la fila, atender al próximo cliente, consultar quién sigue,
 *    imprimir la fila actual.
 * 4. Casos especiales: Manejar el intento de atender cuando no hay clientes en la fila.
 * 5. Comentarios: Incluye este prompt como comentario inicial.
 *
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Implemento una COLA enlazada de objetos Cliente (FIFO: el primero que llega es el primero que se
 * atiende). Uso cola porque la atención al público debe ser EQUITATIVA: quien llegó primero debe
 * ser atendido primero, como en una fila real. Ese es el esquema FIFO. Una pila (LIFO) sería
 * injusta: atendería al último que llegó. La cola se llena por el FINAL (tail) y se atiende por el
 * FRENTE (head).
 *
 * CÓMO DEBE FUNCIONAR INTERNAMENTE
 * - Cada Nodo guarda un Cliente y una referencia al siguiente nodo.
 * - La cola guarda DOS referencias: head (FRENTE, por donde se atiende) y tail (FINAL, por donde
 *   se agrega), más un contador size.
 * - POR QUÉ head Y tail: con head solo puedo insertar adelante en O(1), pero la fila se llena por
 *   el FINAL; llegar al último nodo desde head obliga a recorrer toda la lista, así que agregar
 *   sería O(n). Con tail (que apunta siempre al último nodo) agregar es O(1): tail.siguiente =
 *   nuevo y luego tail = nuevo. Atender sale por head en O(1) porque el frente es el primero que
 *   llegó.
 * - Las operaciones que COMPARAN (contiene) usan equals (definido por número de turno), porque ==
 *   compararía referencias.
 *
 * OPERACIONES: agregarCliente(Cliente) [enqueue], atenderSiguiente() [dequeue], proximoCliente()
 * [peek], imprimirFila(), estaVacia(), contiene(Cliente), getSize().
 *
 * CASOS ESPECIALES: atender o consultar el próximo con la fila vacía lanza NoSuchElementException
 * con un mensaje claro (no se puede atender a nadie si no hay clientes). También: agregar el primer
 * cliente (head y tail al mismo nodo); atender al último (head Y tail a null); el nodo atendido
 * queda para el Garbage Collector; imprimir fila vacía muestra "(fila vacía)".
 *
 * COMPLEJIDAD: agregarCliente es O(1) gracias a tail (sin tail sería O(n)); atenderSiguiente,
 * proximoCliente y estaVacia son O(1) (solo tocan head/tail); contiene e imprimirFila son O(n).
 *
 * PRUEBAS: clase de objeto propio Cliente (con equals por número de turno y toString) y un main que
 * demuestra la fila con aserciones: orden FIFO, consultar quién sigue, imprimir la fila, atender
 * hasta vaciar y el caso de fila vacía.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_6;

import java.util.NoSuchElementException;

/**
 * Cola de atención de clientes (FIFO) implementada sobre una lista enlazada simple de
 * {@link Cliente}.
 *
 * <p>{@code head} es el FRENTE (el próximo a ser atendido) y {@code tail} es el FINAL (por donde se
 * agrega). Tener las dos referencias permite agregar y atender en O(1): no hay que recorrer la
 * lista. Con solo {@code head}, agregar sería O(n).</p>
 *
 * <p>La cola garantiza EQUIDAD: respeta el orden de llegada (FIFO).</p>
 */
public class ColaAtencion {

	/** Nodo de la lista enlazada: un Cliente y la referencia al siguiente. */
	private static final class Nodo {
		private final Cliente dato;
		private Nodo siguiente;

		private Nodo(Cliente dato) {
			this.dato = dato;
		}
	}

	/** FRENTE de la fila: el próximo a atender. Es null cuando no hay clientes. */
	private Nodo head;

	/** FINAL de la fila: por acá se agrega. Es null cuando no hay clientes. */
	private Nodo tail;

	/** Cantidad de clientes en la fila. */
	private int size;

	public ColaAtencion() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/**
	 * Agrega un cliente al FINAL de la fila (enqueue).
	 *
	 * <p>Complejidad O(1) gracias a tail: {@code tail.siguiente = nuevo} y {@code tail = nuevo}.
	 * Sin la referencia tail habría que recorrer toda la lista (O(n)).</p>
	 *
	 * @param cliente cliente que llega a la fila.
	 */
	public void agregarCliente(Cliente cliente) {
		Nodo nuevo = new Nodo(cliente);
		if (estaVacia()) {
			// Primer cliente: head y tail apuntan al mismo único nodo.
			head = nuevo;
			tail = nuevo;
		} else {
			tail.siguiente = nuevo; // enlazo el nuevo después del último
			tail = nuevo;           // y el nuevo pasa a ser el final
		}
		size++;
	}

	/**
	 * Atiende al PRÓXIMO cliente, es decir al del frente (dequeue).
	 *
	 * <p>Complejidad O(1): solo mueve head al siguiente. El nodo atendido queda inaccesible para que
	 * lo libere el Garbage Collector (no se "borra" a mano).</p>
	 *
	 * <p>Si al atender se vacía la fila, tail también vuelve a null; de lo contrario quedaría
	 * apuntando a un nodo que ya no pertenece a la fila.</p>
	 *
	 * @return el cliente que estaba en el frente (al que se atendió).
	 * @throws NoSuchElementException si no hay clientes en la fila.
	 */
	public Cliente atenderSiguiente() {
		if (estaVacia()) {
			throw new NoSuchElementException("No hay clientes en la fila para atender.");
		}
		Cliente atendido = head.dato;
		head = head.siguiente; // el frente pasa a ser el siguiente cliente
		if (head == null) {
			tail = null; // se vació la fila: tail también debe quedar en null
		}
		size--;
		return atendido;
	}

	/**
	 * Consulta quién es el PRÓXIMO cliente sin atenderlo (peek).
	 *
	 * <p>Complejidad O(1): solo lee head.</p>
	 *
	 * @return el cliente del frente.
	 * @throws NoSuchElementException si no hay clientes en la fila.
	 */
	public Cliente proximoCliente() {
		if (estaVacia()) {
			throw new NoSuchElementException("No hay clientes en la fila.");
		}
		return head.dato;
	}

	/**
	 * Indica si no hay clientes esperando.
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si la fila está vacía.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si un cliente está en la fila (contains), recorriendo desde el frente hacia el final.
	 *
	 * <p>Complejidad O(n): recorre nodo por nodo.</p>
	 *
	 * <p>Usa {@code equals} (definido por número de turno) para comparar CONTENIDO; {@code ==}
	 * compararía referencias.</p>
	 *
	 * @param cliente cliente a buscar.
	 * @return {@code true} si está en la fila.
	 */
	public boolean contiene(Cliente cliente) {
		Nodo auxiliar = head;
		while (auxiliar != null) {
			if (java.util.Objects.equals(cliente, auxiliar.dato)) {
				return true;
			}
			auxiliar = auxiliar.siguiente;
		}
		return false;
	}

	/**
	 * Imprime la fila actual desde el FRENTE (quien será atendido primero) hacia el FINAL.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void imprimirFila() {
		if (estaVacia()) {
			System.out.println("(fila vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo auxiliar = head;
		while (auxiliar != null) {
			if (auxiliar == head) {
				texto.append("[PRÓXIMO] "); // el primero de la impresión es el próximo a atender
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

	/** Devuelve la cantidad de clientes en la fila. */
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
		System.out.println("====================================================");
		System.out.println(" COLA DE ATENCIÓN DE CLIENTES (FIFO)");
		System.out.println("====================================================");

		// ---- Fila vacía ----
		System.out.println("\n--- Fila vacía ---");
		ColaAtencion filaVacia = new ColaAtencion();
		comprobar(filaVacia.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(filaVacia.getSize() == 0, "El tamaño inicial debe ser 0.");
		System.out.print("  imprimirFila(): ");
		filaVacia.imprimirFila();
		// CASO ESPECIAL: atender sin clientes debe lanzar una excepción clara.
		comprobarExcepcion(filaVacia::atenderSiguiente, NoSuchElementException.class,
				"Atender sin clientes debe lanzar NoSuchElementException.");
		comprobarExcepcion(filaVacia::proximoCliente, NoSuchElementException.class,
				"Consultar el próximo sin clientes debe lanzar NoSuchElementException.");
		System.out.println("  atenderSiguiente() y proximoCliente() lanzan NoSuchElementException  [Correcto]");

		// ---- Agregar clientes a la fila ----
		System.out.println("\n--- Llegan clientes a la fila ---");
		ColaAtencion fila = new ColaAtencion();
		Cliente ana = new Cliente(1, "Ana López", "Consulta de saldo");
		Cliente bruno = new Cliente(2, "Bruno Díaz", "Reclamo de factura");
		Cliente carla = new Cliente(3, "Carla Ruiz", "Alta de cuenta");
		fila.agregarCliente(ana);
		fila.agregarCliente(bruno);
		fila.agregarCliente(carla); // último en llegar = final
		System.out.print("  Fila: ");
		fila.imprimirFila();
		comprobar(fila.proximoCliente().equals(ana), "El próximo debe ser Ana (la primera en llegar).");
		comprobar(fila.getSize() == 3, "Debe haber 3 clientes.");
		System.out.println("  proximoCliente() = Ana, size = 3  [Correcto]");

		// ---- Buscar un cliente (equals por número de turno) ----
		System.out.println("\n--- contiene() por número de turno ---");
		Cliente anaOtraInstancia = new Cliente(1, "Ana López", "Consulta de saldo"); // mismo turno
		comprobar(fila.contiene(anaOtraInstancia), "Debe encontrar a Ana por turno (equals).");
		comprobar(!fila.contiene(new Cliente(99, "Nadie", "Sin motivo")), "No debe contener el turno 99.");
		System.out.println("  contiene() compara por contenido (turno), no por referencia  [Correcto]");

		// ---- Atender en orden de llegada (FIFO) ----
		System.out.println("\n--- Atender en orden de llegada (FIFO) ---");
		Cliente atendido1 = fila.atenderSiguiente();
		comprobar(atendido1.equals(ana), "Primero se atiende a Ana (la primera en llegar).");
		System.out.println("  Atendido: " + atendido1);
		comprobar(fila.proximoCliente().equals(bruno), "Ahora el próximo es Bruno.");
		Cliente atendido2 = fila.atenderSiguiente();
		comprobar(atendido2.equals(bruno), "Luego se atiende a Bruno.");
		System.out.println("  Atendido: " + atendido2);
		System.out.print("  Fila restante: ");
		fila.imprimirFila();
		comprobar(fila.proximoCliente().equals(carla), "Queda Carla al frente.");
		System.out.println("  Orden FIFO respetado  [Correcto]");

		// ---- Atender al último cliente (head y tail a null) ----
		System.out.println("\n--- Atender al último cliente ---");
		comprobar(fila.atenderSiguiente().equals(carla), "Por último se atiende a Carla.");
		comprobar(fila.estaVacia(), "La fila debe quedar vacía.");
		comprobar(fila.getSize() == 0, "El tamaño debe volver a 0.");
		comprobarExcepcion(fila::atenderSiguiente, NoSuchElementException.class,
				"Al quedar vacía, atender de nuevo debe lanzar NoSuchElementException.");
		System.out.println("  head=null, tail=null, size=0, atender vuelve a lanzar excepción  [Correcto]");

		// ---- Reutilizar la fila tras vaciarla ----
		System.out.println("\n--- Reutilizar la fila ---");
		Cliente diego = new Cliente(4, "Diego Gómez", "Cambio de contraseña");
		fila.agregarCliente(diego);
		comprobar(fila.proximoCliente().equals(diego) && fila.getSize() == 1,
				"Tras vaciarse, head y tail deben permitir volver a agregar.");
		System.out.println("  agregarCliente(Diego) -> proximoCliente()=Diego, size=1  [Correcto]");

		System.out.println("\n[Correcto] La atención respeta el orden de llegada (FIFO) y maneja la fila vacía.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}

/**
 * Cliente que se atiende en la fila.
 *
 * <p>Guarda nombre, número de turno y motivo de consulta. Define {@code equals} por número de turno
 * (dos clientes con el mismo turno son iguales) y {@code toString} para poder imprimirlos en la
 * fila. Sin {@code equals}, la búsqueda por contenido ({@code contiene}) compararía referencias.</p>
 */
class Cliente {

	private final int numeroTurno;
	private final String nombre;
	private final String motivoConsulta;

	Cliente(int numeroTurno, String nombre, String motivoConsulta) {
		this.numeroTurno = numeroTurno;
		this.nombre = nombre;
		this.motivoConsulta = motivoConsulta;
	}

	public int getNumeroTurno() {
		return numeroTurno;
	}

	public String getNombre() {
		return nombre;
	}

	public String getMotivoConsulta() {
		return motivoConsulta;
	}

	/** Dos clientes son iguales si tienen el mismo número de turno. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Cliente)) {
			return false;
		}
		Cliente cliente = (Cliente) otro;
		return numeroTurno == cliente.numeroTurno;
	}

	/** Coherente con equals: el hashCode depende del número de turno. */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(numeroTurno);
	}

	/** Representación legible para imprimir la fila. */
	@Override
	public String toString() {
		return "Turno " + numeroTurno + " - " + nombre + " (" + motivoConsulta + ")";
	}
}
