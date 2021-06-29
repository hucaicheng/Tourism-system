package com.dao;

import com.domain.Member;
import com.domain.Orders;
import com.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdersDao {

    /**
     * 查询Orders订单表的所有字段信息
     * @Result(property = "id" ,column = "id")
     *      id=true   表示id存在
     *      column = "id":取id这一列,在mysql数据库中
     *      property="id" :去domain中的类里面的字段信息
     * @return
     * @throws Exception
     */
    @Select("select * from orders")
    @Results({
            @Result(id = true,property = "id" ,column = "id"),
            @Result(property = "orderNum" ,column = "orderNum"),
            @Result(property = "orderTime" ,column = "orderTime"),
            @Result(property = "orderStatus" ,column = "orderStatus"),
            @Result(property = "peopleCount" ,column = "peopleCount"),
            @Result(property = "payType" ,column = "payType"),
            @Result(property = "orderDesc" ,column = "orderDesc"),
            @Result(property = "orderDesc" ,column = "orderDesc"),
            @Result(property = "product" ,column = "productId",javaType = Product.class,one = @One(select="com.dao.IProductDao.findById")),
    })
    public List<Orders> findAll() throws Exception;

    /**
     * 游客查询，多对多查询
     *    游客表中的信息在Traveller表中，但是无法直接通过orders表来获取游客的id值，需要通过orders_Traveller表中的id进行转换
     */

    @Select("select * from orders where id = #{orderId}")
    @Results({
            @Result(id = true,property = "id" ,column = "id"),
            @Result(property = "orderNum" ,column = "orderNum"),
            @Result(property = "orderTime" ,column = "orderTime"),
            @Result(property = "orderStatus" ,column = "orderStatus"),
            @Result(property = "peopleCount" ,column = "peopleCount"),
            @Result(property = "payType" ,column = "payType"),
            @Result(property = "orderDesc" ,column = "orderDesc"),
            @Result(property = "product" ,column = "productId",javaType = Product.class,one = @One(select="com.dao.IProductDao.findById")),
            @Result(property = "member" ,column = "memberId" ,javaType = Member.class,one = @One(select = "com.dao.IMemberDao.findById")),
            @Result(property = "travellers" ,column = "id" ,javaType = java.util.List.class, many = @Many(select = "com.dao.ITravellerDao.findByOrdersId"))
    })
    public Orders findById(String orderId);
}
