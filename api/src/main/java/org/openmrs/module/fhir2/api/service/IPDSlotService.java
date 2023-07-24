/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.service;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.fhir2.api.dao.IPDSlotDAO;
import org.openmrs.module.fhir2.model.FhirSlot;
import org.openmrs.util.PrivilegeConstants;

public interface IPDSlotService extends OpenmrsService {
	
	void setFhirSlotDao(IPDSlotDAO ipdSlotDAO);
	
	@Authorized({ PrivilegeConstants.GET_PATIENTS })
	FhirSlot getSlot(Integer slotId) throws APIException;
	
	@Authorized({ PrivilegeConstants.ADD_PATIENTS })
	FhirSlot saveSlot(FhirSlot fhirSlot) throws APIException;
}
