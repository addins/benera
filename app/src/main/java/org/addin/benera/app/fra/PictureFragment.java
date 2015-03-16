package org.addin.benera.app.fra;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import org.addin.benera.app.R;
import org.addin.benera.app.common.TouchImageView;
import org.addin.benera.app.db.CountriesDBContract;

public class PictureFragment extends Fragment {
	
	ProgressBar progressBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_picture, container,
				false);
		final TouchImageView imageView = (TouchImageView) view
				.findViewById(R.id.imgViewPict);
		Bundle bundle = getArguments();
		String pictUrl = "http://maps.googleapis.com/maps/api/staticmap?center="
				+ bundle.getString(CountriesDBContract.CountryEntry.COLUMN_NAME_COUNTRY_NAME)
				+ "&zoom=6&size=640x400&scale=1&sensor=false";
		progressBar = (ProgressBar) view.findViewById(R.id.progBar);
		
		
		ImageLoader.getInstance().loadImage(pictUrl, new ImageLoadingListener() {
			
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
				imageView.setImageBitmap(arg2);
				progressBar.setVisibility(ProgressBar.INVISIBLE);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				progressBar.setContentDescription("Loading image canceled! "+arg0);
			}
		});
		
//		ImageGrabberTask imageGrabberTask = new ImageGrabberTask();
//		imageGrabberTask.setTaskHandler(new ImageGrabberTaskHandler() {
//
//			@Override
//			public void taskSuccessful(Bitmap bitmap) {
//				if(bitmap==null){
//					taskFailed();
//				}
//				imageView.setImageBitmap(bitmap);
//				imageView.setMaxZoom(8f);
//				progressBar.setVisibility(ProgressBar.INVISIBLE);
//			}
//
//			@Override
//			public void taskFailed() {
//				// TODO Auto-generated method stub
//				progressBar.setVisibility(ProgressBar.INVISIBLE);
//				Toast.makeText(getActivity().getBaseContext(),
//						"image loading failed!", Toast.LENGTH_LONG).show();
//			}
//		});
//		imageGrabberTask.execute(new HttpGet(pictUrl));
		return view;
	}
}
