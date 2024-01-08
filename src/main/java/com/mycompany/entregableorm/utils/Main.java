/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.entregableorm.utils;

import com.mycompany.orm.model.Producto;
import com.mycompany.orm.pojo.ProductoPojo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jacqueline
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Producto> alProd = lecturaCsvD("compra.csv");
        List<Producto> alProdBBDD = null;
        Producto ini;
        ProductoPojo pp = null;
        Scanner sc = new Scanner(System.in);
        String opcion = "";

        try {
            pp = new ProductoPojo();

            for (Producto producto : alProd) {
                alProdBBDD = pp.getProductsByNameWithHQL(producto.getProductName());
                if (alProdBBDD != null) {

                    if (!alProdBBDD.isEmpty()) {
                        ini = alProdBBDD.get(0);
                        ini.setQuantity(ini.getQuantity() + producto.getQuantity());
                        pp.updateProducto(ini);
                    } else {
                        pp.addProduct(producto);

                    }
                }
            }
            System.out.println(pp.getAllProducts());

        } catch (Exception e) {
            System.out.println(e);
        }

        do {
            opcion = mostrarMenu(sc);

            sc.nextLine();
            switch (opcion) {
                case "listar":
                    listarSuministros(pp.getAllProducts());
                    break;
                case "usar":
                    usarSuministro(sc, pp);
                    break;
                case "hay":
                    haySuministro(sc, pp);
                    break;
                case "adquirir":
                    adquirirSuministro(sc, pp, alProdBBDD);
                    break;
                case "salir":
                    System.out.println("Saliendo del programa");
                    HibernateUtil.shutdown();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }

        } while (!opcion.equalsIgnoreCase("salir"));
    }

    public static void listarSuministros(List<Producto> productos) {
        System.out.println("Listar suministros");
        for (Producto producto : productos) {
            System.out.println(producto.toString());
        }
    }

    public static void usarSuministro(Scanner sc, ProductoPojo pp) {

        System.out.print("Nombre del producto a buscar: ");
        String nombreSuministro = sc.nextLine();
        for (Producto producto : pp.getProductByName(nombreSuministro)) {
            System.out.println(producto);
        }
        System.out.print("Nombre del producto exacto: ");
        String nombreSuministroE = sc.nextLine();
        System.out.print("Ingrese la cantidad a usar: ");
        int cantidadAUsar = sc.nextInt();
        if (cantidadAUsar > 0) {
            int cantidadNueva;
            for (Producto producto : pp.getProductsByNameWithHQL(nombreSuministroE)) {
                System.out.println(producto);
                if (cantidadAUsar > producto.getQuantity()) {
                    System.out.println("No hay suficiente cantidad, solo quedan " + producto.getQuantity() + " " + nombreSuministroE);
                } else if (cantidadAUsar == producto.getQuantity()) {
                    pp.deleteProduct(producto);
                } else {
                    cantidadNueva = producto.getQuantity() - cantidadAUsar;
                    producto.setQuantity(cantidadNueva);
                    pp.updateProducto(producto);
                    System.out.println("nuevo: " + producto);
                }
            }
        } else {
            System.out.println("Tienes que introducir un numero positivo");
        }

    }

    public static void haySuministro(Scanner sc, ProductoPojo pp) {

        System.out.print("Nombre del producto: ");
        String nombreSuministro = sc.nextLine();
        if (pp.getProductByName(nombreSuministro).isEmpty()) {
            System.out.println("No hay de ese producto");
        } else {

            for (Producto producto : pp.getProductByName(nombreSuministro)) {

                System.out.println(producto);
            }
        }
    }

    public static void adquirirSuministro(Scanner sc, ProductoPojo pp, List<Producto> alProdBBDD) {

        System.out.print("Nombre del producto: ");
        String nombreAdquirir = sc.nextLine();
        System.out.print("Ingrese la cantidad a adquirir: ");
        int cantidadAdquirir = sc.nextInt();

        if (cantidadAdquirir>0) {
            Producto adquisicion = new Producto();
            adquisicion.setProductName(nombreAdquirir);
            adquisicion.setQuantity(cantidadAdquirir);
            alProdBBDD = pp.getProductsByNameWithHQL(adquisicion.getProductName());
            if (alProdBBDD != null) {
                if (!alProdBBDD.isEmpty()) {
                    adquisicion = alProdBBDD.get(0);
                    adquisicion.setQuantity(adquisicion.getQuantity() + cantidadAdquirir);
                    pp.updateProducto(adquisicion);

                } else {
                    pp.addProduct(adquisicion);

                }
            }
            for (Producto producto : pp.getProductsByNameWithHQL(adquisicion.getProductName())) {

                System.out.println(producto);
            }
        } else{
            System.out.println("Por favor introduzca una cantidad positiva");
        }

    }

    public static String mostrarMenu(Scanner sc) {

        System.out.println("---------- Menú----------");
        System.out.println("1. Listar suministros -> listar");
        System.out.println("2. Usar suministro -> usar");
        System.out.println("3. Hay suministro -> hay");
        System.out.println("4. Adquirir suministro -> adquirir");
        System.out.println("5. Salir -> salir");
        System.out.println("Ingrese comando: ");
        return sc.next();

    }

    public static ArrayList<Producto> lecturaCsvD(String rutaCSV) {
        String linea;
        File f = new File(rutaCSV);
        Producto p;
        ArrayList<Producto> alProd = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();// String cabecera = 
            while ((linea = br.readLine()) != null) {
                String[] col = linea.split(";");
                p = new Producto();
                p.setQuantity(Integer.parseInt(col[0]));
                p.setProductName(col[1].toLowerCase());
                alProd.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alProd;
    }
}
