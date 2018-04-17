<%--
  Created by IntelliJ IDEA.
  User: serhii.onyshchenko
  Date: 4/16/2018
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<title>Bouquet</title>
<p id="bouquet"></p>

<body>
<script>
    var a = '';
    try {
        var obj = JSON.parse('${bouquet}');
        a += "Bouquet name: " + obj.bouquet.name + ": ";
        a += "price: " + obj.bouquet.price;
        a += "<br>";
        a += "Flowers: ";
        a += "<br>";
        for (i = 0; i < obj.bouquet.flowers.length; i++) {
            a += "flower name: " + obj.bouquet.flowers[i].name + ", "
                + "id: " + obj.bouquet.flowers[i].id + ", "
                + "length: " + obj.bouquet.flowers[i].length + ", "
                + "fresh: " + obj.bouquet.flowers[i].fresh + ", "
                + "price: " + obj.bouquet.flowers[i].price + ", "
                + "petals: " + obj.bouquet.flowers[i].petals + ", "
                + "color: " + obj.bouquet.flowers[i].color + ", "
                + "spike: " + obj.bouquet.flowers[i].spike + ", "
                + "<br>";
        }
    } catch (err) {
        a += "Invalid Id";
    }


    document.getElementById("bouquet").innerHTML = a;

</script>

<a href=/>Back</a>
</body>
</html>
