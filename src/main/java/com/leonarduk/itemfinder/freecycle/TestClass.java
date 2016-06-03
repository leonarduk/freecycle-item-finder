/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * The Class TestClass.
 */
public class TestClass {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("FreecycleItem");
		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();

		FreecycleItem test = em.find(FreecycleItem.class, "Http://local");
		if (test == null) {
			test = new FreecycleItem("Http://local", "there", "3 bags full", "",
			        "baa baa black sheep", new Date());

			tx.begin();
			em.persist(test);
			tx.commit();
		}

		System.out.format(test.toString());

		em.close();
		emf.close();
	}

	/**
	 * Instantiates a new test class.
	 */
	protected TestClass() {
	}
}
