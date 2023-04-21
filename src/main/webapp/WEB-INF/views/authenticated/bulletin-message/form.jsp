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
	<acme:input-textbox code="authenticated.bulletin.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.bulletin.form.label.message" path="message"/>
	<acme:input-moment code="authenticated.bulletin.form.label.instantiation" path="instantiation"/>
	<acme:input-url code="authenticated.bulletin.form.label.link" path="link"/>	
	<acme:input-checkbox code="authenticated.bulletin.form.label.critical" path="isCritical"/>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:button code="authenticated.bulletin.form.button.list" action="/authenticated/bulletin-message/list"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' }">
			<personal:confirm-submit code="authenticated.bulletin.form.button.apply" action="/authenticated/bulletin-message/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>

<script>
</script>