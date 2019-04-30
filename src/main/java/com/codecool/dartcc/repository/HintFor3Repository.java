package com.codecool.dartcc.repository;

import com.codecool.dartcc.model.CheckoutFor3Dart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HintFor3Repository extends JpaRepository<CheckoutFor3Dart, Integer> {

    CheckoutFor3Dart findCheckoutFor3DartsByScore(int score);

}
