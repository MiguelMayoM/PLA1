package PLA1_ejercicios;
/*
  ENUNCIADOS
  ==========
  El usuario introducirá un texto largo por la consola.
  El programa almacenará cada frase en un ArrayList. Frase es todo lo que esté
  separado por un punto.  
  En otro ArrayList se van a almacenar todas las palabras del texto. Palabra es
  todo lo que está separado por espacios.
  
  Después de almacenar estos datos el programa nos mostrará la lista de las
  frases ordenadas de la más corta a la más larga (en número de letras) y las
  palabras en orden de las más frecuentes a las menos, indicando para cada
  palabra su número de apariciones. Si varias palabras tienen el mismo número de
  apariciones se utilizará el orden alfabético.

  Se puede ordenar con algoritmos como burbuja o Quicksort, o usar comparadores
  propios en las funciones sort de ArrayList. También expresiones Lambda.
*/

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
/*Estas dos no se usan directamente sino como otra posibilidad que se deja ahí
  colgada pero sin comentar porque no afecta y siempre es más agradable ver el
  código sin omentar*/
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collections;
import java.util.Comparator;

public class PLA1_ej1_AnalisisTexto {
  static final String strTxtPrueba = "¡Hacía un buen día! Y ya no diluviaba. Las mariposas revoloteaban, sobre los campos en flor.  " +
                                     " Los pajaritos inundaban el aire con sus trinos: ¡qué hermoso canto! " +
                                     "¿Y los mosquitos? Los mosquitos... se merendaban mis brazos; me los llenaban de ronchas.";
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
  
