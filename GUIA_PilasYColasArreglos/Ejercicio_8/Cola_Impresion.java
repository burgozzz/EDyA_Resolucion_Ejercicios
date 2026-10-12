/*
 * PROMPT UTILIZADO:
 * Desarrolla un codigo en java para la cola de una impresora
 * 
 * Cada documento tiene un nombre y una cantidad de páginas, y se tienen que imprimir en
 * el orden en que los mandé. Quiero usar una COLA SIMPLE (FIFO) con un arreglo de tamaño
 * fijo, no ArrayList.
 *
 * La clase ColaImpresion tiene que poder: agregarDocumento(nombre, paginas);
 * imprimirSiguiente() (saca el que está primero); consultarProximaImpresion() (lo muestra
 * sin sacarlo); estaVacia(), estaLlena() y tamano(); y mostrarCola() con los índices front
 * y rear.
 *
 * Usar un arreglo con front (el próximo a imprimir) y rear (el último que llegó),
 * arrancándolos en -1 para representar la cola vacía, y comentar cómo se mueven.
 *
 * Casos límite: capacidad 0 o negativa, nombre vacío o páginas <= 0, cola llena al
 * agregar, cola vacía al imprimir o consultar (avisar con error y NO cambiar la cola), y
 * que como es cola simple no se reutilizan los huecos del principio (dejarlo comentado).
 *
 * En el main hacer una prueba con 3 documentos (ej: "Informe.pdf" 5 páginas, "Trabajo.docx"
 * 12, "Carta.txt" 2) mostrando que salen en orden, más los casos de cola vacía y llena.
 * Después un menú simple de consola para agregar, imprimir, consultar y salir.
 *
 * Explicar por qué va con cola FIFO: el primero que llega se imprime primero, que es como
 * funciona una impresora de verdad. Con una pila saldría primero el último y no tendría
 * sentido.
 */
package GUIA_PilasYColasArreglos.Ejercicio_8;

import java.util.Scanner;

/**
 * Cola simple (FIFO) de documentos para una impresora, sobre un arreglo de tamaño fijo.
 *
 * <p>Se usa una cola y no una pila porque representa correctamente el orden de impresión:
 * el primer documento que se envía es el primero que se imprime, tal como ocurre en una
 * impresora real. Con una pila (LIFO) saldría primero el último documento enviado, lo cual
 * no tendría sentido para imprimir.</p>
 */
public class Cola_Impresion {

	/** Documento en la cola: nombre y cantidad de páginas. */
	public static final class Documento {
		private final String nombre;
		private final int paginas;

		public Documento(String nombre, int paginas) {
			this.nombre = nombre;
			this.paginas = paginas;
		}

		public String getNombre() {
			return nombre;
		}

		public int getPaginas() {
			return paginas;
		}

		@Override
		public String toString() {
			return nombre + " (" + paginas + (paginas == 1 ? " página)" : " páginas)");
		}
	}

	private final Documento[] elementos;
	private int front = -1; // próximo a imprimir; -1 significa cola vacía
	private int rear = -1;  // último documento agregado

	public Cola_Impresion(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new Documento[capacidad];
	}

