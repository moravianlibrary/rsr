package cz.muni.kisk.rsr.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cz.muni.kisk.rsr.tokenizer.AuthorityRecord;
import cz.muni.kisk.rsr.tokenizer.AuthorityTokenizer;

public class AuthorityTokenizerTest {

	@Test
	public void testGetAuthorityRecords() {
		AuthorityTokenizer tokenizer = new AuthorityTokenizer();
		List<AuthorityRecord> result = tokenizer.getAuthorityRecords("Ján Novotný napísal dielo hodné jeho mena. Preto očakávame, že Zbierka povestí bude mať úspech.");
		assertTrue("Method should return 3 Authority records.", result.size() == 3);
		assertTrue("First Authority record should have probability 0", result.get(0).getProbability() == 0);
		assertTrue("Second Authority record should have probability -1", result.get(1).getProbability() == -1);
		assertTrue("Third Authority record should have probability 0", result.get(2).getProbability() == 0);
	}

}
