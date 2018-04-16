<%--
  Created by IntelliJ IDEA.
  User: serhii.onyshchenko
  Date: 4/12/2018
  Time: 7:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
    <form action="/getId" method="post">
        <div>
            <label for="id">Bouquet Id</label>
            <input type="text" placeholder="Enter Id" id="id" name="id">
            <button type="submit">Get bouquet</button>
        </div>
    </form>


</body>
</html>
