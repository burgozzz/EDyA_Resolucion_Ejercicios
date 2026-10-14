/*
 * PROMPT INICIAL UTILIZADO
 *
 * Desarrolla un codigo en java que permita realizar un historial de navegacion a traves de su
 * implementacion con una pila enlazada
 *
 * 1. Estructura: Pila enlazada genérica o de Strings.
 * 2. Por qué corresponde usar pila: Porque una pila representa correctamente el comportamiento de
 *    "volver atrás". El historial de navegación opera bajo un esquema LIFO: la última página
 *    visitada es la primera a la que se regresa cuando se presiona el botón "Atrás".
 * 3. Operaciones: visitar una página (apilar), volver a la página anterior (desapilar), consultar
 *    la página actual (tope), imprimir el historial.
 * 4. Casos especiales: Evitar errores si se intenta volver atrás cuando no hay historial previo.
 * 5. Comentarios: Incluye este prompt como comentario inicial.
 *
 * QUÉ ESTRUCTURA USO Y POR QUÉ
 * Implemento el historial sobre una PILA enlazada GENÉRICA (LIFO: el último que entra es el primero
 * que sale). Uso una pila porque el botón "Atrás" del navegador justamente deshace la última
 * acción: la página visitada más recientemente es la primera a la que se regresa. Ese es el
 * esquema LIFO. Una cola (FIFO) no serviría: al volver atrás quiero la ÚLTIMA visitada, no la
 * primera. Al apoyarme en una lista enlazada no necesito tamaño fijo ni desplazar elementos.
 *
 * CÓMO DEBE FUNCIONAR INTERNAMENTE (GENÉRICOS)
 * - Cada Nodo<E> guarda una página (dato de tipo E) y una referencia al siguiente nodo.
 * - El historial guarda UNA referencia: head, que representa la PÁGINA ACTUAL (el tope de la
 *   pila), más un contador size.
 * - visitar(página) = apilar: el nuevo nodo se pone adelante y pasa a ser la página actual.
 * - volverAtras() = desapilar: se descarta la página actual y la anterior pasa a ser la actual.
 * - La lógica NO depende del tipo de dato: apilar y desapilar solo actualizan la referencia head.
 *   Al historial le da igual si el nodo guarda un String (una URL) o un objeto Pagina; el
 *   comportamiento LIFO es siempre el mismo. El tipo T es solo la carga útil.
 * - La operación que COMPARA (contiene) usa Objects.equals, porque == compararía referencias.
 *
 * OPERACIONES: visitar(T) [push], volverAtras() [pop], paginaActual() [peek],
 * imprimirHistorial(), puedeVolverAtras(), estaVacia(), contiene(T), getSize().
 *
 * CASOS ESPECIALES: volver atrás SIN historial previo NO debe lanzar error. Por eso volverAtras()
 * es "segura": si la pila está vacía o solo hay una página, devuelve null y NO modifica nada.
 * Además, paginaActual() devuelve null si no hay páginas (en vez de lanzar excepción). También se
 * contempla visitar la misma página repetida y vaciar el historial del todo volviendo atrás.
 *
 * COMPLEJIDAD: visitar, volverAtras, paginaActual, puedeVolverAtras y estaVacia son O(1) (solo
 * tocan el head); contiene e imprimirHistorial son O(n) (recorren nodo por nodo).
 *
 * PRUEBAS: un main que demuestra el historial con páginas representadas como String (URLs) y con
 * una clase de objeto propio Pagina (con equals por URL y toString), usando aserciones e incluyendo
 * los casos especiales.
 */
package GUIA_PilasYClolas_ListasEnlazadas.Ejercicio_5;

/**
 * Historial de navegación implementado sobre una PILA enlazada GENÉRICA.
 *
 * <p>{@code head} apunta a la PÁGINA ACTUAL (el tope de la pila). Por eso visitar, volver atrás y
 * consultar la página actual son O(1): solo mueven o leen la cabeza, sin recorrer.</p>
 *
 * <p>Al usar genéricos ({@code <T>}) el mismo historial sirve para {@code HistorialNavegacion<String>}
 * (URLs) o {@code HistorialNavegacion<Pagina>} (objetos propios), con seguridad de tipos en tiempo
 * de compilación.</p>
 *
 * @param <T> tipo de dato que representa una página.
 */
