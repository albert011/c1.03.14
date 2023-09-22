<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="True">
	<acme:input-textbox code="authenticated.course.form.label.code" path="code" />
	<acme:input-textbox code="authenticated.course.form.label.title" path="title" />
	<acme:input-textbox code="authenticated.course.form.label.Abstract"	path="Abstract" />
	<acme:input-double code="authenticated.course.form.label.retailPrice" path="retailPrice" />
	<acme:input-textbox code="authenticated.course.form.label.type" path="type" />
	<acme:input-url code="authenticated.course.form.label.link" path="link" />
	<acme:button code="authenticated.course.form.button.audit-list" action="/authenticated/audit/list?courseId=${id}" />
</acme:form>