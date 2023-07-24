/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.service.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fhir2.api.dao.IPDSlotDAO;
import org.openmrs.module.fhir2.api.service.IPDSlotService;
import org.openmrs.module.fhir2.model.FhirSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class IPDSlotServiceImpl extends BaseOpenmrsService implements IPDSlotService {
	
	private static final Logger log = LoggerFactory.getLogger(IPDSlotServiceImpl.class);
	
	private IPDSlotDAO ipdSlotDAO;
	
	@Override
	public void setFhirSlotDao(IPDSlotDAO ipdSlotDAO) {
		this.ipdSlotDAO = ipdSlotDAO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public FhirSlot getSlot(Integer slotId) throws APIException {
		return ipdSlotDAO.getSlot(slotId);
	}
	
	@Override
	public FhirSlot saveSlot(FhirSlot fhirSlot) throws APIException {
		return ipdSlotDAO.saveSlot(fhirSlot);
	}
}
