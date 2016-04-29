/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The Class ReportableItemTest.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class ReportableItemTest {

	/** The em. */
	private EntityManager em;

	/** The tx. */
	private EntityTransaction tx;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public final void setUp() throws Exception {
		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReportableItem");
		this.em = emf.createEntityManager();
		this.tx = this.em.getTransaction();
		this.tx.begin();

	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public final void tearDown() throws Exception {
		this.tx.rollback();
	}

	/**
	 * Test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	@Ignore
	public final void testFindall() throws Exception {
		final String link = "Http://localhost/1";

		final ReportableItem test = new ReportableItem(link, false);
		this.em.persist(test);
		final String sql = "select r from ReportableItem r";
		final Query findall = this.em.createQuery(sql, ReportableItem.class);
		final List<?> results = findall.getResultList();
		System.out.println(results);
	}

	/**
	 * Test save and query.
	 */
	@Test
	@Ignore
	public final void testSaveAndQuery() {
		final String link = "Http://localhost/1";
		ReportableItem test = this.em.find(ReportableItem.class, link);
		Assert.assertNull(test);
		test = new ReportableItem(link, false);
		this.em.persist(test);
		final ReportableItem test2 = this.em.find(ReportableItem.class, link);
		Assert.assertEquals(test, test2);
	}
}
