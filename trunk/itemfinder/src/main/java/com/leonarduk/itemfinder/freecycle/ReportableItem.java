/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportableItem.
 *
 * @author Stephen Leonard
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * @since 28 Jan 2015
 */
@Entity
@Table(name = "ReportableItem")
public class ReportableItem {

	/** The link. */
	@Id
	private String	link;

	/** The sent. */
	@Basic
	private boolean	sent;

	/**
	 * Instantiates a new reportable item.
	 */
	protected ReportableItem() {
		// for Jpa
	}

	/**
	 * Instantiates a new reportable item.
	 *
	 * @param link
	 *            the link
	 * @param sent
	 *            the sent
	 */
	public ReportableItem(final String link, final boolean sent) {
		super();
		this.link = link;
		this.sent = sent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final ReportableItem other = (ReportableItem) obj;
		if (this.link == null) {
			if (other.link != null) {
				return false;
			}
		}
		else if (!this.link.equals(other.link)) {
			return false;
		}
		if (this.sent != other.sent) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.link == null) ? 0 : this.link.hashCode());
		result = (prime * result) + (this.sent ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportableItem [link=" + this.link + ", sent=" + this.sent + "]";
	}

}
