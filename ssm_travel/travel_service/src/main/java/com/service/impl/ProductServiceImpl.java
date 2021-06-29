package com.service.impl;

import com.dao.IProductDao;
import com.domain.Product;
import com.github.pagehelper.PageHelper;
import com.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

@Transactional
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    /**
     * 查询所有
     * @return
     */
    public List<Product> findAll(int page,int size) {
        System.out.println("业务层：执行findAll");
        PageHelper.startPage(page,size);
        return productDao.findAll();
    }


    public void saveProduct(Product product) {
        System.out.println("业务层：修改前id数据："+product.getId());
        product.setId(Uuid());
        System.out.println("业务层：修改后id数据:"+product.getId());
        productDao.saveProduct(product);
        System.out.println("数据操作完成");
    }

    //自动生成32位字符串
    public String Uuid(){
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString().replaceAll("[-]","");
        return guid;
    }
}
