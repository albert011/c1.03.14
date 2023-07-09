<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title"
		width="30%" />
	<acme:list-column code="lecturer.lecture.list.label.type"
		path="type" width="30%" />
</acme:list>

<jstl:if test="${_command == 'list-mine' && (param.courseId==null)}">
	<acme:button code="lecturer.lecture.list.button.create"
		action="/lecturer/lecture/create" />
</jstl:if>
