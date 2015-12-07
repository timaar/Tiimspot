'use strict';

angular.module('tiimspotApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


