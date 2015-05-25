<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>

	<body bgcolor="#E8E8E8">
					
		<h1 align="center">Testing UserManage Spring Application</h1>
		
      <%--   <a href="<c:url value="j_spring_security_logout" />" > Logout</a> --%>
        <c:url var="logoutUrl" value="/logout"/>
		<form action="${logoutUrl}" method="post">
		  <input type="submit" value="Log out" />
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		      
         <!-- This form include table with all users in database and ability delete users from db -->     
         <form:form action="restrictedAccess.htm" method="post">
            <table align="center" style="border:1px dashed black;">
              <thead>
	               <tr>
				    <th>User ID</th>
				    <th>First name</th>
				    <th>Last name</th>
				    <th>Middle name</th>
				    <th>User Position</th>
				    <th>Action</th>
				   </tr>
			   </thead>
			   
			   <tbody>
				<c:forEach var="listValue" items="${usersModel}">
					<tr>
						<td>${listValue.userId}</td>
						<td>${listValue.firstName}</td>
						<td>${listValue.lastName}</td>
						<td>${listValue.middleName}</td>
						<td>${listValue.userPosition}</td>
						<td><input type="submit" name="delete" value="Delete user"></td>
						<td><input type="hidden" name="usernameVal" value="${listValue.username}"/>
					</tr>
				</c:forEach>
			   </tbody>
		    </table>
		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		 </form:form>
		 <!-- End of code form of users and user delete ability -->
		  
		  <hr> <br>
		  
		  <!-- This form is to add user to database -->
		  <form:form action="restrictedAccess.htm" method="post">
		  	<table border="0" align="center">
                <tr>
                    <td>User Name:</td>
                    <td><form:input path="username"/>
                    <form:errors path="username" cssstyle="color: red;"/>
                    </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:password path="password"/>
                    <form:errors path="password" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><form:input path="firstName"/>
                    <form:errors path="firstName" cssClass="error"/>  
                    </td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><form:input path="lastName"/>
                     <form:errors path="lastName" cssClass="error"/>  
                     </td>
                </tr>
                <tr>
                    <td>Middle Name:</td>
                    <td><form:input path="middleName"/></td>
                </tr>
                <tr>
                    <td>Position:</td>
                    <td><form:input path="userPosition"/>
                     <form:errors path="userPosition" cssClass="error"/>  
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" class="btn" name="save" value="Add user" /></td>
                </tr>
            </table>
		  </form:form>
		  <!-- This form is to add user to database -->
		  
	</body>
</html>