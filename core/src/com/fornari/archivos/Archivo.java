package com.fornari.archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fornari.casino.Carta;
import com.fornari.casino.Figuras;
import com.fornari.casino.Jugador;
import com.fornari.casino.Mazo;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;

public class Archivo {
	private Arbol arbol;
	private BufferedWriter writer;
	private String nombresNodos[]= {"MAZO","MESA","JUGADOR","COMPUTADORA","SELECCIONADAS"};
	
		public Archivo(){
			setArbol(null);
		}
		
	public static boolean existeArchivo() {
		if(Paths.get(Config.pathArchivo).toFile().exists())
			return true;
		else
			return false;
	}
	
	public static void borrar() {
		try {
			Files.deleteIfExists(Paths.get(Config.pathArchivo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void borrarArchivo() {
		File file=new File(Config.pathArchivo);
		
		if(file.delete())
			System.out.println("BORRADO");
		else
			System.out.println("NO BORRADO");
	}

	public void vaciarArchivo(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas) {
			try {
				vaciarInformacion(mazo, mesa, jugador, computadora, seleccionadas);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			getArbol().imprimirArbol();
	}
	
	public void cargarArchivo(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas) {
		if(existeArchivo())
			try {
				cargarInformacion(mazo, mesa, jugador, computadora, seleccionadas);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		System.out.println("PARTIDA CARGADA: ");
		getArbol().imprimirArbol();
	}
	
	public static String nombreUsuario() {
		String linea="";
		File file= new File(Config.pathArchivo);
		try {
			if(existeArchivo()) {
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				while(!(linea=raf.readLine()).equals("///"))
					System.out.println("");
				linea=raf.readLine();
				raf.seek(0);
				raf.close();
				return linea;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return linea;
	}
	
	public void vaciarInformacion(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas) throws IOException {
		if(getArbol()==null) { //Creo los nodos y los inserto en el arbol
			setArbol(new Arbol());
			getArbol().insertarArbol(new NodoArchivo(mazo, "MAZO", 1));
			getArbol().insertarArbol(new NodoArchivo(mesa, "MESA", 2));
			getArbol().insertarArbol(new NodoArchivo(jugador, "JUGADOR", 3));
			getArbol().insertarArbol(new NodoArchivo(computadora, "COMPUTADORA", 4));
			getArbol().insertarArbol(new NodoArchivo(seleccionadas, "SELECCIONADAS", 5));
		} else {//Modifico ya los nodos dentro del arbol
			modificarMazo(mazo,mesa,seleccionadas);
			modificarJugador(jugador, "JUGADOR");
			modificarJugador(computadora, "COMPUTADORA");
		}
		escribirArchivo(); 
	}
	
	public void cargarInformacion(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas) throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader(Config.pathArchivo));
		setArbol(new Arbol());
		getArbol().insertarArbol(new NodoArchivo(transformarMazo(cargarMazo(reader)), "MAZO", 1) );
		getArbol().insertarArbol(new NodoArchivo(cargarMazo(reader), "MESA", 2));
		getArbol().insertarArbol(new NodoArchivo(cargarJugador(reader), "JUGADOR", 3) );
		getArbol().insertarArbol(new NodoArchivo(cargarJugador(reader), "COMPUTADORA", 4) );
		getArbol().insertarArbol(new NodoArchivo(cargarMazo(reader), "SELECCIONADAS", 5));
		reader.readLine();
		Config.setNombre(reader.readLine());
		System.out.println("USUARIO: "+Config.userName);
		
	}
	
	
	public void modificarMazo(Mazo mazo,ArrayList<Carta> mesa, ArrayList<Carta> seleccionadas ) {
		getArbol().buscarNodoEnArbol("MAZO").setMazo(mazo);
		getArbol().buscarNodoEnArbol("MESA").setListaCarta(mesa);
		getArbol().buscarNodoEnArbol("SELECCIONADAS").setListaCarta(seleccionadas);
	}
		
	public void modificarJugador(Jugador jugador, String texto) {
		Jugador jugadorAuxiliar=new Jugador();
		jugador=getArbol().getRaiz().buscarNodo(texto).getJugador();
		jugadorAuxiliar.setClarezas(jugador.getClarezas());
		jugadorAuxiliar.setIdEmparejamiento(jugador.getIdEmparejamiento());
	}
	
	public int escribirBoolean(boolean boleano) {
		if(boleano) return 1;
		else return 0;
	}
	
	public void vaciarTipoMazo(Mazo mazo, BufferedWriter writer) throws IOException {
		Carta carta=null;
		if(!mazo.estaVacio()) {
			carta=mazo.desapilar();
			writer.write(carta.getValor()+"\n");
			writer.write(carta.getFigura()+"\n");
			writer.write(carta.getRepresentacion()+"\n");
			writer.write(carta.getPuntaje()+"\n");
			writer.write(carta.getIdEmparejamiento()+"\n");
			writer.write(carta.getsumaEmparejadas()+"\n");
			writer.write(escribirBoolean(carta.isDoblada())+"\n");
			writer.write(escribirBoolean(carta.isSelected())+"\n");
			vaciarTipoMazo(mazo, writer);
		}
		writer.write('/'+"\n");
	}
	
	public void vaciarMazo(ArrayList<Carta> listaCartas, BufferedWriter writer) throws IOException {
		for(Carta carta: listaCartas) {
			writer.write(carta.getValor()+"\n");
			writer.write(carta.getFigura()+"\n");
			writer.write(carta.getRepresentacion()+"\n");
			writer.write(carta.getPuntaje()+"\n");
			writer.write(carta.getIdEmparejamiento()+"\n");
			writer.write(carta.getsumaEmparejadas()+"\n");
			writer.write(escribirBoolean(carta.isDoblada())+"\n");
			writer.write(escribirBoolean(carta.isSelected())+"\n");
		}
		writer.write('/'+"\n");
	}
	
	public void vaciarJugador(Jugador jugador,BufferedWriter writer) throws IOException {
		vaciarMazo(jugador.getCartas(),writer);
		vaciarMazo(jugador.getCartasRecogidas(),writer);
		writer.write(jugador.getClarezas()+"\n");
		writer.write(jugador.getIdEmparejamiento()+"\n");
		writer.write("//"+"\n");
	}
		
	public void escribirArchivo() throws IOException {
		this.writer=new BufferedWriter(new FileWriter("ARCHIVO.txt"));
		NodoArchivo nodo=new NodoArchivo();
		for(String nodosArbol: this.nombresNodos) {
			nodo=getArbol().buscarNodoEnArbol(nodosArbol);
			if(nodo.getJugador()==null) {
				if(nodo.getMazo()==null)
					vaciarMazo(nodo.getListaCarta(), writer);
				else
					vaciarTipoMazo(nodo.getMazo(),writer);
			}
			 else
				 vaciarJugador(nodo.getJugador(),writer);
		}
		writer.write("///"+"\n");
		writer.write(Config.userName);
		writer.close();
	}

	public Carta crearCarta(String linea, int contador,Carta carta) {
		boolean boleano;
		switch(contador) {
		case 1: carta.setValor(Integer.parseInt(linea));
			break;
		case 2: carta.setFigura(retornaFigura(linea));
			break;
		case 3: carta.setRepresentacion(linea.charAt(0));
			break;
		case 4: carta.setPuntaje(Integer.parseInt(linea));
			break;
		case 5: carta.setIdEmparejamiento(linea);
			break;
		case 6: carta.setSumaEmparejadas(Integer.parseInt(linea));
			break;
		case 7: boleano=Boolean.parseBoolean(linea);
				carta.setDoblada(boleano);
			break;
		case 8: boleano=Boolean.parseBoolean(linea);
				carta.setSelected(boleano);
			break;
		}
		return carta;
	}
	
	public ArrayList<Carta> cargarMazo(BufferedReader reader) throws IOException {
		ArrayList<Carta> listaCarta = new ArrayList<Carta>();
		String linea;
		int contarLinea=1;
		Carta carta=new Carta();
		while(!((linea=reader.readLine()).equals("/"))) {
			carta=crearCarta(linea,contarLinea,carta);
			++contarLinea;
			if(contarLinea==9) {
				carta.setImagen(new Imagen(carta.buildPath(),"btn"));
				listaCarta.add(carta);
				carta=new Carta();
				contarLinea=1;
			}
		}
		return listaCarta;
	}
	
	public Jugador cargarJugador(BufferedReader reader) throws IOException {
		Jugador jugador=new Jugador();
		jugador.setCartas(cargarMazo(reader));
		jugador.setCartasRecogidas(cargarMazo(reader));
		jugador.setClarezas(Integer.parseInt(reader.readLine()));
		jugador.setIdEmparejamiento(reader.readLine());
		reader.readLine();
		return jugador;
	}
	
	public Mazo transformarMazo(ArrayList<Carta> listaCartas) {
		Mazo mazo=new Mazo(true);
		for(Carta carta: listaCartas) 
			mazo.apilar(carta);
		return mazo;
	}
	

	public Figuras retornaFigura(String texto) {
		Figuras figura = null;
		if(texto.equals("espada"))
			return figura.espada;
		if(texto.equals("trebol"))
			return figura.trebol;
		if(texto.equals("corazon"))
			return figura.corazon;
		if(texto.equals("diamante"))
			return figura.diamante;
		return figura.none;
	}

	public Arbol getArbol() {
		return arbol;
	}

	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}


}
