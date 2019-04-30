package com.codecool.dartcc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checkouts_3darts")
public class CheckoutFor3Dart {
    @Id
    int score;
    String checkout;
}
