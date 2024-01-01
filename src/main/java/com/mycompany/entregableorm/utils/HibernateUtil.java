/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entregableorm.utils;

import org.hibernate.cfg.*;
import org.hibernate.SessionFactory;

/**
 *
 * @author jacqueline
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory(){
        return new Configuration().configure().buildSessionFactory();
    }
    public static SessionFactory getFactory(){
        return sessionFactory;
    }
    public static void shutdown(){
         sessionFactory.close();
    }
}
