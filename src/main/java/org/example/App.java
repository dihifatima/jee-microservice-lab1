package org.example;

import org.example.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App {

    public static void main(String[] args) {
        // Création de l'EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-demo");

        // Insertion de produits
        insererProduits(emf);

        // Lecture des produits
        lireProduits(emf);

        // Fermeture de l'EntityManagerFactory
        emf.close();
    }

    private static void insererProduits(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Création de quelques produits
            Product p1 = new Product("Laptop", 2000);
            Product p2 = new Product("Smartphone", 30000);
            Product p3 = new Product("Tablette", 40000);

            // Persistance des produits
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            em.getTransaction().commit();
            System.out.println("Produits insérés avec succès !");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void lireProduits(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            // Requête JPQL pour récupérer tous les produits
            List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class)
                    .getResultList();

            System.out.println("\nListe des produits :");
            for (Product product : products) {
                System.out.println(product);
            }

            // Recherche d'un produit par ID
            System.out.println("\nRecherche du produit avec ID=2 :");
            Product product = em.find(Product.class, 2L);
            if (product != null) {
                System.out.println(product);
            } else {
                System.out.println("Produit non trouvé");
            }
        } finally {
            em.close();
        }
    }
}