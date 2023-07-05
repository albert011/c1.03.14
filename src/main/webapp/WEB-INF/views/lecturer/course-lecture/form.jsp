<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
		<acme:input-select
				code="lecturer.course-lecture.form.label.courseCode" path="course"
				choices="${courses}" readonly="true" />
			<acme:input-select
				code="lecturer.course-lecture.form.label.lectureTitle"
				path="lecture" choices="${lectures}" readonly="true" />
			<acme:submit code="lecturer.course-lecture.form.button.delete"
				action="/lecturer/course-lecture/delete" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
		<acme:input-select
				code="lecturer.course-lecture.form.label.courseCode" path="course"
				choices="${courses}" readonly="true" />
			<acme:input-select
				code="lecturer.course-lecture.form.label.lectureTitle"
				path="lecture" choices="${lectures}" />
			<acme:submit code="lecturer.course-lecture.form.button.add-lectures"
				action="/lecturer/course-lecture/create?courseId=${courseId}" />
			
		</jstl:when>
	</jstl:choose>
</acme:form>

