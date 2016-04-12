package com.louisgeek.louisstickylistheadersdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by louisgeek on 2016/4/7.
 */
public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<String> listStr=new ArrayList<>();
    private LayoutInflater inflater;
    private OnMyItemClickListener listener;

    public MyAdapter(Context context,List<String> listStr) {
        inflater = LayoutInflater.from(context);
        this.listStr=listStr;
        this.listStr = new ArrayList(listStr);//解决java.lang.UnsupportedOperationException
    }

    @Override
    public int getCount() {
        return listStr.size();
    }

    @Override
    public Object getItem(int position) {
        return listStr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.id_tv_item);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyItemClick(position,listStr.get(position));
                }
            });
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onMyItemLongClick(position, listStr.get(position));
                    return false;
                }
            });
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(listStr.get(position)+" pos:"+position);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.id_tv_head_item);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = listStr.get(position).subSequence(0, 1).charAt(0)+" pos:" + position;
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return listStr.get(position).subSequence(0, 1).charAt(0);
      /*  if (position<3){
            return 0;
        }else if (position<5){
            return 1;
        }else if (position<8){
            return 2;
        }else {
            return 3;
        }*/


    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }

    /**
     * 内部接口回调方法
     */
    public interface OnMyItemClickListener {
        void onMyItemClick(int position, Object object);
        void onMyItemLongClick(int position, Object object);
    }
    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }

    void addItem(){
        listStr.add("新增数据");
        notifyDataSetChanged();
    }
    void deleteItem(int position){
        listStr.remove(position);
        notifyDataSetChanged();
    }

}