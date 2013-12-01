package cz.muni.kisk.rsr.tokenizer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorityTokenizer {
	
	private Pattern pattern;
	
	public AuthorityTokenizer() {
		pattern = Pattern.compile("([a-žA-Ž]+)|\\.|,");
	}

	public List<AuthorityRecord> getAuthorityRecords(String text) {
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
		removeEndCommas(result);
		assignProbability(result);
		
		return result;
	}
	
	private static void removeEndCommas(List<AuthorityRecord> records) {
		for (AuthorityRecord record : records) {
			List<StringToken> recordWords = record.getWords();
			StringToken lastWord = recordWords.get(recordWords.size() - 1);
			
			if (lastWord.getType() == StringTokenType.Comma) {
				recordWords.remove(lastWord);
			}
		}
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
	
	private List<StringToken> tokenize(String text) {
		List<StringToken> result = new LinkedList<StringToken>();
		Matcher matcher = pattern.matcher(text);
		
		int start = 0;
		
		while(matcher.find(start)) {
			StringToken token = new StringToken();
			token.setString(matcher.group());
			token.setOffset(matcher.start());
			token.setLength(matcher.end() - matcher.start());
			setTypeToToken(token);
			result.add(token);
			start = matcher.end();
		}
		return result;
	}
	
	private static void setTypeToToken(StringToken token) {
		String str = token.getString();
		if (Character.isLowerCase(str.charAt(0))) {
			token.setType(StringTokenType.LowerCaseWord);
		} else if (Character.isUpperCase(str.charAt(0))) {
			token.setType(StringTokenType.UpperCaseWord);
		} else if (".".equals(str)) {
			token.setType(StringTokenType.Dot);
		} else if (",".equals(str)) {
			token.setType(StringTokenType.Comma);
		}
	}
}
