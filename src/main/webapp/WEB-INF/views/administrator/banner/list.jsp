<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.instantiationMoment" path="instantiationMoment"
		width="10%" />
	<acme:list-column code="administrator.banner.list.label.slogan"
		path="slogan" width="30%" />
</acme:list>

<jstl:if test="${_command == 'list-all'}">
	<acme:button code="administrator.banner.list.button.create"
		action="/administrator/banner/create" />
</jstl:if>