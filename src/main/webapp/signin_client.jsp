<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FlowBank Login</title>

    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Upright:wght@400&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="./index.css">
    <style>
        :root {
            --main-color: #755f44;
            --bg-color: #fffaf4;
            --box-color: #d5bea4;
            --alt-color: #b59875;
            --footer-bg: #b8a692;
            --border-color: #382712;
            --input-bg: #faf3eb;
        }
        body {
            margin: 0;
            padding: 0;
            font-family: 'Cormorant Upright', serif;
            background-color: var(--bg-color);
            color: var(--main-color);
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 95px;
            line-height: normal;
        }
        header {
            text-align: center;
        }
        .logo {
            font-size: 128px;
        }
        .tagline {
            font-size: 36px;
            margin-top: 10px;
        }
        .line {
            border-top: 1px solid #000;
            width: 900px;
            margin: 20px auto;
        }
        .client-container {
            position: relative;
            width: 400px;
            height: 400px;
            background-color: var(--box-color);
            border-radius: 25px;
            box-shadow: 0 -5px 4px rgba(0, 0, 0, 0.25);
            display: flex;
            flex-direction: column;
            align-items: center;
            padding-top: 60px;
        }
        input[type="text"], input[type="password"] {
            width: 310px;
            height: 60px;
            font-size: 28px;
            border: 1px solid var(--main-color);
            border-radius: 25px;
            padding-left: 15px;
            background-color: var(--bg-color);
            color: var(--main-color);
            margin: 10px 0;
            box-shadow: 0 0 10px rgba(0,0,0,0.25);
        }
        .button {
            width: 312px;
            height: 60px;
            font-size: 36px;
            border-radius: 25px;
            border: 1px solid var(--main-color);
            background-color: var(--main-color);
            color: var(--bg-color);
            cursor: pointer;
            margin-top: 10px;
        }
        .forgot-password {
            margin-top: 10px;
            font-size: 18px;
            cursor: pointer;
        }
        .role-selector {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 10px;
            position: relative;
        }
        .role-button {
            width: 150px;
            height: 46px;
            border-radius: 25px;
            background-color: var(--alt-color);
            color: white;
            font-size: 28px;
            border: 1px solid var(--main-color);
            cursor: pointer;
        }
        footer {
            width: 100%;
            height: 250px;
            background-color: var(--footer-bg);
            display: flex;
            justify-content: space-around;
            align-items: center;
            position: relative;
            border-radius: 25px 25px 0 0;
            color: var(--border-color);
        }
        .currency-block, .contact-block {
            display: flex;
            flex-direction: column;
            gap: 5px;

        }
        .currency-title, .contacts-title {
            font-weight: bold;
            font-size: 28px;
        }
        .social-logo {
            position: absolute;
            left: 5%; top: 50%;
            transform: translateY(-50%);
            width: 180px;
            height: 180px;
            border-radius: 50%;
            border: 1px solid var(--border-color);
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 96px;
            background-color: var(--bg-color);
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">FlowBank</div>
    <div class="line"></div>
    <div class="tagline">Manage your finances easily</div>
</header>

<form class="client-container" method="post" action="login">

    <div class="role-selector" style="position: absolute; top: 20px;">
        <button type="button" class="role-button" style="font-family: 'Cormorant Upright'; font-size: 28px;" disabled>Client</button>
        <button type="button" class="role-button" style="font-family: 'Cormorant Upright'; font-size: 28px; color: rgba(56, 39, 18, 1) ;" onclick="location.href='signin_admin.jsp'">Admin</button>
    </div>

    <div style="height: 60px;"></div>

    <input type="text" name="fullName" placeholder="Fullname" style="font-family: 'Cormorant Upright'; font-size: 28px;" required>
    <input type="password" name="password" placeholder="Password" style="font-family: 'Cormorant Upright'; font-size: 28px;" required>
    <button class="button" type="submit" style="font-family: 'Cormorant Upright'; font-size: 28px;">Sign In</button>
    <div class="forgot-password">Forgot password?</div>

    <% if (request.getAttribute("error") != null) { %>
    <p style="color:red; font-size:18px;"><%= request.getAttribute("error") %></p>
    <% } %>
</form>

<footer>
    <div class="currency-block" style="margin-left: 250px;">
        <div class="currency-title">Currency Exchange Rate</div>
        <div style="font-size: 24px;">USD ___ 41.30 ___ 44.73</div>
        <div style="font-size: 24px;">EUR ___ 41.62 ___ 45.63</div>
    </div>
    <div class="contact-block">
        <div class="contacts-title">Contacts</div>
        <div style="font-size: 24px;">+38(xxx)-xxx-xx-xx</div>
        <div style="font-size: 24px;">st.FlowBank, 20</div>
    </div>
    <div class="social-logo">FB</div>
</footer>
</body>
</html>



