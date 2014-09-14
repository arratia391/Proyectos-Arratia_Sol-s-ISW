

//                        Programación dinámica(b)
//      Ingresamos la dimención del arreglo e ingresamos los valores a almecenar en este.
//    GRUPO: SOMA



package programaciondinamica2;

import java.io.IOException;
import java.util.Scanner;

public class ProgramacionDinamica2 {
       
public static void main(String[] args) throws IOException {
        
        int Vector[] = new int[10000];
        int Auxiliar[] = new int[100000];
        int dim,suma = 0;
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese cantidad de valores a almacenar: ");
        dim = leer.nextInt();

        // Se llena el arreglo.
        for (int i = 0; i < dim; i++) {
            System.out.println("Ingrese Valor para la posición "+(i+1)+": " );
            Vector[i] = leer.nextInt();

        }
                

        //Sumar el arreglo
        for (int i = 0; i < dim; i++) {
            suma = Vector[i] + suma;
        }
        //Muestra la suma del arreglo
        for (int i = 0; i < dim - 1; i++) {
            System.out.print(Vector[i] + "+");
        }
        System.out.println(Vector[dim - 1] + "=" + suma);
        
// Primera condición de funcionamiento, si la sumatoria de los elementos de nuestro Vector 
// es impar, resulta imposible dividirlo en cantidades equivalentes; por ende
// el objetivo se ve imposibilitado.
        if (suma % 2 == 0) {
            int mitad = suma / 2; // almacenamos el valor medio de la sumatoria total de Vector
            int alternativas = dim;
            int aux = mitad;
            int pos = 0;
            int k = 0;
//mientras todavia tenga alternaticas de busqueda
            while (alternativas > 0) {
                alternativas--;
// Guardamos en una variable el primer valor de VECTOR, y procedemos a compararlo con cada elemento de este
                int mayor = Vector[0];
                for (int i = 0; i < dim; i++) {
                    if (Vector[i] <= aux && Vector[i] != -1) {// si el primer elemento de Vector es igual o menor a la (sumatoria/2) y además es diferente a -1, prosigue
                        if (Vector[i] > mayor) {
                            // Guardamos el elemento de mayor magnitud y su posición.
                            mayor = Vector[i]; 
                            pos = i;
                        }
                    }
                }
                Auxiliar[k] = mayor; //asignamos el numero a un arreglo de combinaciones
                k++;
                aux = aux - mayor;// Se resta la mitad por todos los valores que encuentre hasta llegar a 0
                if (aux == 0) {
                    System.out.println("Se encontraron 2 agrupaciones cuya sumatoria es de " + mitad +",\n Estas son:");// Si se cumplen las condiciones se imprime la 
                    
                    for (int j = 0; j < k; j++) {                                // la sumatoria de la división
                        System.out.print("-" + Auxiliar[j] + "-");
                    }
                    System.out.println("");
                    k = 0;
                    aux = mitad;
                }
                Vector[pos] = -1;// Una vez que ya utilizamos un numero lo etiquetamos (con un -1) para identidicar y no seguir operando con este.
            }
        } else {
            System.out.println("Dado a que la sumatoria del Vector es impar(" + suma + "), no es posible su agrupación.");
        }
         

    }
}
