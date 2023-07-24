/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.dao.impl;

import org.hibernate.SessionFactory;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.fhir2.api.dao.IPDScheduleDAO;
import org.openmrs.module.fhir2.model.FhirSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateIPDScheduleDAO implements IPDScheduleDAO {
	
	private static final Logger log = LoggerFactory.getLogger(HibernateIPDScheduleDAO.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public FhirSchedule getSchedule(Integer scheduleId) throws DAOException {
		return (FhirSchedule) sessionFactory.getCurrentSession().get(FhirSchedule.class, scheduleId);
	}
	
	@Override
	public FhirSchedule saveSchedule(FhirSchedule fhirSchedule) throws DAOException {
		return null;
	}
}
