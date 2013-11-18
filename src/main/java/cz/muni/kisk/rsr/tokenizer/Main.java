package cz.muni.kisk.rsr.tokenizer;

import java.util.List;

public class Main {

	
	public static void main(String[] args) {
		List<AuthorityRecord> authorities =  AuthorityTokenizer.getAuthorityRecords("Skusobny text. Jan Novak");
		for (AuthorityRecord authorityRecord : authorities) {
			System.out.println(authorityRecord);
		}
	}
}
