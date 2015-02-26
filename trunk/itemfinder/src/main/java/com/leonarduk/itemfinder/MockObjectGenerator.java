/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.mockito.Mockito;

import com.leonarduk.itemfinder.freecycle.FreecycleConfig;

/**
 * The Class MockObjectGenerator.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 24 Feb 2015
 */
public class MockObjectGenerator {

	/**
	 * Creates the freecycle config.
	 *
	 * @return the freecycle config
	 */
	public static FreecycleConfig createFreecycleConfig() {
		final FreecycleConfig config = new FreecycleConfig();
		config.setSearchPeriod(3);
		config.setResultsPerPage(FreecycleConfig.MIN_RESULTS_PER_PAGE);
		return config;
	}

	/**
	 * Gets the mock entity manager.
	 *
	 * @return the mock entity manager
	 */
	public static EntityManager getMockEntityManager() {
		final EntityManager mockEm = Mockito.mock(EntityManager.class);
		final EntityTransaction transaction = Mockito.mock(EntityTransaction.class);
		Mockito.when(mockEm.getTransaction()).thenReturn(transaction);
		return mockEm;
	}
}
