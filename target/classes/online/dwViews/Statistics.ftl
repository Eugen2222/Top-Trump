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

    <body onload=getGameStats()> <!-- Call the initalize method when the page loads -->

		<div class="container" >
			<div class="row justify-content-center mt-5">
				<div class="col-auto mt-5 ">
					<div class="cardStatistic mt-5 mb-4 " style="width: 30rem;">
						<div class="card-header" class="cardInfo">
							<h3 class="my-0 py-0 px-0 mb-1 font-weight-normal text-left">Statistic</h3>
						</div>
						<div  class="card-body">
							<ul id = "gameStats" class=" list-unstyled text-left mt-0 mb-0 ">
								<h5 class= "stats" >Number of Games:</h5>
								<h5 class= "stats" >Number of Games:</h5>
								<h5 class= "stats" >Number of Games:</h5>
								<h5 class= "stats" >Number of Games:</h5>
								<h5 class= "stats" >Number of Games:</h5>
								<h5 class= "stats"></h5>
								<h5 class= "stats"></h5>
							</ul>
						</div>
					</div>
						

				</div>
			</div>
			<div class="row justify-content-center mt-0">
				<div class="col-auto mt-3 "style="width:15em;">
						<div>
							<a ><button class="btn btn-outline-dark btn-block text-decoration-none selectbutton"  class="button-size" id = "newGamePanel" onclick = "enableSelectPlayerNumPanel()">New Game</button></a>
						</div>
						<div class="list-group mb-0" id = "playerNumPanel" style="display: none">
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect2()>2 Players</button>
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect3()>3 Players</button>
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect4()>4 Players</button>
							<button type="button" class="btn btn-outline-dark btn-block my-1 py-1 selectbutton" class="button-size" onclick= userSelect5()>5 Players</button>
						</div>
					</div>
				</div>	
			</div>
			</div>
		</div>
			<footer class="pt-4 my-md-5 pt-md-5 border-top"></footer>	
	</body>




<script type="text/javascript">


	function userSelect2(){
		userSelectNum(2);
	}

	function userSelect3(){
		userSelectNum(3);
	}

	function userSelect4(){
		userSelectNum(4);
	}

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


	function userSelectNum(num) {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userRequestGameInitialised?num="+num); 
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


	function setUpElements(s, targetID, targetCl){
				for(j=0 ; j<s.length ; j++){
					document.getElementById(targetID).getElementsByClassName(targetCl)[j].innerHTML=s[j];
				}
			}
	function javaArrayDecode(input){
					var strr = new Array();
					strr = input.split('|?|');
					return strr;
			}
			

	function getGameStats(){
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateGameStats"); // Request type and URL+parameters
				if (!xhr) {
  					alert("CORS not supported");
				}
				xhr.onload = function(e) {
 					let responseText = xhr.response; // the text of the response
					var strr = new Array();
					strr = javaArrayDecode(responseText);
					setUpElements(strr,"gameStats", "stats");
					document.getElementById("gameStats").style.display = 'block';
				}
				
				xhr.send();
			}
</script>


</html>