package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  1.- Escribir un programa que vaya pidiendo números al usuario entre 1 y 10 y
    que finalice cuando el usuario introduzca el 0. Que los vaya metiendo en un 
    arraylist y al finalizar muestre el número de elementos, la suma de todos
    ellos y los números que sean pares.

  2.- Escribir un programa que nos pida una frase, guarde las palabras en un
    arraylist y nos diga cuantas palabras hay y cual es la más larga.
*/

import java.util.Scanner;
import java.util.ArrayList;

public class PLA1_ejF_ArrayList {
  final static String[] STRPROGRAMA = {"Crear ArrayList de números. Tamaño, suma y mostrar pares.",
                                       "Calcular palabras en una frase y palabra más larga."};
  final static int[] INTNUMGUIONES = {57, 51};
  
  /*Todo y tener el projecto en codificación UTF-8, a veces el IDE puede tener
    la entrada de datos en formato diferente por defecto y hemos de corregirlo
    para que el output sea correcto. A veces se soluciona en el archivo de
    configuración del programa, e.g. en NetBeans añadiendo -J-Dfile.encoding=UTF-8
    a la variable netbeans_default_options en el archivo etc/netbeans.conf. Pero
    también se puede solucionar indicando la codificación de entrada de datos en
    el Scanner. Por ejemplo, si esta fuera "Windows-1252", algo típico:
    static Scanner scnEntrada = new Scanner(System.in, "Windows-1252");
  */
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
    imp("Programas que trabajan con ArrayList\n",
        "====================================\n");
    for(int i = 0; i < 2; i++){
      impln(abc(i+1), ". ", STRPROGRAMA[i]);
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
      imp("Número: ");
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
  
  static int ElegirEntero() {
    int intNumero;
    do{
      intNumero = CompruebaEntero();
      if ((intNumero >= 0) && (intNumero <= 10)) {
        break;
      }
      impln("El número entero ha de estar entre 1 y 10");
    }while(true);
    return intNumero;
  }
 
  static void AListNumeros() {
    Cabecera(0);
    /*Creamos el ArrayList y lo vamos rellenando, comprobando que el número sea
      correcto, hasta que el usuario introduzca el 0. Iba ausar un tamaño
      inicial de 0 para ir comprobando el último elemento añadido si era 0 y
      entonces salir, pero mejor crear una variable al respecto que recoja la
      entrada que no calcular la size cada vez. Si no exijo un tamaño inicial de
      0, se crea con 10 posiciones y, si quiero eliminar las que no se utilicen
      si el usuario introduce menos de 10 números, siempre puedo usar el método
      .trimToSize()*/
    ArrayList<Integer> aLstNumeros = new ArrayList<Integer>(0);
    impln("Introduzca uno a uno números entre 1 y 10 para finalizar introduzca un 0.");
    int intNuevo;
    do {
      intNuevo = ElegirEntero();
      if (intNuevo != 0) {aLstNumeros.add(intNuevo);}
    } while (intNuevo != 0);
    
    impln("Ha introducido un total de ", abc(aLstNumeros.size()), " números");
    
    int intSuma = 0;
    ArrayList<Integer> aLstPares = new ArrayList<Integer>(0);
    for (int i : aLstNumeros){
      intSuma += i;
      /*Mientras recorro la lista, aprovecho para ver cuales son pares y los
        meto en otra lista nueva*/
      if (i%2 == 0) {aLstPares.add(i);}
    }
    impln("La suma de los elementos es: ", abc(intSuma));
    if (aLstPares.size() != 0){
      System.out.println("Los números pares de la lista son: " + aLstPares);
    } else {
      impln("No hay números pares en la lista");
    }
  }

  static void PalabrasFrase() {
    Cabecera(1);
    impln("Escriba la frase que desee analizar:");
    String strFrase = scnEntrada.nextLine();
    /*Por si el usuario emplea signos de puntuación, que, pegados a las palabras,
      les darían a estas una longitud errónea, voy a eliminar todo lo que se me
      ocurra con un ReplaceAll. Además, si hay más de un espacio, también los
      contraigo a uno sólo, para que no me de problemas al separar por espacios
      y que pueda llegar a considerar palabras vacías*/
    strFrase = strFrase.replaceAll("[.,;¿?¡!()]", "").trim().replaceAll("\\s+", " ");
    //impln(strFrase);
       
    /*Ahora ya puedo hacer un split por los espacios. Entiendo que podría usar
      un array porque ya le doy directamente la dimensión con el número de
      elementos que salen del split, no hay que ir añadiendo uno a uno como en
      el ejercicio anterior. Pero se pide guardar las palabras en un ArrayList*/
    /*No puedo directamente asociar el array del split a un ArrayList. Puedo 
      crear un array y recorrerlo e ir llenando el ArrayList con .add*/
    //String[] arrFrase = strFrase.split(" "); 
    //ArrayList<String> aLstPalabras = new ArrayList<String>(0);
    //for (String s : arrFrase) {
    //  aLstPalabras.add(s);
    //}
    
    /*O utilizar el método .asList() del "prototipo" Arrays.*/
    //ArrayList<String> aLstPalabras = new ArrayList<String>(
    // Arrays.asList(strFrase.split(" ")));
    //System.out.println(aLstPalabras);
    
    /*El código escrito arriba funciona y es interesante. De todas formas, creo
      que ahora se pide usar ArrayList para ir añadiendo las palabras una a una
      y no directamente, recorriendo el string de la frase desde el inicio hasta
      el final, posición a posición, y delimitando las palabras por las
      posiciones donde se encuentren los espacios, buscando con indexOf, como en
      un ejercicio anterior*/
    /*Puedo usar indexOf para encontrar los espacios y dos marcadores para las
      posiciones de estos, uno para la posición actual si es que encuentra algún
      espacio, y otro para la posición anterior si es que ya encontró
      anteriormente otro, para así poder sacar el substring que contendrá una
      palabra. Pero claro, si no usé split, ahora usar substring ... Voy a hacer
      otra cosa para no usar ningún método elaborado. Sólo .charAt(), y porque
      no se puede hacer strFrase[x] para leer el carácter en la posición x. Voy
      a recorrer todo el String, posición por posición, guardando los carácteres
      en una variable y cuando encuentre un espacio, en esa variable habrá una
      palabra, la añadiré al ArrayList y vaciaré la variable para irla
      rellenando de nuevo y construir una nueva*/
    char chrLetra;
    String strPalabra = "";
    /*Le añado un espacio al final para que el bucle recoja el último resultado*/
    strFrase += " ";
    ArrayList<String> aLstPalabras = new ArrayList<String>(0);
    /*A medida que salen las palabras, iré mirando su longitud y así no tengo
      que luego recorrer el ArrayList. Como puede ser que haya más de una con la
      misma longitud, voy a usar un ArrayList. Aquí sí. Aquí viene bien para
      poder añadir elementos sobre la marcha. O borrarlos: e.g., si hasta un
      cierto momento he encontrado, como más largas, dos palabras de 4 letras y
      luego encuentro una de 5, he de vaciar el ArrayList y guardar esta de 5 y
      alguna más si la hubiera. De hecho, aquí sí hace falta un ArrayList para
      guardar las más largas, pero guardarlas todas no sería necesario, pues
      sólo se pide contar las palabras y mostrar las más largas*/
    ArrayList<String> aLstMasLargas = new ArrayList<String>(0);
    int intLongMax = 0, intLongActual = 0;
    for (int i=0; i<strFrase.length(); i++) {
      chrLetra = strFrase.charAt(i);
      /*Fijarse que " " da error para un carácter, pero no ' '*/
      if (chrLetra == ' '){
        /*Al encontrar un espacio, tenemos nueva palabra, la añadimos al
          ArrayList*/ 
        aLstPalabras.add(strPalabra);
        /*Comparamos su longitud con la longitud máxima hallada hasta ese
          momento y procedemos en consecuencia. Al final, vaciamos la variable*/
        if (intLongActual > intLongMax) {
          aLstMasLargas.clear();
          aLstMasLargas.add(strPalabra);
          /*Actualizamos la longitud máxima*/
          intLongMax = intLongActual;
        } else if (intLongActual == intLongMax) {
          aLstMasLargas.add(strPalabra);
        }
        strPalabra = "";
        intLongActual = 0;
      } else {
        strPalabra += chrLetra;
        intLongActual += 1;
      }
    }
    //System.out.println(aLstPalabras);
    impln("\nLa frase que ha escrito contiene un total de ", abc(aLstPalabras.size()), " palabras");
    /*Miramos cuantas palabras tiene el ArrayList de palabras más largas*/
    if (aLstMasLargas.size() < 2) {
      impln("La palabra más larga de la frase es \"", aLstMasLargas.get(0), "\" y tiene ", abc(intLongMax), " caracteres");
    } else {
      System.out.println("Las palabras más largas de la frase son " + aLstMasLargas + " y tienen " + intLongMax + " caracteres");
    }
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
            AListNumeros();
            break;
          case "2":
            bolValida = true;
            PalabrasFrase();
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