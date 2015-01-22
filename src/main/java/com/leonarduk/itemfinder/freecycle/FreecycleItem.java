/**
 * 
 */
package com.leonarduk.itemfinder.freecycle;

import com.leonarduk.freecycle.FullPost;
import com.leonarduk.itemfinder.interfaces.Item;

/**
 * @author stephen
 *
 */
public class FreecycleItem implements Item {

	final private Condition condition;
	final private FullPost post;

	final private double price;

	public FreecycleItem(FullPost post) {
		this.post = post;
		this.condition = Condition.USED;
		this.price = 0;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreecycleItem other = (FreecycleItem) obj;
		if (condition != other.condition)
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getCondition()
	 */
	@Override
	public Condition getCondition() {
		return this.condition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.post.getDescription() + post.getExtraHtml();
	}

	public String getLink() {
		return this.post.getLink();
	}

	@Override
	public String getLocation() {
		return this.post.getLocation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getName()
	 */
	@Override
	public String getName() {
		return this.post.getText();
	}

	public FullPost getPost() {
		return this.post;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getPrice()
	 */
	@Override
	public double getPrice() {
		return this.price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.itemfinder.interfaces.Item#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return this.post.getQuantity();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "FreecycleItem [name=" + getName() + ", description="
				+ getDescription() + ", link=" + getLink() + ", location="
				+ post.getLocation() + "]";
	}
}
