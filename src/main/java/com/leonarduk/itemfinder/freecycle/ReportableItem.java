/**
 *
 */
package com.leonarduk.itemfinder.freecycle;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private String link;

    /** The sent. */
    @Basic
    private boolean sent;

    /**
     * Instantiates a new reportable item.
     */
    protected ReportableItem() {
        // for Jpa
    }

    /**
     * Instantiates a new reportable item.
     *
     * @param linkValue
     *            the link
     * @param sentFlag
     *            the sent
     */
    public ReportableItem(final String linkValue, final boolean sentFlag) {
        super();
        this.link = linkValue;
        this.sent = sentFlag;
    }

    /*
     * (non-Javadoc)
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
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.hashcodeHelper(this.link);
        final int seed = 1231;
        final int seed2 = 1237;
        if (this.sent) {
            result += seed;
        }
        else {
            result += seed2;
        }
        result = prime * result;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return "ReportableItem [link=" + this.link + ", sent=" + this.sent
               + "]";
    }

}
