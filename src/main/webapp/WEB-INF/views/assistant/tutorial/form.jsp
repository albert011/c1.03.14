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
	<acme:input-textbox code="assistant.tutorial.form.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorial.form.label.message" path="abstractMessage"/>
	<acme:input-textbox code="assistant.tutorial.form.label.goals" path="goals"/>	
	<acme:input-double code="assistant.tutorial.form.label.time" path="estimatedTotalTime"/>
	<acme:input-checkbox code="assistant.tutorial.form.label.publish" path="isPublished"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') }">
			<acme:button code="assistant.tutorial.form.show" action="/assistant/tutorial/list"/>
			<acme:button code="assistant.tutorial.form.delete" action="/assistant/tutorial/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<personal:confirm-submit code="assistant.tutorial.form.button.apply" action="/assistant/tutorial/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>

<script>
</script>