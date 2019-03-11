package com.miage.altea.tp.trainer_api.controller;

import com.miage.altea.tp.trainer_api.bo.Pokemon;
import com.miage.altea.tp.trainer_api.bo.Trainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Test
    public void trainerController_shouldBeInstanciated(){
        assertNotNull(controller);
    }

    @Test
    public void getTrainer_withNameAsh_shouldReturnAsh() {
        var ash = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonType());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    public void getAllTrainers_shouldReturnAshAndMisty() {
        var trainers = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Ash", trainers[0].getName());
        assertEquals("Misty", trainers[1].getName());
    }

    @Test
    public void createTrainer_shouldReturnAshTest() {
        Trainer t = new Trainer();
        t.setName("AshTest");
        List<Pokemon> team = new ArrayList<>();
        Pokemon p = new Pokemon();
        p.setId(1);
        p.setPokemonType(25);
        p.setLevel(18);
        team.add(p);
        t.setTeam(team);

        this.restTemplate.withBasicAuth(username, password).postForEntity("http://localhost:" + port + "/trainers/", t, Trainer.class);
        var ashTest =this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/AshTest", Trainer.class);
        assertNotNull(ashTest);
        assertEquals("AshTest", ashTest.getName());
        assertEquals(1, ashTest.getTeam().size());

        assertEquals(25, ashTest.getTeam().get(0).getPokemonType());
        assertEquals(18, ashTest.getTeam().get(0).getLevel());
    }

    @Test
    public void updateTrainer_shouldReturnUpdateAsh() {
        Trainer t = new Trainer();
        t.setName("Ash");
        List<Pokemon> team = new ArrayList<>();
        Pokemon p = new Pokemon();
        p.setPokemonType(22);
        p.setLevel(12);
        team.add(p);
        t.setTeam(team);

        this.restTemplate.withBasicAuth(username, password).put("http://localhost:" + port + "/trainers/", t, Trainer.class);
        var ash =this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
                assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(22, ash.getTeam().get(0).getPokemonType());
        assertEquals(12, ash.getTeam().get(0).getLevel());
    }

    @Test
    public void deleteTrainer_shouldReturnUpdateAsh() {
        this.restTemplate.withBasicAuth(username, password).delete("http://localhost:" + port + "/trainers/Ash");
        var ash =this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNull(ash);
    }
}
