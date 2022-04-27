<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Excess Food Management System</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <link rel="stylesheet" href="css/styles.css" type="text/css" media="all">
        <style type="text/css">
            body{
                background-size: cover;
            }
            .drop-down-list{
                margin: 150px auto;
                width: 50%;
                padding: 30px;
            }
        </style>
</head>
<body>

<div class="main">
	<p class="sign" align="center">User Order Creation</p>
	<div class="container">

			<form class="form1" action="<%=request.getContextPath()%>/UserCreateOrderServlet" method="post">
				<div class="input-field">
				<label>Order Details</label>
				<input type="text" name="order_details" required>
				</div>

				<div class="input-field">
					<select id="region">
						<option>Select Region</option>
					</select>
				</div>
				<div class="input-field">
					<select id="locality">
						<option>Select Locality</option>
					</select>
				</div>
				<div class="center">
					<button class="btn">Submit</button>
				</div>
				<h6 style="color: red">${errorMessage}</h6>

			</form>
		</div>
</div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $.ajax({
                    url: "UserCreateOrderServlet",
                    method: "GET",
                    data: {operation: 'region'},
                    success: function (data, textStatus, jqXHR) {
                        console.log(data);
                        //alert(data);
                        let obj = $.parseJSON(data);
                        $.each(obj, function (key, RegionModel) {
                            $('#region').append('<option value="' + RegionModel.region_id + '">' + RegionModel.region_name + '</option>')
                        });
                        $('select').formSelect();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $('#region').append('<option>Region Unavailable</option>');
                    },
                    cache: false
                });


                $('#region').change(function () {
                    $('#locality').find('option').remove();
                   $('#locality').append('<option>Select Locality</option>'); 

                    let rid = $('#region').val();
                    let data = {
                        operation: "locality",
                        id: rid
                    };

                    $.ajax({
                        url: "UserCreateOrderServlet",
                        method: "GET",
                        data: data,
                        success: function (data, textStatus, jqXHR) {
                            console.log(data);
                            let obj = $.parseJSON(data);
                            $.each(obj, function (key, LocalityModel) {
                                $('#locality').append('<option value="' + LocalityModel.id + '">' + LocalityModel.name + '</option>')
                            });
                            $('select').formSelect();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            $('#locality').append('<option>State Unavailable</option>');
                        },
                        cache: false
                    });
                }); 
                
                $('#locality').change(function () {
                    let rid = $('#locality').val();  
                    let data = {
                        operation: "GetId",
                        id: rid
                    };

                    $.ajax({
                        url: "UserCreateOrderServlet",
                        method: "GET",
                        data: data,
                        success: function (data, textStatus, jqXHR) {
                            console.log(data);
                            let obj = $.parseJSON(data);
                            $.each(obj, function (key, LocalityModel) {
                                $('#locality').append('<option value="' + LocalityModel.id + '">' + LocalityModel.name + '</option>')
                            });
                            $('select').formSelect();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            $('#locality').append('<option>State Unavailable</option>');
                        },
                        cache: false
                    });
                }); 
                
            });
        </script>
		<div align="center">
			<h6 style="color: red">${errorMessage}</h6>
		</div> 
</div>

</body>
</html>