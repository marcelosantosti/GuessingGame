package com.marcelosantos.guessinggame;

import com.google.inject.AbstractModule;
import com.marcelosantos.guessinggame.domain.interfaces.repository.IAnimalRepository;
import com.marcelosantos.guessinggame.domain.interfaces.repository.ITraitRepository;
import com.marcelosantos.guessinggame.domain.interfaces.service.IAnimalService;
import com.marcelosantos.guessinggame.domain.interfaces.service.ITraitService;
import com.marcelosantos.guessinggame.domain.service.AnimalService;
import com.marcelosantos.guessinggame.domain.service.TraitService;
import com.marcelosantos.guessinggame.infrastructure.data.AnimalRepository;
import com.marcelosantos.guessinggame.infrastructure.data.TraitRepository;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class GuessingGameModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IAnimalService.class).to(AnimalService.class);
        bind(IAnimalRepository.class).to(AnimalRepository.class);

        bind(ITraitService.class).to(TraitService.class);
        bind(ITraitRepository.class).to(TraitRepository.class);
    }
}
