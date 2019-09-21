package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  + Codificar una palabra con los números que le corresponderían en el teclado
    numérico de un teléfono.
  + Dado un número, decodificarlo mostrando las posibles palabras
*/

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
//import java.time.Instant;
//import java.time.Duration;

public class PLA1_ej2_CodificacionNum {
  final static String[] STRPROGRAMA = {"Codificar una palabra (máx 5 letras, no Q ni Z)",
                                       "Decodificar un código (máx 5 dígitos, no 0 ni 1)"};
  final static int[] INTNUMGUIONES = {47, 48};
  
  final static String[] ALFABETO = {"ABC","DEF","GHI","JKL","MNO","PRS","TUV","WXY"};
  
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
    imp("Codificación Palabra <---> Código\n",
        "=================================\n");
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
  
  static void Representa(String[] arrPalabras) {
    imp("[", arrPalabras[0]);
    int intTotalElem = arrPalabras.length;
    /*Transformo el índice del for sumando 1 para el tema de las divisiones de
      elementos por línea, pero para representar los elementos ya le resto ese 1*/
    for (int i=2; i<=intTotalElem;i++) {
      if (i % 9 == 1) {
        imp(" ", arrPalabras[i-1]);
      } else {
        imp(", ", arrPalabras[i-1]);
      }
      if ((i % 9 == 0) && (i != intTotalElem)){impln();}
    }
    impln("]\n");
  }
  
  static int ElegirEntero() {
    String strNumero;
    do{
      imp("Código: ");
      strNumero = scnEntrada.nextLine().trim();
      if ((strNumero.length() <= 5) && (strNumero.matches("^[2-9]+$"))) {
      break;
      }
      impln("No válido. El código ha de tener cómo máximo 5 letras y no contener los dígitos 0 ni 1");
    }while(true);
    
    //int intDigitos = strNumero.length();
    /*Como los dígitos del número estánelegido de 2 a 9, he de hacer que vayan
      de 0 a 7 para que coincidan con los índices del Array ALFABETO. Para ello
      creo un entero con tantos 2'es como dígitos y se lo resto al número inicial*/
    //String strRestar = new String(new char[intDigitos]).replace("\0", "2");
    //int intCodigo = Integer.parseInt(strNumero) - Integer.parseInt(strRestar);
    /*Aquí me pasaba que si el dígito primero era un 2, al restar era 0 y se
      perdía. Solución, o dejar un String correcto con 0's a la izquierda si es
      el caso y luego convertir cada dígito a Entero, o bien pasar el Entero con
      la numeración sin restar y restar en su momento 2 unidades a cada dígito.
      Voy a hacer esto por ser lo que menos faena da, para conservar más código
      y hacer menos cambios*/
    return Integer.parseInt(strNumero);
  }
  
  static String ElegirPalabra() {
    String strPalabra;
    do{
      imp("Palabra: ");
      strPalabra = scnEntrada.nextLine().trim();
      if ((strPalabra.length() <= 5) && (strPalabra.toUpperCase().matches("^[A-PR-Y]+$"))) {
        break;
      }
      impln("No válida. La palabra ha de tener cómo máximo 5 letras y no contener los caracteres Q ni Z");
    }while(true);
    return strPalabra;  
  }
  
  static void Codificar() {
    Cabecera(0);
    String strPalabra = ElegirPalabra();
    /*Recorro las posiciones de la palabra y busco en que indice del array se
      encuentra cada letra. El índice +1 me da el número del código*/
    String strCodigo = "";
    for (int i=0; i<strPalabra.length(); i++){
      /*Corrección por el hecho de que los dígitos van de 2 a 9, y no de 0 a 7
        como los arrays*/
      for (int j=2; j<9; j++) {
        if (ALFABETO[j-2].contains(strPalabra.substring(i, i+1).toUpperCase()) == true) {
          strCodigo += String.valueOf(j);
          break;
        }
      }
    }
    impln("El código para la palabra \"", strPalabra, "\" es: ", strCodigo);
  }
  
