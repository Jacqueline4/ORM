/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.orm.dao;


import com.mycompany.orm.model.Producto;
import java.util.List;

/**
 *
 * @author jacqueline
 */
public interface ProductoDAO {
    void addProduct(Producto producto);
    List<Producto> getAllProducts();
    void deleteProduct(Producto producto);
    void updateProducto(Producto producto);
    List<Producto> getProductsByNameWithHQL(String name);
    List<Producto> getProductByName(String name);
}
