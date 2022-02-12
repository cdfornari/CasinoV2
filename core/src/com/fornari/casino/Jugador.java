package com.fornari.casino;
import java.util.ArrayList;

import com.fornari.utils.Imagen;

public class Jugador {
	private ArrayList<Carta> cartas;
	private ArrayList<Carta> cartasRecogidas;
	private int clarezas;
	private String idEmparejamiento;
	
	public Jugador() {
		this.cartas = new ArrayList<Carta>();
		this.cartasRecogidas = new ArrayList<Carta>();
		this.clarezas = 0;
		this.idEmparejamiento = "000";
	}
	
	public ArrayList<Carta> getCartas(){
		return this.cartas;
	}
	
	public ArrayList<Carta> getCartasRecogidas() {
		return this.cartasRecogidas;
	}
	
	public int getClarezas() {
		return this.clarezas;
	}
	
	public void addClareza() {
		this.clarezas++;
	}
	
	public String getIdEmparejamiento() {
		return idEmparejamiento;
	}
	
	public void setIdEmparejamiento(String id) {
		this.idEmparejamiento = id;
	}
	
	public void unselectAll() {
		for(int i = 0; i < cartas.size(); i++) {
			if(cartas.get(i).isSelected())
				cartas.get(i).toggleSelected();
		}
	}
	
	public PuntajeJugador contarPuntaje() {
		PuntajeJugador puntaje = new PuntajeJugador(cartasRecogidas.size(), clarezas);
		for(Carta c : cartasRecogidas) {
			puntaje.sumarPuntaje(c.getPuntaje());
			if (c.getFigura() == Figuras.espada)
				puntaje.addEspada();
		}
		return puntaje;
	}
	
	public void asignarCartasSobrantes(ArrayList<Carta> mesa) {
		
	}
	
	public void lanzarCarta(ArrayList<Carta> mesa, int i) {
		mesa.add(cartas.get(i));
		cartas.remove(cartas.get(i));
	}
	
	//Funciones de validacion
	
	public int contarCartas(Carta cartaBuscar, Jugador jugador) {
		int x=0;
		ArrayList<Carta> listaCartas=new ArrayList<Carta>(jugador.getCartas());
		for(Carta listaCarta: listaCartas) {
			if(listaCarta.getValor()==cartaBuscar.getValor())
				++x;
		}
		return x;
	}
	
	public boolean validarCartasRecoger(ArrayList<Carta> cartasARecoger, Carta cartaJugador) {
		boolean figuraSeleccionada=true, doblada=false;
		int contarFiguras=0, sumaNoEmparejadas=0, sumaEmparejadas=0, sumaTotal=0, sumaEsperada;
		Carta cartaConId=null;
		String id1="000",id2="000";
		
		if(cartaJugador.getValor()<=10) 
			figuraSeleccionada=false;
		if(figuraSeleccionada) { //Recoger figuras
			for(Carta carta: cartasARecoger) {
				if(carta.getValor()<=10) //No se puede usar una figura para recoger numero
					return false;
				if(carta.getValor()!=cartaJugador.getValor()) //Todas las figuras deben ser iguales
					return false;
				else
					++contarFiguras;
			}
			if(contarFiguras==2) //No se pueden recoger 3 figuras
				return false;
		} else { //Recoger numeros
				for(Carta carta: cartasARecoger) {
					if(carta.getValor()>10) //No se puede usar un numero para recoger una figura
						return false;
					if(!carta.getIdEmparejamiento().equals("000")) {
						if(id1.equals("000"))
							id1=carta.getIdEmparejamiento();
						else if(id2.equals("000") && !id1.equals(carta.getIdEmparejamiento()))
							id2=carta.getIdEmparejamiento();
							sumaEmparejadas+=carta.getValor(); //Sumo todas las emparejadas
						cartaConId=carta; //Obtengo una carta que tenga el id para conseguir sumaEmparejadas
						if(carta.isDoblada()) //Veo si esta doblado el emparejamiento
							doblada=true;
					} else
					sumaNoEmparejadas+=carta.getValor(); //Sumo las no emparejadas para la suma final
				}
				if(id1.equals("000") && id2.equals("000")) { //Si no hay emparejamientos
					if(sumaNoEmparejadas>10) //La suma no puede exceder de 10
						return false;
					if(cartaJugador.getValor()!=sumaNoEmparejadas) //La carta no es de igual valor a la suma
						return false;
					if(!this.getIdEmparejamiento().equals("000") && contarCartas(cartaJugador, this)<2) //No tienes para recoger tu emparejamiento activo
						return false;
			    }else if(!id1.equals("000") && !id2.equals("000") && !id1.equals(id2) ) //No puedes porque son de emparejamiento distintos
				   return false;
			    else {
			    	sumaEsperada=cartaConId.getsumaEmparejadas();
					if(doblada) sumaEsperada=sumaEsperada*2;
			    	if(sumaEsperada!=sumaEmparejadas) //Deben estar seleccionadas todas las cartas del emparejamiento
			    		return false;
			    	if(doblada)
			    		sumaEmparejadas=sumaEmparejadas/2;
			    	sumaTotal=sumaNoEmparejadas+sumaEmparejadas;
			    	if(sumaTotal>10) //No puede ser mayor de 10
			    		return false;
			    	if(sumaTotal!= cartaJugador.getValor()) //No es la carta correcta para recoger
			    		return false;
			    	if(!this.getIdEmparejamiento().equals("000") && !cartaConId.getIdEmparejamiento().equals(this.getIdEmparejamiento())) { //No tiene carta para recoger su emparejamiento activo
						if(contarCartas(cartaJugador, this)<2)
							return false;
			    	}
			    }
		}
		return true;
	}
	
