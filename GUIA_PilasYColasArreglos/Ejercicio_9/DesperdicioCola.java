/*
 * PROMPT UTILIZADO:
 * Desarrolla un codigo en java donde se pueda ver el desperdicio de espacio de una cola simple
 * 
 * Quiero una cola simple (FIFO) con un arreglo de tamaño fijo, no ArrayList, y que se vea
 * que los huecos que quedan libres al principio NO se pueden reutilizar.
 *
 * La clase DesperdicioCola tiene que poder: encolar(valor), desencolar() (saca el del
 * frente), consultarFrente() (sin sacarlo), estaVacia(), estaLlena(), tamano(), y
 * mostrarArreglo() que imprima todo el arreglo marcando front, rear, qué está ocupado y
 * qué quedó como hueco libre al inicio.
 *
 * Usar front (el próximo a salir) y rear (el último que entró), arrancándolos en -1 para
 * la cola vacía, y comentar cómo se mueven. No desplazar elementos ni reutilizar huecos.
 *
 * Casos límite: capacidad 0 o negativa, cola llena al encolar, cola vacía al desencolar o
 * consultar (avisar con error y NO cambiar la cola).
 *
 * Lo más importante: en el main quiero que se vea el problema. Demo con capacidad 4,
 * encolando varios elementos y desencolando algunos, mostrando el arreglo después de cada
 * paso. Tiene que quedar claro que aunque front avanzó y hay lugares libres al inicio, rear
 * llegó al final y la cola dice estar LLENA, así que no se puede encolar más hasta vaciarla
 * toda. Explicar en un comentario por qué pasa esto en una cola simple.
 *
 * Al final agregar un menú simple de consola para encolar, desencolar, consultar y ver el
 * arreglo.
 *
 * Y contar por qué una cola simple desperdicia memoria y cómo se arreglaría después (por
 * ejemplo con una cola circular y el operador módulo).
 */
package GUIA_PilasYColasArreglos.Ejercicio_9;

import java.util.Scanner;

/**
 * Cola simple (lineal) sobre un arreglo de tamaño fijo, usada para DEMOSTRAR el problema
 * del desperdicio de posiciones.
 *
 * <p>En una cola simple, {@code front} y {@code rear} solo avanzan hacia la derecha. Cuando
 * se desencola, {@code front} aumenta y las posiciones del inicio quedan libres, pero nunca
 * se reutilizan. Por eso, aunque haya huecos adelante, en cuanto {@code rear} llega al
 * último índice la cola se considera LLENA y no acepta más elementos hasta vaciarse por
 * completo. La solución es una cola circular, que reutiliza esos huecos con el operador
 * módulo {@code %}.</p>
 */
public class DesperdicioCola {

	private final int[] elementos;
	private int front = -1; // próximo a salir; -1 significa cola vacía
	private int rear = -1;  // último que entró

	public DesperdicioCola(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new int[capacidad];
	}

	/** Encola al final. En una cola simple no se reutilizan los huecos del inicio. */
	public void encolar(int valor) {
		if (estaLlena()) {
			throw new IllegalStateException("La cola está llena; no se agregó el elemento.");
		}
		if (estaVacia()) {
			front = 0;
		}
		elementos[++rear] = valor;
	}

