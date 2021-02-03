package com.waes.diffapi;

import com.waes.diffapi.domain.Diff;
import com.waes.diffapi.repository.DiffReactiveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DiffApplication.class)
public class MiscTests {

    @Autowired
    DiffReactiveRepository repository;

    @Test
    public void givenValue_whenFindAllByValue_thenFindAccount() {
        /* repository.save(Diff.builder("1", "LEFT", "RIGHT", null)).block();
        Mono<Diff> diffMono = repository.findById("1");

        StepVerifier
                .create(diffMono)
                .assertNext(diff -> {
                    assertEquals("RIGHT", diff.getRightElement());
                    assertNotNull(diff.getId());
                })
                .expectComplete()
                .verify();*/
    }

    @Test
    public void givenId_whenValueExists_thenReturnDiff() {

        repository.save(Diff.builder().id("5").leftElement("LEFT").rightElement("RIGHT").build())
                .block();

        Mono<Diff> diffMono = repository.findById("5");

        StepVerifier
                .create(diffMono)
                .assertNext(diff -> {
                    assertEquals("RIGHT", diff.getRightElement());
                    assertNotNull(diff.getId());
                })
                .expectComplete()
                .verify();

    }

}
