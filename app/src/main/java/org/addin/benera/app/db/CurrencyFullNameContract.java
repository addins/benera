package org.addin.benera.app.db;

import android.provider.BaseColumns;

public class CurrencyFullNameContract {
	static final String SQL_CREATE_CURRENCIES_TABLE = "CREATE TABLE `"+
			CurrencyEntry.TABLE_NAME +"` (" +
			"  `"+CurrencyEntry._ID+"` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			"  `"+CurrencyEntry.COLUMN_NAME_CURRENCY_CODE+"` varchar(5) NOT NULL," +
			"  `"+CurrencyEntry.COLUMN_NAME_CURRENCY_NAME+"` varchar(80) NOT NULL)";
	
	static final String SQL_DELETE_CURRENCIES_TABLE =
		    "DROP TABLE IF EXISTS " + CurrencyEntry.TABLE_NAME;
	
	private CurrencyFullNameContract() {}
	
	public static abstract class CurrencyEntry implements BaseColumns{
		public static final String TABLE_NAME = "currencies";
	    public static final String COLUMN_NAME_CURRENCY_NAME = "currencyName";
	    public static final String COLUMN_NAME_CURRENCY_CODE = "currencyCode";
	}
	
}
