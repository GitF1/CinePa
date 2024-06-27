<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Cinema</title>
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
            width: 100%;
            display: block;
            text-align: center;
            font-size: 16px;
            margin-top: 20px;
        }

        .btn-primary:hover {
            background-color: #3a1d54;
        }
    </style>
</head>
<body>
    <div class="container form-container">
        <h2>Create Cinema</h2>
        <form action="${pageContext.request.contextPath}/owner/createCinema" method="post" enctype="multipart/form-data">
            <input type="hidden" name="cinemaChainID" value="${param.cinemaChainID}">
            <div class="form-group">
                <label for="avatar">Avatar</label>
                <input type="file" class="form-control-file" id="avatar" name="avatar" required>
            </div>
            <div class="form-group">
                <label for="province">Province</label>
                <select class="form-control" id="province" name="province" required>
                    <option value="">Select Province</option>
                </select>
                <input type="hidden" id="provinceName" name="provinceName">
            </div>
            <div class="form-group">
                <label for="district">District</label>
                <select class="form-control" id="district" name="district" required>
                    <option value="">Select District</option>
                </select>
                <input type="hidden" id="districtName" name="districtName">
            </div>
            <div class="form-group">
                <label for="commune">Commune</label>
                <select class="form-control" id="commune" name="commune" required>
                    <option value="">Select Commune</option>
                </select>
                <input type="hidden" id="communeName" name="communeName">
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" class="form-control" id="address" name="address" required>
            </div>
            <button type="submit" class="btn btn-primary">Create Cinema</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            // Fetch provinces
            $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                if (data_tinh.error == 0) {
                    $.each(data_tinh.data, function (key_tinh, val_tinh) {
                        $("#province").append('<option data-id="' + val_tinh.id + '" value="' + val_tinh.full_name + '">' + val_tinh.full_name + '</option>');
                    });
                }
            });

            // On province change
            $("#province").change(function () {
                var idtinh = $('#province option:selected').data('id');
                var selectedProvince = $("#province option:selected").val();
                $("#provinceName").val(selectedProvince); // Update hidden field with selected name

                // Fetch districts
                $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                    if (data_quan.error == 0) {
                        $("#district").html('<option value="">Select District</option>');
                        $("#commune").html('<option value="">Select Commune</option>');
                        $.each(data_quan.data, function (key_quan, val_quan) {
                            $("#district").append('<option data-id="' + val_quan.id + '" value="' + val_quan.full_name + '">' + val_quan.full_name + '</option>');
                        });
                    }
                });
            });

            // On district change
            $("#district").change(function () {
                var idquan = $('#district option:selected').data('id');
                var selectedDistrict = $("#district option:selected").val();
                $("#districtName").val(selectedDistrict); // Update hidden field with selected name

                // Fetch communes
                $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                    if (data_phuong.error == 0) {
                        $("#commune").html('<option value="">Select Commune</option>');
                        $.each(data_phuong.data, function (key_phuong, val_phuong) {
                            $("#commune").append('<option data-id="' + val_phuong.id + '" value="' + val_phuong.full_name + '">' + val_phuong.full_name + '</option>');
                        });
                    }
                });
            });

            // On commune change
            $("#commune").change(function () {
                var selectedCommune = $("#commune option:selected").val();
                $("#communeName").val(selectedCommune); // Update hidden field with selected name
            });
        });
    </script>
</body>
</html>
