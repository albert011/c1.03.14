<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.currency.form.label.systemCurrency"
		path="systemCurrency" />
	<acme:input-textbox code="administrator.currency.form.label.acceptedCurrencies"
		path="acceptedCurrencies" />
	
	<acme:submit code="administrator.currency.form.button.update"
		action="/administrator/currency/update" />
		
</acme:form>