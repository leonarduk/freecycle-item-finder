/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.leonarduk.itemfinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

/**
 * Displays the names of the input and output directories.
 *
 * @author Stephen Leonard
 * @since 1.0
 *
 */
public final class SpringIntegrationUtils {

	/** The Constant logger. */
	private static final Log	LOGGER	= LogFactory.getLog(SpringIntegrationUtils.class);

	/**
	 * Helper Method to dynamically determine and display input and output directories as defined in
	 * the Spring Integration context.
	 *
	 * @param context
	 *            Spring Application Context
	 */
	public static void displayDirectories(final ApplicationContext context) {

		final File inDir = (File) new DirectFieldAccessor(
				context.getBean(FileReadingMessageSource.class)).getPropertyValue("directory");

		final Map<String, FileWritingMessageHandler> fileWritingMessageHandlers = context
				.getBeansOfType(FileWritingMessageHandler.class);

		final List<String> outputDirectories = new ArrayList<String>();

		for (final FileWritingMessageHandler messageHandler : fileWritingMessageHandlers.values()) {
			final Expression outDir = (Expression) new DirectFieldAccessor(messageHandler)
			.getPropertyValue("destinationDirectoryExpression");
			outputDirectories.add(outDir.getExpressionString());
		}

		final StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("\n=========================================================");
		stringBuilder.append("\n");
		stringBuilder.append("\n    Input directory is : '" + inDir.getAbsolutePath() + "'");

		for (final String outputDirectory : outputDirectories) {
			stringBuilder.append("\n    Output directory is: '" + outputDirectory + "'");
		}

		stringBuilder.append("\n\n=========================================================");

		SpringIntegrationUtils.LOGGER.info(stringBuilder.toString());

	}

	/**
	 * Instantiates a new spring integration utils.
	 */
	private SpringIntegrationUtils() {
	}

}
