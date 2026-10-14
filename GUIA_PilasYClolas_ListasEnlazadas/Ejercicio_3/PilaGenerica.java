/*
 * PROMPT INICIAL UTILIZADO 
 *
 * Desarrolla un codigo en java que implemente el uso de una pila generica
 * A continuación, te detallo la justificación y los requerimientos lógicos que debes seguir para la
 * implementación:
 *
 * 1. Estructura a usar: Una pila (Stack) implementada mediante una lista enlazada simple genérica.
 * 2. Por qué corresponde usar una pila: Porque la naturaleza del problema o de este tipo de
 *    estructura requiere un comportamiento LIFO (Last In, First Out), donde el último elemento en
 *    ingresar es el primero en salir.
 * 3. Cómo debe funcionar internamente (Genéricos): Debes utilizar genéricos en Java (`<T>`). Usar
 *    genéricos significa que la clase puede ser instanciada con cualquier tipo de objeto en tiempo
 *    de compilación, aportando seguridad de tipos. La lógica de la pila no depende del tipo de dato
 *    almacenado porque las operaciones de inserción y remoción consisten únicamente en actualizar
 *    las referencias (punteros) entre los nodos. La estructura maneja las conexiones,
 *    independientemente de si el nodo guarda un `Integer`, un `String` o un objeto propio.
 * 4. Operaciones que debe implementar:
 *    - Apilar (push).
 *    - Desapilar (pop).
 *    - Consultar el tope (peek).
 *    - Verificar si está vacía (isEmpty).
 *    - Buscar si contiene un valor (contains).
 *    - Imprimir su contenido.
 * 5. Casos especiales a contemplar: El código debe manejar correctamente el intento de desapilar o
 *    consultar el tope cuando la pila está vacía (por ejemplo, lanzando una excepción adecuada como
 *    `EmptyStackException`).
 * 6. Complejidad esperada: Las operaciones principales (`push` y `pop`) deben tener una complejidad
 *    de O(1), realizando las inserciones y eliminaciones directamente desde el `head` (que
 *    representa el tope).
 * 7. Pruebas: Incluye una clase de prueba (o un método `main`) donde se demuestre el funcionamiento
 *    de la pila almacenando tipos `Integer`, `String` y una clase de objeto propio que también
 *    debes crear.
 *
 * Importante: Como parte de la consigna, debes incluir todo este prompt inicial como un bloque de
 * comentarios en la parte superior del código generado.
 *
 * ==================================================================================
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * ==================================================================================
 * Implemento una PILA (LIFO: el último que entra es el primero que sale) sobre una lista enlazada
 * simple GENÉRICA. Uso pila y no cola porque una pila se atiende por un único extremo (el tope):
 * se apila y se desapila siempre por el mismo lado, lo que da el comportamiento LIFO (por ejemplo,
 * deshacer acciones, validar paréntesis, recorrer recursión). Apoyarme en una lista enlazada hace
 * que no necesite tamaño fijo ni desplazar elementos como en un arreglo.
 *
 * CÓMO DEBE FUNCIONAR INTERNAMENTE (GENÉRICOS)
 * - Cada Nodo<E> guarda un dato de tipo E y una referencia al siguiente nodo.
 * - La pila guarda UNA sola referencia: head, y un contador size.
 * - head representa el TOPE: como los nuevos elementos se agregan adelante, la cabeza siempre
 *   apunta al último que entró, o sea al primero que debe salir. Por eso push y pop son O(1).
 * - La lógica NO depende del tipo de dato: insertar y remover solo actualizan referencias entre
 *   nodos (nodo.siguiente y head). Al nodo le da igual si guarda un Integer, un String o un
 *   Producto; el tipo T es solo el "contenido", la forma de enlazarlos es siempre la misma.
 * - Las operaciones que COMPARAN (contains) usan Objects.equals, porque == compararía referencias.
 *
 * OPERACIONES: apilar(T) [push], desapilar() [pop], tope() [peek], estaVacia() [isEmpty],
 * contiene(T) [contains], imprimir(), getSize().
 *
 * CASOS ESPECIALES: desapilar()/tope() con la pila vacía lanzan EmptyStackException; apilar el
 * primer elemento; desapilar el último (head = null, size = 0; el nodo queda para el Garbage
 * Collector); contiene() en pila vacía = false; imprimir pila vacía muestra "(pila vacía)".
 *
 * COMPLEJIDAD: apilar, desapilar, tope y estaVacia son O(1) (solo tocan el head); contiene e
 * imprimir son O(n) (recorren nodo por nodo).
 *
 * PRUEBAS: clase de objeto propio Producto (con equals por código y toString) y un main que
 * demuestra la pila con Integer, String y Producto usando aserciones, incluyendo el caso de pila
 * vacía.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_3;

import java.util.EmptyStackException;

/**
 * Pila (LIFO) implementada sobre una lista enlazada simple GENÉRICA.
 *
 * <p>{@code head} apunta siempre al TOPE de la pila (el último elemento apilado). Por eso apilar,
 * desapilar y consultar el tope son O(1): solo mueven o leen la cabeza, sin recorrer.</p>
 *
 * <p>Al usar genéricos ({@code <T>}) la misma clase sirve para {@code PilaGenerica<Integer>},
 * {@code PilaGenerica<String>} o {@code PilaGenerica<Producto>}, con seguridad de tipos en tiempo
 * de compilación.</p>
 *
 * @param <T> tipo de dato almacenado en cada nodo de la pila.
 */
