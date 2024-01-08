/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orm.pojo;

import com.mycompany.entregableorm.utils.HibernateUtil;
import com.mycompany.orm.dao.ProductoDAO;
import com.mycompany.orm.model.Producto;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author jacqueline
 */
public class ProductoPojo implements ProductoDAO {

    @Override
    public void addProduct(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(producto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public List<Producto> getAllProducts() {
        try (Session session = HibernateUtil.getFactory().openSession()) {
            Query<Producto> query = session.createQuery("FROM Producto", Producto.class);
            return query.list();// es lo mismo que .getResultList
        } catch (Exception e) {
            System.err.println(e);
//            return null;
            return new ArrayList<>();
        }
    }

    @Override
    public void updateProducto(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            if (producto.getId() != 0) {
                session.merge(producto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public List<Producto> getProductsByNameWithHQL(String name) {
        try (Session session = HibernateUtil.getFactory().openSession()) {//:value no hace falta que sea value, puede ser lo que sea siempre que sea unico (no se puede repetir) ya que ese value se reemplaza por el nombre
            Query<Producto> query = session.createQuery("FROM Producto where productName= :valueName ", Producto.class); // productName y no nombre Producto ya que tiene que ser el nombre del atibuto de la clase Producto
            query.setParameter("valueName", name);//aqui se sustituye ese lo que esta despues de : por nombre;
            return query.getResultList();// hace lo mismo query.list()
        } catch (Exception e) {
            System.err.println(e);
            return null;

        }
    }


    @Override
    public List<Producto> getProductByName(String name) {
        try (Session session = HibernateUtil.getFactory().openSession()) {//:value no hace falta que sea value, puede ser lo que sea siempre que sea unico (no se puede repetir) ya que ese value se reemplaza por el nombre
            Query<Producto> query = session.createQuery("FROM Producto where productName like :valueName ", Producto.class); // productName y no nombre Producto ya que tiene que ser el nombre del atibuto de la clase Producto
            query.setParameter("valueName","%"+ name + "%");//aqui se sustituye ese lo que esta despues de : por nombre;
            return query.getResultList();// hace lo mismo query.list()
        } catch (Exception e) {
            System.err.println(e);
            return null;

        }
    }

    @Override
    public void deleteProduct(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            if (producto.getId() != 0) {
                session.remove(producto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(e);
        }
    }

}
