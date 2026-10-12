/*
 * PROMPT UTILIZADO:
 * Desarrolla un codigo en Java para ralizar una agenda de turnos
 * La idea es que cada persona que llega reciba un turno y se agregue a una cola de
 * espera. Quiero hacerlo con una COLA SIMPLE (FIFO) usando un arreglo de tamaño fijo,
 * no con ArrayList. 
 * 
 * Necesito que la clase Turnos tenga: agregarPersona(nombre) que le da un número de
 * turno consecutivo y la pone al final; atenderSiguiente() que saca y devuelve a la
 * persona que está primero; consultarPrimero() que muestra quién está primero sin
 * sacarla; estaVacia(), estaLlena() y tamano(); y mostrarCola() para ver el orden y los
 * índices front y rear.
 *
 * Usa un arreglo y los índices front (el próximo a atender) y rear (el último que
 * llegó), arrancándolos de forma que la cola vacía quede representada (por ejemplo
 * front = -1). Explicá con comentarios cómo se mueven esos índices.
 *
 * Casos límite: si la capacidad es 0 o negativa, que tire error; si el nombre está
 * vacío, que no lo agregue; si la cola está llena al agregar, o vacía al atender o
 * consultar, que avise con un error y que NO cambie nada de la cola; que los números de
 * turno sigan la secuencia aunque se atienda gente; y como es cola simple, no se
 * reutilizan los huecos del principio (dejarlo comentado).
 *
 * Al final del main hacer una prueba con 3 personas (por ejemplo Ana, Bruno y Carla) y
 * mostrar que se atienden en el orden en que llegaron, más los casos de cola vacía y
 * llena. Después agregar un menú simple por consola para agregar, atender, consultar y
 * salir.
 *
 * Por último explicar por qué este problema va con FIFO y no con LIFO: porque el que
 * llega primero tiene que ser atendido primero (si fuera pila atendería al último, sería
 * injusto).
 */
package GUIA_PilasYColasArreglos.Ejercicio_7;

import java.util.Scanner;

/**
 * Cola simple (FIFO) de atención al público sobre un arreglo de tamaño fijo.
 *
 * <p>Se usa una cola y no una pila porque el turno respeta el orden de llegada: quien
 * llega primero debe ser atendido primero. Una pila (LIFO) atendería al último en
 * llegar, lo que sería injusto y rompería la lógica del sistema de turnos.</p>
 */
public class Turnos {

	/** Persona en espera: nombre y número de turno asignado al llegar. */
	public static final class Persona {
		private final String nombre;
		private final int numeroTurno;

		public Persona(String nombre, int numeroTurno) {
			this.nombre = nombre;
			this.numeroTurno = numeroTurno;
		}

		public String getNombre() {
			return nombre;
		}

		public int getNumeroTurno() {
			return numeroTurno;
		}

		@Override
		public String toString() {
			return "Turno " + numeroTurno + " - " + nombre;
		}
	}

	private final Persona[] elementos;
	private int front = -1; // próximo a atender; -1 significa cola vacía
	private int rear = -1;  // último que llegó
	private int siguienteTurno = 1;

	public Turnos(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new Persona[capacidad];
	}

	/**
	 * Entrega un turno consecutivo y encola a la persona al final.
	 * No reutiliza huecos del inicio (cola simple, no circular).
	 */
	public void agregarPersona(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		if (estaLlena()) {
			throw new IllegalStateException("La cola está llena; no se agregó a la persona.");
		}
		if (estaVacia()) {
			front = 0; // el primer elemento también es el frente
		}
		elementos[++rear] = new Persona(nombre.trim(), siguienteTurno++);
	}

