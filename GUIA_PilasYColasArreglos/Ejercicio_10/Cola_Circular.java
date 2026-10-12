/*
 * PROMPT UTILIZADO:
 *Desarrolla en java una cola circular

 * Es parecida a la cola simple, pero esta SÍ tiene que reutilizar los
 * huecos que quedan libres al principio, dando la vuelta con el operador módulo.
 * 
 * La clase ColaCircular tiene que poder: encolar(valor), desencolar() (saca el del frente),
 * consultarFrente() (sin sacarlo), estaVacia(), estaLlena(), tamano(), y mostrarArreglo()
 * que muestre todo el arreglo con front, rear, el tamaño y qué posiciones están ocupadas.
 *
 * Para el arreglo interno: usar un arreglo de tamaño fijo (nada de ArrayList) con front (el
 * próximo a salir) y rear (el último que entró), pero que avancen con MÓDULO:
 * front = (front + 1) % capacidad y rear = (rear + 1) % capacidad, así al llegar al final
 * vuelven al principio. Comentar cómo se mueven en cada operación.
 *
 * Para no confundir LLENA con VACÍA, llevar un contador de elementos (tamano): vacía cuando
 * tamano == 0 y llena cuando tamano == capacidad. Explicar en un comentario por qué el
 * contador evita el lío de "front == rear".
 *
 * Casos límite: capacidad 0 o negativa, cola llena al encolar, cola vacía al desencolar o
 * consultar (avisar con error y NO cambiar la cola).
 *
 * Lo más importante: en el main QUE SE VEA que ahora no se desperdicia espacio. Demo con
 * capacidad 4: encolar 10, 20, 30, 40 (llena); desencolar 10 y 20 (quedan libres [0] y [1]);
 * encolar 50 y 60: rear da la vuelta y reusa [0] y [1]. Mostrar el arreglo después de cada
 * paso y contar por qué ahora sí se reutilizan los huecos (al contrario que en el Ej. 9).
 *
 * Al final agregar un menú simple de consola para encolar, desencolar, consultar el frente y
 * ver el arreglo. Contar cómo el módulo hace que la cola dé la vuelta y reutilice posiciones,
 * comparándolo con la cola simple que desperdiciaba memoria.
 */
package GUIA_PilasYColasArreglos.Ejercicio_10;

import java.util.Scanner;

/**
 * Cola circular (FIFO) sobre un arreglo de tamaño fijo que REUTILIZA los huecos liberados.
 *
 * <p>El operador módulo {@code %} hace que los índices "den la vuelta": cuando {@code front}
 * o {@code rear} llegan al último índice, la próxima posición vuelve a ser 0. Así las
 * posiciones que quedan libres al principio se reutilizan, a diferencia de la cola simple
 * (lineal) del Ejercicio 9, que las desperdiciaba.</p>
 *
 * <p>Se lleva un contador {@code cantidad}. Si solo se compararan {@code front} y {@code rear}
 * no se podría distinguir una cola llena de una vacía, porque en ambos casos ambos índices
 * pueden coincidir. El contador lo resuelve sin ambigüedad: vacía si {@code cantidad == 0} y
 * llena si {@code cantidad == capacidad}.</p>
 */
public class Cola_Circular {

	private final int[] elementos;
	private int front = 0;       // próximo a salir
	private int rear = -1;       // último que entró
	private int cantidad = 0;    // contador de elementos (resuelve llena vs vacía)

	public Cola_Circular(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new int[capacidad];
	}

	/** Encola al final. rear avanza con módulo, así reutiliza posiciones del inicio. */
	public void encolar(int valor) {
		if (estaLlena()) {
			throw new IllegalStateException("La cola está llena; no se agregó el elemento.");
		}
		rear = (rear + 1) % elementos.length; // si rear estaba en el final, vuelve a 0
		elementos[rear] = valor;
		cantidad++;
	}

