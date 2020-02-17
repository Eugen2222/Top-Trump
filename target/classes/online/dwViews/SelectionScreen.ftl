<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="http://localhost:7777/assets/GameScreen.css">


	</head>

    <body> <!-- Call the initalize method when the page loads -->
    	
		<div class="pricing-header px-1 pt-5 md-1 pb-5 mx-auto text-center">
				<h1 class="display-5"></h1>
			</div>
			<div class="pricing-header px-1 pt-5 md-1 pb-2 mx-auto text-center">
				<h1 class="display-2">Top Trumps Game</h1>
			</div>
			<div class="container" >
				<div class="row justify-content-center mt-5">
					<div class="col-auto mt-5"style="width:15em;">
						<div>
							<a ><button class="btn btn-outline-dark btn-block text-decoration-none selectbutton"  class="button-size"id = "newGamePanel" onclick = "enableSelectPlayerNumPanel()">New Game</button></a>
						</div>
						<#--  button for choosing the number of players  -->
						<div class="list-group mb-0 " id = "playerNumPanel" style="display: none">
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect2()>2 Players</button>
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect3()>3 Players</button>
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect4()>4 Players</button>
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect5()>5 Players</button>
						</div>
					</div>
				</div>

				<div class="row justify-content-center mt-0">
					<div class="col-auto mt-3"style="width:15em;">
					<#--  button for game statistics  -->
						<div>
							<button class="btn btn-outline-dark btn-block selectbutton" class="button-size" onclick = userPressStats()>Game Statistics</button>
						</div>
					</div>
				</div>
				
			</div>
			<footer class="pt-4 my-md-5 pt-md-5 border-top"></footer>	
	</body>




<script type="text/javascript">
    
	// 2 players
	function userSelect2(){
		userSelectNum(2);
	}

    // 3 players
	function userSelect3(){
		userSelectNum(3);
	}

    // 4 players
	function userSelect4(){
		userSelectNum(4);
	}
    
	// 5 players
	function userSelect5(){
		userSelectNum(5);
	}

	function createCORSRequest(method, url) {
  				var xhr = new XMLHttpRequest();
  				if ("withCredentials" in xhr) {

    				// Check if the XMLHttpRequest object has a "withCredentials" property.
    				// "withCredentials" only exists on XMLHTTPRequest2 objects.
    				xhr.open(method, url, true);

  				} else if (typeof XDomainRequest != "undefined") {

    				// Otherwise, check if XDomainRequest.
    				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    				xhr = new XDomainRequest();
    				xhr.open(method, url);

 				 } else {

    				// Otherwise, CORS is not supported by the browser.
    				xhr = null;

  				 }
  				 return xhr;
			}

   // send the players number to the web
	function userSelectNum(num) {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userRequestGameInitialise?num="+num); 
				if (!xhr) {
					alert("CORS not supported");
				}
				xhr.onload = function(e) {
					window.location.href = "http://localhost:7777/toptrumps/game";
				}
				xhr.send();	

			}


	function enableSelectPlayerNumPanel() {
				document.getElementById("newGamePanel").style.display = 'none';
				document.getElementById("playerNumPanel").style.display = 'block';
			}


	function userPressStats() {
		window.location.href = "http://localhost:7777/toptrumps/stats";
	}

	

</script>


</html>