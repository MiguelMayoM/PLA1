package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  Crear los siguientes métodos dentro de la clase principal de nuestro proyecto.
  Como todavía no estamos trabajando con orientación a objetos todos deberán ser
  static.
  1.- Crear un método que pasándole dos cadenas, nos devuelva la cadena más
    larga. Ejemplo:
    masLarga("hola","caracola") devuelve "caracola"
  2.- Crear un método que pasándole una letra y una cadena nos diga cuantas
    veces aparece esa letra en la cadena.
    veces("a","caracola") devuelve 3
*/

import java.util.Scanner;

public class PLA1_ejD_Metodos {
  final static String[] STRPROGRAMA = {"Escribir dos frases y devolver la más larga",
                                       "Escribir un texto y elegir una letra para ver cuantas veces aparece en el mismo"};
  final static int[] INTNUMGUIONES = {53, 79};  
  static Scanner scnEntrada = new Scanner(System.in);
  
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
  static String abc(int intN) {
    return Integer.toString(intN);
  } 
  
  static void CabeceraGeneral() {
    imp("Programas que trabajan con métodos\n",
        "==================================\n");
    for(int i = 0; i < 2; i++){ 
      impln(abc(i+1), ". ", STRPROGRAMA[i]);
    }
    impln("S. Salir\n");  
  }
  
  static void Cabecera(int intEleccion) {
    String strNGuiones = new String(new char[INTNUMGUIONES[intEleccion]]).replace("\0", "=");
    impln("\n", STRPROGRAMA[intEleccion]);
    impln(strNGuiones);
  }
  
  static String FraseLarga(String strFrase1, String strFrase2) {
    String strFraseLarga = strFrase1.length() > strFrase2.length() ? strFrase1 : strFrase2;
    return strFraseLarga;
  }

  static char CompruebaCaracter() {
    String strEntrada ="";
    int intLongCadena;
    /*Repetimos mientras la cadena introducida no tenga un solo carácter*/
    do {
      /*Leemos toda la línea (sin espacios exteriores) para evitar entradas como
        "a b"*/
      strEntrada = scnEntrada.nextLine().trim();
      intLongCadena = strEntrada.length();
      /*Ahora no uso un try catch así que tengo que poner un if para ...*/
      if(intLongCadena != 1) {
        impln("Carácter NO válido o ha escrito más de uno");
        imp("Vuelva a escribir un solo carácter: ");
      }
    } while (intLongCadena != 1);
    /*Cuando salga, se habrá escrito un solo carácter, pero en tipo de dato
      String. Construimos un dato tipo char extrayéndolo del string con charAt*/ 
    char chrLetra = strEntrada.charAt(0);
    return chrLetra;
  }  
  
  static int Contar(String strFrase, char chrLetra) {
    /*Ahora voy a proceder de forma diferente al ejercicio anterior de bucles.
      Voy a reemplazar todo lo que no sea el carácter elegido por espacio vacío.
      Así, me quedará una cadena que sólo contendrá las distintas apariciones
      del carácter deseado una tras otra. Sólo restará pedir la longitud de esta
      cadena para saber el número total de apariciones
      También se podría hacer que fuera el carácter elegido el que desapareciera
      a "", y restar la diferencia entre la longitud incial del string y la
      nueva*/
    /*Para el primer caso, he de usar una Regular Expression: ^ entre [] niega
      el carácter; es decir, la regex selecciona todo lo que no sea el carácter*/ 
    String strRegex = "[^" + Character.toString(chrLetra) + "]";
    String strSoloCaracter = strFrase.replaceAll(strRegex, "");
    int intCoincidencias = strSoloCaracter.length();
    return intCoincidencias;
  }
   
  public static void main(String[] args) {
    boolean bolSalir = false, bolValida = false;
    String strEleccion;
    do {
      CabeceraGeneral();
      do {
        imp("Elija una opción: ");
        strEleccion = scnEntrada.nextLine().trim();

        switch(strEleccion){
          case "1":
            bolValida = true;
            /*Como se trata de hacer un ejercicio para trabajar con parámetros
              pasados a métodos, "ensucio" este esquema y pido los parámetros
              aquí, pasándolos a la función en cuestión, que sólo tendrá una
              línea y se podría haber puesto aquí, pero se trata de ejemplificar
              el paso de parámetros*/
            Cabecera(0);
            imp("Escriba la primera frase: ");
            String strFrase1 = scnEntrada.nextLine().trim();
            imp("Ahora la segunda frase: ");
            String strFrase2 = scnEntrada.nextLine().trim();
            String strFraseLarga = FraseLarga(strFrase1, strFrase2);
            impln("La frase más larga de las que ha escrito tiene ", abc(strFraseLarga.length()), " caracteres y es:\n", strFraseLarga);
            break;
          case "2":
            bolValida = true;
            Cabecera(1);
            imp("Escriba una frase : ");
            String strFrase = scnEntrada.nextLine();
            
            imp("Escriba el carácter cuyo número de apariciones quiere contar (sólo uno): ");
            char chrLetra = CompruebaCaracter();
            
            int intCoincidencias = Contar(strFrase, chrLetra);
            System.out.println("Número de veces que aparece el carácter \"" + chrLetra + "\": " + intCoincidencias);   
 
            break;
          case "S":
            bolValida = true;
            bolSalir = true;
            break;
          default:
            impln("Opcion no válida.");
        }
      } while(!bolValida);
      
      if(strEleccion.equals("S")){
        break;
      }
      
      impln("=====================");
      impln("Pulse Enter para volver al Menú Inicial.");
      scnEntrada.nextLine();
    } while(!bolSalir);
  } 
}