  /*-----------*/
  /*RECURSIVO 1*/ /*R1, método más rápido, seguido de I1 e I3, que tardan un 50% más. El más lento, I2, de 5 a 10 veces más lento*/
  /*-----------*/
  /*Este método recursivo tiene como argumento de entrada el ArrayList en el
    que se van guardando. Pensé que había que hacerlo así, pero ya he visto que
    no en el siguiente ejemplo, Recursivo2. Primero lo implementé sin i,j,k para
    entenderlo y luego ya le puse los índices para abrir en ramas*/
  static ArrayList<String> Recursivo1(ArrayList<String> aLstDecPalabrasRecur1, ArrayList<Integer> aLstCodigo, int intPosicion, String strPalabra) {
    if (intPosicion == -1) {
      aLstDecPalabrasRecur1.add(strPalabra);
      return aLstDecPalabrasRecur1;
    }
    for(int i=0; i<3; i++) {
      //impln(ALFABETO[aLstCodigo.get(intPosicion)].substring(i,i+1));
      Recursivo1(aLstDecPalabrasRecur1, aLstCodigo, intPosicion - 1, strPalabra + ALFABETO[aLstCodigo.get(intPosicion)].substring(i, i+1)); 
    }
    return aLstDecPalabrasRecur1;
  } 
  
  /*-----------*/
  /*RECURSIVO 2*/
  /*-----------*/
  static ArrayList<String> Recursivo2(ArrayList<Integer> aLstCodigo, int intPosicion, String strPalabra) {
    ArrayList<String> aLstDecPalabrasRecur2 = new ArrayList();
    if (intPosicion == -1) {
      aLstDecPalabrasRecur2.add(strPalabra);
      return aLstDecPalabrasRecur2;
    }
    for(int i=0; i<3; i++) {
      aLstDecPalabrasRecur2.addAll(Recursivo2(aLstCodigo, intPosicion - 1, strPalabra + ALFABETO[aLstCodigo.get(intPosicion)].substring(i, i+1))); 
    }
    return aLstDecPalabrasRecur2;
  }
  
  /*-----------*/
  /*ITERATIVO 1*/
  /*-----------*/
  static String[] Iterativo1(ArrayList<Integer> aLstCodigo, int intNumDigitos) {
    /*Creo un array con los elementos que sé que tendrá. Hago un (cast) para
      pasar del Double que da pow, a Integer*/
    int intTotalElem = (int)Math.pow(3, intNumDigitos);
    /*Uso un Array porque así puedo concatenar el nuevo valor al que ya había.
      Con ArrayList tendría que hacer primero un get, guardar en una variable,
      remove de ese valor y add para poner el valor actual con la concatenación
      del nuevo carácter. El ejemplo 3 hace esto*/
    /*Lo malo, que al principio los valores son null y no puedo concatenar, sino
      sobreescribir*/
    String[] arrDecPalabrasIter1 = new String[intTotalElem];
      
    /*Para cada dígito del código (empiezo por el último porque en realidad es
      el primero, ya que al hallarlos me salían invertidos, cosa que me venía
      bien para el método anterior recursivo)...*/
    for (int i=intNumDigitos - 1; i>=0; i--) {
      int intContGrande = 0;
      /*...voy a recorrer todo el Array de la siguiente forma. Si por ejemplo
        hay 4 dígitos -> 3^4=81 elementos (de 0 a 80), para el primer dígito (4)
        voy a usar un inContPeque de 3^3, para que, mientras el intContGrande
        no llegue a 80, el interno haga tiradas de 27, poniendo 27 0's, 27 1's
        y 27 2's, que serán los índices que añadiré de las tres letras que
        conforman cada posición de la terna del ALFABETO correspondiente al
        dígito en cuestión.
        Si bien para el primer dígito hará lo que he dicho, para el segundo, en
        este caso, haría minibucles de 3^2=9, i.e. 9 0's, 9 1's, 9 2's, y vuelta
        a comenzar, 9 0's, 9 1's ... hasta llegar a los 81 elementos.
        Y para saber que índice toca lo que voy a hacer es recorrerlos en
        círculo, con el resto (%) de dividir el número de veces que he pasado por
        el bucle del intContPeque entre 3. O ni eso, que cuando la iteración
        anterior le dé valor 3 a intContPeque, que pase a 0*/
      int intContPeque = (int)Math.pow(3, i);
      int intIndice = 0;
      do{
        if (intIndice == 3) {intIndice = 0;}
        for (int j=0; j<intContPeque; j++){
          /*Para el primer dígito, he de sobreescribir el null*/
          //if (arrDecPalabrasIter1[intContGrande] != null) {
          if (i != intNumDigitos - 1) {
            arrDecPalabrasIter1[intContGrande] += ALFABETO[aLstCodigo.get(i)].substring(intIndice, intIndice + 1);
          } else {
            arrDecPalabrasIter1[intContGrande] = ALFABETO[aLstCodigo.get(i)].substring(intIndice, intIndice + 1);
          }
          intContGrande++; 
        }
        intIndice++;
      } while (intContGrande < intTotalElem);
    }
    return arrDecPalabrasIter1; 
  }
  
