package org.addin.benera.app.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import org.addin.benera.app.R;
import org.addin.benera.app.common.HttpTask;
import org.addin.benera.app.db.BeneraDBOpenHelper;
import org.addin.benera.app.db.CountriesDBContract;
import org.addin.benera.app.db.CurrencyFullNameContract;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Splash extends Activity {

	private static final String TAG = "Benera_";

	BeneraDBOpenHelper dbOpenHelper;
	SQLiteDatabase database;
    private String geonames_uname = "";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		dbOpenHelper = new BeneraDBOpenHelper(getApplicationContext());
		database = dbOpenHelper.getWritableDatabase();
		Intent intent = getIntent();
		String data = intent.getData() != null ? intent.getData().toString()
				: "";
		if ((data != null ? data : "").equalsIgnoreCase("update")&&isOnline(getApplicationContext())) {
			database.delete(CountriesDBContract.CountryEntry.TABLE_NAME, null, null);
			database.delete(CurrencyFullNameContract.CurrencyEntry.TABLE_NAME, null, null);
			database.delete("SQLITE_SEQUENCE", "name=?",
					new String[] { CountriesDBContract.CountryEntry.TABLE_NAME });
			database.delete("SQLITE_SEQUENCE", "name=?",
					new String[] { CurrencyFullNameContract.CurrencyEntry.TABLE_NAME });
		}
		Cursor cursor = database.rawQuery("select count(*) from "
				+ CountriesDBContract.CountryEntry.TABLE_NAME, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		// Toast.makeText(this, database.getVersion()+"",
		// Toast.LENGTH_LONG).show();
		if (count < 1 && isOnline(getApplicationContext())) {
			HttpTask httpTask = new HttpTask();
			httpTask.setTaskHandler(new HttpTask.HttpTaskHandler() {
				@Override
				public void taskSuccessful(JSONObject json) {
					ContentValues values = null;
					try {
						JSONArray nodes = json.getJSONArray("geonames");
						// Log.d(TAG, "Total nodes " + nodes.length());
						for (int i = 0; i < nodes.length(); i++) {
							values = new ContentValues();
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_COUNTRY_NAME,
									nodes.getJSONObject(i).getString(
											"countryName"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_CURRENCY_CODE,
									nodes.getJSONObject(i).getString(
											"currencyCode"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_FIPS_CODE,
									nodes.getJSONObject(i)
											.getString("fipsCode"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_COUNTRY_CODE,
									nodes.getJSONObject(i).getString(
											"countryCode"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_ISO_NUMERIC,
									nodes.getJSONObject(i).getString(
											"isoNumeric"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_CAPITAL, nodes
									.getJSONObject(i).getString("capital"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_CONTINENT_NAME,
									nodes.getJSONObject(i).getString(
											"continentName"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_CONTINENT,
									nodes.getJSONObject(i).getString(
											"continent"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_LANGUAGES,
									nodes.getJSONObject(i).getString(
											"languages"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_AREA_IN_SQ_KM,
									nodes.getJSONObject(i).getString(
											"areaInSqKm"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_POPULATION,
									nodes.getJSONObject(i).getString(
											"population"));
							values.put(
									CountriesDBContract.CountryEntry.COLUMN_NAME_ISO_ALPHA_3,
									nodes.getJSONObject(i).getString(
											"isoAlpha3"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_GEONAME_ID,
									nodes.getJSONObject(i).getLong("geonameId"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_NORTH, nodes
									.getJSONObject(i).getDouble("north"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_SOUTH, nodes
									.getJSONObject(i).getDouble("south"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_EAST, nodes
									.getJSONObject(i).getDouble("east"));
							values.put(CountriesDBContract.CountryEntry.COLUMN_NAME_WEST, nodes
									.getJSONObject(i).getDouble("west"));
							database.insert(CountriesDBContract.CountryEntry.TABLE_NAME, null,
									values);
						}
						Toast.makeText(getApplicationContext(), "data stored",
								Toast.LENGTH_LONG).show();
						// TODO invoke a method to start ListActivity for list
						// of countries
						// startListActivity();
					} catch (JSONException e) {
						e.printStackTrace();
                        Log.e(TAG,json.toString());
						taskFailed();
					} finally {
						startListActivity();
					}
				}

				@Override
				public void taskFailed() {
					String failMessage = "Failed retrieving data from geonames.org";
					Log.e(TAG, failMessage);
					AlertDialog alertDialog = new AlertDialog.Builder(
							Splash.this).create();
					alertDialog.setMessage(failMessage);
					alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Splash.this.finish();
								}
							});
					alertDialog.show();
				}
			});
			httpTask.execute(new HttpGet(
					"http://ws.geonames.org/countryInfoJSON?username="+geonames_uname));

			HttpTask httpTask2 = new HttpTask();
			httpTask2.setTaskHandler(new HttpTask.HttpTaskHandler() {

				@Override
				public void taskSuccessful(JSONObject json) {
					try {
						ContentValues values2 = null;
						// JSONObject nodes = null;
						Iterator<?> iterator = json.keys();
						while (iterator.hasNext()) {
							values2 = new ContentValues();
							String key = (String) iterator.next();
							String value = json.getString(key);
							values2.put(
									CurrencyFullNameContract.CurrencyEntry.COLUMN_NAME_CURRENCY_CODE,
									key);
							values2.put(
									CurrencyFullNameContract.CurrencyEntry.COLUMN_NAME_CURRENCY_NAME,
									value);
							database.insert(CurrencyFullNameContract.CurrencyEntry.TABLE_NAME, null,
									values2);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						taskFailed();
					} finally {
					}
				}

				@Override
				public void taskFailed() {
					String failMessage = "Failed retrieving data from openexchangerates.org";
					Log.e(TAG, failMessage);
					AlertDialog alertDialog = new AlertDialog.Builder(
							Splash.this).create();
					alertDialog.setMessage(failMessage);
					alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Splash.this.finish();
								}
							});
					alertDialog.show();
				}
			});
			httpTask2.execute(new HttpGet(
					"http://openexchangerates.org/api/currencies.json"));

		} else {
			startListActivity();
		}
	}

	private void startListActivity() {
		Intent intent = new Intent(getApplicationContext(), List.class);
		startActivity(intent);
		Splash.this.finish();
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.list, menu);
	// return true;
	// }
}
