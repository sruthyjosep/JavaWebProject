<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <script>
            $(document).ready(function() {
                $(function() {
                    $("#stockid").autocomplete({
                        source: function(request, response) {
                            $.ajax({
                                url: "SearchController",
                                type: "GET",
                                data: {
                                    term: request.term
                                },
                                dataType: "json",
                                success: function(data) {
                                	console.log(data);
                                    response(data);
                                }
                            });
                        }
                    });
                });
            });
        </script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.container
{
margin-top : -5px;
background-color:#f2f2f2;
height : 20rem;

}
.header
{
background-color:white;
width:100%;
height:8rem;
}
.body
{
background-color:#7a7a7a;
width:100%;
height:2rem;
}
.footer
{
background-color:white;
width:100%;
height:3rem;
}




.span_2_of_2 {
	width: 100%;
}

.span_1_of_2 {
	width: 49.2%;
}


/*  GO FULL WIDTH AT LESS THAN 480 PIXELS */

@media only screen and (max-width: 480px) {
	.span_2_of_2 {
		width: 98%; 
	}
	.span_1_of_2 {
		width: 98%; 
	}
	
}


	
	.btn-calc
	{
	font-family: 'Fjalla One',sans-serif;
	display: inline-block;
    vertical-align: middle;
    margin: 0;
    padding: .75rem 1rem;
    -webkit-appearance: none;
    border: 1px solid transparent;
    border-radius: 0;
    transition: background-color .25s ease-out,color .25s ease-out;
    font-size: .9375rem;
    line-height: 1;
    text-align: center;
    cursor: pointer;
    background-color: black;
    color: #fff;
		margin-bottom : 1rem;
	}
	
	
	.section {
	clear: both;
	padding: 0px;
	margin: 0px;
}

/*  GROUPING  ============================================================================= */


.group:before,
.group:after {
    content:"";
    display:table;
}
.group:after {
    clear:both;
}
.group {
    zoom:1; /* For IE 6/7 (trigger hasLayout) */
}

/*  GRID COLUMN SETUP   ==================================================================== */

.col {
	display: block;
	float:left;
	margin: 1% 0 1% 1.6%;
}

.col:first-child { margin-left: 0; } /* all browsers except IE6 and lower */


/*  REMOVE MARGINS AS ALL GO FULL WIDTH AT 480 PIXELS */

@media only screen and (max-width: 480px) {
	.col { 
		margin: 1% 0 1% 0%;
	}
}
	
		
		
</style>
<title>Euromarque Information Card Generator</title>
</head>
<body>
<div class="body">
<div class="header">
<center>
<img align="middle" style="margin-top:1rem;width:25%" src="${pageContext.request.contextPath}/images/header_euromarque.jpg">
</center>
</div>
<img align="middle" style="width:100%" src="${pageContext.request.contextPath}/images/banner2.png">
<div class="container">
<form action ="/InformationCardDynamic/EuromarqueInformationCardServlet" method ="post">


<%! String s1 = ""; String s2=""; %>
<% s1  = (String) session.getAttribute("error");
   s2  = (String) session.getAttribute("success");%>
<% if(s1!=null){ %>
<p align="center" style="color:red;margin-top:0rem;padding-top:7rem;font-weight:bold"><%= session.getAttribute("error") %> </p>
<%} if(s2!=null){ %>
<p align="center" style="color:green;margin-top:0rem;padding-top:7rem;font-weight:bold"><%= session.getAttribute("success") %> </p>
<% } session.invalidate(); %>
<div class="section group">
<% if(s1!=null || s2!=null){ %>
<div class="col span_1_of_2">
<input type = "text" style="margin-left:50rem; margin-top:0rem; height:2.2rem; width:9rem; text-align:center" placeholder="Stock ID *" id="stockid" name = "stockid" size = "10" />
</div>
<div class="col span_1_of_2">
<input class="btn-calc" style="margin-top:rem;" type = "submit" value = "Generate Template" />
</div>
<%} else { %>
<div class="col span_1_of_2">
<input type = "text" style="margin-left:50rem; margin-top:5rem; height:2.2rem; width:9rem; text-align:center" placeholder="Stock ID *" id="stockid" name = "stockid" size = "10" />
</div>
<div class="col span_1_of_2">
<input class="btn-calc" style="margin-top:5rem;" type = "submit" value = "Generate Information Card" />
</div>
<%} %>
</div>


</form>
</div>
<div class="footer">
<br>
<center style="font-size:0.75rem;color:black;font-family:Helvetica">
Euromarque © 2017
</center>
</div>
</div>
</body>
</html>