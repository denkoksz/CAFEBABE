package com.training360.cafebabe.webshop.order.repository;

import com.training360.cafebabe.webshop.basket.entities.Basket;
import com.training360.cafebabe.webshop.order.controller.OrderOperationResponse;
import com.training360.cafebabe.webshop.order.entities.Order;
import com.training360.cafebabe.webshop.order.entities.OrderReportByMonthAndStatus;
import com.training360.cafebabe.webshop.order.entities.OrderReportsByProducts;
import com.training360.cafebabe.webshop.order.entities.OrderStatus;
import com.training360.cafebabe.webshop.product.entities.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

@Repository
public class OrderRepository {

    private JdbcTemplate template;

    public OrderRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public int getNumberOfOrders() {
        return template.queryForObject("SELECT COUNT(id) FROM orders", Integer.class);
    }

    public int getNumberOfActiveOrders() {
        return template.queryForObject("SELECT COUNT(id) FROM orders WHERE order_status = \'ACTIVE\'", Integer.class);
    }

//    private RowMapper<Order> mapper = (rs, rowNum) -> {
//            Long id = rs.getLong("orders.id");
//            String userName = rs.getString("user_name");
//            Date date = rs.getDate("order_date");
//            Time time = rs.getTime("order_date");
//            List<Product> products = new ArrayList<>();
//            int sum = rs.getInt("order_sum");
//            String status = rs.getString("order_status");
//            Order currentOrder = new Order(id, userName, LocalDateTime.of(((java.sql.Date) date).toLocalDate(), time.toLocalTime()), products, sum, OrderStatus.valueOf(status));
//
//
//        long productId = rs.getLong("products.id");
//        String productKey = rs.getString("product_key");
//        String productName = rs.getString("name");
//        String productUrl = rs.getString("url");
//        String productManufacturer = rs.getString("manufacturer");
//        int productPrice = rs.getInt("price");
//        boolean productIsActive = rs.getBoolean("isactive");
//        Product mappedProduct = new Product(productId, productKey, productName, productUrl, productManufacturer, productPrice, productIsActive);
//
//        currentOrder.addProduct(mappedProduct);
//
//        return currentOrder;
//    };

