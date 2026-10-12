/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una cola simple de números enteros usando un arreglo de
 * tamaño fijo, en una clase llamada ColaEnteros.
 *
 * Necesito agregar y quitar elementos, consultar cuál está al frente y saber si la cola
 * está vacía o llena y cuántos elementos contiene. Usa los índices front y rear: front
 * señala el próximo elemento que se quitará y rear el último que se agregó. Inicialízalos
 * de forma que representen una cola vacía y explica con comentarios cómo cambian al
 * encolar y desencolar.
 *
 * Como es una cola simple y no circular, no reutilices posiciones que queden libres al
 * principio ni desplaces elementos. Si se intenta encolar con el arreglo lleno o
 * desencolar o consultar el frente cuando está vacía, informa claramente y no alteres
 * la cola.
 *
 * Incluye una demostración con algunos enteros. Muestra el frente, el tamaño y el estado
 * antes y después de las operaciones. Comprueba FIFO, la cola vacía y llena, y que una
 * posición liberada al inicio no se pueda reutilizar hasta vaciar toda la cola. Mantén
 * el código sencillo y sin dependencias externas. Después de la demostración, ofrece un
 * menú para encolar, desencolar, consultar el frente y ver el arreglo junto con front y
 * rear. Valida las opciones no numéricas. Si hay errores de compilación o alguna prueba
 * falla, corrígelos y vuelve a probar. Informa brevemente si fue necesario ajustar el
 * prompt y muestra una prueba de ejecución.
 */
package GUIA_PilasYColasArreglos.Ejercicio_6;

import java.util.Scanner;

public class ColaEnteros {
	private final int[] elementos;
	private int front = -1;
	private int rear = -1;

	public ColaEnteros(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new int[capacidad];
	}

	/** Agrega al final de la cola; en una cola lineal no se reutilizan huecos iniciales. */
	public void encolar(int valor) {
		if (estaLlena()) {
			throw new IllegalStateException("La cola está llena; no se agregó el elemento.");
		}
		if (estaVacia()) {
			front = 0;
		}
		elementos[++rear] = valor;
	}

