package org.helianto.task.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

/**
 * 
 * @author mauriciofernandesdecastro
 *
 */
public class FolderReadAdapterTests {

	@Test
	public void constructor() {
		int id = 1;
		int categoryId = 10;
		String folderCode = "CODE";
		String folderName = "NAME";
		String folderDecorationUrl = "URL";
		String numberPrefix = "PREF01";
		int numberSize = 4;
		char contentType = 'X';
	    char activityState = 'A';
		FolderReadAdapter adapter = new FolderReadAdapter(id, categoryId, folderCode, folderName
				, folderDecorationUrl, contentType);
		assertEquals(1, adapter.getId());
		assertEquals(10, adapter.getCategoryId());
		assertEquals("CODE", adapter.getFolderCode());
		assertEquals("NAME", adapter.getFolderName());
		assertEquals("URL", adapter.getFolderDecorationUrl());
		assertEquals('X', adapter.getContentType());
		assertEquals("", adapter.getNumberPattern());
		adapter = new FolderReadAdapter(id, categoryId, folderCode, folderName
				, folderDecorationUrl, contentType, numberPrefix, numberSize, activityState);
		assertEquals(1, adapter.getId());
		assertEquals(10, adapter.getCategoryId());
		assertEquals("CODE", adapter.getFolderCode());
		assertEquals("NAME", adapter.getFolderName());
		assertEquals("URL", adapter.getFolderDecorationUrl());
		assertEquals('X', adapter.getContentType());
		
		assertEquals("PREF01", adapter.getNumberPrefix());
		assertEquals(4, adapter.getNumberSize());
		assertEquals('A', adapter.getActivityState());
		assertEquals("'PREF01'0000", adapter.getNumberPattern());
		assertEquals("PREF010153", adapter.generateCode(adapter.getNumberPattern(), 153L));
		
		adapter.setReferenceDate(new Date(100000000));
		assertEquals("PREF010153/1970", adapter.generateCode(adapter.getNumberPattern(), 153L));
		
		adapter = new FolderReadAdapter(id, categoryId, folderCode, folderName
				, folderDecorationUrl, contentType, "'OLD'000", numberSize, activityState);
		assertEquals("'OLD'000", adapter.getNumberPattern());
	}

}
