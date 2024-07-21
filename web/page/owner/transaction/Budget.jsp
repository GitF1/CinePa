


<%@page import="java.util.List"%>
<%@page import="model.Transaction"%>
<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@page import="DAO.transaction.TransactionDAO"%>
<%@page import="util.RouterURL"%>
<%@page import=" jakarta.servlet.*"%>

<%
    TransactionDAO dao;
    StatisticOwnerDAO daoStatis;

    dao = new TransactionDAO(getServletContext());
    daoStatis = new StatisticOwnerDAO(getServletContext());

    Integer userID = (int) request.getSession().getAttribute("userID");
    String role = (String) request.getSession().getAttribute("role");

    if (userID == null || !"OWNER".equalsIgnoreCase(role)) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to view this page.");
        return;
    }

    Integer cinemaChainID = daoStatis.getCinemaChainOfUser(userID);

    if (cinemaChainID == null) {
        response.sendRedirect(RouterURL.ERORPAGE);
        return;
    }
    double balance = dao.getBalance(userID);
    double revenue = daoStatis.getTotalRevenueByChainID(cinemaChainID);
//    double balance = (double) request.getAttribute("balance");
    String message = (String) request.getAttribute("message");
    //double revenue = (double) request.getAttribute("revenue");
    List<Transaction> transactions = dao.getTransactionsByUserId(userID);