	/** Saca y devuelve a la persona del frente, respetando el orden FIFO. */
	public Persona atenderSiguiente() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay a quién atender.");
		}
		Persona persona = elementos[front];
		elementos[front] = null;
		front++;

		// Al atender a la última persona, la cola vuelve al estado vacío.
		if (front > rear) {
			front = -1;
			rear = -1;
		}
		return persona;
	}

	/** Consulta quién está primero sin modificar la cola. */
	public Persona consultarPrimero() {
		if (estaVacia()) {
			throw new IllegalStateException("La cola está vacía; no hay un primero para consultar.");
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

	/** Muestra el orden de espera y los índices front y rear. */
	public void mostrarCola() {
		System.out.println("  front = " + front + " | rear = " + rear + " | tamaño = " + tamano());
		if (estaVacia()) {
			System.out.println("  (cola vacía)");
			return;
		}
		for (int i = 0; i <= rear; i++) {
			boolean enEspera = i >= front;
			String marcas = "";
			if (i == front) {
				marcas += " <- frente";
			}
			if (i == rear) {
				marcas += " <- último";
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
		System.out.println(" SISTEMA DE TURNOS | COLA FIFO");
		System.out.println("====================================");

		try {
			new Turnos(0);
			throw new AssertionError("Se esperaba rechazar la capacidad cero.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		Turnos turnos = new Turnos(3);
		System.out.println("\nCapacidad: 3");
		System.out.println("¿Cola vacía al iniciar? " + turnos.estaVacia() + " | Tamaño: " + turnos.tamano());

		// Cola vacía: atender y consultar deben fallar sin modificar el estado.
		try {
			turnos.atenderSiguiente();
			throw new AssertionError("Se esperaba error al atender una cola vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			turnos.consultarPrimero();
			throw new AssertionError("Se esperaba error al consultar una cola vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		// Nombre inválido no debe encolar.
		try {
			turnos.agregarPersona("   ");
			throw new AssertionError("Se esperaba rechazar un nombre vacío.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		comprobar(turnos.tamano() == 0, "La cola no cambió tras rechazar un nombre vacío.");

		for (String nombre : new String[]{"Ana", "Bruno", "Carla"}) {
			turnos.agregarPersona(nombre);
			System.out.println("Llegó " + nombre + " -> " + turnos.consultarPrimero()
					+ " está primero | tamaño = " + turnos.tamano());
		}
		System.out.println("¿Cola llena? " + turnos.estaLlena());
		System.out.println("Primero en la cola: " + turnos.consultarPrimero());

		// Cola llena: no debe agregar ni consumir un número de turno.
		try {
			turnos.agregarPersona("Diego");
			throw new AssertionError("Se esperaba error al agregar con la cola llena.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		comprobar(turnos.tamano() == 3 && turnos.consultarPrimero().getNumeroTurno() == 1,
				"La cola cambió tras rechazar un alta con la cola llena.");

		System.out.println("\nAtención en orden FIFO:");
		String[] esperados = {"Ana", "Bruno", "Carla"};
		for (int i = 0; i < esperados.length; i++) {
			Persona atendida = turnos.atenderSiguiente();
			System.out.println("  Atendida: " + atendida);
			comprobar(esperados[i].equals(atendida.getNombre()),
					"FIFO debe atender primero a quien llegó antes.");
		}
		comprobar(turnos.estaVacia() && turnos.tamano() == 0, "La cola debe quedar vacía.");

		// El siguiente turno debe continuar la numeración (Ana=1, Bruno=2, Carla=3).
		turnos.agregarPersona("Diego");
		comprobar(turnos.consultarPrimero().getNumeroTurno() == 4,
				"La numeración de turnos debe continuar tras atender.");
		System.out.println("\nAl llegar Diego recibe " + turnos.consultarPrimero()
				+ " (la numeración continúa).");

		System.out.println("\n[Correcto] FIFO, cola vacía, cola llena y numeración verificados.");
		System.out.println("Una pila (LIFO) atendería a Diego primero; la cola respeta el orden de llegada.");
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

	private static void ejecutarMenu(Turnos turnos, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de turnos ---");
			System.out.println("1. Agregar persona");
			System.out.println("2. Atender siguiente persona");
			System.out.println("3. Consultar quién está primero");
			System.out.println("4. Ver estado y cola");
			System.out.println("0. Salir");

			Integer opcion = leerEntero(scanner, "Seleccione una opción: ");
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					System.out.print("Nombre de la persona: ");
					if (!scanner.hasNextLine()) {
						continuar = false;
						break;
					}
					try {
						turnos.agregarPersona(scanner.nextLine());
						System.out.println("Persona agregada. Esperando: " + turnos.tamano() + ".");
					} catch (IllegalArgumentException | IllegalStateException exception) {
						System.out.println("No se pudo agregar: " + exception.getMessage());
					}
					break;
				case 2:
					try {
						System.out.println("Atendida: " + turnos.atenderSiguiente());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo atender: " + exception.getMessage());
					}
					break;
				case 3:
					try {
						System.out.println("Primero en la cola: " + turnos.consultarPrimero());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo consultar: " + exception.getMessage());
					}
					break;
				case 4:
					System.out.println("¿Vacía? " + (turnos.estaVacia() ? "Sí" : "No")
							+ " | ¿Llena? " + (turnos.estaLlena() ? "Sí" : "No"));
					turnos.mostrarCola();
					break;
				default:
					System.out.println("Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada.");
	}

	public static void main(String[] args) {
		demostrar();
		System.out.println("\nAhora puede probar otra oficina con capacidad 3.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Turnos(3), scanner);
		}
	}
}
