<%--
- banner.jsp
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

<div class="rounded"
	style="background: <acme:message code='master.banner.background'/>">
	<img src="images/banner.png" id="imagenBanner"
		style="width: 100% !important"
		alt="<acme:message code='master.banner.alt'/>"
		class="img-fluid mx-auto d-block rounded" />
</div>



<script type="text/javascript">
	$(document).ready(function() {
		    let cookie = document.cookie;
		    
		    
		    if (cookie != null) {
        
                     // Reemplaza "imagenSrc" con el nombre de tu cookie
                        imagenSrc = document.cookie;
                        const imagen = document.getElementById('imagenBanner');
                        let cookieValue = document.cookie.split(" ")[1];
                        
                        let cookieValue2 = cookieValue.split("=")[1];
                        let decodedCookieValue = decodeURIComponent(cookieValue2);
                        
                        let decodedCookieValue2 = decodeURIComponent(cookieValue2);
                 
                        imagen.src=decodedCookieValue;
                        

                
                 
             
            }
	

		});
</script>