  /*-----------*/
  /*ITERATIVO 2*/  /*Me ordena diferente por pantalla, podría arreglarlo*/
  /*-----------*/
  static String[] Iterativo2(ArrayList<Integer> aLstCodigo, int intNumDigitos) {
    /*Este método, por la media del tiempo de 10 llamadas que he medido, me sale
      el doble de lento que los anteriores.
      Consiste en recorrer los números desde 0 hasta el total de combinaciones
      (3^(número de dígitos) - 1) pero en base 3, de forma que será necesario
      el mismo número de dígitos que los del código, con valores de 0 a 2, de
      esta forma, e.g. si el código tiene 3 dígitos, que habrá 27 combinaciones:
      000 001 002 010 011 012 020 021 022 100 101 102 110 111 112 ... 222
        0   1   2   3   4   5   6   7   8   9  10  11  12  13  14 ...  26
      Estas posiciones las usaré para tomar la posición del String adecuado del
      ALFABETO, y los calcularé para cada uno de los iteradores, con las partes
      enteras y restos de dividir 81 por 27 9 3 para las posiciones 0, 1 y 2
      respectivamente: lo que viene siendo un ejercicio de pasar el número en
      cuestión de base 10 a 3, e.g. Por eso es un método con la misma matemática
      que el anterior pero con otro punto de vista, el del cambio de base, que
      hace que sea mucho más lento. Se podría accelerar si, en vez de calcular
      cada vez el cambio de base, se tuvieran unas TABLAS, dado que el número de
      casos, dado por la longitud de palabras, es pequeño*/
    
      /*Si se han pasado como argumentos 4 dígitos, tendré un número máximo de
      3^4 - 1 = 80; escojamos como iría la cosa para el 71:
      
      71    71/(3^3) = 2    71%(3^3) = 17 
      17    17/(3^2) = 1    17%(3^2) =  8         base 10    base 3
       8     8/(3^1) = 2     8%(3^1) =  2 ------>   71    =   2122
      2122 son los índices de cada dígito
    */
    /*Hago un (cast) del Double que sale al calcular la potencia*/
    int intTotalElem = (int)Math.pow(3, intNumDigitos);
    String[] arrDecPalabrasIter2 = new String[intTotalElem];
    /*Ahora recorro todos los elementos del Array, que son números decimales que
      querré representar en base3 según el número de dígitos que tenga el código.
      Es decir, 1 es 1 en base 10 y en base 3, pero, si tengo 4 dígitos, quiero
      representarlo 0001 y estos serán los índices 012 de las letras que le
      corresponderán a cada dígito para crear la palabra determinada*/
    /*Voy dividiendo y tomando el resto que se vuelve a dividir hasta llegar al
      al último divisor, que será el 3, en la posición 0 de intDivisores[]
      Inicializo una variable String para guardar la conversión y un integer
      para guardar el dividisor*/
    String strIaBase3, strPalabra;
    int intPrimerDivisor = (int)Math.pow(3, intNumDigitos - 1);
    int intDividendo, intDivisor, intIndice;
    
    for (int i=0; i<intTotalElem; i++) {
      strIaBase3 = "";
      strPalabra = "";
      intDivisor = intPrimerDivisor;
      intDividendo = i;
      while (intDivisor >= 1) {
        /*Lo primero porque he empezado el divisor con el número máximo, el de
          elementos, para que cuando la siguiente / sea 1, luego ya llegue al
          while y salga. Antes lo había puesto diferente y entonces hacía 1/3=0*/
        strIaBase3 += Integer.toString(intDividendo / intDivisor);
        intDividendo = intDividendo % intDivisor;
        intDivisor /= 3;
      }
      
      /*Ahora supongamos que estamos con un código de 4 dígitos, e.g. 7586, y
        hemos convertido 53 = 1222, entonces para hallar que letras irán en esta
        combinación 53 del total del 81, procederé: del primer dígito, 7, al que 
        le corresponden las letras PRS (no WXY, recordemos que los números van
        de 2 a 9, y luego los adapto restando 2 para que coincidan con el Array
        ALFABETO, que va de 0 a 7), tomaré el primer dígito de la base 3, que
        es un 1 (del string de 3 letras PRS), y entonces le corresponderá una R*/
      /*Empiezo con los dígitos al revés porque me vienen cambiados según los
        hallé a partir del código escrito y como para el primer método que usé
        ya me venían bien, pues los he dejado así, pero es fácil cambiarlos, ya
        comenté un método con bucle o con Collections.reverse*/
      for (int j=intNumDigitos - 1; j>=0; j--) {
        intIndice = Integer.parseInt(strIaBase3.substring(j, j+1));
        strPalabra += ALFABETO[aLstCodigo.get(j)].substring(intIndice, intIndice + 1);
      }
      arrDecPalabrasIter2[i] = strPalabra;
    }
    return arrDecPalabrasIter2; 
  }
  
