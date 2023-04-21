<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textbox code="auditor.audit.form.label.strong-points" path="strongPoints"/>
	<acme:input-textbox code="auditor.audit.form.label.weak-points" path="weakPoints"/>
	<acme:input-select code="auditor.audit.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-textbox code="auditor.audit.form.label.course" path="courseTitle" readonly="${_command != 'create'}"/>
	<jstl:if test="${_command != 'create'}">
		<acme:input-textbox code="auditor.audit.form.label.auditor" path="code" readonly="True"/>
		<acme:input-checkbox code="auditor.audit.form.label.published" path="isPublished" readonly="True"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && isPublished}">
			<acme:button code="auditor.audit.form.button.records" action="/auditor/audit-record/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'show' && !isPublished}">
			<acme:button code="auditor.audit.form.button.records" action="/auditor/audit-record/list?masterId=${id}"/>
			<acme:button code="auditor.audit.form.button.update" action="/auditor/audit/update?id=${id}"/>
			<acme:submit code="auditor.audit.form.submit.delete" action="/auditor/audit/delete"/>
			<acme:submit code="auditor.audit.form.submit.publish" action="/auditor/audit/publish"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.submit.create" action="/auditor/audit/create"/>
		</jstl:when>
		
	
	</jstl:choose>
	
</acme:form>
