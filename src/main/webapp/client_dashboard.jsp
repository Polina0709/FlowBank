<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.paymentapp.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FlowBank – Client Dashboard</title>
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

        body {
            margin: 0;
            padding: 0;
            font-family: 'Cormorant Upright', serif;
            background-color: var(--bg-color);
            color: var(--main-color);
            display: flex;
            flex-direction: column;
            align-items: center;
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
            gap: 20px;
            margin-bottom: 30px;
        }

        .nav-button {
            padding: 10px 30px;
            font-family: 'Cormorant Upright', serif;
            font-size: 24px;
            border-radius: 25px;
            background-color: var(--alt-color);
            color: white;
            border: 1px solid var(--main-color);
            cursor: pointer;
            text-decoration: none;
        }

        .section {
            width: 80%;
            background-color: var(--box-color);
            border-radius: 25px;
            padding: 20px 30px;
            margin-bottom: 40px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }

        h2 {
            text-align: center;
            margin-top: 0;
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin-top: 10px;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: center;
            font-size: 18px;
            border-bottom: 1px solid var(--main-color);
            background: #faf1e2;
        }

        th:first-child {
            border-top-left-radius: 20px;
        }
        th:last-child {
            border-top-right-radius: 20px;
        }

        tr:last-child td:first-child {
            border-bottom-left-radius: 20px;
        }
        tr:last-child td:last-child {
            border-bottom-right-radius: 20px;
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
        .card-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }

        .bank-card {
            width: 300px;
            height: 100px;
            background: linear-gradient(135deg, #e7ded0, #d5bea4);
            border-radius: 20px;
            padding: 25px;
            box-shadow: 0 5px 12px rgba(0, 0, 0, 0.3);
            color: var(--border-color);
            font-size: 20px;
            position: relative;
            font-family: 'Cormorant Upright', serif;
        }

        .card-number {
            margin-top: 20px;
            font-size: 22px;
            letter-spacing: 2px;
        }

        .card-expiry {
            position: absolute;
            top: 45px;
            right: 25px;
            font-size: 18px;
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
        <a href="topup.jsp" class="nav-button">Top Up</a>
        <a href="account" class="nav-button">Accounts</a>
        <a href="profile.jsp" class="nav-button">Profile</a>
        <a href="logout" class="nav-button">Log Out</a>
    </div>
</header>

<%
    List<Card> cards = (List<Card>) request.getAttribute("cards");
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
%>

<div class="section">
    <h2 style="font-size: 28px; font-family: 'Cormorant Upright', serif;">Your Card</h2>

    <div class="card-container">
        <%
            if (cards != null && !cards.isEmpty()) {
                for (Card card : cards) {
                    String cardNumber = card.getCardNumber();
                    String masked = "xxxxx xx** **** " + cardNumber.substring(cardNumber.length() - 4);
                    String exp = card.getExpirationDate().toString(); // yyyy-MM-dd
                    String expShort = exp.length() >= 7 ? exp.substring(5, 7) + "/" + exp.substring(2, 4) : "xx/xx";
        %>
        <div class="bank-card">
            <div class="card-number"><%= masked %></div>
            <div class="card-expiry"><%= expShort %></div>
        </div>
        <%
            }
        } else {
        %>
        <p style="text-align: center;">Картки відсутні.</p>
        <% } %>
    </div>
</div>


<div class="section">
    <h2 style="font-size: 28px; font-family: 'Cormorant Upright', serif;">Payment History</h2>
    <table style="border-radius: 25px;">

        <%
            if (transactions != null && !transactions.isEmpty()) {
                for (Transaction t : transactions) {
        %>
        <tr>
            <td><%= t.getSenderAccountId() == 0 ? "System" : t.getSenderAccountId() %></td>
            <td><%= t.getReceiverAccountId() %></td>
            <td><%= t.getAmount() %></td>
            <td><%= t.getCreatedAt() %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5">Немає транзакцій для відображення.</td>
        </tr>
        <% } %>
    </table>
</div>

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
