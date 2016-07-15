/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

/**
 * The Class Post.
 */
public class Post {

	/** The link. */
	private final String link;

	/** The post date. */
	private final Date postDate;

	/** The post type. */
	private final PostType postType;

	/** The title. */
	private final String title;

	/** The freecycle group. */
	private final FreecycleGroup freecycleGroup;

	/** The post id. */
	private final int postId;

	/**
	 * Instantiates a new post.
	 *
	 * @param postTypeValue
	 *            the post type
	 * @param postDateValue
	 *            the post date
	 * @param titleValue
	 *            the title
	 * @param linkValue
	 *            the link
	 * @param group
	 *            the group
	 */
	Post(final PostType postTypeValue, final Date postDateValue, final String titleValue,
	        final String linkValue, final FreecycleGroup group) {
		this.postType = postTypeValue;
		this.postDate = postDateValue;
		this.title = titleValue;
		this.link = linkValue;
		final String[] urlParts = this.link.split("/");
		final int postIdIndex = 6;
		this.postId = Integer.valueOf(urlParts[postIdIndex]).intValue();
		this.freecycleGroup = group;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Post other = (Post) obj;
		if (this.link == null) {
			if (other.link != null) {
				return false;
			}
		}
		else if (!this.link.equals(other.link)) {
			return false;
		}
		if (this.postType != other.postType) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		}
		else if (!this.title.equals(other.title)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public final Date getDate() {
		return this.postDate;
	}

	/**
	 * Gets the freecycle group.
	 *
	 * @return the freecycle group
	 */
	public final FreecycleGroup getFreecycleGroup() {
		return this.freecycleGroup;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public final String getLink() {
		return this.link;
	}

	/**
	 * Gets the post id.
	 *
	 * @return the post id
	 */
	public final int getPostId() {
		return this.postId;
	}

	/**
	 * Gets the post type.
	 *
	 * @return the post type
	 */
	public final PostType getPostType() {
		return this.postType;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public final String getText() {
		return this.title;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.hashcodeHelper(this.link);
		result = (prime * result) + this.hashcodeHelper(this.postDate);
		result = (prime * result) + this.hashcodeHelper(this.postType);
		result = (prime * result) + this.hashcodeHelper(this.title);
		return result;
	}

	/**
	 * Hashcode helper.
	 *
	 * @param object
	 *            the object
	 * @return the int
	 */
	private int hashcodeHelper(final Object object) {
		if (object == null) {
			return 0;
		}
		return object.hashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return String.format("%s: %s: %s (%s)", this.postDate, this.postType, this.title,
		        this.link);
	}
}
