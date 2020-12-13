package it.polito.oop.books;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Question {
	
	private String question;
	private Topic mainTopic;
	private Map<String, Boolean> answers = new HashMap<>();
	
	public Question (String question, Topic mainTopic) {
		this.question = question;
		this.mainTopic = mainTopic;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return mainTopic;
	}

	public void addAnswer(String answer, boolean correct) {
		answers.put(answer, correct);
	}
	
    @Override
    public String toString() {
        return question + " (" + mainTopic + ")";
    }

	public long numAnswers() {
	    return answers.size();
	}

	public Set<String> getCorrectAnswers() {
		Set<String> r = new HashSet<>();
		for (String a : answers.keySet()) {
			if (answers.get(a)) {
				r.add(a);
			}
		}
		return r;
	}

	public Set<String> getIncorrectAnswers() {
		Set<String> r = new HashSet<>();
		for (String a : answers.keySet()) {
			if (!answers.get(a)) {
				r.add(a);
			}
		}
		return r;
	}
}
