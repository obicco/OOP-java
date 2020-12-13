package ticketing;

import java.util.Set;

import ticketing.IssueManager.UserClass;

public class User {
	
	private String username;
	private Set<IssueManager.UserClass> classes;
	
	public User(String username, Set<UserClass> classes) {
		this.username = username;
		this.classes = classes;
	}
	
	public Set<UserClass> getClasses() {
		return classes;
	}
	
	
}