	/** Quita y devuelve el elemento del frente siguiendo el orden FIFO. */
	public int desencolar() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay elementos para quitar.");
		}
		int valor = elementos[front];
		elementos[front] = 0;
		front++;

		// Cuando se retira el último elemento, ambos índices vuelven al estado vacío.
		if (front > rear) {
			front = -1;
			rear = -1;
		}
		return valor;
	}

	/** Consulta el próximo elemento que se quitará, sin modificar la cola. */
	public int consultarFrente() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay un frente para consultar.");
		}
		return elementos[front];
	}

	public boolean estaVacia() {
		return front == -1;
	}

	/** Indica si rear llegó al final; los huecos anteriores no se reutilizan. */
	public boolean estaLlena() {
		return rear == elementos.length - 1;
	}

	public int tamano() {
		return estaVacia() ? 0 : rear - front + 1;
	}

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private void mostrarArreglo() {
		for (int i = 0; i < elementos.length; i++) {
			boolean ocupado = !estaVacia() && i >= front && i <= rear;
			System.out.println("  [" + i + "] " + (ocupado ? elementos[i] : "(vacío)")
					+ (i == front ? " <- front" : "")
					+ (i == rear ? " <- rear" : ""));
		}
	}

	private static Integer leerEntero(Scanner scanner, String mensaje) {
		while (true) {
			System.out.print(mensaje);
			if (!scanner.hasNextLine()) {
				return null;
			}
			String entrada = scanner.nextLine().trim();
			try {
				return Integer.valueOf(entrada);
			} catch (NumberFormatException exception) {
				System.out.println("Entrada no válida. Ingrese un número entero.");
			}
		}
	}

	private static void ejecutarMenu(ColaEnteros cola, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la cola ---");
			System.out.println("1. Encolar un entero");
			System.out.println("2. Desencolar el frente");
			System.out.println("3. Consultar el frente");
			System.out.println("4. Ver estado e índices");
			System.out.println("5. Mostrar arreglo y huecos");
			System.out.println("0. Salir");

			Integer opcion = leerEntero(scanner, "Seleccione una opción: ");
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					Integer valor = leerEntero(scanner, "Ingrese el entero que desea encolar: ");
					if (valor == null) {
						continuar = false;
						break;
					}
					try {
						cola.encolar(valor);
						System.out.println("Encolado: " + valor + ". Tamaño actual: " + cola.tamano() + ".");
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo encolar: " + exception.getMessage());
					}
					break;
				case 2:
					try {
						System.out.println("Desencolado: " + cola.desencolar());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo desencolar: " + exception.getMessage());
					}
					break;
				case 3:
					try {
						System.out.println("Elemento al frente: " + cola.consultarFrente());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo consultar: " + exception.getMessage());
					}
					break;
				case 4:
					System.out.println("Tamaño: " + cola.tamano());
					System.out.println("¿Vacía? " + (cola.estaVacia() ? "Sí" : "No"));
					System.out.println("¿Llena? " + (cola.estaLlena() ? "Sí" : "No"));
					System.out.println("front: " + cola.front + " | rear: " + cola.rear);
					break;
				case 5:
					cola.mostrarArreglo();
					break;
				default:
					System.out.println("Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada.");
	}

	public static void main(String[] args) {
		System.out.println("====================================");
		System.out.println(" COLA SIMPLE DE ENTEROS | FIFO");
		System.out.println("====================================");

		ColaEnteros cola = new ColaEnteros(3);
		System.out.println("Capacidad: 3");
		System.out.println("Cola vacía al inicio: " + cola.estaVacia());
		System.out.println("Tamaño: " + cola.tamano());
		try {
			cola.consultarFrente();
			throw new AssertionError("Se esperaba error al consultar una cola vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			cola.desencolar();
			throw new AssertionError("Se esperaba error al quitar de una cola vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		for (int valor : new int[]{10, 20, 30}) {
			cola.encolar(valor);
			System.out.println("Encolado: " + valor + " | Frente: " + cola.consultarFrente()
					+ " | Tamaño: " + cola.tamano());
		}
		System.out.println("¿Está llena? " + cola.estaLlena());
		try {
			cola.encolar(40);
			throw new AssertionError("Se esperaba error al encolar en una cola llena.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		comprobar(cola.tamano() == 3 && cola.consultarFrente() == 10,
				"La cola cambió después de rechazar una inserción llena.");

		int primero = cola.desencolar();
		System.out.println("Desencolado: " + primero + " | Frente ahora: " + cola.consultarFrente()
				+ " | Tamaño: " + cola.tamano());
		comprobar(primero == 10, "FIFO debe quitar primero el valor más antiguo.");
		try {
			cola.encolar(40);
			throw new AssertionError("La cola simple no debe reutilizar el hueco inicial.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] No se reutiliza la posición libre inicial: "
					+ exception.getMessage());
		}

		int segundo = cola.desencolar();
		int tercero = cola.desencolar();
		System.out.println("Desencolados en orden FIFO: " + segundo + ", " + tercero);
		comprobar(segundo == 20 && tercero == 30, "Los elementos deben salir en orden FIFO.");
		comprobar(cola.estaVacia() && cola.tamano() == 0, "La cola debe quedar vacía.");
		cola.encolar(40);
		comprobar(cola.consultarFrente() == 40 && cola.tamano() == 1,
				"Al vaciarse toda la cola, los índices deben reiniciarse.");

		System.out.println("\n[Correcto] FIFO, límites y funcionamiento de front/rear verificados.");
		System.out.println("La cola simple no recupera huecos iniciales hasta quedar completamente vacía.");

		System.out.println("\nAhora puedes probar otra cola con capacidad 3.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new ColaEnteros(3), scanner);
		}
	}
}