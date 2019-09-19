package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  Hacer un programa que pida la altura y la edad de un niño.
  El programa evalua si el niño puede entrar en una atracción de portaventura.
  Puede hacerlo si:
    1.- Tiene más de 16 años
    o
    2.- Mide mas de 140 centímetros
*/

import java.util.Scanner;

public class PLA1_ejB_Condicionales {
  static Scanner scnEntrada = new Scanner(System.in);
  
  /*Voy a crear unos alias para imprimir en pantalla sin escribir tanto*/
  static void imp(String... args) {
    for (String arg : args) {
      System.out.print(arg);
    }
  }
  static void impln(String... args) {
    for (String arg : args) {
      System.out.print(arg);
    }
    System.out.print("\n");
  }

  static int EnteroValido(String strVariable, String strUnidad) {
    int numero = 0;
    boolean numValido = false;
    while (!numValido) {
      imp("Introduzca la ", strVariable, " en ", strUnidad, " exactos (sin decimales): ");
      String strEntrada = scnEntrada.nextLine().trim();
      
      try { 
        numero = Integer.parseInt(strEntrada);
        impln("---------------------");
        numValido = true;
      } catch (Exception e) {
        impln("Número no correcto.");
      }
    }
    return numero;
  }  
  
  public static void main(String[] args) {
    int edad, altura;

    impln("Programa para evaluar si el usuario puede entrar una atracción");
    impln("==============================================================\n");

    edad = EnteroValido("edad", "años");
    altura = EnteroValido("altura", "cm");
    
    if ((edad > 16) || (altura) > 140) {
      impln("Acceso permitido a la atracción");
    } else {
      impln("ACCESO DENEGADO a la atracción");
    }
  }  
}