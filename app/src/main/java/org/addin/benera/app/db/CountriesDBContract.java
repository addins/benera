package org.addin.benera.app.db;

import android.provider.BaseColumns;

public class CountriesDBContract {
	
	static final String SQL_CREATE_COUNTRIES_TABLE = "CREATE TABLE `"+
			CountryEntry.TABLE_NAME +"` (" +
			"  `"+CountryEntry._ID+"` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_COUNTRY_NAME+"` varchar(80) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_CURRENCY_CODE+"` varchar(5) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_FIPS_CODE+"` varchar(5) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_COUNTRY_CODE+"` varchar(5) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_ISO_NUMERIC+"` varchar(5) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_CAPITAL+"` varchar(80) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_CONTINENT_NAME+"` varchar(80) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_CONTINENT+"` varchar(5) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_LANGUAGES+"` varchar(120) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_AREA_IN_SQ_KM+"` varchar(32) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_POPULATION+"` varchar(80) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_ISO_ALPHA_3+"` varchar(5) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_GEONAME_ID+"` int(32) NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_NORTH+"` double NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_SOUTH+"` double NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_EAST+"` double NOT NULL," +
			"  `"+CountryEntry.COLUMN_NAME_WEST+"` double NOT NULL)";
	
	static final String SQL_DELETE_COUNTRIES_TABLE =
		    "DROP TABLE IF EXISTS " + CountryEntry.TABLE_NAME;
	
	private CountriesDBContract() {}
	
	public static abstract class CountryEntry implements BaseColumns{
		public static final String TABLE_NAME = "countries";
	    public static final String COLUMN_NAME_COUNTRY_NAME = "countryName";
	    public static final String COLUMN_NAME_CURRENCY_CODE = "currencyCode";
	    public static final String COLUMN_NAME_FIPS_CODE = "fipsCode";
	    public static final String COLUMN_NAME_COUNTRY_CODE = "countryCode";
	    public static final String COLUMN_NAME_ISO_NUMERIC = "isoNumeric";
	    public static final String COLUMN_NAME_CAPITAL = "capital";
	    public static final String COLUMN_NAME_CONTINENT_NAME = "continentName";
	    public static final String COLUMN_NAME_CONTINENT = "continent";
	    public static final String COLUMN_NAME_LANGUAGES = "languages";
	    public static final String COLUMN_NAME_AREA_IN_SQ_KM = "areaInSqKm";
	    public static final String COLUMN_NAME_POPULATION = "population";
	    public static final String COLUMN_NAME_ISO_ALPHA_3 = "isoAlpha3";
	    public static final String COLUMN_NAME_GEONAME_ID = "geonameId";
	    public static final String COLUMN_NAME_NORTH = "north";
	    public static final String COLUMN_NAME_SOUTH = "south";
	    public static final String COLUMN_NAME_EAST = "east";
	    public static final String COLUMN_NAME_WEST = "west";
	}
	
}
