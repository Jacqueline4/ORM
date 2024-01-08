/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orm.model;

import jakarta.persistence.*;//es un mapeador va con el @Entity

/**
 *
 * @author jacqueline
 */
@Entity
@Table(name="productos") //LAS TABLAS EN PLURAL
public class Producto {//SIEMPRE TIENE QUE TENER UN ID
    @Id // esto ya da por hecho que es PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)//esto es para la autoincremento del ID
    private int id;
    
    @Column(name="cantidad")
    private int quantity;
    
    @Column(name="nombreProducto")//por si en la bbdd la columna se llama nombre --, unique = true
    private String productName;  

    public Producto() { //siempre un constructor vacio para el ORM
    }

    public Producto(int quantity, String nombre) {
        this.quantity = quantity;
        this.productName = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "id: " + id + ", quantity: " + quantity + ", product: " + productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    
}
