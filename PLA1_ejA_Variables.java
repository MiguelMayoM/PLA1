package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  Escribir un programa que pida dos números, los almacene en dos variables y nos
  muestre la suma y la multiplicación de los mismos.
*/

import java.util.Scanner;
/*Si queremos usar decimales, hay que tener en cuenta que las sumas con float o
  double no dan resultados exactos, debido a que la representación interna de
  dichos números en la memoria se realiza mediante el desarrollo en sumas de
  base 2, buscando la mayor aproximación al número decimal (base 10) real. Por
  ejemplo, al sumar:
  0.1 + 0.1 = 0.010000001
Para evitarlo, uso la siguiente clase*/
import java.math.BigDecimal;

public class PLA1_ejA_Variables {
  /*Pongo el scanner aquí y no dentro de NumeroValido para no inicializar cada
    vez uno distinto cuando llame a este método. Otra opción era ponerlo en el
    método Main y pasarlo como argumento*/
  static Scanner scnEntrada = new Scanner(System.in);
  
  /*static para que se pueda llamar sin instanciar ningún objeto*/
  static BigDecimal NumeroValido(String ordinal) {
  //static float NumeroValido(Scanner ScnEntrada, String ordinal) {
    /*La inicializamos, si no no mantiene el valor de dentro del while*/
    BigDecimal numero = null ;
    //float numero = 0;
    boolean numValido = false;
    /*Creamos un bucle hasta que se introduzca un número correcto*/
    while (!numValido) {
      System.out.print("Introduzca " + ordinal + "º número: ");
      /*Leo la línea entera para evitar que sea válida una entrada del tipo
        "7 a" si utilizara simplemente el método .next(). Quito los espacios
        exteriores con String.trim()*/
      String strEntrada = scnEntrada.nextLine().trim();
      /*Al Intentar convertir la línea a BigDecimal, puede dar error si se han
        introducido números incorrectos, caracteres o más de un número*/
      try {
        /*Instrucción diferente para BigDecimal o los tipos primitivos(Float,
          Int,...)*/
        numero = new BigDecimal(strEntrada);   
        //numero = Float.parseFloat(strEntrada);
        /*Si hay error, irá al Catch; pero si no lo hay, irá directamente a las
        siguientes líneas, y para salir del bucle pongo:*/
        System.out.println("---------------------");
        numValido = true;
      } catch (Exception e) {
        System.out.println("Número no correcto.");
      }
    }
    return numero;
  }
  
  public static void main(String[] args) {
    /*Como el ejercicio no especifica ningún tipo de número, permitimos
      cualquier tipo y usamos el tipo más general: float*/
    BigDecimal num1, num2;
    //float num1 = 0, num2 = 0;
    
    System.out.println("Programa para calcular la suma y producto de dos números");
    System.out.println("========================================================\n");
    
    /*Pido introducir los números uno a uno, también podría pedirlos separados
      por un espacio y hacer un split.*/
    System.out.println("USO: Introducir el primer número y pulsar Enter. Ídem"
      + " para el segundo.");
    System.out.println("     Para escribir números decimales, usar un \".\"");

    /*Para cada número, llamo al método que verifica si el número es válido. Si
      fueran muchos números podría usar un bucle, pero para dos lo hago
      explícitamente*/
    num1 = NumeroValido("1");
    num2 = NumeroValido("2");
    
    System.out.println("Suma de los dos números");
    System.out.println(num1 + " + " + num2 + " = " + (num1.add(num2)));
    //System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
    System.out.println("Producto de los dos números");
    System.out.println(num1 + " x " + num2 + " = " + (num1.multiply(num2)));
    //System.out.println(num1 + " x " + num2 + " = " + (num1 * num2));
  } 
}