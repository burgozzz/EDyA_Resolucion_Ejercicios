/*
 * PROMPT INICIAL UTILIZADO
 *
 * Desarrolla un codigo en java que implemente una cola generica
 *
 * 1. Estructura: Cola genérica con lista enlazada simple.
 * 2. Por qué corresponde usar cola: Para procesar elementos en orden de llegada (FIFO).
 * 3. Funcionamiento interno (Genéricos): El comportamiento FIFO se mantiene independientemente del
 *    tipo de dato porque la estructura de la cola (usando punteros `head` y `tail`) solo se encarga
 *    de organizar el orden de los nodos en la memoria, sin importar ni manipular la carga útil (el
 *    dato genérico) que contiene cada nodo.
 * 4. Pruebas requeridas: Implementar un `main` que pruebe la cola con nombres de personas (`String`)
 *    y con una clase `Cliente` propia.
 * 5. Casos especiales y Complejidad: Excepciones para cola vacía; operaciones principales en O(1).
 * 6. Comentarios: Incluye este prompt como comentario inicial.
 *
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Implemento una COLA (FIFO: el primero que entra es el primero que sale) sobre una lista enlazada
 * simple GENÉRICA. Uso cola y no pila porque una cola se atiende por los DOS extremos: se encola
 * por el FINAL y se desencola por el FRENTE, respetando el orden de llegada (como una fila de
 * personas, una cola de impresión o la atención de clientes). Una pila, en cambio, se atiende por
 * un único extremo y da LIFO. Apoyarme en una lista enlazada hace que no necesite tamaño fijo ni
 * desplazar elementos como en un arreglo.
 *
 * CÓMO DEBE FUNCIONAR INTERNAMENTE (GENÉRICOS)
 * - Cada Nodo<E> guarda un dato de tipo E y una referencia al siguiente nodo.
 * - La cola guarda DOS referencias: head (FRENTE, por donde se desencola) y tail (FINAL, por donde
 *   se encola), más un contador size.
 * - POR QUÉ head Y tail: con head solo podría insertar adelante en O(1), pero la cola se llena por
 *   el FINAL; llegar al último nodo desde head obliga a recorrer toda la lista, así que encolar
 *   sería O(n). Con tail (que apunta siempre al último nodo) encolar es O(1): tail.siguiente =
 *   nuevo y luego tail = nuevo. Desencolar sale por head en O(1) porque el frente es el primero
 *   que llegó.
 * - La lógica NO depende del tipo de dato: encolar y desencolar solo actualizan referencias entre
 *   nodos (nodo.siguiente, head y tail). La cola organiza el ORDEN de los nodos en memoria y nunca
 *   toca la carga útil (el dato T): le da igual si el nodo guarda un String, un Integer o un
 *   Cliente. El comportamiento FIFO es idéntico para cualquier tipo.
 * - Las operaciones que COMPARAN (contiene) usan Objects.equals, porque == compararía referencias.
 *
 * OPERACIONES: encolar(T) [enqueue], desencolar() [dequeue], frente() [peek], estaVacia()
 * [isEmpty], contiene(T) [contains], imprimir(), getSize().
 *
 * CASOS ESPECIALES: desencolar()/frente() con la cola vacía lanzan NoSuchElementException; encolar
 * el primer elemento (head y tail apuntan al mismo nodo); desencolar el último (head Y tail a
 * null, si no tail quedaría apuntando a un nodo fuera de la cola); contiene() en cola vacía =
 * false; imprimir cola vacía muestra "(cola vacía)".
 *
 * COMPLEJIDAD: encolar es O(1) gracias a tail (sin tail sería O(n)); desencolar, frente y estaVacia
 * son O(1) (solo tocan head/tail); contiene e imprimir son O(n) (recorren nodo por nodo).
 *
 * PRUEBAS: clase de objeto propio Cliente (con equals por DNI y toString) y un main que demuestra
 * la cola con nombres de personas (String) y con Cliente usando aserciones, incluyendo el caso de
 * cola vacía y el orden FIFO.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_4;

import java.util.NoSuchElementException;

/**
 * Cola (FIFO) implementada sobre una lista enlazada simple GENÉRICA.
 *
 * <p>{@code head} es el FRENTE (por acá se desencola) y {@code tail} es el FINAL (por acá se
 * encola). Tener las dos referencias permite encolar y desencolar en O(1): no hay que recorrer la
 * lista. Con solo {@code head}, encolar sería O(n).</p>
 *
 * <p>Al usar genéricos ({@code <T>}) la misma clase sirve para {@code ColaGenerica<String>},
 * {@code ColaGenerica<Integer>} o {@code ColaGenerica<Cliente>}, con seguridad de tipos en tiempo
 * de compilación. La cola solo enlaza nodos: el orden FIFO no depende del dato almacenado.</p>
 *
 * @param <T> tipo de dato almacenado en cada nodo de la cola.
 */
