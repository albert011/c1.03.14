
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${!draftMode}">

	<acme:input-select path="course" code="lecturer.coursesLectures.form.label.course-code" choices="${courses}" readonly="true"/>
	<acme:input-select path="lecture" code="lecturer.coursesLectures.form.label.lecture-code" choices="${lectures}"/>

	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="lecturer.coursesLectures.form.button.update" action="/lecturer/course-lecture/update"/>
			<acme:submit code="lecturer.coursesLectures.form.button.delete" action="/lecturer/course-lecture/delete"/>
			<acme:button code="lecturer.coursesLectures.form.button.show-lecture-details" action="/lecturer/lecture/show?id=${lectureId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.coursesLectures.form.button.attach-to-course" action="/lecturer/course-lecture/create?coursesId=${courseId}"/>
			<br>
			<br>
			<h3><acme:message code="lecturer.coursesLectures.form.cant-you-find-the-lecture"/></h3>
			
			<acme:button code="lecturer.coursesLectures.form.button.create-new-lecture" action="/lecturer/lecture/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

