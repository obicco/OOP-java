package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	//Strings
	private String name;
	private String rectorFirst;
	private String rectorLast;
	
	//Classes
	private Student[] students;
	private Course[] courses;
	
	//Integers
	private int currentSt = 0;
	private int currentCr = 0;
	
	//Static integers
	static final int MAX_NUM_STUDENTS = 1000;
	static final int MAX_NUM_COURSES = 50;
	static final int FIRST_NUM_ID = 10000;
	static final int FIRST_NUM_COURSE = 10;
	
	
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name = name;
		
		students = new Student[MAX_NUM_STUDENTS];
		courses = new Course[MAX_NUM_COURSES];
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		this.rectorFirst = first;
		this.rectorLast = last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return
	 */
	public String getRector(){
		return this.rectorFirst + " " + this.rectorLast;
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	public int enroll(String first, String last){

		Student st = new Student(first, last, FIRST_NUM_ID + currentSt);
		this.students[ currentSt ] = st;
		
		currentSt++;
		
		return this.students[currentSt-1].getId();
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		return this.students[id-FIRST_NUM_ID].getString();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		
		Course cr = new Course(title, teacher, FIRST_NUM_COURSE + currentCr);
		this.courses[ currentCr ] = cr;
		
		currentCr++;
		
		return this.courses[currentCr - 1].getCode();
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		return this.courses[code-FIRST_NUM_COURSE].getString();
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		this.students[studentID - FIRST_NUM_ID].setCourse(courses[courseCode - FIRST_NUM_COURSE].getString());
		this.courses[courseCode - FIRST_NUM_COURSE].setStudent(students[studentID - FIRST_NUM_ID].getString());
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		return this.courses[courseCode - FIRST_NUM_COURSE].getStudents();
	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		return this.students[studentID - FIRST_NUM_ID].getCourses();
	}
}
