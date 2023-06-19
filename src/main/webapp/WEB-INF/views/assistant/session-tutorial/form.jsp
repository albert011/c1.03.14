<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for authenticated particular
- purposes.  The copyright owner does not offer authenticated warranties or representations, nor do
- they accept authenticated liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<%@taglib prefix="personal" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="assistant.session-tutorial.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.session-tutorial.form.label.message" path="abstractMessage"/>
	<acme:input-select code="assistant.session-tutorial.form.label.tutorial" path="tutorial" choices="${tutorials}"/>
	<acme:input-select code="assistant.session-tutorial.form.label.type" path="type" choices="${types}"/>
	<acme:input-moment code="assistant.session-tutorial.form.label.timeStart" path="timeStart"/>	
	<acme:input-moment code="assistant.session-tutorial.form.label.timeEnd" path="timeEnd"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.session-tutorial.form.update" action="/assistant/session-tutorial/update"/>
			<acme:submit code="assistant.session-tutorial.form.delete" action="/assistant/session-tutorial/delete"/>
			<acme:submit code="assistant.session-tutorial.form.publish" action="/assistant/session-tutorial/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.session-tutorial.form.button.apply" action="/assistant/session-tutorial/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>

<script>
</script>