  /*-----------*/
  /*ITERATIVO 3*/
  /*-----------*/
  static ArrayList<String> Iterativo3(ArrayList<Integer> aLstCodigo, int intNumDigitos) {
    ArrayList<String> aLstDecPalabrasIter3 = new ArrayList<String>(1);
    /*Lo tengo que inicializar vacío, si no no me los cuenta como size*/
    aLstDecPalabrasIter3.add("");
    
    /*El método más fácil, aprovechando las características de los ArrayList.
      Explicación con un ejemplo. Si tengo 789, con un bucle recorro los dígtos
      del código. Para cada dígito, ahora el primero, un 7, recorro sus letras
      en un segundo bucle interior (PRS) y las voy añadiendo al ArrayList, que 
      tendrá entonces 3 posiciones. 
      + En este ejemplo es vital empezar por el primer dígito, el de la izquierda,
        por la estructura de arbol que se forma al realizar las combinaciones.
        Así, cuando ahora esté el bucle exterior en el segundo dígito, es cuando
        viene la otra condición imprescindible
      + Leeré el ArrayList desde la última posición, una S del paso anterior,
        eliminaré ese registro y añadiré la concatenación del valor S guardado
        con los tres nuevos TUV(dígito 8), añadiendo las combinaciones desde,
        la posición 3: add(3, ST), add(4, SU), add(5, SV). Y en la posición 2,
        eso es lo bueno, porque sólo se han alterado por detrás, tendré la R para
        combinar de nuevo con TUV , añadiendo desde la posición 2 y empujando todo
        lo que haya detrás, de forma que en la posición 1 aun nos quedará la P
        suelta y nuevamente combinaremos.
      + Ahora tendremos 9 posiciones en el ArrayList y nos toca el siguiente
        dígito. Otra vez comenzando desde atrás, recorriendo todo el ArrayList
        hasta llegar a la 1ª posición, le añadiremos la siguiente terna, de forma
        que sólo se modifican las posiciones por detrás, pero no por delante, y
        ahí está la gracia del método
    */
    /*Para cada dígito, empezando por la izquierda, es importante. Esto sería
      una variante del método 1, donde calculaba las frecuencias en el árbol,
      e.g. para 27 combinaciones, variación cada 9 en la primera posición, cada
      3 en la segunda, y cada 1 en la tercera. Pero aquí no calculo nada, porque
      el truco está en recorrer desde el final hacia delante el ArrayList y en
      añadirle en sentido contrario, empujando hacia abajo, gracias al tamaño
      variable del Array*/
    /*Es importante empezar por un ArrayList de orden 3, para tener algo que
      recorrer desde el final hasta el principio, ya para el primer dígito. Me
      estoy dando cuenta que en esencia, en realidad, creo que estoy haciendo
      un proceso "recursivo", apoyándome en la estructura del ArrayList, aunque el
      código sea iterativo*/
    String strTemp;
    ArrayList<String> aLstTemp = new ArrayList(0);
    for (int i=intNumDigitos-1; i>=0; i--) {
      /*Recorro el ArrayList desde abajo*/
      for (int j=aLstDecPalabrasIter3.size() - 1; j>=0; j--) {
        /*Leo el valor almacenado y lo guardo en variable*/
        strTemp = aLstDecPalabrasIter3.get(j);
        /*Lo elimino para poner en esa posición una nueva Lista combinación de
          lo que hay guardado más la terna nueva*/
        aLstDecPalabrasIter3.remove(j);
        for (int k=0;k<3; k++) {
          aLstTemp.add(strTemp + ALFABETO[aLstCodigo.get(i)].substring(k, k + 1));
        }
 
        /*Y añado el ArrayList temporal en esa posición y luego lo vacío*/
        aLstDecPalabrasIter3.addAll(j, aLstTemp);
        aLstTemp.clear();
        /*Las posiciones hacia abajo se habrán modificado, pero no hacia arriba
          y por eso el bucle sigue su curso como si no hubiéramos añadido nada*/
      }
    }
    return aLstDecPalabrasIter3;
  }

