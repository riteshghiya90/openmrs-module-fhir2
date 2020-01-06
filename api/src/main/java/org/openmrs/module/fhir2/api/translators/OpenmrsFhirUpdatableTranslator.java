/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.translators;

/**
 * Generic interface for a translator between OpenMRS data and FHIR resources that supports updating
 * the OpenMRS resource
 * 
 * @param <T> OpenMRS data type
 * @param <U> FHIR resource type
 */
public interface OpenmrsFhirUpdatableTranslator<T, U> extends OpenmrsFhirTranslator<T, U> {
	
	/**
	 * Maps a FHIR resource to an existing OpenMRS data element
	 * 
	 * @param existingObject the existingObject to update
	 * @param resource the resource to map
	 * @return an updated version of the existingObject
	 */
	T toOpenmrsType(T existingObject, U resource);
}
