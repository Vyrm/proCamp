package com.garden.utils.mapRower;

import com.garden.model.bouquet.Bouquet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BouquetRowMapper {
    private Bouquet bouquet;

    public Bouquet mapRow(ResultSet resultSet) throws SQLException {
        bouquet = new Bouquet();
        bouquet.setId(resultSet.getLong("id"));
        bouquet.setName(resultSet.getString("name"));
        bouquet.setPrice(resultSet.getDouble("assemble_price"));
        return bouquet;
    }
}
