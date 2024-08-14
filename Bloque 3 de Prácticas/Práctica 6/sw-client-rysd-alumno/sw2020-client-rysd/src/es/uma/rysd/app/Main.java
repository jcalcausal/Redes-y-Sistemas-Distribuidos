package es.uma.rysd.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import es.uma.rysd.entities.Film;
import es.uma.rysd.entities.People;
import es.uma.rysd.entities.Planet;
import es.uma.rysd.entities.Vehicle;

public class Main {	
	private static Random rand; // para números aleatorios
	private static Scanner sc; // para leer de teclado
	
    public static void main(String[] args) throws IOException{
	
        SWClient sw = new SWClient();
        String respuesta = null;
    	rand = new Random();
        sc = new Scanner(System.in);

        do{
        	masAlto(sw);
        	masGrande(sw);
        	quienVive1(sw);
        	quienVive2(sw);
        	quienSaleEn(sw);
	       	System.out.println("Desea otra ronda (s/n)?");
	       	respuesta = sc.nextLine();
	    }while(respuesta.equals("s"));
        sc.close();
        
    }
    
    // Genera un número entre 0 y max-1 que no haya sido usado previamente (los usados vienen en l)
    public static Integer getRandomResource(int max, List<Integer> l){
    	if(max == l.size()) return null;

    	Integer p = rand.nextInt(max);
    	while(l.contains(p)){
    		p = (p+1)%max;
    	}
    	return p;
    }
    
    // Pregunta que obtiene dos recursos iguales (personajes en este caso) y los compara
    public static void masAlto(SWClient sw) throws IOException{
    	// Obteniendo la cantidad de gente almacenada
    	int max_people = sw.getNumberOfResources("people");
    	if(max_people == 0){
    		System.out.println("No se encontraron personas.");
    		return;
    	}
    	
    	System.out.println("Generando pregunta...");
    	// Cogiendo dos personas al azar sin repetir
        List<Integer> used = new ArrayList<Integer>();
    	List<People> people = new ArrayList<People>();
    	int contador = 0;
    	while(contador < 2){
    		Integer p = getRandomResource(max_people, used);
    		People person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person != null){
    			people.add(person);
    			contador++;
    		} 
    		used.add(p);
    	}
    	
    	// Escribiendo la pregunta y leyendo la opción
    	Integer n = null;
    	do{
    		System.out.println("¿Quién es más alto? [0] "+ people.get(0).name + " o [1] " + people.get(1).name);
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n != 0 && n != 1);
    	
    	// Mostrando la información de los personajes elegidos
    	for(People p: people){
    		System.out.println(p.name + " mide " + p.height);
    	}
    	
