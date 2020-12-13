package ticketing;

import java.util.LinkedList;
import java.util.List;

public class Component {
	
	private String name;
	private List<Component> subcomponents = new LinkedList<>();
	
	public Component(String name) {
		this.name = name;
	}
	
	public void addSubComponent(Component c) throws TicketException {
		if(subcomponents.contains(c)) {
			throw new TicketException();
		}
		subcomponents.add(c);
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (! (o instanceof Component) ) return false;
		return this.name.equals(((Component)o).name);
	}
}
