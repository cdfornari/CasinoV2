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
	
	public void lanzarCarta(ArrayList<Carta> mesa, Carta cartaJugador) {
		cartas.remove(cartas.indexOf(cartaJugador));
		mesa.add(cartaJugador);
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
	
	public boolean cartaPuedeRecogerse(Carta cartaARecoger, Carta cartaJugador, Jugador jugador) {
		if(cartaARecoger.getIdEmparejamiento().equals("000")) {
			if(cartaARecoger.getValor()!=cartaJugador.getValor()) //Las cartas no son del mismo valor
				return false;
		} else {
			if(cartaARecoger.getsumaEmparejadas()!=cartaJugador.getValor()) //La carta no es de igual valor a la suma de emparejadas
				return false;
		}
		if(!jugador.getIdEmparejamiento().equals("000") && !cartaARecoger.getIdEmparejamiento().equals(jugador.getIdEmparejamiento())) {
			if(contarCartas(cartaJugador, jugador)<2) //Debe tener otra para recoger su emparejamiento
				return false;
		}
		return true;
	}
	
	public boolean multiplesCartasRecogerse(ArrayList<Carta> cartasARecoger, Carta cartaJugador, Jugador jugador) {
		boolean figuraSeleccionada=true, doblada=false;
		int contarFiguras=0, sumaNoEmparejadas=0, sumaEmparejadas=0, sumaTotal=0;
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
						++sumaEmparejadas; //Sumo todas las emparejadas
						cartaConId=carta; //Obtengo una carta que tenga el id para conseguir sumaEmparejadas
						if(carta.isDoblada()) //Veo si esta doblado el emparejamiento
							doblada=true;
					} else
					++sumaNoEmparejadas; //Sumo las no emparejadas para la suma final
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
			    	if(doblada)  //Si esta doblada, se espera la suma *2
			    		sumaEmparejadas=sumaEmparejadas/2;
			    	if(cartaConId.getsumaEmparejadas()!=sumaEmparejadas) //No esta todas las cartas
			    		return false;
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
	
	public boolean cartaPuedeEmparejarse(Carta cartaEmparejar, Carta cartaJugador, Jugador jugador) {
		Carta cartaVerificar = new Carta();
		int suma=0;
		if(!cartaEmparejar.getIdEmparejamiento().equals("000"))
			suma=cartaEmparejar.getsumaEmparejadas()+cartaJugador.getValor();
		else
			suma=cartaEmparejar.getValor()+cartaJugador.getValor();
		cartaVerificar.setValor(suma);
		if(cartaEmparejar.getValor()>10 || cartaJugador.getValor()>10) //No se puede emparejar 2 figuras
			return false;
		if(!jugador.getIdEmparejamiento().equals("000")) //No puede emparejar porque ya tienes uno activo
			return false;
		if(cartaEmparejar.isDoblada()) //No puedes sumar a una doblada
			return false;
		if((cartaEmparejar.getValor() + cartaJugador.getValor())>10 || (cartaEmparejar.getsumaEmparejadas() + cartaJugador.getValor())  >10) //La suma pasaria de 10
			return false;
		if(contarCartas(cartaVerificar, jugador)==0) //No tiene carta para recoger el emparejamiento
			return false;
		return true;
	}
	
	public boolean multiplesCartasEmparejarse(ArrayList<Carta> cartasAEmparejar, Carta cartaJugador, Jugador jugador) {
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
			if(contarFiguras==3) //No se pueden emparejar 3 figuras
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
					++sumaEmparejadas; //Sumo todas las emparejadas
					cartaConId=carta; //Obtengo una carta que tenga el id para conseguir sumaEmparejadas
				} else
				++sumaNoEmparejadas; //Sumo las no emparejadas para la suma
			}
			if(id1.equals("000") && id2.equals("000")) {
				if((sumaNoEmparejadas + cartaJugador.getValor())>10) //La suma no puede exceder de 10
					return false;
		    }else if(!id1.equals("000") && !id2.equals("000") && !id1.equals(id2) ) //No puedes porque son de emparejamiento distintos
			   return false;
		    else {
		    	if(cartaConId.getsumaEmparejadas()!=sumaEmparejadas) //No esta todas las cartas
		    		return false;
		    	sumaTotal=sumaNoEmparejadas+sumaEmparejadas;
		    	cartaVerificar.setValor(sumaTotal);
		    	if(sumaTotal>10) //No puede ser mayor de 10
		    		return false;
		    	if(contarCartas(cartaVerificar, jugador)==0) //No tiene carta para recoger emparejamiento
		    		return false;
		    }
	
	}
		
		return true;
	}
	
	public boolean cartaPuedeDoblarse(Carta cartaADoblar, Carta cartaJugador, Jugador jugador) {
		Carta cartaVerificar = new Carta();
		if(jugador.getIdEmparejamiento().equals("000")) {
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
	
	public void recogerCarta(Mazo mesa, Carta cartaARecoger, Carta cartaJugador) { //Recoger sumas //Carta igual //Emparejamiento //Emparejamiento + suma
		
	}
	
	public void emparejarCarta(Mazo mesa, Carta cartaAEmparejar, Carta cartaJugador) {
		
	}
	
	public void doblarCarta(Mazo mesa, Carta cartaADoblar, Carta cartaJugador) {
		
	}

}
