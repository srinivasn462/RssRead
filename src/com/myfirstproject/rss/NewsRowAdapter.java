package com.myfirstproject.rss;

import java.util.List;

import com.getquery.fragmenttab.R;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsRowAdapter extends ArrayAdapter<Item> {

	private Activity activity;
	private List<Item> items;
	private Item objBean;
	private int row;

	public NewsRowAdapter(Activity act, int resource, List<Item> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.items = arrayList;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		holder.tvTitle = (TextView) view.findViewById(R.id.tvtitle);
		holder.tvDesc = (TextView) view.findViewById(R.id.tvdesc);
		holder.tvDate = (TextView) view.findViewById(R.id.tvdate);
		holder.imgView = (ImageView) view.findViewById(R.id.image);

		holder.imgView.setBackgroundResource(R.drawable.ic_launcher);
		holder.pbar = (ProgressBar) view.findViewById(R.id.pbar);

		if (holder.tvTitle != null && null != objBean.getTitle()
				&& objBean.getTitle().trim().length() > 0) {
			holder.tvTitle.setText(Html.fromHtml(objBean.getTitle()));
		}
		if (holder.tvDesc != null && null != objBean.getDesc()
				&& objBean.getDesc().trim().length() > 0) {
			holder.tvDesc.setText(Html.fromHtml(objBean.getDesc()));
		}

		return view;
	}

	public class ViewHolder {

		public TextView tvTitle, tvDesc, tvDate;
		private ImageView imgView;
		private ProgressBar pbar;

	}

}