public class PilaGenerica<T> {

	/**
	 * Nodo genérico de la lista enlazada: un dato de tipo {@code E} y la referencia al siguiente.
	 *
	 * <p>Es {@code static} y con su propio tipo {@code E} porque no necesita el {@code T} externo:
	 * un nodo genérico sirve para cualquier pila.</p>
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

	/** Cabeza de la lista = TOPE de la pila. Es null cuando la pila está vacía. */
	private Nodo<T> head;

	/** Cantidad de elementos apilados. */
	private int size;

	public PilaGenerica() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * Apila un elemento (push). Inserta un nodo al INICIO de la lista, que pasa a ser el tope.
	 *
	 * <p>Complejidad O(1): el nuevo nodo pasa a ser el head y apunta al anterior head.</p>
	 *
	 * @param dato valor a apilar (puede ser cualquier tipo T).
	 */
	public void apilar(T dato) {
		Nodo<T> nuevo = new Nodo<>(dato);
		nuevo.setSiguiente(head); // el nuevo tope apunta al tope anterior
		head = nuevo;             // y pasa a ser el nuevo head (tope)
		size++;
	}

	/**
	 * Desapila el tope (pop) y devuelve su dato.
	 *
	 * <p>Complejidad O(1): solo mueve el head al siguiente. El nodo removido queda inaccesible para
	 * que lo libere el Garbage Collector (no se "borra" a mano).</p>
	 *
	 * @return el dato que estaba en el tope.
	 * @throws EmptyStackException si la pila está vacía.
	 */
	public T desapilar() {
		if (estaVacia()) {
			throw new EmptyStackException();
		}
		T dato = head.getDato();
		head = head.getSiguiente(); // el tope siguiente pasa a ser el tope
		size--;
		return dato;
	}

	/**
	 * Devuelve el dato del tope (peek) sin desapilar.
	 *
	 * <p>Complejidad O(1): solo lee el head.</p>
	 *
	 * @return el dato del tope.
	 * @throws EmptyStackException si la pila está vacía.
	 */
	public T tope() {
		if (estaVacia()) {
			throw new EmptyStackException();
		}
		return head.getDato();
	}

	/**
	 * Indica si la pila no tiene elementos (isEmpty).
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si la pila está vacía.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si la pila contiene un valor (contains), recorriendo desde el tope hacia la base.
	 *
	 * <p>Complejidad O(n): recorre nodo por nodo; la lista enlazada no tiene acceso directo.</p>
	 *
	 * <p>Usa {@code Objects.equals} para comparar CONTENIDO; {@code ==} compararía referencias y
	 * fallaría con objetos como String o Producto.</p>
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
	 * Imprime el contenido de la pila desde el tope hacia la base.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void imprimir() {
		if (estaVacia()) {
			System.out.println("(pila vacía)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			texto.append(String.valueOf(auxiliar.getDato())).append(" -> ");
			auxiliar = auxiliar.getSiguiente();
		}
		texto.append("null  (tope a la izquierda)");
		System.out.println(texto);
	}

	/** Devuelve la cantidad de elementos apilados. */
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
		System.out.println(" PILA GENÉRICA ENLAZADA (Integer / String / Producto)");
		System.out.println("============================================");

		// ---- Casos de pila vacía (cualquier tipo) ----
		System.out.println("\n--- Pila vacía ---");
		PilaGenerica<Integer> vacia = new PilaGenerica<>();
		comprobar(vacia.estaVacia(), "Debe estar vacía al inicio.");
		comprobar(vacia.getSize() == 0, "El tamaño inicial debe ser 0.");
		comprobar(!vacia.contiene(1), "No debe contener ningún valor.");
		comprobarExcepcion(vacia::desapilar, EmptyStackException.class,
				"Desapilar en pila vacía debe lanzar EmptyStackException.");
		comprobarExcepcion(vacia::tope, EmptyStackException.class,
				"Consultar tope en pila vacía debe lanzar EmptyStackException.");
		System.out.print("  imprimir(): ");
		vacia.imprimir();
		System.out.println("  estaVacia()=true, size=0, contiene(1)=false, desapilar/tope lanzan  [Correcto]");

