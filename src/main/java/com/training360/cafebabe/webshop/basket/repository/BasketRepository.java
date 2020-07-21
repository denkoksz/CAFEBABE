package com.training360.cafebabe.webshop.basket.repository;

import com.training360.cafebabe.webshop.product.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BasketRepository {

    private JdbcTemplate template;

    public BasketRepository(DataSource dataSource) {

        template = new JdbcTemplate(dataSource);
    }

    private RowMapper<Product> mapper = (rs, i) -> {
        Long id = rs.getLong("products.id");
        String productKey = rs.getString("products.product_key");
        String name = rs.getString("products.name");
        String url = rs.getString("products.url");
        String manufacturer = rs.getString("products.manufacturer");
        int price = rs.getInt("products.price");
        boolean isActive = rs.getBoolean("products.isactive");
        return new Product(id, productKey, name, url, manufacturer, price, isActive);
    };

public boolean addProduct(Long id, String username){
        return template.update("insert into baskets(user_name, product_id) values (?, ?)", username,id) !=0;
}

public List<Product> getBasket(String username){
 return template.query("select products.id, products.product_key, products.name, products.url, products.manufacturer, products.price, products.isactive FROM products INNER JOIN baskets ON products.id=baskets.product_id WHERE baskets.user_name = ?",  mapper, username);
} // phpmyadminban lefutott

public boolean deleteBasket(String username){
return template.update("delete from baskets where user_name=?", username) !=0;
} // phpmyadminban lefutott

public boolean deleteProduct(Long id, String username){
      return   template.update("delete from baskets where product_id=? AND user_name = ?",id,username ) !=0;
} // phpmyadminban lefutott

public boolean isProductAlreadyInTheBasket(Long id, String username){
return template.queryForObject("select count(product_id) from baskets where product_id=? AND user_name = ?",Integer.class, id, username ) !=0;
}

public int sumBasketPrice(String username){
   Integer sum = template.queryForObject("select SUM(products.price) FROM products INNER JOIN baskets ON products.id=baskets.product_id WHERE baskets.user_name=?", Integer.class, username);
   return sum == null ? 0 : sum;
} // phpmyadminban lefutott
}
