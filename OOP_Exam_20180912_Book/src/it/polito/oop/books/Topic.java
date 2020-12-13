package it.polito.oop.books;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Topic implements Comparable<Topic> {
	
	private String keyword;
	private Set<Topic> subTopics = new TreeSet<>();		//Insieme
	
	public Topic(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {
		return subTopics.add(topic);		//Se l'inserimento va a buon fine, restituisce true, altrimenti false
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return new ArrayList<>(subTopics);
	}

	@Override
	public int compareTo(Topic altro) {
		return this.keyword.compareTo(altro.keyword);
	}
}
