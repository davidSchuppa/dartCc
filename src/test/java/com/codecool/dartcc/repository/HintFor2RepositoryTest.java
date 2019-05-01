package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.CheckoutFor2Dart;
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
public class HintFor2RepositoryTest {

    @Autowired
    private HintFor2Repository hintFor2Repository;

    @Before
    public void init() {
        CheckoutFor2Dart d11 = CheckoutFor2Dart.builder().score(22).checkout("D11").build();
        CheckoutFor2Dart d15 = CheckoutFor2Dart.builder().score(30).checkout("D15").build();
        CheckoutFor2Dart d20 = CheckoutFor2Dart.builder().score(40).checkout("D20").build();

        List<CheckoutFor2Dart> testCheckouts = new ArrayList(Arrays.asList(d11, d15, d20));

        hintFor2Repository.saveAll(testCheckouts);
    }

    @Test
    public void testRepositoryReturnsCheckoutByScore() {

        CheckoutFor2Dart checkoutFor2DartsByScore = hintFor2Repository.findCheckoutFor2DartsByScore(40);
        assertEquals("D20", checkoutFor2DartsByScore.getCheckout());
    }

}