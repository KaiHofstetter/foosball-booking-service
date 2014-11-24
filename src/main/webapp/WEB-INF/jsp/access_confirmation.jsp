<%@ page
        import="org.springframework.security.core.AuthenticationException" %>
<%@ page
        import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ page
        import="org.springframework.security.web.WebAttributes" %>
<%@ taglib prefix="authz"
           uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Foosball Booking Service</title>
    <link type="text/css" rel="stylesheet"
          href="../webjars/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="../webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="../webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
    <h1>Foosball Booking Service</h1>

    <%
        if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null && !(session.getAttribute(
                WebAttributes.AUTHENTICATION_EXCEPTION) instanceof UnapprovedClientAuthenticationException)) {
    %>
    <div class="error">
        <h2>Woops!</h2>

        <p>
            Access could not be granted. (<%=((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage()%>)
        </p>
    </div>
    <%
        }
    %>
    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

    <authz:authorize ifAllGranted="ROLE_USER">
        <h2>Please Confirm</h2>

        <h3><c:out value="${client.clientId}"/> requests permission for:</h3>

        <form id="confirmationForm" name="confirmationForm"
              action="<%=request.getContextPath()%>/oauth/authorize" method="post">
            <input name="user_oauth_approval" value="true" type="hidden"/>

            <div class="form-group">
                <c:forEach items="${scopes}" var="scope">
                    <div class="row">
                        <label class="col-sm-3 control-label">
                            <c:if test="${scope.key=='scope.Read_Booking_List'}">Reading the booking list</c:if>
                            <c:if test="${scope.key=='scope.Add_Booking'}">Adding bookings on behalf of you</c:if>
                        </label>

                        <div class="col-sm-2">
                            <input type="radio" name="${scope.key}" value="true">Approve</input>
                        </div>
                        <div class="col-sm-2">
                            <input type="radio" name="${scope.key}" value="false" checked>Deny</input>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="row">
                <button class="col-sm-1 btn btn-primary" type="submit">Submit</button>
            </div>
        </form>

    </authz:authorize>

</div>

</body>
</html>
