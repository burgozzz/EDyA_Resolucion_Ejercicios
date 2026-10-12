/*
 * PROMPT UTILIZADO:
 * Implementa en Java una pila de enteros usando un arreglo interno de tamaño fijo.
 * La clase debe permitir push, pop, peek, isEmpty, isFull y size.
 *
 * Recibe la capacidad al crear la pila y rechaza capacidades menores o iguales a cero.
 * Usa únicamente un arreglo de enteros para almacenar los elementos. Mantén un índice
 * top que represente la posición del elemento superior e inicialízalo en -1 para indicar
 * que la pila está vacía. Al agregar, actualiza top y guarda el elemento; al quitar,
 * devuelve el elemento superior y reduce top. Si la pila está llena o vacía, las
 * operaciones que no se pueden realizar deben lanzar un error. size() debe corresponder
 * con la cantidad de elementos según top.
 *
 * Incluye comentarios breves sobre top, LIFO y los casos de pila llena o vacía. Mantén
 * la solución sencilla y sin dependencias externas.
 *
 * Agrega una demostración ejecutable con capacidad 3: inserta 10, 20 y 30; muestra el
 * tamaño, si está llena, la cima y el orden de extracción 30, 20, 10. Demuestra también
 * el intento de insertar cuando está llena y consultar o quitar cuando está vacía,
 * capturando las excepciones y mostrando mensajes claros. Después de la demostración,
 * ofrece un menú para que el usuario pueda agregar, quitar, consultar la cima y revisar
 * el tamaño y el estado de una pila nueva. Valida entradas no numéricas y permite salir.
 * Verifica el comportamiento, corrige errores de compilación o pruebas y vuelve a probar.
 */
package GUIA_PilasYColasArreglos.Ejercicio_1;

import java.util.Scanner;

public class PilaEnteros {
	private final int[] elementos;
	private int top;

