package com.djmakes.a20211205_darrinjones_nycschools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.djmakes.a20211205_darrinjones_nycschools.R;
import com.djmakes.a20211205_darrinjones_nycschools.models.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private static final int SCHOOL_LIST_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    private List<School> mSchoolList;
    private OnSchoolListener mOnSchoolListListener;

    private SchoolRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public SchoolRecyclerViewAdapter(OnSchoolListener mOnSchoolListListener) {
        this.mOnSchoolListListener = mOnSchoolListListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case SCHOOL_LIST_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new SchoolListViewHolder(view, mOnSchoolListListener);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_loading_dots, parent, false);
                return new LoadingViewHolder(view);
            }
            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_search_exhausted, parent, false);
                return new SearchExhaustedViewHolder(view);
            }
            default: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new SchoolListViewHolder(view, mOnSchoolListListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();
        int itemViewType = getItemViewType(position);
        if (itemViewType == SCHOOL_LIST_TYPE) {
            ((SchoolListViewHolder) holder).schoolName.setText(mSchoolList.get(position).getSchool_name());
            ((SchoolListViewHolder) holder).schoolNeighborhood.setText(mSchoolList.get(position).getNeighborhood());
            ((SchoolListViewHolder) holder).schoolAddress.setText(mSchoolList.get(position).getLocation());

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSchoolList.get(position).getSchool_name().equals("Loading...")) {
            return LOADING_TYPE;
        } else if (mSchoolList.get(position).getSchool_name().equals("EXHAUSTED..")) {
            return EXHAUSTED_TYPE;
        }else if (position == mSchoolList.size() - 1
                && position != 0
                && !mSchoolList.get(position).getSchool_name().equals("EXHAUSTED..")) {
            return LOADING_TYPE;
        }else {
            return SCHOOL_LIST_TYPE;
        }
    }

    public void setQueryExhausted(){
        hideLoading();
        School exhaustedSchoolList = new School();
        exhaustedSchoolList.setSchool_name("EXHAUSTED...");
        mSchoolList.add(exhaustedSchoolList);
        notifyDataSetChanged();
    }

    public void hideLoading(){
        if (isLoading()){
            for (School schoolList: mSchoolList){
                if (schoolList.getSchool_name().equals("Loading...")){
                    mSchoolList.remove(schoolList);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            School schoolList = new School();
            schoolList.setSchool_name("Loading...");
            List<School> loadingList = new ArrayList<>();
            loadingList.add(schoolList);
            mSchoolList = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if (mSchoolList != null){
            if (mSchoolList.size() > 0){
                if (mSchoolList.get(mSchoolList.size()-1).equals("Loading...")){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (mSchoolList != null) {
            return mSchoolList.size();
        }
        return 0;
    }

    public void setSchoolList(List<School> schoolList) {
        mSchoolList = schoolList;
        notifyDataSetChanged();
    }

    public School getSelectedSchool(int position){
        if (mSchoolList != null){
            if (mSchoolList.size() > 0){
                return mSchoolList.get(position);
            }
        }
        return null;
    }
}
