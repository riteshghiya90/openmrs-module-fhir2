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
import org.openmrs.module.fhir2.api.dao.IPDScheduleDAO;
import org.openmrs.module.fhir2.api.service.IPDScheduleService;
import org.openmrs.module.fhir2.model.FhirSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class IPDScheduleServiceImpl extends BaseOpenmrsService implements IPDScheduleService {
	
	private static final Logger log = LoggerFactory.getLogger(IPDScheduleServiceImpl.class);
	
	private IPDScheduleDAO ipdScheduleDAO;
	
	/**
	 * @see IPDScheduleService#setFhirScheduleDao(IPDScheduleDAO) (org.openmrs.api.db.PersonDAO)
	 */
	@Override
	public void setFhirScheduleDao(IPDScheduleDAO ipdScheduleDAO) {
		this.ipdScheduleDAO = ipdScheduleDAO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public FhirSchedule getSchedule(Integer scheduleId) throws APIException {
		return ipdScheduleDAO.getSchedule(scheduleId);
	}
	
	@Override
	public FhirSchedule saveSchedule(FhirSchedule schedule) throws APIException {
		return ipdScheduleDAO.saveSchedule(schedule);
	}
	
}
