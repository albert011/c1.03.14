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
		path="workTime" readonly="true" />
	<acme:input-select code="student.enrolment.label.course" path="course"
		choices="${courses}" />
	<jstl:choose>
		<jstl:when test="${_command != 'create' && finalized == false}">
			<acme:input-textbox code="student.enrolment.form.label.creditCard"
				path="creditCard" placeholder="XXXX/XXXX/XXXX/XXXX" />
			<acme:input-textbox code="student.enrolment.form.label.cvc"
				path="cvc" placeholder="XXX" />
			<acme:input-textbox code="student.enrolment.form.label.expiryDate"
				path="expiryDate" placeholder="XX/XX" />
			<acme:input-textbox code="student.enrolment.form.label.holderName"
				path="holderName" />
			<acme:input-textbox code="student.enrolment.form.label.lowerNibble"
				path="lowerNibble" readonly="true" />
		</jstl:when>
		<jstl:when test="${_command != 'create && finalized == true'}">
			<acme:input-textbox code="student.enrolment.form.label.holderName"
				path="holderName" />
			<acme:input-textbox code="student.enrolment.form.label.lowerNibble"
				path="lowerNibble" readonly="true" />
		</jstl:when>
	</jstl:choose>

	<jstl:choose>
		<jstl:when
			test="${acme:anyOf(_command, 'show|update|finalise') && finalized == false}">
			<acme:submit code="student.enrolment.form.button.update"
				action="/student/enrolment/update" />
			<acme:submit code="student.enrolment.form.button.delete"
				action="/student/enrolment/delete" />
			<acme:submit code="student.enrolment.form.button.finalise"
				action="/student/enrolment/finalise" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create"
				action="/student/enrolment/create" />
		</jstl:when>
	</jstl:choose>
	<jstl:if test="${_command == 'show'}">
		<acme:button code="student.enrolment.form.button.activity"
			action="/student/activity/list-mine?masterId=${id}" />
	</jstl:if>
</acme:form>
