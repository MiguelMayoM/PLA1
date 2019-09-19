package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  1.- Crear un programa que pida 10 números al usuario entre 1 y 10. Los guarde 
    dentro de un array y finalmente muestre la suma de todos ellos.
  2.- Modificar el programa anterior para que ordene el array de mayor a menor.
  3.- Crear un programa que le pida al usuario 5 nombres de productos y los guarde
    en un array. Si el usuario introduce un nombre repetido que lo vuelva a pedir.
*/

import java.util.Scanner;
import java.util.Arrays;

public class PLA1_ejE_Arrays {
  final static String[] STRPROGRAMA = {"Sumar 10 números introducidos",
                                       "Ordenar de mayor a menor una lista de 10 números",
                                       "Almacenar 5 nombres de productos en un array"};
  final static int[] INTNUMGUIONES = {44, 48, 44};  
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
    imp("Programas que trabajan con arrays\n" +
                     "=================================\n");
    for(int i = 0; i < 3; i++){ 
      imp(abc(i+1), ". ", STRPROGRAMA[i]);
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
  
  static int CompruebaEntero(int intIndice) {
    int intEntero = 0;
    boolean bolValido = false;
    while (!bolValido) {
      imp("Número ", abc(intIndice+1), "º: ");
      String strEntrada = scnEntrada.nextLine().trim();

      try {
        intEntero = Integer.parseInt(strEntrada);
        bolValido = true;
      } catch (Exception e) {
        impln("Número no correcto.");
      }
    }
    return intEntero;
  }  
  
  static int ElegirEntero(int intIndice) {
    int intNumero;
    do{
      intNumero = CompruebaEntero(intIndice);
      if ((intNumero >= 1) && (intNumero <= 10)) {
        break;
      }
      impln("El número entero ha de estar entre 1 y 10");
    }while(true);
    return intNumero;
  }
  
  static int[] OrdenaBurbuja(int[] arrNumeros) {
    /*Consiste en comparar, 2 a 2, cada elemento del array con los siguientes,
      de forma que se inviertan los índices si el orden no es el correcto. Para
      ello, no hay que repetir las parejas comprobadas, puesto que el orden
      entre ellas ya será absoluto desde la primera comparación. Al final sólo
      hay que recorrer los índices que que corresponderían a una mitad superior
      o inferior de la diagonal de una matriz. Para 4 elementos:
      X 01 02 03
      X X  12 13  
      X X  X  23
      X X  X   X
      Un ejemplo de ordenación: [3 1 9 4]
        1º contra 2º: 3>1,   ok [3 1 9 4]   
                  3º: 3<9,  <-> [9 1 3 4] OJO porque ahora el 1º será un 9
                  4º: 9>4,   ok [9 1 3 4]
        2º contra 3º: 1<3,  <-> [9 3 1 4] OJO porque ahora el 2º será un 3
                  4º: 3<4,  <-> [9 4 1 3]
        3º contra 4º: 1<3,  <-> [9 4 3 1] OK  
    */
    int intIndiceMax = arrNumeros.length - 1;
    /*Comparamos desde el primero hasta el penúltimo. El último ya se habrá
      comparado con todos los demás anteriores y no tiene sentido compararlo
      consigo mismo*/
    for (int i=0; i<intIndiceMax; i++) {
      /*El anterior lo comparamos contra el siguiente y así hasta el último*/
      for (int j=i+1; j<=intIndiceMax; j++) {
        /*Y aquí mi orden, yo quiero de mayor a menor*/
        if (arrNumeros[i] < arrNumeros[j]) {
          /*Los intercanvio ayudándome de una variable temporal*/
          int intTemp = arrNumeros[i];
          arrNumeros[i] = arrNumeros[j];
          arrNumeros[j] = intTemp;
        }
      }
    }
    return arrNumeros;
  }
   
  static void ListaNumeros(int intOpcion) {
    /*Sigo pidiendo los datos 1 a 1, así le voy indicando al usuario cuantos
      lleva escritos. Si se introdujeran todos de golpe, podría ahorrarse un
      Enter cada vez, siempre y cuando no cometiera errores, en cuyo caso se
      volvería a pedir toda la ristra de 10 números. Para proceder de esta
      forma, haría un split del String, comprobaría que tiene 10 posiciones y
      que cada una contiene un dato correcto.*/
    /*Voy a usar enteros*/
    int[] arrNumeros = new int[10];
    /*Bucle para pedir 10 números, introduciéndolos en las posiciones
      respectivas*/
    for (int i=0; i<10; i++){
      arrNumeros[i] = ElegirEntero(i);
    }
    
    /*Según la opción elegida, los sumo o los ordeno*/
    int intSuma = 0;
    if (intOpcion == 1) {
      for (int N : arrNumeros) {
        intSuma += N;
      }
      impln("La suma de los 10 números es: ", abc(intSuma));
    } else {
      /*Opción 1*/
      /*Con java.utils.Collections, se podría hacer*/
      //Arrays.sort(arrNumeros, Collections.reverseOrder());
      
      /*Opción 2*/
      //Arrays.sort(arrNumeros);
      /*Lo clono para tenerlo como modelo para reordenarlo*/
      //int[] arrNumerosCopia = arrNumeros.clone();
      //int intIndiceMax = arrNumeros.length - 1;
      //for (int i=intIndiceMax; i>=0; i--){
      //  arrNumeros[i] = arrNumerosCopia[intIndiceMax - i];
      //}      
      
      /*Opción 3*/
      /*Como estamos trabajando con métodos, ifs, bucles, etc, voy a utilizar el
        algoritmo de la burbuja para ordenar, dándole ya el sentido del orden
        que yo quiera, de mayor a menor en este caso*/
      /*NOTA: el return en este método, ya devuelve ........................................*/
      OrdenaBurbuja(arrNumeros);     
      System.out.println("La lista de los 10 números, ordenada de mayor a menor, es: " + Arrays.toString(arrNumeros));
    }
  }
  
  static void ListaProductos(){
    String[] arrProductos = new String[5];
    boolean blnProdRepe = false;
    for (int i=0; i<5; i++) {
      do {
        imp("Introduzca el nombre del ", abc(i+1), "º producto: ");
        String strNuevoProd = scnEntrada.nextLine().trim();
        /*Comprobamos si el producto se encuentra en el array, en cuyo caso
          cambiamos la flag blnProdRepe a true para volver a pedir otro producto*/
        /*Otra vez más, podemos utilizar algún método ya implementado por alguna
          clase, en concreto Arrays.stream(), que secuencia el array en un 
          stream sobre el cual luego se puede aplicar anyMatch para buscar
          coincidencias*/
        //blnProdRepe = Arrays.stream(arrProductos).anyMatch(strNuevoProd::equals);
        
        /*O convirtiendo el array a Lista y usando el método contains o indexOf.
          Necesario importar 
          import java.util.List*/
        //List<String> lstListaProd = Arrays.asList(arrProductos);
        //blnProdRepe = lstListaProd.contains(strNuevoProd);
        
        /*O a mano con un bucle que recorra el array e ir comprobando cada
          elemento. He leído que es una solución mucho más rápida que cambiar a
          una List, pero menos que usar Array.binarySearch() en un array
          ordenado (sorted)*/
        for (String strElemento : arrProductos) {
          blnProdRepe = strNuevoProd.equals(strElemento);
          if (blnProdRepe){break;}            
        }
               
        if (!blnProdRepe) {
          arrProductos[i] = strNuevoProd;
        } else {
          impln("Este producto ya existe en la lista. Pruebe otra vez.");
        }
      } while (blnProdRepe != false);
    }
    System.out.println("\nLa lista de 5 productos es:" + Arrays.toString(arrProductos));
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
            Cabecera(0);
            ListaNumeros(1);
            break;
          case "2":
            bolValida = true;
            Cabecera(1);
            /*Puesto que no se dice lo contrario, y para simplificar, se
              mantiene la restricción de números entre 1 y 10*/
            ListaNumeros(2);
            break;
          case "3":
            bolValida = true;
            Cabecera(2);
            ListaProductos();
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