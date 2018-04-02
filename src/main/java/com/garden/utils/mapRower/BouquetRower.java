package com.garden.utils.mapRower;

import com.garden.model.bouquet.Bouquet;
import com.garden.model.flower.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BouquetRower {
    private Bouquet bouquet;

    public Bouquet mapRow(ResultSet resultSet) throws SQLException {
        bouquet = new Bouquet();
        bouquet.setId(resultSet.getLong("id"));
        bouquet.setName(resultSet.getString("name"));
        bouquet.setPrice(resultSet.getDouble("assemble_price"));
        return bouquet;
    }
}
