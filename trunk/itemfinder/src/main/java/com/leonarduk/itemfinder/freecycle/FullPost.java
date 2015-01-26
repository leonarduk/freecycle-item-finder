package com.leonarduk.itemfinder.freecycle;


public class FullPost extends Post {
	private String description;

	private String html;

	private String location;

	private int quantity;

	/**
	 * 
	 * @param location
	 * @param description
	 * @param post
	 * @param html
	 */
	public FullPost(String location, String description, Post post, String html) {
		super(post);
		this.location = location;
		this.description = description;
		this.quantity = 1; // TODO parse description
		this.html = html;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FullPost other = (FullPost) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (html == null) {
			if (other.html != null)
				return false;
		} else if (!html.equals(other.html))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	public String getDescription() {
		return description;
	}

	public String getExtraHtml() {
		return this.html;
	}

	public String getLocation() {
		return location;
	}

	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((html == null) ? 0 : html.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public String toString() {
		return "ItemDetails [date=" + getDate() + ", post=" + getPostType()
				+ ", location=" + location + ", description=" + description
				+ ", link=" + getLink() + ", title=" + getText() + ", date="
				+ getDate() + "]";
	}

}