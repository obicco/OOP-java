package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TheoryChapter extends Chapter {
	
	private String text;
	private Set<Topic> topicSet = new HashSet<>();
	
	public TheoryChapter(String title, int numPages, String text) {
		super(title, numPages);
		this.text = text;
	}

    public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text = text;
    }


	public List<Topic> getTopics() {
		List<Topic> lst = new ArrayList<>(topicSet);
		Collections.sort(lst);
		return lst;
	}
    
    public void addTopic(Topic topic) {
    	if(!topicSet.contains(topic)) {
    		topicSet.add(topic);
    		for (Topic t : topic.getSubTopics()) {
    			addTopic(t);
    		}
    	}
    }
    
}
