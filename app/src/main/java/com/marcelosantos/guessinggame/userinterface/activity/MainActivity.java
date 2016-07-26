package com.marcelosantos.guessinggame.userinterface.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.inject.Inject;
import com.marcelosantos.guessinggame.R;
import com.marcelosantos.guessinggame.domain.interfaces.repository.IAnimalRepository;
import com.marcelosantos.guessinggame.domain.interfaces.repository.ITraitRepository;
import com.marcelosantos.guessinggame.domain.interfaces.service.IAnimalService;
import com.marcelosantos.guessinggame.domain.interfaces.service.ITraitService;
import com.marcelosantos.guessinggame.domain.model.Animal;
import com.marcelosantos.guessinggame.domain.model.Trait;
import com.marcelosantos.guessinggame.infrastructure.common.AlertUtil;
import com.marcelosantos.guessinggame.infrastructure.common.SharedPreferencesUtil;
import com.marcelosantos.guessinggame.userinterface.adapter.QuestionAdapter;
import com.marcelosantos.guessinggame.userinterface.model.AskTraitQuestion;
import com.marcelosantos.guessinggame.userinterface.model.Info;
import com.marcelosantos.guessinggame.userinterface.model.Question;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Created by Marcelo on 17/10/2015.
 */

//using android annotations to inject views and event handling with roboguice to inject repositories and services
//using ORMLite for persistance
//the architeture is a variation of DDD for eric evans, althoug DDD is much more than an architeture :)
//the app was tested in a Moto Maxx with Android 5.0.2 and a Moto G (second edition) with Android 5.0.2 too
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
@RoboGuice
public class MainActivity extends BaseActivity {

    @Inject
    public IAnimalService animalService;
    @Inject
    public ITraitService traitService;
    @Inject
    public ITraitRepository traitRepository;
    @Inject
    public IAnimalRepository animalRepository;

    private List<Trait> listTrait;
    private List<Trait> listAskedTrait;
    private List<Question> listQuestion;

    private Trait currentTrait;
    private Trait correctTrait;
    private Animal currentAnimal;
    private Animal defaultAnimal;
    private Question currentQuestion;

    private boolean win;
    private boolean hitOnce;
    private int currentTraitIndex;
    private int currentChildTraitIndex;

    @Bean
    public QuestionAdapter questionAdapter;

    @ViewById
    public RecyclerView recyclerView;
    @ViewById
    public FloatingActionButton buttonNext;
    @ViewById
    public FloatingActionsMenu buttonYesNo;

    @AfterViews
    public void init() {

        try {

            super.configureActionBar();
            super.setTitle(super.getString(R.string.app_name));

            this.listAskedTrait = new ArrayList<>();

            this.createDefaultData();
            this.loadTrait();
            this.loadDefaultAnimal();

            this.createAdapter();

            this.showInitalAlert();
        }
        catch (Exception e) {

            super.catchException(e);
        }
    }

    @Click(R.id.buttonNext)
    public void onButtonNextClicked() {

        try {

            //// TODO: 20/10/2015
            //close keyboard when it´s opened
            //block previus edittext´s that had already been filled

            if (this.win) {

                this.restart();
            }
            else {

                if (this.currentQuestion.getObject() instanceof Info) {

                    this.addTrait();
                }
                else {

                    if (this.currentQuestion instanceof AskTraitQuestion) {

                        Trait askedTrait = (Trait)this.currentQuestion.getObject();

                        if (this.correctTrait != null) {

                            if (!this.correctTrait.getChildTraits().contains(askedTrait) && askedTrait.getAnimal() != null) {

                                this.correctTrait.getChildTraits().add(askedTrait);
                                askedTrait.setBaseTrait(this.correctTrait);
                            }
                        }
                        else
                            this.listTrait.add(askedTrait);

                        this.traitService.createTrait(askedTrait);

                        //restart when ask trait
                        this.restart();
                    }
                    else {

                        if (this.currentQuestion.getObject() instanceof Animal)
                            this.addAskTrait();
                    }
                }
            }
        }
        catch (Exception e) {

            super.catchException(e);
        }
    }