	/** Saca y devuelve el elemento del frente (FIFO); front avanza y deja huecos atrás. */
	public int desencolar() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay elementos para quitar.");
		}
		int valor = elementos[front];
		elementos[front] = 0;
		front++;

		// Al quitar el último elemento, los índices vuelven al estado vacío.
		if (front > rear) {
			front = -1;
			rear = -1;
		}
		return valor;
	}

	/** Consulta el próximo elemento que saldrá, sin modificar la cola. */
	public int consultarFrente() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay un frente para consultar.");
		}
		return elementos[front];
	}

	public boolean estaVacia() {
		return front == -1;
	}

	/** Se llena cuando rear llega al último índice, aunque haya huecos libres al inicio. */
	public boolean estaLlena() {
		return rear == elementos.length - 1;
	}

	public int tamano() {
		return estaVacia() ? 0 : rear - front + 1;
	}

	/**
	 * Muestra TODO el arreglo: valores ocupados, huecos libres al inicio (no reutilizables)
	 * y espacio libre al final, junto con los índices front y rear.
	 */
	public void mostrarArreglo() {
		System.out.println("  front = " + front + " | rear = " + rear + " | tamaño = " + tamano()
				+ " | ¿llena? " + (estaLlena() ? "Sí" : "No"));
		for (int i = 0; i < elementos.length; i++) {
			String contenido;
			if (estaVacia()) {
				contenido = "(vacío)";
			} else if (i < front) {
				contenido = "(HUECO libre, NO reutilizable)";
			} else if (i <= rear) {
				contenido = String.valueOf(elementos[i]);
			} else {
				contenido = "(libre al final)";
			}
			String marcas = "";
			if (i == front) {
				marcas += "  <- front";
			}
			if (i == rear) {
				marcas += "  <- rear";
			}
			System.out.println("  [" + i + "] " + contenido + marcas);
		}
	}

	// ------------------------------------------------------------------
	// Demostración y menú
	// ------------------------------------------------------------------

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private static void demostrar() {
		System.out.println("====================================");
		System.out.println(" DESPERDICIO DE ESPACIO | COLA SIMPLE");
		System.out.println("====================================");

		try {
			new DesperdicioCola(0);
			throw new AssertionError("Se esperaba rechazar la capacidad cero.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		DesperdicioCola cola = new DesperdicioCola(4);
		System.out.println("\nCapacidad: 4");
		System.out.println("\n--- Estado inicial (cola vacía) ---");
		cola.mostrarArreglo();

		System.out.println("\n--- Encolamos 10, 20, 30, 40 ---");
		for (int valor : new int[]{10, 20, 30, 40}) {
			cola.encolar(valor);
		}
		cola.mostrarArreglo();

		System.out.println("\n--- Desencolamos 10 y 20 (front avanza y deja huecos) ---");
		comprobar(cola.desencolar() == 10 && cola.desencolar() == 20,
				"FIFO debe sacar 10 y luego 20.");
		cola.mostrarArreglo();

		// Aquí se ve el problema: hay 2 huecos libres al inicio, pero la cola dice estar llena.
		System.out.println("\n--- Intentamos encolar 50 ---");
		System.out.println("Hay 2 huecos libres en [0] y [1], pero rear ya llegó al final.");
		try {
			cola.encolar(50);
			throw new AssertionError("Se esperaba error: la cola está llena pese a los huecos.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		comprobar(cola.tamano() == 2 && cola.estaLlena() && cola.consultarFrente() == 30,
				"El estado no debe cambiar al rechazar el encolado.");
		cola.mostrarArreglo();
		System.out.println("  >> El desperdicio es evidente: [0] y [1] quedan inservibles hasta vaciar la cola.");

		System.out.println("\n--- Vaciamos la cola (desencolamos 30 y 40) ---");
		comprobar(cola.desencolar() == 30 && cola.desencolar() == 40,
				"FIFO debe sacar 30 y luego 40.");
		comprobar(cola.estaVacia(), "La cola debe quedar vacía.");
		cola.mostrarArreglo();

		System.out.println("\n--- Recién ahora se puede encolar de nuevo: encolamos 50 ---");
		cola.encolar(50);
		cola.mostrarArreglo();
		comprobar(cola.consultarFrente() == 50 && cola.tamano() == 1,
				"Al vaciarse, los índices se reinician y se puede encolar otra vez.");

		System.out.println("\n[Correcto] Desperdicio demostrado: los huecos del inicio no se reutilizan.");
		System.out.println("Solución futura: una cola CIRCULAR que mueva front/rear con el operador %.");
		System.out.println("Ejemplo: rear = (rear + 1) % capacidad y front = (front + 1) % capacidad.");
	}

	private static Integer leerEntero(Scanner scanner, String mensaje) {
		while (scanner.hasNextLine()) {
			System.out.print(mensaje);
			String entrada = scanner.nextLine().trim();
			try {
				return Integer.valueOf(entrada);
			} catch (NumberFormatException exception) {
				System.out.println("Entrada no válida. Ingrese un número entero.");
			}
		}
		return null;
	}

	private static void ejecutarMenu(DesperdicioCola cola, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la cola simple ---");
			System.out.println("1. Encolar un entero");
			System.out.println("2. Desencolar el frente");
			System.out.println("3. Consultar el frente");
			System.out.println("4. Mostrar el arreglo");
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
					cola.mostrarArreglo();
					break;
				default:
					System.out.println("Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada.");
	}

	public static void main(String[] args) {
		demostrar();
		System.out.println("\nAhora puede probar otra cola simple con capacidad 4.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new DesperdicioCola(4), scanner);
		}
	}
}
