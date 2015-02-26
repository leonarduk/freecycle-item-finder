/**
 * All rights reserved. @Leonard UK Ltd.
 */
package com.leonarduk.itemfinder.freecycle;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class LatestPost.
 *
 * @author stephen
 * @version $Author: $: Author of last commit
 * @version $Rev: $: Revision of last commit
 * @version $Date: $: Date of last commit
 * @since 26 Feb 2015
 */
@Entity
@Table(name = "LatestPost")
public class LatestPost {

    /** The group name. */
    @Id
    private FreecycleGroups groupName;

    /** The latest post number. */
    @Basic
    private int latestPostNumber;

    public LatestPost() {
    }

    /**
     * Instantiates a new latest post.
     *
     * @param freecycleGroup
     *            the freecycle group
     */
    public LatestPost(final FreecycleGroups freecycleGroup) {
        this.groupName = freecycleGroup;
        this.latestPostNumber = 0;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public final FreecycleGroups getGroup() {
        return this.groupName;
    }

    /**
     * Gets the latest post number.
     *
     * @return the latest post number
     */
    public final int getLatestPostNumber() {
        return this.latestPostNumber;
    }

}
