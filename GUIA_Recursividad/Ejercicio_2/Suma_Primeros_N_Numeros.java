/*
 * PROMPT UTILIZADO:
 *Quiero implementar en Java una función recursiva que resuelva la suma de los primeros N números.
Este problema puede resolverse recursivamente porque la suma de los primeros n números puede pensarse 
como una versión más pequeña del mismo problema: n + suma(n - 1). En cada llamada, se reduce el valor de n 
en 1 hasta llegar a un caso más simple.

El caso base es cuando n == 0, porque la suma de los primeros 0 números es 0. En ese caso, la función debe devolver 0 y detener la recursión.

El caso recursivo es suma(n) = n + suma(n - 1), porque en cada llamada se suma el valor actual de n y luego se 
resuelve el problema con un valor menor: n - 1. De esta forma, el problema se reduce progresivamente hasta llegar al caso base.

La función debe recibir un número entero no negativo y devolver la suma desde n hasta 1. Si n es negativo, 
debe informarse que el dato es inválido y no debe continuarse con el cálculo.

La solución debe estar separada en dos partes: una función recursiva que realiza el cálculo y una sección de interacción por consola 
para pedir el valor al usuario, validar la entrada y mostrar el resultado.

 */

package GUIA_Recursividad.Ejercicio_2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que calcula la suma de los primeros N números usando recursividad.
 * Ejemplo: suma(5) = 5 + 4 + 3 + 2 + 1 = 15.
 *
 * La recursión se aplica reduciendo el problema a una versión más pequeña:
 * suma(n) = n + suma(n - 1). Cuando n == 0, el problema se resuelve directamente
 * porque la suma de cero elementos es 0.
 */
public class Suma_Primeros_N_Numeros {

    /**
     * Calcula la suma de los números desde n hasta 1 de forma recursiva.
     * Caso base: cuando n == 0, devuelve 0.
     * Caso recursivo: suma(n) = n + suma(n - 1).
     *
     * @param n número entero no negativo
     * @return suma de 1 + 2 + ... + n
     * @throws IllegalArgumentException si n es negativo
     */
    public static int sumaPrimerosN(int n) {
        // Caso base: si n es 0, no hay elementos que sumar, por lo que el resultado es 0.
        if (n == 0) {
            return 0;
        }

        // Caso recursivo: el problema se reduce a una versión más pequeña, restando 1 cada vez.
        // La llamada recursiva resuelve la suma de los números anteriores y luego se suma n.
        if (n < 0) {
            throw new IllegalArgumentException("El número no puede ser negativo.");
        }

        return n + sumaPrimerosN(n - 1);
    }

    /**
     * Punto de entrada del programa. Separa la interacción con el usuario de la
     * lógica de cálculo.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = leerEnteroNoNegativo(scanner);

        try {
            int resultado = sumaPrimerosN(n);
            System.out.println("La suma de los primeros " + n + " números es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Solicita un número entero no negativo y repite si el usuario ingresa un
     * valor inválido.
     *
     * @param scanner objeto para leer desde consola
     * @return número entero mayor o igual a 0
     */
    private static int leerEnteroNoNegativo(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese un número entero no negativo: ");
            try {
                int valor = scanner.nextInt();
                if (valor < 0) {
                    System.out.println("Dato inválido: el número debe ser mayor o igual a 0.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Dato inválido: debe ingresar un número entero.");
                scanner.next();
            }
        }
    }
}
