package org.addin.benera.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BeneraDBOpenHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Benera.db";
	
	public BeneraDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CountriesDBContract.SQL_CREATE_COUNTRIES_TABLE);
		db.execSQL(CurrencyFullNameContract.SQL_CREATE_CURRENCIES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(CountriesDBContract.SQL_DELETE_COUNTRIES_TABLE);
        db.execSQL(CurrencyFullNameContract.SQL_DELETE_CURRENCIES_TABLE);
        onCreate(db);
	}
	
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		super.close();
	}

}