public class HistorialNavegacion<T> {

	/**
	 * Nodo genérico de la pila: una página de tipo {@code E} y la referencia al siguiente.
	 *
	 * @param <E> tipo de dato del nodo.
	 */
	private static final class Nodo<E> {
		private final E dato;
		private Nodo<E> siguiente;

		private Nodo(E dato) {
			this.dato = dato;
		}

		private E getDato() {
			return dato;
		}

		private Nodo<E> getSiguiente() {
			return siguiente;
		}

		private void setSiguiente(Nodo<E> siguiente) {
			this.siguiente = siguiente;
		}
	}

	/** Tope de la pila = PÁGINA ACTUAL. Es null cuando el historial está vacío. */
	private Nodo<T> head;

	/** Cantidad de páginas en el historial. */
	private int size;

	public HistorialNavegacion() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * Visita una página nueva: la apila y pasa a ser la página actual (push).
	 *
	 * <p>Complejidad O(1): el nuevo nodo pasa a ser el head y apunta al head anterior.</p>
	 *
	 * @param pagina página a visitar (puede ser cualquier tipo T).
	 */
	public void visitar(T pagina) {
		Nodo<T> nuevo = new Nodo<>(pagina);
		nuevo.setSiguiente(head); // el nuevo tope apunta al tope anterior
		head = nuevo;             // y pasa a ser la página actual
		size++;
	}

	/**
	 * Vuelve a la página anterior: desapila la actual (pop).
	 *
	 * <p>Complejidad O(1): solo mueve el head al siguiente. El nodo removido queda inaccesible para
	 * que lo libere el Garbage Collector.</p>
	 *
	 * <p>CASO ESPECIAL: si no hay historial previo (pila vacía o con una sola página) NO lanza
	 * error; devuelve {@code null} y deja el historial sin cambios. La página actual no se pierde
	 * cuando es la única: seguiría siendo la actual.</p>
	 *
	 * @return la nueva página actual (la anterior), o {@code null} si no se puede volver atrás.
	 */
	public T volverAtras() {
		if (!puedeVolverAtras()) {
			return null; // no hay página anterior: no se modifica el historial
		}
		head = head.getSiguiente(); // la actual se descarta y la anterior pasa a ser la actual
		size--;
		return head.getDato();
	}

	/**
	 * Consulta la página actual sin desapilarla (peek).
	 *
	 * <p>Complejidad O(1): solo lee el head.</p>
	 *
	 * @return la página actual, o {@code null} si el historial está vacío.
	 */
	public T paginaActual() {
		return estaVacia() ? null : head.getDato();
	}

	/**
	 * Indica si se puede volver atrás: se necesita al menos una página PREVIA a la actual.
	 *
	 * <p>Complejidad O(1): solo verifica head y head.siguiente.</p>
	 *
	 * @return {@code true} si hay una página anterior a la que volver.
	 */
	public boolean puedeVolverAtras() {
		return head != null && head.getSiguiente() != null;
	}

	/**
	 * Indica si el historial no tiene páginas.
	 *
	 * <p>Complejidad O(1): solo verifica si head es null.</p>
	 *
	 * @return {@code true} si no hay páginas en el historial.
	 */
	public boolean estaVacia() {
		return head == null;
	}

