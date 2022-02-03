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
					if(!this.getIdEmparejamiento().equals("000") && contarCartas(cartaJugador, this)<2) //No tienes para recoger tu emparejamiento activo
						return false;
			    }else if(!id1.equals("000") && !id2.equals("000") && !id1.equals(id2) ) //No puedes porque son de emparejamiento distintos
				   return false;
			    else {
			    	sumaEsperada=cartaConId.getsumaEmparejadas();
			    	System.out.println("SUMA ESPERADA: "+sumaEsperada);
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
		String id = "000";
		for (Carta carta: cartasRecogerMesa) {
			if(carta.getIdEmparejamiento() != "000") {
				id = carta.getIdEmparejamiento();
				break;
			}
		}
		if(id != "000" && id == this.idEmparejamiento)
			this.idEmparejamiento = "000";
		if(id != "000" && id == computadora.getIdEmparejamiento())
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
			if(carta.getIdEmparejamiento() != "000") {
				id = carta.getIdEmparejamiento();
				break;
			}
		}
		if(id == "000")
			id = cartaJugador.generarIdEmparejamiento();
		this.idEmparejamiento = id;
		if(id != "000" && id == computadora.getIdEmparejamiento())
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
			if(carta.getIdEmparejamiento() != "000") {
				id = carta.getIdEmparejamiento();
				break;
			}
		}
		if(id == "000")
			id = cartaJugador.generarIdEmparejamiento();
		if(id != "000" && id == computadora.getIdEmparejamiento())
			computadora.setIdEmparejamiento("000");
		this.idEmparejamiento = id;
		mesa.add(cartaJugador);
		for(Carta carta: cartasADoblar) {
			carta.setIdEmparejamiento(id);
			if(!carta.isDoblada())
				carta.toggleDoblada();
		}
		this.cartas.remove(cartaJugador);
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
