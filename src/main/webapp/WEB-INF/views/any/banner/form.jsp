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

<table class="table table-sm">
	<tr>
		<td>
			<a id="enlace"><acme:print value="${pictureLink}"/></a>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(document).ready(function(){
	let enlace = document.getElementById('enlace');
	let enlace2 = enlace.textContent;
	const url = 'http://localhost:8080/Acme-L3-D03/master/welcome';
	let encodedEnlace = encodeURIComponent(enlace2);

	
	document.cookie = "imagenSrc="+encodedEnlace+"; path=/";
	
	window.location.href=url;
	});
</script>