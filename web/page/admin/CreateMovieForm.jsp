<%-- 
    Document   : CreateMovieForm
    Created on : Jun 6, 2024, 10:30:14 AM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ page isELIgnored="false" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />

    </head>
    <body>
        <h1>Hello World!</h1>


        <div class="container">
            <form action="${pageContext.request.contextPath}/CreateMovieServlet" method="post" enctype="multipart/form-data">

            <label for="title" class="form-label">Tiêu đề</label>

            <input
                type="text"
                class="form-control"
                id="title"
                name="title"
                placeholder="Nhập tiêu đề"
                />

            <label for="synopsis" class="form-label">Mô tả phim</label>
            <textarea
                class="form-control"
                id="synopsis"
                name="synopsis"
                rows="3"
                placeholder="Nhập mô tả phim"
                ></textarea>

            <!-- datepicker -->
            <label for="datePublished" class="form-label"
                   >Ngày phát hành</label
            >
            <input
                type="date"
                class="form-control"
                id="datePublished"
                name="datePublished"
                placeholder="Nhập ngày phát hành"
                />

            <label for="file" class="form-label">Hình ảnh</label>
            <input  id = "file"  name="file" type="file" class="form-control" />

            <!-- <label for="exampleFormControlInput1" class="form-label">Country</label> -->
            <label for="country" class="form-label">Quốc gia</label>
            <input
                class="form-control"
                list="datalistOptions"
                id="country"
                placeholder="Nhập quốc gia"
                name="country"
                />
            
            <label for="genres" class="form-label">Thể loại</label><br />

            <div id="formElements">
                <!-- Dynamic form elements will be added here -->
            </div>

            <button type="button" class="btn btn-primary" onclick="addTextField()">
                Thêm thể loại</button
            ><br />
            <!-- <label for="exampleFormControlInput1" class="form-label">Year</label> -->
            <label for="exampleFormControlInput1" class="form-label">Thời lượng</label>
            <div class="input-group">
                <input
                    type="number"
                    class="form-control"
                    id="basic-url"
                    name="duration"
                    aria-describedby="basic-addon3 basic-addon4"
                    placeholder="Nhập thời lượng phim "
                    />
                <span class="input-group-text" id="basic-addon3">'</span>
            </div>

            <label for="exampleFormControlInput1" class="form-label">
                Trailer Link</label
            >
            <input
                type="url"
                class="form-control"
                id="urlInput"
                name="urlInput"
                placeholder="Nhập link youtube..."
                />
            <label for="exampleFormControlInput1" class="form-label">Trạng thái</label>
            <select
                class="form-select form-select-sm"
                aria-label="Small select example"
                name="status"
                >
                <option value="Showing" selected>Đang chiếu</option>
                <option value="Coming">Sắp chiếu</option>
            </select>
            <br/>
            <button type="submit" class="btn btn-danger" onclick="">
                Tạo phim</button
            >
            </form>
        </div>
        <!--Popper-->
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"
        ></script>
        <!--Bootstrap js-->
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"
        ></script>
        <!--jquery-->
        <script
            src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha384-ZvpUoO/+P0o2QE0Wea1Ygq6hEENenOPJo7FfHRVN9cAo50LjMOhpS7CbRBVxnlNv"
            crossorigin="anonymous"
        ></script>


        <!--JS-->
        <script>
                let counter = 0;

                function addTextField() {
                    counter++;
                    let curr = counter;
                    // Create the main div container
                    const div = document.createElement("div");
                    div.classList.add("mb-3");
                    div.id = "genre" + counter;

                    // Create the inner input-group div
                    const inputGroupDiv = document.createElement("div");
                    inputGroupDiv.classList.add("input-group", "mb-3");

                    // Create the input field
                    const input = document.createElement("input");
                    input.type = "text";
                    input.className = "form-control";
                    input.placeholder = "Nhập tên thể loại";
                    input.setAttribute("aria-label", "Recipient's username");
                    input.setAttribute("aria-describedby", "button-addon"+counter);
                    input.setAttribute("list", "datalistOptions2");
                    input.id = "exampleDataList";
                    input.name = "genres";

                    // Create the datalist element
                    const dataList = document.createElement("datalist");
                    dataList.id = "datalistOptions2";

                    // Add options to the datalist
                    const options = ["Afghanistan", "Åland Islands", "Albania"];
                    options.forEach(optionValue => {
                        const option = document.createElement("option");
                        option.value = optionValue;
                        dataList.appendChild(option);
                    });

                    // Create the button element
                    const button = document.createElement("button");
                    button.type = "button";
                    button.className = "btn btn-outline-secondary";
                    button.id = 'button-addon'+counter;
                    button.setAttribute("aria-label", "Close");
                    button.onclick = function () {
                        removeInputGroup(curr);
                    };
                    button.textContent = "x";

                    // Append elements to the input-group div
                    inputGroupDiv.appendChild(input);
                    inputGroupDiv.appendChild(dataList);
                    inputGroupDiv.appendChild(button);

                    // Append the input-group div to the main div container
                    div.appendChild(inputGroupDiv);

                    // Append the main div container to the formElements div
                    document.getElementById("formElements").appendChild(div);
                }
                function removeInputGroup(num) {
                    var inputGroup = document.getElementById(`genre` + num);
                    inputGroup.remove();
                }
        </script>

    </body>
</html>
