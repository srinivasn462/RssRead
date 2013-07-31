package com.myfirstproject.rss;

import java.util.List;

import com.getquery.fragmenttab.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RssNewsFeed extends Activity{
	private static final String rssFeed = "http://www.freewarefiles.com/rss/topten.xml";

	List<Item> arrayOfList;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_layout);

		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Item item = arrayOfList.get(position);
				Intent intent = new Intent(RssNewsFeed.this, DetailActivity.class);
				intent.putExtra("link", item.getLink());
				Toast.makeText(RssNewsFeed.this, item.getLink(), Toast.LENGTH_SHORT).show();
				startActivity(intent);
				
			}
			
			
			
			
		});

		if (Utils.isNetworkAvailable(RssNewsFeed.this)) {
			new MyTask().execute(rssFeed);
		} else {
			Toast.makeText(RssNewsFeed.this, "no internet connection", Toast.LENGTH_SHORT).show();
		}

	}

	// My AsyncTask start...

	class MyTask extends AsyncTask<String, Void, Void> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(RssNewsFeed.this);
			pDialog.setMessage("Loading...");
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			arrayOfList = new NamesParser().getData(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == arrayOfList || arrayOfList.size() == 0) {
				showToast("No data found from web!!!");
				RssNewsFeed.this.finish();
			} else {

				setAdapterToListview();

			}

		}
	}

	/*@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Item item = arrayOfList.get(position);
		Intent intent = new Intent(RssNewsFeed.this, DetailActivity.class);
		intent.putExtra("url", item.getLink());
		intent.putExtra("title", item.getTitle());
		intent.putExtra("description", item.getDesc());
		startActivity(intent);
	}
*/
	public void setAdapterToListview() {
		NewsRowAdapter objAdapter = new NewsRowAdapter(RssNewsFeed.this,
				R.layout.row, arrayOfList);
		listView.setAdapter(objAdapter);
	}

	public void showToast(String msg) {

	}
}