	public boolean validarCartasEmparejar(ArrayList<Carta> cartasAEmparejar, Carta cartaJugador) {
		boolean figuraSeleccionada=true;
		int contarFiguras=0, sumaNoEmparejadas=0, sumaEmparejadas=0, sumaTotal=0;
		Carta cartaConId=null;
		String id1="000",id2="000";
		Carta cartaVerificar = new Carta();
		
		if(cartaJugador.getValor()<=10) figuraSeleccionada=false;
		if(!this.getIdEmparejamiento().equals("000")) //No puedes hacer otro emparejamiento porque ya tienes uno
			return false;
		if(figuraSeleccionada) { //Emparejar figuras
			for(Carta carta: cartasAEmparejar) {
				if(carta.getValor()<=10) //No se puede usar una figura para emparejar numero
					return false;
				if(carta.getValor()!=cartaJugador.getValor()) //Todas las figuras deben ser iguales
					return false;
				else
					++contarFiguras;
			}
			if(contarFiguras==3 || contarFiguras==1) //No se pueden emparejar 3 o 2 figuras
				return false;
			cartaVerificar.setValor(cartaJugador.getValor());
			if(contarCartas(cartaVerificar, this)<2) //No tiene carta para recoger emparejamiento
	    		return false;
		} else { //Recoger numeros
			for(Carta carta: cartasAEmparejar) {
				if(carta.getValor()>10) //No se puede usar un numero para emparejar una figura
					return false;
				if(!carta.getIdEmparejamiento().equals("000")) {
					if(id1.equals("000"))
						id1=carta.getIdEmparejamiento();
					else if(id2.equals("000") && !id1.equals(carta.getIdEmparejamiento()))
						id2=carta.getIdEmparejamiento();
					if(cartasAEmparejar.size()!=1)
						sumaEmparejadas+=carta.getValor(); //Sumo todas las emparejadas
					else
						sumaEmparejadas=carta.getsumaEmparejadas(); //Sumo todas las emparejadas
					cartaConId=carta; //Obtengo una carta que tenga el id para conseguir sumaEmparejadas
				} else
					sumaNoEmparejadas+=carta.getValor(); //Sumo las no emparejadas para la suma
				if(carta.isDoblada()) //No puedes sumar a un emparejamiento protegido
					return false;
			}
			sumaTotal=sumaNoEmparejadas+sumaEmparejadas+cartaJugador.getValor();
	    	cartaVerificar.setValor(sumaNoEmparejadas + cartaJugador.getValor());
			if(id1.equals("000") && id2.equals("000")) {
				if((sumaNoEmparejadas + cartaJugador.getValor())>10) //La suma no puede exceder de 10
					return false;
				if(contarCartas(cartaVerificar, this)==0) //No tiene carta para recoger emparejamiento
		    		return false;
		    }else if(!id1.equals("000") && !id2.equals("000") && !id1.equals(id2) ) //No puedes porque son de emparejamiento distintos
			   return false;
		    else {
		    	cartaVerificar.setValor(sumaTotal);
		    	if(cartaConId.getsumaEmparejadas()!=sumaEmparejadas) //No esta todas las cartas
		    		return false;
		    	if(sumaTotal>10) //No puede ser mayor de 10
		    		return false;
		    	if(contarCartas(cartaVerificar, this)==0) //No tiene carta para recoger emparejamiento
		    		return false;
		    }
	
	}
		
		return true;
	}
	
