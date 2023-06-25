<%--
- form.jsp
-
- Copyright (C) 2022-2023 Javier Fernández Castillo.
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
	<acme:input-textbox code="authenticated.practicum.form.label.code" path="code"/>
	<acme:input-textarea code="authenticated.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.practicum.form.label.abstract" path="abstractText"/>
	<acme:input-textarea code="authenticated.practicum.form.label.goals" path="goals"/>
	<acme:input-double code="authenticated.practicum.form.label.estimated-total-time" path="estimatedTotalTime"/>
	<acme:input-textbox code="authenticated.practicum.form.label.course" path="courseCode" readonly="true"/>
	<acme:input-textbox code="authenticated.practicum.form.label.company" path="companyName" readonly="true"/>
</acme:form>
