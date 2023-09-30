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
	<acme:input-textbox code="auditor.audit-record.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.audit-record.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.audit-record.form.label.period-start" path="periodStart"/>
	<acme:input-moment code="auditor.audit-record.form.label.period-end" path="periodEnd"/>
	<acme:input-url code="auditor.audit-record.form.label.more-info" path="moreInfo"/>
	<acme:input-select code="auditor.audit-record.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-checkbox code="auditor.audit-record.form.label.edited" path="edited" readonly="True"/>
	<acme:hidden-data path="auditId"/>
	
	<acme:input-textbox code="auditor.audit-record.form.label.audit-code" path="auditCode" readonly="True"/>
	<jstl:if test="${_command== 'create' && edited }">
		<acme:input-checkbox code="audit.audit-record.form.label.accept" path="accept"/>
	</jstl:if>
	<jstl:choose>
		<jstl:when test="${_command == 'show' && !edited && auditNotPublished}">
			<acme:submit code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.audit-record.form.submit.delete" action="/auditor/audit-record/delete"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-record.form.submit.create" action="/auditor/audit-record/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>
