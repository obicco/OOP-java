package university;

public class Course {

	private String title;
	private String teacher;
	private int code;
	private int stCounter = 0;
	
	private String students[];
	
	static final int MAX_NUM_STUDENTS = 100;
	
	public Course (String title, String teacher, int code) {
		this.title = title;
		this.teacher = teacher;
		this.code = code;
		
		students = new String[MAX_NUM_STUDENTS];
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getTeacher() {
		return this.teacher;
	}
	
	public String getString() {
		return this.code + "," + this.title + "," + this.teacher;
	}
	
	public void setStudent(String studentId) {
		this.students[stCounter] = studentId;
		stCounter++;
	}
	
	public String getStudents() {
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < stCounter; i++) {
			sb.append(students[i] + "\n");
		}
		
		return sb.toString();
	}
	
}
