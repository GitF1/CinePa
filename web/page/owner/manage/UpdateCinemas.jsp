<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Cinema</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f7f7;
                margin: 0;
                padding: 0;
            }

            .form-container {
                background-color: white;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
            }

            h2 {
                background-color: #4e2c72;
                color: white;
                padding: 10px;
                margin: -20px -20px 20px;
                text-align: center;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
            }

            .form-group label {
                font-weight: bold;
                margin-bottom: 5px;
                display: block;
            }

            .form-group input, .form-group select {
                width: 100%;
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            .form-group input[type="file"] {
                padding: 5px;
            }

            .btn-primary {
                background-color: #4e2c72;
                border-color: #4e2c72;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                display: inline-block;
                text-align: center;
                font-size: 16px;
                margin-top: 20px;
            }

            .btn-primary:hover {
                background-color: #3a1d54;
            }

            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                display: inline-block;
                text-align: center;
                font-size: 16px;
                margin-top: 20px;
                margin-left: 10px;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
            }
        </style>
    </head>
    <body>
        <div class="container form-container">
            <h2>Update Cinema</h2>
            <form action="${pageContext.request.contextPath}/owner/updateCinema" method="post" enctype="multipart/form-data">
                <input type="hidden" name="cinemaID" value="${cinema.cinemaID}">
                <input type="hidden" name="cinemaChainID" value="${cinema.cinemaChainID}">
                <div class="form-group">
                    <label for="avatar">Avatar</label>
                    <input type="file" class="form-control-file" id="avatar" name="avatar" accept="image/*">
                </div>
                <div class="form-group">
                    <label for="province">Province</label>
                    <select class="form-control" id="province" name="province" required>
                        <option value="">Select Province</option>
                    </select>
                    <input type="hidden" id="provinceName" name="provinceName" value="${cinema.province}">
                </div>
                <div class="form-group">
                    <label for="district">District</label>
                    <select class="form-control" id="district" name="district" required>
                        <option value="">Select District</option>
                    </select>
                    <input type="hidden" id="districtName" name="districtName" value="${cinema.district}">
                </div>
                <div class="form-group">
                    <label for="commune">Commune</label>
                    <select class="form-control" id="commune" name="commune" required>
                        <option value="">Select Commune</option>
                    </select>
                    <input type="hidden" id="communeName" name="communeName" value="${cinema.commune}">
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" class="form-control" id="address" name="address" value="${cinema.address}" required>
                </div>
                <button type="submit" class="btn btn-primary">Update Cinema</button>
                <a href="javascript:history.go(-1);" class="btn btn-secondary">Cancel</a>
            </form>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                // Fetch provinces and pre-select if available
                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                    if (data_tinh.error == 0) {
                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                            var selected = val_tinh.full_name === $("#provinceName").val() ? "selected" : "";
                            $("#province").append('<option data-id="' + val_tinh.id + '" value="' + val_tinh.full_name + '" ' + selected + '>' + val_tinh.full_name + '</option>');
                        });
                    }
                });

                // Fetch districts and pre-select if available
                $("#province").change(function () {
                    var idtinh = $('#province option:selected').data('id');
                    var selectedProvince = $("#province option:selected").val();
                    $("#provinceName").val(selectedProvince); // Update hidden field with selected name

                    $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                        if (data_quan.error == 0) {
                            $("#district").html('<option value="">Select District</option>');
                            $("#commune").html('<option value="">Select Commune</option>');
                            $.each(data_quan.data, function (key_quan, val_quan) {
                                var selected = val_quan.full_name === $("#districtName").val() ? "selected" : "";
                                $("#district").append('<option data-id="' + val_quan.id + '" value="' + val_quan.full_name + '" ' + selected + '>' + val_quan.full_name + '</option>');
                            });
                        }
                    });
                }).change(); // Trigger change to fetch districts on load

                // Fetch communes and pre-select if available
                $("#district").change(function () {
                    var idquan = $('#district option:selected').data('id');
                    var selectedDistrict = $("#district option:selected").val();
                    $("#districtName").val(selectedDistrict); // Update hidden field with selected name

                    $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                        if (data_phuong.error == 0) {
                            $("#commune").html('<option value="">Select Commune</option>');
                            $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                var selected = val_phuong.full_name === $("#communeName").val() ? "selected" : "";
                                $("#commune").append('<option value="' + val_phuong.full_name + '" ' + selected + '>' + val_phuong.full_name + '</option>');
                            });
                        }
                    });
                }).change(); // Trigger change to fetch communes on load

                // Update communeName when commune changes
                $("#commune").change(function () {
                    var selectedCommune = $("#commune option:selected").val();
                    $("#communeName").val(selectedCommune);
                });

                // Handle file input change event
                $('#avatar').change(function () {
                    const fileInput = $(this);
                    if (fileInput[0].files.length > 0) {
                        const fileName = fileInput[0].files[0].name;
                        alert('Selected file: ' + fileName);
                    }
                });
            });
        </script>
    </body>
</html>
