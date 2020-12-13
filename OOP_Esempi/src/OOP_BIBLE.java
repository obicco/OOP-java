//LETTURA DA FILE (con regexp)
	public void loadData(Reader reader) throws IOException {
		// TODO Complete method
		
		BufferedReader br= new BufferedReader(reader);	
		String line;
		String elements[];
		while((line = br.readLine()) != null) {
			elements = line.split("//s*;//s*");
			if(elements.length != 3 || elements.length != 5)
				continue;
			if(elements[0].equals("P") || elements[0].equals("p")) {
				/*if(!elements[1].matches("[a-z]{3,25}") || 
						!elements[2].matches("[a-z]{3,25}") ||
						!elements[3].matches("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]"))
					continue;*/
				this.pazienti.put(elements[3], new Patient(elements[1], elements[2], elements[3]));
			}
			else if(elements[0].equals("M") || elements[0].equals("m")) {
				/*if(!elements[1].matches("[0-9]{1-100}") || 
						!elements[2].matches("[a-z]{3,20}") || 
						!elements[3].matches("[a-z]{3,20}") || 
						!elements[4].matches("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]") || 
						!elements[5].matches("[a-z]{3,50}"))
					continue;*/
				this.dottori.put(Integer.parseInt(elements[1]), new Doctor(elements[2], elements[3], elements[4],Integer.parseInt(elements[1]), elements[5]));
			}
		}
	}


