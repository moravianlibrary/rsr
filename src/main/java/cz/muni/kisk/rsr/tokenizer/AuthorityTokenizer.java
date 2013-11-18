package cz.muni.kisk.rsr.tokenizer;

import java.util.LinkedList;
import java.util.List;

public class AuthorityTokenizer {

	public static List<AuthorityRecord> getAuthorityRecords(String text) {
		List<AuthorityRecord> result = new LinkedList<AuthorityRecord>();
		
		Long id = 0l;
		AuthorityRecord authorityRecord = null;
		boolean wasDot = true;
		
		List<StringToken> tokens = tokenize(text);
		for (StringToken stringToken : tokens) {
			if (authorityRecord == null && stringToken.getType() == StringTokenType.UpperCaseWord) {
				authorityRecord = new AuthorityRecord();
				authorityRecord.addWord(stringToken);
				if (wasDot) {
					authorityRecord.decProbability();
				}
			} else if (
				authorityRecord != null &&
					(
					stringToken.getType() == StringTokenType.UpperCaseWord ||
					stringToken.getType() == StringTokenType.Dot ||
					stringToken.getType() == StringTokenType.Comma
					)
				) {
				authorityRecord.addWord(stringToken);
			} else if (authorityRecord != null) {
				authorityRecord.setId(id++);
				result.add(authorityRecord);
				authorityRecord = null;
			}
			if (stringToken.getType() == StringTokenType.Dot) {
				wasDot = true;
			} else {
				wasDot = false;
			}
		}
		if (authorityRecord != null) {
			authorityRecord.setId(id++);
			result.add(authorityRecord);
		}
		assignProbability(result);
		
		return result;
	}
	
	private static void assignProbability(List<AuthorityRecord> records) {
		for (AuthorityRecord authorityRecord : records) {
			int numWords = -1;
			for (StringToken word : authorityRecord.getWords()) {
				if (word.getType() == StringTokenType.UpperCaseWord || word.getType() == StringTokenType.Dot) {
					++numWords;
				} else {
					authorityRecord.setProbability(authorityRecord.getProbability() + numWords);
					numWords = -1;
				}
			}
			if (numWords > 0) {
				authorityRecord.setProbability(authorityRecord.getProbability() + numWords);
			}
		}
	}
	
	private static List<StringToken> tokenize(String text) {
		List<StringToken> result = new LinkedList<StringToken>();
		StringToken token = new StringToken();
		StringBuilder word = new StringBuilder();
		int offset = -1;
		
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			
			if (Character.isWhitespace(c)) {
				if (word.length() == 0) {
					continue;
				} else {
					token.setString(word.toString());
					token.setOffset(offset);
					token.setLength(i - offset);
					result.add(token);
					// reset
					token = new StringToken();
					word = new StringBuilder();
					offset = -1;
				}
			} else if (Character.isLowerCase(c) || Character.isUpperCase(c)) {
				if (offset == -1) {
					offset = i;
					setTypeToToken(token, c);
				}
				word.append(c);
			} else if (c == '.' || c == ',') {
				if (word.length() == 0) {
					word.append(c);
					token.setString(word.toString());
					token.setOffset(i);
					token.setLength(1);
					setTypeToToken(token, c);
					result.add(token);
					// reset
					token = new StringToken();
					word = new StringBuilder();
					offset = -1;
				} else {
					// save before word
					token.setString(word.toString());
					token.setOffset(offset);
					token.setLength(i - offset);
					result.add(token);
					// create new token
					token = new StringToken();
					word = new StringBuilder();
					word.append(c);
					token.setString(word.toString());
					token.setOffset(offset);
					token.setLength(1);
					setTypeToToken(token, c);
					result.add(token);
					// reset
					token = new StringToken();
					word = new StringBuilder();
					offset = -1;
				}
			}
		}
		if (word.length() != 0) {
			token.setString(word.toString());
			token.setOffset(offset);
			token.setLength(text.length() - offset);
			result.add(token);
		}
		return result;
	}
	
	private static void setTypeToToken(StringToken token, char c) {
		if (Character.isLowerCase(c)) {
			token.setType(StringTokenType.LowerCaseWord);
		} else if (Character.isUpperCase(c)) {
			token.setType(StringTokenType.UpperCaseWord);
		} else if (c == '.') {
			token.setType(StringTokenType.Dot);
		} else if (c == ',') {
			token.setType(StringTokenType.Comma);
		}
	}
}
