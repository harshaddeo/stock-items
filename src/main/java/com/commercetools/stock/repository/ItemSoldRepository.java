package com.commercetools.stock.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commercetools.stock.model.ItemSold;

@Repository
public interface ItemSoldRepository extends JpaRepository<ItemSold, String> {

    ItemSold findByStockId(String StockId);

    @Query(value = "with top_sold (stock_id, items_sold) as\n" +
            "(select i.stock_id, sum(i.items_sold) from Item_sold i \n" +
            "where i.item_sold_date >= :value  \n" +
            "group by i.stock_id order by sum(i.items_sold) desc LIMIT 3)\n" +
            "select  p.product_id as productId, t.items_sold as itemsSold \n" +
            "from top_sold t inner join product p on p.stock_id = t.stock_id;", nativeQuery = true)
    List<ITopSellingProduct> findAllByItemSold(@Param("value") Timestamp timestamp);

    interface ITopSellingProduct {

        String getProductId();
        int getItemsSold();
    }

}