//STREAM
        
    //BASIC
        //TreeMap in ordine inverso dove mappo <Lunghezza parola, lista delle parole>
    Map<Integer, List<String>> wordsByLengthDesc = Stream.of(txta).distinct()
				.collect(Collectors.groupingBy(String::length, ()->new TreeMap<>(Collections.reverseOrder()), Collectors.toList()));

        //conta comuni per provincia
    public Map<String, Long> countMunicipalitiesPerProvince() {
		return comuni.values().stream().collect(Collectors.groupingBy(x -> x.getProvince(), Collectors.counting()));
	}
    
        //return a map with the province as key and, as value, a map with the
        //municipality as key and the number of mountain huts as value
    public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return rifugi.values().stream().collect(Collectors.groupingBy(x -> x.getMunicipality().getProvince(),
				Collectors.groupingBy(x -> x.getMunicipality().getName(), Collectors.counting())));
                altitudeRanges.stream().map(x->x.toString()).forEach(r -> res.putIfAbsent(r, 0L));//aggiunge 0 se non ci sono rifugi
	}

        //Il metodo countMountainHutsPerAltitudeRange() restituisce una mappa contenente come chiave gli intervalli dialtitudine definiti dal metodo setAltitudeRanges()
        // e come valore il numero dei rifugi con un'altitudineinclusa in tale intervallo. 
        // NB Se l'altitudine del rifugio non è disponibile, si consideri l'altitudine delcomune corrispondente.
        // NBB t.getAltitude() restituisce un optional che attiva l' orElse in caso di valore nullo

    getMountainHuts().stream()
    .collect(groupingBy(t-> this.getAltitudeRange((t.getAltitude().orElse(t.getMunicipality().getAltitude()))),counting()));
        
        // raggruppo per provincia e sommo tutti i letti dei rifugi nella provincia
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return rifugi.values().stream().collect(Collectors.groupingBy(x -> x.getMunicipality().getProvince(),
				Collectors.summingInt(x -> x.getBedsNumber())));
	}

        //raggruppo per altitudine e cerco il rifugio col massimo numero di letti
    public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return rifugi.values().stream().collect(Collectors.groupingBy(x -> getAltitudeRange(x),
				Collectors.mapping(MountainHut::getBedsNumber, Collectors.maxBy(Comparator.naturalOrder()))));
	}

    //id ordinati dei dottori senza pazienti
    public Collection<Integer> idleDoctors(){
		return dottori.values()
        .stream()
        .filter(x -> x.getPazienti().size() == 0) // filtro i dottori senza pazienti
        .sorted(Comparator.comparing(x -> x.getNome().toLowerCase(), Comparator.naturalOrder())) //li ordino per nome
        .map(x -> x.getnBadge()) //mappo sugli id dei medici
        .collect(Collectors.toList()); //colleziono in una lista
	}

        //ritorna la lista dei dottori che hanno più pazienti della media
    public double getAvarage() {
		return dottori.values()
        .stream()
        .mapToInt(x->x.getPazienti().size()) // mappo sul numero di pazienti per dottore
        .average() // faccio la media
        .orElse(0.0); //se non ci sono elementi ritorno 0
	}
	public Collection<Integer> busyDoctors(){
		return dottori.values()
        .stream()
        .filter(x -> x.getPazienti().size() > getAvarage()) // filtro
        .map(x -> x.getnBadge()) //mappo col id dottore
        .collect(Collectors.toList()); //colleziono in una lista
	}

        //Verifica che tutti i topic relativi ai capitoli di esercizi siano contenuti nei capitoli di teoria
    public boolean checkTopics() {
		Set<Topic> t = chapters.stream()
                        .filter(c -> c instanceof TheoryChapter)
                        .flatMap(c -> c.getTopics().stream()) // da ogni topic faccio partire uno stream
				        .collect(Collectors.toSet());
		Set<Topic> e = chapters.stream().filter(c -> c instanceof ExerciseChapter).flatMap(c -> c.getTopics().stream())
				.collect(Collectors.toSet());
		return t.containsAll(e); // metodo utilissimo 
	}
    
        //ritorna una lista con tutti i topic (non ripetuti) di tutti i capitoli
    public List<Topic> getAllTopics() {
		return chapters.stream()
		        .flatMap(c -> c.getTopics().stream())
		        .distinct()
		        .sorted()
		        .collect(Collectors.toList());
	}

        //mappo ogni corso con la lista degli studenti iscritti
    public Map<String,List<String>> getAssignments(){
        return courses.values().stream()
                .collect(Collectors.toMap(Course::getName,c -> c.getEnrolled()));
    }

        //Mappa <Numero transazioni, lista regioni con quel numero di transazioni>
    public SortedMap<Long, List<String>> deliveryRegionsPerNT() {

		SortedMap<String, Long> map = transactions.values().stream()
		.map(Transaction::getDeliveryRegion) //mappo per nome regione
		.collect(groupingBy(s -> s, TreeMap::new, counting())); //raggruppo per regione e conto le transazioni per ognuna
		
		SortedMap<Long, List<String>> map1 = map.entrySet().stream() //utilizzo la mappa ottenuta prima
		.collect(groupingBy(e -> e.getValue(), () -> new TreeMap<Long, List<String>>(reverseOrder()), 
				mapping(e -> e.getKey(), toList()))); //raggruppo per numero transazioni, creo la mappa ordinata al contrario
                                                    //mappo le regioni in una lista
		return map1;
	}
        //dà il punteggio totale delle transazioni che riguardano lo stesso trasportatore. Le transazioni con punteggio inferiore 
        //al parametro minimumScore sono ignorate. I trasportatori compaiono in ordine alfabetico.
    public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return transactions.values().stream()
		.filter(t -> t.getScore() >= minimumScore)
		.collect(groupingBy(Transaction::getCarrierName, 
		                    TreeMap::new, 
		                    summingInt(Transaction::getScore)));	
	}
        //conta le transazioni effettuate per ogni prodotto
    public SortedMap<String, Long> nTPerProduct() {
		return transactions.values().stream()
		.map(Transaction::getProductId)
		.collect(groupingBy(s->s, 
		                    TreeMap::new, 
		                    counting()));
	}
        //Mappo le halls in <N di facilities, lista di id di halls con quel numero di facilities>
	public Map<Integer,List<Integer>> statHalls() {
	    return halls.stream().
	            sorted(comparingInt(Hall::getId)).
	            collect(groupingBy(
                                Hall::getNumFacilities,
                                TreeMap::new,
                                mapping(Hall::getId, toList())
                                ));

        //sommo i prezzi di tutti gli ordini con stato diverso da new appartenenti ad un cliente
    public double totalCustomer(int customerId){
        Customer c = customers.values().stream().skip(customerId-1).findFirst().orElse(null);
        return orders.stream().
                filter(o -> o.getCustomer() == c).
                filter(o->o.getStatus()!=OrderStatus.NEW).
                mapToDouble(Order::getTotal).sum();
    }

        //in base a quanto hanno speso faccio una lista di clienti partendo dagli ordini
    public SortedMap<Double,List<String>> bestCustomers(){ 
        Map<String,Double> totals = orders.stream().
                filter(o->o.getStatus()!=OrderStatus.NEW). //filtro gli ordini nuovi
                collect(groupingBy(o->o.getCustomer().toString(),summingDouble(Order::getTotal))); //raggruppo per cliente e sommo quanto hanno speso
        return totals.entrySet().stream().collect(groupingBy(
                                                  e -> e.getValue(), //raggruppo per quanto hanno speso 
                                                  () -> new TreeMap<Double,List<String>>(reverseOrder()),//creo lista ordinata
                                                  mapping(e -> e.getKey(),toList()) //raggruppo i clienti
                                                    ));
    }

    static <T> Predicate<T> not(Predicate<T> t) {
	    return t.negate();
	}

	public List<Order> pendingOrders(){
		return orders.values().stream()
		        .filter(not(Order::delivered))
		        .sorted(comparing(Order::getProductCode))
		        .collect(toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
	    return  orders.values().stream()
	            .collect(groupingBy(Order::getProductCode))
	         ;
	}

	public Map<String,Long> orderNBySupplier(){
        return  orders.values().stream()
                .collect(groupingBy(Order::getSupplierName,
                         TreeMap::new,
                         counting()))
             ;        
	}
    	
    public List<String> countDeliveredByProduct(){
        return  orders.values().stream()
                .filter(Order::delivered)
                .collect(groupingBy(Order::getProductCode,
                         counting()))
                .entrySet().stream()
                //.sorted(comparing(e->e.getValue()).reversed()) // in theory ok... Eclipse not parsing
                //.sorted(comparing((Map.Entry<String, Long> e)->e.getValue()).reversed()) // alternative
                .sorted(comparing(Map.Entry<String, Long>::getValue).reversed())
                .map(e -> e.getKey() + " - " + e.getValue())
                .collect(toList())
             ;
        
	}

    	public SortedMap<String, Integer> getCharges() {
		SortedMap<String, Integer> res = 
		requests.values().stream()
		.filter(Request::isCompleted)
		.collect(groupingBy(Request::getOwner, 
							TreeMap::new, 
							summingInt(Request::getAmount)));	
		return res;
	}

    //conti per professione e professioni per palazzo 
				// building      profess  amount
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		SortedMap<String, Map<String, Integer>> res = 		
		requests.values().stream()
		.filter(Request::isCompleted)
		.filter( r -> r.getAmount()>0)
		.collect(groupingBy(Request::getBuilding, 
							TreeMap::new, 
							groupingBy(Request::getProfessional,
										TreeMap::new,
										summingInt(Request::getAmount))));
		return res;
	}

    //
    public List<String> topMaintainers(){
    	
    	return users.values().stream()
    			.sorted(//comparing(User::getCountClosed)
    					comparing( (User u) -> u.getCountClosed() )
    					.reversed()
    					.thenComparing(User::getName))
    			.map( u -> u.getName() + ":" + u.getCountClosed())
    			.collect(toList());
    	
    }

    //ADVANCED
        //prima mappi per nome comune poi raggruppi per nome del comune e conti i rifugi
        //prendi l'entrySet dello stream precedente creo un nuovo stream e raggruppo i comuni per numero di rifugi in una lista 
    public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
       return this.getMountainHuts()
      .stream()
      .map(t->t.getMunicipality().getName())
      .collect(groupingBy(x ->x,TreeMap::new,counting()))
            .entrySet()
            .stream()
            .collect(groupingBy(Map.Entry::getValue,mapping(Map.Entry::getKey,toList())));
    }

    //ritorna lista formattata di dottori ordinata per il numero dei pazienti
    public String toStringFormattato() {
        return String.format("%3d", numeroPazienti)+ ": "+badgeNumber+" "+lastName+" "+firstName;
    }
    public Collection<String> doctorsByNumPatients(){
        return dottori.values()
        .stream()
        .sorted(comparing(d->d.getPazienti().size(),reverseOrder())) 
        .map(t->t.toStringFormattato())
        .collect(toList());
    }

    // numero di pazienti per specializzazione, ordina prima per numero decrescente 
    // di pazienti e poi in ordine alfabetico di specializzazione. 
    // infine mappo su una stringa formattata
    public Collection<String> countPatientsPerSpecialization(){
        return 
        dottori.values()
        .stream()
        .collect(groupingBy(t->t.getSpecialization(),summingInt(t->t.getPazienti().size()))) // raggruppo per specializzazione e sommo tutti i pazienti
        .entrySet()
        .stream()
        .sorted(Comparator.comparing(Map.Entry::getKey,Comparator.naturalOrder())) // ordino prima per specializzazione
        .sorted(Comparator.comparing(Map.Entry::getValue,Comparator.reverseOrder())) // e poi per numero pazienti (ordine contrario alla richiesta)
        .map(t->String.format("%3d",t.getValue()+" - "+t.getKey()))
        .collect(toList());
  }

    //assegna una lista di studenti al corso preferito in base alla loro media, ritorna il numero di studenti
    // ai quali non è possibile assegnare un corso
    public long makeClasses() {
        return
        students.values().stream()
        .sorted(comparing(Student::getGradeAverage).reversed()) // ordino per merito
        .mapToInt(s -> { //dato lo studente provo ad aggiungerlo e ritorno 1 se non ci sono riuscito
            if(s.getPreferences()!=null) {
                s.getPreferences() // streamma le preferenze dello studente
                .stream()
                .filter(Course::hasRoom) // filtra i corsi che hanno posto
                .findFirst() //prende il primo
                .ifPresent(c->{ 
                    c.enroll(s); // lo assegna
                    listeners.forEach(l->l.assignedToCourse(s.getId(), c.getName())); //avvisa i listeners
                });
                
            }
            return s.isEnrolled()?0:1; 
        }).sum();//conta quanti sono i non assegnati
    }

    //per ogni corso restituisce una lista che ha 
    // in posizione 0:  il numero di studenti che lo hanno scelto come prima preferenza
    // in posizione 1:  il numero di studenti che lo hanno scelto come seconda preferenza
    // in posizione 2:  il numero di studenti che lo hanno scelto come terza preferenza
    public Map<String,List<Long>> numberRequests(){        
        return
           courses.keySet().stream().
           collect(
                 toMap(
                    c-> (String) c,
                    c -> (List<Long>) students.values().stream().
                         map(s->s.choiceNo(c)). // restituisce posizione del corso nella classifica dei corsi preferiti dallo studente
                         collect(collectingAndThen( 
                                 groupingBy(n->n,counting()), // raggruppo per posizione(n) e conto il numero di studenti
                                 m -> { // qui ho una mappa <posizione, nStudenti>
                                     ArrayList<Long> res = new ArrayList<>();
                                     for(int i=1; i<=3; ++i) {
                                         res.add( m.getOrDefault(i, 0L) ); //aggiungo alla lista
                                     }
                                     return res;
                                 }
                                 )
                         )
                    ));
     }

        //ritorna una lista di facilities ordinata per numero 
        //decrescente di hall in cui è concessa e poi in ordine alfabetico
	public List<String> statFacility() {
		return halls.stream().flatMap(f -> f.getFacilities().stream()) // faccio partire uno stream per ogni lista di facilities associata ad una hall
				.collect(Collectors.groupingBy(
						x -> x, 
						Collectors.counting())) //raggruppo per facilities e conto in quante hall si ripete
				.entrySet().stream() // utilizzo la mappa creata come stream di ingresso
				.sorted(comparing(Map.Entry<String,Long>::getValue,  // ordino per value
											 reverseOrder()).
								   thenComparing(Map.Entry::getKey)) // e poi alfabeticamente
				.map(Map.Entry::getKey) //mappo sulla stringa
				.collect(Collectors.toList()); // e colleziono in una lista
	}

    //RECURSIVE FUNCTIONS
        
    //ricorsione per aggiungere sotto-topic ai topic
    public void addTopic(Topic topic) {
		if(!topicSet.contains(topic)) {
			topicSet.add(topic);
			for(Topic t: topic.getSubTopics())
				addTopic(t);
		}
	}