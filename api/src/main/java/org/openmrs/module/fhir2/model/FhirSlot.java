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

import javax.persistence.*;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Order;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "fhir_slot")
public class FhirSlot extends BaseOpenmrsMetadata {
	
	// Based on https://hl7.org/fhir/R4/slot.html v4.0.1
	
	private static final long serialVersionUID = 1L;
	
	public enum SlotStatus {
		SCHEDULED,
		MISSED,
		COMPLETED,
		CANCELLED
	}
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "slot_id")
	private Integer id;
	
	/**
	 * The location Where schedule occurs
	 */
	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName = "location_id")
	private Location location;
	
	/**
	 * Whether this schedule is in active use
	 */
	@Column(name = "overbooked", nullable = false)
	private boolean overbooked = Boolean.FALSE;
	
	/**
	 * The Service Category of the Schedule
	 */
	@OneToOne
	@JoinColumn(name = "service_category_id", referencedColumnName = "concept_id")
	private Concept serviceCategoryId;
	
	/**
	 * The Service Type of the Schedule
	 */
	@OneToOne
	@JoinColumn(name = "service_type_id", referencedColumnName = "concept_id")
	private Concept serviceTyppeId;
	
	/**
	 * The Speciality of the Schedule
	 */
	@OneToOne
	@JoinColumn(name = "speciality_id", referencedColumnName = "concept_id")
	private Concept specialityId;
	
	/**
	 * The Appointment Type of the Schedule
	 */
	@OneToOne
	@JoinColumn(name = "appointment_type_id", referencedColumnName = "concept_id")
	private Concept appointmentTypeId;
	
	/**
	 * The entity that belongs to a Schedule
	 */
	@OneToMany
	@JoinColumn(name = "schedule_reference_id", referencedColumnName = "schedule_id")
	private FhirSchedule schedule;
	
	/**
	 * The Start Date the Slot
	 */
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	
	/**
	 * The End Date the Slot
	 */
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	
	/**
	 * Any Comment for the Slot
	 */
	@Column(name = "comment")
	private String comment;
	
	/**
	 * The current status of the slot.
	 */
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private FhirSlot.SlotStatus status;
	
	/**
	 * Order of the Slot
	 */
	@OneToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private Order orderId;
	
}