public class ColaGenerica<T> {

	/**
	 * Nodo genérico de la lista enlazada: un dato de tipo {@code E} y la referencia al siguiente.
	 *
	 * <p>Es {@code static} y con su propio tipo {@code E} porque no necesita el {@code T} externo:
	 * un nodo genérico sirve para cualquier cola.</p>
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

	/** FRENTE de la cola: por acá se desencola. Es null cuando la cola está vacía. */
	private Nodo<T> head;

	/** FINAL de la cola: por acá se encola. Es null cuando la cola está vacía. */
	private Nodo<T> tail;

	/** Cantidad de elementos encolados. */
	private int size;

	public ColaGenerica() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/**
	 * Encola un elemento al FINAL (enqueue).
	 *
	 * <p>Complejidad O(1) gracias a tail: {@code tail.siguiente = nuevo} y {@code tail = nuevo}. Sin
	 * la referencia tail habría que recorrer toda la lista (O(n)).</p>
	 *
	 * @param dato valor a encolar (puede ser cualquier tipo T).
	 */
	public void encolar(T dato) {
		Nodo<T> nuevo = new Nodo<>(dato);
		if (estaVacia()) {
			// Primer elemento: head y tail apuntan al mismo único nodo.
			head = nuevo;
			tail = nuevo;
		} else {
			tail.setSiguiente(nuevo); // enlazo el nuevo después del último
			tail = nuevo;             // y el nuevo pasa a ser el final
		}
		size++;
	}

	/**
	 * Desencola el FRENTE (dequeue) y devuelve su dato.
	 *
	 * <p>Complejidad O(1): solo mueve head al siguiente. El nodo que sale queda inaccesible para que
	 * lo libere el Garbage Collector (no se "borra" a mano).</p>
	 *
	 * <p>Si al sacar el último elemento la cola queda vacía, tail también vuelve a null; de lo
	 * contrario quedaría apuntando a un nodo que ya no pertenece a la cola.</p>
	 *
	 * @return el dato que estaba en el frente.
	 * @throws NoSuchElementException si la cola está vacía.
	 */
	public T desencolar() {
		if (estaVacia()) {
			throw new NoSuchElementException("No se puede desencolar: la cola está vacía.");
		}
		T dato = head.getDato();
		head = head.getSiguiente(); // el frente pasa a ser el siguiente nodo
		if (head == null) {
			tail = null; // se vació la cola: tail también debe quedar en null
		}
		size--;
		return dato;
	}

	/**
	 * Devuelve el dato del FRENTE sin desencolar (peek).
	 *
	 * <p>Complejidad O(1): solo lee head.</p>
	 *
	 * @return el dato del frente.
	 * @throws NoSuchElementException si la cola está vacía.
	 */
	public T frente() {
		if (estaVacia()) {
			throw new NoSuchElementException("No se puede consultar el frente: la cola está vacía.");
		}
		return head.getDato();
	}

