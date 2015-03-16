package org.addin.benera.app.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;

public class HttpTask extends AsyncTask<HttpUriRequest, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(HttpUriRequest... params) {
		HttpUriRequest request = params[0];
		HttpClient client = new DefaultHttpClient();
		JSONObject json = null;
		try {
			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}

			JSONTokener tokener = new JSONTokener(builder.toString());
			json = new JSONObject(tokener);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		if(result!=null){
			taskHandler.taskSuccessful(result);
		}else {
			taskHandler.taskFailed();
		}
	}

	public static interface HttpTaskHandler {
		void taskSuccessful(JSONObject json);
		void taskFailed();
	}
	
	private HttpTaskHandler taskHandler;
	
	public void setTaskHandler(HttpTaskHandler taskHandler){
		this.taskHandler = taskHandler;
	}

}
