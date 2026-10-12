/*
 * PROMPT UTILIZADO:
 * Quiero implementar en Java una función recursiva que sume los dígitos de un número entero positivo.
 *
 * Este problema puede resolverse recursivamente porque cada dígito puede procesarse por separado.
 * Se puede obtener el último dígito con módulo y reducir el número usando división entera.
 * En cada llamada, el problema se vuelve más pequeño hasta llegar a un caso base.
 *
 * El caso base es cuando el número es menor que 10, porque un número de un solo dígito debe devolver ese mismo valor.
 * En ese caso la función debe devolver el número.
 *
 * El caso recursivo es:
 * sumarDigitos(n) = (n % 10) + sumarDigitos(n / 10)
 *
 * Esto ocurre porque se toma el último dígito con n % 10 y luego se reduce el problema con n / 10 hasta llegar a un número de un solo dígito.
 * Al volver de la recursión, se suman los resultados parciales.
 *
 * La función debe devolver la suma total de los dígitos del número usando solo recursividad.
 *
 * Quiero que el código tenga comentarios explicando:
 * - el caso base,
 * - el caso recursivo,
 * - la pila de llamadas.
 *
 * También quiero probarlo con estos casos:
 * - 123 -> 6
 * - 7 -> 7
 * - 4050 -> 9
 * - 999 -> 27
 *
 * Además, quiero que el programa se ejecute por consola, muestre mensajes claros al usuario y valide que el número ingresado sea positivo.
 */

package GUIA_Recursividad.Ejercicio_7;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que suma los dígitos de un número entero positivo usando recursividad.
 *
 * La idea es separar el último dígito con módulo y reducir el número con división entera.
 * En cada llamada, se suma el dígito actual con la suma de la parte restante.
 */
public class Sumar_Digitos {

    /**
     * Suma los dígitos del número recibido usando recursividad.
     *
     * Caso base:
     *      Si n < 10, el número tiene un solo dígito y se devuelve como resultado.
     *
     * Caso recursivo:
     *      Se toma el último dígito con n % 10 y se suma con la llamada recursiva de n / 10:
     *      sumarDigitos(n) = (n % 10) + sumarDigitos(n / 10)
     *
     * Pila de llamadas:
     *      Para sumarDigitos(123):
     *      sumarDigitos(123)
     *      -> sumarDigitos(12)
     *      -> sumarDigitos(1)
     *      -> caso base
     *      Luego cada llamada vuelve y suma el último dígito acumulado.
     *
     * @param n número entero positivo
     * @return suma de todos sus dígitos
     */
    public static int sumarDigitos(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("El número debe ser un entero positivo.");
        }

        // Caso base: si el número tiene un solo dígito, se devuelve directamente.
        if (n < 10) {
            return n;
        }

        // Caso recursivo: se toma el último dígito y se reduce el problema.
        return (n % 10) + sumarDigitos(n / 10);
    }

    /**
     * Programa principal por consola.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Suma de dígitos recursiva ===");
        int numero = leerEnteroPositivo(scanner, "Ingrese un número entero positivo: ");

        try {
            int resultado = sumarDigitos(numero);
            System.out.println("La suma de los dígitos de " + numero + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nPruebas:");
        System.out.println("123 -> " + sumarDigitos(123));
        System.out.println("7 -> " + sumarDigitos(7));
        System.out.println("4050 -> " + sumarDigitos(4050));
        System.out.println("999 -> " + sumarDigitos(999));

        scanner.close();
    }

    /**
    * Lee un número entero positivo desde consola.
     *
     * @param scanner entrada por consola
     * @param mensaje texto a mostrar al usuario
     * @return número válido ingresado por el usuario
     */
    private static int leerEnteroPositivo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                if (valor <= 0) {
                    System.out.println("Error: el valor debe ser mayor que 0.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero.");
                scanner.next();
            }
        }
    }
}

/*
Cambios PROMPT
Cambia los valores de entrada, para que el mismo acepte solo enteros positivos

Correcion: El programa aceptaba numero negativos y uno de los requerimientos era
sumar los digitos de un numero entero positivo
 */