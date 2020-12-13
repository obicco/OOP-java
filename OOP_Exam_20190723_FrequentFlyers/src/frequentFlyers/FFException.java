 package frequentFlyers;
@SuppressWarnings("serial")
public class FFException extends Exception {
    public FFException() {
        super("No description");
    }
	public FFException(String reason) {
		super(reason);
	}
}
