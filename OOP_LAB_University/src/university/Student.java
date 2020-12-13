package university;

public class Student {
	
	private String first;
	private String last;
	private int id;
	private int crCounter = 0;
	
	private String courses[];
	
	static final int MAX_NUM_COURSES = 25;
	
	public Student(String first, String last, int id) {
		this.first = first;
		this.last = last;
		this.id = id;
		
		courses = new String[MAX_NUM_COURSES];
	}
	
	public String getName() {
		return this.first;
	}
	
	public String getSurname() {
		return this.last;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getString() {
		return this.id + " " + this.first + " " + this.last;
	}
	
	public void setCourse(String courseCode) {
		this.courses[crCounter] = courseCode;
		crCounter++;
	}
	
	public String getCourses() {
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < crCounter; i++) {
			sb.append(courses[i] + "\n");
		}
		
		return sb.toString();
	}
}