	/**
	 * Encola un documento al final de la cola de impresión.
	 * No reutiliza huecos del inicio (cola simple, no circular).
	 */
	public void agregarDocumento(String nombre, int paginas) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del documento no puede estar vacío.");
		}
		if (paginas <= 0) {
			throw new IllegalArgumentException("La cantidad de páginas debe ser mayor que cero.");
		}
		if (estaLlena()) {
			throw new IllegalStateException("La cola está llena; no se agregó el documento.");
		}
		if (estaVacia()) {
			front = 0; // el primer documento también es el próximo a imprimir
		}
		elementos[++rear] = new Documento(nombre.trim(), paginas);
	}

	/** Saca y devuelve el documento del frente, respetando el orden FIFO. */
	public Documento imprimirSiguiente() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay documentos para imprimir.");
		}
		Documento documento = elementos[front];
		elementos[front] = null;
		front++;

		// Al imprimir el último documento, la cola vuelve al estado vacío.
		if (front > rear) {
			front = -1;
			rear = -1;
		}
		return documento;
	}

	/** Consulta cuál será el próximo documento sin imprimirlo. */
	public Documento consultarProximaImpresion() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay un próximo documento.");
		}
		return elementos[front];
	}

	public boolean estaVacia() {
		return front == -1;
	}

	/** Como no es circular, se llena cuando rear llega al último índice del arreglo. */
	public boolean estaLlena() {
		return rear == elementos.length - 1;
	}

	public int tamano() {
		return estaVacia() ? 0 : rear - front + 1;
	}

	/** Muestra el orden de impresión y los índices front y rear. */
	public void mostrarCola() {
		System.out.println("  front = " + front + " | rear = " + rear + " | tamaño = " + tamano());
		if (estaVacia()) {
			System.out.println("  (cola de impresión vacía)");
			return;
		}
		for (int i = 0; i <= rear; i++) {
			boolean enEspera = i >= front;
			String marcas = "";
			if (i == front) {
				marcas += " <- próximo a imprimir";
			}
			if (i == rear) {
				marcas += " <- último agregado";
			}
			System.out.println("  [" + i + "] " + (enEspera ? elementos[i] : "(hueco no reutilizable)") + marcas);
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
		System.out.println(" COLA DE IMPRESIÓN | FIFO");
		System.out.println("====================================");

		try {
			new Cola_Impresion(0);
			throw new AssertionError("Se esperaba rechazar la capacidad cero.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		Cola_Impresion impresora = new Cola_Impresion(3);
		System.out.println("\nCapacidad: 3");
		System.out.println("¿Cola vacía al iniciar? " + impresora.estaVacia()
				+ " | Tamaño: " + impresora.tamano());

		// Cola vacía: imprimir y consultar deben fallar sin modificar el estado.
		try {
			impresora.imprimirSiguiente();
			throw new AssertionError("Se esperaba error al imprimir con la cola vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			impresora.consultarProximaImpresion();
			throw new AssertionError("Se esperaba error al consultar una cola vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		// Datos inválidos no deben encolar.
		try {
			impresora.agregarDocumento("   ", 3);
			throw new AssertionError("Se esperaba rechazar un nombre vacío.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			impresora.agregarDocumento("Error.pdf", 0);
			throw new AssertionError("Se esperaba rechazar páginas <= 0.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		comprobar(impresora.tamano() == 0, "La cola no cambió tras rechazar documentos inválidos.");

		// Documentos de ejemplo.
		impresora.agregarDocumento("Informe.pdf", 5);
		impresora.agregarDocumento("Trabajo.docx", 12);
		impresora.agregarDocumento("Carta.txt", 2);
		System.out.println("\nDocumentos enviados en orden: Informe.pdf, Trabajo.docx, Carta.txt");
		System.out.println("Próximo a imprimir: " + impresora.consultarProximaImpresion());
		System.out.println("¿Cola llena? " + impresora.estaLlena());

		// Cola llena: no debe agregar.
		try {
			impresora.agregarDocumento("Extra.pdf", 1);
			throw new AssertionError("Se esperaba error al agregar con la cola llena.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		comprobar(impresora.tamano() == 3, "La cola cambió tras rechazar un alta con la cola llena.");

		System.out.println("\nImpresión en orden FIFO:");
		String[] nombresEsperados = {"Informe.pdf", "Trabajo.docx", "Carta.txt"};
		for (int i = 0; i < nombresEsperados.length; i++) {
			Documento impreso = impresora.imprimirSiguiente();
			System.out.println("  Imprimiendo: " + impreso);
			comprobar(nombresEsperados[i].equals(impreso.getNombre()),
					"FIFO debe imprimir primero el documento que llegó antes.");
		}
		comprobar(impresora.estaVacia() && impresora.tamano() == 0, "La cola debe quedar vacía.");

		System.out.println("\n[Correcto] FIFO, cola vacía, cola llena y datos inválidos verificados.");
		System.out.println("La cola respeta el orden de impresión (FIFO). Una pila imprimiría"
				+ " primero el último enviado, lo cual no tendría sentido.");
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

	private static void ejecutarMenu(Cola_Impresion impresora, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de impresión ---");
			System.out.println("1. Agregar documento");
			System.out.println("2. Imprimir siguiente documento");
			System.out.println("3. Consultar próximo documento");
			System.out.println("4. Ver estado y cola");
			System.out.println("0. Salir");

			Integer opcion = leerEntero(scanner, "Seleccione una opción: ");
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					System.out.print("Nombre del documento: ");
					if (!scanner.hasNextLine()) {
						continuar = false;
						break;
					}
					String nombre = scanner.nextLine();
					Integer paginas = leerEntero(scanner, "Cantidad de páginas: ");
					if (paginas == null) {
						continuar = false;
						break;
					}
					try {
						impresora.agregarDocumento(nombre, paginas);
						System.out.println("Documento agregado. En cola: " + impresora.tamano() + ".");
					} catch (IllegalArgumentException | IllegalStateException exception) {
						System.out.println("No se pudo agregar: " + exception.getMessage());
					}
					break;
				case 2:
					try {
						System.out.println("Impreso: " + impresora.imprimirSiguiente());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo imprimir: " + exception.getMessage());
					}
					break;
				case 3:
					try {
						System.out.println("Próximo a imprimir: " + impresora.consultarProximaImpresion());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo consultar: " + exception.getMessage());
					}
					break;
				case 4:
					System.out.println("¿Vacía? " + (impresora.estaVacia() ? "Sí" : "No")
							+ " | ¿Llena? " + (impresora.estaLlena() ? "Sí" : "No"));
					impresora.mostrarCola();
					break;
				default:
					System.out.println("Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada.");
	}

	public static void main(String[] args) {
		demostrar();
		System.out.println("\nAhora puede probar otra impresora con capacidad 3.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Cola_Impresion(3), scanner);
		}
	}
}
