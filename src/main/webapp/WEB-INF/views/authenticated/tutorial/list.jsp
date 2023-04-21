<%--
- list.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for authenticated particular
- purposes.  The copyright owner does not offer authenticated warranties or representations, nor do
- they accept authenticated liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.tutorial.list.label.code" path="code" width="10%"/>
	<acme:list-column code="authenticated.tutorial.list.label.title" path="title" width="10%"/>
	<acme:list-column code="authenticated.tutorial.list.label.message" path="abstractMessage" width="70%"/>
	<acme:list-column code="authenticated.tutorial.list.label.time" path="estimatedTotalTime" width="10%"/>
</acme:list>
