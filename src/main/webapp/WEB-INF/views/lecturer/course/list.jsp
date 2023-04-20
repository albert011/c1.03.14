<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.course.list.label.code" path="code"
		width="10%" />
	<acme:list-column code="lecturer.course.list.label.Abstract"
		path="Abstract" width="30%" />
	<acme:list-column
		code="lecturer.course.list.label.retailPrice"
		path="retailPrice" width="10%" />
	<acme:list-column code="lecturer.course.list.label.body" path="body"
		width="20%" />
	<acme:list-column code="lecturer.course.list.label.isTheoretical"
		path="isTheoretical" width="10%" />
	<acme:list-column code="lecturer.course.list.label.link" path="link"
		width="10%" />
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="lecturer.course.list.button.create"
		action="/lecturer/course/create" />
</jstl:if>