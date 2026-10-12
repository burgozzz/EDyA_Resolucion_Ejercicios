/*
 * PROMPT UTILIZADO:
 * Quiero hacer en Java un simulador sencillo del historial de navegación. Cada vez que
 * visite una página, guarda su URL en una pila implementada con un arreglo de tamaño
 * fijo. Usa un índice top que empiece en -1 para señalar que no hay páginas guardadas.
 *
 * El programa debe permitir visitar una página, consultar cuál es la página actual y
 * volver a la anterior usando la pila. Para volver, no elimines la página actual si no
 * existe una anterior: avisa al usuario cuando el historial esté vacío o solo tenga una
 * página. Si el arreglo se llena, informa que no se puede guardar otra página y conserva
 * el historial.
 *
 * Agrega un menú de consola para realizar esas operaciones y mostrar el historial.
 * Pruébalo visitando "Inicio", "Noticias" y "Contacto". Al volver desde "Contacto", la
 * página actual debe ser "Noticias". Explica con comentarios cómo se usa top y por qué
 * el historial funciona con LIFO, y aclara por qué no se comporta como una cola FIFO.
 *
 * Mantén el código simple y sin dependencias externas. Comprueba el historial vacío,
 * una sola página, varias visitas, varios retrocesos y el arreglo lleno. Incluye este
 * prompt como comentario al inicio del archivo. Corrige errores de compilación o pruebas
 * y vuelve a probar el programa. Al final, indica brevemente si fue necesario ajustar el
 * prompt y muestra una prueba de ejecución.
 */
package GUIA_PilasYColasArreglos.Ejercicio_4;

import java.util.Scanner;

public class Navegacion {
	private final String[] historial;
	private int top = -1;

	public Navegacion(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		historial = new String[capacidad];
	}

	/** Guarda una visita nueva en la cima del historial. */
	public void visitarPagina(String url) {
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalArgumentException("La dirección de la página no puede estar vacía.");
		}
		if (estaLleno()) {
			throw new IllegalStateException("El historial está lleno; no se agregó la página.");
		}
		historial[++top] = url.trim();
	}

	/** Devuelve la página actual sin modificar el historial. */
	public String paginaActual() {
		if (estaVacio()) {
			throw new IllegalStateException("El historial está vacío; no hay una página actual.");
		}
		return historial[top];
	}

	/**
	 * Retira la página actual y devuelve la página anterior.
	 * Si no existe una página anterior, conserva la actual y avisa con una excepción.
	 */
	public String volverAnterior() {
		if (estaVacio()) {
			throw new IllegalStateException("El historial está vacío; no hay páginas anteriores.");
		}
		if (top == 0) {
			throw new IllegalStateException("No hay una página anterior; se conserva la actual.");
		}

		historial[top--] = null;
		return historial[top];
	}

	public boolean estaVacio() {
		return top == -1;
	}

	public boolean estaLleno() {
		return top == historial.length - 1;
	}

	public int cantidadPaginas() {
		return top + 1;
	}

	/** Muestra las páginas desde la visita más antigua hasta la actual. */
	public void mostrarHistorial() {
		if (estaVacio()) {
			System.out.println("  (historial vacío)");
			return;
		}
		for (int i = 0; i <= top; i++) {
			String marcaActual = i == top ? " <- página actual" : "";
			System.out.println("  " + (i + 1) + ". " + historial[i] + marcaActual);
		}
	}

	private static void demostrarNavegacion() {
		System.out.println("====================================");
		System.out.println(" HISTORIAL DE NAVEGACIÓN | LIFO");
		System.out.println("====================================");
		Navegacion navegacion = new Navegacion(3);

		for (String pagina : new String[]{"Inicio", "Noticias", "Contacto"}) {
			navegacion.visitarPagina(pagina);
			System.out.println("Visitada: " + pagina);
		}
		System.out.println("\nHistorial:");
		navegacion.mostrarHistorial();
		System.out.println("Página actual: " + navegacion.paginaActual());
		System.out.println("¿Está lleno? " + navegacion.estaLleno());

		try {
			navegacion.visitarPagina("Galería");
			throw new AssertionError("Se esperaba rechazar una visita con historial lleno.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		if (navegacion.cantidadPaginas() != 3 || !"Contacto".equals(navegacion.paginaActual())) {
			throw new AssertionError("El historial cambió después de rechazar una visita.");
		}

		String paginaActual = navegacion.volverAnterior();
		System.out.println("\nAl volver desde Contacto, página actual: " + paginaActual);
		if (!"Noticias".equals(paginaActual)) {
			throw new AssertionError("El retroceso no devolvió la página anterior esperada.");
		}
		System.out.println("Al volver otra vez, página actual: " + navegacion.volverAnterior());
		try {
			navegacion.volverAnterior();
			throw new AssertionError("Se esperaba conservar la única página del historial.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		if (!"Inicio".equals(navegacion.paginaActual())) {
			throw new AssertionError("La única página se eliminó al intentar volver.");
		}

		Navegacion vacia = new Navegacion(1);
		try {
			vacia.paginaActual();
			throw new AssertionError("Se esperaba un error al consultar un historial vacío.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			vacia.volverAnterior();
			throw new AssertionError("Se esperaba un error al volver con historial vacío.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}

		System.out.println("\nLIFO retira primero la última página visitada. Una cola FIFO retiraría"
				+ " primero la página más antigua, que no es el comportamiento de un historial.");
		System.out.println("[Correcto] Navegación, capacidad y retrocesos verificados.");
	}

	private static Integer leerOpcion(Scanner scanner) {
		while (scanner.hasNextLine()) {
			System.out.print("Seleccione una opción: ");
			String entrada = scanner.nextLine().trim();
			try {
				return Integer.valueOf(entrada);
			} catch (NumberFormatException exception) {
				System.out.println("Opción no válida. Ingrese el número indicado en el menú.");
			}
		}
		return null;
	}

	private static void ejecutarMenu(Navegacion navegacion, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de navegación ---");
			System.out.println("1. Visitar una página");
			System.out.println("2. Volver a la página anterior");
			System.out.println("3. Consultar la página actual");
			System.out.println("4. Mostrar historial y estado");
			System.out.println("0. Salir");

			Integer opcion = leerOpcion(scanner);
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					System.out.print("Dirección o nombre de la página: ");
					if (!scanner.hasNextLine()) {
						continuar = false;
						break;
					}
					try {
						String pagina = scanner.nextLine();
						navegacion.visitarPagina(pagina);
						System.out.println("Página visitada. Página actual: " + navegacion.paginaActual());
					} catch (IllegalArgumentException | IllegalStateException exception) {
						System.out.println("No se pudo visitar: " + exception.getMessage());
					}
					break;
				case 2:
					try {
						System.out.println("Volviste a: " + navegacion.volverAnterior());
					} catch (IllegalStateException exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case 3:
					try {
						System.out.println("Página actual: " + navegacion.paginaActual());
					} catch (IllegalStateException exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case 4:
					System.out.println("Páginas guardadas: " + navegacion.cantidadPaginas());
					System.out.println("¿Historial vacío? " + (navegacion.estaVacio() ? "Sí" : "No"));
					System.out.println("¿Historial lleno? " + (navegacion.estaLleno() ? "Sí" : "No"));
					navegacion.mostrarHistorial();
					break;
				default:
					System.out.println("Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada.");
	}

	public static void main(String[] args) {
		demostrarNavegacion();
		System.out.println("\nAhora puedes probar otro historial con capacidad 3.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Navegacion(3), scanner);
		}
	}
}
