/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;
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

import com.leonarduk.itemfinder.interfaces.Item.Condition;

/**
 * The Class FreecycleItemTest.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
public class FreecycleItemTest {

    /** The test class. */
    private FreecycleItem testClass;

    /** The quantity. */
    private int quantity;

    /** The price. */
    private double price;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The condition. */
    private Condition condition;

    /** The link. */
    private String link;

    /** The location. */
    private String location;

    /** The date. */
    private Date date;

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
        this.quantity = 1;
        this.price = 0;
        this.name = "testitem";
        this.description = "nice item";
        this.condition = Condition.USED;
        this.location = "somewhere";
        this.link =
                "https://groups.freecycle.org/group/freecycle-kingston/posts/44926473";
        this.date = new Date();

        this.testClass =
                new FreecycleItem(this.link, this.location, this.name, "",
                        this.description, this.date);

        final EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("ReportableItem");
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
     * Test find all.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    @Ignore
    public final void testFindAll() throws Exception {
        final String sql =
                "select r from " + FreecycleItem.class.getSimpleName() + " r";
        final Query findall = this.em.createQuery(sql, FreecycleItem.class);
        @SuppressWarnings("unchecked")
        final List<FreecycleItem> results = findall.getResultList();

        System.out.println(results.get(0));
    }

    /**
     * Test method for
     * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getCondition()}.
     */
    @Test
    public final void testGetCondition() {
        Assert.assertEquals(this.condition, this.testClass.getCondition());
    }

    /**
     * Test method for
     * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getDescription()}
     * .
     */
    @Test
    public final void testGetDescription() {
        Assert.assertEquals(this.description, this.testClass.getDescription());
    }

    /**
     * Test method for
     * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getName()}.
     */
    @Test
    public final void testGetName() {
        Assert.assertEquals(this.name, this.testClass.getName());
    }

    /**
     * Test method for
     * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getPrice()}.
     */
    @Test
    public final void testGetPrice() {
        Assert.assertEquals(this.price, this.testClass.getPrice(), 0);
    }

    /**
     * Test method for
     * {@link com.leonarduk.itemfinder.freecycle.FreecycleItem#getQuantity()}.
     */
    @Test
    public final void testGetQuantity() {
        Assert.assertEquals(this.quantity, this.testClass.getQuantity());
    }
}
