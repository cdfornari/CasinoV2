package com.fornari.casino;
import java.util.ArrayList;

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
	
	public int contarPuntaje() {
		return 0;
	}
	
	public void asignarCartasSobrantes() {
		
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
	
	public boolean validarCartasRecoger(ArrayList<Carta> cartasARecoger, Carta cartaJugador, Jugador jugador) {
		boolean figuraSeleccionada=true, doblada=false;
		int contarFiguras=0, sumaNoEmparejadas=0, sumaEmparejadas=0, sumaTotal=0, sumaEsperada;
		Carta cartaConId=null;
		String id1="",id2="";
		
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
						if(id1.equals(""))
							id1=carta.getIdEmparejamiento();
						else if(id2.equals("") && !id1.equals(carta.getIdEmparejamiento()))
							id2=carta.getIdEmparejamiento();
						if(cartasARecoger.size()!=1)
							sumaEmparejadas+=carta.getValor(); //Sumo todas las emparejadas
						else
							sumaEmparejadas=carta.getsumaEmparejadas();
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
					if(!jugador.getIdEmparejamiento().equals("000") && contarCartas(cartaJugador, jugador)<2) //No tienes para recoger tu emparejamiento activo
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
			    	if(!jugador.getIdEmparejamiento().equals("000") && !cartaConId.getIdEmparejamiento().equals(jugador.getIdEmparejamiento())) { //No tiene carta para recoger su emparejamiento activo
						if(contarCartas(cartaJugador, jugador)<2)
							return false;
			    	}
			    }
		
		}
		return true;
	}
	
	public boolean validarCartasEmparejar(ArrayList<Carta> cartasAEmparejar, Carta cartaJugador, Jugador jugador) {
		boolean figuraSeleccionada=true;
		int contarFiguras=0, sumaNoEmparejadas=0, sumaEmparejadas=0, sumaTotal=0;
		Carta cartaConId=null;
		String id1="",id2="";
		Carta cartaVerificar = new Carta();
		
		if(cartaJugador.getValor()<=10) figuraSeleccionada=false;
		if(!jugador.getIdEmparejamiento().equals("000")) //No puedes hacer otro emparejamiento porque ya tienes uno
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
			if(contarCartas(cartaVerificar, jugador)<2) //No tiene carta para recoger emparejamiento
	    		return false;
		} else { //Recoger numeros
			for(Carta carta: cartasAEmparejar) {
				if(carta.getValor()>10) //No se puede usar un numero para emparejar una figura
					return false;
				if(!carta.getIdEmparejamiento().equals("000")) {
					if(id1.equals(""))
						id1=carta.getIdEmparejamiento();
					else if(id2.equals("") && !id1.equals(carta.getIdEmparejamiento()))
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
				if(contarCartas(cartaVerificar, jugador)==0) //No tiene carta para recoger emparejamiento
		    		return false;
		    }else if(!id1.equals("000") && !id2.equals("000") && !id1.equals(id2) ) //No puedes porque son de emparejamiento distintos
			   return false;
		    else {
		    	cartaVerificar.setValor(sumaTotal);
		    	if(cartaConId.getsumaEmparejadas()!=sumaEmparejadas) //No esta todas las cartas
		    		return false;
		    	if(sumaTotal>10) //No puede ser mayor de 10
		    		return false;
		    	if(contarCartas(cartaVerificar, jugador)==0) //No tiene carta para recoger emparejamiento
		    		return false;
		    }
	
	}
		
		return true;
	}
	
	public boolean validarCartaDoblarse(Carta cartaADoblar, Carta cartaJugador, Jugador jugador) {
		Carta cartaVerificar = new Carta();
		if(cartaADoblar.getIdEmparejamiento().equals("000")) {
			if(cartaADoblar.getValor()!=cartaJugador.getValor()) //Ambas cartas deben ser iguales
				return false;
			else 
				cartaVerificar.setValor(cartaADoblar.getValor());
		} else {
			if(cartaJugador.getValor()!=cartaADoblar.getsumaEmparejadas())
				return false;
			else
				cartaVerificar.setValor(cartaADoblar.getsumaEmparejadas());
			}
		if(!jugador.getIdEmparejamiento().equals("000") && !cartaADoblar.getIdEmparejamiento().equals(jugador.getIdEmparejamiento()))  //No puedes doblar otro emparjeamiento
				return false;
		if(contarCartas(cartaVerificar, jugador)<2) //Debe tener 2 para doblar 1 para hacerlo y otra para recogerlo.
			return false;
		
	  return true;
	}
	
	public void recogerCarta(
			ArrayList<Carta> mesa,
			ArrayList<Carta> cartasRecogerMesa,
			ArrayList<Carta> cartasJugador) { //Recoger sumas //Carta igual //Emparejamiento //Emparejamiento + suma
		
		for(Carta carta : cartasJugador) {
			this.cartas.remove(carta);
		}
		for (Carta carta: cartasRecogerMesa) {
			mesa.remove(carta);
		}
		this.cartasRecogidas.addAll(cartasRecogerMesa);
		this.cartasRecogidas.addAll(cartasJugador);
	}
	
	public void emparejarCarta(ArrayList<Carta> mesa, 
			ArrayList<Carta> cartasAEmparejar, Carta cartaJugador) {
		cartasAEmparejar.add(cartaJugador);
		this.cartas.remove(cartaJugador);
		for(Carta carta: cartasAEmparejar) {
			if (!mesa.contains(carta)) {
				mesa.add(carta);
			}
		}
	}
	
	public void doblarCarta(ArrayList<Carta> mesa, 
			ArrayList<Carta> cartasADoblar, Carta cartaJugador) {
		cartasADoblar.add(cartaJugador);
		this.cartas.remove(cartaJugador);
		for(Carta carta: cartasADoblar) {
			if (!mesa.contains(carta)) {
				mesa.add(carta);
			}
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

	

}