    public List<Order> getOrdersByUsername(String username) {
        Map<Long, Order> orders = new HashMap();
        template.query("SELECT orders.id , user_name , order_date , order_sum , order_status , order_products.order_id , order_products.product_id , products.id , product_key , name , url , manufacturer , price , isactive from orders JOIN order_products on orders.id = order_products.order_id JOIN products on products.id = order_products.product_id where orders.user_name = ?",

                (rs) -> {
                    Long id = rs.getLong("orders.id");
                    Order currentOrder = orders.get(id);
                    if (currentOrder == null) {
                        String userName = rs.getString("user_name");
                        Date date = rs.getDate("order_date");
                        Time time = rs.getTime("order_date");
                        List<Product> products = new ArrayList<>();
                        int sum = rs.getInt("order_sum");
                        String status = rs.getString("order_status");
                        currentOrder = new Order(id, userName, LocalDateTime.of(((java.sql.Date) date).toLocalDate(), time.toLocalTime()), products, sum, OrderStatus.valueOf(status));
                        orders.put(id, currentOrder);
                    }

                    long productId = rs.getLong("products.id");
                    String productKey = rs.getString("product_key");
                    String productName = rs.getString("name");
                    String productUrl = rs.getString("url");
                    String productManufacturer = rs.getString("manufacturer");
                    int productPrice = rs.getInt("price");
                    boolean productIsActive = rs.getBoolean("isactive");
                    Product mappedProduct = new Product(productId, productKey, productName, productUrl, productManufacturer, productPrice, productIsActive);
                    currentOrder.addProduct(mappedProduct);
                }, username);

        List<Order> ordersList = new ArrayList<>(orders.values());
        Collections.sort(ordersList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());  // descending order
            }
        });
        return  ordersList;
    }

    public List<OrderReportByMonthAndStatus> listOrdersByMonthAndStatus() {
        return template.query("SELECT CONCAT(year(order_date),'-', month(order_date)) AS datum, order_status, COUNT(order_status) as darab,  SUM(order_sum) as osszeg FROM orders GROUP BY datum, order_status ORDER BY `datum` DESC,  osszeg DESC",
                new RowMapper<OrderReportByMonthAndStatus>() {
                    @Override
                    public OrderReportByMonthAndStatus mapRow(ResultSet rs, int i) throws SQLException {
                        String date = rs.getString("datum");
                        String status = rs.getString("order_status");
                        int count = rs.getInt("darab");
                        int sum = rs.getInt("osszeg");
                        return new OrderReportByMonthAndStatus(date, status, count, sum);
                    }
                });
    }

    public List<OrderReportsByProducts> listOrdersByProducts() {
        return template.query("SELECT CONCAT(year(order_date),'-', month(order_date)) AS datum, products.name, COUNT(products.name) as darabszam , products.price, SUM(products.price) as osszeg from orders JOIN order_products on orders.id = order_products.order_id JOIN products on products.id = order_products.product_id where orders.order_status = 'FULFILLED' GROUP BY products.price order by datum DESC",
                new RowMapper<OrderReportsByProducts>() {
                    @Override
                    public OrderReportsByProducts mapRow(ResultSet rs, int i) throws SQLException {
                        String date = rs.getString("datum");
                        String product = rs.getString("name");
                        int countOfProducts = rs.getInt("darabszam");
                        int price = rs.getInt("price");
                        int sum = rs.getInt("osszeg");
                        return new OrderReportsByProducts(date, product, countOfProducts, price, sum);
                    }
                });
    }
    public boolean createOrder(String username, Basket myBasket) {
        if (myBasket.getProducts().isEmpty()) {
            return false;
        }
        int sum = myBasket.getSum();
        String status = OrderStatus.ACTIVE.name();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int modifiedRowNumberOrders = template.update(new PreparedStatementCreator() {
                                                          @Override
                                                          public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                                              PreparedStatement ps = connection.prepareStatement("INSERT INTO orders(user_name, order_sum, order_status) VALUES (?, ?, ?)"
                                                                      , Statement.RETURN_GENERATED_KEYS);
                                                              ps.setString(1, username);
                                                              ps.setInt(2, sum);
                                                              ps.setString(3, status);
                                                              return ps;
                                                          }
                                                      }, keyHolder
        );

        if (modifiedRowNumberOrders == 0) {
            return false;
        }

        long id = keyHolder.getKey().longValue();

        List<Object> parameters = new ArrayList<>();
        int[] parameterTypes = new int[myBasket.getProducts().size() * 2];
        int counter = 0;
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO order_products(order_id, product_id) VALUES ");
        for (Product product : myBasket.getProducts()) {
            sqlBuilder.append("(?, ?),");
            parameters.add(id);
            parameters.add(product.getId());
            parameterTypes[counter] = parameterTypes[counter + 1] = Types.BIGINT;
            counter += 2;
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        int modifiedNumberOfOrderProducts = template.update(sqlBuilder.toString(), parameters.toArray(), parameterTypes);

        //Átírt függvény, ami egyesével kérdezi le adatbázisból a termékeket
//        for (Product product : myBasket.getProducts()) {
//            modifiedNumberOfOrderProducts += template.update("INSERT INTO order_products(order_id, product_id) VALUES (?, ?)"
//                    , id
//                    , product.getId()
//            );
//        }
        return (modifiedNumberOfOrderProducts > 0);
    }

    public OrderStatus getOrderStatusById(long id) {
        try {
            return template.queryForObject("SELECT order_status FROM orders WHERE id = ?", new RowMapper<OrderStatus>() {
                @Override
                public OrderStatus mapRow(ResultSet resultSet, int i) throws SQLException {
                    String status = resultSet.getString("order_status");
                    return OrderStatus.valueOf(status);
                }
            }, id);
        } catch (DataAccessException dae) {
            return null;
        }
    }

    public UpdateOrderStatusResponse updateOrderStatus(long id) {
        int modifiedNumberOfRows = template.update("UPDATE orders SET order_status = 'FULFILLED' WHERE id = ?", id);
        if (modifiedNumberOfRows != 0) {
            return new UpdateOrderStatusResponse()
                    .setSuccess(true)
                    .setMessage("Order status was successfully changed to FULFILLED");
        }
        return new UpdateOrderStatusResponse()
                .setSuccess(false)
                .setMessage("Order is already in FULFILLED status");
    }

    public List<Order> listAllOrders() {
        Map<Long, Order> orders = new HashMap();
        template.query("SELECT orders.id , user_name , order_date , order_sum , order_status , order_products.order_id , order_products.product_id , products.id , product_key , name , url , manufacturer , price , isactive from orders JOIN order_products on orders.id = order_products.order_id " +
                        "JOIN products on products.id = order_products.product_id",
                (rs) -> {
                    Long id = rs.getLong("orders.id");
                    Order currentOrder = orders.get(id);
                    if (currentOrder == null) {
                        String userName = rs.getString("user_name");
                        Date date = rs.getDate("order_date");
                        Time time = rs.getTime("order_date");
                        List<Product> products = new ArrayList<>();
                        int sum = rs.getInt("order_sum");
                        String status = rs.getString("order_status");
                        currentOrder = new Order(id, userName, LocalDateTime.of(((java.sql.Date) date).toLocalDate(), time.toLocalTime()), products, sum, OrderStatus.valueOf(status));
                        orders.put(id, currentOrder);
                    }

                    long productId = rs.getLong("products.id");
                    String productKey = rs.getString("product_key");
                    String productName = rs.getString("name");
                    String productUrl = rs.getString("url");
                    String productManufacturer = rs.getString("manufacturer");
                    int productPrice = rs.getInt("price");
                    boolean productIsActive = rs.getBoolean("isactive");
                    Product mappedProduct = new Product(productId, productKey, productName, productUrl, productManufacturer, productPrice, productIsActive);
                    currentOrder.addProduct(mappedProduct);
                });
        List<Order> ordersList = new ArrayList<>(orders.values());
        ordersList.sort(Comparator.comparing(Order::getId).reversed());
        return ordersList;

    }

    public List<Order> listAllActiveOrders() {
        Map<Long, Order> orders = new HashMap();
        template.query("SELECT orders.id , user_name , order_date , order_sum , order_status , order_products.order_id , order_products.product_id , products.id , product_key , name , url , manufacturer , price , isactive from orders JOIN order_products on orders.id = order_products.order_id " +
                        "JOIN products on products.id = order_products.product_id where order_status='ACTIVE'",
                (rs) -> {
                    Long id = rs.getLong("orders.id");
                    Order currentOrder = orders.get(id);
                    if (currentOrder == null) {
                        String userName = rs.getString("user_name");
                        Date date = rs.getDate("order_date");
                        Time time = rs.getTime("order_date");
                        List<Product> products = new ArrayList<>();
                        int sum = rs.getInt("order_sum");
                        String status = rs.getString("order_status");
                        currentOrder = new Order(id, userName, LocalDateTime.of(((java.sql.Date) date).toLocalDate(), time.toLocalTime()), products, sum, OrderStatus.valueOf(status));
                        orders.put(id, currentOrder);
                    }

                    long productId = rs.getLong("products.id");
                    String productKey = rs.getString("product_key");
                    String productName = rs.getString("name");
                    String productUrl = rs.getString("url");
                    String productManufacturer = rs.getString("manufacturer");
                    int productPrice = rs.getInt("price");
                    boolean productIsActive = rs.getBoolean("isactive");
                    Product mappedProduct = new Product(productId, productKey, productName, productUrl, productManufacturer, productPrice, productIsActive);
                    currentOrder.addProduct(mappedProduct);
                });
        List<Order> ordersList = new ArrayList<>(orders.values());
        ordersList.sort(Comparator.comparing(Order::getId).reversed());
        return ordersList;
    }

    public OrderOperationResponse deleteOrder(Long orderID) {
        if(template.update("update orders set order_status ='DELETED' where id=? and order_status ='ACTIVE'", orderID) >0){
            return new OrderOperationResponse().setSuccess(true).setMessage("Order table is successfully deleted!");
        }
        else {
            return new OrderOperationResponse().setSuccess(false).setMessage("Order is already deleted!");
        }
    }

    public int deleteProductFromOrder(Long orderId, Long productId) {
        return template.update("delete from order_products where product_id=? AND order_id=?", productId, orderId);
    }

    public int countProductsPerOrder(Long orderId) {
        return template.queryForObject("select count(product_id) from order_products where order_id=?", Integer.class, orderId);
    }


}
