package Grafo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grafo {

	//No respeto el encapsulamiento dado que es lo de MENOS en la resolucion de un ejercicio asi jaja.
	public int[][] matriz;
	public int cantNodos;
	public int cantAristas;
	public ArrayList<Nodo> nodos;
	public int[] gradosNodos;
	public int nodoSalida;
	public int nodoLlegada;
	public ArrayList<Arista> aristas = new ArrayList<Arista>();
	
	public Grafo(String path) throws FileNotFoundException 
	{
		//Leo el archivo.
		Scanner scan = new Scanner(new File(path));

		//Leo del archivo.
		this.cantNodos = scan.nextInt();
		//El numero de nodo es uno menos porque el numero de nodo 0 corresponde al numero de nodo 1 del problema y asi.
		this.nodoSalida = scan.nextInt()-1;
		this.nodoLlegada = scan.nextInt()-1;
		this.cantAristas = scan.nextInt();

		//Inicializacion de la matriz.
		this.inicializarMatriz();
		nodos = new ArrayList<Nodo>();
		gradosNodos = new int[this.cantNodos];

		for (int i = 0; i < this.cantNodos; i++)
			gradosNodos[i] = 0;
		
		//Voy poniendo las aristas.
		int n1;
		int n2;
		int peso;
		for (int i = 0; i < this.cantAristas; i++) {
			n1 = scan.nextInt()-1; //El nodo 10, es el nodo 9 y asi.
			n2 = scan.nextInt()-1; //El nodo 10, es el nodo 9 y asi.
			peso = scan.nextInt(); //El peso.
			this.gradosNodos[n1]++;
			this.gradosNodos[n2]++;
			this.ponerAristaPeso(n1, n2, peso); //Doble si no es dirigido.
			this.aristas.add(new Arista(n1, n2)); //Agrego la arista a la lista de aristas.
		}

		//Voy agregando los nodos.
		for (int i = 0; i < this.cantNodos; i++) {
			Nodo nodo = new Nodo(i);
			nodo.setGrado(this.gradosNodos[i]);
			this.nodos.add(nodo);
		}
		scan.close();
	}

	//Para Floyd debe inicializarce en INFINITO.
	private void inicializarMatriz() // Recibe el orden de la matriz
	{ 
		int n = this.cantNodos;
		matriz = new int[n][n];

		for (int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
			matriz[i][j] = 0;
	}

	public void ponerArista(int fil, int col) 
	{
		this.matriz[fil][col] = 1;
	}
	
	public void ponerAristaPeso(int fil, int col, int peso)
	{
		this.matriz[fil][col] = peso;
	}

	public boolean hayArista(int fil, int col) 
	{
		return this.matriz[fil][col] != 0;
	}
	
	public boolean hayAristaInversa(int fil, int col)
	{
		return this.matriz[col][fil] != 0;
	}
	
	public int getPesoArista(int fil, int col)
	{
		return this.matriz[fil][col];
	}
	
	public int getPesoAristaInverso(int fil, int col)
	{
		return this.matriz[col][fil];
	}
}
