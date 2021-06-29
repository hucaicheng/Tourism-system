package com.service;

import com.domain.Product;

import java.util.List;

public interface IProductService {

    /**
     * 查询所有数据
     */
    public List<Product> findAll(int page,int size);

    /**
     * 插入数据
     */

    public void saveProduct(Product product);
}