    	// Resultado
    	if(Double.parseDouble(people.get(n).height) >= Double.parseDouble(people.get((n+1)%2).height)){
    		System.out.println("Enhorabuena!!! "+ acierto[rand.nextInt(acierto.length)]);
    	} else {
    		System.out.println("Fallaste :( " + fracaso[rand.nextInt(fracaso.length)]);
    	}
    }
   
    // Pregunta que obtiene dos recursos iguales (vehículos en este caso) y los compara
    public static void masGrande(SWClient sw) throws IOException{
    	// Obteniendo la cantidad de naves almacenada
    	int max_vehicles = sw.getNumberOfResources("vehicles");
    	if(max_vehicles == 0){
    		System.out.println("No se encontraron vehículos.");
    		return;
    	}
    	System.out.println("Generando pregunta...");
    	// Cogiendo dos naves al azar sin repetir
        List<Integer> used = new ArrayList<Integer>();
    	List<Vehicle> vehiculos = new ArrayList<Vehicle>();
    	int contador = 0;
    	while(contador < 2){
    		Integer s = getRandomResource(max_vehicles, used);
    		Vehicle ve = sw.getVehicle(sw.generateEndpoint("vehicles", s));
    		if(ve != null){
    			vehiculos.add(ve);
    			contador++;
    		}
    		used.add(s);
    	}
    	
    	// Escribiendo la pregunta y leyendo la opción
    	Integer n = null;
    	do{
    		System.out.println("¿Qué vehículo es más grande? [0] "+ vehiculos.get(0).name + " o [1] " + vehiculos.get(1).name);
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n != 0 && n != 1);
    	
    	// Mostrando la información de los personajes elegidos
    	for(Vehicle ve: vehiculos){
    		System.out.println(ve.name + " mide " + ve.length);
    	}
    	
    	// Resultado
    	if(Double.parseDouble(vehiculos.get(n).length) >= Double.parseDouble(vehiculos.get((n+1)%2).length)){
    		System.out.println("Enhorabuena!!! "+ acierto[rand.nextInt(acierto.length)]);
    	} else {
    		System.out.println("Fallaste :( " + fracaso[rand.nextInt(fracaso.length)]);
    	}
    }
    
    
    // Pregunta que relaciona varios recursos:
    // - Elige un recurso (planeta en este caso)
    // - Pregunta sobre algún recurso relacionado (persona que nació o fue crear ahí)
    // - Busca ese recurso y comprueba si está relacionado con el primero (si la persona buscada tiene como planeta el original)
    public static void quienVive1(SWClient sw) throws IOException{
    	// Obteniendo la cantidad de planetas
    	int max_planet = sw.getNumberOfResources("planets");
    	if(max_planet == 0){
    		System.out.println("No se encontraron planetas.");
    		return;
    	}
    	
    	System.out.println("Generando pregunta...");
    	// Obteniendo el planeta (que tenga personas)
        List<Integer> used = new ArrayList<Integer>();
        Planet planet = null;
        do{
        	Integer p = getRandomResource(max_planet, used);
        	planet = sw.getPlanet(sw.generateEndpoint("planets", p));
    		if(planet!= null){
    			used.add(p);
    		}
        }while(planet == null || planet.residents == null);

        // Planteamos la pregunta
        String s = null;
   		System.out.println("¿Quién nació/fue creado en " + planet.name + "?");
   		s = sc.nextLine();
   		// Buscamos la persona indicada
   		People p = sw.search(s);
   		
   		// Validamos la respuesta y mostramos sus datos
   		if(p == null){
   			System.out.println("No hay nadie con ese nombre");
   		} else {
   			System.out.println(p.name + " nació en " + p.homeplanet.name);
   		}
   		
   		// Resultados
   		if(p != null && p.homeplanet.name.equals(planet.name)){
    		System.out.println("Enhorabuena!!! "+ acierto[rand.nextInt(acierto.length)]);
    	} else {
    		System.out.println("Fallaste :( " + fracaso[rand.nextInt(fracaso.length)]);
    	}
    }
    
    // Similar a la previa pero en vez de solicitar que escriba se le ofrecen alternativa para ello:
    // - Se elige una al azar de las disponibles en el recurso, persona del planeta (la correcta)
    // - Se buscar aleatoriamente otras 3 que no estén relacionados con el recurso (las incorrectas)
    // - Se inserta la correcta de forma aleatoria
    public static void quienVive2(SWClient sw) throws IOException{
    	
    	// Obteniendo la cantidad de planetas y personas
    	int max_people = sw.getNumberOfResources("people");
    	int max_planet = sw.getNumberOfResources("planets");
    	if(max_people == 0 || max_planet == 0){
    		System.out.println("No se encontraron personas o planetas.");
    		return;
    	}
    	
    	System.out.println("Generando pregunta...");
    	// Obteniendo el planeta (con personas)
        List<Integer> used = new ArrayList<Integer>();
        Planet planet = null;
        do{
        	Integer p = getRandomResource(max_planet, used);
        	planet = sw.getPlanet(sw.generateEndpoint("planets", p));
    		if(planet != null){
    			used.add(p);
    		}
        }while(planet == null || planet.residents == null);
        used.clear();
        // Cogemos uno al azar como acierto
        String [] residents = planet.residents;
        People correcta = sw.getPerson(residents[rand.nextInt(residents.length)]);
        // Metemos todas las personas del planeta como usados para que no se seleccionen
        for(String s: residents){
        	used.add(sw.getIDFromURL(s));
        }
        
        // Buscamos 3 más
        List<People> people = new ArrayList<People>();
        int contador = 0;
    	while(contador < 3){
    		Integer p = getRandomResource(max_people, used);
    		People person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person != null){
    			people.add(person);
    			contador++;
    		}
    		used.add(p);
    	}
    	// Metemos el correcto de forma aleatoria
    	int pos_acierto = rand.nextInt(4);
    	people.add(pos_acierto, correcta);
    	
    	// Leemos la opción
    	Integer n = null;
    	do{
    		System.out.print("¿Quién nació/fue fabricado en "+planet.name +"?");
    		for(int i = 0; i < 4; i++){
    			System.out.print(" ["+i+"] "+ people.get(i).name);
    		}
    		System.out.println();
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n < 0 || n > 3);
    	
    	// Se muestran los resultados    	
    	for(People p: people){
    		System.out.println(p.name + " nació en " + p.homeplanet.name);
    	}
    	
    	// Resultados
    	if(n == pos_acierto){
    		System.out.println("Enhorabuena!!! "+ acierto[rand.nextInt(acierto.length)]);
    	} else {
    		System.out.println("Fallaste :( " + fracaso[rand.nextInt(fracaso.length)]);
    	}
    }     
    
