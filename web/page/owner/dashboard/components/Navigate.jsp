<%@page import="util.RouterURL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sidebar Navigation</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Arial', sans-serif;
            }
            .sidebar {
                height: 100vh;
                background-color: #2E3A46;
                color: #ffffff;
                padding-top: 20px;
            }
            .sidebar .nav-link {
                color: #ffffff;
                width : 190px ;
            }
            .sidebar .nav-link.active {
                color: #FF6B6B;
            }
            .sidebar .nav-link:hover {
                background-color: #3E4E5E;
            }
            .sidebar .nav-item .badge {
                font-size: 0.7rem;
            }
            .sidebar .navbar-brand {
                color: #ffffff;
                font-size: 1.5rem;
                font-weight: bold;
                display: flex;
                align-items: center;
            }
            .sidebar .navbar-brand img {
                width: 30px;
                height: 30px;
                margin-right: 10px;
            }
            .nav-section-title {
                padding-left: 15px;
                margin-top: 10px;
                margin-bottom: 10px;
                font-size: 1rem;
                color: #8C9BA5;
            }
            .dropdown-menu {
                background-color: #77899b !important;
                border: none;
            }
            .dropdown-menu .dropdown-item {
                color: #ffffff;
            }
            .dropdown-menu .dropdown-item:hover {
                background-color: #3E4E5E;
            }
            .dropdown-toggle::after {
                display: none;
            }
            .dropdown-toggle:active, .dropdown-toggle:focus {
                box-shadow: none;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <nav class="col-md-3 col-lg-2 d-md-block sidebar">
                    <div class="navbar-brand">
                        <img src="https://res.cloudinary.com/dsvllb1am/image/upload/v1718269790/sgvvasrlc3tisefkq92j.png" alt="Logo"> adminty
                    </div>
                    <div class="nav flex-column" id="nav-container">
                        <!-- Dynamic navigation will be rendered here -->
                    </div>
                </nav>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
                    <!-- Main content goes here -->
                </main>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <!-- Custom JS for dynamic navigation -->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                
                const navItems = [
                    {
                        title: "Dashboard",
                        url: "<%= RouterURL.OWNER_DASHBOARD_PAGE%>",

                    },
                    {
                        title: "Movies ",

                        url: "<%= RouterURL.OWNER_MOVIES_STATISTIC%>",

                    },
                    {
                        title: "Cinemas ",

                        url: "<%= RouterURL.OWNER_CINEMAS_STATISTIC%>"
                    },
                    {
                        title: "City",
                        url: "/movie/OwnerDashboardCityServlet"
                    },
                    {
                        title: "Widget",
                        icon: "fas fa-cubes",
                        url: "/movie/owner/manage/finance",
                        badge: "Renevue",
                        badgeClass: "badge-danger"
                    },
                    {
                        title: "Create cinema chain",
                        url: "/movie/owner/createCinemaChain",
                    },
                    {
                        title: "Create movie slot",
                        url: "/movie/CreateMovieSlotFormInfoServlet",
                    },
                    {
                        title: "Create movie request",
                        url: "/movie/page/owner/createMovie/CreateMovieRequest.jsp",
                    },
                    {
                        title: "View movie request",
                        url: "/movie/owner/viewmovierequests",
                    }
                ];

                function renderNavItems(items) {
                    const navContainer = document.getElementById('nav-container');
                    let navHtml = '<div class="nav-section-title">Navigation</div>';
                    items.forEach(item => {
                        if (item.children) {
                            navHtml += '<div class="nav-item dropdown">' +
                                    '<a class="nav-link dropdown-toggle" href="#" id="' + item.title.replace(/\s+/g, '') + 'Dropdown" role="button">' +
                                    '<i class="' + item.icon + '"></i> ' + item.title +
                                    '</a>' +
                                    '<div class="dropdown-menu" aria-labelledby="' + item.title.replace(/\s+/g, '') + 'Dropdown">' +
                                    item.children.map(child =>
                                        '<a class="dropdown-item" href="' + child.url + '">' +
                                                child.title +
                                                (child.badge ? '<span class="badge ' + child.badgeClass + '">' + child.badge + '</span>' : '') +
                                                '</a>'
                                    ).join('') +
                                    '</div>' +
                                    '</div>';
                        } else {
                            navHtml += '<a class="nav-link" href="' + item.url + '">' +
                                    '<i class="' + item.icon + '"></i> ' + item.title +
                                    (item.badge ? '<span class="badge ' + item.badgeClass + '">' + item.badge + '</span>' : '') +
                                    '</a>';
                        }
                    });
                    navContainer.innerHTML = navHtml;

                    // Add event listeners for dropdown toggles
                    const dropdownToggles = navContainer.querySelectorAll('.nav-link.dropdown-toggle');
                    dropdownToggles.forEach(toggle => {
                        toggle.addEventListener('click', function (event) {
                            event.preventDefault();
                            const dropdownMenu = this.nextElementSibling;
                            dropdownMenu.classList.toggle('show');
                        });
                    });

                    document.addEventListener('click', function (event) {
                        if (!event.target.closest('.nav-item.dropdown')) {
                            const dropdownMenus = navContainer.querySelectorAll('.dropdown-menu');
                            dropdownMenus.forEach(menu => {
                                menu.classList.remove('show');
                            });
                        }
                    });
                }

                renderNavItems(navItems);
            });
        </script>

    </body>
</html>
