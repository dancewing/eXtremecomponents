<%@ page import="org.extremecomponents.table.core.internal.CollectionTableDataSource" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.extremecomponents.org/tags" prefix="ec" %>

<html>

<head>
    <title>eXtremeTest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/extremecomponents.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/extremecomponents.js"></script>
</head>

<% java.util.List presidents = new java.util.ArrayList(); %>

<% for (int k = 0; k < 50; k++) { %>
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
<div id="tableAjaxId">

</div>
<ec:table dataSource="${dataSource}"
          action="${pageContext.request.contextPath}/ajax.jsp"
          title="Presidents"
          width="60%"
          rowsDisplayed="50" medianRowsDisplayed="100" maxRowsDisplayed="150" filterable="true" var="t" tableId="ec_224" inPlace="tableAjaxId"
        >
    <ec:exportXls fileName="Test.xls"/>
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

<button onclick="javascript:document.forms.ec_224.ec_eti.value='ec_224';document.forms.ec_224.ec_224_ev.value='html';document.forms.ec_224.ec_224_efn.value='Test.xls';document.forms.ec_224.setAttribute('action','/index.jsp');document.forms.ec_224.setAttribute('method','post');document.forms.ec_224.submit()">Click</button>
<br>

</body>
</html>