    @Click(R.id.buttonYes)
    public void onButtonYesClicked() {

        try {


            if (this.currentQuestion.getObject() instanceof Trait) {

                this.correctTrait = this.getCurrentTrait();
                this.currentChildTraitIndex = 0;

                //verify if the last visited trait has childs
                if (this.currentTrait.getChildTraits().size() == 0)
                    this.addAnimal();
                else
                    this.addTrait();
            }
            else {

                if (this.currentQuestion.getObject() instanceof Animal) {

                    this.addWin();
                }
            }

            this.toggleButtonYesNo();
        }
        catch (Exception e) {

            super.catchException(e);
        }
    }

    @Click(R.id.buttonNo)
    public void onButtonNoClicked() {

        try {

            if (this.currentQuestion.getObject() instanceof Trait) {

                //there is childs and there is one correct aswner
                if (this.getCurrentTrait().getChildTraits().size() - 1 > currentChildTraitIndex && this.correctTrait != null) {

                    this.addAskTrait();
                }
                else {

                    if (this.correctTrait != null) {

                        if (this.correctTrait.getChildTraits().size() - 1 > this.currentChildTraitIndex) {

                            currentChildTraitIndex++;
                            this.addTrait();
                        }
                        else {

                            //get previus trait to ask about that animal
                            this.currentTrait = this.correctTrait;
                            this.addAnimal();
                        }
                    }
                    else {

                        //if there asks to do
                        if (this.listAskedTrait.size() < this.listTrait.size())
                            this.addTrait();
                        else
                            this.addAnimal();
                    }
                }
            }
            else {

                //if not the animmal i asked i have to add the animal question
                if (this.currentQuestion.getObject() instanceof Animal) {

                    if (!this.currentQuestion.isAsk())
                        this.addAskAnimal();
                    else
                        this.addAskTrait();
                }
            }

            this.toggleButtonYesNo();
        }
        catch (Exception e) {

            super.catchException(e);
        }
    }

    @OptionsItem(R.id.actionRestart)
    public void onMenuRestartClicked() {

        //// TODO: 20/10/2015
        //close keyboard if it is opened

        try {

            this.toggleButtonYesNo();

            this.enableNextButton();
            this.restart();
        }
        catch (Exception e) {

            super.catchException(e);
        }
    }

