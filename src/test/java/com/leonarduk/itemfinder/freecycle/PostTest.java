/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class PostTest.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 26 Feb 2015
 */
public class PostTest {

	/** The post type value. */
	private PostType postTypeValue;

	/** The post date value. */
	private Date postDateValue;

	/** The title value. */
	private String titleValue;

	/** The link value. */
	private String linkValue;

	/** The group. */
	private FreecycleGroup group;

	/** The post. */
	private Post post;

	/** The post id. */
	private int postId;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public final void setUp() throws Exception {
		this.postDateValue = new Date();
		this.postTypeValue = PostType.OFFER;
		this.titleValue = "Kids Snow Boots";
		final int samplePostId = 45736770;
		this.postId = samplePostId;
		this.linkValue = "https://groups.freecycle.org/group/freecycle-kingston/posts/"
		        + this.postId + "/Kids%20Snow%20Boots";
		this.group = FreecycleGroup.kingston;
		this.post = new Post(this.postTypeValue, this.postDateValue, this.titleValue,
		        this.linkValue, this.group);
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test get date.
	 */
	@Test
	public final void testGetDate() {
		Assert.assertEquals(this.postDateValue, this.post.getDate());
	}

	/**
	 * Test get freecycle group.
	 */
	@Test
	public final void testGetFreecycleGroup() {
		Assert.assertEquals(this.group, this.post.getFreecycleGroup());
	}

	/**
	 * Test get link.
	 */
	@Test
	public final void testGetLink() {
		Assert.assertEquals(this.linkValue, this.post.getLink());
	}

	/**
	 * Test get post id.
	 */
	@Test
	public final void testGetPostId() {
		Assert.assertEquals(this.postId, this.post.getPostId());
	}

	/**
	 * Test get post type.
	 */
	@Test
	public final void testGetPostType() {
		Assert.assertEquals(this.postTypeValue, this.post.getPostType());
	}

	/**
	 * Test get text.
	 */
	@Test
	public final void testGetText() {
		Assert.assertEquals(this.titleValue, this.post.getText());
	}

	/**
	 * Test to post.
	 */
	@Test
	public final void testToPost() {
		final String expected = "OFFER: Kids Snow Boots (https://groups.freecycle.org/group/freecycle-kingston"
		        + "/posts/45736770/Kids%20Snow%20Boots)";
		Assert.assertEquals(expected, this.post.toPost());
	}

	/**
	 * Test to string.
	 */
	@Test
	public final void testToString() {
		final String expected = this.postDateValue
		        + ": OFFER: Kids Snow Boots (https://groups.freecycle.org/group/"
		        + "freecycle-kingston/posts/45736770/Kids%20Snow%20Boots)";
		Assert.assertEquals(expected, this.post.toString());
	}

}
