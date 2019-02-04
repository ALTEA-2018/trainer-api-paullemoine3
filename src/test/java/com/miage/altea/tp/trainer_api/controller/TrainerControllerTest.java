package com.miage.altea.tp.trainer_api.controller;

import com.miage.altea.tp.trainer_api.bo.Trainer;
import com.miage.altea.tp.trainer_api.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public class TrainerControllerTest {
    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTrainers_shouldCallTheService() {
        trainerController.getAllTrainers();

        verify(trainerService).getAllTrainers();
    }

    @Test
    void getTrainer_shouldCallTheService() {
        trainerController.getTrainer("Ash");

        verify(trainerService).getTrainer("Ash");
    }

    @Test
    void createTrainer_shouldCallTheService() {
        Trainer t = new Trainer();
        t.setName("Yolo");
        trainerController.createTrainer(t);

        verify(trainerService).createTrainer(t);
    }

    @Test
    void updatePutTrainer_shouldCallTheService() {
        Trainer t = new Trainer();
        t.setName("Yolo");
        trainerController.updateTrainer(t);

        verify(trainerService).updateTrainer(t);
    }

    @Test
    void trainerController_shouldBeAnnotated(){
        var controllerAnnotation =
                TrainerController.class.getAnnotation(RestController.class);
        assertNotNull(controllerAnnotation);

        var requestMappingAnnotation =
                TrainerController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/trainers"}, requestMappingAnnotation.value());
    }

    @Test
    void getAllTrainers_shouldBeAnnotated() throws NoSuchMethodException {
        var getAllTrainers =
                TrainerController.class.getDeclaredMethod("getAllTrainers");
        var getMapping = getAllTrainers.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/"}, getMapping.value());
    }

    @Test
    void getTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var getTrainer =
                TrainerController.class.getDeclaredMethod("getTrainer", String.class);
        var getMapping = getTrainer.getAnnotation(GetMapping.class);

        var pathVariableAnnotation = getTrainer.getParameters()[0].getAnnotation(PathVariable.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/{name}"}, getMapping.value());

        assertNotNull(pathVariableAnnotation);
    }

    @Test
    void createTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var createTrainer =
                TrainerController.class.getDeclaredMethod("createTrainer", Trainer.class);
        var postMapping = createTrainer.getAnnotation(PostMapping.class);

        var requestAnnotation = createTrainer.getParameters()[0].getAnnotation(RequestBody.class);

        assertNotNull(postMapping);
        assertArrayEquals(new String[]{"/"}, postMapping.value());

        assertNotNull(requestAnnotation);
    }

    @Test
    void updateTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var updatePutTrainer =
                TrainerController.class.getDeclaredMethod("updateTrainer", Trainer.class);
        var putMapping = updatePutTrainer.getAnnotation(PutMapping.class);

        var requestAnnotation = updatePutTrainer.getParameters()[0].getAnnotation(RequestBody.class);

        assertNotNull(putMapping);
        assertArrayEquals(new String[]{"/"}, putMapping.value());

        assertNotNull(requestAnnotation);
    }

    @Test
    void deleteTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var updatePutTrainer =
                TrainerController.class.getDeclaredMethod("deleteTrainer", String.class);
        var deleteMapping = updatePutTrainer.getAnnotation(DeleteMapping.class);

        var pathAnnotation = updatePutTrainer.getParameters()[0].getAnnotation(PathVariable.class);

        assertNotNull(deleteMapping);
        assertArrayEquals(new String[]{"/{name}"}, deleteMapping.value());

        assertNotNull(pathAnnotation);
    }
}
