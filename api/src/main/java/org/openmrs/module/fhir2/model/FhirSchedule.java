/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openmrs.BaseOpenmrsMetadata;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "fhir_schedule")
public class FhirSchedule extends BaseOpenmrsMetadata {
	
	// Based on https://hl7.org/fhir/R4/schedule.html v4.0.1

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "schedule_id")
	private Integer id;

	/**
	 * The entity who benefits from the performance of the service specified in the task (e.g., the
	 * patient).
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "for_reference_id", referencedColumnName = "reference_id")
	private FhirReference forReference;

	/**
	 * The location Where task occurs
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_reference_id", referencedColumnName = "reference_id")
	private FhirReference locationReference;

	/**
	 * Whether this schedule is in active use
	 */
	@Column(name = "active", nullable = false)
	private boolean active = Boolean.TRUE;
}
