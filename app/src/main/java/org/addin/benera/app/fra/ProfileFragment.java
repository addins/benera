package org.addin.benera.app.fra;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.addin.benera.app.R;
import org.addin.benera.app.db.BeneraDBOpenHelper;
import org.addin.benera.app.db.CountriesDBContract;
import org.addin.benera.app.db.CurrencyFullNameContract;

import java.util.Locale;

public class ProfileFragment extends Fragment {

	private static BeneraDBOpenHelper dbOpenHelper;
	private static SQLiteDatabase database;

	private Cursor countriesCursor;
	private Cursor currenciesCursor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);
		TextView capitalTextView = (TextView) rootView
				.findViewById(R.id.textViewCapital);
		TextView continentTextView = (TextView) rootView
				.findViewById(R.id.textViewContinent);
		TextView areaTextView = (TextView) rootView
				.findViewById(R.id.textViewArea);
		TextView currencyTextView = (TextView) rootView
				.findViewById(R.id.textViewCurrency);
		TextView languagesTextView = (TextView) rootView
				.findViewById(R.id.textViewLanguages);
		TextView populationTextView = (TextView) rootView
				.findViewById(R.id.textViewPopulation);

		Bundle bundle = getArguments();
		int id = Integer.parseInt(bundle.getString(CountriesDBContract.CountryEntry._ID));
		dbOpenHelper = new BeneraDBOpenHelper(getActivity());
		database = dbOpenHelper.getReadableDatabase();

		countriesCursor = database.query(CountriesDBContract.CountryEntry.TABLE_NAME, new String[] {
				CountriesDBContract.CountryEntry.COLUMN_NAME_CAPITAL,
				CountriesDBContract.CountryEntry.COLUMN_NAME_CONTINENT_NAME,
				CountriesDBContract.CountryEntry.COLUMN_NAME_AREA_IN_SQ_KM,
				CountriesDBContract.CountryEntry.COLUMN_NAME_CURRENCY_CODE,
				CountriesDBContract.CountryEntry.COLUMN_NAME_LANGUAGES,
				CountriesDBContract.CountryEntry.COLUMN_NAME_POPULATION }, CountriesDBContract.CountryEntry._ID
				+ " = ?", new String[] { id + "" }, null, null, null);

		countriesCursor.moveToFirst();
		currenciesCursor = database
				.query(CurrencyFullNameContract.CurrencyEntry.TABLE_NAME, new String[] {
						CurrencyFullNameContract.CurrencyEntry.COLUMN_NAME_CURRENCY_CODE,
						CurrencyFullNameContract.CurrencyEntry.COLUMN_NAME_CURRENCY_NAME },
						CurrencyFullNameContract.CurrencyEntry.COLUMN_NAME_CURRENCY_CODE + " = ?",
						new String[] { countriesCursor.getString(3) }, null,
						null, null);
		
		capitalTextView.setText(countriesCursor.getString(0));
		continentTextView.setText(countriesCursor.getString(1));
		areaTextView.setText(countriesCursor.getString(2));
		String curr = "";
		if(currenciesCursor!=null){
			if(currenciesCursor.moveToFirst())curr = currenciesCursor.getString(1);
		}
		 
		currencyTextView.setText(countriesCursor.getString(3)+"-"+curr);
		String[] lang = countriesCursor.getString(4).split(",");
		Locale locale;
		String langs = "";
		for (String l : lang) {
			locale = new Locale(l);
			langs += locale.getDisplayLanguage() + ", ";
		}
		languagesTextView.setText(langs.substring(0, langs.length() - 2));
		populationTextView.setText(countriesCursor.getString(5));
		currenciesCursor.close();
		countriesCursor.close();
		return rootView;
	}
}
