<%@page language="java" import="acme.roles.Assistant"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox code="any.course.form.label.code" path="code" />
	<acme:input-textbox code="any.course.form.label.title" path="title" />
	<acme:input-textarea code="any.course.form.label.Abstract"
		path="Abstract" />
	<acme:input-textbox code="any.course.form.label.type" path="type"
		readonly="true" />
	<acme:input-url code="any.course.form.label.link" path="link" />
	<acme:input-money code="any.course.form.label.retail-price"
		path="retailPrice" />
</acme:form>
