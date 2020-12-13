package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	
	private boolean isOpen;

	public Tap(String name) {
		super(name);
		//TODO: complete
	}
	
	/**
	 * Defines whether the tap is open or closed.
	 * 
	 * @param open  opening level
	 */
	public void setOpen(boolean open){
		isOpen = open;
	}
	
	@Override
	public void simulate(double inFlow, SimulationObserver observer) {
		double outFlow = isOpen ? inFlow : 0.0;
		observer.notifyFlow("Tap", getName(), inFlow, outFlow);
		getOutput().simulate(outFlow, observer);
		
	}
}
