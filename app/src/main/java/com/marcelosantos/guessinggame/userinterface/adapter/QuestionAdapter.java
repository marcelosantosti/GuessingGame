package com.marcelosantos.guessinggame.userinterface.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.marcelosantos.guessinggame.userinterface.adapter.holder.ViewWrapper;
import com.marcelosantos.guessinggame.userinterface.itemview.QuestionItemView;
import com.marcelosantos.guessinggame.userinterface.itemview.QuestionItemView_;
import com.marcelosantos.guessinggame.userinterface.model.Question;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by Marcelo on 18/10/2015.
 */
@EBean
public class QuestionAdapter extends BaseRecyclerViewAdapter<Question, QuestionItemView> {

    @RootContext
    public Context context;

    @Override
    protected QuestionItemView onCreateItemView(ViewGroup parent, int viewType) {

        return QuestionItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<QuestionItemView> viewHolder, int position) {

        QuestionItemView view = viewHolder.getView();
        Question question = getItems().get(position);

        view.bind(question);
    }

    public void add(Question question, int position) {

        super.getItems().add(position, question);
        super.notifyItemInserted(position);
    }
}