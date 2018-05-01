const $ = require('jquery');
const _ = require('underscore');
const queryString = require('query-string');

const { onLogin, showLogin, showRegister, hideOverlay } = require('../overlay.js');

const initMap = (map_data, googleMaps) => {
  let map = new googleMaps.Map($('div.show-map div.map')[0], {
    center: {lat: 48.8709, lng: 2.3561},
    zoom: 12,
    mapTypeId: 'roadmap'
  });

  const generateContentWindowLocation = (loc) => {
    let tpl = _.template(require('./tpl/map-location.html'));
    return $(tpl({'location': loc}))[0];
  };

  const addMarker = (loc) => {
    loc.latitude = parseFloat(loc.latitude);
    loc.longitude = parseFloat(loc.longitude);
    if (loc.content.length)
      loc.content = loc.content[0];
    if (loc.image.length)
      loc.content = loc.image[0];

    let infowindow = new googleMaps.InfoWindow({
      'position' : {lat: loc.latitude, lng: loc.longitude},
      'content': generateContentWindowLocation(loc),
    });
    let marker = new googleMaps.Marker({
      position: new googleMaps.LatLng(loc.latitude, loc.longitude),
      map: map,
      title: loc.nameplace,
    });
    infowindow.open(map, marker);
    //listener pour pour ouvrir l'infowindow si elle est fermée
    marker.addListener('click', () => {
      infowindow.open(map, marker);
    });
    marker.addListener('rightclick', () => { // TODO delete location
      this.setMap(null);
      marker=null;
    });
  };

  const loadLocations = () => {
    $.ajax({
      url: '/ws/location/get/by-mapId/' + map_data.id,
    }).fail(console.log).done((data) => {
      console.log(data);
      data.forEach(addMarker);
    });
  };
  loadLocations();

  //ajout sur la map des markeurs aprtir d'une liste de locations provenant de ES
  /*const addMarkers = (locationList) => {
    for (let i = 0; i < locationList.length; i++){
      let infowindow = new googleMaps.InfoWindow({
        'position' : {lat:locationList[i][1], lng:locationList[i][2]},
        'content': generateContentWindowLocation({name: locationList[i][0], image: 'about:blank'}),
      });
      let marker = new googleMaps.Marker({
        position: new googleMaps.LatLng(locationList[i][1], locationList[i][2]),
        map: map,
        title: locationList[i][0]
      });
      infowindow.open(map, marker);
      //listener pour pour ouvrir l'infowindow si elle est fermée
      marker.addListener('click', () => {
        infowindow.open(map, marker);

      });
      marker.addListener('rightclick', () => {
        this.setMap(null);
        marker=null;
      });

    }
  };*/

  const generateContentWindowForm = (lat, lng, marker, infowindow) => {
    let tpl = _.template(require('./tpl/map-form.html'));
    let form = $(tpl({map: map_data, lat: lat, lng: lng}));
    let html = $('<div></div>');
    html.append(form);
    form.submit(() => {
      $.ajax({
        method: 'PUT',
        url: '/ws/location/add',
        data: form.serialize(),
      }).fail((err) => {
        console.log(err);
        alert('Location name already exists!');
      }).done((data) => {
        console.log(data);
        alert('Saved');
        addMarker(data);
        infowindow.close();
        marker.setMap(null);
      });
      return false;
    });
    return html[0];
  };

  const openFormulaire = (lat,lng) => {
    let marker = new googleMaps.Marker({ //on créé le marqueur
      position: {lat:lat, lng:lng},
      map: map
    });

    let infowindow = new googleMaps.InfoWindow({
      'position' : {lat:lat, lng:lng},
    });
    infowindow.setContent(generateContentWindowForm(lat, lng, marker, infowindow));

    infowindow.addListener('closeclick', () => {
      infowindow.close();
      marker.setMap(null);
    });

    infowindow.open(map, marker);

/*
    //listener pour pour ouvrir l'infowindow si elle est fermée
    marker.addListener('click', () => {
      infowindow.open(map, marker);

    });
    //listener pour supprimer le marker de la map
    //PS: IL FAUT IMPLEMENTER ICI LA SUPRESSION DES INFO DE LA BD
    marker.addListener('rightclick', () => {
      this.setMap(null);
    });*/
  }

  //la locationlist quiprovient de ES
  /*let locationList =[
    ['GrandmoulinParis', 48.8299181, 2.3812189999999998, 'tag1'],
    ['Paris', 48.856614,2.3522219000000177, 'tag2']
  ];

  addMarkers(locationList);*/

  // creer le searchbox et le mettre en haut a gauche
  let input = $('div.show-map input.pac-input')[0];
  let searchBox = new googleMaps.places.SearchBox(input);
  map.controls[googleMaps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', () => {
    searchBox.setBounds(map.getBounds());
  });

  let markers = [];
  //ajout d'un listener si l'utilisateur selectionne une prediction et récupèration 
  // plus d'info sur cette place 
  searchBox.addListener('places_changed', () => {
    let places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }

    //efface les anciens marqueus pour rajouter le nouveau a leurs places
    markers.forEach((marker) => {
      marker.setMap(null);
    });
    markers = [];
    //pour chaque place on recupère son icone, son nom et sa location
    let bounds = new googleMaps.LatLngBounds();
    places.forEach((place) => {
      if (!place.geometry) {
        console.log("Returned place contains no geometry");
        return;
      }
      let icon = {
        url: place.icon,
        size: new googleMaps.Size(71, 71),
        origin: new googleMaps.Point(0, 0),
        anchor: new googleMaps.Point(17, 34),
        scaledSize: new googleMaps.Size(25, 25)
      };

      // creer un marqueur pour chaque postion
      markers.push(new googleMaps.Marker({
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
  googleMaps.event.addListener(map, 'click', (ev) => {
    let myLatLng = ev.latLng;
    let lat = myLatLng.lat();
    let lng = myLatLng.lng();
    openFormulaire(lat,lng);
  });
}

const showUserMaps = (user) => {
  $('div.app').html(require('./tpl/create_map.html'));
  let form = $('div.app form.form-create-map');
  form.submit(() => {
    $.ajax({
      method: 'PUT',
      url: '/ws/map/add',
      data: form.serialize(),
    }).fail(console.log).done((data) => {
      console.log(data);
      window.location.replace('?id=' + data.id);
    });
    return false;
  });
  $.ajax({
    url: '/ws/user/maps',
  }).fail(console.log).done((data) => {
    console.log(data);
    let tpl = _.template(require('./tpl/personal_map.html'));
    data.forEach((map) => {
      $('div.app').append(tpl({map: map}));
    });
  });
};

const showMap = (user, map) => {
  $.ajax({
    url: '/ws/map/by-id/' + map
  }).fail(console.log).done((data) => {
    console.log(data);
    let tpl = _.template(require('./tpl/map.html'));
    $('div.app').html(tpl({map: data}));
    const loadGoogleMapsApi = require('load-google-maps-api');
    let initMapCallback = (_data) => {
      return (googleMaps) => {
        initMap(_data, googleMaps);
      }
    };
    loadGoogleMapsApi({
      libraries: ['places'],
      key: 'AIzaSyBNHJBng7O58WtcW4JoZG7j7GQedI_jrmk',
    }).then(initMapCallback(data));
  });
};

const main = () => {
  const parsed = queryString.parse(location.search);
  console.log(parsed);
  if (parsed.id)
    onLogin((user) => showMap(user, parsed.id), () => showMap(null, parsed.id));
  else
    onLogin(showUserMaps, showLogin);
}

module.exports = {
  main: main
};
