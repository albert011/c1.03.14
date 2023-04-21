<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title"
		width="10%" />
	<acme:list-column code="lecturer.lecture.list.label.Abstract"
		path="Abstract" width="30%" />
	<acme:list-column
		code="lecturer.lecture.list.label.estimatedLearningTime"
		path="estimatedLearningTime" width="10%" />
	<acme:list-column code="lecturer.lecture.list.label.body" path="body"
		width="20%" />
	<acme:list-column code="lecturer.lecture.list.label.isTheoretical"
		path="isTheoretical" width="10%" />
	<acme:list-column code="lecturer.lecture.list.label.link" path="link"
		width="10%" />
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="lecturer.lecture.list.button.create"
		action="/lecturer/lecture/create" />
</jstl:if>
