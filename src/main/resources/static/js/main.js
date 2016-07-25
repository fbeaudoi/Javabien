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

L.marker([45.50955, -73.569131]).addTo(map);

var fetchFoodtruck = function(url)
{
   alert(url);
   fetch(url).then(function(resp)
   {
      return rest.json()
   }).then(function (data)
   {
      
      showFoodTruck(data);
   })
}

var onResearchClick = function()
{
   var dateDebut = document.getElementById("dateDebut").value;
   var dateFin = document.getElementById("dateFin").value;
   
   var url = baseURL + "/horaire-camions/" + dateDebut + "/" + dateFin;
   fetchFoodtruck(url);
   return true;
}









/*
var renderCitation = function (citation) {
  return '<li>'+ citation.contenu +' &mdash;'+ citation.auteur +'</li>'
}

var renderListeCitations = function (citations) {
  return '<ul>'+ citations.map(renderCitation).join('') +'</ul>'
}

var installerListeCitations = function (listeCitationsHtml) {
  document.getElementById('liste-citations').innerHTML = listeCitationsHtml
}

var fetchAll = function () {
  fetch('/citations').then(function(resp) {
    return resp.json()
  }).then(function (data) {
    installerListeCitations(renderListeCitations(data))
  })
}

document.addEventListener('DOMContentLoaded', fetchAll)
*/
