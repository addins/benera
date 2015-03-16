package org.addin.benera.app.act;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;
import org.addin.benera.app.R;
import org.addin.benera.app.db.BeneraDBOpenHelper;
import org.addin.benera.app.db.CountriesDBContract.CountryEntry;

import java.util.Locale;

public class List extends ListActivity {

    private BeneraDBOpenHelper dbOpenHelper;
    private SQLiteDatabase database;

	private Cursor cursor;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);

		final EditText editText_countryname = (EditText) findViewById(R.id.editText_countryname);

		dbOpenHelper = new BeneraDBOpenHelper(getApplicationContext());
		database = dbOpenHelper.getReadableDatabase();

		final String[] columns = { CountryEntry._ID,
				CountryEntry.COLUMN_NAME_COUNTRY_NAME,
				CountryEntry.COLUMN_NAME_COUNTRY_CODE };

		final String sortOrder = CountryEntry.COLUMN_NAME_COUNTRY_NAME + " ASC";

		cursor = database.query(CountryEntry.TABLE_NAME, columns, null, null,
				null, null, sortOrder);

		int[] to = { R.id.countryName, R.id.countryName, R.id.countryCode };

		adapter = new SimpleCursorAdapter(getApplicationContext(),
				R.layout.list_item, cursor, columns, to,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		adapter.setFilterQueryProvider(new FilterQueryProvider() {

			@Override
			public Cursor runQuery(CharSequence constraint) {
				return database.query(CountryEntry.TABLE_NAME, columns,
						CountryEntry.COLUMN_NAME_COUNTRY_NAME + " like ?",
						new String[] { constraint + "%" }, null, null,
						sortOrder);
			}
		});
		setListAdapter(adapter);

		editText_countryname.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				adapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cur = ((SimpleCursorAdapter) l.getAdapter()).getCursor();
		cur.moveToPosition(position);
		int country_id = cur.getInt(0);
		String countryName = cur.getString(1);
		String countryCode = cur.getString(2).toLowerCase(Locale.ENGLISH);
		// Toast.makeText(getApplicationContext(),
		// country_id+":"+countryName+":"+countryCode,
		// Toast.LENGTH_SHORT).show();
		Intent intent = new Intent("", Uri.parse(country_id + ":" + countryName
				+ ":" + countryCode), List.this, Detail.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_update:
			if (!Splash.isOnline(getApplicationContext())) {
				Toast.makeText(getApplicationContext(),
						"No internet connection!", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent("", Uri.parse("update"), this,
						Splash.class);
				startActivity(intent);
				finish();
			}
			break;
		case R.id.action_info:
			AlertDialog alertDialog = new AlertDialog.Builder(
					this).create();
			alertDialog.setMessage("Datasource:\n" +
					"geonames.org\n" +
					"openexchangerates.org\n" +
					"\n" +
					"By:\n" +
					"TI-B 2010 Dev Team");
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			alertDialog.show();
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}

}
