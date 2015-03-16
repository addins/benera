package org.addin.benera.app.act;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import org.addin.benera.app.R;
import org.addin.benera.app.db.CountriesDBContract;
import org.addin.benera.app.fra.FlagFragment;
import org.addin.benera.app.fra.PictureFragment;
import org.addin.benera.app.fra.ProfileFragment;

public class Detail extends FragmentActivity{
	
	CountriesPagerAdapter countriesPagerAdapter;
	ViewPager viewPager;
	
	static String data[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Intent intent = getIntent();
		Uri uri = intent.getData();
		String str = uri.toString();
		
		data=str.split(":");
		setTitle(data[1]);
		
		countriesPagerAdapter = new CountriesPagerAdapter(getSupportFragmentManager());
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(countriesPagerAdapter);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@SuppressLint("NewApi")
	private void setupActionBar() {
		if (android.os.Build.VERSION.SDK_INT >= 11)
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.detail, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class CountriesPagerAdapter extends FragmentStatePagerAdapter{

		public CountriesPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment=null;
			switch (arg0) {
			case 0:
//				title = "Flag";
				fragment = new FlagFragment();
				Bundle bundle = new Bundle();
				bundle.putString(CountriesDBContract.CountryEntry.COLUMN_NAME_COUNTRY_CODE, data[2]);
				fragment.setArguments(bundle);
				break;
			case 1:
//				title = "Profile";
				fragment = new ProfileFragment();
				Bundle bundle1 = new Bundle();
				bundle1.putString(CountriesDBContract.CountryEntry._ID, data[0]);
				fragment.setArguments(bundle1);
				break;
			case 2:
//				title = "Picture";
				fragment = new PictureFragment();
				Bundle bundle2 = new Bundle();
				bundle2.putString(CountriesDBContract.CountryEntry.COLUMN_NAME_COUNTRY_NAME, data[1].replaceAll("\\s","%20"));
				fragment.setArguments(bundle2);
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			String title = "";
			switch (position) {
			case 0:
				title = "Flag";
				break;
			case 1:
				title = "Profile";
				break;
			case 2:
				title = "Picture";
				break;
			}
			return title;
		}
		
	}

}
