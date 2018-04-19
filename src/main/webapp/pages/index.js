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

let register_tpl = _.template(require('./tpl/register.html'));

let main = () => {
  let register_page = $(register_tpl());
  register_page.find('form.register-form').on('submit', (ev) => {
    return false;
  });
  $('div.app').html(register_page);
  /*get('ws/user/all', (data, _status, xhr) => {
    console.log(data, xhr);
    $('div.app').html(tpl(data));
  });*/
}

module.exports = {
  main: main
};
