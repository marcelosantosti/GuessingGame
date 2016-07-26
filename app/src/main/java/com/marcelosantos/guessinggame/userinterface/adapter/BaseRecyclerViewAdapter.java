package com.marcelosantos.guessinggame.userinterface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marcelosantos.guessinggame.userinterface.adapter.holder.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcelo on 18/10/2015.
 */
public abstract class BaseRecyclerViewAdapter<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    private List<T> items = new ArrayList<T>();

    @Override
    public int getItemCount() {

        return items.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}