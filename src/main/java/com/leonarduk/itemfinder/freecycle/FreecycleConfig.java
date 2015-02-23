/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.freecycle;

import java.io.IOException;

import com.leonarduk.core.config.Config;

/**
 * The Class FreecycleConfig.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 23 Feb 2015
 */
public class FreecycleConfig extends Config {

	/**
	 * Instantiates a new freecycle config.
	 */
	public FreecycleConfig() {
		super();
	}

	/**
	 * Instantiates a new freecycle config.
	 *
	 * @param configFile
	 *            the config file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public FreecycleConfig(final String configFile) throws IOException {
		super(configFile);
	}

}
