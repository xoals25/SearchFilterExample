package com.example.searchfilterexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*
Adapter가 필터링 기능을 지원하기 위해서는 Filterable 인터페이스를 사용해야함.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<String> unFilteredlist; //필터링 되지 않은 리스트
    ArrayList<String> filteredList; // 필터링 된 리스트
    /*
    MainActivity에서 Adapter를 초기화할 때 데이터가 담긴 리스트를 받아서  필터링되지않은 리스트와
    필터링된리스트로 할당해준다.
    그렇게 하는 이유는 getFilter에서의 쓰임과 리사이클러뷰의 사이즈를 결정하는 getItemCount에서의 쓰임때문에
     */

    public RecyclerViewAdapter(Context context, ArrayList<String> list) {
        super();
        this.context = context;
        this.unFilteredlist = list;
        this.filteredList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(filteredList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textview);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            /**
             *
             * @param constraint : 입력된 스트링이라고 생각
             * @return
             */
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString(); //입력받은 constraint를 스트링으로 변환해준다.
                if(charString.isEmpty()) { //변환된 스트링이 비었다면, 필터링되지않은 리스트를 필터링된 리스트라고 본다.
                    filteredList = unFilteredlist;
                } else {
                    /*
                    그렇지 않다면 필터링되지않은 리스트를 하나하나 검색해서 일치하는 케이스에 대해
                    필터링중인 리스트에 추가한다.
                    이후 필터링중인 리스트를 필터링 될 리스트로 사용한다.
                     */
                    ArrayList<String> filteringList = new ArrayList<>(); //filteringList:필터링중인 리스트
                    for(String name : unFilteredlist) {
                        if(name.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    filteredList = filteringList;
                }
                /*
                FilterResults의 values로 필터링된 리스트를 넘긴다.
                 */
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }


            /*
            필터링이 완료되면 리사이클러뷰를 업데이트해주는 작업을 publishResults에서 수행한다.
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<String>)results.values;
                notifyDataSetChanged();
            }
        };
    }

}