  public static void main(String[] args) {
    impln("Análisis de un texto\n",
          "====================\n",
          " + Orden de frases por longitud\n",
          " + Orden de palabras según frecuencia\n");
    impln("Texto de prueba: ", strTxtPrueba, "\n");

    impln("Escriba el texto que desee analizar o pulse Enter para usar el texto de prueba:");
    String strTexto = scnEntrada.nextLine().trim(); 
    if (strTexto.equals("")) {
      strTexto = strTxtPrueba;
    }
    /*Consideraciones:
      - Además del punto, también tendré en cuenta, como limitadores de frases,
        los signos de puntuación ;:¡!¿?
      - Una frase podrá tener comas interiores
      - Guardaré todos los signos de puntuación para escribir la frase de forma
        más auténtica.
      - Pero no contaré los signos de puntuación externos para la longitud de la
        frase. Las comas, sí.
      - Hay signos de puntuación que delimitan por delante y por detrás, como ¿?
        o ¡!
    */
    /*--------------------------*/
    /*SEPARAR EL TEXTO EN FRASES*/
    /*--------------------------*/
    /*Voy a meter las frases en un arrayList. Ahora lo voy a hacer directamente,
      con un split (en el ejercicio anterior lo dejé comentado), y no recorriendo
      todo el texto hallando los separadores (signos de puntuación) e
      introduciendo las frases una a una en el ArrayList, porque ya antes lo
      hice así.
      + Para eliminar los espacios extra que suele haber después de un punto u
        otro signo, voy a eliminarlos con una regex: 
          replaceAll("([\\.;:?!])\\s*", "$1") 
           []   cualquier símbolo que sea un .;:?! (el punto es carácter especial y hay que escaparlo)
          (  )  los uso para capturar el grupo y usarlo después
          \\s*  seguido por 0 o 1 (*) espacios (\\)
          $1    y lo sustituyo por sólo el signo ortográfico capturado en el primer grupo 
      
      + Para conservar los signos de puntuación en el split, también utilizo una
        regex que conserva los delimitadores, usando lo que se llama Lookaround.
        En vez de usar un caracter como divisor, e.g. el punto en "a.b", dando
        lugar "a" y "b", se usaría lo que es el "espacio, que no existe como tal,
        entre dos caracteres". Así, uso por ejemplo un Lookbehind para decir que
        se ponga entre el punto y la "b", y mire lo que hay antes del punto y
        separe por ahí, conservando obviamente el punto. Usaré dos tipos de
        Lookarounds:
        - Lookbehind (?<=regex) para ponerme justo detrás de un [\\.;:?!] y que
          los tome formando parte del split anterior.
        - Lookahead (?!regex) negado con ! para que los caracteres anteriores,
          en concreto el punto, no vayan seguidos de más puntos, y así ver los
          ... como una sola cosa en el Lookbehind anterior y que al hacer el
          split no me parta cada punto por separado. 
          OJO: Si usara simplemente [^\\.], detectaría bien los 3 puntos, pero
          se estaría cargando la primera letra de cada frase.
    */

    ArrayList<String> aLstFrases = new ArrayList<String>(
      Arrays.asList(strTexto.replaceAll("([\\.;:?!])\\s*", "$1").split("(?<=[\\.;:?!])(?!\\.)")));
    //for (String i: aLstFrases){impln("1"+i+"1");}
    
    /******************************* OTRA FORMA *******************************/
    /*Usar un pattern y matcher y buscar con .find una a una, metiendo las
      ocurrencias en el ArrayList con add. Primero quito espacios externos como
      arriba*/
    String strTexto2 = strTexto.replaceAll("([\\.;:?!])\\s*", "$1");
    /*La regex para delimitar las frases se divide en:
      + Ubicarme al principio de todo lo que pueda ser frase. 
        ^ Principio de línea(para localizar la primera frase de una línea)
        | o   [\\s\\.;:?!] un carácter de finalización de la frase anterior,
        como .;?! Y con la posibilidad que haya un espacio antes de empezar la
        siguiente frase. Todo ello entre [] por la disyunción |
      + Gran grupo hasta el final de la regex (). Capturar [^\\.;:?!] caracteres
        que no sean estos (i.e. letras o números, o incluso - para palabras
        compuestas) y que se encuentren 1 o más veces(+) y que vayan seguidos de
        un sigo de final de frase de estos [;:?!] o | de 1 a 3 puntos [\\.]{1,3}
        puntos. La disyunción entre (), con [] no funciona.
    */
    Pattern patron = Pattern.compile("[^|[\\s\\.;:?!]]([^\\.;:?!]+([;:?!]|[\\.]{1,3}))");
    Matcher buscador = patron.matcher(strTexto2);
    ArrayList<String> aLstFrases2 = new ArrayList<String>(0);
    while (buscador.find()) {
      aLstFrases2.add(buscador.group());
      //impln("2"+buscador.group()+"2");
    }
    /**************************************************************************/
    
    /*--------------*/
    /*ORDENAR FRASES*/
    /*--------------*/
    /*Ahora que tenemos las frases, vamos ordenarlas por longitud (sin contar
      los signos de puntuación), de menor a mayor, y en caso de empate por orden
      alfabético. Ya usé en otro ejercicio el método de la burbuja. Ahora voy a
      usar sort() y proporcionarle dos COMPARADORES.
    */
    
    /*Puedo crear el comparador de la LONGITUD así*/
    Comparator<String> CompLongitud = new Comparator<String>() {
      /*Me pedía ponerlo, supongo que es un aviso de modificación o definición
        del método*/
      @Override
        /*Darse cuenta que la interface ya tiene unos nombres para los métodos que he
          de rellenar, así "compare" no lo he elegido yo*/  
        public int compare(String strF1, String strF2) {
            /*Antes de calcular length, elimino los signos de puntuación*/
            return strF1.replaceAll("[\\.:;¿?¡!]","").length() - strF2.replaceAll("[\\.:;¿?¡!]","").length();
            /*O también*/
            //return Integer.compare(strF1.replaceAll("[\\.:;¿?¡!]","").length(), strF2.replaceAll("[\\.:;¿?¡!]","").length());
            /*Si queremos invertir el orden, es tan sencillo como intercambiar
              el argumento strF1 con strF2, hacer la resta al revés*/
        }
    };
 
    /*Y para el comparador ALFABÉTICO, voy a simplificar con una expresión LAMBDA*/
    Comparator<String> CompAlfabetico = (String strF1, String strF2) ->
      /*Para comparar alfabéticamente, también quitamos los signos de puntuación
        que pudiera haber al principio, y ponemos todo el texto en minúsculas o
        mayúsculas*/
      strF1.replaceAll("[\\.:;¿?¡!]","").toLowerCase().compareTo(strF2.replaceAll("[\\.:;¿?¡!]","").toLowerCase());
 
    /*Uso el método sort() de Collections (no puedo usar el de Arrays porque el
      ArrayList no es un array) y encadeno ambas comparaciones*/
    Collections.sort(aLstFrases, CompLongitud.thenComparing(CompAlfabetico));

    /*También se podía hacer directamente...*/
    //Collections.sort(aLstFrases2, new java.util.Comparator<String>() {
    //  @Override 
    //  public int compare(String strF1, String strF2) {
    //    return strFrase1.replaceAll("[\\.:;¿?¡!]","").length() - strFrase2.replaceAll("[\\.:;¿?¡!]","").length();
    //  }  
    //});
    /*Aunque el propio IDE te sugiere que, en vez de hacer una "anonymous inner
      class creation", lo hagas con una expresión Lambda, e.g.*/      
    //Collections.sort(aLstFrases, (a, b) -> a.replaceAll("[\\.:;¿?¡!]","").length() - b.replaceAll("[\\.:;¿?¡!]","").length());
    
    /*Otro comentario: si hubiera querido usar el método sort() de Arrays,
      debería haber convertido primero el ArrayList a Array. En esta operación
      le daría el tamaño adecuado al array con .size() del ArrayList*/
    //String arrFrases[] = aLstFrases.toArray(new String[aLstFrases.size()]);
    //Arrays.sort(arrFrases, (a, b) -> Integer.compare(a.replaceAll("[\\.:;¿?¡!]","").length(), b.replaceAll("[\\.:;¿?¡!]","").length()));
    
    /*Y sólo queda imprimir por pantalla*/
    impln("Las frases ordenadas por longitud de menor a mayor y alfabéticamente, sin contar los signos de puntuación, es:\n",
          "--------------------------------------------------------------------------------------------------------------");
    aLstFrases.forEach(System.out::println);
    //for (String i: aLstFrases){impln("1"+i+"1");}
 
    /*--------------*/
    /*ORDEN PALABRAS*/
    /*--------------*/
    /*Extraigo todas las palabras del texto inicial. Ahora también elimino las
      comas. Además, aprovecho para eliminar todos los espacios extra que
      pudiera haber. Y para convertir a minúsculas todo, para que no considere
      una palabra distintas si está a principio de frase*/
    ArrayList<String> aLstPalabrasRep = new ArrayList<String>(
      Arrays.asList(strTexto.replaceAll("[\\.;:¿?¡!]", "").replaceAll("[\\s]+"," ").toLowerCase().split(" ")));
    /*Según las instrucciones, creo otro ArrayList con las palabras sin repetir,
      recorriendo el anterior*/
    ArrayList<String> aLstPalabras = new ArrayList<String>(0);
    for (String strPalabra : aLstPalabrasRep) {
      if (!aLstPalabras.contains(strPalabra.toLowerCase())) {
        aLstPalabras.add(strPalabra);
      } else {
        continue;
      }
    }
    
    /*Como ya utilicé algoritmo de burbuja en un ejercicio anterior y aquí me
      he puesto a usar comparadores, voy a crear ahora uno que ordene en función
      de la aparición de una palabra en el ArrayList del total de palabras con
      repeticiones, esto es, el aLstPalabrasRep*/
    Comparator<String> CompFrecuencia = (String strP1, String strP2) -> {
      int intFrecP1 = Collections.frequency(aLstPalabrasRep, strP1);
      int intFrecP2 = Collections.frequency(aLstPalabrasRep, strP2);
      /*Fijémonos que hemos cambiado el orden para que ordene de mayor a menor*/
      return intFrecP2 - intFrecP1;
    };
    
    /*Aplicando el comparador, y concatenando el que había creado para el orden
      alfabético de las frases*/
    Collections.sort(aLstPalabras, CompFrecuencia.thenComparing(CompAlfabetico));
    //aLstPalabras.forEach(System.out::println);
    
    /*Ahora ya tengo ordenadas las palabras del aLstPalabras por frecuencia de
      mayor a menor según aparecen en aLstPalabrasRep. Esto es estupendo para
      imprimirlas por orden de frecuencia, pero me falta saber la frecuencia
      para cada palabra, cosa que no he calculado directamente en ningún momento
      y por eso no he guardado estos datos, aunque internamente los comparadores
      sí que lo han hecho, claro. Pues simplemente voy leyendo cada palabra del
      ArrayList de palabras sin repetir y que ya están ordenadas, y busco su
      frecuencia en el ArrayList de palabras con repeticiones. Por simplicidad,
      vuelvo a usar el método frequency de Collections para hallar la frecuencia
      de cada palabra de aLstPalabras en aLstPalabrasRep. Guardo el primer valor
      en una variable, y, cada vez que este cambie, lo actualizo y muestro una
      nueva cabecera que indique el número de veces que se repiten las palabras
      que escribiré a continuación*/
    imp("\nLas palabras ordenadas por frecuencia de mayor a menor y alfabéticamente, con su número de apariciones, es:\n",
          "-----------------------------------------------------------------------------------------------------------");
    
    int intFrecAnt = -1 ,intFrecAct;   
    for (String strP : aLstPalabras) {
      intFrecAct = Collections.frequency(aLstPalabrasRep, strP);
      if (intFrecAct != intFrecAnt) {
        /*Lo actualizo y escribo por pantalla*/
        intFrecAnt = intFrecAct;
        if (intFrecAct != 1) {
          imp("\n", abc(intFrecAct), " veces: ", strP);
        } else {
         imp("\n1 vez: ", strP);         
        }  
      } else {
        imp(", ", strP);
      }
    }
    impln();
  }  
}