	public boolean validarDoblar(ArrayList<Carta> cartasADoblar, Carta cartaJugador) {
		boolean figuraSeleccionada=true;
		int contarFiguras=0, sumaEmparejadas=0, sumaTotal=0, sumaNoEmparejadas=0, valorAComparar=0;
		Carta cartaVerificar= new Carta();
		Carta cartaConId=new Carta();
		String id="000";
		
		if(cartaJugador.getValor()<=10) figuraSeleccionada=false;
		if(figuraSeleccionada) { //Emparejar figuras
			for(Carta carta: cartasADoblar) {
				if(carta.getValor()<=10) //No se puede usar una figura para emparejar numero
					return false;
				if(carta.getValor()!=cartaJugador.getValor()) //Todas las figuras deben ser iguales
					return false;
				else
					++contarFiguras;
			}
			if(contarFiguras==3 || contarFiguras==1) //No se pueden emparejar 3 o 2 figuras
				return false;
			cartaVerificar.setValor(cartaJugador.getValor());
			if(contarCartas(cartaVerificar, this)<2) //No tiene carta para recoger emparejamiento
	    		return false;
		} else {
			for(Carta carta: cartasADoblar) { //Si hay emparejamiento deben estar todas las emparejadas
				if(carta.getValor()>10) //No puede recoger una figura con un numero
					return false;
				if(!carta.getIdEmparejamiento().equals("000")) {
					if(!id.equals("000") && !id.equals(carta.getIdEmparejamiento())) //No puede haber carta de otro emparejamiento
						return false;
					if(carta.isDoblada()) //Solo se puede doblar una vez
						return false;
					id=carta.getIdEmparejamiento();
					sumaEmparejadas+=carta.getValor();
					cartaConId=carta;
				}
				else
					sumaNoEmparejadas+=carta.getValor();
			}
			
			//Comprobar si puede doblar
			for(int i=0; i<cartasADoblar.size()+1; i++) {
				sumaTotal=0; valorAComparar=0;
				if(i==(cartasADoblar.size())) {
					sumaTotal=sumaEmparejadas+sumaNoEmparejadas;
					if(cartaJugador.getValor()!=sumaTotal) { //No hay carta con suma
						return false;
					}
				} else {
					for(int j=0; j<cartasADoblar.size(); j++) {
						if(j!=i) 
							sumaTotal+=cartasADoblar.get(j).getValor();
						else 
							valorAComparar=cartasADoblar.get(j).getValor();
					}
					sumaTotal+=cartaJugador.getValor();
					if(sumaTotal==valorAComparar) break;
				}
			}
			cartaVerificar.setValor(sumaTotal);
			//Caso cuando la suma sea la que tenga el jugador
			if(!id.equals("000")) {
				if(cartaConId.getsumaEmparejadas()!=sumaEmparejadas) //No estan todas las cartas emparejadas
					return false;
			}
			if(sumaTotal!=cartaJugador.getValor()) {
				if(contarCartas(cartaVerificar, this)==0) //No tiene carta para recoger emparejamiento
		    		return false;
			} else {
				if(contarCartas(cartaVerificar, this)<2) //No tiene para recoger 
					return false;
			}
			System.out.println("SUMA TOTAL: "+sumaTotal+" cartaJugador: "+cartaJugador.getValor());
		}
		return true;
	}
	
	public boolean validarCartaDoblarse(ArrayList<Carta> cartasADoblar, Carta cartaJugador) {
		int suma = 0;
		for(Carta carta: cartasADoblar)
			suma += carta.getValor();
		if(suma == cartaJugador.getValor()) 
			return true;
		else {
			suma = 0;
			int maximo = 0;
			for (Carta carta: cartasADoblar) {
				if(carta.getValor() > maximo) 
					maximo = carta.getValor();
			}
			for (Carta carta: cartasADoblar) {
				if(carta.getValor() < maximo) 
					suma += carta.getValor();
			}
			if(suma == maximo)
				return true;
		}
		return false;
	}
	
