package com.marcelosantos.guessinggame.userinterface.itemview;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marcelosantos.guessinggame.R;
import com.marcelosantos.guessinggame.domain.model.Animal;
import com.marcelosantos.guessinggame.domain.model.Trait;
import com.marcelosantos.guessinggame.infrastructure.common.LogUtil;
import com.marcelosantos.guessinggame.userinterface.model.AskTraitQuestion;
import com.marcelosantos.guessinggame.userinterface.model.Info;
import com.marcelosantos.guessinggame.userinterface.model.Question;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Marcelo on 18/10/2015.
 */
@EViewGroup(R.layout.item_question)
public class QuestionItemView extends LinearLayout {

    private Question question;

    @ViewById
    public TextView textViewDescription;
    @ViewById
    public AppCompatEditText editTextDescription;

    public QuestionItemView(Context context) {

        super(context);
    }

    public void bind(Question question) {

        try {

            this.question = question;

            if (!this.question.isAsk()) {

                this.editTextDescription.setVisibility(View.GONE);

                if (question.getObject() instanceof Info) {

                    Info info = (Info)question.getObject();

                    this.textViewDescription.setText(info.getDescription());
                }
                else {

                    if (question.getObject() instanceof Trait) {

                        Trait trait = (Trait)question.getObject();
                        this.textViewDescription.setText(String.format(super.getContext().getString(R.string.default_trait_message), trait.getDescription()));
                    }
                    else {

                        if (question.getObject() instanceof Animal) {

                            Animal animal = (Animal)question.getObject();
                            this.textViewDescription.setText(String.format(super.getContext().getString(R.string.default_animal_message), animal.getName()));
                        }
                    }
                }
            }
            else {

                if (question.getObject() instanceof Animal)
                    this.textViewDescription.setText(super.getContext().getString(R.string.what_was_the_animal));
                else {

                    if (question.getObject() instanceof Trait) {

                        Trait trait = (Trait)question.getObject();

                        Animal previusAnimal = ((AskTraitQuestion)question).getPreviusAnimal();
                        //default animal name
                        String animalName = super.getContext().getString(R.string.shark);

                        if (previusAnimal != null)
                            animalName = previusAnimal.getName();

                        this.textViewDescription.setText(String.format(super.getContext().getString(R.string.a_animal_does), trait.getAnimal().getName(), animalName));
                    }
                }

                this.editTextDescription.setText("");
                this.editTextDescription.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e) {

            LogUtil.catchException(super.getContext(), e);
        }
    }

    @TextChange(R.id.editTextDescription)
    public void onEditTextDescriptionTextChanged() {

        try {

            if (this.question.getObject() instanceof Animal) {

                Animal animal = (Animal)this.question.getObject();
                animal.setName(this.editTextDescription.getText().toString());
            }
            else {

                Trait trait = (Trait)this.question.getObject();
                trait.setDescription(this.editTextDescription.getText().toString());
            }
        }
        catch (Exception e) {

            LogUtil.catchException(super.getContext(), e);
        }
    }
}
