<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.paymentapp.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>FlowBank – Мої рахунки</title>
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

    .section {
      width: 80%;
      background-color: var(--box-color);
      border-radius: 25px;
      padding: 20px 30px;
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
      background-color: var(--bg-color);
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

    .action-form button {
      padding: 6px 16px;
      font-size: 16px;
      border-radius: 15px;
      background-color: var(--alt-color);
      color: white;
      border: 1px solid var(--main-color);
      cursor: pointer;
    }

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
    <a href="topup.jsp" class="nav-button">Top Up</a>
    <a href="profile.jsp" class="nav-button">Profile</a>
    <a href="logout" class="nav-button">Log Out</a>
  </div>
</header>

<main>
  <div class="section">
    <h2>Our accounts</h2>
    <table>
      <tr>
        <th>ID</th>
        <th>Account Number</th>
        <th>Balance</th>
        <th>Status</th>
        <th></th>
      </tr>
      <%
        List<Account> accounts = (List<Account>) request.getAttribute("accounts");
        if (accounts != null && !accounts.isEmpty()) {
          for (Account acc : accounts) {
      %>
      <tr>
        <td><%= acc.getId() %></td>
        <td><%= acc.getAccountNumber() %></td>
        <td><%= acc.getBalance() %> ₴</td>
        <td><%= acc.isBlocked() ? "Blocked" : "Active" %></td>
        <td>
          <% if (!acc.isBlocked()) { %>
          <form method="post" action="account" class="action-form">
            <input type="hidden" name="accountId" value="<%= acc.getId() %>">
            <input type="hidden" name="action" value="block">
            <button type="submit">Block</button>
          </form>
          <% } else { %>
          <span style="opacity: 0.6;">Unavailable</span>
          <% } %>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="5">No accounts</td>
      </tr>
      <% } %>
    </table>
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

