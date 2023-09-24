<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h3>
	<acme:message code="company.dashboard.form.title.num-practicum-by-month"/>
</h3>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.january"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('JANUARY')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.february"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('FEBRUARY')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.march"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('MARCH')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.april"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('APRIL')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.may"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('MAY')}"/>
		</td>	
		<th scope="row">
			<acme:message code="company.dashboard.form.label.june"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('JUNE')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.july"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('JULY')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.august"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('AUGUST')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.september"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('SEPTEMBER')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.october"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('OCTOBER')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.november"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('NOVEMBER')}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.december"/>
		</th>
		<td>
			<acme:print value="${numPracticumByMonth.get('DECEMBER')}"/>
		</td>
				
	</tr>
</table>
<h3>
	<acme:message code="company.dashboard.form.title.practicum-statistics"/>
</h3>

<h3>
	
</h3>

<table class="table table-sm">
	<tr>
				<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.average"/>
		</th>
		<td>
			<acme:print value="${averagePracticumLength}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.stDev"/>
		</th>
		<td>
			<acme:print value="${deviationPracticumLength}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.minimum"/>
		</th>
		<td>
			<acme:print value="${minimumPracticumLength}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-statistics.maximum"/>
		</th>
		<td>
			<acme:print value="${maximumPracticumLength}"/>
		</td>
		
	</tr>
</table>

<h3>
	<acme:message code="company.dashboard.form.title.session-statistics"/>
</h3>

<h3>
	
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.average"/>
		</th>
		<td>
			<acme:print value="${averageSessionLength}"/>
		</td>
				<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.stDev"/>
		</th>
		<td>
			<acme:print value="${deviationPracticumLength}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.minimum"/>
		</th>
		<td>
			<acme:print value="${minimumSessionLength}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-statistics.maximum"/>
		</th>
		<td>
			<acme:print value="${maximumSessionLength}"/>
		</td>

	</tr>
</table>


<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.practicum-number"/>
		</th>
		<td>
			<acme:print value="${numPracticum}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.session-number"/>
		</th>
		<td>
			<acme:print value="${numSession}"/>
		</td>
	</tr>
</table>