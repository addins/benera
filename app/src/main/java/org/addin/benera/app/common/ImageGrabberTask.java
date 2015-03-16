package org.addin.benera.app.common;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class ImageGrabberTask extends AsyncTask<HttpUriRequest, Boolean, Bitmap> {

	@Override
	protected Bitmap doInBackground(HttpUriRequest... params) {
		HttpUriRequest request = params[0];
		HttpClient client = new DefaultHttpClient();
		Bitmap bitmap = null;
		try {
			HttpResponse response = client.execute(request);
			bitmap = BitmapFactory.decodeStream(new BufferedInputStream(response.getEntity().getContent()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if(result!=null){
			taskHandler.taskSuccessful(result);
		}else {
			taskHandler.taskFailed();
		}
	}

	public static interface ImageGrabberTaskHandler {
		void taskSuccessful(Bitmap bitmap);
		void taskFailed();
	}
	
	private ImageGrabberTaskHandler taskHandler;
	
	public void setTaskHandler(ImageGrabberTaskHandler taskHandler){
		this.taskHandler = taskHandler;
	}

}
