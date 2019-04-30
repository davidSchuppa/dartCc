package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.CheckoutFor2Dart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HintFor2Repository extends JpaRepository<CheckoutFor2Dart, Integer> {

    CheckoutFor2Dart findCheckoutFor2DartsByScore(int score);
}
