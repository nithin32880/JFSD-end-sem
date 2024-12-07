package com.klef.jfsd.exam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        // Setup session factory
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        // Insert a new customer record
        insertCustomer(session);

        // Apply criteria restrictions
        applyCriteriaRestrictions(session);

        // Close session
        session.close();
    }

    public static void insertCustomer(Session session) {
        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer("John Doe", "john@example.com", 30, "New York");
        session.save(customer);
        transaction.commit();
        System.out.println("Customer inserted with ID: " + customer.getId());
    }

    public static void applyCriteriaRestrictions(Session session) {
        Criteria criteria = session.createCriteria(Customer.class);

        // Example: Find customers with age greater than 25
        criteria.add(Restrictions.gt("age", 25));
        List<Customer> customers = criteria.list();
        
        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.getName() + ", Age: " + customer.getAge());
        }

        // Example: Find customers where location is 'New York'
        criteria = session.createCriteria(Customer.class);
        criteria.add(Restrictions.eq("location", "New York"));
        customers = criteria.list();
        
        for (Customer customer : customers) {
            System.out.println("Customer from New York: " + customer.getName());
        }
    }
}
