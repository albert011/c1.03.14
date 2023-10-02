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
			<acme:message code="auditor.dashboard.form.label.total-audits-hands-on"/>
		</th>
		<td>
			<acme:print value="${totalAuditsHandOn}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-audits-theory"/>
		</th>
		<td>
			<acme:print value="${totalAuditsTheory}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.avg-period"/>
		</th>
		<td>
			<acme:print value="${avgPeriodLengthH} h ${avgPeriodLengthM} m ${avgPeriodLengthS} s"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.std-period"/>
		</th>
		<td>
			<acme:print value="${stdPeriodLengthH} h ${stdPeriodLengthM} m ${stdPeriodLengthS} s"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.min-period"/>
		</th>
		<td>
			<acme:print value="${minPeriodLengthH} h ${minPeriodLengthM} m ${minPeriodLengthS} s"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.max-period"/>
		</th>
		<td>
			<acme:print value="${maxPeriodLengthH} h ${maxPeriodLengthM} m ${maxPeriodLengthS} s"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.average-records"/>
		</th>
		<td>
			<acme:print value="${averageAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviation-records"/>
		</th>
		<td>
			<acme:print value="${standardDeviationAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.min-records"/>
		</th>
		<td>
			<acme:print value="${minimumAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.max-records"/>
		</th>
		<td>
			<acme:print value="${maximumAuditRecords}"/>
		</td>
	</tr>
</table>

<acme:return/>

