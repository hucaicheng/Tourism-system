package com.dao;

import com.domain.Traveller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITravellerDao {

    @Select("SELECT * FROM traveller where id in (select travellerId from order_traveller where orderId = #{ordersId} )")
    public List<Traveller> findByOrdersId(String ordersId) throws Exception;
}
