package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checkouts_2darts")
public class CheckoutFor2Dart {
    @Id
    int score;
    String checkout;
}
