<%@ page import="org.extremecomponents.table.core.internal.CollectionTableDataSource" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.extremecomponents.org/tags" prefix="ec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<META HTTP-EQUIV="expires" CONTENT="0">
    <title>eXtremeTest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/extremecomponents.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/extremecomponents.js"></script>

</head>

<% java.util.List presidents = new java.util.ArrayList(); %>

<% for (int k = 0; k < 113; k++) { %>
<% java.util.Map president = new java.util.HashMap(); %>
<% president.put("firstName", "George"); %>
<% president.put("lastName", "Washington"); %>
<% president.put("nickname", "Father of His Country"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1789-1797"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("firstName", "John"); %>
<% president.put("lastName", "Adams"); %>
<% president.put("sex", "0"); %>
<% president.put("term", "1797-1801"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("firstName", "Thomas"); %>
<% president.put("lastName", "Jefferson"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1801-09"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("firstName", "James"); %>
<% president.put("lastName", "Madison"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1809-17"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("firstName", "James"); %>
<% president.put("lastName", "Monroe"); %>
<% president.put("sex", "0"); %>
<% president.put("term", "1817-25"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("firstName", "John"); %>
<% president.put("lastName", "Adams"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1825-29"); %>
<% presidents.add(president); %>

<%
    }
%>

<%
    HashMap sexMap = new HashMap();
    sexMap.put("1", "Male");
    sexMap.put("0", "Female");
    request.setAttribute("sexMap", sexMap);
    request.setAttribute("presidents",presidents);
%>

<body style="margin:25px;">

<p style="font-family: Verdana;font-size:14px;">
    Congratulations!! You have successfully configured eXtremeTable!
</p>

<br>
<% long start = System.currentTimeMillis();%>
<ec:table items="${presidents}" totalRows="${fn:length(presidents)}"
          action="${pageContext.request.contextPath}/index.jsp"
          title="Presidents"
          width="60%"
          rowsDisplayed="15" filterable="true" var="t" tableId="ec_224" retrieveRowsCallback="org.extremecomponents.table.callback.MemoryRowsCallback"
        >
    <ec:exportXls fileName="Test.xls"/>
    <ec:exportPdf fileName="Test.pdf" font="sans"/>
    <ec:exportCsv fileName="Test.csv"/>
    <ec:row>
        <ec:column property="name" viewsAllowed="html" filterable="false" alias="checkbox" headerCell="selectAll"
                   cell="checkbox"/>
        <ec:column alias="Information">
            <ec:column alias="fullName">
                 <ec:column property="firstName" sortable="true"/>
                 <ec:column property="lastName"/>
            </ec:column>
            <ec:column alias="sex">
            ${sexMap[t.sex]}
        </ec:column>
        </ec:column>


        <ec:column property="term"/>
    </ec:row>
</ec:table>
<%=System.currentTimeMillis()-start%>
<br>

<br>


</body>
</html>