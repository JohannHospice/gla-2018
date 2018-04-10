const $ = require('jquery');
const _ = require('underscore');

let get = (url, callback) => {
  $.ajax({
    url: url,
    dataType: 'json',
  }).done(callback).fail((err) => {
    console.log(err);
  });
}

let tpl = _.template(require('./tpl/hello.html'));

let main = () => {
  get('ws/user/all', (data, _status, xhr) => {
    console.log(data, xhr);
    //$('div.app').html(tpl(data));
  });
}

module.exports = {
  main: main
};
