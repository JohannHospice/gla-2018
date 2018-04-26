
	$(document).ready(function () {
	  $('input[type="file"]').on('change', function(event){ 
     		console.log('changed!'); 
		});
	});
      function initAutocomplete() {
        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: -33.8688, lng: 151.2195},
          zoom: 13,
          mapTypeId: 'roadmap'
        });
        
	
        // creer le searchbox et le mettre en haut a gauche
        var input = document.getElementById('pac-input');
        var searchBox = new google.maps.places.SearchBox(input);
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

        // Bias the SearchBox results towards current map's viewport.
        map.addListener('bounds_changed', function() {
          searchBox.setBounds(map.getBounds());
        });
				
        var markers = [];
        // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.
        searchBox.addListener('places_changed', function() {
          var places = searchBox.getPlaces();

          if (places.length == 0) {
            return;
          }

          // Clear out the old markers.
          markers.forEach(function(marker) {
            marker.setMap(null);
          });
          markers = [];
          // For each place, get the icon, name and location.
          var bounds = new google.maps.LatLngBounds();
          places.forEach(function(place) {
            if (!place.geometry) {
              console.log("Returned place contains no geometry");
              return;
            }
            var icon = {
              url: place.icon,
              size: new google.maps.Size(71, 71),
              origin: new google.maps.Point(0, 0),
              anchor: new google.maps.Point(17, 34),
              scaledSize: new google.maps.Size(25, 25)
            };

            // Create a marker for each place.
            markers.push(new google.maps.Marker({
              map: map,
              icon: icon,
              title: place.name,
              position: place.geometry.location
            }));

            if (place.geometry.viewport) {
              // Only geocodes have viewport.
              bounds.union(place.geometry.viewport);
            } else {
              bounds.extend(place.geometry.location);
            }
          });
          map.fitBounds(bounds);
        });
     
         
      
      //listener pour ajouter un popup avec le formulaire sur la map
		google.maps.event.addListener(map, 'click', function(event) {
		 var myLatLng = event.latLng;
    	 var lat = myLatLng.lat();
    	 var lng = myLatLng.lng();
    	 openFormulaire(lat,lng);
 	 	});
 
 	  
 	 	
 	 	var marker;
 	   	//lecontenu de infowindow (le formulaire)
 	 	var contentWindow=
 	 	'<form action="myServer" method="post">'+
  			'<div>'+
        		'<label for="place">Place :</label>'+
        		'<input type="text" id="place" name="user_place">'+
    		'</div>'+
    		'<div>'+
        		'<label for="tag"> #tag :</label>'+
        		'<input type="tag" id="tag" name="user_tag">'+
    		'</div>'+
    		'<button type="button" id="submit" onClick="toSubmit">submit</button> '+
			'<button type="button" id="cancel" onClick="toCancel">cancel</button> '+
			'<input type="file">'+'<br>'+
			'<img src="" height="200" alt="Image preview...">'+
		'</form>'
		 ;
 	 	
 	 	var marker;
		function openFormulaire(lat,lng) {
			 var infowindow = new google.maps.InfoWindow({
			 	'position' : {lat:lat, lng:lng}, 
    			'content': contentWindow
    			
 			 });
 			 	
  			 marker = new google.maps.Marker({ //on créé le marqueur
      		 	position: {lat:lat, lng:lng},
      			map: map
    		 });
    		 infowindow.open(map, marker);
    		 
    		 marker.addListener('click',function(){
 	  			infowindow.open(map, marker);
 	 		 });
 	 		 //listener pour supprimer le marker de la map
 	 		 //PS: IL FAUT IMPLEMENTER ICI LA SUPRESSION DES INFO DE LA BD
 	 		 marker.addListener('rightclick',function(){
 	 		 	this.setMap(null);
 	 		 	marker=null;
 	 		 });
    	}
    	
    	//fonction toSubmit()
    	function toSubmit(){
    	
   		}
    	//function toCancel()
    	function toCancel(){
    	
    	}
	
 



  }

		