%>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Budget App</title>
        <!-- Font Awesome Icons -->
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
            />
        <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500&display=swap"
            rel="stylesheet"
            />
        <!-- Stylesheet -->
        <link rel="stylesheet" href="style.css" />
        <style>
            * {
                padding: 0;
                margin: 0;
                box-sizing: border-box;
                font-family: "Poppins", sans-serif;
            }
            body {
                background-color: #f7f9fd;
            }
            .wrapper {
                width: 90%;
                font-size: 16px;
                max-width: 43.75em;
                margin: 1em auto;
            }
            .container {
                width: 100%;
            }
            .sub-container {
                width: 100%;
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 3em;
            }
            .flex {
                display: flex;
                align-items: center;
            }
            .flex-space {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .wrapper h3 {
                color: #363d55;
                font-weight: 500;
                margin-bottom: 0.6em;
            }
            .container input {
                display: block;
                width: 100%;
                padding: 0.6em 0.3em;
                border: 1px solid #d0d0d0;
                border-radius: 0.3em;
                color: #414a67;
                outline: none;
                font-weight: 400;
                margin-bottom: 0.6em;
            }
            .container input:focus {
                border-color: #587ef4;
            }
            .total-amount-container,
            .user-amount-container {
                background-color: #ffffff;
                padding: 1.25em 0.9em;
                border-radius: 0.3em;
                box-shadow: 0 0.6em 1.2em rgba(28, 0, 80, 0.06);
            }
            .output-container {
                background-color: #587ef4;
                color: #ffffff;
                border-radius: 0.3em;
                box-shadow: 0 0.6em 1.2em rgba(28, 0, 80, 0.06);
                margin: 2em 0;
                padding: 1.2em;
            }
            .output-container p {
                font-weight: 500;
                margin-bottom: 0.6em;
            }
            .output-container span {
                display: block;
                text-align: center;
                font-weight: 400;
                color: #e5e5e5;
            }
            .submit {
                font-size: 1em;
                margin-top: 0.8em;
                background-color: #587ef4;
                border: none;
                outline: none;
                color: #ffffff;
                padding: 0.6em 1.2em;
                border-radius: 0.3em;
                cursor: pointer;
            }
            .list {
                background-color: #ffffff;
                padding: 1.8em 1.2em;
                box-shadow: 0 0.6em 1.2em rgba(28, 0, 80, 0.06);
                border-radius: 0.6em;
            }
            .sublist-content {
                width: 100%;
                border-left: 0.3em solid #587ef4;
                margin-bottom: 0.6em;
                padding: 0.5em 1em;
                display: grid;
                grid-template-columns: 3fr 2fr 1fr 1fr;
            }
            .product {
                font-weight: 500;
                color: #363d55;
            }
            .amount {
                color: #414a67;
                margin-left: 20%;
            }
            .icons-container {
                width: 5em;
                margin: 1.2em;
                align-items: center;
            }
            .product-title {
                margin-bottom: 1em;
            }
            .hide {
                display: none;
            }
            .error {
                color: #ff465a;
            }
            .edit {
                margin-left: auto;
            }
            .edit,
            .delete {
                background: transparent;
                cursor: pointer;
                margin-right: 1.5em;
                border: none;
                color: #587ef4;
            }
            @media screen and (max-width: 600px) {
                .wrapper {
                    font-size: 14px;
                }
                .sub-container {
                    grid-template-columns: 1fr;
                    gap: 1em;
                }
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0,0,0);
                background-color: rgba(0,0,0,0.4);
                padding-top: 60px;
            }
            .modal-content {
                background-color: #fefefe;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 400px;
                border-radius: 8px;
                text-align: center;
            }
            .modal-content h4 {
                margin: 20px 0;
            }
            .modal-content button {
                padding: 10px 20px;
                margin: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }
            .modal-content .confirm {
                background-color: #4CAF50;
                color: white;
            }
            .modal-content .confirm:hover {
                background-color: #45a049;
            }
            .modal-content .cancel {
                background-color: #f44336;
                color: white;
            }
            .modal-content .cancel:hover {
                background-color: #e53935;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            table th, table td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            table th {
                background-color: #f2f2f2;
            }
            table tbody tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            table tbody tr:hover {
               opacity: 0.6;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <div class="container">
                <div class="sub-container">
                    <!-- Budget -->
                    <div class="total-amount-container">
                        <h3>Budget</h3>
                        <p class="hide error" id="budget-error">
                            Value cannot be empty or negative
                        </p>
                        <% if (request.getAttribute("message") != null) {%>
                        <span class="error"><%= request.getAttribute("message")%></span>
                        <% }%>
                        <input
                            type="number"
                            id="total-amount"
                            placeholder="Enter Total Amount"
                            />
                        <button class="submit" id="total-amount-button">Request WithDraw</button>
                    </div>


                    <!-- Expenditure -->
                    <div class="user-amount-container">

                        <h3>Expenses</h3>
                        <p class="hide error" id="product-title-error">
                            Values cannot be empty
                        </p>
                        <input
                            type="text"
                            class="product-title"
                            id="product-title"
                            placeholder="Enter Title of Product"
                            />
                        <input
                            type="number"
                            id="user-amount"
                            placeholder="Enter Cost of Product"
                            />
                        <button class="submit" id="check-amount">Check Amount</button>
                    </div>
                </div>

                <!-- Output -->
                <div class="output-container flex-space">
                    <div>
                        <p>Total Budget</p>
                        <span id="amount"><%= revenue*0.95 - balance%></span>
                    </div>
                    <div>
                        <p>Expenses</p>
                        <span id="expenditure-value">5%</span>
                    </div>
                    <div>
                        <p>Balance</p>
                        <span id="balance-amount"><%= balance %></span>
                    </div>
                </div>
            </div>
            <!-- List -->
            <div class="list">
                <h3>Transactions</h3>
                <div class="list-container" id="list">
                    <table>
                        <thead>
                            <tr>
                                <td>Amount</td>
                                <td>Type</td>
                                <td>Status</td>
                                <td>Date</td>
                            </tr>
                        </thead>
                        <tbody >
                            <%
                                for (Transaction transaction : transactions) {
                            %>
                            <tr>
                                <td><%= transaction.getAmount()%></td>
                                <td><%= transaction.getTransactionType()%></td>
                                <td><%= transaction.getStatus()%></td>
                                <td><%= transaction.getTransactionDate()%></td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

        <div id="confirmationModal" class="modal">
            <div class="modal-content">
                <h4>Confirm Withdrawal</h4>
                <p id="confirm-message"></p>
                <button class="confirm" id="confirm-withdraw">Confirm</button>
                <button class="cancel" id="cancel-withdraw">Cancel</button>
            </div>
        </div>
        <!-- Script -->
        <script>
            let totalAmount = document.getElementById("total-amount");
            let userAmount = document.getElementById("user-amount");
            const checkAmountButton = document.getElementById("check-amount");
            const totalAmountButton = document.getElementById("total-amount-button");
            const productTitle = document.getElementById("product-title");
            const errorMessage = document.getElementById("budget-error");
            const productTitleError = document.getElementById("product-title-error");
            const productCostError = document.getElementById("product-cost-error");
            const amount = document.getElementById("amount");
            const expenditureValue = document.getElementById("expenditure-value");
            const balanceValue = document.getElementById("balance-amount");
            const list = document.getElementById("list");

            //
            const confirmationModal = document.getElementById("confirmationModal");
            const confirmMessage = document.getElementById("confirm-message");
            const confirmWithdrawButton = document.getElementById("confirm-withdraw");
            const cancelWithdrawButton = document.getElementById("cancel-withdraw");

            let tempAmount = 0;

            //Set Budget Part
            totalAmountButton.addEventListener("click", () => {
                tempAmount = totalAmount.value;
                //empty or negative input
                if (tempAmount === "" || tempAmount < 0) {
                    errorMessage.classList.remove("hide");
                } else {
                    errorMessage.classList.add("hide");
                    confirmMessage.textContent = `Are you sure you want to withdraw ${tempAmount}?`;
                    confirmationModal.style.display = "block";
                }
            });

            confirmWithdrawButton.addEventListener("click", () => {

                // Create a form and submit it

                const form = document.createElement("form");
                form.method = "POST";
                form.action = "/movie/owner/transaction/withdraw"; // Your servlet handling withdrawal
                const amountInput = document.createElement("input");
                amountInput.type = "hidden";
                amountInput.name = "amount";
                amountInput.value = totalAmount.value;
                form.appendChild(amountInput);
                document.body.appendChild(form);
                form.submit();

                confirmationModal.style.display = "none";
            });

            cancelWithdrawButton.addEventListener("click", () => {
                // Hide confirmation modal
                confirmationModal.style.display = "none";
            });

//Function To Disable Edit and Delete Button
            const disableButtons = (bool) => {
                let editButtons = document.getElementsByClassName("edit");
                Array.from(editButtons).forEach((element) => {
                    element.disabled = bool;
                });
            };

//Function To Modify List Elements
            const modifyElement = (element, edit = false) => {
                let parentDiv = element.parentElement;
                let currentBalance = balanceValue.innerText;
                let currentExpense = expenditureValue.innerText;
                let parentAmount = parentDiv.querySelector(".amount").innerText;
                if (edit) {
                    let parentText = parentDiv.querySelector(".product").innerText;
                    productTitle.value = parentText;
                    userAmount.value = parentAmount;
                    disableButtons(true);
                }
                balanceValue.innerText = parseInt(currentBalance) + parseInt(parentAmount);
                expenditureValue.innerText =
                        parseInt(currentExpense) - parseInt(parentAmount);
                parentDiv.remove();
            };

            //Function To Create List
            const listCreator = (expenseName, expenseValue) => {
                let sublistContent = document.createElement("div");
                sublistContent.classList.add("sublist-content", "flex-space");
                list.appendChild(sublistContent);
                sublistContent.innerHTML = `<p class="product">${expenseName}</p><p class="amount">${expenseValue}</p>`;
                let editButton = document.createElement("button");
                editButton.classList.add("fa-solid", "fa-pen-to-square", "edit");
                editButton.style.fontSize = "1.2em";
                editButton.addEventListener("click", () => {
                    modifyElement(editButton, true);
                });
                let deleteButton = document.createElement("button");
                deleteButton.classList.add("fa-solid", "fa-trash-can", "delete");
                deleteButton.style.fontSize = "1.2em";
                deleteButton.addEventListener("click", () => {
                    modifyElement(deleteButton);
                });
                sublistContent.appendChild(editButton);
                sublistContent.appendChild(deleteButton);
                document.getElementById("list").appendChild(sublistContent);
            };

            //Function To Add Expenses
            checkAmountButton.addEventListener("click", () => {
                //empty checks
                if (!userAmount.value || !productTitle.value) {
                    productTitleError.classList.remove("hide");
                    return false;
                }
                //Enable buttons
                disableButtons(false);
                //Expense
                let expenditure = parseInt(userAmount.value);
                //Total expense (existing + new)
                let sum = parseInt(expenditureValue.innerText) + expenditure;
                expenditureValue.innerText = sum;
                //Total balance(budget - total expense)
                const totalBalance = tempAmount - sum;
                balanceValue.innerText = totalBalance;
                //Create list
                listCreator(productTitle.value, userAmount.value);
                //Empty inputs
                productTitle.value = "";
                userAmount.value = "";
            });
        </script>
    </body>
</html>