	public void recogerCarta(ArrayList<Carta> mesa,
			ArrayList<Carta> cartasRecogerMesa, Carta cartaJugador, Jugador computadora) 
	{ //Recoger sumas //Carta igual //Emparejamiento //Emparejamiento + suma
		cartaJugador.setImagen(new Imagen(cartaJugador.buildPath(),"btn"));
		String id = "000";
		for (Carta carta: cartasRecogerMesa) {
			if(carta.getIdEmparejamiento() != "000") {
				id = carta.getIdEmparejamiento();
				break;
			}
		}
		if(!id.equals("000") && id.equals(this.idEmparejamiento))
			this.idEmparejamiento = "000";
		if(!id.equals("000") && id.equals(computadora.getIdEmparejamiento()))
			computadora.setIdEmparejamiento("000");
		this.cartas.remove(cartaJugador);
		mesa.removeAll(cartasRecogerMesa);
		this.cartasRecogidas.addAll(cartasRecogerMesa);
		this.cartasRecogidas.add(cartaJugador);
		if(mesa.size() == 0)
			this.clarezas++;
	}
	
	public void emparejarCarta(ArrayList<Carta> mesa, 
			ArrayList<Carta> cartasAEmparejar, Carta cartaJugador, Jugador computadora) 
	{
		int suma = cartaJugador.getValor();
		for (Carta carta: cartasAEmparejar)
			suma += carta.getValor();
		cartaJugador.setSumaEmparejadas(suma);
		for (Carta carta: cartasAEmparejar)
			carta.setSumaEmparejadas(suma);
		String id = "000";
		for (Carta carta: cartasAEmparejar) {
			if(!carta.getIdEmparejamiento().equals("000")) {
				id = carta.getIdEmparejamiento();
				break;
			}
		}
		if(id.equals("000")) 
			id = cartaJugador.generarIdEmparejamiento();
		this.idEmparejamiento = id;
		if(!id.equals("000") && id.equals(computadora.getIdEmparejamiento()))
			computadora.setIdEmparejamiento("000");
		cartaJugador.setIdEmparejamiento(id);
		mesa.add(cartaJugador);
		for(Carta carta: cartasAEmparejar) 
			carta.setIdEmparejamiento(id);
		this.cartas.remove(cartaJugador);
	}
	
	public void doblarCarta(ArrayList<Carta> mesa, 
			ArrayList<Carta> cartasADoblar, Carta cartaJugador, Jugador computadora) 
	{
		int maximo = 0;
		for (Carta carta: cartasADoblar) {
			if(carta.getValor() > maximo) 
				maximo = carta.getValor();
		}
		cartaJugador.setSumaEmparejadas(maximo);
		for (Carta carta: cartasADoblar)
			carta.setSumaEmparejadas(maximo);
		String id = "000";
		for (Carta carta: cartasADoblar) {
			if(!carta.getIdEmparejamiento().equals("000")) {
				id = carta.getIdEmparejamiento();
				break;
			}
		}
		if(id.equals("000"))
			id = cartaJugador.generarIdEmparejamiento();
		if(!id.equals("000") && id.equals(computadora.getIdEmparejamiento()))
			computadora.setIdEmparejamiento("000");
		this.idEmparejamiento = id;
		cartaJugador.setIdEmparejamiento(id);
		mesa.add(cartaJugador);
		for(Carta carta: cartasADoblar) {
			carta.setIdEmparejamiento(id);
			if(!carta.isDoblada())
				carta.toggleDoblada();
		}
		this.cartas.remove(cartaJugador);
	}
	
	//Obtener id emparejamiento (obtener uno o el del computador)
	public static String obtenerId(ArrayList<Carta> mesa, int numeroEmparejamiento) {
		String id1="000",id2="000";
		for(int i=0; i<mesa.size(); i++) {
			if(!mesa.get(i).getIdEmparejamiento().equals("000"))
				if(id1.equals("000")) {
					id1=mesa.get(i).getIdEmparejamiento();
					if(numeroEmparejamiento==1)
						return id1;
				}
				else if(id2.equals("000") && !id1.equals(mesa.get(i).getIdEmparejamiento()))
					return mesa.get(i).getIdEmparejamiento();
		}
		return id2; //Si no lo encuentra, devuelve el id por defecto 000
	}
	
