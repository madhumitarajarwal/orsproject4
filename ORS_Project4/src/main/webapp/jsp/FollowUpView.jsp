<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.FollowUpCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> User Page</title>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#udatee" ).datepicker({
      changeMonth: true,
      changeYear: true,
	  yearRange:'1980:2020',
    });
  } );
  </script>



<body>
   <jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean" scope="request"></jsp:useBean>
   <%@ include file="Header.jsp"%>
   
   <center>

    <form action="<%=ORSView.FOLLOWUP_CTL%>" method="post">

        <%
            List l = (List) request.getAttribute("roleList");
        %>

        
    <div align="center">    
            <h1>
 				
           		<% if(bean != null && bean.getId() > 0) { %>
            <tr><th><font size="5px"> Update FollowUp </font>  </th></tr>
            	<%}else{%>
			<tr><th><font size="5px"> Add FollowUp </font>  </th></tr>            
            	<%}%>
            </h1>
   
            <h3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
	       
</div>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table>
                <tr>
                    <th align="left">Patient Name <span style="color: red">*</span> :</th>
                    <td><input type="text" name="patient" placeholder="Enter Patient Name" size="26"  value="<%=DataUtility.getStringData(bean.getPatient())%>"></td>
                    <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("patient", request)%></font></td> 
                    
                </tr>
    
    <tr><th style="padding: 3px"></th></tr>          
              
              <tr>
                    <th align="left">Doctor Name <span style="color: red">*</span> :</th>
                    <td><input type="text" name="doctor" placeholder="Enter doctor Name" size="26" value="<%=DataUtility.getStringData(bean.getDoctor())%>"></td>
                     <td style="position: fixed"><font  color="red"> <%=ServletUtility.getErrorMessage("doctor", request)%></font></td>
                </tr>
    <tr><th style="padding: 3px"></th></tr>                          
                
                <tr>
                    <th align="left">Visit Date <span style="color: red">*</span> :</th>
                    <td><input type="text" name="visitDate" placeholder="Enter visit date" size= "26" readonly="readonly" id="udatee" value="<%=DataUtility.getDateString(bean.getVisitDate())%>"></td>
                      <td style="position: fixed;">	<font color="red"> <%=ServletUtility.getErrorMessage("visitDate", request)%></font></td>
                </tr>
                <tr>
    <tr><th style="padding: 3px"></th></tr>          
                
                <tr>
                    <th align="left">Fees <span style="color: red">*</span> :</th>
                    <td><input type="text" name="fees" maxlength="10" placeholder="Enter Fees" size= "26" value="<%=DataUtility.getStringData(bean.getFees())%>"></td>
                    <td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("fees", request)%></font></td>
                </tr>
    <tr><th style="padding: 3px"></th></tr>          

                <tr ><th></th>
                <%
                if(bean.getId()>0){
                %>
                <td colspan="2" >
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=FollowUpCtl.OP_UPDATE%>">
                      &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=FollowUpCtl.OP_CANCEL%>"></td>
                
                <% }else{%>
                
                <td colspan="2" > 
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=FollowUpCtl.OP_SAVE%>">
                    &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=FollowUpCtl.OP_RESET%>"></td>
                
                <% } %>
                </tr>
            </table>
    </form>
    </center>

    <%@ include file="Footer.jsp"%>
</body>
</html>