package org.addin.benera.app.fra;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import org.addin.benera.app.db.CountriesDBContract;
import org.addin.benera.app.R;

public class FlagFragment extends Fragment {

	ImageView imageViewFlag;
	ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_flag, container,
				false);
		Bundle bundle = getArguments();
		final String fileName = bundle
				.getString(CountriesDBContract.CountryEntry.COLUMN_NAME_COUNTRY_CODE);
		String flagUrl = "http://www.geonames.org/flags/x/" + fileName + ".gif";

		imageViewFlag = (ImageView) rootView.findViewById(R.id.imgViewFlag);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progBar);
		
		ImageLoader.getInstance().loadImage(flagUrl, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				progressBar.setContentDescription("Loading image...");
				progressBar.setVisibility(ProgressBar.VISIBLE);
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				progressBar.setContentDescription("Loading image failed! "+arg2.toString());
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				imageViewFlag.setImageBitmap(arg2);
				progressBar.setVisibility(ProgressBar.INVISIBLE);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				progressBar.setContentDescription("Loading image canceled! "+arg0);
			}
		});
		// final ProgressDialog progressDialog =
		// ProgressDialog.show(getActivity(), null, "Loading image...",
		// true);
//		ImageGrabberTask imageGrabberTask = new ImageGrabberTask();
//		imageGrabberTask.setTaskHandler(new ImageGrabberTaskHandler() {
//
//			@Override
//			public void taskSuccessful(Bitmap bitmap) {
//				if (bitmap == null) {
//					taskFailed();
//				}
//				imageViewFlag.setImageBitmap(bitmap);
//				progressBar.setVisibility(ProgressBar.INVISIBLE);
//			}
//
//			@Override
//			public void taskFailed() {
//				progressBar.setVisibility(ProgressBar.INVISIBLE);
//				Toast.makeText(getActivity().getBaseContext(),
//						"image loading failed!", Toast.LENGTH_LONG).show();
//			}
//		});
//		imageGrabberTask.execute(new HttpGet(flagUrl));

		return rootView;
	}
}
