<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <div>
            <h1 class="titlee">Phim đang chiếu</h1>
            <jsp:include page="/page/movie/MovieListComponents.jsp">
                <jsp:param name="status" value="Showing"/>
            </jsp:include>
        </div>

        <div>
            <h1 class="titlee">Phim sắp chiếu</h1>
            <jsp:include page="/page/movie/MovieListComponents.jsp">
                <jsp:param name="status" value="Coming"/>
            </jsp:include>
        </div>
    </body>
</html>