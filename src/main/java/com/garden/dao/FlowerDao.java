package com.garden.dao;

import com.garden.model.flower.Flower;

import java.sql.SQLException;

public interface FlowerDao {
    long addFlower(Flower flower) throws SQLException;
}
