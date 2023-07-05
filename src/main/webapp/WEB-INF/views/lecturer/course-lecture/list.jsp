<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.course-lecture.list.label.courseCode"
		path="courseCode" width="20%" />
	<acme:list-column code="lecturer.course-lecture.list.label.lectureTitle"
		path="lectureTitle" width="40%" />
</acme:list>

<acme:button code="lecturer.course-lecture.form.button.add-lectures"
	action="/lecturer/course-lecture/create?courseId=${param.courseId}" />