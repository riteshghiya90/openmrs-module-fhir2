/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.providers.r3;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.server.IBundleProvider;
import ca.uhn.fhir.rest.param.TokenAndListParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.MethodNotAllowedException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.OperationOutcome;
import org.hl7.fhir.dstu3.model.Task;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openmrs.module.fhir2.FhirConstants;
import org.openmrs.module.fhir2.api.FhirTaskService;
import org.openmrs.module.fhir2.providers.r4.MockIBundleProvider;
import org.openmrs.module.fhir2.providers.util.TaskVersionConverter;

@RunWith(MockitoJUnitRunner.class)
public class TaskFhirResourceProviderTest extends BaseFhirR3ProvenanceResourceTest<org.hl7.fhir.r4.model.Task> {
	
	private static final String TASK_UUID = "bdd7e368-3d1a-42a9-9538-395391b64adf";
	
	private static final String WRONG_TASK_UUID = "df34a1c1-f57b-4c33-bee5-e601b56b9d5b";
	
	private static final int START_INDEX = 0;
	
	private static final int END_INDEX = 10;
	
	@Mock
	private FhirTaskService taskService;
	
	@Getter(AccessLevel.PUBLIC)
	private TaskFhirResourceProvider resourceProvider;
	
	private org.hl7.fhir.r4.model.Task task;
	
	@Before
	public void setup() {
		resourceProvider = new TaskFhirResourceProvider();
		resourceProvider.setFhirTaskService(taskService);
	}
	
	@Before
	public void initTask() {
		task = new org.hl7.fhir.r4.model.Task();
		task.setId(TASK_UUID);
		setProvenanceResources(task);
	}
	
	private List<IBaseResource> get(IBundleProvider results) {
		return results.getResources(START_INDEX, END_INDEX);
	}
	
	@Test
	public void getResourceType_shouldReturnResourceType() {
		assertThat(resourceProvider.getResourceType(), equalTo(Task.class));
		assertThat(resourceProvider.getResourceType().getName(), equalTo(Task.class.getName()));
	}
	
	@Test
	public void getTaskById_shouldReturnMatchingTask() {
		IdType id = new IdType();
		id.setValue(TASK_UUID);
		when(taskService.get(TASK_UUID)).thenReturn(task);
		
		Task result = resourceProvider.getTaskById(id);
		
		assertThat(result.isResource(), is(true));
		assertThat(result, notNullValue());
		assertThat(result.getId(), notNullValue());
		assertThat(result.getId(), equalTo(TASK_UUID));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getTaskByWithWrongId_shouldThrowResourceNotFoundException() {
		IdType idType = new IdType();
		idType.setValue(WRONG_TASK_UUID);
		
		assertThat(resourceProvider.getTaskById(idType).isResource(), is(true));
		assertThat(resourceProvider.getTaskById(idType), nullValue());
	}
	
	@Test
	public void createTask_shouldCreateNewTask() {
		when(taskService.create(any(org.hl7.fhir.r4.model.Task.class))).thenReturn(task);
		
		MethodOutcome result = resourceProvider.createTask(TaskVersionConverter.convertTask(task));
		
		assertThat(result, notNullValue());
		assertThat(result.getCreated(), is(true));
		assertThat(result.getResource(), notNullValue());
		assertThat(result.getResource().getIdElement().getIdPart(), equalTo(TASK_UUID));
	}
	
	@Test
	public void updateTask_shouldUpdateTask() {
		when(taskService.update(eq(TASK_UUID), any(org.hl7.fhir.r4.model.Task.class))).thenReturn(task);
		
		MethodOutcome result = resourceProvider.updateTask(new IdType().setValue(TASK_UUID),
		    TaskVersionConverter.convertTask(task));
		
		assertThat(result, notNullValue());
		assertThat(result.getResource(), notNullValue());
		assertThat(result.getResource().getIdElement().getIdPart(), equalTo(TASK_UUID));
	}
	
	@Test(expected = InvalidRequestException.class)
	public void updateTask_shouldThrowInvalidRequestForUuidMismatch() {
		when(taskService.update(eq(WRONG_TASK_UUID), any(org.hl7.fhir.r4.model.Task.class)))
		        .thenThrow(InvalidRequestException.class);
		
		resourceProvider.updateTask(new IdType().setValue(WRONG_TASK_UUID), TaskVersionConverter.convertTask(task));
	}
	
	@Test(expected = InvalidRequestException.class)
	public void updateTask_shouldThrowInvalidRequestForMissingId() {
		org.hl7.fhir.r4.model.Task noIdTask = new org.hl7.fhir.r4.model.Task();
		
		when(taskService.update(eq(TASK_UUID), any(org.hl7.fhir.r4.model.Task.class)))
		        .thenThrow(InvalidRequestException.class);
		
		resourceProvider.updateTask(new IdType().setValue(TASK_UUID), TaskVersionConverter.convertTask(noIdTask));
	}
	
	@Test(expected = MethodNotAllowedException.class)
	public void updateTask_shouldThrowMethodNotAllowedIfDoesNotExist() {
		org.hl7.fhir.r4.model.Task wrongTask = new org.hl7.fhir.r4.model.Task();
		wrongTask.setId(WRONG_TASK_UUID);
		
		when(taskService.update(eq(WRONG_TASK_UUID), any(org.hl7.fhir.r4.model.Task.class)))
		        .thenThrow(MethodNotAllowedException.class);
		
		resourceProvider.updateTask(new IdType().setValue(WRONG_TASK_UUID), TaskVersionConverter.convertTask(wrongTask));
	}
	
	@Test
	public void searchTasks_shouldReturnMatchingTasks() {
		List<org.hl7.fhir.r4.model.Task> tasks = new ArrayList<>();
		tasks.add(task);
		
		when(taskService.searchForTasks(any()))
		        .thenReturn(new MockIBundleProvider<>(tasks, 10, 1));
		
		TokenAndListParam status = new TokenAndListParam();
		TokenParam statusToken = new TokenParam();
		statusToken.setValue("ACCEPTED");
		status.addAnd(new TokenOrListParam().add(statusToken));
		
		IBundleProvider results = resourceProvider.searchTasks(null, null, status, null, null, null, null);
		
		List<IBaseResource> resultList = get(results);
		
		assertThat(results, notNullValue());
		assertThat(resultList, hasSize(greaterThanOrEqualTo(1)));
		assertThat(resultList.iterator().next().fhirType(), equalTo(FhirConstants.TASK));
	}
	
	@Test
	public void deleteTask_shouldDeleteRequestedTask() {
		OperationOutcome result = resourceProvider.deleteTask(new IdType().setValue(TASK_UUID));
		
		assertThat(result, notNullValue());
		assertThat(result.getIssue(), notNullValue());
		assertThat(result.getIssueFirstRep().getSeverity(), equalTo(OperationOutcome.IssueSeverity.INFORMATION));
		assertThat(result.getIssueFirstRep().getDetails().getCodingFirstRep().getCode(), equalTo("MSG_DELETED"));
	}
}
