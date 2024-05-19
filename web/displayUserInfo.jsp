
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="displayUserInfoCss.css"/>
        
         <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    
    
    
    <body>
        
        
        <div class="container">
         
            <div class="avatar">
                <img src="${user.avatarLink}" alt="Avatar" id="avatarImage">
                <input type="file" id="avatarInput" accept="image/*">
            </div>
            
                
                <h1>Update information </h1>
            <form action="${pageContext.request.contextPath}/UpdateUserInfo" method="post" enctype="multipart/form-data" >
                <div class="hide">
                    <label for="userId">userid</label>
                    <input type="text" id="username" name="userId" value="${user.userID}"  >
                </div>
                
                <div class="form-group">
                    <i class="fas fa-user  icon-blue"></i>
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${user.username}">
                </div>

                <div class="form-group">
                                    <i class="fas fa-envelope  icon-blue"></i>

                    <label for="email">Email:</label>
                    <input  id="email" name="email"  value="${user.email}" >
                </div>
                <div class="form-group">
                                    <i class="fas fa-birthday-cake  icon-blue"></i>

                    <label for="fullname">Full Name:</label>
                    <input type="text" id="fullname" name="fullname"  value="${user.fullname}">
                </div>
                <div class="form-group">
                                    <i class="fas fa-birthday-cake  icon-blue"></i>

                    <label for="birthday">Birthday:</label>
                    <input  placeholder="yyyy/MM/dd" id="birthday" name="birthday"  value="${user.birthday}">
                </div>
                <div class="form-group">
                                    <i class="fas fa-map-marker-alt  icon-blue"></i>

                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address"  value="${user.address}">
                </div>
                <div class="form-group">
                                    <i class="fas fa-map-marked-alt  icon-blue"></i>

                    <label for="province">Province:</label>
                    <input type="text" id="province" name="province"  value="${user.province}">
                </div>
                <div class="form-group">
                                    <i class="fas fa-city  icon-blue"></i>

                    <label for="district">District:</label>
                    <input type="text" id="district" name="district"  value="${user.district} ">
                </div>
                <div class="form-group">
                                    <i class="fas fa-landmark  icon-blue"></i>

                    <label for="commune">Commune:</label>
                    <input type="text" id="commune" name="commune" value="${user.commune} " >
                </div>
                <div class="form-group">


                    <label for="avatar">chon anh dai dien :</label>
                    <input type="text" id="avatar" name="avatarUrl" ">


                </div>





                <button type="submit update-btn">Update now</button>

            </form>
                
                
            <form action="changPassword.jsp" method="post">
                <button type="submit" class="logout-button change-btn ">Change Password</button>
            </form>

            <form action="logoutServlet" method="post">
                <button type="submit " class="logout-button  logOut-btn">Log out</button>
            </form>

        </div>
        <script src="script.js"></script>
    </body>
    
    
    
</html>
