<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.course.form.label.code" path="code" />
	<acme:input-textbox code="student.course.form.label.title" path="title" />
	<acme:input-textbox code="student.course.form.label.abstract"
		path="abstract" />
	<acme:input-double code="student.course.form.label.retailPrice"
		path="retailPrice" />
	<acme:input-textbox code="student.course.form.label.body" path="body" />
	<acme:input-checkbox code="student.course.form.label.isTheoretical"
		path="isTheoretical" />
	<acme:input-url code="student.course.form.label.link" path="link" />

	<acme:button code="student.activity.form.button.list"
		action="/student/lecture/list-lecture?masterId=${id}" />
</acme:form>