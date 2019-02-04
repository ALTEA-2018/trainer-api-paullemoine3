package com.miage.altea.tp.trainer_api.controller;

import com.miage.altea.tp.trainer_api.bo.Trainer;
import com.miage.altea.tp.trainer_api.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    TrainerController(TrainerService trainerService){
        this.trainerService = trainerService;
    }

    @GetMapping(value="/")
    Iterable<Trainer> getAllTrainers(){
        return trainerService.getAllTrainers();
    }

    @GetMapping(value="/{name}")
    Trainer getTrainer(@PathVariable String name){
        return trainerService.getTrainer(name);
    }

    @PostMapping(value="/")
    Trainer createTrainer(@RequestBody Trainer trainer){
        return trainerService.createTrainer(trainer);
    }

    @PutMapping(value="/")
    Trainer updateTrainer(@RequestBody Trainer trainer){
        return trainerService.updateTrainer(trainer);
    }

    @DeleteMapping(value="/{name}")
    void deleteTrainer(@PathVariable String name){
        Trainer t = trainerService.getTrainer(name);
        trainerService.deleteTrainer(t);
    }


}
