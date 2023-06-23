<%--
- list.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for assistant particular
- purposes.  The copyright owner does not offer assistant warranties or representations, nor do
- they accept assistant liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.session-tutorial.list.label.timeStart" path="timeStart" width="10%"/>
	<acme:list-column code="assistant.session-tutorial.list.label.timeEnd" path="timeEnd" width="10%"/>
	<acme:list-column code="assistant.session-tutorial.list.label.title" path="title" width="10%"/>
	<acme:list-column code="assistant.session-tutorial.list.label.draft-status" path="draftMode" width="5%"/>
	<acme:list-column code="assistant.session-tutorial.list.label.message" path="abstractMessage" width="65%"/>
</acme:list>

<acme:button code="assistant.session.create" action="/assistant/session-tutorial/create"/>
