package com.ankit.carorder.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankit.carorder.R;
import com.ankit.carorder.dbmanager.model.Order;

public class OrderAdapter extends BaseAdapter {
	List<Order> orders;
	int status;
	Context context;
	private ViewHolder holder;
	public OrderAdapter(List<Order> pendingOrderValues, int status, Context context) {
		super();
		this.orders = pendingOrderValues;
		this.status = status;
		this.context = context;
	}

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Object getItem(int position) {
		return orders.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.order_list_item_layout,
					parent, false);
			holder = new ViewHolder();
			holder.model = (TextView) convertView
					.findViewById(R.id.orderModel);
			holder.position = position;
			holder.time = (TextView) convertView
					.findViewById(R.id.orderTime);
			holder.carImage = (ImageView) convertView
					.findViewById(R.id.orderCarImageView);

			convertView.setTag(holder);
			//holder.carImage.setTag(holder);

		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.model.setText(orders.get(position).getModelName());
		holder.time.setText(orders.get(position).getTime());
		Log.d("Color", "Color"+orders.get(position).getColor());
		if(orders.get(position).getColor().equalsIgnoreCase("Black")){
			holder.carImage.setImageResource(R.drawable.ic_launcher_black);
			holder.model.setTextColor(context.getResources().getColor(android.R.color.black));
		}else if(orders.get(position).getColor().equalsIgnoreCase("Red")){
			holder.carImage.setImageResource(R.drawable.ic_launcher_red);
			holder.model.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
		}else{
			holder.carImage.setImageResource(R.drawable.ic_launcher);
			holder.model.setTextColor(context.getResources().getColor(android.R.color.holo_blue_bright));
		}
		return convertView;
	}
	private static class ViewHolder {
		TextView model;
		ImageView carImage;
		TextView time;
		int position;
	}
public void refreshData(List<Order> list){
	this.orders = list;
}
}
