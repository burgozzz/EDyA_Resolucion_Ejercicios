package GUIA_Listas_Enlazadas_SImples.Ejercicio_10;

import java.util.Objects;

/**
 * Clase de apoyo para demostrar la lista genérica con objetos propios.
 *
 * <p>Define {@code equals} por legajo (dos alumnos con el mismo legajo se consideran iguales) y
 * {@code toString} para poder imprimirlos. Sin estos métodos, la búsqueda/eliminación por valor en
 * la lista no funcionarían como se espera.</p>
 */
public class Alumno {

	private final int legajo;
	private final String nombre;

	public Alumno(int legajo, String nombre) {
		this.legajo = legajo;
		this.nombre = nombre;
	}

	public int getLegajo() {
		return legajo;
	}

	public String getNombre() {
		return nombre;
	}

	/** Dos alumnos son iguales si tienen el mismo legajo. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Alumno)) {
			return false;
		}
		Alumno alumno = (Alumno) otro;
		return legajo == alumno.legajo;
	}

	/** Coherente con equals: el hashCode depende del legajo. */
	@Override
	public int hashCode() {
		return Objects.hash(legajo);
	}

	/** Representación legible para imprimir la lista. */
	@Override
	public String toString() {
		return "Alumno(" + legajo + ", " + nombre + ")";
	}
}