	/** Saca y devuelve el elemento del frente. front avanza con módulo. */
	public int desencolar() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay elementos para quitar.");
		}
		int valor = elementos[front];
		elementos[front] = 0;
		front = (front + 1) % elementos.length; // front también da la vuelta
		cantidad--;
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
		return cantidad == 0;
	}

	public boolean estaLlena() {
		return cantidad == elementos.length;
	}

	public int tamano() {
		return cantidad;
	}

	/**
	 * Muestra todo el arreglo indicando front, rear, tamaño y qué posiciones están ocupadas.
	 * Una posición está ocupada si queda dentro de los "cantidad" elementos desde front.
	 */
	public void mostrarArreglo() {
		System.out.println("  front = " + front + " | rear = " + rear + " | tamaño = " + tamano()
				+ " | ¿llena? " + (estaLlena() ? "Sí" : "No"));
		for (int i = 0; i < elementos.length; i++) {
			boolean ocupado = cantidad > 0 && ((i - front + elementos.length) % elementos.length) < cantidad;
			String contenido = ocupado ? String.valueOf(elementos[i]) : "(libre)";
			String marcas = "";
			if (ocupado && i == front) {
				marcas += "  <- front";
			}
			if (ocupado && i == rear) {
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
		System.out.println(" COLA CIRCULAR | REUTILIZA HUECOS");
		System.out.println("====================================");

		try {
			new Cola_Circular(0);
			throw new AssertionError("Se esperaba rechazar la capacidad cero.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		Cola_Circular cola = new Cola_Circular(4);
		System.out.println("\nCapacidad: 4");
		System.out.println("\n--- Estado inicial (cola vacía) ---");
		cola.mostrarArreglo();

		System.out.println("\n--- Encolamos 10, 20, 30, 40 ---");
		for (int valor : new int[]{10, 20, 30, 40}) {
			cola.encolar(valor);
		}
		cola.mostrarArreglo();
		comprobar(cola.estaLlena(), "Con 4 elementos la cola debe estar llena.");

		System.out.println("\n--- Desencolamos 10 y 20 (se liberan [0] y [1]) ---");
		comprobar(cola.desencolar() == 10 && cola.desencolar() == 20,
				"FIFO debe sacar 10 y luego 20.");
		cola.mostrarArreglo();

		System.out.println("\n--- Encolamos 50 (rear da la vuelta de 3 a 0) ---");
		cola.encolar(50);
		cola.mostrarArreglo();
		comprobar(cola.tamano() == 3, "Tras encolar 50 debe haber 3 elementos.");

		System.out.println("\n--- Encolamos 60 (reusa [1] y vuelve a estar llena) ---");
		cola.encolar(60);
		cola.mostrarArreglo();
		comprobar(cola.estaLlena() && cola.tamano() == 4,
				"Debe quedar llena reutilizando las 4 posiciones.");
		System.out.println("  >> Los huecos [0] y [1] se REUTILIZARON: ya no hay desperdicio.");
		System.out.println("  >> A diferencia del Ejercicio 9, aquí rear volvió al inicio con %.");

		System.out.println("\n--- Sacamos todo en orden FIFO ---");
		int[] esperados = {30, 40, 50, 60};
		for (int esperado : esperados) {
			int valor = cola.desencolar();
			System.out.println("  Desencolado: " + valor);
			comprobar(valor == esperado, "FIFO debe respetar el orden de encolado.");
		}
		comprobar(cola.estaVacia(), "La cola debe quedar vacía.");
		cola.mostrarArreglo();

		System.out.println("\n[Correcto] Cola circular probada: reutiliza huecos y respeta FIFO.");
		System.out.println("El módulo hace que front y rear den la vuelta: (indice + 1) % capacidad.");
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

	private static void ejecutarMenu(Cola_Circular cola, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la cola circular ---");
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
		System.out.println("\nAhora puede probar otra cola circular con capacidad 4.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Cola_Circular(4), scanner);
		}
	}
}
