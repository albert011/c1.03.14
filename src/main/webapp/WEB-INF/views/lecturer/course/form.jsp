<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.course.form.label.code"
		path="code" />
		<acme:input-textbox code="lecturer.course.form.label.title"
		path="title" />
	<acme:input-textbox code="lecturer.course.form.label.Abstract"
		path="Abstract" />
	<acme:input-double
		code="lecturer.course.form.label.retailPrice"
		path="retailPrice" />
	<acme:input-textbox code="lecturer.course.form.label.body" path="body" />
	<acme:input-checkbox code="lecturer.course.form.label.isTheoretical"
		path="isTheoretical" />
	<acme:input-url code="lecturer.course.form.label.link" path="link" />
	<jstl:choose>
		<jstl:when
			test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="lecturer.course.form.button.update"
				action="/lecturer/course/update" />
			<acme:submit code="lecturer.course.form.button.delete"
				action="/lecturer/course/delete" />
			<acme:submit code="lecturer.course.form.button.publish"
				action="/lecturer/course/publish" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create"
				action="/lecturer/course/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>