	/**
	 * Indica si la cola no tiene elementos (isEmpty).
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si la cola está vacía.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si la cola contiene un valor (contains), recorriendo desde el frente hacia el final.
	 *
	 * <p>Complejidad O(n): recorre nodo por nodo; la lista enlazada no tiene acceso directo.</p>
	 *
	 * <p>Usa {@code Objects.equals} para comparar CONTENIDO; {@code ==} compararía referencias y
	 * fallaría con objetos como String o Cliente.</p>
	 *
	 * @param dato valor a buscar.
	 * @return {@code true} si aparece al menos una vez.
	 */
	public boolean contiene(T dato) {
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			// equals compara CONTENIDO; == compararía referencias (mal para objetos).
			if (java.util.Objects.equals(dato, auxiliar.getDato())) {
				return true;
			}
			auxiliar = auxiliar.getSiguiente();
		}
		return false;
	}

	/**
	 * Imprime el contenido de la cola desde el FRENTE hacia el FINAL.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void imprimir() {
		if (estaVacia()) {
			System.out.println("(cola vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			texto.append(String.valueOf(auxiliar.getDato())).append(" -> ");
			auxiliar = auxiliar.getSiguiente();
		}
		texto.append("null  (frente a la izquierda)");
		System.out.println(texto);
	}

	/** Devuelve la cantidad de elementos encolados. */
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
		System.out.println("============================================");
		System.out.println(" COLA GENÉRICA ENLAZADA (String / Cliente / Integer)");
		System.out.println("============================================");

		// ---- Casos de cola vacía (cualquier tipo) ----
		System.out.println("\n--- Cola vacía ---");
		ColaGenerica<String> vacia = new ColaGenerica<>();
		comprobar(vacia.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(vacia.getSize() == 0, "El tamaño inicial debe ser 0.");
		comprobar(!vacia.contiene("Ana"), "No debe contener ningún valor.");
		comprobarExcepcion(vacia::desencolar, NoSuchElementException.class,
				"Desencolar en cola vacía debe lanzar NoSuchElementException.");
		comprobarExcepcion(vacia::frente, NoSuchElementException.class,
				"Consultar el frente en cola vacía debe lanzar NoSuchElementException.");
		System.out.print("  imprimir(): ");
		vacia.imprimir();
		System.out.println("  estaVacia()=true, size=0, contiene(\"Ana\")=false, desencolar/frente lanzan  [Correcto]");

		// ---- String (nombres de personas) ----
		System.out.println("\n--- ColaGenerica<String> (nombres de personas) ---");
		ColaGenerica<String> nombres = new ColaGenerica<>();
		nombres.encolar("Ana");
		nombres.encolar("Bruno");
		nombres.encolar("Carla"); // último en entrar = final
		System.out.print("  Contenido (frente a la izquierda): ");
		nombres.imprimir();
		comprobar(nombres.frente().equals("Ana"), "El frente debe ser \"Ana\" (la primera que llegó).");
		comprobar(nombres.getSize() == 3, "El tamaño debe ser 3.");
		// "Bruno" es otra instancia pero con el MISMO contenido: equals debe dar true.
		comprobar(nombres.contiene(new String("Bruno")), "Debe encontrar \"Bruno\" por contenido (equals).");
		comprobar(!nombres.contiene("Diego"), "No debe contener a \"Diego\".");
		comprobar(nombres.desencolar().equals("Ana"), "Primero sale \"Ana\" (FIFO).");
		comprobar(nombres.desencolar().equals("Bruno"), "Luego sale \"Bruno\" (FIFO).");
		comprobar(nombres.frente().equals("Carla"), "Queda \"Carla\" en el frente.");
		System.out.print("  Tras desencolar Ana y Bruno: ");
		nombres.imprimir();
		comprobar(nombres.desencolar().equals("Carla") && nombres.estaVacia(),
				"Al sacar a \"Carla\" la cola vuelve a quedar vacía.");
		System.out.println("  Orden FIFO verificado con Strings y la cola vuelve a quedar vacía  [Correcto]");

		// ---- Cliente (objeto propio, igualdad por DNI) ----
		System.out.println("\n--- ColaGenerica<Cliente> (objeto propio) ---");
		Cliente ana = new Cliente(1001, "Ana López", "Pérez");
		Cliente bruno = new Cliente(1002, "Bruno Díaz", "Gómez");
		Cliente carla = new Cliente(1003, "Carla Ruiz", "Sosa");
		Cliente anaDuplicada = new Cliente(1001, "Ana López", "Pérez"); // otra instancia, mismo DNI

		ColaGenerica<Cliente> clientes = new ColaGenerica<>();
		clientes.encolar(ana);
		clientes.encolar(bruno);
		clientes.encolar(carla);
		System.out.print("  Contenido: ");
		clientes.imprimir();
		comprobar(clientes.frente().equals(ana), "El frente debe ser Ana (la primera en llegar).");
		// anaDuplicada es OTRA instancia pero con el mismo DNI: equals debe dar true.
		comprobar(clientes.contiene(anaDuplicada),
				"Debe encontrar a Ana por DNI (equals), no por referencia.");
		comprobar(!clientes.contiene(new Cliente(9999, "Nadie", "Ninguno")),
				"No debe contener un cliente con DNI inexistente.");
		comprobar(clientes.desencolar().equals(ana), "Primero sale Ana (FIFO).");
		comprobar(clientes.desencolar().equals(bruno), "Luego sale Bruno (FIFO).");
		comprobar(clientes.desencolar().equals(carla), "Por último sale Carla (FIFO).");
		comprobar(clientes.estaVacia(), "Debe quedar vacía.");
		System.out.println("  Busca por DNI (equals) y respeta el orden de llegada  [Correcto]");

		// ---- Integer (bonus: mismo comportamiento con otro tipo) ----
		System.out.println("\n--- ColaGenerica<Integer> (mismo comportamiento) ---");
		ColaGenerica<Integer> enteros = new ColaGenerica<>();
		enteros.encolar(10);
		enteros.encolar(20);
		enteros.encolar(30);
		System.out.print("  Contenido: ");
		enteros.imprimir();
		comprobar(enteros.frente() == 10, "El frente debe ser 10.");
		comprobar(enteros.desencolar() == 10, "Primero sale el 10 (FIFO).");
		comprobar(enteros.desencolar() == 20, "Luego sale el 20 (FIFO).");
		comprobar(enteros.desencolar() == 30 && enteros.estaVacia(), "Por último sale el 30.");
		System.out.println("  El mismo FIFO funciona con Integer  [Correcto]");

		System.out.println("\n[Correcto] Las mismas operaciones FIFO funcionan con cualquier tipo de dato.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}

/**
 * Clase de objeto propio para probar la cola genérica con un tipo que NO es de la librería.
 *
 * <p>Define {@code equals} por DNI (dos clientes con el mismo DNI son iguales) y {@code toString}
 * para poder imprimirlos. Sin {@code equals}, la búsqueda por contenido ({@code contiene})
 * compararía referencias y no encontraría una instancia equivalente.</p>
 */
class Cliente {

	private final int dni;
	private final String nombre;
	private final String apellido;

	Cliente(int dni, String nombre, String apellido) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	/** Dos clientes son iguales si tienen el mismo DNI. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Cliente)) {
			return false;
		}
		Cliente cliente = (Cliente) otro;
		return dni == cliente.dni;
	}

	/** Coherente con equals: el hashCode depende del DNI. */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(dni);
	}

	/** Representación legible para imprimir la cola. */
	@Override
	public String toString() {
		return "Cliente(" + dni + ", " + nombre + " " + apellido + ")";
	}
}
