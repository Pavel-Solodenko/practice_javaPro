<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Prog Academy</title>
  </head>
  <body>
    <% String login = (String)session.getAttribute("user_login"); %>
    <% String age = request.getParameter("age"); %>
    <% String data = request.getParameter("data"); %>
    <% String loginOkOrNo = request.getParameter("loginOkOrNo"); %>
    <% String passwordOkOrNo = request.getParameter("passwordOkOrNo"); %>
    <% String passwordGood = request.getParameter("passwordGood"); %>

    <% if (age != null && age.equals("notValid")) { %>
        <p>Age is invalid,please try again!</p>
    <% } %>

    <% if (age != null && age.equals("notEnough")) {%>
        <p> You are too young for this web-site :(</p>
    <% }%>

    <% if (data != null && data.equals("empty")) { %>
        <p> The form has empty fields, please enter data again :o </p>
    <% }%>

    <% if (loginOkOrNo != null && loginOkOrNo.equals("false")) { %>
        <p> Incorrect login! </p>
    <% } %>

    <% if (passwordOkOrNo != null && passwordOkOrNo.equals("false")) { %>
        <p> Incorrect password! </p>
    <% } %>

    <% if (passwordGood != null && passwordGood.equals("false")) { %>
        <p>Your password must be 10-character length and contain: <br>
            Minimum one later<br>
            Minimum one capital later<br>
            Minimum one digit<br>
            Minimum one special symbol<br>
        </p>
    <% } %>
    <% if (login == null || "".equals(login)) { %>
        <form action="/login" method="POST">
            Login: <input type="text" name="login"><br>
            Password: <input type="password" name="password" minlength="10" ><br>
            Age: <input type="text" name="age" maxlength="2"><br>
            <input type="submit" />
        </form>
    <% } else { %>
        <h1>You are logged in as: <%= login %></h1>
        <br>Click this link to <a href="/login?a=exit">logout</a>
    <% } %>
  </body>
</html>
