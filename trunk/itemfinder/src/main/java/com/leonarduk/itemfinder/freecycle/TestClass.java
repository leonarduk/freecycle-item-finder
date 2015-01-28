package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestClass {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("FreecycleItem");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		FreecycleItem test = em.find(FreecycleItem.class, "Http://local");
		if (test == null) {
			test = new FreecycleItem("Http://local", "there", "3 bags full",
					"", "baa baa black sheep", new Date());

			tx.begin();
			em.persist(test);
			tx.commit();
		}

		System.out.format(test.toString());

		em.close();
		emf.close();
	}
}
