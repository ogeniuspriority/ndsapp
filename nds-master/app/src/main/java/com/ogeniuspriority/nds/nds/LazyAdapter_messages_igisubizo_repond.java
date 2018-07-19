package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter_messages_igisubizo_repond extends BaseAdapter {

	private Activity activity;
	private String[] sender_names_local;
	private String[] reg_dates_local;
	private String[] Query_response_data_local;
	private String[] sender_ids_local;
	private String[] query_reponse_local_id_local;
	private String[] query_reponse_remote_id_local;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter_messages_igisubizo_repond(Activity a, String[] sender_names,String[] reg_dates,String[] Query_response_data,String[] sender_ids, String[] query_reponse_local_id,String[] query_reponse_remote_id) {
		activity = a;
		sender_names_local = sender_names;
		reg_dates_local = reg_dates;
		Query_response_data_local = Query_response_data;
		sender_ids_local=sender_ids;
		query_reponse_local_id_local=query_reponse_local_id;
		query_reponse_remote_id_local=query_reponse_remote_id;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return sender_names_local.length;
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
			vi = inflater.inflate(R.layout.message_answers_respond, null);
		// ---textId---------------------------
		TextView official_names = (TextView) vi.findViewById(R.id.bon4);
		TextView query_response_regdate = (TextView) vi.findViewById(R.id.iubimsg);
		TextView query_response_data = (TextView) vi.findViewById(R.id.tView5);
		official_names.setText(sender_names_local[position]);
		query_response_regdate.setText(reg_dates_local[position]);
		query_response_data.setText(Query_response_data_local[position]);
		//------------------------------
		ImageView del_me_if = (ImageView) vi.findViewById(R.id.del_me_if);
		if(sender_ids_local[position].equalsIgnoreCase(NDS_messages_respond.MYRemoteId)){
			del_me_if.setVisibility(View.VISIBLE);
			del_me_if.setTag(query_reponse_remote_id_local[position]);
		}else{
			del_me_if.setVisibility(View.GONE);
		}




		// /------------------------
		//TextView text = (TextView) vi.findViewById(R.id.text);
		//TextView textTr = (TextView) vi.findViewById(R.id.textId);
		//ImageView image = (ImageView) vi.findViewById(R.id.image);

		//imageLoader.DisplayImage(data[position], image);
		return vi;
	}
}
