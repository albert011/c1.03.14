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
		style="width: 100% !important;max-height: 300px"
		alt="<acme:message code='master.banner.alt'/>"
		class="img-fluid mx-auto d-block rounded" />
</div>



<script type="text/javascript">
	$(document).ready(function() {
		    let cookie = document.cookie;
		    
		    
		    if (cookie != null) {
        
                     // Reemplaza "imagenSrc" con el nombre de tu cookie
				const cookieName = "imagenSrc=";
				const cookieArray = document.cookie.split(";");
				
				let decodedCookieValue = null;
				
				for (let i = 0; i < cookieArray.length; i++) {
				  let cookie = cookieArray[i];
				  while (cookie.charAt(0) === " ") {
				    cookie = cookie.substring(1);
				  }
				  if (cookie.indexOf(cookieName) === 0) {
				    decodedCookieValue = decodeURIComponent(cookie.substring(cookieName.length));
				    break;
				  }
				}
				
				if (decodedCookieValue) {
				  const imagen = document.getElementById('imagenBanner');
				  imagen.src = decodedCookieValue;
				}                        

                
                 
             
            }
	

		});
</script>