package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private String[] Images_local;
	private String[] Names_local;
	private String[] message;
	private String[] Time_local;
	private String[] Posts_local;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, String[] Images, String[] Names, String[] Time,String[] Posts) {
		activity = a;
		Images_local = Images;
		Names_local = Names;
		Time_local = Time;
		Posts_local=Posts;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return Images_local.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.forum_main_feed, null);
		// ---textId---------------------------


		// /------------------------

		//TextView text = (TextView) vi.findViewById(R.id.text);
		//TextView textTr = (TextView) vi.findViewById(R.id.textId);
		//ImageView image = (ImageView) vi.findViewById(R.id.image);

		//imageLoader.DisplayImage(data[position], image);
		return vi;
	}
}
