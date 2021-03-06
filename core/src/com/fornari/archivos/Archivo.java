package com.fornari.archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import com.fornari.casino.TipoJugador;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
    /**
     * Clase para el manejo de archivos
     * @author Carlos Fornari, Sandro Portanova, Maria Porras
     *
     */
public class Archivo {
	//Atributos privados de la clase
	private Arbol arbol;
	private BufferedWriter writer;
	private String nombresNodos[]= {"MAZO","MESA","JUGADOR","COMPUTADORA","SELECCIONADAS"};
	 /**
	  * Constructor de la clase
	  */
		public Archivo(){
			setArbol(null);
		}
		
	 //Metodos publicos
		
	 /**
	  * Comprueba que el archivo de texto exista	
	  * @return <ul>
      *  <li>true: el archivo existe</li>
      *  <li>false: el archivo no existe</li>
      *  </ul>
	  */
	public static boolean existeArchivo() {
		if(Paths.get(Config.pathArchivo).toFile().exists())
			return true;
		else
			return false;
	}
	 /**
	  * Borra el archivo de texto
	  */
	public static void borrar() {
		try {
			Files.deleteIfExists(Paths.get(Config.pathArchivo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * Vacia en el archivo toda la informacion de la partida
     * @param mazo
     * @param mesa
     * @param jugador
     * @param computadora
     * @param seleccionadas
     * @param turno
     * @param reparte
     * @param ultimoRecoger
     * @param ultimoJugar
     */
	public void vaciarArchivo(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas, boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar) {
			try {
				vaciarInformacion(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoRecoger, ultimoJugar);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//getArbol().imprimirArbol();
	}
	/**
	 * Llena el archivo con la informacion del juego
	 * @param mazo
	 * @param mesa
	 * @param jugador
	 * @param computadora
	 * @param seleccionadas
	 * @param turno
	 * @param reparte
	 * @param ultimoRecoger
	 * @param ultimoJugar
	 */
	public void cargarArchivo(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas, boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar) {
		if(existeArchivo())
			try {
				cargarInformacion(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoRecoger, ultimoJugar);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	/**
	 * Escribe el nombre de jugador en el archivo
	 * @return Devuelve el nombre del jugador
	 */
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
	/**
	 * LLeva la informacion desde el archivo hasta el Arbol
	 * @param mazo
	 * @param mesa
	 * @param jugador
	 * @param computadora
	 * @param seleccionadas
	 * @param turno
	 * @param reparte
	 * @param ultimoRecoger
	 * @param ultimoJugar
	 * @throws IOException
	 */
	public void vaciarInformacion(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas, boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar) throws IOException {
		if(getArbol()==null) { //Creo los nodos y los inserto en el arbol
			setArbol(new Arbol());
			getArbol().insertarArbol(new NodoArchivo(mazo, "MAZO", 1));
			getArbol().insertarArbol(new NodoArchivo(mesa, "MESA", 2));
			getArbol().insertarArbol(new NodoArchivo(jugador, "JUGADOR", 3));
			getArbol().insertarArbol(new NodoArchivo(computadora, "COMPUTADORA", 4));
			getArbol().insertarArbol(new NodoArchivo(seleccionadas, "SELECCIONADAS", 5));
			getArbol().insertarArbol(new NodoArchivo(turno, reparte, ultimoRecoger, ultimoJugar, "UPDATE", 6));
		} else {//Modifico ya los nodos dentro del arbol
			modificarMazo(mazo,mesa,seleccionadas);
			modificarJugador(jugador, "JUGADOR");
			modificarJugador(computadora, "COMPUTADORA");
			modificarUpdate(turno, reparte, ultimoRecoger, ultimoJugar, "UPDATE");
		}
		escribirArchivo(); 
	}
	/**
	 * Carga la informacion del arbol al archivo
	 * @param mazo
	 * @param mesa
	 * @param jugador
	 * @param computadora
	 * @param seleccionadas
	 * @param turno
	 * @param reparte
	 * @param ultimoRecoger
	 * @param ultimoJugar
	 * @throws IOException
	 */
	public void cargarInformacion(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas, boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar) throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader(Config.pathArchivo));
		setArbol(new Arbol());
		getArbol().insertarArbol(new NodoArchivo(transformarMazo(cargarMazo(reader)), "MAZO", 1) );
		getArbol().insertarArbol(new NodoArchivo(cargarMazo(reader), "MESA", 2));
		getArbol().insertarArbol(new NodoArchivo(cargarJugador(reader), "JUGADOR", 3) );
		getArbol().insertarArbol(new NodoArchivo(cargarJugador(reader), "COMPUTADORA", 4) );
		getArbol().insertarArbol(new NodoArchivo(cargarMazo(reader), "SELECCIONADAS", 5));
		getArbol().insertarArbol(new NodoArchivo(cargarBool(reader),cargarBool(reader),cargarTipoJugador(reader),cargarTipoJugador(reader), "UPDATE", 6));
		reader.readLine();
		Config.setNombre(reader.readLine());
	}
	
	/**
	 * Modifica los nodos de los mazos en el arbol
	 * @param mazo
	 * @param mesa
	 * @param seleccionadas
	 */
	public void modificarMazo(Mazo mazo,ArrayList<Carta> mesa, ArrayList<Carta> seleccionadas ) {
		getArbol().buscarNodoEnArbol("MAZO").setMazo(mazo);
		getArbol().buscarNodoEnArbol("MESA").setListaCarta(mesa);
		getArbol().buscarNodoEnArbol("SELECCIONADAS").setListaCarta(seleccionadas);
	}
	/**
	 * Modifica el nodo donde se guarda informacion del juego
	 * @param turno
	 * @param reparte
	 * @param ultimoRecoger
	 * @param ultimoJugar
	 * @param texto
	 */
	public void modificarUpdate(boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar, String texto) {
		getArbol().getRaiz().buscarNodo(texto).setTurno(turno);
		getArbol().getRaiz().buscarNodo(texto).setReparte(reparte);
		getArbol().getRaiz().buscarNodo(texto).setUltimoRecoger(ultimoRecoger);
		getArbol().getRaiz().buscarNodo(texto).setUltimoJugar(ultimoJugar);
	}
	/**
	 * Modifica el nodo donde se guarda informacion de un jugador	
	 * @param jugador
	 * @param texto
	 */
	public void modificarJugador(Jugador jugador, String texto) {
		Jugador jugadorAuxiliar=new Jugador();
		jugador=getArbol().getRaiz().buscarNodo(texto).getJugador();
		jugadorAuxiliar.setClarezas(jugador.getClarezas());
		jugadorAuxiliar.setIdEmparejamiento(jugador.getIdEmparejamiento());
	}
	/**
	 * Vacia todo el contenido de las cartas de un mazo en el archivo
	 * @param mazo
	 * @param writer
	 * @throws IOException
	 */
	public void vaciarTipoMazo(Mazo mazo, BufferedWriter writer) throws IOException {
		Carta carta=null;
		if(!mazo.estaVacio()) {
			carta=mazo.deleteTope();
			writer.write(carta.getValor()+"\n");
			writer.write(carta.getFigura()+"\n");
			writer.write(carta.getRepresentacion()+"\n");
			writer.write(carta.getPuntaje()+"\n");
			writer.write(carta.getIdEmparejamiento()+"\n");
			writer.write(carta.getsumaEmparejadas()+"\n");
			writer.write(booleanAInt(carta.isDoblada())+"\n");
			writer.write(booleanAInt(carta.isSelected())+"\n");
			vaciarTipoMazo(mazo, writer);
			mazo.setTope(carta);
		} else
		writer.write('/'+"\n");
	}
	/**
	 * Vacia todo el contenido de las cartas de un mazo en el archivo
	 * @param listaCartas
	 * @param writer
	 * @throws IOException
	 */
	public void vaciarMazo(ArrayList<Carta> listaCartas, BufferedWriter writer) throws IOException {
		for(Carta carta: listaCartas) {
			writer.write(carta.getValor()+"\n");
			writer.write(carta.getFigura()+"\n");
			writer.write(carta.getRepresentacion()+"\n");
			writer.write(carta.getPuntaje()+"\n");
			writer.write(carta.getIdEmparejamiento()+"\n");
			writer.write(carta.getsumaEmparejadas()+"\n");
			writer.write(booleanAInt(carta.isDoblada())+"\n");
			writer.write(booleanAInt(carta.isSelected())+"\n");
		}
		writer.write('/'+"\n");
	}
	/**
	 * Vacia todo el contenido de un jugador en el archivo
	 * @param jugador
	 * @param writer
	 * @throws IOException
	 */
	public void vaciarJugador(Jugador jugador,BufferedWriter writer) throws IOException {
		vaciarMazo(jugador.getCartas(),writer);
		vaciarMazo(jugador.getCartasRecogidas(),writer);
		writer.write(jugador.getClarezas()+"\n");
		writer.write(jugador.getIdEmparejamiento()+"\n");
		writer.write("//"+"\n");
	}
	/**
	 * Convierte un boolean a un entero
	 * @param boleano
	 * @return Devuelve un entero
	 */
	public int booleanAInt(boolean boleano) {
		int unInt= boleano ? 1 : 0;
		return unInt;
	}
	/**
	 * Convierte variable tipo string a un boolean
	 * @param numero
	 * @return Devuelve un boolean
	 */
	public boolean intABool(String numero) {
		boolean bool=(numero.equals("1")) ? true : false;
		return bool;
	}
	/**
	 * Vacia un boolean en el archivo de texto
	 * @param boleano
	 * @param writer
	 */
	public void vaciarBool(boolean boleano, BufferedWriter writer) throws IOException {
		writer.write(booleanAInt(boleano)+"\n");
		writer.write("//"+"\n");
	}
	/**
	 * Vacia una variable de TipoJugador en el arhivo de texto
	 * @param tipoJugador 
	 * @param writer
	 */
	public void vaciarTipoJugador(TipoJugador tipoJugador, BufferedWriter writer) throws IOException {
		writer.write(tipoJugador+"\n");
		writer.write("//"+"\n");
	}
	/**
	 * Vacia cada cada uno de los nodos del arbol en el archivo de texto
	 * 
	 */
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
		vaciarBool(getArbol().buscarNodoEnArbol("UPDATE").isTurno(), writer);
		vaciarBool(getArbol().buscarNodoEnArbol("UPDATE").isReparte(), writer);
		vaciarTipoJugador(getArbol().buscarNodoEnArbol("UPDATE").getUltimoRecoger(), writer);
		vaciarTipoJugador(getArbol().buscarNodoEnArbol("UPDATE").getUltimoJugar(), writer);
		writer.write("///"+"\n");
		writer.write(Config.userName);
		writer.close();
	}
	/**
	 * Crea una carta a medida que lee una linea del archivo de texto
	 * @param linea
	 * @param contador
	 * @param carta 
	 * @return 
	 */
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
	/**
	 * Convierte dado un texto un string a un TipoJugador
	 * @param texto
	 * @return Devuelve un TipoJugador
	 */
	public TipoJugador convertirTipoJugador(String texto) {
		if(texto.equals("jugador"))
			return TipoJugador.jugador;
		if(texto.equals("computadora"))
			return TipoJugador.computadora;
		return TipoJugador.none;
	}
	/**
	 * Carga del archivo la informacion de un TipoJugador 
	 * @param  reader
	 * @return Devuelve un TipoJugador 
	 */
	public TipoJugador cargarTipoJugador(BufferedReader reader) throws IOException {
		TipoJugador tipo;	
		String linea="";
		linea=reader.readLine();
		tipo=convertirTipoJugador(linea);
		reader.readLine();
		return tipo;
	}
	/**
	 * Carga del archivo la informacion de un boolean
	 * @param reader
	 * @return Devuelve un boolean
	 */
	public boolean cargarBool(BufferedReader reader) throws IOException {
		boolean bool=true;
		String linea="";
		linea=reader.readLine();
		bool=intABool(linea);
		reader.readLine();
		return bool;
	}
	/**
	 * Carga del archivo la informacion de una lista de cartas
	 * @param reader
	 * @return Devuelve una lista de cartas
	 */
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
	/**
	 * Carga del archivo la informacion de un solo jugador
	 * @param reader
	 * @return Devuelve un Jugador
	 */
	public Jugador cargarJugador(BufferedReader reader) throws IOException {
		Jugador jugador=new Jugador();
		jugador.setCartas(cargarMazo(reader));
		jugador.setCartasRecogidas(cargarMazo(reader));
		jugador.setClarezas(Integer.parseInt(reader.readLine()));
		jugador.setIdEmparejamiento(reader.readLine());
		reader.readLine();
		return jugador;
	}
	/**
	 * Convierte una variable tipo ArrayList a Mazo
	 * @param listaCartas
	 * @return Devuelve un Mazo
	 */
	public Mazo transformarMazo(ArrayList<Carta> listaCartas) {
		Mazo mazo=new Mazo(true);
		for(Carta carta: listaCartas) 
			mazo.setTope(carta);
		return mazo;
	}
	/**
	 * Convierte dado un texto a un tipo Figuras
	 * @param texto
	 * @return Devuelve una Figuras
	 */
	public Figuras retornaFigura(String texto) {
		if(texto.equals("espada"))
			return Figuras.espada;
		if(texto.equals("trebol"))
			return Figuras.trebol;
		if(texto.equals("corazon"))
			return Figuras.corazon;
		if(texto.equals("diamante"))
			return Figuras.diamante;
		return Figuras.none;
	}
	/**
	 * Devuelve el nodo del arbol
	 * @return Devuelve el arbol
	 */
	public Arbol getArbol() {
		return arbol;
	}
	/**
	 * 
	 * @param arbol
	 */
	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}


}
