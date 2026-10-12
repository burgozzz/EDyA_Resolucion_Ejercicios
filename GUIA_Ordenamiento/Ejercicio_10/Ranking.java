/*
 * PROMPT UTILIZADO:
 * Implementa en Java un programa que ordene un ranking de jugadores de mayor a menor
 * puntaje. Representa cada jugador con un nombre y un puntaje entero. Usa como prueba:
 * Ana, 1200 puntos; Pedro, 900 puntos; Lucia, 1500 puntos.
 *
 * El algoritmo elegido es el Insertion Sort ya que permite ordenar de forma sencilla y conservar 
 * el orden original entre jugadores con el mismo puntaje.
 * Si hay puntajes iguales, conserva el orden original de esos jugadores. Muestra el
 * ranking original, el algoritmo elegido y el ranking final. Incluye comentarios breves
 * sobre la elección del método y la comparación de puntajes. Mantén la solución sencilla
 * y sin dependencias externas.
 *
 * Verifica que el ejemplo quede: Lucia, 1500; Ana, 1200; Pedro, 900. Comprueba también
 * un ranking vacío, uno unitario, uno ya ordenado, otro en orden inverso y uno con
 * puntajes empatados. Corrige errores de compilación o pruebas y vuelve a verificarla.
 */
package GUIA_Ordenamiento.Ejercicio_10;

import java.util.Arrays;

/** Ordena rankings de jugadores por puntaje descendente. */
public class Ranking {
	private static final class Jugador {
		private final String nombre;
		private final int puntaje;

		private Jugador(String nombre, int puntaje) {
			this.nombre = nombre;
			this.puntaje = puntaje;
		}
	}

	/**
	 * Ordena de mayor a menor puntaje con Insertion Sort estable.
	 * Solo desplaza jugadores con puntaje estrictamente menor, por lo que los empates
	 * conservan su orden original.
	 */
	private static void ordenarRanking(Jugador[] jugadores) {
		for (int i = 1; i < jugadores.length; i++) {
			Jugador jugadorActual = jugadores[i];
			int posicion = i - 1;

			// Se desplazan hacia la derecha los puntajes menores que el actual.
			while (posicion >= 0 && jugadores[posicion].puntaje < jugadorActual.puntaje) {
				jugadores[posicion + 1] = jugadores[posicion];
				posicion--;
			}
			jugadores[posicion + 1] = jugadorActual;
		}
	}

	private static Jugador[] copiarRanking(Jugador[] jugadores) {
		return Arrays.copyOf(jugadores, jugadores.length);
	}

	private static void mostrarRanking(String titulo, Jugador[] jugadores) {
		System.out.println(titulo);
		if (jugadores.length == 0) {
			System.out.println("  (sin jugadores)");
			return;
		}

		for (int i = 0; i < jugadores.length; i++) {
			System.out.println("  " + (i + 1) + ". " + jugadores[i].nombre
					+ " - " + jugadores[i].puntaje + " puntos");
		}
	}

	private static boolean rankingsIguales(Jugador[] primero, Jugador[] segundo) {
		if (primero.length != segundo.length) {
			return false;
		}
		for (int i = 0; i < primero.length; i++) {
			if (!primero[i].nombre.equals(segundo[i].nombre)
					|| primero[i].puntaje != segundo[i].puntaje) {
				return false;
			}
		}
		return true;
	}

	private static void verificarCaso(String descripcion, Jugador[] entrada, Jugador[] esperado) {
		Jugador[] resultado = copiarRanking(entrada);
		ordenarRanking(resultado);
		if (!rankingsIguales(resultado, esperado)) {
			throw new AssertionError("Falló la verificación: " + descripcion);
		}
		System.out.println("[Correcto] " + descripcion + ".");
	}

	public static void main(String[] args) {
		Jugador[] rankingOriginal = {
				new Jugador("Ana", 1200),
				new Jugador("Pedro", 900),
				new Jugador("Lucia", 1500)
		};
		Jugador[] rankingOrdenado = copiarRanking(rankingOriginal);

		System.out.println("============================================");
		System.out.println(" RANKING DE JUGADORES");
		System.out.println("============================================");
		mostrarRanking("\nRanking original:", rankingOriginal);
		System.out.println("\nAlgoritmo elegido: Insertion Sort");
		System.out.println("Motivo: permite ordenar de forma sencilla y conservar el orden"
				+ " original entre jugadores con el mismo puntaje.");

		ordenarRanking(rankingOrdenado);
		mostrarRanking("\nRanking final (mayor a menor puntaje):", rankingOrdenado);

		Jugador[] esperado = {
				new Jugador("Lucia", 1500),
				new Jugador("Ana", 1200),
				new Jugador("Pedro", 900)
		};
		if (!rankingsIguales(rankingOrdenado, esperado)) {
			throw new AssertionError("El ranking de prueba no coincide con el resultado esperado.");
		}
		System.out.println("\n[Correcto] El ranking de prueba coincide con el esperado.");

		System.out.println("\n--- Verificaciones adicionales ---");
		verificarCaso("ranking vacío", new Jugador[]{}, new Jugador[]{});
		verificarCaso("un solo jugador",
				new Jugador[]{new Jugador("Ana", 1200)},
				new Jugador[]{new Jugador("Ana", 1200)});
		verificarCaso("ranking ya ordenado",
				new Jugador[]{new Jugador("Lucia", 1500), new Jugador("Ana", 1200)},
				new Jugador[]{new Jugador("Lucia", 1500), new Jugador("Ana", 1200)});
		verificarCaso("ranking en orden inverso",
				new Jugador[]{new Jugador("Pedro", 900), new Jugador("Lucia", 1500)},
				new Jugador[]{new Jugador("Lucia", 1500), new Jugador("Pedro", 900)});
		verificarCaso("puntajes empatados conservan su orden",
				new Jugador[]{new Jugador("Ana", 1200), new Jugador("Pedro", 1200),
						new Jugador("Lucia", 1500)},
				new Jugador[]{new Jugador("Lucia", 1500), new Jugador("Ana", 1200),
						new Jugador("Pedro", 1200)});
	}
}