	//Ordenar emparejadas para que queden adyacentes
	public static void ordenar(ArrayList<Carta> mesa, String id) {
		int saltarEmparejamiento=0, nuevoInicio=0, posicionCarta=0;
		Carta carta = new Carta();
		if(mesa.size()!=0) {
			while(!mesa.get(saltarEmparejamiento).getIdEmparejamiento().equals("000"))
				++saltarEmparejamiento;
		}
		nuevoInicio=saltarEmparejamiento;
		posicionCarta=nuevoInicio;
		for(int i=nuevoInicio; i<mesa.size(); i++) {
			if(mesa.get(i).getIdEmparejamiento().equals(id)) {    
				carta=mesa.get(posicionCarta);
				mesa.set(posicionCarta,mesa.get(i));
				mesa.set(i, carta);
				++posicionCarta;
			}
		}
	}
	
	public static int primeraPosicionDeId(ArrayList<Carta> mesa, String id) {
		int posicion=0;
		for(int i=0; i<mesa.size(); i++)
			if(mesa.get(i).getIdEmparejamiento().equals(id)) {
				posicion=i;
				break;
			}
		return posicion;
	}
	
	public static void agregar(ArrayList<Carta> mesa, String id) { //Agregar en la lista los emparejamientos de forma ordenada
		int ultimaPosicion=primeraPosicionDeId(mesa, id); //Comienzo en la primera posicion para obtener la ultima
		Carta carta=new Carta();
		if(mesa.size()!=0) {
			while(!mesa.get(ultimaPosicion).getIdEmparejamiento().equals(id))
				++ultimaPosicion;
		}
		for(int i=ultimaPosicion; i<mesa.size(); i++) {
			if(mesa.get(i).getIdEmparejamiento().equals(id)) {
				carta=mesa.get(i);
				mesa.remove(carta);
				mesa.add(ultimaPosicion, carta);
				++ultimaPosicion;
			}
		}
	}
	
	//Ordenar ambos emparejamientos
	public static void ordenarEmparejamientos(ArrayList<Carta> mesa) {
		String id1=obtenerId(mesa, 1), id2=obtenerId(mesa, 2);
		if(!id1.equals("000")) {
			ordenar(mesa, id1);
			agregar(mesa, id1);
		}
		if(!id2.equals("000")) {
			ordenar(mesa, id2);
			agregar(mesa, id2);
		}	
	}

	public void setCartas(ArrayList<Carta> cartas) {
		this.cartas = cartas;
	}

	public void setCartasRecogidas(ArrayList<Carta> cartasRecogidas) {
		this.cartasRecogidas = cartasRecogidas;
	}

	public void setClarezas(int clarezas) {
		this.clarezas = clarezas;
	}
	
