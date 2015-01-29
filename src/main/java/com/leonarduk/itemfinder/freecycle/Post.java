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
	private final String	link;

	/** The post date. */
	private final Date	   postDate;

	/** The post type. */
	private final PostType	postType;

	/** The title. */
	private final String	title;

	/**
	 * Instantiates a new post.
	 *
	 * @param post
	 *            the post
	 */
	public Post(final Post post) {
		this(post.postType, post.postDate, post.title, post.link);
	}

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
	 */
	public Post(final PostType postTypeValue, final Date postDateValue, final String titleValue,
			final String linkValue) {
		this.postType = postTypeValue;
		this.postDate = postDateValue;
		this.title = titleValue;
		this.link = linkValue;
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
	 * Gets the link.
	 *
	 * @return the link
	 */
	public final String getLink() {
		return this.link;
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
		result = (prime * result) + ((this.link == null) ? 0 : this.link.hashCode());
		result = (prime * result) + ((this.postDate == null) ? 0 : this.postDate.hashCode());
		result = (prime * result) + ((this.postType == null) ? 0 : this.postType.hashCode());
		result = (prime * result) + ((this.title == null) ? 0 : this.title.hashCode());
		return result;
	}

	/**
	 * To post.
	 *
	 * @return the string
	 */
	public final String toPost() {
		return String.format("%s: %s (%s)", this.postType, this.title, this.link);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return String
				.format("%s: %s: %s (%s)", this.postDate, this.postType, this.title, this.link);
	}
}
