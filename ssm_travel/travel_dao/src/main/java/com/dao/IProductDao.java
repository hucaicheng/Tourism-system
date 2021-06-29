package com.dao;

import com.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductDao {

    //根据id查询信息
    @Select("select * from product where id = #{id}")
    public Product findById(String id);


    /**
     * 查询所有产品信息
     * @return
     */
    @Select("SELECT * FROM product")
    public List<Product> findAll();

    /**
     * 植入数据
     */

    @Insert("insert into product(id,productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{id},#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void saveProduct(Product product);

}