	public PilaEnteros(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
		}
		elementos = new int[capacidad];
		top = -1;
	}

	/** Agrega un entero en la cima; falla si no queda espacio en el arreglo. */
	public void push(int valor) {
		if (isFull()) {
			throw new IllegalStateException("No se puede agregar: la pila está llena.");
		}
		elementos[++top] = valor;
	}

	/** Quita y devuelve la cima; falla si la pila está vacía. */
	public int pop() {
		if (isEmpty()) {
			throw new IllegalStateException("No se puede quitar: la pila está vacía.");
		}
		return elementos[top--];
	}

	/** Devuelve la cima sin quitarla; falla si la pila está vacía. */
	public int peek() {
		if (isEmpty()) {
			throw new IllegalStateException("No hay cima: la pila está vacía.");
		}
		return elementos[top];
	}

	/** Indica si no hay elementos; top vale -1 cuando está vacía. */
	public boolean isEmpty() {
		return top == -1;
	}

	/** Indica si top llegó al último índice disponible del arreglo. */
	public boolean isFull() {
		return top == elementos.length - 1;
	}

	/** Devuelve la cantidad de elementos almacenados, que es top + 1. */
	public int size() {
		return top + 1;
	}

	private static void demostrarOperaciones() {
		System.out.println("====================================");
		System.out.println(" DEMOSTRACIÓN: PILA DE ENTEROS (LIFO)");
		System.out.println("====================================");

		try {
			new PilaEnteros(0);
			throw new AssertionError("Se esperaba rechazar una capacidad igual a cero.");
		} catch (IllegalArgumentException exception) {
			System.out.println("[Correcto] Se rechazó la capacidad cero: " + exception.getMessage());
		}

		PilaEnteros pila = new PilaEnteros(3);
		System.out.println("\nCapacidad de la pila: 3");
		System.out.println("¿Está vacía al iniciar? " + pila.isEmpty());

		pila.push(10);
		pila.push(20);
		pila.push(30);
		System.out.println("Elementos agregados: 10, 20, 30");
		System.out.println("Tamaño actual: " + pila.size());
		System.out.println("¿Está llena? " + pila.isFull());
		System.out.println("Elemento en la cima (peek): " + pila.peek());

		try {
			pila.push(40);
			throw new AssertionError("Se esperaba un error al insertar en una pila llena.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] Inserción rechazada: " + exception.getMessage());
		}
		if (pila.size() != 3 || pila.peek() != 30) {
			throw new AssertionError("La pila cambió después de rechazar la inserción.");
		}

		System.out.println("\nExtracciones en orden LIFO:");
		int[] ordenEsperado = {30, 20, 10};
		for (int valorEsperado : ordenEsperado) {
			int valorExtraido = pila.pop();
			System.out.println("  pop(): " + valorExtraido);
			if (valorExtraido != valorEsperado) {
				throw new AssertionError("Orden de extracción incorrecto.");
			}
		}
		System.out.println("Tamaño después de extraer: " + pila.size());
		System.out.println("¿Está vacía? " + pila.isEmpty());

		try {
			pila.peek();
			throw new AssertionError("Se esperaba un error al consultar una pila vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] Consulta rechazada: " + exception.getMessage());
		}
		try {
			pila.pop();
			throw new AssertionError("Se esperaba un error al quitar de una pila vacía.");
		} catch (IllegalStateException exception) {
			System.out.println("[Correcto] Extracción rechazada: " + exception.getMessage());
		}

		System.out.println("\n[Correcto] La pila respetó el comportamiento LIFO.");
	}

	private static Integer leerEntero(Scanner scanner, String mensaje) {
		while (true) {
			System.out.print(mensaje);
			if (!scanner.hasNext()) {
				return null;
			}
			if (scanner.hasNextInt()) {
				return scanner.nextInt();
			}
			System.out.println("Entrada no válida. Ingrese un número entero.");
			scanner.next();
		}
	}

	private static void ejecutarMenu(PilaEnteros pila, Scanner scanner) {
		boolean continuar = true;
		while (continuar) {
			System.out.println("\n--- Menú de la pila ---");
			System.out.println("1. Agregar un entero (push)");
			System.out.println("2. Quitar el elemento superior (pop)");
			System.out.println("3. Consultar la cima (peek)");
			System.out.println("4. Ver tamaño y estado");
			System.out.println("0. Salir");

			Integer opcion = leerEntero(scanner, "Seleccione una opción: ");
			if (opcion == null || opcion == 0) {
				continuar = false;
				continue;
			}

			switch (opcion) {
				case 1:
					Integer valor = leerEntero(scanner, "Ingrese el entero que desea agregar: ");
					if (valor == null) {
						continuar = false;
						break;
					}
					try {
						pila.push(valor);
						System.out.println("Agregado: " + valor + ". Tamaño actual: " + pila.size() + ".");
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo agregar: la pila está llena.");
					}
					break;
				case 2:
					try {
						System.out.println("Elemento retirado: " + pila.pop());
					} catch (IllegalStateException exception) {
						System.out.println("No se pudo quitar: la pila está vacía.");
					}
					break;
				case 3:
					try {
						System.out.println("Elemento en la cima: " + pila.peek());
					} catch (IllegalStateException exception) {
						System.out.println("No hay elemento para consultar: la pila está vacía.");
					}
					break;
				case 4:
					System.out.println("Elementos almacenados: " + pila.size());
					System.out.println("¿Está vacía? " + (pila.isEmpty() ? "Sí" : "No"));
					System.out.println("¿Está llena? " + (pila.isFull() ? "Sí" : "No"));
					if (!pila.isEmpty()) {
						System.out.println("Cima actual: " + pila.peek());
					}
					break;
				default:
					System.out.println("Opción no válida. Seleccione una opción del menú.");
			}
		}
		System.out.println("\nSesión finalizada. ¡Hasta luego!");
	}

	public static void main(String[] args) {
		demostrarOperaciones();
		System.out.println("\nAhora puede probar las operaciones con una pila nueva de capacidad 3.");
		try (Scanner scanner = new Scanner(System.in)) {
			ejecutarMenu(new PilaEnteros(3), scanner);
		}
	}
}
