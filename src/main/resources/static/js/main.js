var baseURL = new URL(window.location.origin);

var map = L.map('mapid').setView([45.50955, -73.569131],13);
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', 
{
   attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
         '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
         'Imagery © <a href="http://mapbox.com">Mapbox</a>',
   maxZoom: 18,
   id: 'mapbox.streets',
   accessToken: 'pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw'
}).addTo(map)

var foodtruckMarkers = [];
var bixiMarkers = [];
var arceauMarkers = [];

var rechercheResult = document.getElementById("rechercheResult");

var placePartagee = function(request, foodtruckJson, index)
{
   var longitude = foodtruckJson[index].longitude;
   var latitude = foodtruckJson[index].latitude;
   
   for (var j=0 ; j<foodtruckMarkers.length; j++)
   {
      if (foodtruckMarkers[j].getLatLng().lat === latitude && foodtruckMarkers[j].getLatLng().lng === longitude)
      {
         return j;
      }
   }
   return -1;
}

var clearFoodtruckMarkers = function()
{
   for (var i=0 ; i<foodtruckMarkers.length ; i++)
   {
      map.removeLayer(foodtruckMarkers[i]);
   }
}

var showFoodtruck = function(request)
{
   clearFoodtruckMarkers();
   foodtruckMarkers = [];
   var foodtruckJson = JSON.parse(request.responseText);
   rechercheResult.innerHTML = foodtruckJson.length + " food truck trouvé !";
   
   for ( var i=0 ; i<foodtruckJson.length ; i++)
   {
      var foodtruckGroup = placePartagee(request, foodtruckJson, i);
      
      if (foodtruckGroup !== -1)
      {
         foodtruckMarkers[foodtruckGroup].popupText += "<br><br>Camion: " + foodtruckJson[i].camion
                                                      +"<br>Lieu: " + foodtruckJson[i].lieu
                                                      +"<br>Date: " + foodtruckJson[i].date
                                                      +"<br>Heure ouverture: " + foodtruckJson[i].heureDebut
                                                      +"<br>Heure fermeture: " + foodtruckJson[i].heureFin;
      }
      else
      {
         var marker = new L.Marker([foodtruckJson[i].latitude, foodtruckJson[i].longitude]).addTo(map);
         marker.popupText = "Camion: " + foodtruckJson[i].camion
                           +"<br>Lieu: " + foodtruckJson[i].lieu
                           +"<br>Date: " + foodtruckJson[i].date
                           +"<br>Heure ouverture: " + foodtruckJson[i].heureDebut
                           +"<br>Heure fermeture: " + foodtruckJson[i].heureFin;
                 
         marker.on('click', onFoodtruckMarkerClick);
         marker.setIcon(L.icon({iconUrl:"/foodtruck.png"}));
         foodtruckMarkers.push(marker);
      }

   }
   
   for (var i=0 ; i<foodtruckMarkers.length ; i++)
   {
      foodtruckMarkers[i].bindPopup(foodtruckMarkers[i].popupText);
   }
}

var clearBixiMarkers = function()
{
   for (var i=0 ; i<bixiMarkers.length ; i++)
   {
      map.removeLayer(bixiMarkers[i]);
   }
}

var showBixi = function(requestBixi)
{
   clearBixiMarkers();
   BixiMarkers = [];
   
   var bixiJson = JSON.parse(requestBixi.responseText);
   
   for (var i=0 ; i<bixiJson.length ; i++)
   {
      var bixiMarker = new L.Marker([bixiJson[i].latitude, bixiJson[i].longitude]).addTo(map);
      bixiMarker.popupText = "Place restantes: " + bixiJson[i].nbEmptyDocks;
      //bixiMarker.setIcon(L.icon({iconUrl:"bixi.png"}));
      bixiMarker.bindPopup(bixiMarker.popupText);
      bixiMarkers.push(bixiMarker);
   }
}

var onFoodtruckMarkerClick = function(e)
{
   var longitude = e.latlng.lng;
   var latitude = e.latlng.lat;
   var urlBixi = baseURL + "/bixi?lat=" +  latitude + "&lon=" + longitude;
   
   var requestBixi = new XMLHttpRequest();
   requestBixi.onreadystatechange = function()
   {
      if (requestBixi.readyState === 4 && requestBixi.status === 200)
      {
         showBixi(requestBixi);
      }
   }
   requestBixi.open("GET", urlBixi, true);
   requestBixi.send();
   
}

var onRechercherClick = function()
{
   var dateDebut = document.getElementById("dateDebut").value;
   var dateFin = document.getElementById("dateFin").value;
   
   var url = baseURL + "/horaire-camions/" + dateDebut + "/" + dateFin;
   
   var request = new XMLHttpRequest();
   request.onreadystatechange = function()
   {
      if (request.readyState === 4 && request.status === 200)
      {
         showFoodtruck(request);
      }
   }
   request.open("GET", url, true);
   request.send();
}
