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

<h2>
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.total-number-tutorials-hands-on"/>
		</th>
		<td>
			<acme:print value="${numberOfTutorialsHandsOn}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.total-number-tutorials-theory"/>
		</th>
		<td>
			<acme:print value="${numberOfTutorialsTheory}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${averageTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${deviationTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${minTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${maxTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-sessions"/>
		</th>
		<td>
			<acme:print value="${averageTimeSessions}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-time-sessions"/>
		</th>
		<td>
			<acme:print value="${deviationTimeSessions}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-time-sessions"/>
		</th>
		<td>
			<acme:print value="${minTimeSessions}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-time-sessions"/>
		</th>
		<td>
			<acme:print value="${maxTimeSessions}"/>
		</td>
	</tr>
</table>

<acme:return/>

