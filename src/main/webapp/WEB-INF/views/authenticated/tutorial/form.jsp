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
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant" path="assistant"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>
	<acme:input-select code="authenticated.tutorial.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.message" path="abstractMessage"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.goals" path="goals"/>	
	<acme:input-double code="authenticated.tutorial.form.label.time" path="estimatedTotalTime"/>
	
</acme:form>

<script>
</script>