	/**
	 * Busca si el historial contiene una página (contains), recorriendo desde la actual hacia el
	 * inicio.
	 *
	 * <p>Complejidad O(n): recorre nodo por nodo.</p>
	 *
	 * <p>Usa {@code Objects.equals} para comparar CONTENIDO; {@code ==} compararía referencias.</p>
	 *
	 * @param pagina página a buscar.
	 * @return {@code true} si aparece al menos una vez.
	 */
	public boolean contiene(T pagina) {
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			if (java.util.Objects.equals(pagina, auxiliar.getDato())) {
				return true;
			}
			auxiliar = auxiliar.getSiguiente();
		}
		return false;
	}

	/**
	 * Imprime el historial desde la página actual (tope) hacia la primera visitada.
	 *
	 * <p>Complejidad O(n): recorre todos los nodos.</p>
	 */
	public void imprimirHistorial() {
		if (estaVacia()) {
			System.out.println("(historial vacío)");
			return;
		}
		StringBuilder texto = new StringBuilder();
		Nodo<T> auxiliar = head;
		while (auxiliar != null) {
			if (auxiliar == head) {
				texto.append("[ACTUAL] "); // la primera de la impresión es la página actual
			}
			texto.append(String.valueOf(auxiliar.getDato()));
			texto.append(auxiliar.getSiguiente() != null ? " -> " : " -> null");
			if (auxiliar.getSiguiente() != null) {
				texto.append(" ");
			}
			auxiliar = auxiliar.getSiguiente();
		}
		texto.append("  (actual a la izquierda)");
		System.out.println(texto);
	}

	/** Devuelve la cantidad de páginas en el historial. */
	public int getSize() {
		return size;
	}

	// ------------------------------------------------------------------
	// Demostración automática (pruebas)
	// ------------------------------------------------------------------

	private static void comprobar(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new AssertionError(mensaje);
		}
	}

	private static void demostrar() {
		System.out.println("==================================================");
		System.out.println(" HISTORIAL DE NAVEGACIÓN CON PILA (String / Pagina)");
		System.out.println("==================================================");

		// ---- Historial vacío y volver atrás sin historial ----
		System.out.println("\n--- Historial vacío ---");
		HistorialNavegacion<String> vacio = new HistorialNavegacion<>();
		comprobar(vacio.estaVacia(), "Debe estar vacío al inicio.");
		comprobar(vacio.getSize() == 0, "El tamaño inicial debe ser 0.");
		comprobar(vacio.paginaActual() == null, "La página actual debe ser null si está vacío.");
		comprobar(!vacio.contiene("inicio"), "No debe contener ninguna página.");
		// CASO ESPECIAL: volver atrás sin historial no debe lanzar error.
		comprobar(vacio.volverAtras() == null, "Volver atrás sin historial debe devolver null (sin error).");
		comprobar(!vacio.puedeVolverAtras(), "No debería poder volver atrás.");
		System.out.print("  imprimirHistorial(): ");
		vacio.imprimirHistorial();
		System.out.println("  volverAtras()=null y paginaActual()=null  [Correcto]");

		// ---- Historial con URLs (String) ----
		System.out.println("\n--- HistorialNavegacion<String> (URLs) ---");
		HistorialNavegacion<String> navegador = new HistorialNavegacion<>();
		navegador.visitar("https://inicio.com");
		navegador.visitar("https://perfil.com");
		navegador.visitar("https://ajustes.com"); // última visitada = página actual
		System.out.print("  Historial: ");
		navegador.imprimirHistorial();
		comprobar(navegador.paginaActual().equals("https://ajustes.com"),
				"La página actual debe ser la última visitada.");
		comprobar(navegador.getSize() == 3, "Debe haber 3 páginas.");
		comprobar(navegador.contiene("https://perfil.com"), "Debe contener la página de perfil.");

		// ---- Volver atrás: LIFO ----
		System.out.println("\n--- Presionar 'Atrás' (LIFO) ---");
		comprobar(navegador.volverAtras().equals("https://perfil.com"),
				"Al volver atrás desde ajustes se regresa al perfil.");
		comprobar(navegador.paginaActual().equals("https://perfil.com"), "La actual ahora es el perfil.");
		System.out.print("  Tras un 'Atrás': ");
		navegador.imprimirHistorial();
		comprobar(navegador.volverAtras().equals("https://inicio.com"),
				"Al volver atrás otra vez se regresa al inicio.");
		comprobar(navegador.getSize() == 1, "Queda una sola página.");
		// CASO ESPECIAL: ya en la primera página, no hay historial previo.
		comprobar(!navegador.puedeVolverAtras(), "Ya no se puede volver más atrás.");
		comprobar(navegador.volverAtras() == null, "Volver atrás en la primera página da null (sin error).");
		comprobar(navegador.paginaActual().equals("https://inicio.com"),
				"La página actual no se pierde al no poder volver atrás.");
		System.out.println("  Se regresa en orden inverso al visitado y no falla al llegar al inicio  [Correcto]");

		// ---- Visitar la misma página repetida y volver a llenar ----
		System.out.println("\n--- Visitar repetido y reutilizar el historial ---");
		navegador.visitar("https://tienda.com");
		navegador.visitar("https://tienda.com"); // misma página visitada dos veces
		comprobar(navegador.getSize() == 3, "Las repeticiones cuentan como visitas (3 páginas).");
		comprobar(navegador.volverAtras().equals("https://tienda.com"),
				"Al volver atrás se regresa a la visita anterior de la misma página.");
		System.out.println("  Visitas repetidas se conservan en el historial  [Correcto]");

		// ---- Historial con objeto propio Pagina ----
		System.out.println("\n--- HistorialNavegacion<Pagina> (objeto propio) ---");
		Pagina inicio = new Pagina("https://inicio.com", "Inicio");
		Pagina perfil = new Pagina("https://perfil.com", "Perfil");
		Pagina ajustes = new Pagina("https://ajustes.com", "Ajustes");
		Pagina perfilEquivalente = new Pagina("https://perfil.com", "Perfil"); // otra instancia, misma URL

		HistorialNavegacion<Pagina> historial = new HistorialNavegacion<>();
		historial.visitar(inicio);
		historial.visitar(perfil);
		historial.visitar(ajustes);
		System.out.print("  Historial: ");
		historial.imprimirHistorial();
		comprobar(historial.paginaActual().equals(ajustes), "La página actual debe ser Ajustes.");
		// perfilEquivalente es OTRA instancia pero con la misma URL: equals debe dar true.
		comprobar(historial.contiene(perfilEquivalente),
				"Debe encontrar el Perfil por URL (equals), no por referencia.");
		comprobar(historial.volverAtras().equals(perfil), "Se regresa al Perfil (LIFO).");
		comprobar(historial.volverAtras().equals(inicio), "Luego se regresa al Inicio.");
		comprobar(historial.volverAtras() == null, "Sin historial previo, volver atrás da null (sin error).");
		System.out.println("  Busca por URL (equals) y respeta el orden LIFO  [Correcto]");

		System.out.println("\n[Correcto] El historial funciona igual con cualquier tipo de página y "
				+ "nunca falla al volver atrás sin historial previo.");
	}

	public static void main(String[] args) {
		demostrar();
	}
}

/**
 * Clase de objeto propio para probar el historial con un tipo que NO es de la librería.
 *
 * <p>Define {@code equals} por URL (dos páginas con la misma URL son iguales) y {@code toString}
 * para poder imprimirlas. Sin {@code equals}, la búsqueda por contenido ({@code contiene})
 * compararía referencias y no encontraría una instancia equivalente.</p>
 */
class Pagina {

	private final String url;
	private final String titulo;

	Pagina(String url, String titulo) {
		this.url = url;
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public String getTitulo() {
		return titulo;
	}

	/** Dos páginas son iguales si tienen la misma URL. */
	@Override
	public boolean equals(Object otro) {
		if (this == otro) {
			return true;
		}
		if (!(otro instanceof Pagina)) {
			return false;
		}
		Pagina pagina = (Pagina) otro;
		return java.util.Objects.equals(url, pagina.url);
	}

	/** Coherente con equals: el hashCode depende de la URL. */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(url);
	}

	/** Representación legible para imprimir el historial. */
	@Override
	public String toString() {
		return "Pagina(" + url + ", " + titulo + ")";
	}
}
