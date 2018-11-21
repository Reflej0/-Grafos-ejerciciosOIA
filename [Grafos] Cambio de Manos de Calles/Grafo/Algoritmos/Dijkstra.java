package Algoritmos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import Grafo.Arista;
import Grafo.CostoAlNodo;
import Grafo.Grafo;

public class Dijkstra {

	private Grafo grafo;
	private int nodoInicial;

	private int cantNodos;
	boolean nodoTerminado[];
	private ArrayList<CostoAlNodo> costos;

	private static final int INFINITO = -1;

	public Dijkstra(Grafo grafo, int nodoInicial) 
	{
		this.grafo = grafo;
		this.nodoInicial = nodoInicial;
		this.cantNodos = grafo.cantNodos;
		this.nodoTerminado = new boolean[cantNodos];
		costos = new ArrayList<CostoAlNodo>();
	}

	@SuppressWarnings("unchecked")
	public void ejecutar() throws IOException 
	{
		Queue<CostoAlNodo> cola = new PriorityQueue<CostoAlNodo>();
		CostoAlNodo nodoActual = new CostoAlNodo(this.nodoInicial, 0);
		CostoAlNodo actualizado = null;
		cola.add(nodoActual);
		int nodo = nodoActual.getNodo();

		for (int i = 0; i < this.cantNodos; i++) 
		{
			if (i != nodo) 
				costos.add(new CostoAlNodo(i, INFINITO));
			else 
			{
				costos.add(new CostoAlNodo(i, 0));
				costos.get(i).agregarNodoAlCamino(nodo);
			}
		}

		while (!cola.isEmpty()) //Mientras que la cola no este vacia.
		{
			nodoActual = cola.poll();
			nodo = nodoActual.getNodo();
			
			for (int i = 0; i < this.cantNodos; i++) 
			{
				if (nodo != i && !this.nodoTerminado[i]) 
				{
					//Si encuentro una arista de i->j
					if (this.grafo.hayArista(nodo, i)) 
					{
						if (this.costos.get(i).getCostoMinimo() == INFINITO || this.costos.get(nodo).getCostoMinimo() + this.grafo.getPesoArista(nodo, i) < this.costos.get(i).getCostoMinimo()) {
							actualizado = this.costos.get(i);
							actualizado.setCostoMinimo(this.costos.get(nodo).getCostoMinimo() + this.grafo.getPesoArista(nodo, i));
							actualizado.setCaminoMasCorto((ArrayList<Integer>)this.costos.get(nodo).getCaminoMasCorto().clone());
							actualizado.agregarNodoAlCamino(i);
							
							if (!cola.contains(actualizado))
								cola.add(actualizado);
						}
					}
					//Si encuentro una arista de j->i
					//Porque el grafo es dirigido, pero a mi para el Dijsktra no me importa la direccion
					//Sino saber si hay una arista que conecta, aunque sea en sentido contrario.
					else if(this.grafo.hayAristaInversa(nodo, i))
					{
						if (this.costos.get(i).getCostoMinimo() == INFINITO || this.costos.get(nodo).getCostoMinimo() + this.grafo.getPesoAristaInverso(nodo, i) < this.costos.get(i).getCostoMinimo()) {
							actualizado = this.costos.get(i);
							actualizado.setCostoMinimo(this.costos.get(nodo).getCostoMinimo() + this.grafo.getPesoAristaInverso(nodo, i));
							actualizado.setCaminoMasCorto((ArrayList<Integer>)this.costos.get(nodo).getCaminoMasCorto().clone());
							actualizado.agregarNodoAlCamino(i);
							
							if (!cola.contains(actualizado))
								cola.add(actualizado);
						}
					}
				}
			}
			this.nodoTerminado[nodo] = true;
		}
		this.escribirSolucionEnConsola();
	}

	//Escribo la solucion y le doy una vuelta de rosca.
	private void escribirSolucionEnConsola() 
	{
		int costo, calle;
		for (int i = this.grafo.nodoLlegada; i <= this.grafo.nodoLlegada; i++) {
			costo = costos.get(this.grafo.nodoLlegada).getCostoMinimo();
			if (costo == INFINITO)
				System.out.println("No se puede llegar a la escuela.");
			else
			{
				System.out.println(costo); //El costo del camino mas corto, esto es puro Dijsktra sin respetar los sentidos.
				//Contiene los nodos incluyendo el inicial y el final del camino mas corto desde el inicial al final.
				ArrayList<Integer> caminoMasCorto = costos.get(this.grafo.nodoLlegada).getCaminoMasCorto();
				for(int j = 0; j < caminoMasCorto.size()-1; j++)
				{
					//Compruebo si estoy respetando bien el sentido de la calle.
					boolean respetar = this.grafo.hayArista(caminoMasCorto.get(j), caminoMasCorto.get(j+1));
					//Si no estoy respetando el sentido, es decir no encontre la arista.
					if(!respetar)
					{
						//Busco la posicion de la calle que tengo que invertirle el sentido.
						//Incremento uno porque la arista 0 de mi programa es la arista 1 del enunciado.
						calle = this.grafo.aristas.indexOf(new Arista(caminoMasCorto.get(j+1), caminoMasCorto.get(j)))+1;
						System.out.print(calle+" ");
					}	
				}	
			}	
		}
	}
	
}
