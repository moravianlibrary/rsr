package cz.muni.kisk.rsr.tokenizer;

import java.util.LinkedList;
import java.util.List;

public class AuthorityRecord {

	private Long id;
	private List<StringToken> words;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<StringToken> getWords() {
		return words;
	}
	public void addWord(StringToken word) {
		if (words == null) {
			words = new LinkedList<StringToken>();
		}
		words.add(word);
	}
	
	@Override
	public String toString() {
		return "AuthorityRecord [id=" + id + ", words=" + words + "]";
	}
}
