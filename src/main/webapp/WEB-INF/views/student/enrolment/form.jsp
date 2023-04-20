<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.form.label.code"
		path="code" />
	<acme:input-textbox code="student.enrolment.form.label.motivation"
		path="motivation" />
	<acme:input-textbox code="student.enrolment.form.label.goals"
		path="goals" />
	<acme:input-textbox code="student.enrolment.form.label.workTime"
		path="workTime" readonly="true"/>
	<acme:input-select code="student.enrolment.label.course" path="course"
		choices="${courses}" />
	<acme:input-textbox code="student.enrolment.form.label.creditCard"
		path="creditCard" />
	<acme:input-textbox code="student.enrolment.form.label.cvc" path="cvc" />
	<acme:input-textbox code="student.enrolment.form.label.expiryDate"
		path="expiryDate" />
	<acme:input-textbox code="student.enrolment.form.label.holderName"
		path="holderName" />
	<acme:input-textbox code="student.enrolment.form.label.lowerNibble"
		path="lowerNibble" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="student.enrolment.form.button.update"
				action="/student/enrolment/update" />
			<acme:submit code="student.enrolment.form.button.delete"
				action="/student/enrolment/delete" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create"
				action="/student/enrolment/create" />
		</jstl:when>
	</jstl:choose>
	<jstl:if test="${_command == finalized == true}">
		<acme:submit code="student.enrolment.list.button.finalize"
			action="/student/enrolment/finalize" />
	</jstl:if>
</acme:form>
