package com.miage.altea.tp.trainer_api.service;

import com.miage.altea.tp.trainer_api.bo.Trainer;
import com.miage.altea.tp.trainer_api.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Iterable<Trainer> getAllTrainers() {
        Iterable<Trainer> itTrainer = this.trainerRepository.findAll();
        return  itTrainer;
    }

    @Override
    public Trainer getTrainer(String name) {
        return this.trainerRepository.findById(name).isPresent() ? this.trainerRepository.findById(name).get() : null;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return this.trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainer(Trainer trainer){
        if(this.trainerRepository.findById(trainer.getName()).isPresent()){
            Trainer t = this.trainerRepository.save(trainer);
            return t;
        }
        return null;
    }

    @Override
    public void deleteTrainer(Trainer t){
        this.trainerRepository.delete(t);
    }
}
