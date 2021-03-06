/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.foregroundmodules.systemadmin;

import se.unlogic.hierarchy.core.beans.SimpleSectionDescriptor;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.cache.SectionCache;
import se.unlogic.standardutils.collections.KeyNotCachedException;
import se.unlogic.standardutils.time.TimeUtils;

public class SectionUpdater extends SectionBackgroundTask {

	public SectionUpdater(SectionCache sectionCache, SimpleSectionDescriptor simpleSectionDescriptor, User user) {

		super(sectionCache, simpleSectionDescriptor, user);
	}

	@Override
	public void run() {

		try {
			long starttime = System.currentTimeMillis();

			log.info("Section update thread for section " + this.getSectionDescriptorBean() + " started...");

			sectionCache.update(this.getSectionDescriptorBean());

			log.info("Section " + this.getSectionDescriptorBean() + " updated by user " + this.getUser() + " using background thread after waiting " + TimeUtils.millisecondsToString(System.currentTimeMillis() - starttime) + " ms");
		} catch (KeyNotCachedException e) {
			log.info("Unable to update section " + this.getSectionDescriptorBean() + " requested by user " + this.getUser() + ", section unloaded.");
		}
	}
}