	public void decidirMovimiento(ArrayList<Carta> mesa, Jugador jugador) {
		ArrayList<Carta> cartasSeleccionadas = new ArrayList<Carta>();
		//buscar 10 de diamantes
		for(Carta cartaMesa: mesa) {
			if(cartaMesa.getValor() == 10 && cartaMesa.getFigura() == Figuras.diamante) {
				for(Carta carta: this.cartas) {
					if(carta.getValor() == 10) {
						cartasSeleccionadas.add(cartaMesa);
						if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
							this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
						break;
					}
				}
				break;
			}
		}
		cartasSeleccionadas.clear();
		for(Carta carta: this.cartas) {
			if(carta.getValor() == 10 && carta.getFigura() == Figuras.diamante) {
				for(Carta cartaMesa: mesa) {
					if(cartaMesa.getValor() == 10) {
						cartasSeleccionadas.add(cartaMesa);
						if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
							this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
						break;
					}
				}
				break;
			}
		}
		cartasSeleccionadas.clear();
		//buscar 2 de espadas
		for(Carta cartaMesa: mesa) {
			if(cartaMesa.getValor() == 2 && cartaMesa.getFigura() == Figuras.espada) {
				for(Carta carta: this.cartas) {
					if(carta.getValor() == 2) {
						cartasSeleccionadas.add(cartaMesa);
						if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
							this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
					}
				}
				break;
			}
		}
		cartasSeleccionadas.clear();
		for(Carta carta: this.cartas) {
			if(carta.getValor() == 2 && carta.getFigura() == Figuras.espada) {
				for(Carta cartaMesa: mesa) {
					if(cartaMesa.getValor() == 2) {
						cartasSeleccionadas.add(cartaMesa);
						if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
							this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
					}
				}
				break;
			}
		}
		cartasSeleccionadas.clear();
		//buscar un as
		for(Carta cartaMesa: mesa) {
			if(cartaMesa.getValor() == 1) {
				for(Carta carta: this.cartas) {
					if(carta.getValor() == 1) {
						cartasSeleccionadas.add(cartaMesa);
						if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
							this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//buscar una espada
		for(Carta cartaMesa: mesa) {
			if(cartaMesa.getFigura() == Figuras.espada) {
				int valor = cartaMesa.getValor();
				for(Carta carta: this.cartas) {
					if(carta.getValor() == valor) {
						cartasSeleccionadas.add(cartaMesa);
						if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
							this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//recoger emparejamiento
		if(!this.idEmparejamiento.equals("000")) {
			int suma = 0;
			for(Carta cartaMesa: mesa)
				if(cartaMesa.getIdEmparejamiento().equals(this.idEmparejamiento)) {
					cartasSeleccionadas.add(cartaMesa);
					suma += cartaMesa.getValor();
				}
			//emparejamiento doblado
			if(cartasSeleccionadas.get(0).isDoblada()) {
				int maximo = 0;
				for(Carta carta: cartasSeleccionadas)
					if(carta.getValor() > maximo)
						maximo = carta.getValor();
				suma = maximo;
			}
			for(Carta carta: this.cartas)
				if(carta.getValor() == suma) {
					if(this.validarCartasRecoger(cartasSeleccionadas, carta)) {
						this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
						return;
					}
				}
		}
		cartasSeleccionadas.clear();
		//chequear si puede recoger una carta
		for(Carta carta: this.cartas) {
			for(Carta cartaMesa: mesa) {
				if(carta.getValor() == cartaMesa.getValor()) {
					cartasSeleccionadas.add(cartaMesa);
					if(this.validarCartasRecoger(cartasSeleccionadas,carta)) {
						this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
						return;
					}else
						cartasSeleccionadas.clear();
				}
			}
		}
		cartasSeleccionadas.clear();
		//chequear si puede recoger 2 cartas
		for(Carta cartaMesa: mesa) {
			for(Carta cartaMesa2: mesa) {
				if(!cartaMesa.equals(cartaMesa2) && cartaMesa.getValor() <= 10 && cartaMesa2.getValor() <= 10) {
					int suma = cartaMesa.getValor() + cartaMesa2.getValor();
					for(Carta carta: this.cartas) {
						if(carta.getValor() == suma) {
							cartasSeleccionadas.add(cartaMesa);
							cartasSeleccionadas.add(cartaMesa2);
							if(this.validarCartasRecoger(cartasSeleccionadas, carta)) {
								this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
								return;
							}else
								cartasSeleccionadas.clear();
						}
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//chequear si puede recoger 3 cartas
		for(Carta cartaMesa: mesa) {
			for(Carta cartaMesa2: mesa) {
				for(Carta cartaMesa3: mesa) {
					if(!cartaMesa.equals(cartaMesa2) && !cartaMesa.equals(cartaMesa3) && !cartaMesa2.equals(cartaMesa3) &&
						cartaMesa.getValor() <= 10 && cartaMesa2.getValor() <= 10 && cartaMesa3.getValor() <= 10) {
						int suma = cartaMesa.getValor() + cartaMesa2.getValor() + cartaMesa3.getValor();
						for(Carta carta: this.cartas) {
							if(carta.getValor() == suma) {
								cartasSeleccionadas.add(cartaMesa);
								cartasSeleccionadas.add(cartaMesa2);
								cartasSeleccionadas.add(cartaMesa3);
								if(this.validarCartasRecoger(cartasSeleccionadas, carta)) {
									this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
									return;
								}else
									cartasSeleccionadas.clear();
							}
						}
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//chequear si puede robar emparejamiento
		if(!jugador.getIdEmparejamiento().equals("000")) {
			int suma = 0;
			for(Carta cartaMesa: mesa)
				if(cartaMesa.getIdEmparejamiento().equals(jugador.getIdEmparejamiento())) {
					cartasSeleccionadas.add(cartaMesa);
					suma += cartaMesa.getValor();
				}
			//emparejamiento doblado
			if(cartasSeleccionadas.get(0).isDoblada()) {
				int maximo = 0;
				for(Carta carta: cartasSeleccionadas)
					if(carta.getValor() > maximo)
						maximo = carta.getValor();
				suma = maximo;
			}
			for(Carta carta: this.cartas)
				if(carta.getValor() == suma) {
					if(this.validarCartasRecoger(cartasSeleccionadas, carta)) {
						this.recogerCarta(mesa, cartasSeleccionadas, carta, jugador);
						return;
					}
				}
		}
		cartasSeleccionadas.clear();
		//chequear si puede sumar a un emparejamiento
		if(!jugador.getIdEmparejamiento().equals("000")) {
			boolean doblado = false;
			for(Carta cartaMesa: mesa) {
				if(cartaMesa.getIdEmparejamiento().equals(jugador.getIdEmparejamiento())) {
					if(cartaMesa.isDoblada()) {
						doblado = true;
						break;
					}
					cartasSeleccionadas.add(cartaMesa);
				}
			}
			if(!doblado) {
				for(Carta carta: this.cartas) {
					if(this.validarCartasEmparejar(cartasSeleccionadas, carta)) {
						this.emparejarCarta(mesa, cartasSeleccionadas, carta, jugador);
						return;
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//chequear si puede emparejar una carta
		for(Carta cartaMesa: mesa) {
			for(Carta carta: this.cartas) {
				cartasSeleccionadas.add(cartaMesa);
				if(this.validarCartasEmparejar(cartasSeleccionadas, carta)) {
					this.emparejarCarta(mesa, cartasSeleccionadas, carta, jugador);
					return;
				}
				else
					cartasSeleccionadas.clear();
			}
		}
		cartasSeleccionadas.clear();
		//chequear si puede emparejar 2 cartas
		for(Carta cartaMesa: mesa) {
			for(Carta cartaMesa2: mesa) {
				if(!cartaMesa.equals(cartaMesa2) && cartaMesa.getValor() <= 10 && cartaMesa2.getValor() <= 10) {
					for(Carta carta: this.cartas) {
						cartasSeleccionadas.add(cartaMesa);
						cartasSeleccionadas.add(cartaMesa2);
						if(this.validarCartasEmparejar(cartasSeleccionadas, carta)) {
							this.emparejarCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
						else
							cartasSeleccionadas.clear();
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//chequear si puede emparejar 3 cartas
		for(Carta cartaMesa: mesa) {
			for(Carta cartaMesa2: mesa) {
				for(Carta cartaMesa3: mesa) {
					if(!cartaMesa.equals(cartaMesa2) && !cartaMesa.equals(cartaMesa3) && !cartaMesa2.equals(cartaMesa3)) {
						for(Carta carta: this.cartas) {
							cartasSeleccionadas.add(cartaMesa);
							cartasSeleccionadas.add(cartaMesa2);
							cartasSeleccionadas.add(cartaMesa3);
							if(this.validarCartasEmparejar(cartasSeleccionadas, carta)) {
								this.emparejarCarta(mesa, cartasSeleccionadas, carta, jugador);
								return;
							}
							else
								cartasSeleccionadas.clear();
						}
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//chequear que puede doblar su emparejamiento
		if(!this.idEmparejamiento.equals("000")) {
			int suma = 0;
			boolean doblado = false;
			for(Carta cartaMesa: mesa)
				if(cartaMesa.getIdEmparejamiento().equals(this.idEmparejamiento)) {
					if(cartaMesa.isDoblada()) {
						doblado = true;
						break;
					}
					cartasSeleccionadas.add(cartaMesa);
					suma += cartaMesa.getValor();
				}
			if(!doblado && suma <= 10) {
				for(Carta carta: this.cartas) {
					if(carta.getValor() == suma) {
						if(this.validarCartaDoblarse(cartasSeleccionadas, carta)) {
							this.doblarCarta(mesa, cartasSeleccionadas, carta, jugador);
							return;
						}
					}
				}
			}
		}
		cartasSeleccionadas.clear();
		//lanzar
		this.lanzarCarta(mesa, 0);
	}
}
