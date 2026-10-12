/*
 * PROMPT UTILIZADO:
 * Quiero hacer en Java un programa para simular una torre de platos. Cada plato puede
 * tener un nombre y quiero guardarlos en una pila hecha con un arreglo de tamaño fijo.
 *
 * Necesito poder agregar un plato, ver cuál está arriba y retirar el plato superior.
 * También quiero saber si la pila está vacía o llena y cuántos platos tiene. Explícame
 * con comentarios cómo funciona el índice top, por qué empieza en -1 y qué pasa cuando
 * intento agregar a una pila llena o retirar o consultar cuando está vacía.
 *
 * Pruébalo con una capacidad pequeña y algunos platos, por ejemplo "Plato A", "Plato B"
 * y "Plato C". Muestra cómo se agregan y retiran, y verifica que salgan en orden LIFO:
 * primero el último que se agregó. Explica brevemente por qué una torre de platos funciona
 * como una pila y no como una cola.
 *
 * Mantén el código simple, sin dependencias externas, e incluye una prueba ejecutable.
 * Si hay errores de compilación o el resultado no es correcto, corrígelos y vuelve a
 * probarlo. Al final, comenta brevemente si fue necesario ajustar este prompt.
 */
package GUIA_PilasYColasArreglos.Ejercicio_2;

import java.util.Scanner;

public class Torre_Platos {
	private final String[] platos;
	private int top;

	public Torre_Platos(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		platos = new String[capacidad];
		top = -1;
	}

	/** Agrega un plato en la cima; top vale -1 cuando la pila está vacía. */
	public void agregarPlato(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del plato no puede estar vacío.");
		}
		if (estaLlena()) {
			throw new IllegalStateException("La torre está llena; no se pueden agregar más platos.");
		}
		platos[++top] = nombre.trim();
	}

	/** Retira y devuelve el plato superior siguiendo el orden LIFO. */
	public String retirarPlato() {
		if (estaVacia()) {
			throw new IllegalStateException("La torre está vacía; no hay platos para retirar.");
		}
		String platoRetirado = platos[top];
		platos[top--] = null;
		return platoRetirado;
	}

	/** Consulta el plato superior sin retirarlo. */
	public String consultarSuperior() {
		if (estaVacia()) {
			throw new IllegalStateException("La torre está vacía; no hay un plato superior.");
		}
		return platos[top];
	}

	public boolean estaVacia() {
		return top == -1;
	}

	public boolean estaLlena() {
		return top == platos.length - 1;
	}

	public int cantidadPlatos() {
		return top + 1;
	}

	private void mostrarContenido() {
		if (estaVacia()) {
			System.out.println("  (torre vacía)");
			return;
		}
		System.out.println("  Platos desde la base hasta la cima:");
		for (int i = 0; i <= top; i++) {
			String marcaCima = i == top ? " <- cima" : "";
			System.out.println("  " + (i + 1) + ". " + platos[i] + marcaCima);
		}
	}

	private static void demostrarLifo() {
		System.out.println("====================================");
		System.out.println(" TORRE DE PLATOS | PILA LIFO");
		System.out.println("====================================");
		Torre_Platos torre = new Torre_Platos(3);
		System.out.println("Capacidad: 3 platos");
		System.out.println("¿Está vacía al iniciar? " + torre.estaVacia());

		for (String plato : new String[]{"Plato A", "Plato B", "Plato C"}) {
			torre.agregarPlato(plato);
			System.out.println("Agregado: " + plato);
		}
		torre.mostrarContenido();
		System.out.println("Plato superior: " + torre.consultarSuperior());
		System.out.println("Cantidad: " + torre.cantidadPlatos());
		System.out.println("¿Está llena? " + torre.estaLlena());

		try {
			torre.agregarPlato("Plato D");
			throw new AssertionError("Se esperaba rechazar la inserción en una torre llena.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		if (torre.cantidadPlatos() != 3 || !"Plato C".equals(torre.consultarSuperior())) {
			throw new AssertionError("La torre cambió tras rechazar el plato adicional.");
		}

		System.out.println("\nRetiro en orden LIFO:");
		String[] ordenEsperado = {"Plato C", "Plato B", "Plato A"};
		for (String esperado : ordenEsperado) {
			String retirado = torre.retirarPlato();
			System.out.println("  Retirado: " + retirado);
			if (!esperado.equals(retirado)) {
				throw new AssertionError("Los platos no se retiraron en orden LIFO.");
			}
		}
		System.out.println("Cantidad después de retirar: " + torre.cantidadPlatos());
		System.out.println("¿Está vacía? " + torre.estaVacia());
		try {
			torre.consultarSuperior();
			throw new AssertionError("Se esperaba un error al consultar una torre vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		try {
			torre.retirarPlato();
			throw new AssertionError("Se esperaba un error al retirar de una torre vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] " + exception.getMessage());
		}
		System.out.println("\nUna torre usa LIFO: el último plato colocado queda arriba y se retira primero."
				+ " Una cola usa FIFO y retiraría primero el plato más antiguo.");
		System.out.println("[Correcto] La demostración respetó el orden LIFO.");
	}

	private static Integer leerOpcion(Scanner scanner) {
		while (scanner.hasNextLine()) {
			System.out.print("Seleccione una opción: ");
			String entrada = scanner.nextLine().trim();
			try {
				return Integer.valueOf(entrada);
			} catch (NumberFormatException exception) {
				System.out.println("Opción no válida. Ingrese el número de una opción del menú.");
			}
		}
		return null;
	}

	private static void ejecutarMenu(Torre_Platos torre, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la torre ---");
			System.out.println("1. Agregar un plato");
			System.out.println("2. Retirar el plato superior");
			System.out.println("3. Consultar el plato superior");
			System.out.println("4. Ver contenido y estado");
			System.out.println("0. Salir");

			Integer opcion = leerOpcion(scanner);
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					System.out.print("Nombre del plato: ");
					if (!scanner.hasNextLine()) {
						continuar = false;
						break;
					}
					try {
						String nombre = scanner.nextLine();
						torre.agregarPlato(nombre);
						System.out.println("Plato agregado. Hay " + torre.cantidadPlatos() + " plato(s).");
					} catch (IllegalArgumentException | IllegalStateException exception) {
						System.out.println("No se pudo agregar: " + exception.getMessage());
					}
					break;
				case 2:
					try {
						System.out.println("Plato retirado: " + torre.retirarPlato());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo retirar: " + exception.getMessage());
					}
					break;
				case 3:
					try {
						System.out.println("Plato en la cima: " + torre.consultarSuperior());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo consultar: " + exception.getMessage());
					}
					break;
				case 4:
					System.out.println("Cantidad: " + torre.cantidadPlatos());
					System.out.println("¿Está vacía? " + (torre.estaVacia() ? "Sí" : "No"));
					System.out.println("¿Está llena? " + (torre.estaLlena() ? "Sí" : "No"));
					torre.mostrarContenido();
					break;
				default:
					System.out.println("Opción no válida. Elija una opción del menú.");
			}
		}
		System.out.println("\nSimulación finalizada.");
	}

	public static void main(String[] args) {
		demostrarLifo();
		System.out.println("\nAhora puede probar otra torre de platos con capacidad 3.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new Torre_Platos(3), scanner);
		}
	}
}
