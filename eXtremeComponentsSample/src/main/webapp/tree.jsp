<%@ page import="org.extremecomponents.table.core.internal.CollectionTableDataSource" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.extremecomponents.org/tags" prefix="ec" %>

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


<% java.util.Map president = new java.util.HashMap(); %>
<% president.put("id", "1"); %>
<% president.put("firstName", "George"); %>
<% president.put("lastName", "Washington"); %>
<% president.put("nickname", "Father of His Country"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1789-1797"); %>
<% president.put("parent", ""); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("id", "2"); %>
<% president.put("firstName", "John"); %>
<% president.put("lastName", "Adams"); %>
<% president.put("sex", "0"); %>
<% president.put("term", "1797-1801"); %>
<% president.put("parent", "1"); %>
<% presidents.add(president); %>


<% president = new java.util.HashMap(); %>
<% president.put("id", "3"); %>
<% president.put("firstName", "Thomas"); %>
<% president.put("lastName", "Jefferson"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1801-09"); %>
<% president.put("parent", "2"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("id", "4"); %>
<% president.put("firstName", "James"); %>
<% president.put("lastName", "Madison"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1809-17"); %>
<% president.put("parent", ""); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("id", "5"); %>
<% president.put("firstName", "James"); %>
<% president.put("lastName", "Monroe"); %>
<% president.put("sex", "0"); %>
<% president.put("term", "1817-25"); %>
<% president.put("parent", "1"); %>
<% presidents.add(president); %>

<% president = new java.util.HashMap(); %>
<% president.put("id", "6"); %>
<% president.put("firstName", "John"); %>
<% president.put("lastName", "Adams"); %>
<% president.put("sex", "1"); %>
<% president.put("term", "1825-29"); %>
<% presidents.add(president); %>

<%
    CollectionTableDataSource dataSource = new CollectionTableDataSource(presidents);

%>
<% request.setAttribute("dataSource", dataSource);
    HashMap sexMap = new HashMap();
    sexMap.put("1", "Male");
    sexMap.put("0", "Female");
    request.setAttribute("sexMap", sexMap);
%>

<body style="margin:25px;">

<p style="font-family: Verdana;font-size:14px;">
    Congratulations!! You have successfully configured eXtremeTable!
</p>

<br>


<ec:tree parentAttribute="parent" identifier="id" dataSource="${dataSource}" var="t">
    <ec:row>
        <ec:column property="name" viewsAllowed="html" filterable="false" alias="checkbox" headerCell="selectAll"
                   cell="checkbox"/>
        <ec:column cell="tree" alias="id" property="id"/>
        <ec:column property="firstName" sortable="true"/>
        <ec:column property="lastName"/>
        <ec:column alias="sex">${sexMap[t.sex]}</ec:column>
        <ec:column property="term"/>
    </ec:row>
</ec:tree>

<br>

</body>
</html>