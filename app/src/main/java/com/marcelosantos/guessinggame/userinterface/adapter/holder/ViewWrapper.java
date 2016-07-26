package com.marcelosantos.guessinggame.userinterface.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Marcelo on 18/10/2015.
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {

        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}