    private void showInitalAlert() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);
        String key = super.getString(R.string.initial_alert_showed);

        boolean initialAlertShowed = sharedPreferencesUtil.getBoolean(key, false);

        if (!initialAlertShowed) {

            AlertUtil.showOkAlert(this, super.getString(R.string.welcome), super.getString(R.string.initial_alert));
            sharedPreferencesUtil.putBoolean(key, true);
        }
    }

    private void createDefaultData() throws SQLException {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);
        String key = super.getString(R.string.created_default_data);

        boolean createdDefaultData = sharedPreferencesUtil.getBoolean(key, false);

        if (!createdDefaultData) {

            this.animalService.createDefaultAnimal();
            this.traitService.createDefaultTrait();

            sharedPreferencesUtil.putBoolean(key, true);
        }
    }

    private void loadTrait() throws SQLException {

        ///// TODO: 22/10/2015
        //here i have to use a progressdialog to prevent blocking ui in while database processing

        this.listTrait = this.traitService.getOrderedTrait();
    }

    private void loadDefaultAnimal() throws SQLException {

        List<Animal> listDefaultAnimal = this.animalRepository.getAnimalByDefault(true);

        if (listDefaultAnimal.size() > 0)
            this.defaultAnimal = listDefaultAnimal.get(0);
    }

    public Trait getNextTrait() {

        Trait traitReturn = null;

        if (this.correctTrait != null && this.correctTrait.getChildTraits().size() > 0 && this.correctTrait.getChildTraits().size() > this.currentChildTraitIndex) {

            traitReturn = (Trait)this.correctTrait.getChildTraits().toArray()[this.currentChildTraitIndex];
        }
        else {

            if (this.listAskedTrait.size() < this.listTrait.size() && !hitOnce) {

                traitReturn = this.listTrait.get(this.currentTraitIndex);
                this.currentTraitIndex++;
                this.hitOnce = false;
            }
        }

        return traitReturn;
    }

    private void createAdapter() {

        this.listQuestion = new ArrayList<>();
        this.addFirstQuestion();
        this.questionAdapter.setItems(this.listQuestion);

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new FadeInAnimator());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.questionAdapter);
    }

    private void restart() throws SQLException {

        this.win = false;
        this.listAskedTrait.clear();
        this.currentTrait = null;
        this.currentAnimal = null;
        this.currentQuestion = null;
        this.currentTraitIndex = 0;
        this.correctTrait = null;
        this.hitOnce = false;
        this.currentTraitIndex = 0;

        this.listQuestion.clear();
        this.addFirstQuestion();
        this.toggleButtonYesNo();
        this.questionAdapter.notifyDataSetChanged();

        //loading traits again to get updated relationship
        this.loadTrait();
    }

    private void enableNextButton() {

        this.toggleButtonYesNo();
        this.buttonYesNo.setVisibility(View.GONE);
        this.buttonNext.setVisibility(View.VISIBLE);
    }

    private void addFirstQuestion() {

        Question firstQuestion = this.getInfoQuestion(super.getString(R.string.think_about_animal));
        this.currentQuestion = firstQuestion;
        this.listQuestion.add(firstQuestion);
    }

    private void addAskAnimal() {

        Question question = new Question();
        question.setObject(new Animal(" "));
        question.setAsk(true);
        this.addQuestion(question);

        this.enableNextButton();
    }

    private void addAskTrait() {

        AskTraitQuestion question = new AskTraitQuestion();
        Trait trait = new Trait(" ");
        trait.setAnimal((Animal) this.currentQuestion.getObject());
        question.setAsk(true);
        question.setObject(trait);
        question.setPreviusAnimal(this.currentAnimal);

        this.addQuestion(question);
    }

    private void addAnimal() {

        Question question = null;

        if (this.currentQuestion.getObject() instanceof Trait) {

            if (this.getCurrentTrait().getBaseTrait() == null && this.correctTrait == null) {

                question = this.getAnimalQuestion(this.defaultAnimal);
                this.currentAnimal = this.defaultAnimal;
            }
            else {

                question = this.getAnimalQuestion(this.currentTrait.getAnimal());
                this.currentAnimal = this.currentTrait.getAnimal();
            }
        }

        this.addQuestion(question);
    }

    private void addTrait() {

        Trait nextTrait = this.getNextTrait();
        this.currentTrait = nextTrait;

        Question question = null;

        this.listAskedTrait.add(this.currentTrait);
        this.currentAnimal = this.currentTrait.getAnimal();
        question = this.getTraitQuestion(this.currentTrait);

        this.buttonNext.setVisibility(View.GONE);
        this.buttonYesNo.setVisibility(View.VISIBLE);

        this.toggleButtonYesNo();

        this.addQuestion(question);
    }

    private void toggleButtonYesNo() {

        if (this.buttonYesNo.isExpanded())
            this.buttonYesNo.toggle();
    }

    private void addWin() {

        this.addQuestion(this.getInfoQuestion(super.getString(R.string.win_message)));
        this.win = true;
        this.toggleButtonYesNo();
        this.enableNextButton();
    }

    private void addQuestion(Question question) {

        if (question != null) {

            this.currentQuestion = question;
            this.questionAdapter.add(question, this.questionAdapter.getItemCount());
            this.recyclerView.scrollToPosition(this.questionAdapter.getItemCount() - 1);
        }
    }

    private Question getTraitQuestion(Trait trait) {

        Question question = new Question();
        question.setObject(trait);

        return question;
    }

    private Question getAnimalQuestion(Animal animal) {

        Question question = new Question();
        question.setObject(animal);

        return question;
    }

    private Question getInfoQuestion(String description) {

        Question question = new Question();
        Info info = new Info();
        info.setDescription(description);
        question.setObject(info);

        return question;
    }

    private Trait getCurrentTrait() {

        if (this.currentQuestion.getObject() instanceof Trait)
            return (Trait)this.currentQuestion.getObject();
        else
            return null;
    }
}
