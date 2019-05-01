package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.CheckoutFor3Dart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HintFor3RepositoryTest {

    @Autowired
    private HintFor3Repository hintFor3Repository;

    @Before
    public void init() {
        CheckoutFor3Dart d11 = CheckoutFor3Dart.builder().score(100).checkout("20 D20 D20").build();
        CheckoutFor3Dart d15 = CheckoutFor3Dart.builder().score(150).checkout("T20 T18 D18").build();
        CheckoutFor3Dart d20 = CheckoutFor3Dart.builder().score(170).checkout("T20 T20 Bull").build();

        List<CheckoutFor3Dart> testCheckouts = new ArrayList(Arrays.asList(d11, d15, d20));

        hintFor3Repository.saveAll(testCheckouts);
    }

    @Test
    public void testHintFor3DartRepositoryReturnsCheckoutByScore() {

        CheckoutFor3Dart checkoutFor3DartByScore = hintFor3Repository.findCheckoutFor3DartsByScore(150);
        assertEquals("T20 T18 D18", checkoutFor3DartByScore.getCheckout());

    }

}