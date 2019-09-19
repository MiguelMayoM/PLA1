package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  1.- Escribir un programa que pida un número al usuario entre 1 y 10. Si el
    usuario introduce un número que no esté en el rango, debe volver a pedirlo
    (Bucle do while)
  2.- Escribir un programa que nos pida una cadena y nos diga cuantas veces
    aparece la letra "a" (bucle while, string indexof)
  3.- Escribir un programa que nos pida un número mayor de 1 y que nos diga la
    suma de todos los números hasta el número introducido. Por ejemplo, si
    introduce el 4: 1+2+3+4=10
*/

import java.util.Scanner;

public class PLA1_ejC_Bucles {
  final static String[] STRPROGRAMA = {"Elegir un número entre 1 y 10",
                                "Contar apariciones letra \"a\"",
                                "Suma de los números entre 1 y un número elegido"};
  final static int[] INTNUMGUIONES = {29, 28, 47};  
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
  /*Para cuando hay enteros, hay que pasarlos convertidos a String*/
  static String abc(int intN) {
    return Integer.toString(intN);
  }  
    
  static void CabeceraGeneral() {
    imp("Programas que trabajan con bucles\n" +
                     "=================================\n");
    for(int i = 0; i < 3; i++){ 
      impln((i+1) + ". " + STRPROGRAMA[i]);
    }
    impln("S. Salir\n");  
  }
  
  static void Cabecera(int intEleccion) {
    /*Voy a crear una línea para subrayar el título. Podría usar un bucle, ya
      que estamos en ello, pero así aprendemos algo. Creamos un array, que
      tendrá sus posiciones con valor null (\0) y las cambiamos por guiones*/
    String strNGuiones = new String(new char[INTNUMGUIONES[intEleccion]]).replace("\0", "=");
    /*También se podría haber hecho, con //import java.util.Collections;*/
    //System.out.print(String.join("", Collections.nCopies(intNumGuiones[intEleccion], "=")));
        
    impln("\n", STRPROGRAMA[intEleccion]);
    impln(strNGuiones);
  }
  
  static int CompruebaEntero() {
    int intEntero = 0;
    boolean bolValido = false;
    while (!bolValido) {
      imp("Introduzca un número entero: ");
      String strEntrada = scnEntrada.nextLine().trim();

      try {
        intEntero = Integer.parseInt(strEntrada);
        impln("---------------------");
        bolValido = true;
      } catch (Exception e) {
        impln("Número no correcto.");
      }
    }
    return intEntero;
  }

  static void Elegir() {
    Cabecera(0);
    int intNumero;
    /*No hace falta una variable que controle la condición, puedo hacer un bucle
      infinito y salir con break;*/
    //boolean bolValido = false;
    do{
      intNumero = CompruebaEntero();
      if ((intNumero >= 1) && (intNumero <= 10)) {
        //bolValido = true;
        break;
      }
      impln("El número entero ha de estar entre 1 y 10");
    }while(true);
    //}while(!bolValido);
    impln("Eligió el número ", abc(intNumero));
  }