		// ---- Integer ----
		System.out.println("\n--- PilaGenerica<Integer> ---");
		PilaGenerica<Integer> enteros = new PilaGenerica<>();
		enteros.apilar(10);
		enteros.apilar(20);
		enteros.apilar(30); // último en entrar = tope
		System.out.print("  Contenido: ");
		enteros.imprimir();
		comprobar(enteros.tope() == 30, "El tope debe ser 30 (el último apilado).");
		comprobar(enteros.contiene(20), "Debe contener el 20.");
		comprobar(!enteros.contiene(99), "No debe contener el 99.");
		comprobar(enteros.desapilar() == 30, "Primero sale el 30 (LIFO).");
		comprobar(enteros.desapilar() == 20, "Luego sale el 20 (LIFO).");
		comprobar(enteros.tope() == 10 && enteros.getSize() == 1, "Queda el 10 en el tope.");
		System.out.print("  Tras desapilar 30 y 20: ");
		enteros.imprimir();
		comprobar(enteros.desapilar() == 10 && enteros.estaVacia(), "Al sacar el 10 queda vacía.");
		System.out.println("  Orden LIFO verificado y la pila vuelve a quedar vacía  [Correcto]");

		// ---- String ----
		System.out.println("\n--- PilaGenerica<String> ---");
		PilaGenerica<String> textos = new PilaGenerica<>();
		textos.apilar("hola");
		textos.apilar("mundo");
		textos.apilar("java");
		System.out.print("  Contenido: ");
		textos.imprimir();
		comprobar(textos.tope().equals("java"), "El tope debe ser \"java\".");
		// "mundo" es otro literal/instancia pero con el MISMO contenido: equals debe dar true.
		comprobar(textos.contiene(new String("mundo")), "Debe encontrar \"mundo\" por contenido (equals).");
		comprobar(textos.desapilar().equals("java"), "Primero sale \"java\".");
		comprobar(textos.desapilar().equals("mundo"), "Luego sale \"mundo\".");
		System.out.println("  Busca por contenido y respeta LIFO  [Correcto]");

		// ---- Producto (objeto propio, igualdad por código) ----
		System.out.println("\n--- PilaGenerica<Producto> (objeto propio) ---");
		Producto pan = new Producto(101, "Pan");
		Producto leche = new Producto(202, "Leche");
		Producto panDistinto = new Producto(101, "Pan"); // otra instancia, mismo código

		PilaGenerica<Producto> productos = new PilaGenerica<>();
		productos.apilar(pan);
		productos.apilar(leche);
		System.out.print("  Contenido: ");
		productos.imprimir();
		comprobar(productos.tope().equals(leche), "El tope debe ser Leche.");
		// panDistinto es OTRA instancia pero con el mismo código: equals debe dar true.
		comprobar(productos.contiene(panDistinto),
				"Debe encontrar el Pan por código (equals), no por referencia.");
		comprobar(productos.desapilar().equals(leche), "Primero sale Leche (LIFO).");
		comprobar(productos.desapilar().equals(pan), "Luego sale Pan.");
		comprobar(productos.estaVacia(), "Debe quedar vacía.");
		System.out.println("  Busca por código (equals) y respeta LIFO  [Correcto]");

		System.out.println("\n[Correcto] Las mismas operaciones funcionan con cualquier tipo de dato.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}

/**
 * Clase de objeto propio para probar la pila genérica con un tipo que NO es de la librería.
 *
 * <p>Define {@code equals} por código (dos productos con el mismo código son iguales) y
 * {@code toString} para poder imprimirlos. Sin {@code equals}, la búsqueda por contenido
 * ({@code contiene}) compararía referencias y no encontraría una instancia equivalente.</p>
 */
class Producto {

	private final int codigo;
	private final String nombre;

	Producto(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	/** Dos productos son iguales si tienen el mismo código. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Producto)) {
			return false;
		}
		Producto producto = (Producto) otro;
		return codigo == producto.codigo;
	}

	/** Coherente con equals: el hashCode depende del código. */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(codigo);
	}

	/** Representación legible para imprimir la pila. */
	@Override
	public String toString() {
		return "Producto(" + codigo + ", " + nombre + ")";
	}
}
