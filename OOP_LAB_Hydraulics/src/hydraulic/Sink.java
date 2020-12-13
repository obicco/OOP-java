package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
	}
	
	@Override
	public void connect(Element elem) {
			System.err.println("Cannot call connect with Sink object");
			return;
	}
	
	@Override
	public void simulate(double inFlow, SimulationObserver observer) {
		observer.notifyFlow("Sink", getName(), inFlow, SimulationObserver.NO_FLOW);
	}
	
}