  static void Contar() {
    Cabecera(1);
    imp("Escriba una frase: ");
    String strTexto = scnEntrada.nextLine();
    /*Como nos piden hacerlo con indexOf y bucles, reservamos otra forma de
      hacerlo para el siguiente ejercicio del PLA1. Aun así, una manera de
      hacerlo con indexOf sería recorrer una a una todas las posiciones de
      strTexto, es decir, letra a letra, y ver si indexOf da 0, en cuyo caso
      incrementaríamos el contador de coincidencias. Pero voy a hacerlo de otra
      forma con indexOf, utilizando la búsqueda a partir de la última
      coincidencia encontrada (que actualizaré dentro del bucle), con la
      condición implementada con un while hasta que llegue al último carácter
      del texto (si es que allí hubiera una coincidencia) o hasta que
      indexOf = -1,sinónimo de que ya no hay más coincidencias.*/
    int intCoincidencias = 0,
        /*La posición de inicio para buscar la pongo -1 para que en la primera
          iteración, al sumarle 1 empiece desde 0*/
        intPosUltima = -1,
        intLargoTexto = strTexto.length();
    do {
      /*Busco tanto si se trata de una "a" o "A", a partir de la posición de la
        última coincidencia*/
      int intMinusc = strTexto.indexOf("a", intPosUltima + 1);
      int intMayusc = strTexto.indexOf("A", intPosUltima + 1);
      /*Cuando encuentra de ambos tipos, hay que tomar la posición menor, para
        ir por orden. Para no usar Math.min voy a usar un operador ternario*/
      intPosUltima = (intMinusc < intMayusc) ? intMinusc : intMayusc;
      /*Pero lo anterior no vale si sólo hay una de las coincidencias, por lo
        cual el -1 de no encontrar la otra sería el número menor y tomaria este,
        dando un comportamiento incorrecto. Para ello, compruebo si una de ellas
        es menor que 0 y, en ese caso, cojo el valor positivo. Para no preguntar
        cual de las dos es la positiva, utilizo un operador AND con las dos
        variables, que me da directamente la positiva, e.g.*/
      //  5 = 0101 
      // -1 = 1111 (Two's complement: 1111 + 0001 = 10000) (15 + 1 = 16)
      //  &   0101 = 4
      intPosUltima = ((intMinusc < 0) || (intMayusc < 0)) ? intMinusc & intMayusc : intPosUltima; 
      //impln("hola: " + abc(intPosUltima));
     
      if (intPosUltima >= 0){
        intCoincidencias += 1;
      }
    } while((intPosUltima <= intLargoTexto) && (intPosUltima != -1));
    impln("Número de veces que aparece una \"a\" o \"A\": ", abc(intCoincidencias));   
  }
  
  static void SumaPA() {
    Cabecera(2);
    int intNumero = CompruebaEntero();
    /*Lo normal sería usar la fórmula de la suma de términos de una progresión
      aritmética, pero como estamos trabajando con bucles, voy a usar un bucle.
      En cuanto a la fórmula citada, no habría problema en trabajar con enteros
      porque la suma siempre va a ser un entero, sólo hay que tener cuidado de
      multiplicar primero y después dividir entre 2; al revés, en el caso de un
      número impar de términos de la serie, aparecería un ",5" si se realizara
      primero la división y luego se multiplicara por 2*/
    //int suma = (1 + hastaNum) * hastaNum / 2;
    //return suma;
    int intSuma = 0, intMenor, intMayor;
    /*Voy a hacer que sea válida para enteros positivos y negativos*/
    if (intNumero >= 1){
      intMenor = 1; intMayor = intNumero;
      for(int i=1; i<=intNumero; i++){
        intSuma += i;
      }
    }else{
      intMenor = intNumero; intMayor = 1;
      for(int i=intNumero; i<=1; i++){
        intSuma += i;
      }        
    }
    impln("La suma de los números entre ", abc(intMenor), " y ", abc(intMayor), " vale ", abc(intSuma));
  }
  
  public static void main(String[] args) {
    boolean bolSalir = false, bolValida = false;
    /*Elijo un string porque para comparar ya vale y así no tengo que realizar
      ninguna gestión de errores. Y en las opciones del switch también las
      pongo como string*/
    String strEleccion;
        
    /*Creamos un bucle hasta que se elija una opción válida*/
    do {
      CabeceraGeneral();
      do {
        imp("Elija una opción: ");
        strEleccion = scnEntrada.nextLine().trim();

        switch(strEleccion){
          case "1":
            /*Para escribir sólo una vez esta asignación, la podríamos poner en
              la función Cabecera()*/
            bolValida = true;
            Elegir();
            break;
          case "2":
            bolValida = true;
            Contar();
            break;
          case "3":
            bolValida = true;
            SumaPA();
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