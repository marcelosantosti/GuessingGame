package com.marcelosantos.guessinggame.domain.service;

import android.content.Context;

import com.google.inject.Inject;
import com.marcelosantos.guessinggame.R;
import com.marcelosantos.guessinggame.domain.interfaces.repository.IAnimalRepository;
import com.marcelosantos.guessinggame.domain.interfaces.repository.ITraitRepository;
import com.marcelosantos.guessinggame.domain.interfaces.service.ITraitService;
import com.marcelosantos.guessinggame.domain.model.Animal;
import com.marcelosantos.guessinggame.domain.model.Trait;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class TraitService implements ITraitService {

    private ITraitRepository traitRepository;
    private IAnimalRepository animalRepository;
    private Context context;

    @Inject
    public TraitService(ITraitRepository traitRepository, IAnimalRepository animalRepository, Context context) {

        this.traitRepository = traitRepository;
        this.animalRepository = animalRepository;
        this.context = context;
    }

    @Override
    public void createTrait(Trait trait) throws SQLException {

        if (trait.getAnimal() != null) {

            this.animalRepository.create(trait.getAnimal());
            this.traitRepository.create(trait);
        }
    }

    public List<Trait> getOrderedTrait() throws SQLException {

        List<Trait> listTraitOrdered = this.traitRepository.getListTraitOrdered(Trait.ID_FIELD_NAME, true);

        //for some reason there is some traits being created without animal...
        //removing these traits
        for (Trait currentTrait : listTraitOrdered)
            this.clearTraits(currentTrait);

        return listTraitOrdered;
    }

    private void clearTraits(Trait trait) {

        for (int i = 0; i < trait.getChildTraits().size(); i++) {

            Trait currentChildTrait = (Trait)trait.getChildTraits().toArray()[i];

            if (currentChildTrait.getAnimal() == null)
                trait.getChildTraits().remove(currentChildTrait);
            else
                this.clearTraits(currentChildTrait);
        }
    }

    @Override
    public void createDefaultTrait() throws SQLException {

        Trait trait = new Trait();
        trait.setDescription(this.context.getString(R.string.lives_in_the_water));
        Animal animal = new Animal();
        animal.setName(this.context.getString(R.string.shark));
        trait.setAnimal(animal);

        this.createTrait(trait);
    }
}
