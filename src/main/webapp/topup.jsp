<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FlowBank – Поповнення рахунку</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Upright:wght@400&display=swap" rel="stylesheet">
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

        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: 'Cormorant Upright', serif;
            background-color: var(--bg-color);
            color: var(--main-color);
            display: flex;
            flex-direction: column;
        }

        header {
            text-align: center;
            padding: 40px 0 10px 0;
        }

        .logo {
            font-size: 72px;
        }

        .line {
            border-top: 1px solid #000;
            width: 800px;
            margin: 10px auto 20px;
        }

        .nav-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-bottom: 30px;
        }

        .nav-button {
            padding: 10px 30px;
            font-size: 20px;
            border-radius: 25px;
            background-color: var(--alt-color);
            color: white;
            border: 1px solid var(--main-color);
            cursor: pointer;
            text-decoration: none;
        }

        main {
            flex: 1 0 auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding-bottom: 60px;
        }

        .form-container {
            width: 500px;
            background-color: var(--box-color);
            border-radius: 25px;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        h2 {
            text-align: center;
            margin: 0;
        }

        .form-container form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        input[type="text"],
        input[type="number"] {
            height: 50px;
            border-radius: 25px;
            border: 1px solid var(--main-color);
            padding-left: 15px;
            font-size: 20px;
            background-color: var(--bg-color);
            color: var(--main-color);
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
        }

        .button {
            height: 50px;
            font-size: 24px;
            border-radius: 25px;
            border: 1px solid var(--main-color);
            background-color: var(--main-color);
            color: var(--bg-color);
            cursor: pointer;
        }

        .message {
            text-align: center;
            font-size: 18px;
            margin-top: 10px;
        }

        .message.success { color: green; }
        .message.error { color: red; }

        footer {
            flex-shrink: 0;
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
            left: 5%;
            top: 50%;
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
    <div class="nav-buttons">
        <a href="client-dashboard" class="nav-button">Home</a>
        <a href="transfer.jsp" class="nav-button">Transfer</a>
        <a href="account" class="nav-button">Account</a>
        <a href="profile.jsp" class="nav-button">Profile</a>
        <a href="logout" class="nav-button">Log Out</a>
    </div>
</header>

<main>
    <div class="form-container">
        <h2>Account Top Up</h2>
        <form method="post" action="topup">
            <input type="text" name="accountNumber" placeholder="Account Number" style="font-family: 'Cormorant Upright'; font-size: 20px;" required>
            <input type="number" name="amount" step="0.01" min="0.01" placeholder="Amount" style="font-family: 'Cormorant Upright'; font-size: 20px;" required>
            <button class="button" type="submit" style="font-family: 'Cormorant Upright'; font-size: 20px;">Top Up</button>
        </form>

        <% if (request.getAttribute("success") != null) { %>
        <div class="message success" style="font-family: 'Cormorant Upright'; font-size: 16px;"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
        <div class="message error" style="font-family: 'Cormorant Upright'; font-size: 16px;"><%= request.getAttribute("error") %></div>
        <% } %>
    </div>
</main>

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


