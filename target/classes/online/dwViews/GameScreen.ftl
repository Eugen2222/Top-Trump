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

    <body onload=stage()> <!-- Call the initalize method when the page loads -->
    	
    	<div class="container">

			<!-- Add your HTML Here -->
						 		

			<div class="container">

			<div class="pricing-header px-1 pt-5 md-1 pb-md-1 mx-auto text-center">
			<h1 class="display-5" id = gameStatus></h1>
			</div>
			
			<div class="pricing-header px-0 pb-3 pt-md-0 md-1 mx-auto text-center">
			<h1 class="lead">Top Trumps Game</h1>
			</div>
	
				
				<div class="row ">
					<div class="col-2 col-xl-3 mb-5 ml-5 pb-5 pl-5 mr-3 pr-3">
						<div class="card-play  pb-3" id = "gamePanel" style="width: 13rem; display:none;">
							<div class="card-header " class="cardInfo">
								<h5 class="my-0 py-0 px-0 mb-1 font-weight-normal text-left" id="gameRole"></h5>
							</div>
							<div  class="card-body text-center">
                                <#--  show winner button  -->
								<div id="showWinner">
									<button class="btn btn-outline-dark btn-lg mt-3 mb-0 selectbutton" class="button-size" class="selectbutton" onclick="userPressShowWinner()">Show winner</button>
								</div>
                                <#--  next round button  -->
								<div id="nextRound">
									<button class="btn btn-outline-dark btn-lg mt-3 mb-0 selectbutton" class="button-size" class="selectbutton" onclick="userPressNewTurn()">Next round</button>
								</div>
                                <#--  human selection button  -->
								<div id="humanSelectButton">
									<button class="btn btn-outline-dark btn-lg mt-3 mb-0 selectbutton" class="button-size" class="selectbutton"  onclick="userPressSelect()">Select</button>
								</div>
                                <#-- AI player selection button  -->
								<div id="AISelectButton">
									<button class="btn btn-outline-dark btn-lg mt-3 mb-0 selectbutton" class="button-size" class="selectbutton" onclick="userPressSelect()">Show AI's selection</button>
								</div>
								
                                <#--  list of category for human choose <button>  -->
								<div id = selectList class="list-group">
									<li class="list-group-item list-group-item-action list-group-item-light" class="button-size" onclick="userSelectAttr1()">Size</li>
									<li class="list-group-item list-group-item-action list-group-item-light" class="button-size" onclick="userSelectAttr2()">Speed</li>
									<li class="list-group-item list-group-item-action list-group-item-light" class="button-size" onclick="userSelectAttr3()">Range</li>
									<li class="list-group-item list-group-item-action list-group-item-light" class="button-size" onclick="userSelectAttr4()">Firepower</li>
									<li class="list-group-item list-group-item-action list-group-item-light" class="button-size" onclick="userSelectAttr5()">Cargo</li>
								</div>
								
							    <#--  show the result in the end of the game  -->
								<div >
									<ul id = "playerResult" class=" list-unstyled text-left mt-0 mb-4 px-3">
										<h6 class= "result">Loading game result</h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
										<h6 class= "result"></h6>
									</ul>
								</div>

								<div id="returnToMenu">
									<a href=http://localhost:7777/toptrumps><button class="btn btn-outline-dark btn-lg mb-0 selectbutton">Return to menu</button></a>
								</div>	
							</div>	
						</div>	

						<#--  quick button to leave the game  -->
						<div class="row mt-3">
							<div class="col-md-auto mb-3">
								<a href="http://localhost:7777/toptrumps/">
									<h5>
										<span class="badge badge-warning">Let me leave</span>
									</h5>
								</a>
							</div>
						</div>

						
						<div class="row mt-3">
							<div id="card6" class="card mb-4 shadow-sm" style="width: 11rem; display:none;">
								<div class="card-header">
									<div class="row">
										<div class="col-auto mx-0 pr-0">
											<h5 class="my-0 font-weight-normal">Common pile</h5>
										</div>
										<div class="col-auto mx-0 pl-1">
											<span id = numCards6 class="card-Num" class="badge badge-warning"></span>
										</div>
									</div>
								</div>
								<div  class="card-body mt-1 pt-2">
										<h5 id = "cardName6"></h5>
										<div >
											<img id="cardImg6" src="" alt="Card image" style="width: 8.5rem;">
										</div>
										
									<div id = "cardContent6" class=" list-unstyled mt-3 mb-0 pb-0">
										<h6 class= "attr"></h6>
										<h6 class= "attr"></h6>
										<h6 class= "attr"></h6>
										<h6 class= "attr"></h6>
										<h6 class= "attr"></h6>
									</div>
								</div>
							</div>
						</div>




					</div>
					<div class="col-8">
					<div class="row">
					<div class="col-md-auto">
						<div class="row">
							<div class="col-md-auto mb-1">
								<div id="card1" class="card mb-4 shadow-sm" style="width: 11rem; display:none;">
									<div class="card-header">
										<div class="row">
											<div class="col-auto mx-0 pr-0">
												<h5 class="my-0 font-weight-normal">You</h5>
											</div>
											<div class="col-auto mx-0 pl-1">
												<span id = numCards1 class="card-Num" class="badge badge-warning"></span>
											</div>
										</div>
									</div>
									<div  class="card-body mt-1 pt-2">
											<h5 id = "cardName1"></h5>
											<div >
												<img id="cardImg1" src="" alt="Card image" style="width: 8.5rem;" >
											</div>
											
										<div id = "cardContent1" class=" list-unstyled mt-3 mb-0 pb-0">
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-auto mb-1">
								<div id="card2" class="card mb-4 shadow-sm" style="width: 11rem; display:none;">
									<div class="card-header">
										<div class="row">
											<div class="col-auto mx-0 pr-0">
												<h5 class="my-0 font-weight-normal">AI Player 1</h5>
											</div>
											<div class="col-auto mx-0 pl-1">
												<span id = numCards2 class="card-Num" class="badge badge-warning"></span>
											</div>
										</div>
									</div>
									<div  class="card-body mt-1 pt-2">
											<h5 id = "cardName2"></h5>
											<div >
												<img id="cardImg2" src="" alt="Card image" style="width:8.5rem;">
											</div>
											
										<div id = "cardContent2" class=" list-unstyled mt-3 mb-0 pb-0">
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-auto mb-1">
								<div id="card3" class="card mb-4 shadow-sm" style="width: 11rem; display:none;">
									<div class="card-header">
										<div class="row">
											<div class="col-auto mx-0 pr-0">
												<h5 class="my-0 font-weight-normal">AI Player 2</h5>
											</div>
											<div class="col-auto mx-0 pl-1" >
												<span id = numCards3 class="card-Num" class="badge badge-warning"></span>
											</div>
										</div>
									</div>
									<div  class="card-body mt-1 pt-2">
											<h5 id = "cardName3"></h5>
											<div >
												<img id="cardImg3" src="" alt="Card image" style="width: 8.5rem;">
											</div>
											
										<div id = "cardContent3" class=" list-unstyled mt-3 mb-0 pb-0">
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-auto">
						<div class="row">
							<div class="col-md-auto mb-1">
								<div id="card4" class="card mb-4 shadow-sm" style="width: 11rem; display:none;">
									<div class="card-header">
										<div class="row">
											<div class="col-auto mx-0 pr-0">
												<h5 class="my-0 font-weight-normal">AI Player 3</h5>
											</div>
											<div class="col-auto mx-0 pl-1">
												<span id = numCards4 class="card-Num" class="badge badge-warning"></span>
											</div>
										</div>
									</div>
									<div  class="card-body mt-1 pt-2">
											<h5 id = "cardName4"></h5>
											<div >
												<img id="cardImg4" src="" alt="Card image" style="width: 8.5rem;">
											</div>
											
										<div id = "cardContent4" class=" list-unstyled mt-3 mb-0 pb-0">
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-auto mb-1">
								<div id="card5" class="card mb-4 shadow-sm" style="width: 11rem; display:none;">
									<div class="card-header">
										<div class="row">
											<div class="col-auto mx-0 pr-0">
												<h5 class="my-0 font-weight-normal">AI Player 4</h5>
											</div>
											<div class="col-auto mx-0 pl-1">
												<span id = numCards5 class="card-Num" class="badge badge-warning"></span>
											</div>
										</div>
									</div>
									<div  class="card-body mt-1 pt-2">
											<h5 id = "cardName5"></h5>
											<div >
												<img id="cardImg5" src="" alt="Card image" style="width: 8.5rem;">
											</div>
											
										<div id = "cardContent5" class=" list-unstyled mt-3 mb-0 pb-0">
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
											<h6 class= "attr"></h6>
										</div>
									</div>
								</div>
							</div>

					</div>	
					</div>
					</div>
				</div>
			</div>
			<footer class="pt-4 my-md-5 pt-md-5 border-top">
			
			</footer>
			</div>
		</body>

	<script type="text/javascript">
	
			
           // when human is the active player
			function humanActived(){
				clear();
				document.getElementById("card1").style.display = 'block';
				document.getElementById("selectList").style.display = 'block';
				
			}

           // when AI player is the active player
			function showAISelection(){
				requestAISelect();
				document.getElementById("AISelectButton").style.display  = 'none';
				document.getElementById("showWinner").style.display  = 'block';
				getCardOnDeck();
			}
			

           //reset the page information
			function clear(){
				document.getElementById("playerResult").style.display = 'none';
				document.getElementById("showWinner").style.display = 'none';
				document.getElementById("nextRound").style.display = 'none';
				document.getElementById("humanSelectButton").style.display = 'none';
				document.getElementById("AISelectButton").style.display = 'none';
				document.getElementById("selectList").style.display = 'none';
				document.getElementById("returnToMenu").style.display = 'none';

				clearCard();
			}
            
			//reset the card information
			function clearCard(){
					document.getElementById("card1").style.display = 'none';
					document.getElementById("card2").style.display = 'none';
					document.getElementById("card3").style.display = 'none';
					document.getElementById("card4").style.display = 'none';
					document.getElementById("card5").style.display = 'none';
					document.getElementById("card6").style.display = 'none';
			}



			function playerSelect(num){
				sendPlayerSelect(num);
			}

			function decodeString(s){

				var strr = new Array();
				strr = s.split(',');
				return strr;

			}

			function setUpElements(s, targetID, targetCl){
				for(j=0 ; j<s.length ; j++){
					document.getElementById(targetID).getElementsByClassName(targetCl)[j].innerHTML=s[j];
				}
			}

			

			
			function stage(){
				clear();
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/webMaster"); 
				if (!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e) {

					var num = xhr.response;
					if(num == 2){
						playerSelectEnableStage();
					}
					else if(num == 3){
						playerSelectListStage();
					}

					else if(num == 4){
						waitAIPlayerStage();
					}

					else if(num == 5){
						selectionResultStage();
					}

					else if(num == 6){
						showWinnerStage();
					}
					
					else if(num == 7){
						showGameResultStage();
						requestUpdateGameData();
					}
					else {
						window.location.href = "http://localhost:7777/toptrumps/";
					}

					document.getElementById("gamePanel").style.display  = 'block';
				}
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();	
			}




			function playerSelectEnableStage(){
				getHumanCardOnDeck();
				getGameInfoPackage();
				getCommonPile();
				document.getElementById("humanSelectButton").style.display  = 'block';
			}

			function playerSelectListStage(){
				getHumanCardOnDeck();
				getGameInfoPackage();
				getCommonPile();
				document.getElementById("selectList").style.display  = 'block';
			}


			function waitAIPlayerStage(){
				getHumanCardOnDeck();
				getGameInfoPackage();
				getCommonPile();
				document.getElementById("AISelectButton").style.display = 'block';
			}


			function selectionResultStage(){
				getCardOnDeck();
				getGameInfoPackage();
				getCommonPile();
				document.getElementById("showWinner").style.display  = 'block';
			}

	

			function showWinnerStage(){			
				getGameInfoPackage();
				getWinner();
				getCommonPile();
				document.getElementById("nextRound").style.display  = 'block';

			}

			function showGameResultStage(){
				document.getElementById("playerResult").style.display = 'block';
				getGameInfoPackage();
				getWinner();
				getCommonPile();
				getPlayerResult();

			}





			function userPressNewTurn(){
				clear();
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userPressNewTurn"); 
				if (!xhr) {
					alert("CORS not supported");
				}
				xhr.onload = function(e) {
					stage();
				}
				xhr.send();	
			}


			function userPressSelect(){
				clear();
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userPressSelect"); 
				if (!xhr) {
					alert("CORS not supported");
				}
				xhr.onload = function(e) {
					stage();
				}
				xhr.send();	
			}


			function userSelectAttr1(){
				sendUserSelect("1");
			}
			
			function userSelectAttr2(){
				sendUserSelect("2");
			}

			function userSelectAttr3(){
				sendUserSelect("3");
			}

			function userSelectAttr4(){
				sendUserSelect("4");
			}

			function userSelectAttr5(){
				sendUserSelect("5");
			}



			function sendUserSelect(num) {
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userSelect?Word="+num); // Request type and URL+parameters
				if (!xhr) {
  					alert("CORS not supported");
				}
				xhr.onload = function(e) {
					stage();
				}
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
		
			function userPressShowWinner(){
				clear();
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userPressShowWinner"); 
				if (!xhr) {
					alert("CORS not supported");
				}
				xhr.onload = function(e) {
					stage();
				}
				xhr.send();	
			}


			function setUpCard(stringArray, index){
				var name = new Array();
				var num = new Array();
				var attr =new Array();
				var cardNameI= "cardName"+(index+1);
				var numCardI= "numCards"+(index+1);
				var cardCI= "cardContent"+(index+1);
				name =stringArray[0].split('Name:');
				num	= stringArray[1];
				document.getElementById(cardNameI).innerHTML = name[1];
				getImage(name[1], (index+1));
				document.getElementById(numCardI).innerHTML = num;
				for(j=2 ; j<stringArray.length; j++){
					document.getElementById(cardCI).getElementsByClassName("attr")[j-2].innerHTML=stringArray[j];
				}
			}



			function getImage(cardName, index) {
				var img = document.getElementById("cardImg" + index);
				img.src = "http://localhost:7777/assets/"+cardName+".jpg";
			}



			function javaArrayDecode(input){
					var strr = new Array();
					strr = input.split('|?|');
					return strr;
			}
			// -----------------------------------------
			// Add your other Javascript methods Here
			// -----------------------------------------
		
			// This is a reusable method for creating a CORS request. Do not edit this.
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
		
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">
	


			
				
			function getPlayerResult(){
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateViewPlayerResult"); // Request type and URL+parameters
				if (!xhr) {
  					alert("CORS not supported");
				}
				xhr.onload = function(e) {
 					let responseText = xhr.response; // the text of the response
					var strr = new Array();
					strr = javaArrayDecode(responseText);
					setUpElements(strr,"playerResult", "result");

				}
				
				xhr.send();
			}
			

			
			function getWinner() {
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateViewShowWinnerCard"); 
				if (!xhr) {
					alert("CORS not supported");
				}
				xhr.onload = function(e) {
					var responseText = xhr.response;
					var strr = new Array();
					strr=javaArrayDecode(responseText);
					if(strr[0]=='null'){
					}else{
						cardI = "card"+(1+parseInt(strr[0]));
						document.getElementById(cardI).style.display = 'block';
						setUpCard(decodeString(strr[1]),parseInt(strr[0]));
					}

				}

				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();	
			}
			
			function getCommonPile() {
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateViewCommonPileCard"); 
				if (!xhr) {
					alert("CORS not supported");
				}
				xhr.onload = function(e) {

					var responseText = xhr.response;
					var strr = new Array();
					strr=javaArrayDecode(responseText);

					if(strr[0]=='null'){
					}else{
						
						cardI = "card"+(1+parseInt(strr[0]));
						document.getElementById(cardI).style.display = 'block';
						setUpCard(decodeString(strr[1]),parseInt(strr[0]));
					}
				}

				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();	
			}





			function requestAISelect(){
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/requestAISelect"); // Request type and URL+parameters
					if (!xhr) {
						alert("CORS not supported");
					}
					xhr.onload = function(e) {
						getGameInfoPackage();
					}
				xhr.send();

			}

			function requestUpdateGameData(){
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/requestUpdateGameData"); // Request type and URL+parameters
					if (!xhr) {
						alert("CORS not supported");
					}
					xhr.onload = function(e) {
						getGameInfoPackage();
					}
				xhr.send();
			}


			function getGameInfoPackage(){
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateViewGameInformPackage"); 
					if (!xhr) {
						alert("CORS not supported");
					}
					xhr.onload = function(e) {
						var responseText = xhr.response; // the text of the response
						var strr = new Array();
						strr=javaArrayDecode(responseText);
						document.getElementById("gameStatus").innerHTML = strr[0];
						document.getElementById("gameRole").innerHTML = strr[1];
					}
					// We have done everything we need to prepare the CORS request, so send it
					xhr.send();	
			}


	

			function getHumanCardOnDeck() {
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateViewHumanCardOnDeck"); // Request type and URL+parameters
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
					alert("CORS not supported");
				}
				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
					var responseText = xhr.response; // the text of the response
					if(responseText=='null'){
							document.getElementById("card1").style.display = 'none';  
					}
					else{
						setUpCard(decodeString(responseText),0);
						document.getElementById("card1").style.display = 'block';
					}
				}
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();
			}



			function getCardOnDeck() {
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateViewCardSOnDeck"); // Request type and URL+parameters
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
					alert("CORS not supported");
				}
				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
					let responseText = xhr.response; // the text of the response
					  var strr = new Array();
					  strr = javaArrayDecode(responseText);
					  for(i = 0 ; i<strr.length; i++){
						  cardI = "card"+(1+i);
						  cardCI = "cardContent"+(1+i);
					  	if(strr[i]=='null'){
							document.getElementById(cardI).style.display = 'none';  
					 	 }
					  	else{
							setUpCard(decodeString(strr[i]),i);
							document.getElementById(cardI).style.display = 'block';
						}
					}
				}
				xhr.send();
			}


		</script>
</html>