<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Excess Food Management System</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
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
    <body class="cyan">
        <div class="container">
            <div class="drop-down-list card">
                <div class="center">
                    <h5>Service Register Form</h5>
                </div>
                <div class="divider"></div>
                <form action="<%=request.getContextPath()%>/AdminServlet" method="post">
                <div class="input-field">
                <label>First Name</label>
                <input type="text" name="first_name" required/>
                </div>
                <div class="input-field">
                <label>Last Name</label>
                <input type="text" name="last_name" required/>
                </div>
                <div class="input-field">
                <label>Email</label>
                <input type="email" name="email" required/>
                </div>
                <div class="input-field">
                <label>User Name</label>
                <input type="text" name="username" required/>
                </div>
                <div class="input-field">
                <label>Password</label>
                <input type="password" name="password" required/>
                </div>
                <div class="input-field">
                <label>Address</label>
                <input type="text" name="address" required/>
                </div>
                <div class="input-field">
                <label>Contact</label>
                <input type="number" name="contact" required/>
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
                    <div class="input-field">
                <label>Description</label>
                <input type="text" name="description" required/>
                </div>
                    <div class="center">
                        <button class="btn">Submit</button>
                    </div>
                    <h6 style="color:red">${errorMessage}</h6>
                </form>
            </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $.ajax({
                    url: "AdminServlet",
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
                        url: "AdminServlet",
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
                            $('#locality').append('<option>Locality Unavailable</option>');
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
                        url: "AdminServlet",
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
    </body>
</html>