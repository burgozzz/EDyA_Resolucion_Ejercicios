/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una pila genérica llamada Pila<T> usando un arreglo interno
 * de tamaño fijo. Debe permitir agregar un elemento, quitar y devolver el que está arriba,
 * consultar la cima sin quitarla, y comprobar si está vacía o llena y cuántos elementos
 * contiene. Usa un índice top que empiece en -1. Al llenarse, no agregues el elemento;
 * al estar vacía, informa claramente si se intenta quitar o consultar la cima.
 *
 * En Java no se pueden crear directamente arreglos de un tipo genérico. Elige una forma
 * sencilla de manejar el arreglo interno y explica brevemente esa decisión en un comentario.
 * Prueba la misma implementación con enteros, textos y una clase sencilla creada para la
 * demostración. Muestra que se respeta el orden LIFO e incluye casos de pila vacía y llena.
 * Mantén el código simple y sin dependencias externas.
 *
 * Incluye este prompt como comentario al inicio del archivo y una demostración ejecutable.
 * Si hay errores de compilación o alguna prueba falla, corrígelos y vuelve a probar. Al
 * finalizar, informa brevemente si fue necesario ajustar el prompt y muestra una prueba.
 */
package GUIA_PilasYColasArreglos.Ejercicio_5;

/** Pila LIFO genérica de capacidad fija, reutilizable con cualquier tipo de referencia. */
public class Pila<T> {
	// Java no permite crear T[] directamente; Object[] almacena los valores genéricos.
	private final Object[] elementos;
	private int top = -1;

	public Pila(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new Object[capacidad];
	}

	/** Agrega un elemento en la cima o falla si la pila está llena. */
	public void push(T elemento) {
		if (isFull()) {
			throw new IllegalStateException("La pila está llena; no se agregó el elemento.");
		}
		elementos[++top] = elemento;
	}

	/** Retira y devuelve la cima; falla si la pila está vacía. */
	@SuppressWarnings("unchecked")
	public T pop() {
		if (isEmpty()) {
			throw new IllegalStateException("La pila está vacía; no hay elementos para retirar.");
		}
		T elemento = (T) elementos[top];
		elementos[top--] = null;
		return elemento;
	}

	/** Consulta la cima sin modificar la pila; falla si está vacía. */
	@SuppressWarnings("unchecked")
	public T peek() {
		if (isEmpty()) {
			throw new IllegalStateException("La pila está vacía; no hay una cima para consultar.");
		}
		return (T) elementos[top];
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == elementos.length - 1;
	}

	public int size() {
		return top + 1;
	}

	private static final class Producto {
		private final String nombre;

		private Producto(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			return nombre;
		}
	}

	private static void demostrarEnteros() {
		System.out.println("\n--- Pila de enteros ---");
		Pila<Integer> pila = new Pila<>(3);
		pila.push(10);
		pila.push(20);
		pila.push(30);
		System.out.println("Agregados: 10, 20, 30");
		System.out.println("Tamaño: " + pila.size() + " | Llena: " + pila.isFull()
				+ " | Cima: " + pila.peek());

		try {
			pila.push(40);
			throw new AssertionError("Se esperaba un error al agregar a una pila llena.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		for (int esperado : new int[]{30, 20, 10}) {
			int retirado = pila.pop();
			System.out.println("Retirado: " + retirado);
			if (retirado != esperado) {
				throw new AssertionError("La pila no respetó el orden LIFO.");
			}
		}
		if (!pila.isEmpty() || pila.size() != 0) {
			throw new AssertionError("La pila debería estar vacía después de retirar todo.");
		}

		try {
			pila.peek();
			throw new AssertionError("Se esperaba un error al consultar una pila vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			pila.pop();
			throw new AssertionError("Se esperaba un error al retirar de una pila vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
	}

	private static void demostrarTextos() {
		System.out.println("\n--- Pila de textos ---");
		Pila<String> pila = new Pila<>(2);
		pila.push("Hola");
		pila.push("Java");
		System.out.println("Cima: " + pila.peek());
		if (!"Java".equals(pila.pop()) || !"Hola".equals(pila.pop())) {
			throw new AssertionError("La pila de textos no respetó el orden LIFO.");
		}
		System.out.println("Retirados en orden LIFO: Java, Hola");
	}

	private static void demostrarObjetos() {
		System.out.println("\n--- Pila de objetos ---");
		Pila<Producto> pila = new Pila<>(2);
		Producto primerProducto = new Producto("Cuaderno");
		Producto segundoProducto = new Producto("Lápiz");
		pila.push(primerProducto);
		pila.push(segundoProducto);
		System.out.println("Cima: " + pila.peek());
		if (pila.pop() != segundoProducto || pila.pop() != primerProducto) {
			throw new AssertionError("La pila de objetos no respetó el orden LIFO.");
		}
		System.out.println("Retirados en orden LIFO: Lápiz, Cuaderno");
	}

	public static void main(String[] args) {
		System.out.println("====================================");
		System.out.println(" PILA GENÉRICA | DEMOSTRACIÓN LIFO");
		System.out.println("====================================");
		demostrarEnteros();
		demostrarTextos();
		demostrarObjetos();
		System.out.println("\n[Correcto] La misma pila funcionó con enteros, textos y objetos.");
	}
}