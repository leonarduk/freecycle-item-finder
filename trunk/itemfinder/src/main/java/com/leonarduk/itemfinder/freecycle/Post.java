package com.leonarduk.itemfinder.freecycle;

import java.util.Date;

public class Post {
	private final String link;

	private final Date postDate;

	private final PostType postType;
	private final String title;
	public Post(Post post) {
		this(post.postType, post.postDate, post.title, post.link);
	}
	public Post(PostType postType, Date postDate, String title,
			String link) {
		this.postType = postType;
		this.postDate = postDate;
		this.title = title;
		this.link = link;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (postType != other.postType)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public Date getDate() {
		return postDate;
	}

	public String getLink() {
		return link;
	}

	public PostType getPostType() {
		return postType;
	}

	public String getText() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((postDate == null) ? 0 : postDate.hashCode());
		result = prime * result
				+ ((postType == null) ? 0 : postType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	public String toPost() {
		return String.format("%s: %s (%s)", postType, title, link);
	}

	@Override
	public String toString() {
		return String.format("%s: %s: %s (%s)", postDate, postType,
				title, link);
	}
}