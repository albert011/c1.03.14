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

<h3>
	<acme:message code="student.dashboard.form.title.workbook" />
</h3>


<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.periodTime.numTheory" /></th>
		<td><acme:print value="${totalTheoryActivities}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.periodTime.numHandsOn" /></th>
		<td><acme:print value="${totalHandsOnActivities}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.periodTime.average" /></th>
		<td><acme:print value="${averageTimePeriod}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.periodTime.deviation" /></th>
		<td><acme:print value="${deviationTimePeriod}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.periodTime.minimum" />
		</th>
		<td><acme:print value="${minimumTimePeriod}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.periodTime.maximum" />
		</th>
		<td><acme:print value="${maximumTimePeriod}" /></td>

	</tr>
</table>

<h3>
	<acme:message code="student.dashboard.form.title.learning" />
</h3>

<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.learningTime.average" /></th>
		<td><acme:print value="${averageLearningTime}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.learningTime.deviation" /></th>
		<td><acme:print value="${deviationLearningTime}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.learningTime.minimum" />
		</th>
		<td><acme:print value="${minimumLearningTime}" /></td>
		<th scope="row"><acme:message
				code="student.dashboard.form.label.learningTime.maximum" />
		</th>
		<td><acme:print value="${maximumLearningTime}" /></td>

	</tr>
</table>