  static void Decodificar() {
    Cabecera(1);
    int intCodigo = ElegirEntero();
    /*Tendré que extraer cada dígito del código para compararlo con los dígitos
      del teclado. Podría convertirlo a String y extraerlo y luego volver a
      convertirlo a Entero, o sacarlo como resto de dividir entre 10 el número
      entero que recursivamente sale de la parte entera de dividirlo por 10*/
    /*También podría haber pasado la longitud del código desde ElegirEntero y
      usar un Array en vez de un Array List*/
    ArrayList<Integer> aLstCodigo = new ArrayList(0);
    while (intCodigo > 0) {
      aLstCodigo.add((intCodigo % 10) - 2);
      intCodigo /= 10;
    }
    
    /*Haciendo esta operación, me vienen alrevés los dígitos, pero esto ya me va
      bien porque a la función Recursiva le paso la longitud del ArrayList y
      en cada llamada decremento el índice en una unidad hasta llegar a -1, que
      es cuando sale. Como el "diagrama de árbol" que se crea quiero que empiece
      desde el primer índice, por el primer dígito del código, entonces ya me va
      bien que este dígito ocupe la última posición, así que el siguiente código
      para girarlos ya no me hace falta*/
    //Collections.reverse(aLstCodigo);
    /*O bien, copio el último elemento al principio y lo elimino del final. Como
      el nuevo elemento puesto al principio habrá empujado todo para arriba,
      ahora el último será el que antes era el penúltimo, y lo vuelvo a copiar y
      poner primero y así repito el ciclo hasta que los he movido todos*/
    //  int intIndiceMax = aLstCodigo.size() - 1;
    //  for (int i=0; i<intIndiceMax; i++) {
    //    aLstCodigo.add(i, aLstCodigo.get(intIndiceMax));
    //    aLstCodigo.remove(intIndiceMax + 1);
    //  }

    /*========================================================================*/
    /*           LAS 5 LLAMADAS, REPRESENTACIÓN DATOS Y CÁLCULO TIEMPO        */
    /*========================================================================*/
    /*Voy a "medir" el tiempo. Hay varios sistemas, cada uno con sus más y sus
      menos. En concreto .nanoTime() tiene precisión de ns PERO no garantizan
      esta resolución porque depende de cuando se van actualizando los relojes,
      que coincida con el momento en que uno los enciende o apaga. Y ... a ver...
      No estoy midiendo sólo el proceso, también el crear o vaciar Arrays o
      ArrayLists, pero bueno, era por probar.
    */
    long lngInicio, lngFinal, lngDuracion;
    
    int intNumDigitos = aLstCodigo.size();
    /**********************/
    /*De forma RECURSIVA 1*/
    /**********************/
    lngInicio = System.nanoTime();
    ArrayList<String> aLstDecPalabrasRecur1 = new ArrayList(0);
    for (int i=0;i<10;i++){
      aLstDecPalabrasRecur1.clear();
      Recursivo1(aLstDecPalabrasRecur1, aLstCodigo, intNumDigitos - 1, "");
    }
    lngFinal = System.nanoTime();
    lngDuracion = (lngFinal - lngInicio)/10;
    
    System.out.println("Las posibles descodificaciones del código son (calculado recursivamente por el método 1 en " + lngDuracion + " ns): ");
    //System.out.println(aLstDecPalabrasRecur);
    //impln();
    /*Mejoro la visibilidad del output. Convierto el ArrayList a Array para hacer
      una función para presentar el resultado por pantalla que me valga para los
      tres casos*/
    String arrDecPalabrasRecur[] = aLstDecPalabrasRecur1.toArray(new String[aLstDecPalabrasRecur1.size()]);
    Representa(arrDecPalabrasRecur);
    
    
    /**********************/
    /*De forma RECURSIVA 2*/
    /**********************/
    lngInicio = System.nanoTime();
    for (int i=0;i<10;i++){
      Recursivo2(aLstCodigo, intNumDigitos - 1, "");
    }
    lngFinal = System.nanoTime();
    lngDuracion = (lngFinal - lngInicio)/10;
    
    ArrayList<String> aLstDecPalabrasRecur2 = Recursivo2(aLstCodigo, intNumDigitos - 1, "");
    System.out.println("Las posibles descodificaciones del código son (calculado recursivamente por el método 2 en " + lngDuracion + " ns): ");

    String arrDecPalabrasRecur2[] = aLstDecPalabrasRecur2.toArray(new String[aLstDecPalabrasRecur2.size()]);
    Representa(arrDecPalabrasRecur);

    /**********************/
    /*De forma ITERATIVA 1*/
    /**********************/
    lngInicio = System.nanoTime();
    for (int i=0;i<10;i++){
      String[] arrDecPalabrasIter1 = Iterativo1(aLstCodigo, intNumDigitos);
    }
    lngFinal = System.nanoTime();
    lngDuracion = (lngFinal - lngInicio)/10;
    /**/
    String[] arrDecPalabrasIter1 = Iterativo1(aLstCodigo, intNumDigitos);
    
    System.out.println("Las posibles descodificaciones del código son (calculado iterativamente por el método 1 en " + lngDuracion + " ns): ");
    //System.out.println(Arrays.toString(arrDecPalabrasIter1));
    //impln();
    Representa(arrDecPalabrasIter1);
    
    /**********************/
    /*De forma ITERATIVA 2*/
    /**********************/
    lngInicio = System.nanoTime();
    for (int i=0;i<10;i++){
      Iterativo2(aLstCodigo, intNumDigitos);
    }
    lngFinal = System.nanoTime();
    lngDuracion = (lngFinal - lngInicio)/10;
    /**/
    String[] arrDecPalabrasIter2 = Iterativo2(aLstCodigo, intNumDigitos);
    System.out.println("Las posibles descodificaciones del código son (calculado iterativamente por el método 2 en " + lngDuracion + " ns): ");
    Representa(arrDecPalabrasIter2);

    /**********************/
    /*De forma ITERATIVA 3*/
    /**********************/
    lngInicio = System.nanoTime();
    for (int i=0;i<10;i++){
      Iterativo3(aLstCodigo, intNumDigitos);
    }
    lngFinal = System.nanoTime();
    lngDuracion = (lngFinal - lngInicio)/10;
    /**/
    ArrayList<String> aLstDecPalabrasIter3 = Iterativo3(aLstCodigo, intNumDigitos);
    
    /*Lo convierto a Array para pasarlo a la función de visualización*/
    String arrDecPalabrasIter3[] = aLstDecPalabrasIter3.toArray(new String[aLstDecPalabrasIter3.size()]);
    System.out.println("Las posibles descodificaciones del código son (calculado iterativamente por el método 3 en " + lngDuracion + " ns): ");
    Representa(arrDecPalabrasIter3);
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
            Codificar();
            break;
          case "2":
            bolValida = true;
            Decodificar();
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
