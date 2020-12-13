package hydraulic;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	//int
//	private int currentEl = 0;
	
	// Stringhe
	
	//Classi
//	private Element elements[] = new Element[MAX_NUM_ELEMENTS];
	
	//Collection o lista
	private List<Element> elements = new ArrayList<>();
	
//	static final int MAX_NUM_ELEMENTS = 100;
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
//		elements[currentEl] = elem;
//		currentEl++;
		elements.add(elem);
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to the number of added elements
	 */
	public Element[] getElements(){	
//		return Arrays.copyOf(elements, currentEl);		//Metodo supersemplificato per copiare un vettore
//		Element[] result = new Element[elements.size()];
//		for (int i = 0; i < elements.size(); i++) {	Possibilità 1
//			result[i] = elements.get(i);
//		}
//		int i = 0;									Possibilità 2
//		for (Element e : elements) {
//			result[i++] = e;
//		}
		return elements.toArray(new Element[elements.size()]);
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for (Element e : elements) {
			if (e instanceof Source) {
				e.simulate(SimulationObserver.NO_FLOW, observer);
			}
		}
	}
}
