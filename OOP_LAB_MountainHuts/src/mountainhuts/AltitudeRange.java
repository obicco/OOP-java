package mountainhuts;

public class AltitudeRange {
	private int minValue;
	private int maxValue;
	
	public AltitudeRange(Integer minValue, Integer maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public AltitudeRange(String range) {
		String[] fields = range.split("-");
		minValue = Integer.parseInt(fields[0]);
		maxValue = Integer.parseInt(fields[1]);
	}
	
	public Integer getMinValue() {
		return minValue;
	}
	
	public Integer getMaxValue() {
		return maxValue;
	}
	
	@Override
	public String toString() {
		return minValue + "-" + maxValue;
	}
}
