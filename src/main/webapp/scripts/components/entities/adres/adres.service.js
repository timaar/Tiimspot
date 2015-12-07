'use strict';

angular.module('tiimspotApp')
    .factory('Adres', function ($resource, DateUtils) {
        return $resource('api/adress/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
