package Pruebas;

import java.io.IOException;

import Algoritmos.Dijkstra;
import Grafo.Grafo;

public class Main {

	public static void main(String[] args) throws IOException 
	{
		Grafo g = new Grafo("./in/ejercicio.in");
		Dijkstra algoritmo = new Dijkstra(g, g.nodoSalida);
		algoritmo.ejecutar();
	}

}