public static void quienSaleEn(SWClient sw) throws IOException{
    	
    	// Obteniendo la cantidad de planetas y personas
		int max_movies = sw.getNumberOfResources("films");
    	int max_people = sw.getNumberOfResources("people");
    	if(max_movies == 0 || max_people == 0){
    		System.out.println("No se encontraron personas o planetas.");
    		return;
    	}
    	
    	System.out.println("Generando pregunta...");
    	// Obteniendo la lista de películas
        List<Integer> used = new ArrayList<Integer>();
        Film movie = null;
        do{
        	Integer p = getRandomResource(max_movies, used);
        	movie = sw.getMovie(sw.generateEndpoint("films", p));
    		if(movie != null){
    			used.add(p);
    		}
        }while(movie == null || movie.characters == null);
        used.clear();
        // Cogemos uno al azar como acierto
        String [] characters = movie.characters;
        People correcta = sw.getPerson(characters[rand.nextInt(characters.length)]);
        // Metemos todas las personas de la película como usados para que no se seleccionen
        for(String s: characters){
        	used.add(sw.getIDFromURL(s));
        }
        
        // Buscamos 3 más
        List<People> people = new ArrayList<People>();
        int contador = 0;
    	while(contador < 3){
    		Integer p = getRandomResource(max_people, used);
    		People person = sw.getPerson(sw.generateEndpoint("people", p));
    		if(person != null){
    			people.add(person);
    			contador++;
    		}
    		used.add(p);
    	}
    	// Metemos el correcto de forma aleatoria
    	int pos_acierto = rand.nextInt(4);
    	people.add(pos_acierto, correcta);
    	
    	// Leemos la opción
    	Integer n = null;
    	do{
    		System.out.print("¿Quién apareció en "+movie.title +"?");
    		for(int i = 0; i < 4; i++){
    			System.out.print(" ["+i+"] "+ people.get(i).name);
    		}
    		System.out.println();
    		try{
    			n = Integer.parseInt(sc.nextLine());
    		}catch(NumberFormatException ex){
    			n = -1;
    		}
    	}while(n < 0 || n > 3);
    	
    	// Se muestran los resultados    	
    	System.out.println(correcta.name + " apareció en " + movie.title);
    	
    	// Resultados
    	if(n == pos_acierto){
    		System.out.println("Enhorabuena!!! "+ acierto[rand.nextInt(acierto.length)]);
    	} else {
    		System.out.println("Fallaste :( " + fracaso[rand.nextInt(fracaso.length)]);
    	}
    }     
    
    
	private static String [] acierto = {"Ese es el camino", 
			"Eres uno con la Fuerza. La Fuerza está contigo",
			"Que la fuerza te acompañe",
			"Nada ocurre por accidente",
			"Sin duda, maravillosa tu mente es",
			"Cuando te fuiste, no era más que el aprendiz. Ahora eres el maestro",
			"La Fuerza te está llamando, déjala entrar",
			"Tu cantidad de midiclorianos debe ser muy alta"};
	private static String [] fracaso = {"El mejor profesor, el fracaso es.",
			"El miedo es el camino hacia el Lado Oscuro. El miedo lleva a la ira, la ira lleva al odio, el odio lleva al sufrimiento. Percibo mucho miedo en ti",
			"Tu carencia de fe resulta molesta",
			"La capacidad de hablar no te hace inteligente",
			"Concéntrate en el momento. Siente, no pienses, usa tu instinto",
			"No lo intentes. Hazlo, o no lo hagas, pero no lo intentes",
			"Paciencia, utiliza la Fuerza. Piensa",
			"Siento una perturbación en la fuerza",
			"El lado oscurso se intensifica en ti"};


}
